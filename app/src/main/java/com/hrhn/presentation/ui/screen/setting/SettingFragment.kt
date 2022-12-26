package com.hrhn.presentation.ui.screen.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.hrhn.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}