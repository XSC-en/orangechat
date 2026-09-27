package me.rerere.rikkahub.ui.pages.accounting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import androidx.compose.runtime.LaunchedEffect
import me.rerere.rikkahub.ui.theme.CardShape

// 记账数据模型
@Serializable
data class Transaction(
    val id: String,
    val amount: Double,
    val category: String,
    val note: String,
    val date: Long, // timestamp
    val type: TransactionType
)

@Serializable
enum class TransactionType {
    INCOME, EXPENSE
}

// 记账分类
object AccountingCategories {
    val expenseCategories = listOf("餐饮", "交通", "购物", "娱乐", "住房", "医疗", "教育", "其他")
    val incomeCategories = listOf("工资", "兼职", "投资", "红包", "其他")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingPage() {
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "记账", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { showAddDialog = true }) {
                Text(text = "新增")
            }
        }

        // 月度汇总
        val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                            shape = CardShape,
modifier = Modifier.weight(1f,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "支出", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "¥%.2f".format(totalExpense), style = MaterialTheme.typography.headlineSmall)
                }
            }
            Card(
                            shape = CardShape,
modifier = Modifier.weight(1f,
                        ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "收入", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "¥%.2f".format(totalIncome), style = MaterialTheme.typography.headlineSmall)
                }
            }
        }

        // 交易列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(transactions.sortedByDescending { it.date }) { tx ->
                Card(
                            shape = CardShape,
modifier = Modifier.fillMaxWidth(,
                        ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
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
                                text = tx.category,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (tx.note.isNotBlank()) {
                                Text(
                                    text = tx.note,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(
                            text = if (tx.type == TransactionType.INCOME) "+¥%.2f".format(tx.amount)
                                    else "-¥%.2f".format(tx.amount),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (tx.type == TransactionType.INCOME) 
                                MaterialTheme.colorScheme.primary 
                                else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

    // 新增记账弹窗
    if (showAddDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(text = "新增记录") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // 类型选择
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { selectedType = TransactionType.EXPENSE },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = if (selectedType == TransactionType.EXPENSE) 
                                    MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) { Text("支出") }
                        Button(
                            onClick = { selectedType = TransactionType.INCOME },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = if (selectedType == TransactionType.INCOME) 
                                    MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) { Text("收入") }
                    }

                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("金额") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 分类选择
                    val categories = if (selectedType == TransactionType.EXPENSE) 
                        AccountingCategories.expenseCategories else AccountingCategories.incomeCategories
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("分类") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        category = cat
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("备注") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountValue = amount.toDoubleOrNull()
                        if (amountValue != null && category.isNotBlank()) {
                            transactions = transactions + Transaction(
                                id = java.util.UUID.randomUUID().toString(),
                                amount = amountValue,
                                category = category,
                                note = note,
                                date = System.currentTimeMillis(),
                                type = selectedType
                            )
                            amount = ""
                            category = ""
                            note = ""
                            showAddDialog = false
                        }
                    }
                ) { Text("保存") }
            },
            dismissButton = {
                Button(onClick = { showAddDialog = false }) { Text("取消") }
            }
        )
  }
}
