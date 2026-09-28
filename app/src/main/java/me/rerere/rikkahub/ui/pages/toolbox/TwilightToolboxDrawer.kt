/*
 * 橘瓣 OrangeChat
 * 衍生自 RikkaHub (https://github.com/rikkahub/rikkahub)，原作者 RE
 * 本项目基于 GNU AGPL v3 开源，详见根目录 LICENSE 文件
 */

package me.rerere.rikkahub.ui.pages.toolbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.rerere.hugeicons.HugeIcons
import me.rerere.hugeicons.stroke.AppStore
import me.rerere.hugeicons.stroke.BookOpen01
import me.rerere.hugeicons.stroke.ChartColumn
import me.rerere.hugeicons.stroke.CloudServer
import me.rerere.hugeicons.stroke.Image02
import me.rerere.hugeicons.stroke.LanguageCircle
import me.rerere.hugeicons.stroke.Message01
import me.rerere.hugeicons.stroke.Pulse01
import me.rerere.hugeicons.stroke.Rocket01
import me.rerere.rikkahub.Screen
import me.rerere.rikkahub.ui.components.nav.BackButton
import me.rerere.rikkahub.ui.context.LocalNavController
import me.rerere.rikkahub.ui.context.Navigator

// ── 工具数据模型 ──────────────────────────────────────────────
private data class Tool(
    val name: String,
    val desc: String,
    val icon: ImageVector,
    val iconBg: Color,
    val screen: Screen,
    val isNew: Boolean = false,
)

// ── 工具箱主页 ────────────────────────────────────────────────
@Composable
fun TwilightToolboxDrawer(
    onDismiss: () -> Unit
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    // 常用工具（项目内真实功能）
    val pinnedTools = listOf(
        Tool("AI 翻译", "多语言即时互译", HugeIcons.LanguageCircle, Color(0xFF2E3A52), Screen.Translator),
        Tool("图像生成", "文字转图片", HugeIcons.Image02, Color(0xFF2E2A45), Screen.ImageGen),
        Tool("健康数据", "记录与分析", HugeIcons.Pulse01, Color(0xFF3A2A2A), Screen.Health),
        Tool("记账", "收支一目了然", HugeIcons.ChartColumn, Color(0xFF2A3A2E), Screen.Accounting),
    )

    // 全部工具（项目内真实功能）
    val allTools = listOf(
        Tool("天气", "实时天气与预报", HugeIcons.CloudServer, Color(0xFF2A3040), Screen.Weather),
        Tool("QQ 日志", "消息记录", HugeIcons.Message01, Color(0xFF2E2A45), Screen.QQLogs),
        Tool("插件市场", "扩展更多能力", HugeIcons.AppStore, Color(0xFF2A3A2E), Screen.PluginMarket, isNew = true),
        Tool("考公刷题", "每日练习", HugeIcons.BookOpen01, Color(0xFF3A2E2A), Screen.ExamPrep),
        Tool("Mini Apps", "轻量小工具", HugeIcons.Rocket01, Color(0xFF2A2A3A), Screen.MiniAppManager),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202536))
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // 顶部栏
            ToolboxTopBar(onBack = onDismiss)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                // 常用工具（2列大卡片）
                Text(
                    text = "常用",
                    color = Color(0xFFE7CFA8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 10.dp),
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(((pinnedTools.size / 2) * 120 + 10).dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    userScrollEnabled = false,
                ) {
                    items(pinnedTools) { tool ->
                        PinnedToolCard(
                            tool = tool,
                            onClick = {
                                navController.navigate(tool.screen)
                                onDismiss()
                            }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 全部工具（3列小卡片）
                Text(
                    text = "全部",
                    color = Color(0xFFE7CFA8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 10.dp),
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(((allTools.size / 3 + if (allTools.size % 3 != 0) 1 else 0) * 110 + 10).dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    userScrollEnabled = false,
                ) {
                    items(allTools) { tool ->
                        SmallToolCard(
                            tool = tool,
                            onClick = {
                                navController.navigate(tool.screen)
                                onDismiss()
                            }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ── 顶部栏 ────────────────────────────────────────────────────
@Composable
private fun ToolboxTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BackButton()
        Text(
            text = "工具箱",
            color = Color(0xFFF0EDF1),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}

// ── 常用工具大卡片 ────────────────────────────────────────────
@Composable
private fun PinnedToolCard(tool: Tool, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF303548))
            .border(1.dp, Color(0xFF2C2A3E), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 图标
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tool.iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(tool.icon, contentDescription = null, tint = Color(0xFFA89BCB), modifier = Modifier.size(18.dp))
            }

            Spacer(Modifier.weight(1f))

            Text(text = tool.name, color = Color(0xFFF0EDF1), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = tool.desc, color = Color(0xFF9695A2), fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
        }

        // 新标签
        if (tool.isNew) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFD8B98A))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Text("新", color = Color(0xFF202536), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ── 全部工具小卡片 ────────────────────────────────────────────
@Composable
private fun SmallToolCard(tool: Tool, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF303548))
            .border(1.dp, Color(0xFF2C2A3E), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tool.iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(tool.icon, contentDescription = null, tint = Color(0xFFA89BCB), modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = tool.name,
                color = Color(0xFFF0EDF1),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }

        // 新标签
        if (tool.isNew) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFD8B98A))
                    .padding(horizontal = 5.dp, vertical = 1.dp),
            ) {
                Text("新", color = Color(0xFF202536), fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}