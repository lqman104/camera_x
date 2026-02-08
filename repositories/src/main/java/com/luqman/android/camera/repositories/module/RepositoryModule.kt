package com.luqman.android.camera.repositories.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.luqman.android.camera.datastore.UserPreferences
import com.luqman.android.camera.repositories.serializer.UserPreferenceSerializer
import com.luqman.android.camera.repositories.service.user.UserApiService
import com.luqman.android.camera.repositories.service.user.UserApiServiceImpl
import com.luqman.android.camera.repositories.user.UserRepository
import com.luqman.android.camera.repositories.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataStore: DataStore<UserPreferences>,
        userApiService: UserApiService
    ): UserRepository {
        return UserRepositoryImpl(
            dataStore = userDataStore,
            userApiService = userApiService
        )
    }

    @Provides
    @Singleton
    fun provideUserApiService(
        httpClient: HttpClient
    ): UserApiService {
        return UserApiServiceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceSerializer(): UserPreferenceSerializer {
        return UserPreferenceSerializer()
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
        userPreferenceSerializer: UserPreferenceSerializer
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = userPreferenceSerializer,
            produceFile = {
                context.dataStoreFile("user_preferences.pb")
            }
        )
    }

}