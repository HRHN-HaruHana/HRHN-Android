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

        if (savedInstanceState == null) {
            viewModel.needToUpdateLastChallengeEvent
                .observeEvent(this) { navigateToCheck ->
                    supportFragmentManager.commit {
                        if (navigateToCheck) {
                            add(R.id.fcv_add_challenge, CheckChallengeFragment())
                        } else {
                            add(R.id.fcv_add_challenge, AddChallengeFragment())
                        }
                    }
                }
        }
        observeData()
    }

    private fun observeData() {
        viewModel.message.observeEvent(this) {
            this.showToast(it)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, AddChallengeActivity::class.java)
    }
}