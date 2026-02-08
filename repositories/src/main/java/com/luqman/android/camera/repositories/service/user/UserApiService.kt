package com.luqman.android.camera.repositories.service.user

import com.luqman.android.camera.repositories.service.user.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface UserApiService {
    suspend fun getUsers(): UserResponse
}

class UserApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : UserApiService {

    override suspend fun getUsers(): UserResponse = httpClient.get("/users").body()

}
