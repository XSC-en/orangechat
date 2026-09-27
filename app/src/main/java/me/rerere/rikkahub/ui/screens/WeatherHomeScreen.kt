package me.rerere.rikkahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import me.rerere.rikkahub.ui.components.CompanionHeader
import me.rerere.rikkahub.ui.components.GlassCard
import me.rerere.rikkahub.ui.components.GlowAvatar
import me.rerere.rikkahub.ui.components.WeatherCompanionCard
import me.rerere.rikkahub.ui.pages.weather.WeatherData
import me.rerere.rikkahub.ui.pages.weather.weatherCodeToDescription
import me.rerere.rikkahub.ui.pages.weather.weatherCodeToEmoji
import me.rerere.rikkahub.ui.theme.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.koin.compose.koinInject

@Composable
fun WeatherHomeScreen(
    companionDays: Int = 1,
    onOpenDrawer: () -> Unit = {},
    onNewChat: () -> Unit = {}
) {
    val client = koinInject<OkHttpClient>()
    var weather by remember { mutableStateOf<WeatherData?>(null) }

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

                // 3. 玻璃态搜索框
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

                items(3) { index ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        cornerRadius = 20.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GlowAvatar(size = 44.dp) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(TwilightPurpleDark)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = if (index == 0) "暮暗思考者" else "对话空间 ${index + 1}",
                                    style = MuranTypography.titleLarge.copy(fontSize = 15.sp)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "\"夕阳褪去时，我们在这里倾听。\"",
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
