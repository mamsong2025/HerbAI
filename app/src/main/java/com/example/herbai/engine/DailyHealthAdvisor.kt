package com.example.herbai.engine

/**
 * Daily Health Advisor - Tư vấn theo dõi sức khỏe hàng ngày
 * Dựa trên hội chứng đã chẩn đoán, đưa ra lời khuyên cá nhân hóa
 */
class DailyHealthAdvisor {

    /**
     * Generate personalized daily advice based on syndrome
     */
    fun generateDailyAdvice(syndrome: String): DailyHealthAdvice {
        return when {
            syndrome.contains("khí hư", ignoreCase = true) -> DailyHealthAdvice(
                syndrome = syndrome,
                dietAdvice = """
                    ✅ NÊN ĂN:
                    • Gạo nếp, khoai lang, bí đỏ
                    • Thịt gà, cá chép
                    • Đậu đỏ, hạt sen
                    
                    ❌ KIÊNG:
                    • Đồ sống lạnh
                    • Thực phẩm khó tiêu
                """.trimIndent(),
                teaRecommendation = "Trà Nhân sâm + Hoàng kỳ (5g mỗi loại, hãm 10 phút)",
                lifestyleAdvice = """
                    • Ngủ đủ 7-8 tiếng
                    • Tránh làm việc quá sức
                    • Giữ ấm vùng bụng
                """.trimIndent(),
                exerciseAdvice = "Tập nhẹ: Thái cực quyền, đi bộ chậm 20-30 phút",
                warningSign = "⚠️ Gặp bác sĩ ngay nếu: Mệt mỏi tăng, khó thở khi nghỉ, sốt"
            )
            
            syndrome.contains("huyết hư", ignoreCase = true) -> DailyHealthAdvice(
                syndrome = syndrome,
                dietAdvice = """
                    ✅ NÊN ĂN:
                    • Gan heo, thịt bò
                    • Rau chân vịt, củ dền
                    • Táo đỏ, long nhãn
                    
                    ❌ KIÊNG:
                    • Trà đặc, cà phê
                    • Đồ cay nóng
                """.trimIndent(),
                teaRecommendation = "Trà Đương quy + Táo đỏ (3 quả + 3g, nấu 15 phút)",
                lifestyleAdvice = """
                    • Nghỉ ngơi đầy đủ
                    • Tránh thức khuya
                    • Massage huyệt Túc tam lý
                """.trimIndent(),
                exerciseAdvice = "Yoga nhẹ, bài tập thở sâu 15 phút/ngày",
                warningSign = "⚠️ Gặp bác sĩ ngay nếu: Chóng mặt nặng, da vàng, xuất huyết"
            )
            
            syndrome.contains("can dương", ignoreCase = true) || syndrome.contains("hỏa vượng", ignoreCase = true) -> DailyHealthAdvice(
                syndrome = syndrome,
                dietAdvice = """
                    ✅ NÊN ĂN:
                    • Rau cần tây, dưa chuột
                    • Đậu xanh, bí đao
                    • Trái cây mát (lê, dưa hấu)
                    
                    ❌ KIÊNG:
                    • Rượu bia, cà phê
                    • Đồ chiên xào, cay nóng
                    • Thịt dê, thịt chó
                """.trimIndent(),
                teaRecommendation = "Trà Cúc hoa + Câu kỷ tử (5g mỗi loại)",
                lifestyleAdvice = """
                    • Tránh stress, giữ tâm bình
                    • Ngủ trước 23h
                    • Massage huyệt Thái xung
                """.trimIndent(),
                exerciseAdvice = "Thiền định 15 phút, đi bộ buổi sáng sớm",
                warningSign = "⚠️ Gặp bác sĩ ngay nếu: Đau đầu dữ dội, nôn mửa, huyết áp >180"
            )
            
            syndrome.contains("thận", ignoreCase = true) -> DailyHealthAdvice(
                syndrome = syndrome,
                dietAdvice = """
                    ✅ NÊN ĂN:
                    • Hạt óc chó, hạt điều
                    • Tôm, hàu, cá biển
                    • Đậu đen, vừng đen
                    
                    ❌ KIÊNG:
                    • Đồ lạnh, kem
                    • Uống quá nhiều nước
                """.trimIndent(),
                teaRecommendation = "Trà Đỗ trọng + Cẩu tích (5g mỗi loại)",
                lifestyleAdvice = """
                    • Giữ ấm lưng và chân
                    • Không nhịn tiểu
                    • Massage vùng thắt lưng
                """.trimIndent(),
                exerciseAdvice = "Bài tập Kegel, đứng thiền 10 phút/ngày",
                warningSign = "⚠️ Gặp bác sĩ ngay nếu: Tiểu ra máu, phù chân, đau lưng dữ dội"
            )
            
            else -> DailyHealthAdvice(
                syndrome = syndrome,
                dietAdvice = """
                    ✅ NGUYÊN TẮC CHUNG:
                    • Ăn đa dạng, cân bằng
                    • Ưu tiên thực phẩm tươi
                    • Uống đủ nước (1.5-2L/ngày)
                """.trimIndent(),
                teaRecommendation = "Trà Cam thảo + Đại táo (giải độc, bồi bổ)",
                lifestyleAdvice = """
                    • Ngủ đủ giấc
                    • Giảm stress
                    • Vận động đều đặn
                """.trimIndent(),
                exerciseAdvice = "Đi bộ 30 phút/ngày, tập thể dục nhẹ",
                warningSign = "⚠️ Gặp bác sĩ nếu triệu chứng kéo dài hơn 7 ngày"
            )
        }
    }

    /**
     * Compare similar diseases and highlight differences
     */
    fun compareDiseases(disease1: String, disease2: String): DiseaseComparison {
        // Example: Compare "Phế âm hư" vs "Phế khí hư"
        val comparisons = mapOf(
            Pair("Phế âm hư", "Phế khí hư") to DiseaseComparison(
                disease1 = "Phế âm hư",
                disease2 = "Phế khí hư",
                commonSymptoms = listOf("Ho", "Mệt mỏi", "Thở ngắn"),
                uniqueToFirst = listOf("Ho khan không đờm", "Sốt nhẹ về chiều", "Họng khô", "Lòng bàn tay nóng"),
                uniqueToSecond = listOf("Ho có đờm trắng loãng", "Ra mồ hôi tự nhiên", "Sợ lạnh", "Tiếng nói nhỏ yếu"),
                keyDifferentiator = "Phế ÂM hư: Ho KHAN, sốt chiều, họng khô\nPhế KHÍ hư: Ho có ĐỜM, ra mồ hôi, sợ lạnh"
            )
        )
        
        return comparisons[Pair(disease1, disease2)] 
            ?: comparisons[Pair(disease2, disease1)]
            ?: DiseaseComparison(
                disease1, disease2,
                commonSymptoms = emptyList(),
                uniqueToFirst = listOf("Chưa có dữ liệu"),
                uniqueToSecond = listOf("Chưa có dữ liệu"),
                keyDifferentiator = "Vui lòng tham khảo thầy thuốc để phân biệt"
            )
    }
}

data class DailyHealthAdvice(
    val syndrome: String,
    val dietAdvice: String,
    val teaRecommendation: String,
    val lifestyleAdvice: String,
    val exerciseAdvice: String,
    val warningSign: String
)

data class DiseaseComparison(
    val disease1: String,
    val disease2: String,
    val commonSymptoms: List<String>,
    val uniqueToFirst: List<String>,
    val uniqueToSecond: List<String>,
    val keyDifferentiator: String
)
