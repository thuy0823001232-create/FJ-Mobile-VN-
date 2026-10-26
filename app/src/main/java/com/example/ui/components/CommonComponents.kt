package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowDiagonalGradient
import com.example.ui.theme.RainbowGradient
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowYellow
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun FJTopBar(
    title: String = "😘FJ Mobile VN",
    subtitle: String = "Hệ Thống Mượt Mà & Làm Mát CPU",
    onResetClick: () -> Unit = {},
    onAiToggleClick: () -> Unit = {},
    isAiEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Slate900)
            .border(
                width = 1.5.dp,
                brush = RainbowGradient,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(RainbowDiagonalGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "😘", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        ),
                        color = RainbowCyan
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = Slate400
                    )
                }
            }

            // Right action buttons: AI Icon permission toggle + Reset / Làm lại button
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Toggle AI permission
                IconButton(
                    onClick = onAiToggleClick,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(if (isAiEnabled) RainbowCyan.copy(alpha = 0.2f) else Slate800)
                        .border(1.dp, if (isAiEnabled) RainbowCyan else Slate700, CircleShape)
                        .testTag("topbar_ai_permission_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "Quyền AI",
                        tint = if (isAiEnabled) RainbowCyan else Slate400,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Reset "Làm Lại" button
                IconButton(
                    onClick = onResetClick,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(RainbowOrange.copy(alpha = 0.15f))
                        .border(1.dp, RainbowOrange, CircleShape)
                        .testTag("topbar_reset_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "Làm lại",
                        tint = RainbowOrange,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
