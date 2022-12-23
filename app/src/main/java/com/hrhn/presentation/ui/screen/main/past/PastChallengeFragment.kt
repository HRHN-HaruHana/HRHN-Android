package com.hrhn.presentation.ui.screen.main.past

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrhn.databinding.FragmentPastChallengeBinding
import com.hrhn.presentation.ui.adapter.PastChallengeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastChallengeFragment : Fragment() {
    private var _binding: FragmentPastChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<PastChallengeViewModel>()
    private val adapter by lazy { PastChallengeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastChallengeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPastChallenge.adapter = adapter
        viewModel.challenges.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}