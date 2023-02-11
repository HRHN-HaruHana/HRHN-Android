package com.hrhn.presentation.ui.screen.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityEditChanllengeBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.ui.screen.addchallenge.add.AddChallengeFragment
import com.hrhn.presentation.util.customGetSerializableExtra
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditChallengeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditChanllengeBinding.inflate(layoutInflater) }
    private val data: Challenge? by lazy { intent.customGetSerializableExtra(KEY) as Challenge? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_edit_challenge, AddChallengeFragment.newInstance(data!!))
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbAddChallenge)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
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