package com.example.studywithmusic.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studywithmusic.databinding.FragmentMainBinding
import com.example.studywithmusic.presentation.SharedViewModel
import com.example.studywithmusic.presentation.model.PresentationStorageItem
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    private val adapter by lazy {
        MainAdapter(this) // 인터페이스 구현체 전달
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        setupRecyclerView()

        // ViewModel 관찰
        observeViewModel()

        // 초기 경로 설정
        viewModel.updatePath("")
    }

    private fun setupRecyclerView() {
        binding.rvFiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFiles.adapter = adapter
    }

    private fun observeViewModel() {
        // 현재 경로 업데이트
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentPath.collect { path ->
                updatePathUI(path)
            }
        }

        // 파일 및 폴더 목록 업데이트
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { items ->
                adapter.submitList(items)
            }
        }
    }

    private fun updatePathUI(path: String) {
        binding.tvCurrentPath.text = if (path.isEmpty()) "Root" else path
        binding.btnBack.isVisible = path.isNotEmpty() // 최상위 경로에서는 숨김
    }

    override fun onFolderClick(item: PresentationStorageItem) {
        viewModel.updatePath(item.displayName) // 폴더 탐색
    }

    override fun onFileClick(item: PresentationStorageItem) {
        if (item.displayName.endsWith(".mp3", ignoreCase = true)) {
            getFileUrl(item.fullPath) { fileUrl ->
                val action = MainFragmentDirections.actionMainFragmentToPlayingFragment(
                    fileTitle = item.displayName, // 파일 이름 전달
                    filePath = fileUrl // 파일 경로 전달
                )
                findNavController().navigate(action)
//                navigateToPlayingFragment(item.displayName, fileUrl)
            }
        } else {
            showToast("지원하지 않는 파일 형식입니다.")
        }
    }

    private fun getFileUrl(fullPath: String, onUrlReceived: (String) -> Unit) {
        val storageRef = Firebase.storage.reference.child(fullPath)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            onUrlReceived(uri.toString())
        }.addOnFailureListener { e ->
            Log.e("MainFragment", "파일 URL 가져오기 실패: $fullPath", e)
            showToast("파일을 가져올 수 없습니다.")
        }
    }

    private fun navigateToPlayingFragment(fileTitle: String, filePath: String) {
        val action = MainFragmentDirections.actionMainFragmentToPlayingFragment(
            fileTitle = fileTitle,
            filePath = filePath
        )
        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}