package com.zkrallah.z_students.di

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.data.repositories.LoginRepositoryImpl
import com.zkrallah.z_students.data.repositories.MainRepositoryImpl
import com.zkrallah.z_students.domain.repositories.LoginRepository
import com.zkrallah.z_students.domain.repositories.MainRepository
import com.zkrallah.zhttp.ZHttpClient
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
    fun provideMainRepository(
        dataStore: DataStore
    ): MainRepository {
        return MainRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        zHttpClient: ZHttpClient,
        dataStore: DataStore
    ): LoginRepository {
        return LoginRepositoryImpl(zHttpClient, dataStore)
    }
}