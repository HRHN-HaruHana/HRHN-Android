package com.hrhn.presentation.ui.screen.addchallenge.check

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrhn.domain.model.Emoji
import com.hrhn.domain.repository.ChallengeRepository
import com.hrhn.presentation.ui.screen.addchallenge.CheckableEmoji
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {
    private val _selected = MutableLiveData<Emoji?>()
    val selected: LiveData<Emoji?> get() = _selected

    private val _emojis = MutableLiveData<List<CheckableEmoji>>(
        Emoji.values().map { CheckableEmoji(emoji = it) }
    )
    val emojis: LiveData<List<CheckableEmoji>> get() = _emojis

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
}