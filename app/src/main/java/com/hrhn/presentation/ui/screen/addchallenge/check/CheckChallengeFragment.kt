package com.hrhn.presentation.ui.screen.addchallenge.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.FragmentCheckChallengeBinding
import com.hrhn.presentation.ui.screen.addchallenge.add.AddChallengeFragment
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckChallengeFragment : Fragment() {
    private var _binding: FragmentCheckChallengeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by activityViewModels<CheckChallengeViewModel>()
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
        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CheckChallengeFragment.viewModel
            rvEmoji.adapter = adapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            emojis.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            navigateEvent.observeEvent(viewLifecycleOwner) {
                parentFragmentManager.commit {
                    replace(R.id.fcv_add_challenge, AddChallengeFragment())
                    addToBackStack(null)
                }
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