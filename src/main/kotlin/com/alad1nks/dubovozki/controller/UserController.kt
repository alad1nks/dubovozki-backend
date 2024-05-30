package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.User
import com.alad1nks.dubovozki.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/user")
class UserController(
    val userService: UserService
) {
    @GetMapping("/list")
    fun getUserList(): List<User> = userService.getUserList()

    @PostMapping("/create")
    fun createUser(
        @RequestBody user: User
    ): User? = userService.createUser(user)
}
