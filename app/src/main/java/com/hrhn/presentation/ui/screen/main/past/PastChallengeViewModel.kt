package com.hrhn.presentation.ui.screen.main.past

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrhn.data.repository.ChallengeRepositoryImpl
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository

class PastChallengeViewModel : ViewModel() {
    private val repository: ChallengeRepository = ChallengeRepositoryImpl()
    private val _challenges = MutableLiveData(repository.getPastChallenges())
    val challenges: LiveData<List<Challenge>>
        get() = _challenges
}