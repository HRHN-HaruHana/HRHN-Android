package com.hrhn.presentation.ui.screen.storage.add

import androidx.lifecycle.*
import com.hrhn.domain.model.StorageItem
import com.hrhn.domain.repository.StorageRepository
import com.hrhn.presentation.util.Event
import com.hrhn.presentation.util.emit
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AddStorageViewModel @AssistedInject constructor(
    private val repository: StorageRepository,
    @Assisted val storageItem: StorageItem
) : ViewModel() {

    private val _navigateEvent = MutableLiveData<Event<Unit>>()
    val navigateEvent: LiveData<Event<Unit>> get() = _navigateEvent

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    val input = MutableLiveData<String>(storageItem.content)
    val nextEnabled: LiveData<Boolean> = Transformations.map(input) {
        it.length in (2..50)
    }

    fun saveStorageItem() {
        val content = requireNotNull(input.value)
        viewModelScope.launch {
            repository.insertStorageItem(storageItem.copy(content = content))
                .onSuccess {
                    _navigateEvent.emit()
                }
                .onFailure { t ->
                    t.message?.let { _message.emit(it) }
                }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: AddEditStorageViewModelFactory,
            storageItem: StorageItem
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(storageItem) as T
            }
        }
    }
}

@AssistedFactory
interface AddEditStorageViewModelFactory {
    fun create(item: StorageItem): AddStorageViewModel
}