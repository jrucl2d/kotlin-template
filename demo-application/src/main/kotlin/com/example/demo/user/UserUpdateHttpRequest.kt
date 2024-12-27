package com.example.demo.user

import com.example.demo.user.request.UserUpdateRequest
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserUpdateHttpRequest(
    @field:NotBlank(message = "이름은 비어있으면 안 됩니다.")
    val name: String?,

    @field:Email(message = "이메일 형식에 맞지 않습니다.")
    @field:NotBlank(message = "이메일은 비어있으면 안 됩니다.")
    val email: String?,

    @field:Min(value = 10L, message = "나이는 10살 이상이어야 합니다.")
    @field:NotNull(message = "나이는 비어있으면 안 됩니다.")
    val age: Int?,
) {
    fun toDomain(id: Long): UserUpdateRequest {
        return UserUpdateRequest(
            id = id,
            name = requireNotNull(name),
            email = requireNotNull(email),
            age = requireNotNull(age),
        )
    }
}
