package com.example.demo.user

import com.example.demo.jpa.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository,
) {
    fun readOrNull(id: Long): RegisteredUser? {
        return userRepository.findById(id)
            .orElse(null)
            ?.let {
                RegisteredUser(
                    id = id,
                    name = it.name,
                    email = it.email,
                    age = it.age,
                )
            }
    }
}
