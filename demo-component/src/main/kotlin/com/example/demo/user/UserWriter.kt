package com.example.demo.user

import com.example.demo.encrypt.EncryptDecryptManager
import com.example.demo.jpa.user.UserEntity
import com.example.demo.jpa.user.UserRepository
import com.example.demo.user.request.UserAddRequest
import com.example.demo.user.request.UserUpdateRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UserWriter(
    private val userRepository: UserRepository,
    private val encryptDecryptManager: EncryptDecryptManager,
) {
    fun write(user: UserAddRequest) {
        val encryptedEmail = encryptDecryptManager.encrypt(user.email)
        val entity = UserEntity(
            name = user.name,
            email = encryptedEmail,
            age = user.age,
        )
        userRepository.save(entity)
    }

    @Transactional
    fun update(user: RegisteredUser, updateRequest: UserUpdateRequest) {
        TODO("업데이트 로직 수행")
    }
}
