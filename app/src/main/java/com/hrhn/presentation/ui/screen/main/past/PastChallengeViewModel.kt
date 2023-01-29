package com.hrhn.presentation.ui.screen.main.past

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.filter
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PastChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _challenges = MutableLiveData<List<Challenge>>(listOf())
    val challenges: LiveData<List<Challenge>>
        get() = _challenges

    val isEmpty: LiveData<Boolean> = Transformations.map(challenges) { it.isEmpty() }

    val challengesFlow = repository.challengesFlow
        .map { it.filter { it.date.toLocalDate() != LocalDate.now() } }
        .cachedIn(viewModelScope)
        .catch { e -> e.message?.let { _message.emit(it) } }

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getChallenges()
                .onSuccess { list ->
                    _challenges.postValue(list.filter { it.date.toLocalDate() != LocalDate.now() })
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }
}