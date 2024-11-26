package com.example.studywithmusic.presentation.model

import com.example.studywithmusic.domain.model.DomainStorageItem

fun DomainStorageItem.toPresentation(currentPath: String): PresentationStorageItem {
    return PresentationStorageItem(
        displayName = this.name,
        isFolder = this.isFolder,
        fullPath = "$currentPath/${this.name}"
    )
}