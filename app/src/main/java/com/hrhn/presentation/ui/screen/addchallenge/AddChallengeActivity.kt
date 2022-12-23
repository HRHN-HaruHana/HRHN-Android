package com.hrhn.presentation.ui.screen.addchallenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.hrhn.R
import com.hrhn.databinding.ActivityAddChanllengeBinding
import com.hrhn.presentation.ui.screen.addchallenge.add.AddChallengeFragment

class AddChallengeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddChanllengeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // TODO last 아이템이 평가가 안되어있는지 체크
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_add_challenge, AddChallengeFragment())
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, AddChallengeActivity::class.java)
    }
}