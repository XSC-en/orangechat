package me.rerere.rikkahub.ui.pages.qqlog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.components.GlassCard
import me.rerere.rikkahub.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QQLogPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "QQ 日志",
                style = MuranTypography.displayLarge.copy(fontSize = 28.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "AI 思考链与 token 追踪",
                style = MuranTypography.bodyMedium.copy(color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassCard(
                    modifier = Modifier.weight(1f),
                    cornerRadius = 16.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "1,234",
                            style = MuranTypography.bodyLarge.copy(color = WarmGoldMain)
                        )
                        Text(
                            text = "总消息",
                            style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                        )
                    }
                }
                GlassCard(
                    modifier = Modifier.weight(1f),
                    cornerRadius = 16.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "56.8K",
                            style = MuranTypography.bodyLarge.copy(color = TwilightPurpleMain)
                        )
                        Text(
                            text = "Tokens",
                            style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Log List
            Text(
                text = "最近日志",
                style = MuranTypography.titleLarge.copy(fontSize = 16.sp, color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(sampleLogs) { log ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        cornerRadius = 16.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = log.time,
                                    style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                                )
                                Text(
                                    text = log.tokens,
                                    style = MuranTypography.bodyMedium.copy(color = TextSecondary)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = log.preview,
                                style = MuranTypography.bodyMedium.copy(color = TextPrimary)
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class LogItem(
    val time: String,
    val tokens: String,
    val preview: String
)

private val sampleLogs = listOf(
    LogItem("14:32", "1.2K tokens", "分析用户需求：整理会议重点..."),
    LogItem("14:30", "856 tokens", "调用工具：搜索网页..."),
    LogItem("14:28", "2.1K tokens", "生成回复：会议议程包括..."),
    LogItem("14:25", "640 tokens", "调用工具：获取天气信息..."),
    LogItem("14:20", "3.5K tokens", "深度思考：用户询问关于...")
)
