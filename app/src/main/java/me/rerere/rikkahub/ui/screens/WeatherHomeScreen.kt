package me.rerere.rikkahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.rerere.hugeicons.HugeIcons
import me.rerere.hugeicons.stroke.ArrowRight01
import me.rerere.hugeicons.stroke.BookOpen01
import me.rerere.hugeicons.stroke.ChartColumn
import me.rerere.hugeicons.stroke.CloudServer
import me.rerere.hugeicons.stroke.Image02
import me.rerere.hugeicons.stroke.LanguageCircle
import me.rerere.hugeicons.stroke.Message01
import me.rerere.hugeicons.stroke.Settings03
import me.rerere.hugeicons.stroke.AppStore
import me.rerere.hugeicons.stroke.Sparkles
import me.rerere.rikkahub.Screen
import me.rerere.rikkahub.data.datastore.Settings
import me.rerere.rikkahub.data.datastore.getAssistantById
import me.rerere.rikkahub.data.model.Avatar
import me.rerere.rikkahub.data.model.Conversation
import me.rerere.rikkahub.data.repository.ConversationRepository
import me.rerere.rikkahub.ui.components.CompanionHeader
import me.rerere.rikkahub.ui.components.GlassCard
import me.rerere.rikkahub.ui.components.GlowAvatar
import me.rerere.rikkahub.ui.components.ui.UIAvatar
import me.rerere.rikkahub.ui.components.WeatherCompanionCard
import me.rerere.rikkahub.ui.context.LocalNavController
import me.rerere.rikkahub.ui.context.LocalSettings
import me.rerere.rikkahub.ui.pages.weather.WeatherData
import me.rerere.rikkahub.ui.pages.weather.weatherCodeToDescription
import me.rerere.rikkahub.ui.pages.weather.weatherCodeToEmoji
import me.rerere.rikkahub.ui.theme.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.koin.compose.koinInject
import kotlin.uuid.Uuid

@Composable
fun WeatherHomeScreen(
    companionDays: Int = 1,
    onOpenDrawer: () -> Unit = {},
    onNewChat: () -> Unit = {}
) {
    val client = koinInject<OkHttpClient>()
    val conversationRepository = koinInject<ConversationRepository>()
    val settings = LocalSettings.current
    val navController = LocalNavController.current

    var weather by remember { mutableStateOf<WeatherData?>(null) }
    var weatherError by remember { mutableStateOf<String?>(null) }
    var recentConversations by remember { mutableStateOf<List<Conversation>>(emptyList()) }
    var currentPage by remember { mutableIntStateOf(0) }
    var pageOffset by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        try {
            val lat = 29.5630
            val lon = 106.5516
            val url = "http://106.53.203.40/weather/v1/forecast?latitude=$lat&longitude=$lon&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia/Shanghai"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                val json = JSONObject(body)
                val current = json.getJSONObject("current")
                weather = WeatherData(
                    temp = current.getDouble("temperature_2m"),
                    humidity = current.getInt("relative_humidity_2m"),
                    windSpeed = current.getDouble("wind_speed_10m"),
                    description = weatherCodeToDescription(current.getInt("weather_code")),
                    icon = weatherCodeToEmoji(current.getInt("weather_code"))
                )
                weatherError = null
            } else {
                weatherError = "天气服务暂时不可用"
            }
        } catch (_: Exception) {
            weatherError = "无法获取天气数据"
        }
    }

    LaunchedEffect(settings.assistantId) {
        if (settings.assistantId != Uuid.random()) {
            recentConversations = try {
                conversationRepository.getRecentConversations(
                    assistantId = settings.assistantId,
                    limit = 5
                )
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {
        val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }

        Column(modifier = Modifier.fillMaxSize()) {
            // 1. 顶部栏
            CompanionHeader(
                daysCount = companionDays,
                onAddClick = onNewChat,
                onMenuClick = onOpenDrawer
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. 双页滑动容器
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                pageOffset = (pageOffset + dragAmount).coerceIn(
                                    if (currentPage == 0) -maxWidthPx / 3f else -maxWidthPx * 2 / 3f,
                                    if (currentPage == 0) maxWidthPx / 3f else maxWidthPx * 2 / 3f
                                )
                            },
                            onDragEnd = {
                                if (pageOffset > maxWidthPx / 4) {
                                    currentPage = 0
                                } else if (pageOffset < -maxWidthPx / 4) {
                                    currentPage = 1
                                }
                                pageOffset = 0f
                            }
                        )
                    }
            ) {
                // Page 1: 天气 + 最近对话
                val page1X = when (currentPage) {
                    0 -> pageOffset
                    1 -> -maxWidthPx + pageOffset
                    else -> pageOffset
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { androidx.compose.ui.unit.IntOffset(page1X.toInt(), 0) }
                        .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        WeatherCompanionCard(
                            location = "重庆",
                            weatherState = weather?.description ?: "暮色加载中…",
                            temperature = weather?.let { "${it.temp.toInt()}°C" } ?: "--",
                            whisperText = weather?.let { "${it.icon} 湿度 ${it.humidity}% · 风速 ${it.windSpeed} m/s" }
                                ?: "夜幕降临时，适合把积攒了一天的思绪交给我。"
                        )
                    }

                    item {
                        Text(
                            text = "最近对话",
                            style = MuranTypography.titleLarge.copy(
                                fontSize = 15.sp,
                                color = TextSecondary
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    if (recentConversations.isEmpty()) {
                        item {
                            Text(
                                text = "还没有对话，点击「新对话」开始",
                                style = MuranTypography.bodyMedium.copy(color = TextTertiary, fontSize = 13.sp),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    } else {
                        items(recentConversations) { conversation ->
                            val assistant = settings.getAssistantById(conversation.assistantId)
                            GlassCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screen.Chat(id = conversation.id.toString())
                                        )
                                    },
                                cornerRadius = 16.dp
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    GlowAvatar(size = 36.dp, modifier = Modifier.padding(2.dp)) {
                                        UIAvatar(
                                            name = assistant?.name ?: "AI",
                                            value = assistant?.avatar ?: Avatar.Dummy,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = conversation.title.ifBlank { "未命名对话" },
                                            style = MuranTypography.titleLarge.copy(fontSize = 14.sp)
                                        )
                                    }
                                    Icon(
                                        imageVector = HugeIcons.ArrowRight01,
                                        contentDescription = null,
                                        tint = TextTertiary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Page 2: 工具分类
                val page2X = when (currentPage) {
                    0 -> maxWidthPx + pageOffset
                    1 -> pageOffset
                    else -> maxWidthPx + pageOffset
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { androidx.compose.ui.unit.IntOffset(page2X.toInt(), 0) }
                        .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ToolCategoryCard(
                            title = "核心",
                            items = listOf(
                                ToolItem("新对话", HugeIcons.Message01) { onNewChat() },
                                ToolItem("助手", HugeIcons.Sparkles) { navController.navigate(Screen.Assistant) }
                            )
                        )
                    }
                    item {
                        ToolCategoryCard(
                            title = "创作",
                            items = listOf(
                                ToolItem("翻译", HugeIcons.LanguageCircle) { navController.navigate(Screen.Translator) },
                                ToolItem("图片", HugeIcons.Image02) { navController.navigate(Screen.ImageGen) }
                            )
                        )
                    }
                    item {
                        ToolCategoryCard(
                            title = "生活",
                            items = listOf(
                                ToolItem("记账", HugeIcons.ChartColumn) { navController.navigate(Screen.Accounting) },
                                ToolItem("天气", HugeIcons.CloudServer) { navController.navigate(Screen.Weather) }
                            )
                        )
                    }
                    item {
                        ToolCategoryCard(
                            title = "学习与系统",
                            items = listOf(
                                ToolItem("考公", HugeIcons.BookOpen01) { navController.navigate(Screen.ExamPrep) },
                                ToolItem("插件", HugeIcons.AppStore) { navController.navigate(Screen.PluginMarket) },
                                ToolItem("统计", HugeIcons.ChartColumn) { navController.navigate(Screen.Stats) },
                                ToolItem("设置", HugeIcons.Settings03) { navController.navigate(Screen.Setting) }
                            )
                        )
                    }
                }
            }

            // 3. 页面指示器
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (currentPage == index) 24.dp else 8.dp, 8.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(
                                if (currentPage == index) WarmGoldMain else TwilightPurpleMain.copy(alpha = 0.4f)
                            )
                            .clickable { currentPage = index }
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolCategoryCard(
    title: String,
    items: List<ToolItem>
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MuranTypography.titleLarge.copy(
                    fontSize = 14.sp,
                    color = WarmGoldMain
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(GlassSurface)
                            .clickable(onClick = item.onClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = WarmGoldMain,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                text = item.title,
                                style = MuranTypography.bodyMedium.copy(fontSize = 11.sp),
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class ToolItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
