package com.hexagonal.architecture.example.domain.model

class Post(
    val id: String,
    val userId: String,
    val userInfo: UserInfo,
    val content: String,
)