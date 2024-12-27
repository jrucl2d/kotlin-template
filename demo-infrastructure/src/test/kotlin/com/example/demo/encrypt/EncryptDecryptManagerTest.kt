package com.example.demo.encrypt

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EncryptDecryptManagerTest {
    private val givenSecretKey = "12345678901234567890123456789012"
    private val givenSecretIv = "1234567890123456"

    private lateinit var sut: EncryptDecryptManager

    @BeforeEach
    fun setUp() {
        sut = EncryptDecryptManager(
            secretKey = givenSecretKey,
            secretIv = givenSecretIv,
        )
    }

    @Test
    fun `주어진 문자열을 암호화한 뒤 다시 복호화하면 최초의 문자열과 같다`() {
        // given
        val givenString = "someString"
        val givenEncrypted = sut.encrypt(givenString)

        // when
        val actual = sut.decrypt(givenEncrypted)

        // then
        actual shouldBe givenString
    }
}
