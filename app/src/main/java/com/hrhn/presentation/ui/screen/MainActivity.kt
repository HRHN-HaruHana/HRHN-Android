package com.hrhn.presentation.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityMainBinding
import com.hrhn.presentation.ui.screen.main.past.PastChallengeFragment
import com.hrhn.presentation.ui.screen.main.today.TodayFragment
import com.hrhn.presentation.ui.screen.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val todayFragment by lazy { TodayFragment() }
    private val pastChallengeFragment by lazy { PastChallengeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit { add(R.id.fcv_main, todayFragment) }
        }
        initViews()
    }

    private fun initViews() {
        binding.navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_today -> {
                    supportFragmentManager.commit {
                        replace(R.id.fcv_main, todayFragment)
                    }
                }
                R.id.menu_past_challenge -> {
                    supportFragmentManager.commit {
                        replace(R.id.fcv_main, pastChallengeFragment)
                    }
                }
            }
            true
        }
        binding.mtMain.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_setting) {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            true
        }
    }
}