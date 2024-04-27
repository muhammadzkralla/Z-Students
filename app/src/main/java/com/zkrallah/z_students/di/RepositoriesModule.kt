package com.zkrallah.z_students.di

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.data.repositories.MainRepositoryImpl
import com.zkrallah.z_students.domain.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideHomeRepository(
        dataStore: DataStore
    ): MainRepository {
        return MainRepositoryImpl(dataStore)
    }
}