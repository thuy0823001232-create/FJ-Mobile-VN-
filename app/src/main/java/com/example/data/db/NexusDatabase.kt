package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.SocialVideoPostEntity
import com.example.data.model.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        SocialVideoPostEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun socialVideoPostDao(): SocialVideoPostDao

    companion object {
        @Volatile
        private var INSTANCE: NexusDatabase? = null

        fun getInstance(context: Context): NexusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NexusDatabase::class.java,
                    "fj_mobile_vn.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
