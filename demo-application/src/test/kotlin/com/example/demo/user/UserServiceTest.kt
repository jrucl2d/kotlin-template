package com.example.demo.user

import com.example.demo.team.TeamWriter
import com.example.demo.user.request.UserUpdateRequestBuilder
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.transaction.support.TransactionOperations

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @MockK
    private lateinit var userWriter: UserWriter

    @MockK
    private lateinit var userReader: UserReader

    @MockK
    private lateinit var userValidator: UserValidator

    @MockK
    private lateinit var teamWriter: TeamWriter

    private lateinit var sut: UserService

    @BeforeEach
    fun setUp() {
        sut = UserService(
            userWriter = userWriter,
            userReader = userReader,
            userValidator = userValidator,
            teamWriter = teamWriter,
            transactionOperations = TransactionOperations.withoutTransaction(),
        )
    }

    @Test
    fun `수정시 등록된 유저 정보가 없다면 에러를 던진다`() {
        // given
        val givenUpdateRequest = UserUpdateRequestBuilder().build()

        every { userReader.readOrNull(givenUpdateRequest.id) } returns null

        // when
        val actual = shouldThrowExactly<RuntimeException> {
            sut.update(givenUpdateRequest)
        }

        // then
        actual.message shouldBe "Not Exist User (id=${givenUpdateRequest.id})"
        verify(exactly = 0) {
            userValidator.validateForUpdate(any())
            userWriter.update(any(), any())
            teamWriter.update(any())
        }
    }

    @Test
    fun `수정시 등록된 유저의 수정 가능 여부를 검증하고 업데이트 후 팀 정보도 업데이트한다`() {
        // given
        val givenUpdateRequest = UserUpdateRequestBuilder().build()

        val givenUser = RegisteredUserBuilder().build()
        every { userReader.readOrNull(givenUpdateRequest.id) } returns givenUser

        justRun {
            userValidator.validateForUpdate(givenUser)
            userWriter.update(
                user = givenUser,
                updateRequest = givenUpdateRequest,
            )
            teamWriter.update(givenUser)
        }

        // when
        sut.update(givenUpdateRequest)

        // then
        verify(exactly = 1) {
            userValidator.validateForUpdate(givenUser)
            userWriter.update(
                user = givenUser,
                updateRequest = givenUpdateRequest,
            )
            teamWriter.update(givenUser)
        }
    }
}
