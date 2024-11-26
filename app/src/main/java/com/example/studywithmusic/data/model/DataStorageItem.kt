package com.example.studywithmusic.data.model

data class DataStorageItem(
    val id: String,          // Firebase에서 가져온 파일/폴더 이름
    val isDirectory: Boolean // true: 폴더, false: 파일
)