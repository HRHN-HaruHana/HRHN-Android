package com.hrhn.presentation.ui.screen.main.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.usecase.GetTodayChallengeUseCase
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    getTodayChallengeUseCase: GetTodayChallengeUseCase
) : ViewModel() {
    val todayChallengeFlow: StateFlow<Challenge?> = getTodayChallengeUseCase()
        .catch { e ->
            e.message?.let { _message.emit(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = null
        )

    val isEmpty: StateFlow<Boolean> = todayChallengeFlow.map { it == null }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = true
    )

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    private val _addEvent = MutableLiveData<Event<Unit>>()
    val addEvent: LiveData<Event<Unit>> get() = _addEvent

    private val _editEvent = MutableLiveData<Event<Challenge>>()
    val editEvent: LiveData<Event<Challenge>> get() = _editEvent

    fun addTodayChallenge() = _addEvent.emit()

    fun editTodayChallenge() {
        _editEvent.emit(todayChallengeFlow.value!!)
    }
}