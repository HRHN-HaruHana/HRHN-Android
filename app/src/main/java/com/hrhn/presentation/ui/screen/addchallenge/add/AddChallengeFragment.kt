package com.hrhn.presentation.ui.screen.addchallenge.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hrhn.databinding.FragmentAddChallengeBinding
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddChallengeFragment : Fragment() {
    private var _binding: FragmentAddChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<AddChallengeViewModel>()

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
        observeData()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            etNewChallenge.requestFocus()
        }
    }

    private fun observeData() {
        with(viewModel) {
            navigateEvent.observeEvent(viewLifecycleOwner) {
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
}