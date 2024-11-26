package com.example.studywithmusic.data.repository

import com.example.studywithmusic.data.datasource.FirebaseStorageDataSource
import com.example.studywithmusic.data.model.toDomain
import com.example.studywithmusic.domain.model.DomainStorageItem
import com.example.studywithmusic.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseStorageDataSource
) : StorageRepository {

    override suspend fun getFolders(path: String): List<DomainStorageItem> {
        return dataSource.getFolders(path).map { it.toDomain() }
    }

    override suspend fun getFiles(path: String): List<DomainStorageItem> {
        return dataSource.getFiles(path).map { it.toDomain() }
    }

    override suspend fun getDownloadUrl(path: String): String {
        return dataSource.getDownloadUrl(path)
    }
}