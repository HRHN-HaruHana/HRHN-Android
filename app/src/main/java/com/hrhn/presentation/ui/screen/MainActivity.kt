package com.hrhn.presentation.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hrhn.R
import com.hrhn.databinding.ActivityMainBinding
import com.hrhn.presentation.ui.screen.main.past.PastChallengeFragment
import com.hrhn.presentation.ui.screen.main.today.TodayFragment
import com.hrhn.presentation.util.replace

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.replace(
                TodayFragment::class.java,
                R.id.fcv_main,
                TodayFragment.TAG
            )
        }
        initViews()
    }

    private fun initViews() {
        binding.navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_today -> {
                    supportFragmentManager.replace(
                        TodayFragment::class.java,
                        R.id.fcv_main,
                        TodayFragment.TAG
                    )
                }
                R.id.menu_past_challenge -> {
                    supportFragmentManager.replace(
                        PastChallengeFragment::class.java,
                        R.id.fcv_main,
                        PastChallengeFragment.TAG
                    )
                }
            }
            true
        }
    }
}