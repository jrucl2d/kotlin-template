package com.example.demo.encrypt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class EncryptDecryptManager(
    @Value("\${decrypt.secret.key}") private val secretKey: String,
    @Value("\${decrypt.secret.iv}") private val secretIv: String,
) {
    private lateinit var secretKeySpec: SecretKeySpec
    private lateinit var ivParameterSpec: IvParameterSpec

    init {
        require(secretKey.length == 32) { "Key must be 32 characters (256 bits) long" }
        require(secretIv.length == 16) { "IV must be 16 characters (128 bits) long" }

        secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
        ivParameterSpec = IvParameterSpec(secretIv.toByteArray())
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedInput: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decodedBytes = Base64.getDecoder().decode(encryptedInput)
        val originalBytes = cipher.doFinal(decodedBytes)
        return String(originalBytes)
    }

    companion object {
        private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    }
}
