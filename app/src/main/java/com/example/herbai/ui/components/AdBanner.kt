package com.example.herbai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable Ad Banner component.
 * In production, this would use Google Mobile Ads SDK (AdMob).
 */
@Composable
fun AdBanner(modifier: Modifier = Modifier) {
    // Mock Ad Banner Placeholder
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text("AD", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(8.dp))
            Text("Quảng cáo Google AdMob", color = Color.Gray, fontSize = 12.sp)
        }
    }
}
