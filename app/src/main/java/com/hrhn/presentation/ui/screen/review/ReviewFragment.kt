package com.hrhn.presentation.ui.screen.review

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.hrhn.R
import com.hrhn.databinding.FragmentReviewBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.view.CheckEmojiAdapter
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewFragment : Fragment() {
    private var _binding: FragmentReviewBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val data by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY_CHALLENGE, Challenge::class.java)
        } else {
            requireArguments().getSerializable(KEY_CHALLENGE) as Challenge
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
        initHeader(requireArguments().getInt(KEY_HOST_ACTIVITY))
    }

    private fun initHeader(hostActivityTag: Int) {
        binding.tvTitleCheck.text = when (hostActivityTag) {
            R.string.tag_add_challenge -> getString(R.string.title_check_last_challenge)
            R.string.tag_review_challenge -> getString(R.string.title_check_challenge)
            else -> throw IllegalArgumentException()
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
        private const val KEY_CHALLENGE = "challenge"
        private const val KEY_HOST_ACTIVITY = "hostActivity"
        const val KEY_REQUEST_EMOJI = "requestKey"

        fun newInstance(challenge: Challenge, @StringRes hostActivityTag: Int) =
            ReviewFragment().apply {
                arguments = bundleOf(
                    KEY_CHALLENGE to challenge,
                    KEY_HOST_ACTIVITY to hostActivityTag
                )
            }
    }
}