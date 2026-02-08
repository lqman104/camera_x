package com.luqman.android.camera.setting.use_case

import com.luqman.android.camera.repositories.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        title: String
    ) {
        userRepository.saveUser(name = name, title = title)
    }
}