package com.hrhn.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrhn.data.entity.ChallengeEntity
import com.hrhn.data.entity.DateConverters
import com.hrhn.data.local.dao.ChallengeDao

@Database(entities = [ChallengeEntity::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class HRHNDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
}