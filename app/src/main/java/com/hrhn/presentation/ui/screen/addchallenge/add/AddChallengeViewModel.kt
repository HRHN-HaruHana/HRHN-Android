package com.hrhn.presentation.ui.screen.addchallenge.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hrhn.data.repository.ChallengeRepositoryImpl
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit

class AddChallengeViewModel : ViewModel() {
    private val repository: ChallengeRepository = ChallengeRepositoryImpl()

    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val content = MutableLiveData<String>()
    val nextEnabled: LiveData<Boolean> = Transformations.map(content) {
        it.length in 10 until 100
    }

    fun saveNewChallenge() {
        content.value?.let {
            repository.addChallenge(Challenge(content = it))
                .onSuccess {
                    _navigateEvent.emit()
                }
                .onFailure { t ->
                    _message.emit(t.message ?: "저장 실패")
                }
        }
    }
}