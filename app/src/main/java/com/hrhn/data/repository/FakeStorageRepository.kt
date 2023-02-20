package com.hrhn.data.repository

import com.hrhn.domain.model.StorageItem
import com.hrhn.domain.repository.StorageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeStorageRepository @Inject constructor() : StorageRepository {
    private var storageItems = mutableListOf(
        StorageItem(0, "오전 러닝 12km"),
        StorageItem(1, "계란, 소금 사러가기"),
        StorageItem(2, "오늘의 다짐, 목표, 습관, 영어문장, 할일 혹은 무엇이든 좋아요"),
        StorageItem(3, "도서관 책 반납하기"),
        StorageItem(4, "운영체제 공부하기"),
        StorageItem(5, "홈화면 위젯 완성하기"),
        StorageItem(6, "챌린지 바구니 기본 화면 구성하기"),
        StorageItem(7, "대행사 11회 보기"),
        StorageItem(8, "안녕하세요 감사해요 잘있어요"),
    )

    override fun getStorageItems(): Flow<List<StorageItem>> {
        return flow {
            delay(300)
            emit(storageItems.toList())
        }
    }

    override suspend fun insertStorageItem(item: StorageItem): Result<Unit> {
        delay(300)
        return runCatching { storageItems.add(item) }
    }

    override suspend fun deleteStorageItem(item: StorageItem): Result<Unit> {
        delay(300)
        return runCatching { storageItems.remove(item) }
    }


}