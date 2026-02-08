package com.luqman.android.camera.repositories.user

import androidx.datastore.core.DataStore
import com.luqman.android.camera.core.network.error.handleResult
import com.luqman.android.camera.datastore.UserPreferences
import com.luqman.android.camera.datastore.copy
import com.luqman.android.camera.repositories.service.user.UserApiService
import com.luqman.android.camera.repositories.service.user.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    suspend fun fetchUser(): Result<UserResponse>
    suspend fun saveUser(name: String, title: String)
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val dataStore: DataStore<UserPreferences>
) : UserRepository {

    override suspend fun fetchUser() = handleResult {
        return@handleResult userApiService.getUsers()
    }

    override suspend fun saveUser(name: String, title: String) {
        dataStore.updateData { userPreferences ->
            userPreferences.copy {
                this.title = title
                this.name = name
            }
        }
    }
}