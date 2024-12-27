package com.example.demo.user.request

data class UserUpdateRequestBuilder(
    val id: Long = 1L,
    val name: String = "name",
    val email: String = "some@gmail.com",
    val age: Int = 10,
) {
    fun build(): UserUpdateRequest {
        return UserUpdateRequest(
            id = id,
            name = name,
            email = email,
            age = age,
        )
    }
}
