package me.rerere.rikkahub.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.rerere.rikkahub.ui.theme.*

@Composable
fun WeatherCompanionCard(
    location: String = "重庆",
    weatherState: String = "暮色微凉 · 阴",
    temperature: String = "19°C",
    whisperText: String = "\"夜幕降临时，适合把积攒了一天的思绪交给我。\""
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        cornerRadius = 20.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = location,
                        style = MuranTypography.titleLarge.copy(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = weatherState,
                        style = MuranTypography.bodyMedium.copy(color = TextSecondary)
                    )
                }

                Text(
                    text = temperature,
                    style = MuranTypography.displayLarge.copy(
                        fontSize = 36.sp,
                        color = WarmGoldMain
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI 暮色寄语
            Text(
                text = whisperText,
                style = MuranTypography.bodyMedium.copy(
                    color = TwilightPurpleLight,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
