package com.hrhn.presentation.ui.screen.main.past

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PastChallengeViewModel @Inject constructor(
    repository: ChallengeRepository
) : ViewModel() {
    val challengesFlow: Flow<PagingData<Challenge>> = repository.challengesFlow
        .map { it.filter { it.date.toLocalDate() != LocalDate.now() } }
        .cachedIn(viewModelScope)
        .catch { e -> e.message?.let { _message.emit(it) } }

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

}