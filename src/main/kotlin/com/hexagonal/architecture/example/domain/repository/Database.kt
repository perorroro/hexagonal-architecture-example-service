package com.hexagonal.architecture.example.domain.repository

interface Database {
    fun <T> transaction(func: () -> T): TransactionBuilder<T> = TransactionBuilder(this, Transaction(func))
    fun <T> run(t: Transaction<T>): T
}

class TransactionBuilder<T>(private val db: Database, private val transaction: Transaction<T>) {
    fun onCommit(cb: (T) -> Unit) = TransactionBuilder(db, transaction.copy(commitCallback = cb))
    fun onRollback(cb: (Exception) -> Unit) = TransactionBuilder(db, transaction.copy(rollbackCallback = cb))
    fun run(): T = db.run(transaction)
}

data class Transaction<T>(
    val func: () -> T,
    val commitCallback: ((T) -> Unit)? = null,
    val rollbackCallback: ((Exception) -> Unit)? = null
)
