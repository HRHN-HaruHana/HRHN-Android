package com.hrhn.presentation.ui.screen.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrhn.domain.model.StorageItem
import com.hrhn.domain.repository.StorageRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val repository: StorageRepository
) : ViewModel() {
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val storageItems = repository.getStorageItems()

    fun delete(item: StorageItem) {
        viewModelScope.launch {
            repository.deleteStorageItem(item)
                .onSuccess {
                    _message.emit("삭제되었습니다.")
                }.onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }
}