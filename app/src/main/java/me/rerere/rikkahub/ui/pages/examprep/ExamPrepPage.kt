package me.rerere.rikkahub.ui.pages.examprep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
fun ExamPrepPage() {
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
                text = "考公刷题",
                style = MuranTypography.displayLarge.copy(fontSize = 28.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "每日一练，稳步上岸",
                style = MuranTypography.bodyMedium.copy(color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Progress Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "今日进度",
                            style = MuranTypography.bodyLarge
                        )
                        Text(
                            text = "12/30",
                            style = MuranTypography.bodyLarge.copy(color = WarmGoldMain)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = 12f / 30f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = WarmGoldMain,
                        trackColor = GlassBorder,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "已完成 40%，继续加油！",
                        style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Question List
            Text(
                text = "题目列表",
                style = MuranTypography.titleLarge.copy(fontSize = 16.sp, color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(sampleQuestions) { question ->
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = question.category,
                                    style = MuranTypography.bodyMedium.copy(color = TwilightPurpleMain)
                                )
                                if (question.done) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Done",
                                        tint = WarmGoldMain,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = question.title,
                                style = MuranTypography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question.difficulty,
                                style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class QuestionItem(
    val category: String,
    val title: String,
    val difficulty: String,
    val done: Boolean
)

private val sampleQuestions = listOf(
    QuestionItem("言语理解", "下面句子中，成语使用恰当的一项是...", "中等", true),
    QuestionItem("数量关系", "某工厂生产一批零件，原计划...", "困难", false),
    QuestionItem("判断推理", "所有A都是B，有些B不是C， therefore...", "中等", false),
    QuestionItem("资料分析", "根据表格数据，2024年同比增长率...", "简单", true),
    QuestionItem("常识判断", "下列关于我国地理的说法正确的是...", "简单", false)
)
