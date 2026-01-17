package com.example.herbai.ui.treatment

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.herbai.engine.AcupointEngine
import com.example.herbai.engine.LocalizationEngine
import com.example.herbai.engine.MedicinePreparationGuide
import com.example.herbai.ui.components.MedicalDisclaimerBanner

@Composable
fun TreatmentProtocolScreen(
    formulaId: String,
    syndrome: String,
    acupointEngine: AcupointEngine
) {
    val medicineGuide = remember { MedicinePreparationGuide() }
    val preparation = remember { medicineGuide.getPreparationGuide(formulaId) }
    val acupoints = remember { acupointEngine.getCommonAcupoints(syndrome) }
    val synergy = remember { acupointEngine.getSynergyExplanation(preparation.formulaName, syndrome) }
    
    val emeraldGreen = Color(0xFF10B981)
    
    com.example.herbai.ui.components.ResponsiveContent(
        modifier = Modifier.background(Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Phác Đồ Kết Hợp",
                        en = "Combined Treatment Protocol",
                        zh = "综合治疗方案",
                        ko = "통합 치료 프로토콜"
                    ),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Thảo dược & Huyệt vị: $syndrome",
                        en = "Herbs & Acupoints: $syndrome",
                        zh = "草药与穴位: $syndrome",
                        ko = "약초 및 경혈: $syndrome"
                    ),
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // Synergy Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = emeraldGreen)
                Spacer(Modifier.width(12.dp))
                Text(synergy, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF065F46))
            }
        }

        // 1. Herbal Formula Section
        TreatmentSectionHeader(
            LocalizationEngine.getLocalizedString(
                vi = "1. Thuốc Thảo Dược",
                en = "1. Herbal Medicine",
                zh = "1. 草药"
            ),
            Icons.Default.LocalFlorist, 
            emeraldGreen
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(preparation.formulaName, fontWeight = FontWeight.Bold, color = emeraldGreen)
                Spacer(Modifier.height(8.dp))
                preparation.ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("• ${ingredient.name}", fontSize = 14.sp)
                        Text("${ingredient.amount}${ingredient.unit}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 2. Acupoint Section
        TreatmentSectionHeader(
            LocalizationEngine.getLocalizedString(
                vi = "2. Châm Cứu / Bấm Huyệt",
                en = "2. Acupuncture & Acupressure",
                zh = "2. 针灸与按摩"
            ),
            Icons.Default.Adjust, 
            emeraldGreen
        )
        acupoints.forEach { point ->
            AcupointItem(point, emeraldGreen)
        }

        Spacer(Modifier.height(16.dp))

        // 3. Recommended Equipment (Affiliate)
        TreatmentSectionHeader(
            LocalizationEngine.getLocalizedString(
                vi = "3. Dụng cụ hỗ trợ",
                en = "3. Recommended Tools",
                zh = "3. 推荐工具"
            ),
            Icons.Default.ShoppingCart, 
            emeraldGreen
        )
        val products = remember { acupointEngine.getRecommendedEquipments(syndrome) }
        products.forEach { product ->
            AffiliateProductCard(product, emeraldGreen)
        }

        Spacer(Modifier.height(24.dp))
        
        MedicalDisclaimerBanner(modifier = Modifier.padding(horizontal = 16.dp))
        
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun AffiliateProductCard(product: com.example.herbai.model.AffiliateProduct, color: Color) {
    val isInternational = product.currency != "VND"
    val platformName = if (product.affiliate_link?.contains("shopee") == true) "Shopee" else "Amazon"
    val platformColor = if (platformName == "Shopee") Color(0xFFFF5722) else Color(0xFFFF9900)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, color.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (product.is_top_rated) {
                Row(
                    modifier = Modifier
                        .background(Color(0xFFFEF3C7), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFFD97706))
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (isInternational) "EXPERT RECOMMENDED" else "CHUYÊN GIA KHUYÊN DÙNG",
                        fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706)
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Devices, contentDescription = null, tint = color)
                }
                
                Spacer(Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFFFFB400))
                        Text(" ${product.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(
                            if (isInternational) " (${product.review_count} reviews)" else " (${product.review_count} đánh giá)",
                            fontSize = 11.sp, color = Color.Gray
                        )
                    }
                    
                    Text(product.description, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
                    
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val priceText = if (product.currency == "VND") {
                            "${String.format("%,.0f", product.price)}đ"
                        } else {
                            "$${String.format("%.2f", product.price)}"
                        }
                        
                    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
                    
                    Text(priceText, color = platformColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { 
                            val link = product.affiliate_link ?: "https://www.google.com/search?q=${product.fallback_search_query}"
                            uriHandler.openUri(link)
                        },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = platformColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        val buttonText = when {
                            product.affiliate_link != null && isInternational -> "Buy on $platformName"
                            product.affiliate_link != null -> "Mua tại $platformName"
                            isInternational -> "Search on $platformName"
                            else -> "Tìm trên $platformName"
                        }
                        Text(buttonText, fontSize = 10.sp, color = Color.White)
                    }
                    }
                }
            }
        }
        }
    }
}

@Composable
fun TreatmentSectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray)
    }
}

@Composable
fun AcupointItem(point: com.example.herbai.model.Acupoint, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(point.name_vietnamese, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text("(${point.id})", color = color, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text(point.name_han, color = Color.Gray, fontSize = 12.sp)
            }
            
            Spacer(Modifier.height(8.dp))
            
            // Instructional Image Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Text("Ảnh hướng dẫn vị trí ${point.name_vietnamese}", fontSize = 11.sp, color = Color.Gray)
                }
            }
            
            Spacer(Modifier.height(8.dp))
            
            Text("📍 Vị trí: ${point.location}", fontSize = 12.sp)
            Text("🎯 Công năng: ${point.indication}", fontSize = 12.sp)
            Text("👉 Kỹ thuật: ${point.technique}", fontSize = 12.sp, color = color, fontWeight = FontWeight.Medium)
        }
    }
}
