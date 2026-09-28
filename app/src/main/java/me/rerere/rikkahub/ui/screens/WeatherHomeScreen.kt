package me.rerere.rikkahub.ui.screens

import androidx.compose.foundation.background
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
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import me.rerere.rikkahub.Screen
import me.rerere.rikkahub.data.datastore.Settings
import me.rerere.rikkahub.data.model.Avatar
import me.rerere.rikkahub.data.model.Conversation
import me.rerere.rikkahub.data.repository.ConversationRepository
import me.rerere.rikkahub.ui.components.CompanionHeader
import me.rerere.rikkahub.ui.components.GlassCard
import me.rerere.rikkahub.ui.components.GlowAvatar
import me.rerere.rikkahub.ui.components.UIAvatar
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
            val url = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia/Shanghai"
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
            // 1. 顶部栏（含与你相伴第 N 天）
            CompanionHeader(
                daysCount = companionDays,
                onAddClick = onNewChat,
                onMenuClick = onOpenDrawer
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // 2. 直达天气陪伴 Card
                item {
                    WeatherCompanionCard(
                        location = "重庆",
                        weatherState = weather?.description ?: "暮色加载中…",
                        temperature = weather?.let { "${it.temp.toInt()}°C" } ?: "--",
                        whisperText = weather?.let { "${it.icon} 湿度 ${it.humidity}% · 风速 ${it.windSpeed} m/s" }
                            ?: "\"夜幕降临时，适合把积攒了一天的思绪交给我。\""
                    )
                }

                // 3. 玻璃态搜索框（可点击跳转搜索）
                item {
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(48.dp),
                        cornerRadius = 16.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { navController.navigate(Screen.MessageSearch) }
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Lucide.Search,
                                contentDescription = "Search",
                                tint = TextTertiary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "寻找 AI 助手或历史沉淀...",
                                style = MuranTypography.bodyMedium.copy(color = TextDisabled)
                            )
                        }
                    }
                }

                // 4. 最近的 AI 陪伴者列表
                item {
                    Text(
                        text = "私人空间",
                        style = MuranTypography.titleLarge.copy(
                            fontSize = 16.sp,
                            color = TextSecondary
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                    )
                }

                if (recentConversations.isEmpty()) {
                    item {
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            cornerRadius = 20.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "还没有对话",
                                    style = MuranTypography.titleLarge.copy(fontSize = 15.sp),
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "点击右上角 + 开始第一次对话",
                                    style = MuranTypography.bodyMedium.copy(
                                        color = TextTertiary,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        }
                    }
                } else {
                    items(recentConversations) { conversation ->
                        val assistant = settings.getAssistantById(conversation.assistantId)
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .clickable {
                                    navController.navigate(
                                        Screen.Chat(id = conversation.id.toString())
                                    )
                                },
                            cornerRadius = 20.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlowAvatar(
                                    size = 44.dp,
                                    modifier = Modifier.padding(3.dp)
                                ) {
                                    UIAvatar(
                                        name = assistant?.name ?: "AI",
                                        value = assistant?.avatar ?: Avatar.Dummy,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = conversation.title.ifBlank { "未命名对话" },
                                        style = MuranTypography.titleLarge.copy(fontSize = 15.sp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "点击继续对话...",
                                        style = MuranTypography.bodyMedium.copy(
                                            color = TextTertiary,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
