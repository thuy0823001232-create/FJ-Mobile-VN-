package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowDiagonalGradient
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
fun WalkingAiAndPinWidget(
    isExpanded: Boolean,
    pinEntered: String,
    isUnlocked: Boolean,
    onToggleExpand: () -> Unit,
    onPinChange: (String) -> Unit,
    onDigitClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onQuickBoost: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (!isExpanded) {
            // WALKING AI MASCOT (Animated)
            WalkingAiMascot(onClick = onToggleExpand)
        } else {
            // EXPANDED PIN & SYSTEM WIDGET
            ExpandedPinWidget(
                pinEntered = pinEntered,
                isUnlocked = isUnlocked,
                onClose = onToggleExpand,
                onPinChange = onPinChange,
                onDigitClick = onDigitClick,
                onClearClick = onClearClick,
                onQuickBoost = onQuickBoost
            )
        }
    }
}

@Composable
fun WalkingAiMascot(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "WalkingLoop")

    // Walking stride oscillation
    val legSwing by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LegSwing"
    )

    val bobbing by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bobbing"
    )

    Card(
        modifier = Modifier
            .offset(y = bobbing.dp)
            .clickable(onClick = onClick)
            .testTag("walking_ai_mascot"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900.copy(alpha = 0.95f)),
        border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Walking Mascot Figure
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(RainbowDiagonalGradient),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "AI Walking",
                        tint = Slate950,
                        modifier = Modifier.size(26.dp)
                    )
                    // Animated walking feet
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp, 4.dp)
                                .offset(x = (legSwing / 3).dp)
                                .background(Slate950, RoundedCornerShape(2.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(5.dp, 4.dp)
                                .offset(x = (-legSwing / 3).dp)
                                .background(Slate950, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(RainbowGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "AI Đang Đi Bộ...",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = RainbowCyan
                        )
                    )
                }
                Text(
                    text = "Chạm để biến thành Widget PIN",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Slate300
                )
            }
        }
    }
}

@Composable
fun ExpandedPinWidget(
    pinEntered: String,
    isUnlocked: Boolean,
    onClose: () -> Unit,
    onPinChange: (String) -> Unit,
    onDigitClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onQuickBoost: () -> Unit
) {
    var showPasswordPlain by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(300.dp)
            .testTag("ai_pin_widget"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900.copy(alpha = 0.98f)),
        border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Widget Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (isUnlocked) RainbowGreen else RainbowYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isUnlocked) "Quyền Quản Trị Đã Mở" else "Widget Mật Khẩu Số PIN",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Slate100
                        )
                    )
                }
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Text Number Input Field ("nhập mật khẩu vào input bằng text number")
            OutlinedTextField(
                value = pinEntered,
                onValueChange = onPinChange,
                label = { Text("Nhập mật khẩu bằng text number", fontSize = 11.sp) },
                singleLine = true,
                visualTransformation = if (showPasswordPlain) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    IconButton(onClick = { showPasswordPlain = !showPasswordPlain }) {
                        Icon(
                            imageVector = if (showPasswordPlain) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle PIN",
                            tint = Slate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("widget_pin_text_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = if (isUnlocked) RainbowGreen else RainbowCyan,
                    unfocusedTextColor = Slate100,
                    focusedBorderColor = if (isUnlocked) RainbowGreen else RainbowCyan,
                    unfocusedBorderColor = Slate700,
                    focusedContainerColor = Slate950,
                    unfocusedContainerColor = Slate950
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Numeric Keypad Grid (1-9, 0, Clear, Backspace)
            val rows = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("C", "0", "⌫")
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                rows.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        row.forEach { key ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when (key) {
                                            "C" -> RainbowRed.copy(alpha = 0.2f)
                                            "⌫" -> Slate800
                                            else -> Slate850
                                        }
                                    )
                                    .clickable {
                                        when (key) {
                                            "C" -> onClearClick()
                                            "⌫" -> {
                                                if (pinEntered.isNotEmpty()) {
                                                    onDigitClick("BACKSPACE")
                                                }
                                            }
                                            else -> onDigitClick(key)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (key == "⌫") {
                                    Icon(
                                        imageVector = Icons.Default.Backspace,
                                        contentDescription = "Backspace",
                                        tint = Slate300,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Text(
                                        text = key,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = when (key) {
                                                "C" -> RainbowRed
                                                else -> Slate100
                                            }
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Boost Action when Unlocked
            Button(
                onClick = onQuickBoost,
                colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RainbowCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            ) {
                Icon(Icons.Default.Speed, contentDescription = null, tint = RainbowCyan, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tối Ưu & Làm Mát Nhanh", fontSize = 11.sp, color = RainbowCyan, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AiWalkingPermissionDialog(
    onGrant: () -> Unit,
    onDeny: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeny,
        icon = {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(RainbowDiagonalGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = Slate950,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        title = {
            Text(
                text = "Cấp Quyền Hiển Thị Icon AI Đi Bộ",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
        },
        text = {
            Text(
                text = "Ứng dụng FJ Mobile VN cần quyền hiển thị biểu tượng linh vật AI đang đi bộ trên màn hình và cho phép biến đổi thành Widget nhập mật khẩu số khi chạm vào.",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate300
            )
        },
        confirmButton = {
            Button(
                onClick = onGrant,
                colors = ButtonDefaults.buttonColors(containerColor = RainbowCyan),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Đồng Ý & Bật Icon AI", color = Slate950, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDeny) {
                Text("Từ Chối", color = Slate400)
            }
        },
        containerColor = Slate900
    )
}
