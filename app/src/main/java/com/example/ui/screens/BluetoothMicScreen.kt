package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BluetoothMic
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
import kotlin.random.Random

@Composable
fun BluetoothMicScreen(
    viewModel: NexusViewModel,
    modifier: Modifier = Modifier
) {
    val mics by viewModel.bluetoothMics.collectAsState()
    val activeMic = mics.find { it.isConnected }

    val infiniteTransition = rememberInfiniteTransition(label = "WaveformAnim")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Wave"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Bluetooth Microphone Studio",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Slate100
                    )
                    Text(
                        text = "Kết nối mic không dây, đo sóng âm dB & lọc tạp âm AI",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }
                Icon(
                    imageVector = Icons.Default.BluetoothConnected,
                    contentDescription = null,
                    tint = if (activeMic != null) RainbowCyan else Slate400,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Live Audio Waveform Monitor Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (activeMic != null) RainbowGreen else RainbowRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (activeMic != null) "MIC ĐANG THU ÂM (LIVE)" else "CHƯA KẾT NỐI MIC",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (activeMic != null) RainbowGreen else RainbowRed
                            )
                        }
                        Text(
                            text = if (activeMic != null) "-14.2 dB (Chuẩn Studio)" else "-∞ dB",
                            style = MaterialTheme.typography.labelSmall.copy(color = RainbowCyan, fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Live Waveform Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Slate950)
                            .border(1.dp, Slate800, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 6.dp)) {
                            val barCount = 28
                            val barWidth = size.width / (barCount * 1.5f)
                            val spacing = barWidth * 0.5f

                            for (i in 0 until barCount) {
                                val factor = if (activeMic != null) {
                                    val sinVal = Math.sin((i + wavePhase * 10).toDouble()).toFloat()
                                    ((sinVal + 1.2f) / 2.2f).coerceIn(0.15f, 1f)
                                } else 0.08f

                                val barHeight = size.height * factor
                                val x = i * (barWidth + spacing)
                                val y = (size.height - barHeight) / 2f

                                val barColor = when {
                                    i < 8 -> RainbowCyan
                                    i < 18 -> RainbowGreen
                                    i < 24 -> RainbowYellow
                                    else -> RainbowRed
                                }

                                drawRect(
                                    color = barColor,
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, barHeight)
                                )
                            }
                        }
                    }

                    if (activeMic != null) {
                        Spacer(modifier = Modifier.height(14.dp))
                        // Mic Gain Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Độ Nhạy Gain Âm Thanh", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text("${(activeMic.gain * 100).toInt()}%", style = MaterialTheme.typography.labelSmall.copy(color = RainbowCyan, fontWeight = FontWeight.Bold))
                        }
                        Slider(
                            value = activeMic.gain,
                            onValueChange = { viewModel.updateMicGain(activeMic.id, it) },
                            valueRange = 0.1f..1.0f,
                            colors = SliderDefaults.colors(
                                thumbColor = RainbowCyan,
                                activeTrackColor = RainbowCyan
                            )
                        )
                    }
                }
            }
        }

        // Bluetooth Devices List
        item {
            Text(
                text = "Thiết Bị Microphone Bluetooth",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
        }

        items(mics, key = { it.id }) { mic ->
            BluetoothMicCard(
                mic = mic,
                onToggle = { viewModel.toggleBluetoothMic(mic.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BluetoothMicCard(
    mic: BluetoothMic,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("bluetooth_mic_${mic.id}"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(14.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = if (mic.isConnected) RainbowGradient else Brush.linearGradient(listOf(Slate800, Slate700))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (mic.isConnected) RainbowCyan.copy(alpha = 0.2f) else Slate800),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (mic.isConnected) Icons.Default.Mic else Icons.Default.MicOff,
                        contentDescription = null,
                        tint = if (mic.isConnected) RainbowCyan else Slate400,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = mic.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Slate100
                    )
                    Text(
                        text = "${mic.type} • Pin: ${mic.battery}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }
            }

            Button(
                onClick = onToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mic.isConnected) RainbowGreen.copy(alpha = 0.2f) else Slate800
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("toggle_mic_${mic.id}")
            ) {
                Text(
                    text = if (mic.isConnected) "Đã Nối" else "Kết Nối",
                    color = if (mic.isConnected) RainbowGreen else Slate300,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
