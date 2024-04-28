package com.zkrallah.z_students.di

import android.content.Context
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.data.dataStore.DataStoreImpl
import com.zkrallah.zhttp.ZHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideZHttpClient(): ZHttpClient {
        return ZHttpClient.Builder()
            .baseUrl("http://192.168.1.4:8080")
            .connectionTimeout(20000)
            .readTimeout(20000)
            .build()
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext appContext: Context
    ): DataStore {
        return DataStoreImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext appContext: Context
    ): Context {
        return appContext
    }
}