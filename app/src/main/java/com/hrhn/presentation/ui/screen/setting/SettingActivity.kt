package com.hrhn.presentation.ui.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fcv_setting, SettingFragment())
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbSetting)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }
}