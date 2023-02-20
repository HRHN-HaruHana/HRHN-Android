package com.hrhn.presentation.ui.screen.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hrhn.databinding.FragmentStorageBinding
import com.hrhn.domain.model.StorageItem
import com.hrhn.presentation.ui.screen.storage.add.AddStorageActivity
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<StorageViewModel>()
    private val adapter by lazy {
        StorageAdapter(
            onClick = { navigateToAddEditStorage(it) },
            onDelete = { viewModel.delete(it) })
    }

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
        binding.fabAddStorage.setOnClickListener { navigateToAddEditStorage() }
        binding.rvStorages.adapter = adapter
    }

    private fun observeData() {
        viewModel.message.observeEvent(this) {
            requireContext().showToast(it)
        }
        viewModel.storageItems
            .onEach {
                adapter.submitList(it)
                binding.tvEmptyStorage.isVisible = it.isEmpty()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToAddEditStorage(item: StorageItem? = null) {
        requireContext().startActivity(AddStorageActivity.newIntent(requireContext(), item))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}