package com.example.herbai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.herbai.engine.LocalizationEngine

/**
 * Update Ready Snackbar - Hiển thị khi bản cập nhật đã tải xong (Flexible update)
 */
@Composable
fun UpdateReadySnackbar(
    onInstallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val emeraldGreen = Color(0xFF10B981)
    
    Snackbar(
        modifier = modifier.padding(16.dp),
        action = {
            TextButton(onClick = onInstallClick) {
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "CÀI ĐẶT",
                        en = "INSTALL",
                        zh = "安装",
                        ko = "설치"
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = emeraldGreen
    ) {
        Text(
            LocalizationEngine.getLocalizedString(
                vi = "Bản cập nhật mới đã sẵn sàng!",
                en = "New update is ready!",
                zh = "新更新已准备就绪！",
                ko = "새 업데이트가 준비되었습니다!"
            ),
            color = Color.White
        )
    }
}

/**
 * Update Available Dialog - Hiển thị khi có bản cập nhật mới
 */
@Composable
fun UpdateAvailableDialog(
    onUpdate: () -> Unit,
    onLater: () -> Unit,
    isForced: Boolean = false
) {
    val emeraldGreen = Color(0xFF10B981)
    
    Dialog(onDismissRequest = { if (!isForced) onLater() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(emeraldGreen.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.SystemUpdate,
                        contentDescription = null,
                        tint = emeraldGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Title
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Phiên bản mới đã có!",
                        en = "New Version Available!",
                        zh = "新版本可用！",
                        ko = "새 버전 사용 가능!"
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Description
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = if (isForced) 
                            "Bản cập nhật này bao gồm các sửa lỗi quan trọng. Vui lòng cập nhật để tiếp tục sử dụng."
                        else 
                            "Cập nhật để nhận tính năng mới và các cải tiến hiệu suất.",
                        en = if (isForced)
                            "This update includes critical fixes. Please update to continue."
                        else
                            "Update to get new features and performance improvements.",
                        zh = if (isForced)
                            "此更新包含关键修复。请更新以继续使用。"
                        else
                            "更新以获取新功能和性能改进。",
                        ko = if (isForced)
                            "이 업데이트에는 중요한 수정 사항이 포함되어 있습니다. 계속하려면 업데이트하세요."
                        else
                            "새로운 기능과 성능 개선을 받으려면 업데이트하세요."
                    ),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Update Button
                Button(
                    onClick = onUpdate,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Cập nhật ngay",
                            en = "Update Now",
                            zh = "立即更新",
                            ko = "지금 업데이트"
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                
                // Later Button (only for non-forced updates)
                if (!isForced) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TextButton(onClick = onLater) {
                        Text(
                            LocalizationEngine.getLocalizedString(
                                vi = "Để sau",
                                en = "Later",
                                zh = "稍后",
                                ko = "나중에"
                            ),
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

/**
 * What's New Card - Hiển thị tính năng mới sau khi cập nhật
 */
@Composable
fun WhatsNewCard(
    features: List<String>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val emeraldGreen = Color(0xFF10B981)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "✨ Có gì mới?",
                        en = "✨ What's New?",
                        zh = "✨ 新功能",
                        ko = "✨ 새로운 기능"
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = emeraldGreen
                )
                
                TextButton(onClick = onDismiss) {
                    Text("×", fontSize = 20.sp, color = Color.Gray)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            features.forEach { feature ->
                Text(
                    "• $feature",
                    fontSize = 14.sp,
                    color = Color(0xFF065F46),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}
