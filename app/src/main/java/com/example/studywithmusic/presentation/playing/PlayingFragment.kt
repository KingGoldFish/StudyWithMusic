package com.example.studywithmusic.presentation.playing

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.studywithmusic.R
import com.example.studywithmusic.databinding.FragmentPlayingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingFragment : Fragment(R.layout.fragment_playing) {

    private var _binding: FragmentPlayingBinding? = null
    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null
    private var isPlayingS = false // 음악 재생 상태 관리

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playMp3File()
        stopAndPlay()
    }

    private fun stopAndPlay() {
        binding.playPauseButton.setOnClickListener {
            togglePlayPause()
        }
    }

    private fun playMp3File() {
        // 전달된 파일 경로 및 제목 가져오기
        val fileTitle = arguments?.getString("fileTitle") ?: "Unknown Title"
        val filePath = arguments?.getString("filePath") ?: ""

        // 파일 제목 설정
        binding.songTitle.text = fileTitle

        if (filePath.isNotEmpty()) {
            // 음악 재생 초기화 및 시작
            initializeAndPlay(filePath)
        } else {
            showToast("파일 경로를 가져올 수 없습니다.")
        }
    }

    private fun initializeAndPlay(filePath: String) {
        val fileUri = Uri.parse(filePath)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(requireContext(), fileUri)
            prepare()
            start()
            isPlayingS = true
            binding.playPauseButton.setImageResource(R.drawable.icon_pause) // 재생 아이콘 설정

            setOnCompletionListener {
                // 음악이 끝났을 때 재생 상태 초기화
                isPlayingS = false
                binding.playPauseButton.setImageResource(R.drawable.icon_play)
                showToast("음악 재생이 완료되었습니다.")
            }
        }
    }

    /**
     * 재생/멈춤 버튼 동작 처리
     */
    private fun togglePlayPause() {
        if (isPlayingS) {
            mediaPlayer?.pause()
            isPlayingS = false
            binding.playPauseButton.setImageResource(R.drawable.icon_play)
        } else {
            mediaPlayer?.start()
            isPlayingS = true
            binding.playPauseButton.setImageResource(R.drawable.icon_pause)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}