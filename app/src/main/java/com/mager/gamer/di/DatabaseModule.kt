package com.mager.gamer.di

import android.app.Application
import com.mager.gamer.data.local.MagerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MagerDatabase {
        return MagerDatabase.instance(application)
    }
}