package com.hrhn.presentation.ui.screen.addchallenge.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val input = MutableLiveData<String>()
    val nextEnabled: LiveData<Boolean> = Transformations.map(input) {
        it.length in 10 until 100
    }

    fun saveNewChallenge() {
        val content = requireNotNull(input.value)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertChallenge(Challenge(content = content))
                .onSuccess {
                    _navigateEvent.emit()
                }
                .onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }
}