package com.example.studywithmusic.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studywithmusic.domain.repository.StorageRepository
import com.example.studywithmusic.presentation.model.PresentationStorageItem
import com.example.studywithmusic.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: StorageRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<PresentationStorageItem>>(emptyList())
    val items: StateFlow<List<PresentationStorageItem>> = _items.asStateFlow()

    private val _currentPath = MutableStateFlow("")
    val currentPath: StateFlow<String> = _currentPath.asStateFlow()

    private val pathStack = mutableListOf<String>()

    fun updatePath(selectedPath: String) {
        viewModelScope.launch {
            try {
                if (selectedPath.isNotEmpty()) {
                    pathStack.add(selectedPath) // 선택된 경로를 스택에 추가
                }
                val fullPath = pathStack.joinToString("/") // 스택을 기반으로 전체 경로 생성
                _currentPath.value = fullPath // UI에 표시할 현재 경로 설정

                // 폴더 및 파일 데이터 로드
                val folders = repository.getFolders(fullPath)
                val files = repository.getFiles(fullPath)

                // PresentationStorageItem으로 변환 후 데이터 업데이트
                _items.value = (folders + files).map { it.toPresentation(fullPath) }
            } catch (e: Exception) {
                Log.e("SharedViewModel", "경로 업데이트 중 오류 발생: $selectedPath", e)
            }
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            if (pathStack.isNotEmpty()) {
                pathStack.removeLast() // 스택에서 마지막 경로 제거
                val fullPath = pathStack.joinToString("/") // 전체 경로 재계산
                _currentPath.value = fullPath // UI에 표시할 경로 업데이트

                // 경로에 따라 데이터 다시 로드
                val folders = repository.getFolders(fullPath)
                val files = repository.getFiles(fullPath)
                _items.value = (folders + files).map { it.toPresentation(fullPath) }
            }
        }
    }
}