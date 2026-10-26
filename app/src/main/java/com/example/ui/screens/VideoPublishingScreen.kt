package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SocialVideoPostEntity
import com.example.ui.NexusViewModel
import com.example.ui.theme.FacebookBlue
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
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokPink
import com.example.ui.theme.YouTubeRed

@Composable
fun VideoPublishingScreen(
    viewModel: NexusViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val posts by viewModel.videoPosts.collectAsState()

    var videoTitle by remember { mutableStateOf("Trải Nghiệm FJ Mobile VN Cực Mượt!") }
    var videoContent by remember { mutableStateOf("Khám phá cách làm mát CPU và kết nối mic không dây chuyên nghiệp. Đăng tự động qua FJ Mobile VN! #FJMobileVN #Tech #Viral") }
    var selectedFilePath by remember { mutableStateOf("/storage/emulated/0/DCIM/Camera/VID_20261025.mp4") }
    var isFilePicked by remember { mutableStateOf(true) }

    var uploadYouTube by remember { mutableStateOf(true) }
    var uploadTikTok by remember { mutableStateOf(true) }
    var uploadFacebook by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tải & Đăng Video Tự Động",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
            Text(
                text = "Chọn video từ tệp, đặt nội dung và tự động đăng lên YouTube, TikTok, Facebook",
                style = MaterialTheme.typography.bodySmall,
                color = Slate400
            )
        }

        // Upload & Content Creation Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // File Picker Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1. Tải Video Bằng Nút File",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = RainbowCyan
                        )
                        Button(
                            onClick = {
                                val dummyFiles = listOf(
                                    "/storage/emulated/0/DCIM/Camera/VID_Gameplay_120fps.mp4 (45.2 MB)",
                                    "/storage/emulated/0/Movies/TikTok_Review_FJ.mp4 (28.4 MB)",
                                    "/storage/emulated/0/Download/Podcast_Mic_Wireless.mp4 (62.0 MB)"
                                )
                                selectedFilePath = dummyFiles.random()
                                isFilePicked = true
                                viewModel.showToast("Đã chọn tệp video từ bộ nhớ máy!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("pick_video_file_button")
                        ) {
                            Icon(Icons.Default.FileUpload, contentDescription = null, tint = RainbowCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Chọn Tệp File", color = RainbowCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Display selected file info
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Slate950)
                            .border(1.dp, RainbowCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VideoFile, contentDescription = null, tint = RainbowCyan, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = selectedFilePath.substringAfterLast("/"),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = Slate100
                                )
                                Text(
                                    text = selectedFilePath,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = Slate400,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Title & Content Inputs
                    Text(
                        text = "2. Đặt Nội Dung & Caption Video",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = RainbowOrange
                    )

                    OutlinedTextField(
                        value = videoTitle,
                        onValueChange = { videoTitle = it },
                        label = { Text("Tiêu Đề Video") },
                        modifier = Modifier.fillMaxWidth().testTag("video_title_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Slate100,
                            unfocusedTextColor = Slate100,
                            focusedBorderColor = RainbowOrange
                        )
                    )

                    OutlinedTextField(
                        value = videoContent,
                        onValueChange = { videoContent = it },
                        label = { Text("Nội Dung & Hashtags (#)") },
                        modifier = Modifier.fillMaxWidth().testTag("video_content_input"),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Slate100,
                            unfocusedTextColor = Slate100,
                            focusedBorderColor = RainbowOrange
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Channel Connections Checkboxes
                    Text(
                        text = "3. Kênh Kết Nối Tự Động Đăng",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = RainbowGreen
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // YouTube
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = uploadYouTube,
                                onCheckedChange = { uploadYouTube = it },
                                colors = CheckboxDefaults.colors(checkedColor = YouTubeRed)
                            )
                            Text("YouTube", color = Slate100, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // TikTok
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = uploadTikTok,
                                onCheckedChange = { uploadTikTok = it },
                                colors = CheckboxDefaults.colors(checkedColor = TikTokPink)
                            )
                            Text("TikTok", color = Slate100, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // Facebook
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = uploadFacebook,
                                onCheckedChange = { uploadFacebook = it },
                                colors = CheckboxDefaults.colors(checkedColor = FacebookBlue)
                            )
                            Text("Facebook", color = Slate100, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Auto-Publish Action Button
                    Button(
                        onClick = {
                            if (videoTitle.isBlank()) {
                                viewModel.showToast("Vui lòng nhập tiêu đề video!")
                                return@Button
                            }
                            viewModel.publishVideoPost(
                                title = videoTitle,
                                content = videoContent,
                                filePath = selectedFilePath,
                                uploadYouTube = uploadYouTube,
                                uploadTikTok = uploadTikTok,
                                uploadFacebook = uploadFacebook
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("auto_publish_button")
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tự Động Đăng Video Lên 3 Kênh",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Slate950)
                        )
                    }
                }
            }
        }

        // Live Feed / History
        item {
            Text(
                text = "Hàng Đợi & Lịch Sử Tự Động Đăng (${posts.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
        }

        items(posts, key = { it.id }) { post ->
            SocialPostCard(
                post = post,
                onDelete = { viewModel.deletePost(post) },
                onCopyLinks = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Published Links", post.publishedLinks))
                    viewModel.showToast("Đã sao chép liên kết video!")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SocialPostCard(
    post: SocialVideoPostEntity,
    onDelete: () -> Unit,
    onCopyLinks: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(14.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Platform pills
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (post.uploadYouTube) ChannelBadge("YouTube", YouTubeRed)
                    if (post.uploadTikTok) ChannelBadge("TikTok", TikTokPink)
                    if (post.uploadFacebook) ChannelBadge("Facebook", FacebookBlue)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (post.status) {
                                "PUBLISHED" -> RainbowGreen.copy(alpha = 0.2f)
                                "UPLOADING" -> RainbowCyan.copy(alpha = 0.2f)
                                else -> RainbowOrange.copy(alpha = 0.2f)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = post.status,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = when (post.status) {
                            "PUBLISHED" -> RainbowGreen
                            "UPLOADING" -> RainbowCyan
                            else -> RainbowOrange
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodySmall,
                color = Slate400,
                maxLines = 2
            )

            if (post.status != "PUBLISHED") {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { post.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = RainbowCyan,
                    trackColor = Slate800
                )
            }

            if (post.publishedLinks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.publishedLinks,
                    style = MaterialTheme.typography.bodySmall.copy(color = RainbowCyan, fontSize = 11.sp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (post.status == "PUBLISHED") {
                    Button(
                        onClick = onCopyLinks,
                        colors = ButtonDefaults.buttonColors(containerColor = Slate800),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Slate300, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sao Chép Link", fontSize = 11.sp, color = Slate300)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RainbowRed.copy(alpha = 0.8f))
                }
            }
        }
    }
}

@Composable
fun ChannelBadge(name: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
    }
}
