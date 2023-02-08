package com.hrhn.presentation.ui.screen.main.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _isRefreshing = MutableSharedFlow<Boolean>()
    val isRefreshing = _isRefreshing.asSharedFlow()

    private val _today = MutableSharedFlow<LocalDateTime>()
    val today = _today.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = LocalDateTime.now()
    )

    val todayChallengeFlow: StateFlow<Challenge?> = _today.map { today ->
        repository.getChallengesWithPeriod(today, today.plusDays(1)).map {
            if (it.isNotEmpty()) it.getOrNull(0) else null
        }.first()
    }.catch { e ->
        e.message?.let { _message.emit(it) }
    }.onEach {
        _isRefreshing.emit(false)
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

    fun fetchData() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            _today.emit(LocalDate.now().atStartOfDay())
        }
    }

    fun addTodayChallenge() = _addEvent.emit()

    fun editTodayChallenge() {
        _editEvent.emit(todayChallengeFlow.value!!)
    }
}