package com.hrhn.presentation.ui.screen.review

import androidx.lifecycle.*
import com.hrhn.domain.model.Challenge
import com.hrhn.domain.model.Emoji
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.ui.view.CheckableEmoji
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _finishEvent = MutableLiveData<Event<Unit>>()
    val finishEvent: LiveData<Event<Unit>> get() = _finishEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    private val _selected = MutableLiveData<Emoji?>()

    val challenge: Challenge get() = savedStateHandle[KEY_CHALLENGE] ?: Challenge()

    private val _emojis = MutableLiveData(
        Emoji.values().map { CheckableEmoji(emoji = it, isChecked = challenge.emoji == it) }
    )
    val emojis: LiveData<List<CheckableEmoji>> get() = _emojis

    fun checkedChanged(checkableEmoji: CheckableEmoji) {
        _selected.value = checkableEmoji.emoji
        saveChallengeEmoji()
    }

    private fun saveChallengeEmoji() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChallenge(challenge.copy(emoji = _selected.value))
                .onSuccess {
                    _finishEvent.emit()
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }

    companion object {
        private const val KEY_CHALLENGE = "challenge"
    }
}