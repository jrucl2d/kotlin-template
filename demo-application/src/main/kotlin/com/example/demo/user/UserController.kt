package com.example.demo.user

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/user")
    fun addUser(@RequestBody request: UserAddHttpRequest) {
        val addRequest = request.toDomain()
        userService.write(addRequest)
    }

    @PutMapping("/user/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UserUpdateHttpRequest,
    ) {
        val updateRequest = request.toDomain(id)
        userService.update(updateRequest)
    }
}
