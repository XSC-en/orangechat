package me.rerere.rikkahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.rerere.hugeicons.HugeIcons
import me.rerere.hugeicons.stroke.Message01
import me.rerere.hugeicons.stroke.Sparkles
import me.rerere.hugeicons.stroke.LanguageCircle
import me.rerere.hugeicons.stroke.Image02
import me.rerere.hugeicons.stroke.ChartColumn
import me.rerere.hugeicons.stroke.CloudServer
import me.rerere.hugeicons.stroke.BookOpen01
import me.rerere.hugeicons.stroke.AppStore
import me.rerere.hugeicons.stroke.Settings03
import me.rerere.hugeicons.stroke.ChevronRight
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
    var recentConversations by remember { mutableStateOf<List<Conversation>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val lat = 29.5630
            val lon = 106.5516
            // 走自建服务器反代：open-meteo 直连在国内约 6 秒会超时
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
            }
        } catch (_: Exception) { }
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. 顶部栏
            CompanionHeader(
                daysCount = companionDays,
                onAddClick = onNewChat,
                onMenuClick = onOpenDrawer
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, bottom = 24.dp)
            ) {
                // 2. 天气陪伴 Card
                item {
                    WeatherCompanionCard(
                        location = "重庆",
                        weatherState = weather?.description ?: "暮色加载中…",
                        temperature = weather?.let { "${it.temp.toInt()}°C" } ?: "--",
                        whisperText = weather?.let { "${it.icon} 湿度 ${it.humidity}% · 风速 ${it.windSpeed} m/s" }
                            ?: "夜幕降临时，适合把积攒了一天的思绪交给我。"
                    )
                }

                // 3. 工具网格（2 列）
                item {
                    val tools = listOf(
                        Triple("新对话", Message01, { onNewChat() }),
                        Triple("助手", Sparkles, { navController.navigate(Screen.Assistant) }),
                        Triple("翻译", LanguageCircle, { navController.navigate(Screen.Translator) }),
                        Triple("图片", Image02, { navController.navigate(Screen.ImageGen) }),
                        Triple("记账", ChartColumn, { navController.navigate(Screen.Accounting) }),
                        Triple("天气", CloudServer, { navController.navigate(Screen.Weather) }),
                        Triple("考公", BookOpen01, { navController.navigate(Screen.ExamPrep) }),
                        Triple("插件", AppStore, { navController.navigate(Screen.PluginMarket) }),
                        Triple("统计", ChartColumn, { navController.navigate(Screen.Stats) }),
                        Triple("设置", Settings03, { navController.navigate(Screen.Setting) }),
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (i in tools.indices step 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                ToolCard(
                                    title = tools[i].component1(),
                                    icon = tools[i].component2(),
                                    onClick = tools[i].component3(),
                                    modifier = Modifier.weight(1f)
                                )
                                if (i + 1 < tools.size) {
                                    ToolCard(
                                        title = tools[i + 1].component1(),
                                        icon = tools[i + 1].component2(),
                                        onClick = tools[i + 1].component3(),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }

                // 4. 最近对话（小卡片）
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
                                    imageVector = ChevronRight,
                                    contentDescription = null,
                                    tint = TextTertiary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun ToolCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        cornerRadius = 18.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFB07D4F),
                modifier = Modifier.size(26.dp)
            )
            Text(
                text = title,
                style = MuranTypography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFF5A4C3E)
            )
        }
    }
}
