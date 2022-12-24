package com.hrhn.presentation.ui.screen.main.past

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
class PastChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _challenges = MutableLiveData<List<Challenge>>(listOf())
    val challenges: LiveData<List<Challenge>>
        get() = _challenges

    val isEmpty: LiveData<Boolean> = Transformations.map(challenges) { it.isEmpty() }

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getChallenges()
                .onSuccess { list ->
                    _challenges.postValue(list.filter { it.emoji != null })
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }
}