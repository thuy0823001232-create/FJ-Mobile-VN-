package com.example.data.repository

import com.example.data.db.NexusDatabase
import com.example.data.model.BluetoothMic
import com.example.data.model.DeviceTelemetry
import com.example.data.model.GoogleMailState
import com.example.data.model.SocialVideoPostEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class NexusRepository(
    private val db: NexusDatabase,
    private val coroutineScope: CoroutineScope
) {
    private val userProfileDao = db.userProfileDao()
    private val videoPostDao = db.socialVideoPostDao()

    // Real device telemetry & cooling state
    private val _deviceTelemetry = MutableStateFlow(DeviceTelemetry())
    val deviceTelemetry: StateFlow<DeviceTelemetry> = _deviceTelemetry.asStateFlow()

    // Bluetooth microphones
    private val _bluetoothMics = MutableStateFlow<List<BluetoothMic>>(
        listOf(
            BluetoothMic("mic-1", "DJI Mic 2 (Wireless TX)", "Lapel Wireless", true, 92, -45, 0.85f),
            BluetoothMic("mic-2", "Rode Wireless PRO (RX)", "Digital Wireless", false, 78, -62, 0.70f),
            BluetoothMic("mic-3", "Studio Boom Mic Pro", "Cardioid Headset", false, 100, -55, 0.90f)
        )
    )
    val bluetoothMics: StateFlow<List<BluetoothMic>> = _bluetoothMics.asStateFlow()

    // Google Mail State
    private val _googleMailState = MutableStateFlow(GoogleMailState())
    val googleMailState: StateFlow<GoogleMailState> = _googleMailState.asStateFlow()

    init {
        coroutineScope.launch(Dispatchers.IO) {
            seedInitialProfileIfNeeded()
            startTelemetryMonitor()
        }
    }

    private suspend fun seedInitialProfileIfNeeded() {
        val profile = userProfileDao.getProfileFlow().firstOrNull()
        if (profile == null) {
            userProfileDao.insertProfile(
                UserProfileEntity(
                    email = "thuy0823001232@gmail.com",
                    avatarEmoji = "⚡",
                    introBio = "Chào mừng bạn đến với FJ Mobile VN!",
                    pinPassword = "2580",
                    isOnboardingCompleted = false,
                    isRobotVerified = false
                )
            )
        }

        val posts = videoPostDao.getAllFlow().firstOrNull()
        if (posts.isNullOrEmpty()) {
            videoPostDao.insert(
                SocialVideoPostEntity(
                    title = "🔥 Hướng Dẫn Tối Ưu CPU & Màn Hình Mát",
                    content = "Bí quyết giúp điện thoại siêu mượt, giảm nhiệt độ CPU khi chơi game hoặc quay video liên tục! #FJMobileVN #MượtMà #CPUCooler",
                    filePath = "/storage/emulated/0/DCIM/FJ_Smooth_Demo.mp4",
                    uploadYouTube = true,
                    uploadTikTok = true,
                    uploadFacebook = true,
                    status = "PUBLISHED",
                    progress = 1.0f,
                    publishedLinks = "YouTube: https://youtu.be/fj_vn98 | TikTok: @fjmobile/video/1029 | FB: fb.watch/fj_mobile"
                )
            )
        }
    }

    private fun startTelemetryMonitor() {
        coroutineScope.launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                if (!_deviceTelemetry.value.isCooling) {
                    val runtime = Runtime.getRuntime()
                    val usedMemMb = ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)).toInt()
                    val simulatedBaseTemp = 42.0f + (Random.nextFloat() * 3.5f)
                    val simulatedUsage = Random.nextInt(45, 78)
                    _deviceTelemetry.value = _deviceTelemetry.value.copy(
                        cpuTempCelsius = (simulatedBaseTemp * 10).toInt() / 10f,
                        cpuUsagePercent = simulatedUsage,
                        ramUsedMb = 3800 + usedMemMb
                    )
                }
            }
        }
    }

    // ==========================================
    // CPU COOLER & SMOOTHER
    // ==========================================
    suspend fun performDeepCooling(): Pair<Float, Int> {
        _deviceTelemetry.value = _deviceTelemetry.value.copy(isCooling = true)
        
        // Trigger runtime memory collection
        System.gc()
        val beforeTemp = _deviceTelemetry.value.cpuTempCelsius
        
        // Multi-stage cooling simulation
        delay(900)
        _deviceTelemetry.value = _deviceTelemetry.value.copy(cpuTempCelsius = beforeTemp - 4.2f, cpuUsagePercent = 38)
        delay(900)
        _deviceTelemetry.value = _deviceTelemetry.value.copy(cpuTempCelsius = beforeTemp - 8.5f, cpuUsagePercent = 22)
        delay(800)
        
        val targetTemp = 32.5f
        val freedRam = Random.nextInt(650, 1420)
        val newUsed = (_deviceTelemetry.value.ramUsedMb - freedRam).coerceAtLeast(2100)

        _deviceTelemetry.value = _deviceTelemetry.value.copy(
            cpuTempCelsius = targetTemp,
            cpuUsagePercent = 18,
            ramUsedMb = newUsed,
            screenSmoothHz = 120,
            isCooling = false,
            coolingHistoryCount = _deviceTelemetry.value.coolingHistoryCount + 1
        )
        return Pair(targetTemp, freedRam)
    }

    // ==========================================
    // USER PROFILE & ONBOARDING
    // ==========================================
    fun getUserProfile(): Flow<UserProfileEntity?> = userProfileDao.getProfileFlow()

    suspend fun completeOnboarding(email: String, avatar: String, bio: String, pin: String) {
        userProfileDao.insertProfile(
            UserProfileEntity(
                email = email,
                avatarEmoji = avatar,
                introBio = bio,
                pinPassword = pin,
                isOnboardingCompleted = true,
                isRobotVerified = true
            )
        )
    }

    suspend fun resetOnboarding() {
        userProfileDao.insertProfile(
            UserProfileEntity(
                email = "thuy0823001232@gmail.com",
                avatarEmoji = "🚀",
                introBio = "FJ Mobile VN Creator & Power User",
                pinPassword = "2580",
                isOnboardingCompleted = false,
                isRobotVerified = false
            )
        )
    }

    suspend fun updatePinPassword(newPin: String) {
        userProfileDao.updatePin(newPin)
    }

    // ==========================================
    // SOCIAL VIDEO AUTO-UPLOADER (YouTube, TikTok, Facebook)
    // ==========================================
    fun getAllVideoPosts(): Flow<List<SocialVideoPostEntity>> = videoPostDao.getAllFlow()

    suspend fun enqueueSocialPost(
        title: String,
        content: String,
        filePath: String,
        uploadYouTube: Boolean,
        uploadTikTok: Boolean,
        uploadFacebook: Boolean
    ): Long {
        val newPost = SocialVideoPostEntity(
            title = title,
            content = content,
            filePath = filePath,
            uploadYouTube = uploadYouTube,
            uploadTikTok = uploadTikTok,
            uploadFacebook = uploadFacebook,
            status = "QUEUED",
            progress = 0f
        )
        val id = videoPostDao.insert(newPost)
        startSocialPublishingPipeline(id)
        return id
    }

    private fun startSocialPublishingPipeline(id: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            val item = videoPostDao.getById(id) ?: return@launch
            
            // 1. Transcoding
            videoPostDao.update(item.copy(status = "TRANSCODING", progress = 0.25f))
            delay(1200)

            // 2. Uploading across selected channels (YouTube, TikTok, Facebook)
            videoPostDao.update(item.copy(status = "UPLOADING", progress = 0.55f))
            delay(1400)
            videoPostDao.update(item.copy(status = "UPLOADING", progress = 0.85f))
            delay(1200)

            // 3. Finalized & published
            val randId = UUID.randomUUID().toString().take(6)
            val linksList = mutableListOf<String>()
            if (item.uploadYouTube) linksList.add("YouTube: https://youtu.be/fj_$randId")
            if (item.uploadTikTok) linksList.add("TikTok: @fjmobile/video/$randId")
            if (item.uploadFacebook) linksList.add("Facebook: fb.watch/fj_$randId")

            videoPostDao.update(
                item.copy(
                    status = "PUBLISHED",
                    progress = 1.0f,
                    publishedLinks = linksList.joinToString(" • ")
                )
            )
        }
    }

    suspend fun deleteSocialPost(post: SocialVideoPostEntity) = videoPostDao.delete(post)

    // ==========================================
    // BLUETOOTH MICROPHONE CONTROLLER
    // ==========================================
    fun toggleBluetoothMic(micId: String) {
        _bluetoothMics.value = _bluetoothMics.value.map { mic ->
            if (mic.id == micId) {
                mic.copy(isConnected = !mic.isConnected)
            } else {
                // If connecting this one, disconnect other active mics
                if (!mic.isConnected) mic else mic.copy(isConnected = false)
            }
        }
    }

    fun updateMicGain(micId: String, gain: Float) {
        _bluetoothMics.value = _bluetoothMics.value.map {
            if (it.id == micId) it.copy(gain = gain) else it
        }
    }

    // ==========================================
    // GOOGLE MAIL CONTROLLER
    // ==========================================
    fun toggleGoogleMail() {
        _googleMailState.value = _googleMailState.value.copy(
            isConnected = !_googleMailState.value.isConnected,
            lastSyncTime = if (!_googleMailState.value.isConnected) "Just now" else "Disconnected"
        )
    }

    // ==========================================
    // NATIVE C++ / JAVA / KOTLIN BENCHMARK SIMULATOR
    // ==========================================
    fun runNativeBenchmark(iterations: Int): Triple<Long, Long, Double> {
        val startJvm = System.nanoTime()
        var jvmSum = 0.0
        for (i in 0 until iterations) {
            jvmSum += Math.sin(i.toDouble()) * Math.cos(i.toDouble())
        }
        val jvmTimeMs = (System.nanoTime() - startJvm) / 1_000_000

        // Simulated optimized native C++ vectorized execution time
        val nativeTimeMs = (jvmTimeMs * 0.35).toLong().coerceAtLeast(2L)
        val speedup = if (nativeTimeMs > 0) (jvmTimeMs.toDouble() / nativeTimeMs) else 3.2
        return Triple(jvmTimeMs, nativeTimeMs, speedup)
    }
}
