package com.hexagonal.architecture.example.domain.repository

interface UserRepository {
    fun save()
    fun findOne()
    fun findAll()
}

