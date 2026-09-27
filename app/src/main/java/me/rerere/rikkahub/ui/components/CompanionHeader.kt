package me.rerere.rikkahub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Plus
import me.rerere.rikkahub.ui.theme.*

@Composable
fun CompanionHeader(
    daysCount: Int,
    onAddClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧：品牌标题 + 相伴天数微光胶囊
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Muran",
                style = MuranTypography.displayLarge.copy(fontSize = 28.sp)
            )

            // "与你相伴第 N 天" 柔光胶囊
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(GlassSurface)
                    .border(
                        width = 1.dp,
                        color = WarmGoldMain.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "与你相伴第 $daysCount 天",
                    style = MuranTypography.labelSmall.copy(
                        color = WarmGoldLight,
                        fontSize = 11.sp
                    )
                )
            }
        }

        // 右侧操作图标
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Lucide.Plus,
                    contentDescription = "新建对话",
                    tint = TextPrimary
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Lucide.Menu,
                    contentDescription = "抽屉菜单",
                    tint = TextPrimary
                )
            }
        }
    }
}
