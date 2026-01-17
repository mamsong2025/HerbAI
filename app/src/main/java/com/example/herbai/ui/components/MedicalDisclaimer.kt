package com.example.herbai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.herbai.engine.LocalizationEngine

/**
 * Medical Disclaimer Dialog - CRITICAL for legal and safety compliance
 * Shows important warnings about the educational nature of this app.
 * Required for Google Play / App Store approval.
 */
@Composable
fun MedicalDisclaimerDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    val isVi = LocalizationEngine.getCurrentLanguage() == LocalizationEngine.AppLanguage.VIETNAMESE
    val isZh = LocalizationEngine.getCurrentLanguage() == LocalizationEngine.AppLanguage.CHINESE
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "⚠️ CẢNH BÁO Y TẾ QUAN TRỌNG",
                        en = "⚠️ IMPORTANT MEDICAL DISCLAIMER",
                        zh = "⚠️ 重要医疗免责声明",
                        ko = "⚠️ 중요한 의료 면책 조항"
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFDC2626),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Ứng dụng này chỉ cung cấp thông tin mang tính GIÁO DỤC và THAM KHẢO về Y học Cổ truyền.",
                        en = "This app provides EDUCATIONAL and REFERENCE information about Traditional Chinese Medicine only.",
                        zh = "本应用程序仅提供有关中医药的教育和参考信息。"
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Key legal points
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFEF3C7), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    DisclaimerPoint(LocalizationEngine.getLocalizedString(
                        "❌ KHÔNG phải lời khuyên y tế chuyên nghiệp",
                        "❌ NOT professional medical advice",
                        "❌ 不构成专业医疗建议"
                    ))
                    DisclaimerPoint(LocalizationEngine.getLocalizedString(
                        "❌ KHÔNG thay thế chẩn đoán của bác sĩ",
                        "❌ NOT a substitute for doctor's diagnosis",
                        "❌ 不能替代医生诊断"
                    ))
                    DisclaimerPoint(LocalizationEngine.getLocalizedString(
                        "⚠️ Luôn tham khảo ý kiến chuyên gia y tế",
                        "⚠️ Always consult healthcare professionals",
                        "⚠️ 请务必咨询医疗专业人员"
                    ))
                    DisclaimerPoint(LocalizationEngine.getLocalizedString(
                        "🚨 Trường hợp khẩn cấp: Gọi 115 ngay",
                        "🚨 Emergency: Call local emergency services",
                        "🚨 紧急情况：请立即拨打急救电话"
                    ))
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Nhà phát triển KHÔNG chịu trách nhiệm về bất kỳ hậu quả nào phát sinh từ việc sử dụng thông tin trong ứng dụng này. Bằng việc tiếp tục, bạn đồng ý với Điều khoản sử dụng.",
                        en = "The developer assumes NO LIABILITY for any consequences arising from the use of information in this app. By continuing, you agree to our Terms of Service.",
                        zh = "开发者对使用本应用程序信息产生的任何后果不承担任何责任。继续使用即表示您同意我们的服务条款。"
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = onAccept,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Tôi đã đọc, hiểu và đồng ý",
                            en = "I have read, understood and agree",
                            zh = "我已阅读、理解并同意"
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DisclaimerPoint(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Medium
    )
}

/**
 * Inline disclaimer banner for screens that show medical recommendations
 */
@Composable
fun MedicalDisclaimerBanner(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                tint = Color(0xFFF59E0B),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Chỉ mang tính tham khảo - Hãy tham vấn bác sĩ",
                    en = "For reference only - Consult a doctor",
                    zh = "仅供参考 - 请咨询医生"
                ),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF92400E)
            )
        }
    }
}
