package com.example.studywithmusic.domain.repository

import com.example.studywithmusic.domain.model.DomainStorageItem

interface StorageRepository {
    suspend fun getFolders(path: String): List<DomainStorageItem>
    suspend fun getFiles(path: String): List<DomainStorageItem>
    suspend fun getDownloadUrl(path: String): String
}