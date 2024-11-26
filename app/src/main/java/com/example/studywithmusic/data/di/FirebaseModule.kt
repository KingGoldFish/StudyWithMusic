package com.example.studywithmusic.data.di

import com.example.studywithmusic.data.datasource.FirebaseStorageDataSource
import com.example.studywithmusic.data.repository.StorageRepositoryImpl
import com.example.studywithmusic.domain.repository.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageDataSource(
        firebaseStorage: FirebaseStorage
    ): FirebaseStorageDataSource {
        return FirebaseStorageDataSource(firebaseStorage)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(
        dataSource: FirebaseStorageDataSource
    ): StorageRepository {
        return StorageRepositoryImpl(dataSource)
    }
}