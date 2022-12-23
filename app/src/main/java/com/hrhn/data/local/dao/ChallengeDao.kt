package com.hrhn.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hrhn.data.entity.ChallengeEntity

@Dao
interface ChallengeDao {
    @Insert
    fun insertChallenge(challengeEntity: ChallengeEntity)

    @Query("SELECT * FROM challenge ORDER BY date DESC")
    fun getChallenges(): List<ChallengeEntity>

    @Update
    fun updateChallenge(challengeEntity: ChallengeEntity)

    @Query("DELETE FROM challenge WHERE id=:id")
    fun deleteChallenge(id: Long)
}
