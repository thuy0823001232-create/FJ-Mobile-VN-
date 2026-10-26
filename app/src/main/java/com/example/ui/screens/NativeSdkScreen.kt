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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.IntegrationInstructions
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NexusViewModel
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGradient
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowPurple
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
fun NativeSdkScreen(
    viewModel: NexusViewModel,
    modifier: Modifier = Modifier
) {
    var selectedLayerTab by remember { mutableIntStateOf(0) }
    val benchmarkResult by viewModel.benchmarkResult.collectAsState()

    val codeSnippets = listOf(
        Pair(
            "C++ (native-lib.cpp)",
            """
#include <jni.h>
#include <cmath>

extern "C" JNIEXPORT jdouble JNICALL
Java_com_example_FJNativeBridge_computeMatrixSimd(
    JNIEnv* env,
    jobject /* this */,
    jint iterations
) {
    double total = 0.0;
    #pragma omp simd
    for (int i = 0; i < iterations; ++i) {
        total += std::sin(i) * std::cos(i);
    }
    return total;
}
            """.trimIndent()
        ),
        Pair(
            "Java (FJNativeBridge.java)",
            """
package com.example;

public class FJNativeBridge {
    static {
        // Tải thư viện C++ biên dịch từ Android NDK
        System.loadLibrary("fj_native_core");
    }

    // Khai báo hàm Native viết bằng C++
    public native double computeMatrixSimd(int iterations);
}
            """.trimIndent()
        ),
        Pair(
            "Kotlin SDK (FJMobileSdk.kt)",
            """
package com.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FJMobileSdk {
    private val nativeBridge = FJNativeBridge()

    suspend fun runFastMath(count: Int): Double = withContext(Dispatchers.Default) {
        // Gọi C++ qua Java JNI an toàn trong Coroutine
        nativeBridge.computeMatrixSimd(count)
    }
}
            """.trimIndent()
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "C++ & Java & Kotlin SDK Bridge",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
            Text(
                text = "Kiến trúc kết nối JNI (Java Native Interface) đồng bộ giữa C++, Java và Kotlin",
                style = MaterialTheme.typography.bodySmall,
                color = Slate400
            )
        }

        // Answer / Explanation Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(18.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(RainbowGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.IntegrationInstructions, contentDescription = null, tint = RainbowGreen, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "KẾT NỐI ĐƯỢC 100% VỚI SDK JNI",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = RainbowGreen
                            )
                            Text(
                                text = "Chuẩn kiến trúc chính thức của Google Android NDK",
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate400
                            )
                        }
                    }

                    Text(
                        text = "• C++ biên dịch thành mã máy nhị phân (.so) chạy trực tiếp trên vi xử lý ARM/x86 để làm mát và tăng tốc CPU cực đại.\n" +
                               "• Java làm cầu nối JNIEXPORT trung gian ánh xạ con trỏ bộ nhớ JVM.\n" +
                               "• Kotlin là tầng giao diện UI hiện đại (Jetpack Compose) gọi C++ mượt mà không làm đơ giao diện.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate300,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Benchmark Action
                    Button(
                        onClick = { viewModel.runNativeBenchmark() },
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowCyan),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_native_benchmark_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Slate950, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Chạy Benchmark C++ Native Qua JNI", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    benchmarkResult?.let { result ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate950)
                                .border(1.dp, RainbowGreen, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = result,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = RainbowGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Code Inspector Tabs
        item {
            Text(
                text = "Mã Nguồn Cầu Nối Thực Tế (Source Code Layers)",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Slate100
            )
            Spacer(modifier = Modifier.height(8.dp))

            TabRow(
                selectedTabIndex = selectedLayerTab,
                containerColor = Slate900,
                contentColor = RainbowCyan,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedLayerTab]),
                        color = RainbowCyan
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Slate800, RoundedCornerShape(10.dp))
            ) {
                codeSnippets.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedLayerTab == index,
                        onClick = { selectedLayerTab = index },
                        text = {
                            Text(
                                text = item.first.substringBefore(" "),
                                fontSize = 11.sp,
                                fontWeight = if (selectedLayerTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Code Display Box
            Card(
                colors = CardDefaults.cardColors(containerColor = Slate950),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = RainbowGradient)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = codeSnippets[selectedLayerTab].first,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = RainbowOrange
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = codeSnippets[selectedLayerTab].second,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = RainbowCyan,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
