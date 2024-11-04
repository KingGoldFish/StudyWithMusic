package com.example.studywithmusic.presentation.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studywithmusic.R
import com.example.studywithmusic.databinding.FragmentCategoryBinding
import com.example.studywithmusic.presentation.detailCategory.DetailCategoryFragment


class CategoryFragment : Fragment() {

    private var _binding : FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnTest.setOnClickListener {
            val detailCategoryFragment = DetailCategoryFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, detailCategoryFragment)
                .addToBackStack(null)
                .commit()
        }
    }


    private fun goBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}