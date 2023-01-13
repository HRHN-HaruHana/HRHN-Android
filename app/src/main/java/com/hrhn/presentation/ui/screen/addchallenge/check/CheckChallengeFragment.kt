package com.hrhn.presentation.ui.screen.addchallenge.check

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.hrhn.databinding.FragmentReviewBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.screen.review.ReviewViewModel
import com.hrhn.presentation.ui.screen.review.ReviewViewModelFactory
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckChallengeFragment : Fragment() {
    private var _binding: FragmentReviewBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val data by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY, Challenge::class.java)
        } else {
            requireArguments().getSerializable(KEY) as Challenge
        }
    }

    @Inject
    lateinit var reviewViewModelFactory: ReviewViewModelFactory
    private val viewModel: ReviewViewModel by viewModels {
        ReviewViewModel.provideFactory(reviewViewModelFactory, data!!)
    }
    private val adapter by lazy { CheckEmojiAdapter { viewModel.checkedChanged(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            rvEmoji.adapter = adapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            emojis.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            finishEvent.observeEvent(viewLifecycleOwner) {
                setFragmentResult(KEY_REQUEST_EMOJI, bundleOf(KEY_REQUEST_EMOJI to true))
            }
            message.observeEvent(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY = "challenge"
        const val KEY_REQUEST_EMOJI = "requestKey"

        fun newInstance(challenge: Challenge) = CheckChallengeFragment().apply {
            arguments = bundleOf(KEY to challenge)
        }
    }
}