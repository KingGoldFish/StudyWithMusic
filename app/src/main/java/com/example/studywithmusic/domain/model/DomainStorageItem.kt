package com.example.studywithmusic.domain.model

data class DomainStorageItem(
    val name: String,       // 사용자 관점에서의 이름 (가공된 이름)
    val isFolder: Boolean   // true: 폴더, false: 파일
//    val path: String
)