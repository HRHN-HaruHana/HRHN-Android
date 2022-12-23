package com.hrhn.presentation.ui.screen.main.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hrhn.databinding.FragmentTodayBinding
import com.hrhn.presentation.ui.screen.addchallenge.AddChallengeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.today = LocalDateTime.now()
        binding.btnAddChallenge.setOnClickListener {
            startActivity(AddChallengeActivity.newIntent(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}