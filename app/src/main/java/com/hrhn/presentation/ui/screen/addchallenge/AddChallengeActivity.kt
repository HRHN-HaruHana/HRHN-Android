package com.hrhn.presentation.ui.screen.addchallenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityAddChanllengeBinding
import com.hrhn.presentation.ui.screen.addchallenge.add.AddChallengeFragment
import com.hrhn.presentation.ui.screen.addchallenge.check.CheckChallengeFragment
import com.hrhn.presentation.ui.screen.addchallenge.check.CheckChallengeViewModel
import com.hrhn.presentation.util.observeEvent
import com.hrhn.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddChallengeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddChanllengeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CheckChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initToolbar()
        if (savedInstanceState == null) {
            viewModel.needToUpdateLastChallengeEvent.observeEvent(this) { lastChallenge ->
                supportFragmentManager.commit {
                    if (lastChallenge != null) {
                        add(R.id.fcv_add_challenge, CheckChallengeFragment.newInstance(lastChallenge))
                    } else {
                        add(R.id.fcv_add_challenge, AddChallengeFragment())
                    }
                }
            }
            with(supportFragmentManager) {
                setFragmentResultListener(
                    CheckChallengeFragment.KEY_REQUEST_EMOJI,
                    this@AddChallengeActivity
                ) { _, _ ->
                    commit {
                        replace(R.id.fcv_add_challenge, AddChallengeFragment())
                    }
                }
            }
        }
        observeData()
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
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, AddChallengeActivity::class.java)
    }
}