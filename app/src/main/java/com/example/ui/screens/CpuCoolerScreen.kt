package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NexusViewModel
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGradient
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowPurple
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowYellow
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun CpuCoolerScreen(
    viewModel: NexusViewModel,
    modifier: Modifier = Modifier
) {
    val telemetry by viewModel.deviceTelemetry.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "CoolingPulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Làm Mát CPU & Tối Ưu Điện Thoại",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Slate100
                    )
                    Text(
                        text = "Màn hình 120Hz mượt mà • Giảm nhiệt độ chip CPU",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (telemetry.cpuTempCelsius < 38f) RainbowGreen.copy(alpha = 0.2f) else RainbowOrange.copy(alpha = 0.2f))
                        .border(1.dp, if (telemetry.cpuTempCelsius < 38f) RainbowGreen else RainbowOrange, CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (telemetry.cpuTempCelsius < 38f) "MÁT MẺ" else "CẦN LÀM MÁT",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (telemetry.cpuTempCelsius < 38f) RainbowGreen else RainbowOrange
                    )
                }
            }
        }

        // Circular Thermal Gauge
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(170.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { (telemetry.cpuTempCelsius / 70f).coerceIn(0f, 1f) },
                            modifier = Modifier.size(160.dp),
                            color = if (telemetry.cpuTempCelsius > 40f) RainbowOrange else RainbowCyan,
                            trackColor = Slate800,
                            strokeWidth = 12.dp
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (telemetry.isCooling) Icons.Default.AcUnit else Icons.Default.Thermostat,
                                contentDescription = null,
                                tint = if (telemetry.cpuTempCelsius > 40f) RainbowOrange else RainbowCyan,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "${telemetry.cpuTempCelsius}°C",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                color = Slate100
                            )
                            Text(
                                text = "Nhiệt độ CPU",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("CPU Usage", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text("${telemetry.cpuUsagePercent}%", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = RainbowYellow))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Tần số quét", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text("${telemetry.screenSmoothHz}Hz", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = RainbowCyan))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("RAM Đã Dùng", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text("${telemetry.ramUsedMb} MB", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = RainbowPurple))
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Cool Down Now Button
                    Button(
                        onClick = { viewModel.coolDownCpuNow() },
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("cool_down_cpu_button")
                    ) {
                        Icon(Icons.Default.AcUnit, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (telemetry.isCooling) "Đang Làm Mát & Tối Ưu RAM..." else "Bắt Đầu Làm Mát CPU & Tăng Tốc",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Slate950)
                        )
                    }
                }
            }
        }

        // Feature Highlights
        item {
            Text(
                text = "Tính Năng Tối Ưu Đang Hoạt Động",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
        }

        item {
            OptimizationItem(
                title = "Màn hình siêu mượt (120Hz Ultra Smooth)",
                desc = "Khóa tốc độ làm tươi màn hình cao nhất để thao tác vuốt chạm không bị giật lag.",
                icon = Icons.Default.PhoneAndroid,
                accentColor = RainbowCyan
            )
        }

        item {
            OptimizationItem(
                title = "Giải phóng RAM & Dọn dẹp tiến trình rác",
                desc = "Kích hoạt bộ gom rác Garbage Collector để giải phóng bộ nhớ đệm ẩn.",
                icon = Icons.Default.Memory,
                accentColor = RainbowGreen
            )
        }

        item {
            OptimizationItem(
                title = "Kiểm soát xung nhịp CPU ngăn ngừa quá nhiệt",
                desc = "Hạ tải các luồng tính toán nặng khi quay video hoặc chơi game liên tục.",
                icon = Icons.Default.Bolt,
                accentColor = RainbowOrange
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun OptimizationItem(
    title: String,
    desc: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Slate100)
                Text(text = desc, style = MaterialTheme.typography.bodySmall, color = Slate400)
            }
        }
    }
}
