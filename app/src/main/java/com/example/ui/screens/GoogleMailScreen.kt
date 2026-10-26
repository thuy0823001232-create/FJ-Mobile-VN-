package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun GoogleMailScreen(
    viewModel: NexusViewModel,
    modifier: Modifier = Modifier
) {
    val mailState by viewModel.googleMailState.collectAsState()
    var autoNotify by remember { mutableStateOf(mailState.autoNotifyPublishedVideos) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Kết Nối Google Mail",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
            Text(
                text = "Đồng bộ tài khoản Gmail để nhận thông báo video và cảnh báo bảo mật",
                style = MaterialTheme.typography.bodySmall,
                color = Slate400
            )
        }

        // Connection Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(RainbowRed.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null, tint = RainbowRed, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = mailState.email,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Slate100
                                )
                                Text(
                                    text = if (mailState.isConnected) "Đã Liên Kết Google OAuth2" else "Chưa Kết Nối",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (mailState.isConnected) RainbowGreen else RainbowOrange
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.toggleGoogleMail() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (mailState.isConnected) RainbowGreen.copy(alpha = 0.2f) else RainbowCyan
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("toggle_gmail_button")
                        ) {
                            Text(
                                text = if (mailState.isConnected) "Đã Nối" else "Kết Nối",
                                color = if (mailState.isConnected) RainbowGreen else Slate950,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Slate950)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Hộp thư đến chưa đọc", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text("${mailState.unreadCount} Email mới", style = MaterialTheme.typography.titleSmall.copy(color = RainbowCyan, fontWeight = FontWeight.Bold))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Đồng bộ lần cuối", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text(mailState.lastSyncTime, style = MaterialTheme.typography.titleSmall.copy(color = Slate300, fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }

        // Automated Email Notifications Setting
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = RainbowYellow, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Báo Cáo Video Đăng Qua Email",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Slate100
                                )
                                Text(
                                    text = "Nhận link YouTube, TikTok, Facebook ngay sau khi đăng",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate400
                                )
                            }
                        }

                        Switch(
                            checked = autoNotify,
                            onCheckedChange = {
                                autoNotify = it
                                viewModel.showToast(if (it) "Đã bật thông báo qua Gmail!" else "Đã tắt thông báo Gmail")
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = RainbowCyan, checkedTrackColor = RainbowCyan.copy(alpha = 0.5f))
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = { viewModel.showToast("Hộp thư Gmail đã được đồng bộ mới nhất!") },
                        colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = null, tint = RainbowCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Đồng Bộ Lại Hộp Thư Ngay", color = RainbowCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
