package com.hexagonal.architecture.example.domain.repository

interface PostRepository {
    fun save()
    fun findOne()
    fun findAll()
}