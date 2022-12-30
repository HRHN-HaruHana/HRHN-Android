package com.hrhn.presentation.ui.screen.edit

import androidx.lifecycle.*
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _finishEvent = MutableLiveData<Event<Unit>>()
    val finishEvent: LiveData<Event<Unit>> get() = _finishEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val content = MutableLiveData<String>()
    val nextEnabled: LiveData<Boolean> = Transformations.map(content) {
        it.length in 5 until 50
    }

    private lateinit var challenge: Challenge

    fun setData(challenge: Challenge) {
        this.challenge = challenge
        content.value = challenge.content
    }

    fun updateChallenge() {
        if (this::challenge.isInitialized) {
            val content = requireNotNull(content.value)
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateChallenge(challenge.copy(content = content))
                    .onSuccess {
                        _finishEvent.emit()
                    }
                    .onFailure { t ->
                        t.message?.let { _message.emit(it) }
                    }
            }
        }
    }
}