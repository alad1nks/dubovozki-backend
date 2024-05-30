package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.User
import com.alad1nks.dubovozki.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun getUserList(): List<User> = userRepository.findAll()

    fun createUser(user: User): User? {
        if (userRepository.existsByEmail(user.email)) {
            return null
        }
        return userRepository.save(user)
    }
}
