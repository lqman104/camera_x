package com.luqman.android.template.repositories.user

import androidx.datastore.core.DataStore
import com.luqman.android.template.core.network.client.AppHttpClient
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.mvi.datastore.copy
import com.luqman.android.template.repositories.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val httpClient: AppHttpClient,
    private val dataStore: DataStore<UserPreferences>
) {

    suspend fun getUser(): Flow<User> {

        val users = httpClient.get<User>(url = "",)
        return htt
    }

    suspend fun saveUser(name: String, title: String) {
        dataStore.updateData { userPreferences ->
            userPreferences.copy {
                this.title = title
                this.name = name
            }
        }
    }

}