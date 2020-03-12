package com.example.github.model

data class User (
    val login: String,
    val id: Int,
    val email: String,
    val location: String,
    val created_at: String,
    val followers: Int,
    val following: Int,
    val blog: String,
    val avatar_url: String
)