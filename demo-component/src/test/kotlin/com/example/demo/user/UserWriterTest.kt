package com.example.demo.user

import com.example.demo.encrypt.EncryptDecryptManager
import com.example.demo.jpa.user.UserEntity
import com.example.demo.jpa.user.UserRepository
import com.example.demo.user.request.UserAddRequestBuilder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserWriterTest {
    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var encryptDecryptManager: EncryptDecryptManager

    private lateinit var sut: UserWriter

    @BeforeEach
    fun setUp() {
        sut = UserWriter(
            userRepository = userRepository,
            encryptDecryptManager = encryptDecryptManager,
        )
    }

    @Test
    fun `유저 초기 정보를 저장할 때 이메일은 암호화하여 저장한다`() {
        // given
        val givenEmail = "initialEmail"
        val givenInitiatedUser = UserAddRequestBuilder(
            email = givenEmail,
        ).build()

        val givenEncryptedEmail = "encryptedEmail"
        every { encryptDecryptManager.encrypt(givenEmail) } returns givenEncryptedEmail

        val givenSlot = slot<UserEntity>()
        every { userRepository.save(capture(givenSlot)) } returnsArgument 0

        // when
        sut.write(givenInitiatedUser)

        // then
        val capturedEmail = givenSlot.captured.email
        capturedEmail shouldNotBe givenEmail
        capturedEmail shouldBe givenEncryptedEmail
    }
}
