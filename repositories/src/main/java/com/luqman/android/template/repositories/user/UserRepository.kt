package com.luqman.android.template.repositories.user

import androidx.datastore.core.DataStore
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.mvi.datastore.copy
import com.luqman.android.template.repositories.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {

    fun getUser(): Flow<User> {
        return dataStore.data.map {
            User(name = it.name, title = it.title)
        }
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