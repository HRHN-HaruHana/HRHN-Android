package com.hrhn.presentation.ui.screen.edit

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrhn.TodayChallengeWidgetProvider
import com.hrhn.databinding.ActivityEditChanllengeBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditChallengeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditChanllengeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<EditChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        initData()
        initToolbar()
        observeData()
    }

    private fun initData() {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KEY, Challenge::class.java)
        } else {
            intent.getSerializableExtra(KEY) as Challenge
        }
        if (data != null) {
            viewModel.setData(data)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbAddChallenge)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun observeData() {
        viewModel.message.observeEvent(this) {
            showToast(it)
        }
        viewModel.finishEvent.observeEvent(this) {
            TodayChallengeWidgetProvider.newIntent(this)
            finish()
        }
    }

    companion object {
        private const val KEY = "challenge"

        fun newIntent(context: Context, challenge: Challenge) =
            Intent(context, EditChallengeActivity::class.java).apply {
                putExtra(KEY, challenge)
            }
    }
}