package com.hrhn.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hrhn.data.entity.ChallengeEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challengeEntity: ChallengeEntity)

    @Query("SELECT * FROM challenge ORDER BY date DESC LIMIT 1")
    suspend fun getLastChallenge(): ChallengeEntity?

    @Query("SELECT * FROM challenge ORDER BY date DESC")
    fun getChallengesFlow(): PagingSource<Int, ChallengeEntity>

    @Query("SELECT * FROM challenge WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    fun getChallengesWithPeriod(from: LocalDateTime, to: LocalDateTime): Flow<List<ChallengeEntity>>

    @Update
    fun updateChallenge(challengeEntity: ChallengeEntity)

    @Query("DELETE FROM challenge WHERE id=:id")
    fun deleteChallenge(id: Long)
}
