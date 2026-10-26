package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    onComplete: (email: String, avatar: String, bio: String, pin: String) -> Unit,
    onToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showLoadingSplash by remember { mutableStateOf(true) }

    // Loading Splash display duration
    LaunchedEffect(Unit) {
        delay(2200)
        showLoadingSplash = false
    }

    if (showLoadingSplash) {
        LoadingSplashScreen()
    } else {
        OnboardingForm(onComplete = onComplete, onToast = onToast)
    }
}

@Composable
fun LoadingSplashScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "RainbowWave")
    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Shift"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate950),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(RainbowDiagonalGradient)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "😘", fontSize = 42.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Animated Rainbow App Name
            Text(
                text = "😘FJ Mobile VN",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                ),
                color = RainbowCyan,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Đang khởi tạo hệ thống mượt mà & bảo mật AI...",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate400,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Rainbow shimmering bar
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(RainbowGradient)
            )
        }
    }
}

@Composable
fun OnboardingForm(
    onComplete: (email: String, avatar: String, bio: String, pin: String) -> Unit,
    onToast: (String) -> Unit
) {
    var email by remember { mutableStateOf("thuy0823001232@gmail.com") }
    var selectedAvatar by remember { mutableStateOf("🚀") }
    var introBio by remember { mutableStateOf("Chào mừng đến với FJ Mobile VN - Ứng dụng mượt mà và làm mát CPU!") }
    var pinPassword by remember { mutableStateOf("2580") }

    // Robot Verification Challenge
    var isCaptchaChecked by remember { mutableStateOf(false) }
    var captchaAnswer by remember { mutableStateOf("") }
    val captchaNum1 = 8
    val captchaNum2 = 6
    val expectedAnswer = (captchaNum1 + captchaNum2).toString()

    val avatars = listOf("🚀", "⚡", "🤖", "🔥", "💎", "🎮", "👑", "🌟")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate950)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        // Rainbow Header Tag
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(RainbowGradient)
                .padding(horizontal = 14.dp, vertical = 4.dp)
        ) {
            Text(
                text = "THIẾT LẬP BẮT BUỘC BAN ĐẦU",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = Slate950,
                    letterSpacing = 1.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "😘FJ Mobile VN",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Slate100
        )

        Text(
            text = "Vui lòng hoàn tất thông tin để kích hoạt tài khoản & bảo mật PIN.",
            style = MaterialTheme.typography.bodySmall,
            color = Slate400,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Step 1: Avatar selection
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "1. Tải ảnh & Chọn Avatar Đại Diện",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = RainbowCyan
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(avatars) { av ->
                        val isSel = selectedAvatar == av
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .then(
                                    if (isSel) Modifier.background(RainbowDiagonalGradient)
                                    else Modifier.background(Slate800)
                                )
                                .border(2.dp, if (isSel) Color.White else Color.Transparent, CircleShape)
                                .clickable { selectedAvatar = av },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = av, fontSize = 22.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Step 2: Google Email
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "2. Liên Kết Email Google",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = RainbowOrange
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = RainbowOrange) },
                    modifier = Modifier.fillMaxWidth().testTag("onboarding_email_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Slate100,
                        unfocusedTextColor = Slate100,
                        focusedBorderColor = RainbowOrange
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Step 3: Bio / Introduction
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "3. Giới Thiệu Bản Thân (Bio)",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = RainbowGreen
                )
                OutlinedTextField(
                    value = introBio,
                    onValueChange = { introBio = it },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = RainbowGreen) },
                    modifier = Modifier.fillMaxWidth().testTag("onboarding_bio_input"),
                    maxLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Slate100,
                        unfocusedTextColor = Slate100,
                        focusedBorderColor = RainbowGreen
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Step 4: Numeric PIN password
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "4. Mật Khẩu PIN Số (Text Number)",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = RainbowPurple
                )
                OutlinedTextField(
                    value = pinPassword,
                    onValueChange = { if (it.length <= 6 && it.all { char -> char.isDigit() }) pinPassword = it },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = RainbowPurple) },
                    placeholder = { Text("Nhập 4-6 số...", color = Slate400) },
                    modifier = Modifier.fillMaxWidth().testTag("onboarding_pin_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Slate100,
                        unfocusedTextColor = Slate100,
                        focusedBorderColor = RainbowPurple
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Step 5: "Xác minh có phải robot" (CAPTCHA)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "5. Xác Minh Bạn Không Phải Robot",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = RainbowYellow
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate850)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Checkbox(
                        checked = isCaptchaChecked,
                        onCheckedChange = { isCaptchaChecked = it },
                        colors = CheckboxDefaults.colors(checkedColor = RainbowGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Tôi không phải là người máy (reCAPTCHA)",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = Slate100
                    )
                }

                if (isCaptchaChecked) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Câu đố bảo mật: $captchaNum1 + $captchaNum2 = ?",
                            style = MaterialTheme.typography.bodySmall.copy(color = RainbowCyan, fontWeight = FontWeight.Bold)
                        )
                        OutlinedTextField(
                            value = captchaAnswer,
                            onValueChange = { captchaAnswer = it },
                            placeholder = { Text("Đáp án...") },
                            modifier = Modifier.width(100.dp).testTag("captcha_answer_input"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Slate100,
                                unfocusedTextColor = Slate100
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Submit Button
        Button(
            onClick = {
                if (email.isBlank() || !email.contains("@")) {
                    onToast("Vui lòng nhập email hợp lệ!")
                    return@Button
                }
                if (pinPassword.length < 4) {
                    onToast("Mật khẩu PIN phải có ít nhất 4 chữ số!")
                    return@Button
                }
                if (!isCaptchaChecked || captchaAnswer.trim() != expectedAnswer) {
                    onToast("Vui lòng tích xác nhận CAPTCHA và nhập đáp án đúng ($captchaNum1 + $captchaNum2)!")
                    return@Button
                }
                onComplete(email, selectedAvatar, introBio, pinPassword)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RainbowCyan),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("submit_onboarding_button")
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Slate950, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hoàn Tất & Vào FJ Mobile VN",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Slate950)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
