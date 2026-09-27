package me.rerere.rikkahub.ui.pages.examprep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.theme.CardShape

// 考公学堂页面
// 包含：每日一练、分类刷题、错题本、学习进度

data class ExamCategory(
    val id: String,
    val name: String,
    val icon: String,
    val color: androidx.compose.ui.graphics.Color
)

data class PracticeQuestion(
    val id: String,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String
)

@Composable
fun ExamPrepPage() {
    var selectedTab by remember { mutableStateOf("daily") } // daily, practice, wrong, progress
    var dailyCompleted by remember { mutableStateOf(false) }
    var dailyScore by remember { mutableStateOf<Int?>(null) }

    val categories = listOf(
        ExamCategory("常识", "常识判断", "📚", androidx.compose.ui.graphics.Color(0xFF4CAF50)),
        ExamCategory("言语", "言语理解", "📝", androidx.compose.ui.graphics.Color(0xFF2196F3)),
        ExamCategory("数量", "数量关系", "🔢", androidx.compose.ui.graphics.Color(0xFFFF9800)),
        ExamCategory("判断", "判断推理", "🧩", androidx.compose.ui.graphics.Color(0xFF9C27B0)),
        ExamCategory("资料", "资料分析", "📊", androidx.compose.ui.graphics.Color(0xFFF44336))
    )

    val dailyQuestions = remember {
        listOf(
            PracticeQuestion(
                id = "1",
                category = "常识",
                question = "中国古代四大发明中，哪一项发明与航海有关？",
                options = listOf("造纸术", "印刷术", "指南针", "火药"),
                correctAnswer = 2,
                explanation = "指南针是中国古代四大发明之一，最早应用于航海导航。"
            ),
            PracticeQuestion(
                id = "2",
                category = "言语",
                question = "下列成语中，与"刻苦学习"最相近的是？",
                options = listOf("废寝忘食", "守株待兔", "掩耳盗铃", "画蛇添足"),
                correctAnswer = 0,
                explanation = "废寝忘食形容专心努力，与刻苦学习意思最相近。"
            ),
            PracticeQuestion(
                id = "3",
                category = "数量",
                question = "一个数除以3余2，除以5余3，除以7余2，这个数最小是多少？",
                options = listOf("23", "17", "31", "37"),
                correctAnswer = 0,
                explanation = "使用中国剩余定理求解，最小解为23。"
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "考公学堂", style = MaterialTheme.typography.headlineMedium)

        // 标签页切换
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOf("每日一练", "分类刷题", "错题本", "学习进度")) { tab ->
                val isSelected = when (tab) {
                    "每日一练" -> selectedTab == "daily"
                    "分类刷题" -> selectedTab == "practice"
                    "错题本" -> selectedTab == "wrong"
                    "学习进度" -> selectedTab == "progress"
                    else -> false
                }
                Button(
                    onClick = { selectedTab = when (tab) {
                        "每日一练" -> "daily"
                        "分类刷题" -> "practice"
                        "错题本" -> "wrong"
                        "学习进度" -> "progress"
                        else -> selectedTab
                    }},
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(text = tab, color = if (isSelected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // 内容区域
        when (selectedTab) {
            "daily" -> DailyPracticeTab(
                questions = dailyQuestions,
                completed = dailyCompleted,
                score = dailyScore,
                onComplete = { score ->
                    dailyCompleted = true
                    dailyScore = score
                }
            )
            "practice" -> PracticeTab(categories = categories)
            "wrong" -> WrongAnswersTab()
            "progress" -> ProgressTab()
        }
    }
}

@Composable
private fun DailyPracticeTab(
    questions: List<PracticeQuestion>,
    completed: Boolean,
    score: Int?,
    onComplete: (Int) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var correctCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (completed) {
            Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "今日完成！", style = MaterialTheme.typography.headlineSmall)
                    Text(text = "得分: $score / ${questions.size}", style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = {
                        currentIndex = 0
                        selectedAnswer = null
                        showExplanation = false
                        correctCount = 0
                    }) {
                        Text("再来一次")
                    }
                }
            }
        } else if (currentIndex < questions.size) {
            val question = questions[currentIndex]
            Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "${question.category} | 第 ${currentIndex + 1}/${questions.size} 题",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = question.question, style = MaterialTheme.typography.bodyLarge)

                    // 选项
                    question.options.forEachIndexed { index, option ->
                        val isSelected = selectedAnswer == index
                        val isCorrect = index == question.correctAnswer
                        Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    showExplanation && isCorrect -> androidx.compose.ui.graphics.Color(0xFF4CAF50).copy(alpha = 0.2f)
                                    showExplanation && isSelected && !isCorrect -> androidx.compose.ui.graphics.Color(0xFFF44336).copy(alpha = 0.2f)
                                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surface
                                }
                            ),
                            onClick = {
                                if (!showExplanation) {
                                    selectedAnswer = index
                                }
                            }
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // 解析
                    if (showExplanation) {
                        Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFF4CAF50).copy(alpha = 0.1f))
                        ) {
                            Text(
                                text = "解析: ${question.explanation}",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // 按钮
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!showExplanation) {
                            Button(
                                onClick = {
                                    selectedAnswer?.let { ans ->
                                        if (ans == question.correctAnswer) correctCount++
                                    }
                                    showExplanation = true
                                },
                                enabled = selectedAnswer != null
                            ) {
                                Text("确认答案")
                            }
                        } else {
                            Button(onClick = {
                                if (currentIndex < questions.size - 1) {
                                    currentIndex++
                                    selectedAnswer = null
                                    showExplanation = false
                                } else {
                                    onComplete(correctCount)
                                }
                            }) {
                                Text(if (currentIndex < questions.size - 1) "下一题" else "完成")
                            }
                        }
                    }
                }
            }
        } else {
            // 应该不会走到这里，因为 completed 为 true 时会显示完成状态
        }
    }
}

@Composable
private fun PracticeTab(categories: List<ExamCategory>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { cat ->
            Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "${cat.icon} ${cat.name}", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "点击开始刷题",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(onClick = { /* TODO: 进入刷题模式 */ }) {
                        Text("开始练习")
                    }
                }
            }
        }
    }
}

@Composable
private fun WrongAnswersTab() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📝", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "暂无错题", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = "做错的题目会自动收录到这里", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProgressTab() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "学习进度", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "连续打卡: 0 天", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "已练习: 0 题", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "正确率: --", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        items(listOf("常识判断", "言语理解", "数量关系", "判断推理", "资料分析")) { category ->
            Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = category, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "进度: 0%", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
