package com.hrhn.presentation.ui.screen.addchallenge.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrhn.R
import com.hrhn.databinding.FragmentAddChallengeBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import com.hrhn.presentation.widget.TodayChallengeWidgetProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddChallengeFragment : Fragment() {
    private var _binding: FragmentAddChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: AddChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddChallengeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initHeader()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            etNewChallenge.root.requestFocus()
        }
    }

    private fun initHeader() {
        binding.tvTitle.text = if (requireArguments().getBoolean(KEY_IS_EDIT)) {
            getString(R.string.message_edit_challenge)
        } else {
            getString(R.string.message_new_challenge)
        }
    }

    private fun observeData() {
        with(viewModel) {
            navigateEvent.observeEvent(viewLifecycleOwner) {
                TodayChallengeWidgetProvider.newIntent(requireContext())
                requireActivity().finish()
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
        private const val KEY_IS_EDIT = "isEdit"

        fun newInstance(challenge: Challenge = Challenge()) =
            AddChallengeFragment().apply {
                arguments = bundleOf(
                    KEY_CHALLENGE to challenge,
                    KEY_IS_EDIT to (challenge.id > 0)
                )
            }
    }
}