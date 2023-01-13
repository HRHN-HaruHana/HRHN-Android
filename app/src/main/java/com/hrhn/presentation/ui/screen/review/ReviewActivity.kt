package com.hrhn.presentation.ui.screen.review

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityReviewBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.screen.addchallenge.check.ReviewFragment
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_review, ReviewFragment.newInstance(data!!))
            }
            supportFragmentManager.setFragmentResultListener(ReviewFragment.KEY_REQUEST_EMOJI, this) { _, _ ->
                finish()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbReview)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    companion object {
        private const val KEY = "challenge"

        fun newIntent(context: Context, challenge: Challenge) =
            Intent(context, ReviewActivity::class.java).apply {
                putExtra(KEY, challenge)
            }
    }
}