package com.example.studywithmusic.domain.usecase

import com.example.studywithmusic.domain.model.DomainStorageItem
import com.example.studywithmusic.domain.repository.StorageRepository

class GetFilesUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(path: String): List<DomainStorageItem> {
        return repository.getFiles(path)
    }
}