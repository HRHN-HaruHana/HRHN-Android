package com.hrhn.presentation.ui.screen.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hrhn.databinding.FragmentStorageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {

    }

    private fun observeData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}