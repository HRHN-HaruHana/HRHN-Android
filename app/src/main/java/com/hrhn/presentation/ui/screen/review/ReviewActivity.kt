package com.hrhn.presentation.ui.screen.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityReviewBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.util.customGetSerializableExtra
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewActivity : AppCompatActivity() {
    private val binding by lazy { ActivityReviewBinding.inflate(layoutInflater) }
    private val data: Challenge? by lazy { intent.customGetSerializableExtra(KEY) as Challenge? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(
                    R.id.fcv_review,
                    ReviewFragment.newInstance(
                        challenge = data!!,
                        hostActivityTag = R.string.tag_review_challenge
                    )
                )
            }
            supportFragmentManager.setFragmentResultListener(
                ReviewFragment.KEY_REQUEST_EMOJI, this
            ) { _, _ ->
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