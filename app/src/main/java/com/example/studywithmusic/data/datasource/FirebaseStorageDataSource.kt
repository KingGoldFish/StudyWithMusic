package com.example.studywithmusic.data.datasource

import com.example.studywithmusic.data.model.DataStorageItem
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.tasks.await

class FirebaseStorageDataSource(
    private val firebaseStorage: FirebaseStorage
) {

    // 폴더 목록 가져오기
    suspend fun getFolders(path: String): List<DataStorageItem> {
        val listRef = if (path.isEmpty()) firebaseStorage.reference else firebaseStorage.reference.child(path)
        val result: ListResult = listRef.listAll().await()

        return result.prefixes.map { folder ->
            DataStorageItem(id = folder.name, isDirectory = true)
        }
    }

    // 파일 목록 가져오기
    suspend fun getFiles(path: String): List<DataStorageItem> {
        val listRef = if (path.isEmpty()) firebaseStorage.reference else firebaseStorage.reference.child(path)
        val result: ListResult = listRef.listAll().await()

        return result.items.map { file ->
            DataStorageItem(id = file.name, isDirectory = false)
        }
    }

    // 다운로드 URL 가져오기
    suspend fun getDownloadUrl(path: String): String {
        return firebaseStorage.reference.child(path).downloadUrl.await().toString()
    }
}