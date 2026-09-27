package me.rerere.rikkahub.ui.pages.pluginmarket

import androidx.compose.foundation.layout.Row
import me.rerere.rikkahub.R

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.compose.koinInject
import java.io.File
import java.io.FileOutputStream
import me.rerere.rikkahub.ui.theme.CardShape

@Serializable
data class PluginInfo(
    val id: String,
    val name: String,
    val description: String,
    val version: String,
    val author: String,
    val icon: String
)

@Composable
fun PluginMarketPage() {
    val client = koinInject<OkHttpClient>()
    val scope = rememberCoroutineScope()
    var plugins by remember { mutableStateOf(listOf<PluginInfo>()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var installingId by remember { mutableStateOf<String?>(null) }
    val baseUrl = stringResource(id = R.string.plugin_market_base_url)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "插件市场", style = MaterialTheme.typography.headlineMedium)

        when {
            loading -> Text(text = "加载中...")
            error != null -> Text(text = error!!, color = MaterialTheme.colorScheme.error)
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(plugins) { plugin ->
                        Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "${plugin.icon} ${plugin.name}", style = MaterialTheme.typography.titleMedium)
                                    Text(text = "v${plugin.version}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text(text = plugin.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
                                Text(text = "作者: ${plugin.author}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                if (installingId == plugin.id) {
                                    Text(text = "安装中...", color = MaterialTheme.colorScheme.primary)
                                } else {
                                    Button(
                                        onClick = {
                                            installingId = plugin.id
                                            scope.launch {
                                                try {
                                                    withContext(Dispatchers.IO) {
                                                        val request = Request.Builder()
                                                            .url("${baseUrl}/plugin-market/api/plugins/${plugin.id}/download")
                                                            .build()
                                                        val response = client.newCall(request).execute()
                                                        if (response.isSuccessful) {
                                                            val bytes = response.body?.bytes()
                                                            if (bytes != null) {
                                                                val pluginDir = File("/data/data/me.rerere.rikkahub/plugins/${plugin.id}")
                                                                pluginDir.mkdirs()
                                                                val zipFile = File(pluginDir, "${plugin.id}.zip")
                                                                FileOutputStream(zipFile).use { it.write(bytes) }
                                                            }
                                                        }
                                                    }
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                } finally {
                                                    installingId = null
                                                }
                                            }
                                        }
                                    ) {
                                        Text("安装")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            val request = Request.Builder()
                .url("${baseUrl}/plugin-market/api/plugins")
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                val json = Json { ignoreUnknownKeys = true }
                val wrapper = json.decodeFromString(PluginListResponse.serializer(), body)
                plugins = wrapper.plugins
            } else {
                error = "加载失败: ${response.code}"
            }
        } catch (e: Exception) {
            error = "网络错误: ${e.message}"
        } finally {
            loading = false
        }
    }
}

@Serializable
data class PluginListResponse(
    val plugins: List<PluginInfo>,
    val count: Int
)
