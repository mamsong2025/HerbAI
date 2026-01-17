package com.example.herbai.ui.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.herbai.engine.LocalizationEngine

/**
 * Health Connect Screen - Kết nối vòng tay thông minh và đọc dữ liệu sức khỏe
 * Sử dụng Google Health Connect API
 */
@Composable
fun HealthConnectScreen(
    onBackClick: () -> Unit
) {
    val emeraldGreen = Color(0xFF10B981)
    
    var isConnected by remember { mutableStateOf(false) }
    var healthData by remember { mutableStateOf<HealthDataSummary?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    
                    // Connection Status
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                if (isConnected) Color.White.copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.3f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (isConnected) Color.Green else Color.Red,
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (isConnected) 
                                LocalizationEngine.getLocalizedString("Đã kết nối", "Connected", "已连接", "연결됨")
                            else 
                                LocalizationEngine.getLocalizedString("Chưa kết nối", "Not Connected", "未连接", "연결 안됨"),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Thiết Chẩn Thông Minh",
                        en = "Smart Health Diagnosis",
                        zh = "智能切诊",
                        ko = "스마트 건강 진단"
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Kết nối vòng tay để theo dõi nhịp tim, giấc ngủ",
                        en = "Connect wearable to track heart rate, sleep",
                        zh = "连接手环追踪心率、睡眠",
                        ko = "손목밴드를 연결하여 심박수, 수면 추적"
                    ),
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Connection Card
            item {
                ConnectionCard(
                    isConnected = isConnected,
                    isLoading = isLoading,
                    onConnect = {
                        isLoading = true
                        // Simulate connection
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            isConnected = true
                            isLoading = false
                            healthData = HealthDataSummary(
                                heartRate = 72,
                                steps = 8547,
                                sleepHours = 7.5f,
                                bloodOxygen = 98,
                                lastSyncTime = "10 phút trước"
                            )
                        }, 2000)
                    },
                    emeraldGreen = emeraldGreen
                )
            }
            
            // Health Data Cards
            if (isConnected && healthData != null) {
                item {
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Dữ liệu sức khỏe hôm nay",
                            en = "Today's Health Data",
                            zh = "今日健康数据",
                            ko = "오늘의 건강 데이터"
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                items(getHealthMetrics(healthData!!)) { metric ->
                    HealthMetricCard(metric, emeraldGreen)
                }
                
                // TCM Analysis Card
                item {
                    TCMAnalysisCard(healthData!!, emeraldGreen)
                }
            }
            
            // Supported Devices
            item {
                SupportedDevicesCard(emeraldGreen)
            }
        }
    }
}

@Composable
private fun ConnectionCard(
    isConnected: Boolean,
    isLoading: Boolean,
    onConnect: () -> Unit,
    emeraldGreen: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Watch,
                contentDescription = null,
                tint = emeraldGreen,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Google Health Connect",
                    en = "Google Health Connect",
                    zh = "Google健康连接",
                    ko = "Google 헬스 커넥트"
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Đồng bộ dữ liệu từ các thiết bị đeo thông minh",
                    en = "Sync data from smart wearables",
                    zh = "同步智能穿戴设备数据",
                    ko = "스마트 웨어러블에서 데이터 동기화"
                ),
                color = Color.Gray,
                fontSize = 13.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            if (!isConnected) {
                Button(
                    onClick = onConnect,
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Kết nối ngay",
                            en = "Connect Now",
                            zh = "立即连接",
                            ko = "지금 연결"
                        )
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFD1FAE5), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = emeraldGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Đã kết nối thành công!",
                            en = "Connected successfully!",
                            zh = "连接成功！",
                            ko = "연결 성공!"
                        ),
                        color = emeraldGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun HealthMetricCard(metric: HealthMetric, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(metric.iconColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(metric.icon, contentDescription = null, tint = metric.iconColor)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(metric.label, color = Color.Gray, fontSize = 13.sp)
                Text(
                    metric.value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            
            // Status indicator
            Box(
                modifier = Modifier
                    .background(
                        if (metric.status == "normal") Color(0xFFD1FAE5) else Color(0xFFFEF3C7),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    if (metric.status == "normal") 
                        LocalizationEngine.getLocalizedString("Bình thường", "Normal", "正常", "정상")
                    else 
                        LocalizationEngine.getLocalizedString("Chú ý", "Attention", "注意", "주의"),
                    fontSize = 11.sp,
                    color = if (metric.status == "normal") Color(0xFF059669) else Color(0xFFD97706)
                )
            }
        }
    }
}

@Composable
private fun TCMAnalysisCard(data: HealthDataSummary, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = color)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Phân tích theo Đông Y",
                        en = "TCM Analysis",
                        zh = "中医分析",
                        ko = "한의학 분석"
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val analysis = analyzeTCM(data)
            Text(analysis, fontSize = 14.sp, color = Color(0xFF065F46))
        }
    }
}

@Composable
private fun SupportedDevicesCard(color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Thiết bị được hỗ trợ",
                    en = "Supported Devices",
                    zh = "支持的设备",
                    ko = "지원 기기"
                ),
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val devices = listOf(
                "Samsung Galaxy Watch",
                "Google Pixel Watch",
                "Fitbit",
                "Xiaomi Mi Band",
                "Garmin",
                "Amazfit"
            )
            
            devices.forEach { device ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(device, fontSize = 14.sp)
                }
            }
        }
    }
}

// Helper functions and data classes
data class HealthDataSummary(
    val heartRate: Int,
    val steps: Int,
    val sleepHours: Float,
    val bloodOxygen: Int,
    val lastSyncTime: String
)

data class HealthMetric(
    val icon: ImageVector,
    val iconColor: Color,
    val label: String,
    val value: String,
    val status: String
)

private fun getHealthMetrics(data: HealthDataSummary): List<HealthMetric> {
    return listOf(
        HealthMetric(
            Icons.Default.Favorite,
            Color(0xFFEF4444),
            LocalizationEngine.getLocalizedString("Nhịp tim", "Heart Rate", "心率", "심박수"),
            "${data.heartRate} bpm",
            if (data.heartRate in 60..100) "normal" else "attention"
        ),
        HealthMetric(
            Icons.Default.DirectionsWalk,
            Color(0xFF3B82F6),
            LocalizationEngine.getLocalizedString("Số bước đi", "Steps", "步数", "걸음 수"),
            "${data.steps}",
            if (data.steps >= 8000) "normal" else "attention"
        ),
        HealthMetric(
            Icons.Default.Bedtime,
            Color(0xFF8B5CF6),
            LocalizationEngine.getLocalizedString("Giấc ngủ", "Sleep", "睡眠", "수면"),
            "${data.sleepHours}h",
            if (data.sleepHours >= 7) "normal" else "attention"
        ),
        HealthMetric(
            Icons.Default.Air,
            Color(0xFF06B6D4),
            LocalizationEngine.getLocalizedString("SpO2", "SpO2", "血氧", "혈중 산소"),
            "${data.bloodOxygen}%",
            if (data.bloodOxygen >= 95) "normal" else "attention"
        )
    )
}

private fun analyzeTCM(data: HealthDataSummary): String {
    return when {
        data.heartRate > 90 && data.sleepHours < 6 -> 
            LocalizationEngine.getLocalizedString(
                vi = "Nhịp tim nhanh kết hợp thiếu ngủ có thể chỉ ra chứng 'Tâm Âm Hư'. Nên bổ dưỡng tâm âm, an thần.",
                en = "Fast heart rate with poor sleep may indicate 'Heart Yin Deficiency'. Consider nourishing heart yin.",
                zh = "心率快加睡眠不足可能提示'心阴虚'。建议滋养心阴，安神。",
                ko = "빠른 심박수와 수면 부족은 '심음허'를 나타낼 수 있습니다. 심음 보양을 고려하세요."
            )
        data.steps < 5000 ->
            LocalizationEngine.getLocalizedString(
                vi = "Hoạt động ít, có thể dẫn đến khí trệ huyết ứ. Nên vận động nhẹ nhàng để khí huyết lưu thông.",
                en = "Low activity may lead to qi stagnation. Light exercise recommended for circulation.",
                zh = "活动量少，可能导致气滞血瘀。建议轻度运动以促进气血流通。",
                ko = "활동량 부족은 기체혈어를 유발할 수 있습니다. 가벼운 운동을 권장합니다."
            )
        else ->
            LocalizationEngine.getLocalizedString(
                vi = "Các chỉ số sinh tồn trong ngưỡng bình thường. Tiếp tục duy trì lối sống lành mạnh.",
                en = "Vital signs are within normal range. Continue maintaining a healthy lifestyle.",
                zh = "生命体征在正常范围内。请继续保持健康的生活方式。",
                ko = "활력 징후가 정상 범위 내에 있습니다. 건강한 생활 방식을 유지하세요."
            )
    }
}
