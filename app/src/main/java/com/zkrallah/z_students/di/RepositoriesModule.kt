package com.zkrallah.z_students.di

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.data.repositories.AuthRepositoryImpl
import com.zkrallah.z_students.data.repositories.BrowseRepositoryImpl
import com.zkrallah.z_students.data.repositories.MainRepositoryImpl
import com.zkrallah.z_students.data.repositories.RequestRepositoryImpl
import com.zkrallah.z_students.data.repositories.UserRepositoryImpl
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.repositories.BrowseRepository
import com.zkrallah.z_students.domain.repositories.MainRepository
import com.zkrallah.z_students.domain.repositories.RequestsRepository
import com.zkrallah.z_students.domain.repositories.UserRepository
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
    fun provideAuthRepository(
        zHttpClient: ZHttpClient,
        dataStore: DataStore
    ): AuthRepository {
        return AuthRepositoryImpl(zHttpClient, dataStore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        zHttpClient: ZHttpClient,
        dataStore: DataStore
    ): UserRepository {
        return UserRepositoryImpl(zHttpClient, dataStore)
    }

    @Provides
    @Singleton
    fun provideBrowseRepository(
        zHttpClient: ZHttpClient,
        dataStore: DataStore
    ): BrowseRepository {
        return BrowseRepositoryImpl(zHttpClient, dataStore)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(
        zHttpClient: ZHttpClient,
        dataStore: DataStore
    ): RequestsRepository {
        return RequestRepositoryImpl(zHttpClient, dataStore)
    }
}