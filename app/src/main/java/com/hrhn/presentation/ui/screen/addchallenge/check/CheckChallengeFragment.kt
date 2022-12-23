package com.hrhn.presentation.ui.screen.addchallenge.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrhn.databinding.FragmentCheckChallengeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckChallengeFragment : Fragment() {
    private var _binding: FragmentCheckChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<CheckChallengeViewModel>()
    private val adapter by lazy { CheckEmojiAdapter { viewModel.checkedChanged(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckChallengeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CheckChallengeFragment.viewModel
            rvEmoji.adapter = adapter
        }

        viewModel.emojis.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}