package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppNavTab
import com.example.ui.NexusViewModel
import com.example.ui.components.AiWalkingPermissionDialog
import com.example.ui.components.FJTopBar
import com.example.ui.components.WalkingAiAndPinWidget
import com.example.ui.screens.BluetoothMicScreen
import com.example.ui.screens.CpuCoolerScreen
import com.example.ui.screens.GoogleMailScreen
import com.example.ui.screens.NativeSdkScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.VideoPublishingScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGradient
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

class MainActivity : ComponentActivity() {
    private val viewModel: NexusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = true) {
                FJMobileApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FJMobileApp(viewModel: NexusViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()

    val isAiPermissionGranted by viewModel.isAiPermissionGranted.collectAsState()
    val showAiPermissionDialog by viewModel.showAiPermissionDialog.collectAsState()

    val isAiWidgetExpanded by viewModel.isAiWidgetExpanded.collectAsState()
    val pinEntered by viewModel.pinEntered.collectAsState()
    val isUnlocked by viewModel.isUnlocked.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Collect snackbar / toast notifications
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Check mandatory onboarding completion
    val isOnboardingDone = profile?.isOnboardingCompleted == true

    if (!isOnboardingDone) {
        OnboardingScreen(
            onComplete = { email, avatar, bio, pin ->
                viewModel.submitOnboarding(email, avatar, bio, pin)
            },
            onToast = { viewModel.showToast(it) }
        )
    } else {
        // Back press handling
        BackHandler(enabled = currentTab != AppNavTab.COOLER_DASHBOARD || isAiWidgetExpanded) {
            if (isAiWidgetExpanded) {
                viewModel.toggleAiWidget()
            } else {
                viewModel.selectTab(AppNavTab.COOLER_DASHBOARD)
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                FJTopBar(
                    title = "😘FJ Mobile VN",
                    subtitle = when (currentTab) {
                        AppNavTab.COOLER_DASHBOARD -> "Làm Mát CPU & Màn Hình Mượt 120Hz"
                        AppNavTab.VIDEO_UPLOADER -> "Tự Động Đăng YouTube, TikTok, Facebook"
                        AppNavTab.BLUETOOTH_MIC -> "Thu Âm Microphone Không Dây"
                        AppNavTab.NATIVE_SDK -> "Cầu Nối C++ & Java & Kotlin JNI"
                        AppNavTab.GOOGLE_MAIL -> "Đồng Bộ Tài Khoản Google Mail"
                    },
                    onResetClick = { viewModel.resetAppToOnboarding() },
                    onAiToggleClick = { viewModel.promptAiPermissionDialog() },
                    isAiEnabled = isAiPermissionGranted
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Slate900,
                    contentColor = Slate100,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .border(width = 1.dp, brush = RainbowGradient, shape = RoundedCornerShape(0.dp))
                        .testTag("fj_bottom_nav")
                ) {
                    val tabs = listOf(
                        Triple(AppNavTab.COOLER_DASHBOARD, "Mát CPU", Icons.Default.AcUnit),
                        Triple(AppNavTab.VIDEO_UPLOADER, "Đăng Video", Icons.Default.CloudUpload),
                        Triple(AppNavTab.BLUETOOTH_MIC, "Mic BT", Icons.Default.Mic),
                        Triple(AppNavTab.NATIVE_SDK, "C++ SDK", Icons.Default.Code),
                        Triple(AppNavTab.GOOGLE_MAIL, "Gmail", Icons.Default.Email)
                    )

                    tabs.forEach { (tab, label, icon) ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 10.sp
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Slate950,
                                selectedTextColor = RainbowCyan,
                                indicatorColor = RainbowCyan,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Slate950)
                    .padding(innerPadding)
            ) {
                // Active Screen View
                Crossfade(targetState = currentTab, label = "ScreenTransition") { targetScreen ->
                    when (targetScreen) {
                        AppNavTab.COOLER_DASHBOARD -> CpuCoolerScreen(viewModel = viewModel)
                        AppNavTab.VIDEO_UPLOADER -> VideoPublishingScreen(viewModel = viewModel)
                        AppNavTab.BLUETOOTH_MIC -> BluetoothMicScreen(viewModel = viewModel)
                        AppNavTab.NATIVE_SDK -> NativeSdkScreen(viewModel = viewModel)
                        AppNavTab.GOOGLE_MAIL -> GoogleMailScreen(viewModel = viewModel)
                    }
                }

                // AI ICON PERMISSION DIALOG ("Hỏi quyền hiển icon AI đang đi bộ")
                if (showAiPermissionDialog) {
                    AiWalkingPermissionDialog(
                        onGrant = { viewModel.grantAiPermission(true) },
                        onDeny = { viewModel.grantAiPermission(false) }
                    )
                }

                // FLOATING WALKING AI & EXPANDABLE PIN WIDGET (chỉ hiển thị khi đã cấp quyền)
                if (isAiPermissionGranted) {
                    WalkingAiAndPinWidget(
                        isExpanded = isAiWidgetExpanded,
                        pinEntered = pinEntered,
                        isUnlocked = isUnlocked,
                        onToggleExpand = { viewModel.toggleAiWidget() },
                        onPinChange = { newPin -> viewModel.setPinEntered(newPin) },
                        onDigitClick = { digit ->
                            if (digit == "BACKSPACE") {
                                if (pinEntered.isNotEmpty()) {
                                    viewModel.setPinEntered(pinEntered.dropLast(1))
                                }
                            } else {
                                viewModel.appendPinDigit(digit)
                            }
                        },
                        onClearClick = { viewModel.clearPin() },
                        onQuickBoost = {
                            viewModel.coolDownCpuNow()
                            viewModel.toggleAiWidget()
                        },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}
