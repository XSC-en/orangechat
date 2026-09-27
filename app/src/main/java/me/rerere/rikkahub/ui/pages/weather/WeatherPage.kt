package me.rerere.rikkahub.ui.pages.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.koin.compose.koinInject
import me.rerere.rikkahub.ui.theme.CardShape
import me.rerere.rikkahub.data.datastore.SettingsStore
import androidx.compose.foundation.layout.Row

@Composable
fun WeatherPage() {
    val client = koinInject<OkHttpClient>()
    var weather by remember { mutableStateOf<WeatherData?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val settingsStore = koinInject<SettingsStore>()
    val companionDays = remember {
        val t = settingsStore.settingsFlow.value.firstLaunchTime
        if (t > 0) ((System.currentTimeMillis() - t) / 86400000L).toInt() + 1 else 1
    }

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
                val temp = current.getDouble("temperature_2m")
                val humidity = current.getInt("relative_humidity_2m")
                val code = current.getInt("weather_code")
                val wind = current.getDouble("wind_speed_10m")
                weather = WeatherData(
                    temp = temp,
                    humidity = humidity,
                    windSpeed = wind,
                    description = weatherCodeToDescription(code),
                    icon = weatherCodeToEmoji(code)
                )
            } else {
                error = "天气请求失败: ${response.code}"
            }
        } catch (e: Exception) {
            error = "网络错误: ${e.message}"
        } finally {
            loading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "天气", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "与你相伴第 $companionDays 天",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        when {
            loading -> Text(text = "加载中...")
            error != null -> Text(text = error!!, color = MaterialTheme.colorScheme.error)
            else -> {
                weather?.let { data ->
                    Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = "重庆", style = MaterialTheme.typography.titleLarge)
                            Text(text = "${data.icon} ${data.description}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "${data.temp}°C", style = MaterialTheme.typography.displayMedium)
                            Text(text = "湿度 ${data.humidity}%", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "风速 ${data.windSpeed} m/s", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

data class WeatherData(
    val temp: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String
)

fun weatherCodeToDescription(code: Int): String = when (code) {
    0 -> "晴"
    1 -> "大部晴朗"
    2 -> "多云"
    3 -> "阴天"
    45, 48 -> "雾"
    51 -> "小毛毛雨"
    53 -> "中毛毛雨"
    55 -> "大毛毛雨"
    61 -> "小雨"
    63 -> "中雨"
    65 -> "大雨"
    71 -> "小雪"
    73 -> "中雪"
    75 -> "大雪"
    77 -> "雪粒"
    80 -> "小阵雨"
    81 -> "中阵雨"
    82 -> "大阵雨"
    85 -> "小阵雪"
    86 -> "大阵雪"
    95 -> "雷暴"
    96 -> "雷暴+小冰雹"
    99 -> "雷暴+大冰雹"
    else -> "未知"
}

fun weatherCodeToEmoji(code: Int): String = when (code) {
    0 -> "☀️"
    1, 2 -> "🌤"
    3 -> "☁️"
    45, 48 -> "🌫️"
    51, 53, 55 -> "🌦"
    61, 63, 65, 80, 81, 82 -> "🌧"
    71, 73, 75, 77, 85, 86 -> "❄️"
    95, 96, 99 -> "⛈"
    else -> "🌡"
}
