package com.hrhn.presentation.ui.screen.addchallenge.check

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrhn.presentation.ui.screen.addchallenge.Emoji
import com.hrhn.presentation.ui.screen.addchallenge.getEmojis

class CheckChallengeViewModel : ViewModel() {
    private val _selected = MutableLiveData<String?>()
    val selected: LiveData<String?> get() = _selected

    private val _emojis = MutableLiveData<List<Emoji>>(getEmojis())
    val emojis: LiveData<List<Emoji>> get() = _emojis

    fun checkedChanged(emoji: Emoji) {
        if (emoji.isChecked) {
            _emojis.value =
                _emojis.value?.map {
                    if (it.label == emoji.label) it.copy(isChecked = false)
                    else it
                }
            _selected.value = null
        } else {
            _emojis.value = _emojis.value?.map {
                if (it.label == emoji.label) it.copy(isChecked = true)
                else if (it.isChecked) it.copy(isChecked = false)
                else it
            }
            _selected.value = emoji.label
        }
    }
}