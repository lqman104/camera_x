package com.luqman.android.camera.dashboard.use_case

import com.luqman.android.camera.repositories.models.User
import com.luqman.android.camera.repositories.service.user.response.UserDto
import com.luqman.android.camera.repositories.user.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): List<User> {
        val result = userRepository.fetchUser()
        return if (result.isSuccess) {
            result.getOrNull()?.data?.map { it.toUser() }.orEmpty()
        } else {
            listOf()
        }
    }

}

private fun UserDto.toUser(): User {
    return User(
        name = name,
        email = email
    )
}
