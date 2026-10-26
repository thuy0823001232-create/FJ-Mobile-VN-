package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.SocialVideoPostEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfileFlow(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET pinPassword = :newPin WHERE id = 1")
    suspend fun updatePin(newPin: String)

    @Query("UPDATE user_profile SET isOnboardingCompleted = 1 WHERE id = 1")
    suspend fun completeOnboarding()
}

@Dao
interface SocialVideoPostDao {
    @Query("SELECT * FROM social_video_posts ORDER BY createdAt DESC")
    fun getAllFlow(): Flow<List<SocialVideoPostEntity>>

    @Query("SELECT * FROM social_video_posts WHERE id = :id")
    suspend fun getById(id: Long): SocialVideoPostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: SocialVideoPostEntity): Long

    @Update
    suspend fun update(post: SocialVideoPostEntity)

    @Delete
    suspend fun delete(post: SocialVideoPostEntity)
}
