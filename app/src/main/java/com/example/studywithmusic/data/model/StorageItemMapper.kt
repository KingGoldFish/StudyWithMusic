package com.example.studywithmusic.data.model

import com.example.studywithmusic.domain.model.DomainStorageItem

fun DataStorageItem.toDomain(): DomainStorageItem { //data -> domain
    return DomainStorageItem(
        name = id,
        isFolder = isDirectory
    )
}