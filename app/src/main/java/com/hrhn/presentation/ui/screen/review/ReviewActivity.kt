package com.hrhn.presentation.ui.screen.review

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrhn.databinding.ActivityReviewBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.screen.addchallenge.check.CheckEmojiAdapter
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewActivity : AppCompatActivity() {
    private val binding by lazy { ActivityReviewBinding.inflate(layoutInflater) }
    private val data: Challenge? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KEY, Challenge::class.java)
        } else {
            intent.getSerializableExtra(KEY) as Challenge
        }
    }

    @Inject
    lateinit var reviewViewModelFactory: ReviewViewModelFactory
    private val viewModel: ReviewViewModel by viewModels {
        ReviewViewModel.provideFactory(reviewViewModelFactory, data!!)
    }
    private val adapter by lazy { CheckEmojiAdapter { viewModel.checkedChanged(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initViews()
        observeData()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbReview)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.rvEmoji.adapter = adapter
    }

    private fun observeData() {
        viewModel.emojis.observe(this) { adapter.submitList(it) }
        viewModel.finishEvent.observeEvent(this) { finish() }
        viewModel.message.observeEvent(this) { showToast(it) }
    }

    companion object {
        private const val KEY = "challenge"

        fun newIntent(context: Context, challenge: Challenge) =
            Intent(context, ReviewActivity::class.java).apply {
                putExtra(KEY, challenge)
            }
    }
}