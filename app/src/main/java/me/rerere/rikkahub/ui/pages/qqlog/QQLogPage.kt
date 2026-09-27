package me.rerere.rikkahub.ui.pages.qqlog

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import me.rerere.rikkahub.ui.theme.CardShape

// QQ 日志数据模型
data class QQLogEntry(
    val id: String,
    val timestamp: Long,
    val botName: String,
    val message: String,
    val reasoning: String?,
    val model: String,
    val inputTokens: Int,
    val outputTokens: Int,
    val totalTokens: Int
)

@Composable
fun QQLogPage() {
    var logs by remember { mutableStateOf(listOf<QQLogEntry>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showImportHint by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "QQ 日志",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        // 操作按钮
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showAddDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text("➕ 添加日志")
            }
            Button(onClick = { showImportHint = true }, modifier = Modifier.fillMaxWidth()) {
                Text("📂 导入 JSONL")
            }
        }

        if (logs.isEmpty()) {
            Text(
                text = "暂无日志，请添加或导入",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(logs) { log ->
                    Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // 头部：时间 + 模型
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(log.timestamp)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = log.model,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // 消息内容
                            Text(
                                text = "[${log.botName}] ${log.message}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            // 思考链
                            if (!log.reasoning.isNullOrBlank()) {
                                Text(
                                    text = "💭 ${log.reasoning}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }

                            // Token 统计
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "输入: ${log.inputTokens}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "输出: ${log.outputTokens}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "总计: ${log.totalTokens}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // 添加日志弹窗
    if (showAddDialog) {
        AddLogDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { entry ->
                logs = logs + entry
                showAddDialog = false
            }
        )
    }

    // 导入提示
    if (showImportHint) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showImportHint = false },
            title = { Text("导入 JSONL") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("请将 JSONL 文件放到手机存储的 Documents/QQLogs/ 目录下，然后在设置中启用自动导入。")
                    Text("或使用下方格式手动添加：", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "{\"botName\":\"沉\",\"message\":\"...\",\"reasoning\":\"...\",\"model\":\"gpt-4o\",\"inputTokens\":100,\"outputTokens\":50}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showImportHint = false }) {
                    Text("知道了")
                }
            }
        )
    }
}

@Composable
private fun AddLogDialog(
    onDismiss: () -> Unit,
    onConfirm: (QQLogEntry) -> Unit
) {
    var botName by remember { mutableStateOf("沉") }
    var message by remember { mutableStateOf("") }
    var reasoning by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("gpt-4o") }
    var inputTokens by remember { mutableStateOf("") }
    var outputTokens by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加日志") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                androidx.compose.material3.OutlinedTextField(
                    value = botName,
                    onValueChange = { botName = it },
                    label = { Text("机器人名称") },
                    modifier = Modifier.fillMaxWidth()
                )
                androidx.compose.material3.OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("消息内容") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                androidx.compose.material3.OutlinedTextField(
                    value = reasoning,
                    onValueChange = { reasoning = it },
                    label = { Text("思考链（可选）") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                androidx.compose.material3.OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("模型") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    androidx.compose.material3.OutlinedTextField(
                        value = inputTokens,
                        onValueChange = { inputTokens = it },
                        label = { Text("输入Token") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )
                    androidx.compose.material3.OutlinedTextField(
                        value = outputTokens,
                        onValueChange = { outputTokens = it },
                        label = { Text("输出Token") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val inTokens = inputTokens.toIntOrNull() ?: 0
                val outTokens = outputTokens.toIntOrNull() ?: 0
                onConfirm(
                    QQLogEntry(
                        id = java.util.UUID.randomUUID().toString(),
                        timestamp = System.currentTimeMillis(),
                        botName = botName.ifBlank { "未知" },
                        message = message.ifBlank { "（空消息）" },
                        reasoning = reasoning.ifBlank { null },
                        model = model.ifBlank { "unknown" },
                        inputTokens = inTokens,
                        outputTokens = outTokens,
                        totalTokens = inTokens + outTokens
                    )
                )
            }) {
                Text("保存")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("取消") }
        }
    )
}
