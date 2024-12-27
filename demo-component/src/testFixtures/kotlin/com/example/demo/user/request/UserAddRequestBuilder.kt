package com.example.demo.user.request

data class UserAddRequestBuilder(
    val name: String = "name",
    val email: String = "some@gmail.com",
    val age: Int = 10,
) {
    fun build(): UserAddRequest {
        return UserAddRequest(
            name = name,
            email = email,
            age = age,
        )
    }
}
