package com.example.herbai.ui.medicine

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
import com.example.herbai.engine.*
import com.example.herbai.ui.components.MedicalDisclaimerBanner
import com.example.herbai.ui.treatment.AffiliateProductCard

@Composable
fun MedicinePreparationScreen(
    formulaId: String,
    onSetReminder: (List<MedicineReminder>) -> Unit
) {
    val guide = remember { MedicinePreparationGuide() }
    val preparation = remember { guide.getPreparationGuide(formulaId) }
    
    val emeraldGreen = Color(0xFF10B981)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(emeraldGreen)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    preparation.formulaName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Hướng dẫn pha chế",
                        en = "Preparation Guide",
                        zh = "制备指南"
                    ),
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        // Ingredients Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalFlorist, contentDescription = null, tint = emeraldGreen)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Thành phần & Liều lượng",
                            en = "Ingredients & Dosages",
                            zh = "成分与剂量"
                        ),
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp
                    )
                }
                
                Spacer(Modifier.height(12.dp))
                
                preparation.ingredients.forEach { ingredient ->
                    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row {
                                Text("• ${ingredient.name}", fontWeight = FontWeight.Medium)
                                Spacer(Modifier.width(8.dp))
                                Text("${ingredient.amount}${ingredient.unit}", color = emeraldGreen, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                "   ${ingredient.role}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        
                        // Buy Herb Button
                        TextButton(
                            onClick = {
                                val link = com.example.herbai.config.AffiliateConfig.getHerbLink(ingredient.name)
                                uriHandler.openUri(link)
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart, 
                                contentDescription = null, 
                                tint = Color(0xFFFF5722),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                LocalizationEngine.getLocalizedString(
                                    vi = "Mua",
                                    en = "Buy",
                                    zh = "购买",
                                    ko = "구매"
                                ),
                                fontSize = 12.sp,
                                color = Color(0xFFFF5722)
                            )
                        }
                    }
                }
            }
        }
        
        // Preparation Steps
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, tint = emeraldGreen)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Cách sắc thuốc",
                            en = "Decoction Method",
                            zh = "煎药方法"
                        ),
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp
                    )
                }
                
                Spacer(Modifier.height(12.dp))
                
                PreparationStep(1, LocalizationEngine.getLocalizedString("Ngâm thuốc", "Soak herbs", "浸泡药材"), preparation.preparationMethod.step1_soak)
                PreparationStep(2, LocalizationEngine.getLocalizedString("Sắc lần 1", "First boil", "第一次煎"), preparation.preparationMethod.step2_firstBoil)
                PreparationStep(3, LocalizationEngine.getLocalizedString("Lọc nước", "Strain", "过滤"), preparation.preparationMethod.step3_strain)
                PreparationStep(4, LocalizationEngine.getLocalizedString("Sắc lần 2", "Second boil", "第二次煎"), preparation.preparationMethod.step4_secondBoil)
                PreparationStep(5, LocalizationEngine.getLocalizedString("Hoàn thành", "Complete", "完成"), preparation.preparationMethod.step5_combine)
            }
        }
        
        Spacer(Modifier.height(16.dp))
        
        // Dosage Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.WaterDrop, contentDescription = null, tint = emeraldGreen)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Liều dùng",
                            en = "Dosage",
                            zh = "剂量"
                        ),
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp
                    )
                }
                
                Spacer(Modifier.height(12.dp))
                
                DosageRow(LocalizationEngine.getLocalizedString("Mỗi lần", "Per dose", "每次"), preparation.dosageInstruction.singleDose)
                DosageRow(LocalizationEngine.getLocalizedString("Số lần/ngày", "Times/day", "每日次数"), preparation.dosageInstruction.frequency)
                DosageRow(LocalizationEngine.getLocalizedString("Thời điểm", "Timing", "服用时间"), preparation.dosageInstruction.timing)
                DosageRow(LocalizationEngine.getLocalizedString("Thời gian dùng", "Duration", "疗程"), preparation.dosageInstruction.duration)
            }
        }
        
        Spacer(Modifier.height(16.dp))
        
        // Warnings
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                preparation.warnings.forEach { warning ->
                    Text(warning, fontSize = 13.sp, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))
        
        // Set Reminder Button
        Button(
            onClick = {
                val reminders = guide.createMedicineSchedule(
                    formulaName = preparation.formulaName,
                    frequency = 2,
                    durationDays = 7
                )
                onSetReminder(reminders)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Alarm, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Đặt nhắc uống thuốc", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        
        // Shopee Tools Section
        Text(
            "Dụng cụ sắc thuốc khuyên dùng (Shopee)",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
        val tools = remember { guide.getRecommendedTools() }
        tools.forEach { tool ->
            // Reusing AffiliateProductCard from treatment package or defining a local one
            AffiliateProductCard(tool, emeraldGreen)
        }

        Spacer(Modifier.height(16.dp))

        // Disclaimer
        MedicalDisclaimerBanner(modifier = Modifier.padding(16.dp))
        
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun PreparationStep(step: Int, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(Color(0xFF10B981), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("$step", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Medium)
            Text(description, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun DosageRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Medium)
    }
}
