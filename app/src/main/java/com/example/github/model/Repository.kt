package com.example.github.model

data class Repository (
    val name: String,
    val forks: Int,
    val stargazers_count: Int,
    val html_url: String
)