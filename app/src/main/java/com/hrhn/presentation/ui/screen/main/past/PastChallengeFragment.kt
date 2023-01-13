package com.hrhn.presentation.ui.screen.main.past

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hrhn.databinding.FragmentPastChallengeBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.screen.main.past.adapter.PastChallengeAdapter
import com.hrhn.presentation.ui.screen.review.ReviewActivity
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastChallengeFragment : Fragment() {
    private var _binding: FragmentPastChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<PastChallengeViewModel>()
    private val adapter by lazy { PastChallengeAdapter { navigateToReview(it) } }

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
        initViews()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun initViews() {
        with(binding) {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            rvPastChallenge.adapter = adapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            challenges.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            message.observeEvent(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
        }
    }

    private fun navigateToReview(challenge: Challenge) {
        startActivity(ReviewActivity.newIntent(requireContext(), challenge))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}