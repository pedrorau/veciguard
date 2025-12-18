package com.pedrorau.veciguard.di

import com.pedrorau.veciguard.data.source.MockUserDataSource
import com.pedrorau.veciguard.data.source.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDataSource {
        return MockUserDataSource()
    }
}