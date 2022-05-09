package com.mager.gamer.di

import android.app.Application
import com.mager.gamer.data.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ApiService {
        return ApiClient(okHttpClient).instance()
    }

    @Provides
    @Singleton
    fun provideNetworkStateManager(application: Application): NetworkStateManager {
        return NetworkStateManagerImpl(application)
    }
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = okHttpClient(authInterceptor)
}