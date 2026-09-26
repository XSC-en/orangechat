package me.rerere.rikkahub.ui.pages.accounting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.rerere.rikkahub.ui.components.GlassCard
import me.rerere.rikkahub.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "记账",
                style = MuranTypography.displayLarge.copy(fontSize = 28.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "记录每一笔收支",
                style = MuranTypography.bodyMedium.copy(color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Add Record Button
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp,
                onClick = { /* TODO: add record */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = WarmGoldMain
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "记一笔",
                        style = MuranTypography.bodyLarge.copy(color = TextPrimary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Records List
            Text(
                text = "最近记录",
                style = MuranTypography.titleLarge.copy(fontSize = 16.sp, color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(sampleRecords) { record ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        cornerRadius = 16.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = record.category,
                                    style = MuranTypography.bodyLarge
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = record.date,
                                    style = MuranTypography.bodyMedium.copy(color = TextTertiary)
                                )
                            }
                            Text(
                                text = record.amount,
                                style = MuranTypography.bodyLarge.copy(
                                    color = if (record.isIncome) WarmGoldLight else TextSecondary
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class RecordItem(
    val category: String,
    val date: String,
    val amount: String,
    val isIncome: Boolean
)

private val sampleRecords = listOf(
    RecordItem("餐饮", "今天 12:30", "-¥35.00", false),
    RecordItem("交通", "今天 08:15", "-¥6.00", false),
    RecordItem("工资", "昨天 10:00", "+¥5000.00", true),
    RecordItem("购物", "昨天 15:20", "-¥299.00", false),
    RecordItem("餐饮", "前天 19:00", "-¥58.00", false)
)
