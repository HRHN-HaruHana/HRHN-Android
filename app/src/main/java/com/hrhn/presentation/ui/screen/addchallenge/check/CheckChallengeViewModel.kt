package com.hrhn.presentation.ui.screen.addchallenge.check

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.model.Emoji
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.ui.screen.addchallenge.CheckableEmoji
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _needToUpdateLastChallengeEvent = MutableLiveData<Event<Boolean>>()
    val needToUpdateLastChallengeEvent: LiveData<Event<Boolean>>
        get() = _needToUpdateLastChallengeEvent

    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _lastChallenge = MutableLiveData<Challenge>()
    val lastChallenge: LiveData<Challenge> get() = _lastChallenge

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    private val _selected = MutableLiveData<Emoji?>()
    val selected: LiveData<Emoji?> get() = _selected

    private val _emojis = MutableLiveData<List<CheckableEmoji>>(
        Emoji.values().map { CheckableEmoji(emoji = it) }
    )
    val emojis: LiveData<List<CheckableEmoji>> get() = _emojis

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLastChallenge()
                .onSuccess {
                    if (it == null || it.emoji != null) {
                        _needToUpdateLastChallengeEvent.emit(false)
                    } else {
                        _lastChallenge.postValue(it)
                        _needToUpdateLastChallengeEvent.emit(true)
                    }
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }

    fun checkedChanged(checkableEmoji: CheckableEmoji) {
        if (checkableEmoji.isChecked) {
            _emojis.value =
                _emojis.value?.map {
                    if (it.emoji.label == checkableEmoji.emoji.label) it.copy(isChecked = false)
                    else it
                }
            _selected.value = null
        } else {
            _emojis.value = _emojis.value?.map {
                if (it.emoji.label == checkableEmoji.emoji.label) it.copy(isChecked = true)
                else if (it.isChecked) it.copy(isChecked = false)
                else it
            }
            _selected.value = checkableEmoji.emoji
        }
    }

    fun saveLastChallengeEmoji() {
        viewModelScope.launch(Dispatchers.IO) {
            _lastChallenge.value?.copy(emoji = selected.value)?.let {
                repository.updateChallenge(it)
                    .onSuccess {
                        _navigateEvent.emit()
                    }.onFailure { t ->
                        t.message?.let { _message.emit(it) }
                    }
            }
        }
    }
}