/*
 * 橘瓣 OrangeChat
 * 衍生自 RikkaHub (https://github.com/rikkahub/rikkahub)，原作者 RE
 * 本项目基于 GNU AGPL v3 开源，详见根目录 LICENSE 文件
 */

package me.rerere.rikkahub.ui.pages.toolbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.rerere.hugeicons.HugeIcons
import me.rerere.hugeicons.stroke.Search01
import me.rerere.rikkahub.ui.context.LocalNavController
import me.rerere.rikkahub.ui.context.Navigator

@Composable
fun TwilightToolboxDrawer(
    onDismiss: () -> Unit
) {
    val navController = LocalNavController.current
    var searchQuery by remember { mutableStateOf("") }

    val toolCategories = remember {
        listOf(
            ToolCategory(
                name = "开发工具",
                items = listOf(
                    ToolItem("JSON解析", "🔍", "https://json.org"),
                    ToolItem("时间戳", "⏰", "https://timestamp.rikkahub.app"),
                )
            ),
            ToolCategory(
                name = "文本处理",
                items = listOf(
                    ToolItem("字数统计", "📝", ""),
                    ToolItem("Markdown", "✍️", ""),
                )
            )
        )
    }

    val filteredCategories = remember(searchQuery, toolCategories) {
        if (searchQuery.isBlank()) return@remember toolCategories
        val q = searchQuery.lowercase()
        toolCategories.map { cat ->
            cat.copy(
                items = cat.items.filter { it.name.lowercase().contains(q) }
            )
        }.filter { it.items.isNotEmpty() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202536))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 头部
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "工具箱",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color(0xFFE7CFA8)
                    )
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "关闭",
                        tint = Color(0xFF9695A2),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 搜索框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "搜索工具...",
                        color = Color(0xFF737582),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = HugeIcons.Search01,
                        contentDescription = null,
                        tint = Color(0xFF9695A2),
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(22.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF252A3D),
                    unfocusedContainerColor = Color(0xFF252A3D),
                    focusedIndicatorColor = Color(0xFFD8B98A).copy(alpha = 0.3f),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color(0xFFF0EDF1),
                    unfocusedTextColor = Color(0xFFF0EDF1),
                    cursorColor = Color(0xFFD8B98A)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 工具列表
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredCategories.size) { index ->
                    val category = filteredCategories[index]
                    Column {
                        // 分类标题
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFA89BCB),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // 2列网格
                        val items = category.items
                        for (i in items.indices step 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ToolGridItem(
                                    item = items[i],
                                    onClick = {
                                        if (items[i].url.isNotBlank()) {
                                            navController.navigate(Screen.WebView(url = items[i].url))
                                        }
                                        onDismiss()
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                if (i + 1 < items.size) {
                                    ToolGridItem(
                                        item = items[i + 1],
                                        onClick = {
                                            if (items[i + 1].url.isNotBlank()) {
                                                navController.navigate(Screen.WebView(url = items[i + 1].url))
                                            }
                                            onDismiss()
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolGridItem(
    item: ToolItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF303548).copy(alpha = 0.6f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.icon,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    color = Color(0xFFC2C0C8),
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

private data class ToolCategory(
    val name: String,
    val items: List<ToolItem>
)

private data class ToolItem(
    val name: String,
    val icon: String,
    val url: String
)
