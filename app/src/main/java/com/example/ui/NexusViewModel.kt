package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.NexusDatabase
import com.example.data.model.BluetoothMic
import com.example.data.model.DeviceTelemetry
import com.example.data.model.GoogleMailState
import com.example.data.model.SocialVideoPostEntity
import com.example.data.model.UserProfileEntity
import com.example.data.repository.NexusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavTab(val title: String) {
    COOLER_DASHBOARD("Mát CPU & Mượt"),
    VIDEO_UPLOADER("Tải & Đăng Video"),
    BLUETOOTH_MIC("Microphone Bluetooth"),
    NATIVE_SDK("C++ / Java / Kotlin"),
    GOOGLE_MAIL("Google Mail")
}

class NexusViewModel(application: Application) : AndroidViewModel(application) {
    private val database = NexusDatabase.getInstance(application)
    val repository = NexusRepository(database, viewModelScope)

    // Active Tab
    private val _currentTab = MutableStateFlow(AppNavTab.COOLER_DASHBOARD)
    val currentTab: StateFlow<AppNavTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppNavTab) {
        _currentTab.value = tab
    }

    // UI Toasts / Alerts
    private val _uiEvents = MutableSharedFlow<String>()
    val uiEvents: SharedFlow<String> = _uiEvents.asSharedFlow()

    fun showToast(message: String) {
        viewModelScope.launch {
            _uiEvents.emit(message)
        }
    }

    // User Profile
    val userProfile: StateFlow<UserProfileEntity?> = repository.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Device Telemetry
    val deviceTelemetry: StateFlow<DeviceTelemetry> = repository.deviceTelemetry
    val bluetoothMics: StateFlow<List<BluetoothMic>> = repository.bluetoothMics
    val videoPosts: StateFlow<List<SocialVideoPostEntity>> = repository.getAllVideoPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val googleMailState: StateFlow<GoogleMailState> = repository.googleMailState

    // ------------------------------------------------------------------------
    // WALKING AI & PIN WIDGET CONTROLLER (Hỏi quyền hiển icon AI)
    // ------------------------------------------------------------------------
    private val _isAiPermissionGranted = MutableStateFlow(false)
    val isAiPermissionGranted: StateFlow<Boolean> = _isAiPermissionGranted.asStateFlow()

    private val _showAiPermissionDialog = MutableStateFlow(true)
    val showAiPermissionDialog: StateFlow<Boolean> = _showAiPermissionDialog.asStateFlow()

    fun grantAiPermission(granted: Boolean) {
        _isAiPermissionGranted.value = granted
        _showAiPermissionDialog.value = false
        if (granted) {
            showToast("✨ Đã cấp quyền hiển thị icon AI đi bộ trên màn hình!")
        } else {
            showToast("Đã từ chối quyền icon AI. Bạn có thể mở lại trong cài đặt.")
        }
    }

    fun promptAiPermissionDialog() {
        _showAiPermissionDialog.value = true
    }

    private val _isAiWidgetExpanded = MutableStateFlow(false)
    val isAiWidgetExpanded: StateFlow<Boolean> = _isAiWidgetExpanded.asStateFlow()

    private val _pinEntered = MutableStateFlow("")
    val pinEntered: StateFlow<String> = _pinEntered.asStateFlow()

    private val _isUnlocked = MutableStateFlow(false)
    val isUnlocked: StateFlow<Boolean> = _isUnlocked.asStateFlow()

    fun toggleAiWidget() {
        _isAiWidgetExpanded.value = !_isAiWidgetExpanded.value
    }

    fun setPinEntered(pin: String) {
        val filtered = pin.filter { it.isDigit() }.take(6)
        _pinEntered.value = filtered
        val correctPin = userProfile.value?.pinPassword ?: "2580"
        if (filtered == correctPin) {
            _isUnlocked.value = true
            showToast("🔓 Mở khóa thành công! Quyền quản trị đã kích hoạt.")
        } else if (filtered.length >= 4 && filtered != correctPin.take(filtered.length)) {
            showToast("⚠️ Mật khẩu PIN sai, vui lòng thử lại!")
        }
    }

    fun appendPinDigit(digit: String) {
        if (_pinEntered.value.length < 6) {
            setPinEntered(_pinEntered.value + digit)
        }
    }

    fun clearPin() {
        _pinEntered.value = ""
        _isUnlocked.value = false
    }

    // ------------------------------------------------------------------------
    // ONBOARDING & RESET (LÀM LẠI TỪ ĐẦU)
    // ------------------------------------------------------------------------
    fun submitOnboarding(email: String, avatar: String, bio: String, pin: String) {
        viewModelScope.launch {
            repository.completeOnboarding(email, avatar, bio, pin)
            showToast("Chào mừng $email gia nhập FJ Mobile VN! 🚀")
        }
    }

    fun resetAppToOnboarding() {
        viewModelScope.launch {
            repository.resetOnboarding()
            clearPin()
            _isAiWidgetExpanded.value = false
            _showAiPermissionDialog.value = true
            _isAiPermissionGranted.value = false
            showToast("🔄 Đã đặt lại toàn bộ ứng dụng từ đầu!")
        }
    }

    // ------------------------------------------------------------------------
    // CPU COOLER & SMOOTHER
    // ------------------------------------------------------------------------
    fun coolDownCpuNow() {
        viewModelScope.launch {
            showToast("❄️ Đang kích hoạt làm mát CPU và tối ưu màn hình...")
            val (newTemp, freedRam) = repository.performDeepCooling()
            showToast("✅ CPU đã hạ xuống ${newTemp}°C! Màn hình 120Hz mượt mà, giải phóng ${freedRam}MB RAM.")
        }
    }

    // ------------------------------------------------------------------------
    // BLUETOOTH MICROPHONE
    // ------------------------------------------------------------------------
    fun toggleBluetoothMic(micId: String) {
        repository.toggleBluetoothMic(micId)
        val mic = bluetoothMics.value.find { it.id == micId }
        val status = if (mic?.isConnected == true) "Đã ngắt kết nối" else "Đã kết nối thành công"
        showToast("$status ${mic?.name ?: "Microphone"}")
    }

    fun updateMicGain(micId: String, gain: Float) {
        repository.updateMicGain(micId, gain)
    }

    // ------------------------------------------------------------------------
    // SOCIAL VIDEO POSTS
    // ------------------------------------------------------------------------
    fun publishVideoPost(
        title: String,
        content: String,
        filePath: String,
        uploadYouTube: Boolean,
        uploadTikTok: Boolean,
        uploadFacebook: Boolean
    ) {
        viewModelScope.launch {
            val id = repository.enqueueSocialPost(
                title = title,
                content = content,
                filePath = filePath,
                uploadYouTube = uploadYouTube,
                uploadTikTok = uploadTikTok,
                uploadFacebook = uploadFacebook
            )
            showToast("Đang tự động tải video lên YouTube, TikTok, Facebook...")
        }
    }

    fun deletePost(post: SocialVideoPostEntity) {
        viewModelScope.launch {
            repository.deleteSocialPost(post)
            showToast("Đã xóa bài viết video.")
        }
    }

    // ------------------------------------------------------------------------
    // GOOGLE MAIL
    // ------------------------------------------------------------------------
    fun toggleGoogleMail() {
        repository.toggleGoogleMail()
        val isNowConnected = googleMailState.value.isConnected
        showToast(if (isNowConnected) "Google Mail đã kết nối đồng bộ!" else "Đã ngắt kết nối Google Mail")
    }

    // ------------------------------------------------------------------------
    // NATIVE C++ / JAVA / KOTLIN BENCHMARK
    // ------------------------------------------------------------------------
    private val _benchmarkResult = MutableStateFlow<String?>(null)
    val benchmarkResult: StateFlow<String?> = _benchmarkResult.asStateFlow()

    fun runNativeBenchmark() {
        viewModelScope.launch {
            _benchmarkResult.value = "Đang chạy phép tính C++ Native SIMD qua JNI..."
            delay(600)
            val (jvmTime, nativeTime, speedup) = repository.runNativeBenchmark(500_000)
            _benchmarkResult.value = "C++ Native: ${nativeTime}ms | JVM Kotlin: ${jvmTime}ms (Nhanh hơn ${String.format("%.1f", speedup)}x)"
            showToast("C++ Native SDK Benchmark hoàn tất!")
        }
    }
}
