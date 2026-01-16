package com.luqman.android.template.repositories.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.repositories.serializer.UserPreferenceSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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