package com.example.herbai.ui.legal

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.herbai.engine.LocalizationEngine

/**
 * Terms of Service Screen - Required for App Store compliance
 */
@Composable
fun TermsOfServiceScreen(onBack: () -> Unit) {
    val emeraldGreen = Color(0xFF10B981)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(20.dp)
        ) {
            Column {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Điều khoản sử dụng",
                        en = "Terms of Service",
                        zh = "服务条款",
                        ko = "이용 약관"
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            LegalSection(
                title = LocalizationEngine.getLocalizedString("1. Chấp nhận điều khoản", "1. Acceptance of Terms", "1. 接受条款"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Bằng việc tải xuống, cài đặt hoặc sử dụng ứng dụng HerbAI, bạn đồng ý bị ràng buộc bởi các điều khoản này.",
                    en = "By downloading, installing, or using the HerbAI application, you agree to be bound by these terms.",
                    zh = "通过下载、安装或使用HerbAI应用程序，您同意受这些条款的约束。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("2. Mục đích sử dụng", "2. Purpose of Use", "2. 使用目的"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Ứng dụng này chỉ cung cấp thông tin mang tính giáo dục về Y học Cổ truyền. KHÔNG được sử dụng để tự chẩn đoán, điều trị hoặc thay thế lời khuyên y tế chuyên nghiệp.",
                    en = "This app provides educational information about Traditional Chinese Medicine ONLY. It is NOT intended for self-diagnosis, treatment, or as a substitute for professional medical advice.",
                    zh = "本应用程序仅提供有关中医药的教育信息。不得用于自我诊断、治疗或替代专业医疗建议。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("3. Miễn trừ trách nhiệm", "3. Limitation of Liability", "3. 责任限制"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Nhà phát triển KHÔNG chịu trách nhiệm về bất kỳ thiệt hại trực tiếp, gián tiếp, ngẫu nhiên hoặc hậu quả nào phát sinh từ việc sử dụng hoặc không thể sử dụng ứng dụng này.",
                    en = "The developer shall NOT be liable for any direct, indirect, incidental, or consequential damages arising from the use or inability to use this application.",
                    zh = "开发者对因使用或无法使用本应用程序而产生的任何直接、间接、附带或后果性损害不承担责任。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("4. Quyền sở hữu trí tuệ", "4. Intellectual Property", "4. 知识产权"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Tất cả nội dung trong ứng dụng, bao gồm văn bản, hình ảnh và dữ liệu, được bảo vệ bởi luật bản quyền.",
                    en = "All content in this app, including text, images, and data, is protected by copyright laws.",
                    zh = "本应用程序中的所有内容，包括文字、图像和数据，均受版权法保护。"
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Cập nhật lần cuối: Tháng 1, 2026",
                    en = "Last updated: January 2026",
                    zh = "最后更新：2026年1月"
                ),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun LegalSection(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

/**
 * Privacy Policy Screen - Required for App Store compliance
 */
@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    val emeraldGreen = Color(0xFF10B981)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(20.dp)
        ) {
            Column {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Chính sách quyền riêng tư",
                        en = "Privacy Policy",
                        zh = "隐私政策",
                        ko = "개인정보 보호정책"
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            LegalSection(
                title = LocalizationEngine.getLocalizedString("1. Thu thập dữ liệu", "1. Data Collection", "1. 数据收集"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Chúng tôi chỉ thu thập dữ liệu sức khỏe (nhịp tim, hình ảnh) khi bạn chủ động sử dụng các tính năng tương ứng. Dữ liệu được xử lý cục bộ trên thiết bị của bạn.",
                    en = "We only collect health data (heart rate, images) when you actively use the corresponding features. Data is processed locally on your device.",
                    zh = "我们仅在您主动使用相应功能时收集健康数据（心率、图像）。数据在您的设备上本地处理。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("2. Chia sẻ dữ liệu", "2. Data Sharing", "2. 数据共享"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Chúng tôi KHÔNG chia sẻ thông tin cá nhân của bạn với bên thứ ba. Dữ liệu nhật ký sức khỏe chỉ được lưu trữ cục bộ.",
                    en = "We do NOT share your personal information with third parties. Health journal data is stored locally only.",
                    zh = "我们不会与第三方共享您的个人信息。健康日志数据仅在本地存储。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("3. Bảo mật", "3. Security", "3. 安全性"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Chúng tôi áp dụng các biện pháp bảo mật tiêu chuẩn để bảo vệ dữ liệu của bạn. Tuy nhiên, không có phương thức truyền dữ liệu nào qua Internet là an toàn 100%.",
                    en = "We implement industry-standard security measures to protect your data. However, no method of data transmission over the Internet is 100% secure.",
                    zh = "我们采用行业标准的安全措施来保护您的数据。但是，没有任何通过互联网传输数据的方法是100%安全的。"
                )
            )
            
            LegalSection(
                title = LocalizationEngine.getLocalizedString("4. Quyền của bạn", "4. Your Rights", "4. 您的权利"),
                content = LocalizationEngine.getLocalizedString(
                    vi = "Bạn có quyền xóa tất cả dữ liệu cá nhân bất cứ lúc nào bằng cách gỡ cài đặt ứng dụng hoặc xóa dữ liệu trong phần Cài đặt.",
                    en = "You have the right to delete all personal data at any time by uninstalling the app or clearing data in Settings.",
                    zh = "您有权随时通过卸载应用程序或在设置中清除数据来删除所有个人数据。"
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Liên hệ: support@herbai.app",
                    en = "Contact: support@herbai.app",
                    zh = "联系方式：support@herbai.app"
                ),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
