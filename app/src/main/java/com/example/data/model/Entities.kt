package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Long = 1,
    val email: String = "thuy0823001232@gmail.com",
    val avatarEmoji: String = "🚀",
    val introBio: String = "FJ Mobile VN Creator & Power User",
    val pinPassword: String = "1234",
    val isOnboardingCompleted: Boolean = false,
    val isRobotVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "social_video_posts")
data class SocialVideoPostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val filePath: String,
    val uploadYouTube: Boolean = true,
    val uploadTikTok: Boolean = true,
    val uploadFacebook: Boolean = true,
    val status: String = "QUEUED", // QUEUED, TRANSCODING, UPLOADING, PUBLISHED
    val progress: Float = 0f,
    val publishedLinks: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class DeviceTelemetry(
    val cpuTempCelsius: Float = 44.5f,
    val cpuUsagePercent: Int = 62,
    val ramUsedMb: Int = 4320,
    val ramTotalMb: Int = 8192,
    val screenSmoothHz: Int = 120,
    val isCooling: Boolean = false,
    val coolingHistoryCount: Int = 3
)

data class BluetoothMic(
    val id: String,
    val name: String,
    val type: String,
    val isConnected: Boolean,
    val battery: Int,
    val signalStrength: Int,
    val gain: Float = 0.8f
)

data class GoogleMailState(
    val email: String = "thuy0823001232@gmail.com",
    val isConnected: Boolean = true,
    val unreadCount: Int = 5,
    val lastSyncTime: String = "Just now",
    val autoNotifyPublishedVideos: Boolean = true
)
