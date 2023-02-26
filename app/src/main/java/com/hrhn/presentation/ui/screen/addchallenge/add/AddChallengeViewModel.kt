package com.hrhn.presentation.ui.screen.addchallenge.add

import androidx.lifecycle.*
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChallengeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val challenge: Challenge get() = savedStateHandle[KEY_CHALLENGE] ?: Challenge()

    val input = MutableLiveData<String>(challenge.content)
    val nextEnabled: LiveData<Boolean> = Transformations.map(input) {
        it.length in (2..50)
    }

    fun saveNewChallenge() {
        val content = requireNotNull(input.value)
        viewModelScope.launch {
            repository.insertChallenge(challenge.copy(content = content))
                .onSuccess {
                    _navigateEvent.emit()
                }
                .onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }

    companion object {
        private const val KEY_CHALLENGE = "challenge"
    }
}