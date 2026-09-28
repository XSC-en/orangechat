package me.rerere.rikkahub.ui.pages.examprep

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.theme.CardShape

// 考公学堂页面
// 包含：每日一练、分类刷题、错题本、学习进度

data class ExamCategory(
    val id: String,
    val name: String,
    val icon: String,
    val color: Color
)

data class PracticeQuestion(
    val id: String,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String
)

// 题库：按分类 id 分组
private fun buildQuestionBank(): Map<String, List<PracticeQuestion>> = mapOf(
    "常识" to listOf(
        PracticeQuestion("c1", "常识", "中国古代四大发明中，哪一项与航海关系最密切？", listOf("造纸术", "印刷术", "指南针", "火药"), 2, "指南针最早应用于航海导航，是四大发明中与航海关系最密切的一项。"),
        PracticeQuestion("c2", "常识", "我国的最高国家权力机关是？", listOf("国务院", "全国人民代表大会", "最高人民法院", "中央军委"), 1, "《宪法》规定，全国人民代表大会是最高国家权力机关。"),
        PracticeQuestion("c3", "常识", "\"一带一路\"指的是\"丝绸之路经济带\"和？", listOf("21世纪海上丝绸之路", "中欧班列", "亚投行", "长江经济带"), 0, "一带一路即丝绸之路经济带和21世纪海上丝绸之路。"),
        PracticeQuestion("c4", "常识", "我国现行宪法最近一次修正是在哪一年？", listOf("2004年", "2014年", "2018年", "2020年"), 2, "现行宪法于2018年3月进行了第五次修正。"),
        PracticeQuestion("c5", "常识", "北京位于下列哪个平原？", listOf("东北平原", "华北平原", "长江中下游平原", "关中平原"), 1, "北京地处华北平原北部。")
    ),
    "言语" to listOf(
        PracticeQuestion("y1", "言语", "下列成语中，与\"刻苦学习\"最相近的是？", listOf("废寝忘食", "守株待兔", "掩耳盗铃", "画蛇添足"), 0, "废寝忘食形容专心努力，与刻苦学习意思最相近。"),
        PracticeQuestion("y2", "言语", "\"他这个人总是文过饰非\"中\"文过饰非\"的意思是？", listOf("文采飞扬", "掩饰自己的过失错误", "善于辞令", "铺张浪费"), 1, "文过饰非指用漂亮的言词掩饰自己的过失和错误。"),
        PracticeQuestion("y3", "言语", "填入：他的演讲（  ），赢得了阵阵掌声。", listOf("抑扬顿挫", "平平无奇", "语无伦次", "无精打采"), 0, "抑扬顿挫形容声音高低起伏、和谐悦耳，形容演讲恰当。"),
        PracticeQuestion("y4", "言语", "下列句子没有语病的是？", listOf("通过这次活动，使我们增进了友谊。", "通过这次活动，我们增进了友谊。", "这次活动，使我们友谊增进。", "这次活动，友谊被我们增进的。"), 1, "\"通过……使……\"会造成主语残缺，B 项主语完整无语病。")
    ),
    "数量" to listOf(
        PracticeQuestion("s1", "数量", "一个数除以3余2，除以5余3，除以7余2，这个数最小是多少？", listOf("23", "17", "31", "37"), 0, "使用中国剩余定理求解，最小解为23。"),
        PracticeQuestion("s2", "数量", "甲乙两地相距240公里，汽车以每小时60公里的速度行驶，需要几小时？", listOf("3", "4", "5", "6"), 1, "时间 = 路程 ÷ 速度 = 240 ÷ 60 = 4 小时。"),
        PracticeQuestion("s3", "数量", "一件衣服原价200元，打八折后售价是多少？", listOf("140元", "150元", "160元", "180元"), 2, "八折即原价的80%：200 × 0.8 = 160 元。"),
        PracticeQuestion("s4", "数量", "5个人每两人互相握手一次，共握手多少次？", listOf("8", "10", "12", "20"), 1, "C(5,2) = 5×4÷2 = 10 次。")
    ),
    "判断" to listOf(
        PracticeQuestion("p1", "判断", "\"所有金属都导电，铜是金属，所以铜导电\"属于什么推理？", listOf("归纳推理", "三段论推理", "类比推理", "因果推理"), 1, "由一般性前提推出个别结论，是典型的三段论（演绎）推理。"),
        PracticeQuestion("p2", "判断", "\"如果下雨，地面就会湿。地面没湿，所以没下雨\"是什么推理？", listOf("逆否推理", "肯定前件", "归纳推理", "类比推理"), 0, "由否定后件推出否定前件，是逆否命题的有效推理。"),
        PracticeQuestion("p3", "判断", "从\"有些学生是党员\"可以推出？", listOf("所有党员是学生", "有些党员是学生", "所有学生是党员", "没有学生是党员"), 1, "\"有些A是B\"可换位为\"有些B是A\"，故有些党员是学生。"),
        PracticeQuestion("p4", "判断", "类比：苹果 : 水果 = 咖啡 : ？", listOf("饮料", "咖啡豆", "提神", "杯子"), 0, "苹果属于水果，咖啡属于饮料，均为种属关系。")
    ),
    "资料" to listOf(
        PracticeQuestion("d1", "资料", "某公司2024年营收100万，2025年营收120万，增长率是多少？", listOf("12%", "20%", "25%", "120%"), 1, "增长率 = (120-100)÷100 = 20%。"),
        PracticeQuestion("d2", "资料", "某班50人，及格40人，及格率是多少？", listOf("70%", "75%", "80%", "85%"), 2, "及格率 = 40÷50 = 80%。"),
        PracticeQuestion("d3", "资料", "某市人口从100万增长到110万，增长了百分之几？", listOf("5%", "10%", "11%", "15%"), 1, "增长率 = (110-100)÷100 = 10%。"),
        PracticeQuestion("d4", "资料", "某图表中某项占比25%，总量为200，该项数量是多少？", listOf("25", "40", "50", "75"), 2, "200 × 25% = 50。")
    )
)

@Composable
fun ExamPrepPage() {
    var selectedTab by remember { mutableStateOf("daily") } // daily, practice, wrong, progress
    var dailyCompleted by remember { mutableStateOf(false) }
    var dailyScore by remember { mutableStateOf<Int?>(null) }

    // 状态提升到顶层：切 tab 不丢
    var wrongQuestions by remember { mutableStateOf(listOf<PracticeQuestion>()) }
    var totalAnswered by remember { mutableIntStateOf(0) }
    var totalCorrect by remember { mutableIntStateOf(0) }
    var categoryStats by remember { mutableStateOf<Map<String, Pair<Int, Int>>>(emptyMap()) }
    // 当前正在刷的分类（null = 分类列表）
    var practiceCategory by remember { mutableStateOf<ExamCategory?>(null) }

    val categories = listOf(
        ExamCategory("常识", "常识判断", "📚", Color(0xFF4CAF50)),
        ExamCategory("言语", "言语理解", "📝", Color(0xFF2196F3)),
        ExamCategory("数量", "数量关系", "🔢", Color(0xFFFF9800)),
        ExamCategory("判断", "判断推理", "🧩", Color(0xFF9C27B0)),
        ExamCategory("资料", "资料分析", "📊", Color(0xFFF44336))
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
                question = "下列成语中，与「刻苦学习」最相近的是？",
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

    val questionBank = remember { buildQuestionBank() }

    // 记录一次答题
    fun recordAnswer(question: PracticeQuestion, correct: Boolean) {
        totalAnswered += 1
        if (correct) totalCorrect += 1
        val stat = categoryStats[question.category] ?: (0 to 0)
        categoryStats = categoryStats + (question.category to (stat.first + 1 to stat.second + if (correct) 1 else 0))
        if (!correct && wrongQuestions.none { it.id == question.id }) {
            wrongQuestions = wrongQuestions + question
        }
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
                    onClick = {
                        selectedTab = when (tab) {
                            "每日一练" -> "daily"
                            "分类刷题" -> "practice"
                            "错题本" -> "wrong"
                            "学习进度" -> "progress"
                            else -> selectedTab
                        }
                        if (tab != "分类刷题") practiceCategory = null
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(text = tab, color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // 内容区域
        when (selectedTab) {
            "daily" -> DailyPracticeTab(
                questions = dailyQuestions,
                completed = dailyCompleted,
                score = dailyScore,
                onAnswer = { q, c -> recordAnswer(q, c) },
                onComplete = { score ->
                    dailyCompleted = true
                    dailyScore = score
                },
                onRestart = {
                    dailyCompleted = false
                    dailyScore = null
                }
            )

            "practice" -> {
                val cat = practiceCategory
                if (cat == null) {
                    PracticeTab(
                        categories = categories,
                        categoryStats = categoryStats,
                        onSelectCategory = { practiceCategory = it }
                    )
                } else {
                    QuestionRunner(
                        title = "${cat.icon} ${cat.name}",
                        questions = questionBank[cat.id] ?: emptyList(),
                        onAnswer = { q, c -> recordAnswer(q, c) },
                        onExit = { practiceCategory = null }
                    )
                }
            }

            "wrong" -> WrongAnswersTab(
                wrongQuestions = wrongQuestions,
                onClear = { wrongQuestions = emptyList() }
            )

            "progress" -> ProgressTab(
                totalAnswered = totalAnswered,
                totalCorrect = totalCorrect,
                categoryStats = categoryStats
            )
        }
    }
}

// ── 通用刷题组件（分类刷题 / 错题重做共用）────────────────────
@Composable
private fun QuestionRunner(
    title: String,
    questions: List<PracticeQuestion>,
    onAnswer: (PracticeQuestion, Boolean) -> Unit,
    onExit: () -> Unit,
    showExit: Boolean = true
) {
    var currentIndex by remember(questions) { mutableStateOf(0) }
    var selectedAnswer by remember(questions) { mutableStateOf<Int?>(null) }
    var showExplanation by remember(questions) { mutableStateOf(false) }
    var correctCount by remember(questions) { mutableIntStateOf(0) }
    var finished by remember(questions) { mutableStateOf(false) }

    if (questions.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "这个分类还没有题目", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (showExit) {
                OutlinedButton(onClick = onExit) { Text("返回") }
            }
        }

        if (finished) {
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "本轮完成！", style = MaterialTheme.typography.headlineSmall)
                    Text(text = "得分: $correctCount / ${questions.size}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = {
                        currentIndex = 0
                        selectedAnswer = null
                        showExplanation = false
                        correctCount = 0
                        finished = false
                    }) {
                        Text("再来一轮")
                    }
                }
            }
            return
        }

        val question = questions[currentIndex]
        Card(
            shape = CardShape,
            modifier = Modifier.fillMaxWidth(),
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

                question.options.forEachIndexed { index, option ->
                    val isSelected = selectedAnswer == index
                    val isCorrect = index == question.correctAnswer
                    Card(
                        shape = CardShape,
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                showExplanation && isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                showExplanation && isSelected && !isCorrect -> Color(0xFFF44336).copy(alpha = 0.2f)
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

                if (showExplanation) {
                    Card(
                        shape = CardShape,
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
                    ) {
                        Text(
                            text = "解析: ${question.explanation}",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!showExplanation) {
                        Button(
                            onClick = {
                                selectedAnswer?.let { ans ->
                                    val correct = ans == question.correctAnswer
                                    if (correct) correctCount += 1
                                    onAnswer(question, correct)
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
                                currentIndex += 1
                                selectedAnswer = null
                                showExplanation = false
                            } else {
                                finished = true
                            }
                        }) {
                            Text(if (currentIndex < questions.size - 1) "下一题" else "完成")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyPracticeTab(
    questions: List<PracticeQuestion>,
    completed: Boolean,
    score: Int?,
    onAnswer: (PracticeQuestion, Boolean) -> Unit,
    onComplete: (Int) -> Unit,
    onRestart: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (completed) {
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "今日完成！", style = MaterialTheme.typography.headlineSmall)
                    Text(text = "得分: $score / ${questions.size}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        currentIndex = 0
                        selectedAnswer = null
                        showExplanation = false
                        correctCount = 0
                        onRestart()
                    }) {
                        Text("再来一次")
                    }
                }
            }
        } else if (currentIndex < questions.size) {
            val question = questions[currentIndex]
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
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

                    question.options.forEachIndexed { index, option ->
                        val isSelected = selectedAnswer == index
                        val isCorrect = index == question.correctAnswer
                        Card(
                            shape = CardShape,
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    showExplanation && isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                    showExplanation && isSelected && !isCorrect -> Color(0xFFF44336).copy(alpha = 0.2f)
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

                    if (showExplanation) {
                        Card(
                            shape = CardShape,
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
                        ) {
                            Text(
                                text = "解析: ${question.explanation}",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!showExplanation) {
                            Button(
                                onClick = {
                                    selectedAnswer?.let { ans ->
                                        val correct = ans == question.correctAnswer
                                        if (correct) correctCount += 1
                                        onAnswer(question, correct)
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
                                    currentIndex += 1
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
        }
    }
}

@Composable
private fun PracticeTab(
    categories: List<ExamCategory>,
    categoryStats: Map<String, Pair<Int, Int>>,
    onSelectCategory: (ExamCategory) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { cat ->
            val stat = categoryStats[cat.id]
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "${cat.icon} ${cat.name}", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (stat != null) "已练 ${stat.first} 题 · 正确 ${stat.second} 题" else "点击开始刷题",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(onClick = { onSelectCategory(cat) }) {
                        Text("开始练习")
                    }
                }
            }
        }
    }
}

@Composable
private fun WrongAnswersTab(
    wrongQuestions: List<PracticeQuestion>,
    onClear: () -> Unit
) {
    if (wrongQuestions.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "📝", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            Text(text = "暂无错题", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = "做错的题目会自动收录到这里", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "共 ${wrongQuestions.size} 道错题", style = MaterialTheme.typography.titleMedium)
                OutlinedButton(onClick = onClear) { Text("清空") }
            }
        }
        items(wrongQuestions) { q ->
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = q.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = q.question, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "正确答案: ${q.options.getOrNull(q.correctAnswer) ?: "-"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4CAF50)
                    )
                    Text(
                        text = "解析: ${q.explanation}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressTab(
    totalAnswered: Int,
    totalCorrect: Int,
    categoryStats: Map<String, Pair<Int, Int>>
) {
    val accuracy = if (totalAnswered > 0) {
        "${(totalCorrect * 100 / totalAnswered)}%"
    } else "--"

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "学习进度", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(text = "已练习: $totalAnswered 题", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "答对: $totalCorrect 题", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "正确率: $accuracy", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        items(listOf("常识判断", "言语理解", "数量关系", "判断推理", "资料分析")) { categoryName ->
            val catId = when (categoryName) {
                "常识判断" -> "常识"
                "言语理解" -> "言语"
                "数量关系" -> "数量"
                "判断推理" -> "判断"
                else -> "资料"
            }
            val stat = categoryStats[catId]
            Card(
                shape = CardShape,
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = categoryName, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = if (stat != null) "已练 ${stat.first} 题 · 正确率 ${if (stat.first > 0) "${stat.second * 100 / stat.first}%" else "--"}" else "进度: 0%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}