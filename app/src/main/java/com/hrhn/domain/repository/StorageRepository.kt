package com.hrhn.domain.repository

import com.hrhn.domain.model.StorageItem
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun getStorageItems(): Flow<List<StorageItem>>
    suspend fun insertStorageItem(item: StorageItem): Result<Unit>
    suspend fun deleteStorageItem(item: StorageItem): Result<Unit>
}