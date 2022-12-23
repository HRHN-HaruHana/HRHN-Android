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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    repository: ChallengeRepository
) : ViewModel() {
    private val _isEmpty = MutableLiveData<Boolean>(true)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _todayChallenge = MutableLiveData<Challenge>()
    val todayChallenge: LiveData<Challenge> get() = _todayChallenge

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    private val _addEvent = MutableLiveData<Event<Unit>>()
    val addEvent: LiveData<Event<Unit>> get() = _addEvent

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getChallengesWithPeriod(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().plusDays(1).atStartOfDay()
            ).onSuccess {
                if (it.isNotEmpty()) {
                    _isEmpty.postValue(false)
                    _todayChallenge.postValue(it[0])
                } else {
                    _isEmpty.postValue(true)
                }
            }.onFailure { t ->
                t.message?.let { _message.emit(it) }
            }
        }
    }

    fun addTodayChallenge() = _addEvent.emit()

}