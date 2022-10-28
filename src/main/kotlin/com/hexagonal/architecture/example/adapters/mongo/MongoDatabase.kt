package com.hexagonal.architecture.example.adapters.mongo;

import com.hexagonal.architecture.example.domain.repository.Database
import com.hexagonal.architecture.example.domain.repository.Transaction
import kotlin.Suppress;
import org.slf4j.LoggerFactory
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional

@Component
class MongoDatabase(private val transactionalExecutor: TransactionalExecutor) : Database {

    private val retryTemplate = RetryTemplate.builder()
        .retryOn(Exception::class.java)
        .withListener(DbRetryListener())
        .traversingCauses()
        .maxAttempts(MAXIMUM_RETRIES)
        .uniformRandomBackoff(1, MAXIMUM_BACKOFF_TIME)
        .build()

    override fun <T> run(t: Transaction<T>): T =
        try {
            retryTemplate.execute(RetryCallback<T, RuntimeException?> { transactionalExecutor.execute(t.func) })
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            t.rollbackCallback?.let { it(e) }
            throw e
        }.also { result ->
            t.commitCallback?.let { it(result) }
        }

    class DbRetryListener : RetryListener {
        override fun <T, E : Throwable?> open(context: RetryContext, callback: RetryCallback<T, E>) = true

        override fun <T, E : Throwable?> close(
            context: RetryContext,
            callback: RetryCallback<T, E>,
            throwable: Throwable?
        ) {
            if (throwable != null && context.retryCount > 1) {
                logger.warn("Failed to execute transaction multiple times. Giving up.", throwable)
            }
        }

        override fun <T, E : Throwable?> onError(
            context: RetryContext,
            callback: RetryCallback<T, E>,
            throwable: Throwable
        ) {
            logger.info("Failed transaction (${context.retryCount} times) with ${throwable.javaClass.simpleName}. Retrying after delay")
        }
    }

    @Component
    class TransactionalExecutor {
        @Transactional(rollbackFor = [Exception::class])
        fun <T> execute(func: () -> T): T = func()
    }

    companion object {
        const val MAXIMUM_RETRIES = 5
        const val MAXIMUM_BACKOFF_TIME = 300L

        private val logger = LoggerFactory.getLogger(MongoDatabase::class.java)
    }
}