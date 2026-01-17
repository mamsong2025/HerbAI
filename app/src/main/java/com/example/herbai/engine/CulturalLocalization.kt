package com.example.herbai.engine

import com.example.herbai.engine.LocalizationEngine.AppLanguage

/**
 * Cultural Localization Engine - Điều chỉnh nội dung theo phong tục tập quán từng quốc gia
 * 
 * Mỗi ngôn ngữ không chỉ là dịch thuật, mà còn là điều chỉnh:
 * - Thuật ngữ y học (TCM vs 한의학 vs Western)
 * - Thảo dược phổ biến tại địa phương
 * - Đơn vị đo lường (g vs 돈 vs oz)
 * - Màu sắc & biểu tượng văn hóa
 * - Phong cách giao tiếp (formal/informal)
 */
object CulturalLocalization {

    data class CulturalContext(
        val medicineTerminology: String,      // Cách gọi Y học cổ truyền
        val primaryHerbs: List<String>,       // Thảo dược phổ biến tại quốc gia
        val weightUnit: String,               // Đơn vị cân nặng thuốc
        val weightUnitSymbol: String,
        val conversionRate: Float,            // Quy đổi từ gram
        val currencySymbol: String,
        val dateFormat: String,
        val formalityLevel: Int,              // 1-3 (casual, normal, formal)
        val culturalColors: CulturalColors,
        val disclaimer: String                // Cảnh báo y tế theo văn hóa
    )

    data class CulturalColors(
        val primary: Long,      // Màu chủ đạo
        val warning: Long,      // Màu cảnh báo (đỏ có thể là may mắn ở TQ)
        val success: Long,
        val background: Long
    )

    /**
     * Get cultural context based on current language
     */
    fun getCulturalContext(): CulturalContext {
        return when (LocalizationEngine.getCurrentLanguage()) {
            AppLanguage.VIETNAMESE -> CulturalContext(
                medicineTerminology = "Đông Y / Y học Cổ truyền",
                primaryHerbs = listOf("Hoàng kỳ", "Nhân sâm", "Cam thảo", "Đương quy", "Bạch truật"),
                weightUnit = "gram",
                weightUnitSymbol = "g",
                conversionRate = 1.0f,
                currencySymbol = "₫",
                dateFormat = "dd/MM/yyyy",
                formalityLevel = 2,
                culturalColors = CulturalColors(
                    primary = 0xFF10B981,   // Emerald green (tự nhiên, thuần khiết)
                    warning = 0xFFDC2626,   // Red (cảnh báo)
                    success = 0xFF059669,
                    background = 0xFFF3F4F6
                ),
                disclaimer = "Ứng dụng chỉ mang tính tham khảo. Vui lòng hỏi ý kiến thầy thuốc trước khi sử dụng."
            )

            AppLanguage.KOREAN -> CulturalContext(
                medicineTerminology = "한의학 (韓醫學)",
                primaryHerbs = listOf("인삼 (Nhân sâm)", "황기 (Hoàng kỳ)", "감초 (Cam thảo)", "당귀 (Đương quy)", "백출 (Bạch truật)"),
                weightUnit = "돈",  // Traditional Korean unit (1돈 = 3.75g)
                weightUnitSymbol = "돈",
                conversionRate = 0.267f, // 1g = 0.267돈
                currencySymbol = "₩",
                dateFormat = "yyyy년 MM월 dd일",
                formalityLevel = 3, // Korean culture uses formal speech
                culturalColors = CulturalColors(
                    primary = 0xFF2563EB,   // Royal blue (trustworthy, professional)
                    warning = 0xFFEA580C,   // Orange (less aggressive than red)
                    success = 0xFF059669,
                    background = 0xFFF8FAFC
                ),
                disclaimer = "본 앱은 교육 목적으로만 제공됩니다. 사용 전 반드시 한의사와 상담하세요."
            )

            AppLanguage.CHINESE -> CulturalContext(
                medicineTerminology = "中医药 / 中医学",
                primaryHerbs = listOf("黄芪", "人参", "甘草", "当归", "白术"),
                weightUnit = "克",
                weightUnitSymbol = "克",
                conversionRate = 1.0f,
                currencySymbol = "¥",
                dateFormat = "yyyy年MM月dd日",
                formalityLevel = 2,
                culturalColors = CulturalColors(
                    primary = 0xFFDC2626,   // Red (prosperity, good fortune in Chinese culture)
                    warning = 0xFFD97706,   // Amber (caution)
                    success = 0xFF059669,
                    background = 0xFFFEF2F2 // Light red tint
                ),
                disclaimer = "本应用仅供教育参考。使用前请咨询专业中医师。"
            )

            AppLanguage.ENGLISH -> CulturalContext(
                medicineTerminology = "Traditional Chinese Medicine (TCM)",
                primaryHerbs = listOf("Astragalus", "Ginseng", "Licorice Root", "Dong Quai", "Atractylodes"),
                weightUnit = "grams",
                weightUnitSymbol = "g",
                conversionRate = 1.0f,
                currencySymbol = "$",
                dateFormat = "MM/dd/yyyy",
                formalityLevel = 1, // More casual in Western apps
                culturalColors = CulturalColors(
                    primary = 0xFF10B981,   // Emerald green (health, nature)
                    warning = 0xFFDC2626,   // Red (universal warning)
                    success = 0xFF059669,
                    background = 0xFFF9FAFB
                ),
                disclaimer = "This app is for educational purposes only. Please consult a healthcare professional before use."
            )
        }
    }

    /**
     * Convert weight to local unit
     */
    fun convertWeight(grams: Float): Pair<Float, String> {
        val context = getCulturalContext()
        val converted = grams * context.conversionRate
        return Pair(converted, context.weightUnitSymbol)
    }

    /**
     * Format weight with local unit
     */
    fun formatWeight(grams: Float): String {
        val (value, unit) = convertWeight(grams)
        return if (unit == "돈") {
            String.format("%.1f%s", value, unit)
        } else {
            String.format("%.0f%s", value, unit)
        }
    }

    /**
     * Get culturally appropriate greeting based on time of day
     */
    fun getGreeting(): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (LocalizationEngine.getCurrentLanguage()) {
            AppLanguage.VIETNAMESE -> when {
                hour < 12 -> "Chào buổi sáng!"
                hour < 18 -> "Chào buổi chiều!"
                else -> "Chào buổi tối!"
            }
            AppLanguage.KOREAN -> when {
                hour < 12 -> "좋은 아침이에요!" // Formal Korean
                hour < 18 -> "좋은 오후에요!"
                else -> "좋은 저녁이에요!"
            }
            AppLanguage.CHINESE -> when {
                hour < 12 -> "早上好！"
                hour < 18 -> "下午好！"
                else -> "晚上好！"
            }
            AppLanguage.ENGLISH -> when {
                hour < 12 -> "Good morning!"
                hour < 18 -> "Good afternoon!"
                else -> "Good evening!"
            }
        }
    }

    /**
     * Get medicine system explanation for the region
     */
    fun getMedicineSystemInfo(): String {
        return when (LocalizationEngine.getCurrentLanguage()) {
            AppLanguage.VIETNAMESE -> """
                Đông Y (Y học Cổ truyền Việt Nam) là hệ thống y học kế thừa từ Trung Quốc, 
                kết hợp với kinh nghiệm dân gian Việt Nam. Đặc trưng bởi việc sử dụng 
                thảo dược bản địa và phương pháp châm cứu, bấm huyệt.
            """.trimIndent()
            
            AppLanguage.KOREAN -> """
                한의학(韓醫學)은 한국 고유의 전통의학으로, 중국 중의학에서 기원하였으나 
                한국의 기후와 체질에 맞게 발전했습니다. 사상의학과 한국 고유 약재를 
                특징으로 합니다.
            """.trimIndent()
            
            AppLanguage.CHINESE -> """
                中医药是中国传统医学体系，有数千年历史。以阴阳五行理论为基础，
                强调整体观念和辨证论治，包括中药、针灸、推拿等治疗方法。
            """.trimIndent()
            
            AppLanguage.ENGLISH -> """
                Traditional Chinese Medicine (TCM) is an ancient healing system 
                with thousands of years of history. It emphasizes holistic health, 
                using herbal remedies, acupuncture, and lifestyle practices.
            """.trimIndent()
        }
    }
}
