package com.luqman.android.template.repositories.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.repositories.serializer.UserPreferenceSerializer
import com.luqman.android.template.repositories.user.userPreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPreferences> {
        return context.userPreferenceDataStore
    }

}