package com.hrhn.presentation.ui.screen.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private val binding by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_onboarding, StartFragment())
            }
        }
    }
}