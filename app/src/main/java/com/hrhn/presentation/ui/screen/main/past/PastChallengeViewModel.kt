package com.hrhn.presentation.ui.screen.main.past

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PastChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _challenges = MutableLiveData(repository.getPastChallenges())
    val challenges: LiveData<List<Challenge>>
        get() = _challenges
}