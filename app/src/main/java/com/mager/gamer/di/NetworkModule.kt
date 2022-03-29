package com.mager.gamer.di

import android.app.Application
import com.mager.gamer.data.remote.ApiClient
import com.mager.gamer.data.remote.ApiService
import com.mager.gamer.data.remote.NetworkStateManager
import com.mager.gamer.data.remote.NetworkStateManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.instance
    }

    @Provides
    @Singleton
    fun provideNetworkStateManager(application: Application): NetworkStateManager {
        return NetworkStateManagerImpl(application)
    }
}