package com.hrhn.presentation.ui.screen.addchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CheckChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _needToUpdateLastChallengeEvent = MutableLiveData<Event<Challenge?>>()
    val needToUpdateLastChallengeEvent: LiveData<Event<Challenge?>>
        get() = _needToUpdateLastChallengeEvent

    private val _finishEvent = MutableLiveData<Event<Unit>>()
    val finishEvent: LiveData<Event<Unit>> get() = _finishEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLastChallenge()
                .onSuccess {
                    if (it != null && it.date.toLocalDate() == LocalDate.now()) {
                        _finishEvent.emit()
                    } else if (it == null || it.emoji != null) {
                        _needToUpdateLastChallengeEvent.emit(null)
                    } else {
                        _needToUpdateLastChallengeEvent.emit(it)
                    }
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }
}