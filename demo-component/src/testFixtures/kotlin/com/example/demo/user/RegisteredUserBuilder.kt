package com.example.demo.user

data class RegisteredUserBuilder(
    val id: Long = 1L,
    val name: String = "name",
    val email: String = "some@gmail.com",
    val age: Int = 10,
) {
    fun build(): RegisteredUser {
        return RegisteredUser(
            id = id,
            name = name,
            email = email,
            age = age
        )
    }
}
