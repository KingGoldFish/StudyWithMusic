package com.example.studywithmusic.presentation.model

data class PresentationStorageItem(
    val displayName: String,    // 파일 이름
    val isFolder: Boolean,  //폴더 여부
    val fullPath: String    // 전체 경로
//    val icon: Int             // 아이콘
)

fun PresentationStorageItem.isMp3File(): Boolean {
    return displayName.endsWith(".mp3", ignoreCase = true)
}
