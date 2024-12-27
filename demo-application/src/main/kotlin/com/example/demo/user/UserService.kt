package com.example.demo.user

import com.example.demo.team.TeamWriter
import com.example.demo.user.request.UserAddRequest
import com.example.demo.user.request.UserUpdateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionOperations

@Service
class UserService(
    private val userWriter: UserWriter,
    private val userReader: UserReader,
    private val userValidator: UserValidator,
    private val teamWriter: TeamWriter,
    private val transactionOperations: TransactionOperations,
) {
    fun write(user: UserAddRequest) {
        userWriter.write(user)
    }

    fun update(updateRequest: UserUpdateRequest) {
        val registeredUser = userReader.readOrNull(updateRequest.id)
            ?: throw RuntimeException("Not Exist User (id=${updateRequest.id})")

        userValidator.validateForUpdate(registeredUser)

        transactionOperations.executeWithoutResult {
            userWriter.update(
                user = registeredUser,
                updateRequest = updateRequest,
            )
            teamWriter.update(registeredUser)
        }
    }
}
