package com.hexagonal.architecture.example.adapters.mongo.model


class PostDb(
    val id: String,
    val userId: String,
    val content: String,
    val userInfo: UserInfoDb,
)