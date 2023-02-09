package com.hrhn.presentation.ui.screen.addchallenge.add

import androidx.lifecycle.*
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddChallengeViewModel @AssistedInject constructor(
    private val repository: ChallengeRepository,
    @Assisted val challenge: Challenge
) : ViewModel() {
    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val input = MutableLiveData<String>(challenge.content)
    val nextEnabled: LiveData<Boolean> = Transformations.map(input) {
        it.length in (2..50)
    }

    fun saveNewChallenge() {
        val content = requireNotNull(input.value)
        viewModelScope.launch(Dispatchers.IO) {
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
        fun provideFactory(
            assistedFactory: AddEditChallengeViewModelFactory,
            challenge: Challenge
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(challenge) as T
            }
        }
    }
}

@AssistedFactory
interface AddEditChallengeViewModelFactory {
    fun create(challenge: Challenge): AddChallengeViewModel
}