package com.example.studywithmusic.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studywithmusic.R
import com.example.studywithmusic.databinding.FragmentMainBinding
import com.example.studywithmusic.presentation.category.CategoryFragment


class MainFragment : Fragment() {

    //binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //화면전환 예시 (main -> categoryFragment)
        binding.btnTest1.setOnClickListener {
            val categoryFragment = CategoryFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, categoryFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}