package com.example.demo.user.request

data class UserUpdateRequest(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
)
