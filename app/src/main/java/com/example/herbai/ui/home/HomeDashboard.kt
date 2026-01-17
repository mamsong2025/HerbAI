package com.example.herbai.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun HomeDashboard(
    onScanClick: () -> Unit,
    onHealthConnectClick: () -> Unit = {}
) {
    val emeraldGreen = Color(0xFF10B981)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(listOf(emeraldGreen, Color.White))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "HerbAI",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Search Bar (Placeholder)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search plants, symptoms, or formulas") },
            shape = RoundedCornerShape(24.dp)
        )

        // Main Action: Scanner
        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Scan"
                )
                Spacer(Modifier.width(8.dp))
                Text("Scan Plant with Camera", fontSize = 18.sp)
            }
        }
        
        Spacer(Modifier.height(12.dp))
        
        // Health Connect Button
        OutlinedButton(
            onClick = onHealthConnectClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFF3B82F6))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⌚", fontSize = 24.sp)
                Spacer(Modifier.width(8.dp))
                Text("Connect Smartwatch", fontSize = 16.sp, color = Color(0xFF3B82F6))
            }
        }

        // Quick Access Grid
        Spacer(Modifier.height(24.dp))
        Text(
            "Quick Access",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuickTile("Traditional Formulas", Modifier.weight(1f))
            QuickTile("Syndrome-ICD Mapping", Modifier.weight(1f))
            QuickTile("Safety Rules", Modifier.weight(1f))
        }
    }
}

@Composable
fun QuickTile(title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(title, textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}
