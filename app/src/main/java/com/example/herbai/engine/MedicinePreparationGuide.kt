package com.example.herbai.engine

/**
 * Medicine Preparation Guide - Hướng dẫn pha chế và sử dụng thuốc
 * Cung cấp liều lượng, cách sắc, cách uống theo chuẩn Trung Dược Học
 */
class MedicinePreparationGuide {

    /**
     * Get detailed preparation instructions for a formula
     */
    fun getPreparationGuide(formulaId: String): PreparationGuide {
        // Example guides for common formulas
        return when (formulaId) {
            "F001" -> PreparationGuide(
                formulaName = "Bổ trung ích khí thang",
                ingredients = listOf(
                    IngredientDosage("Hoàng kỳ", 15f, "g", "Quân - Bổ khí thăng dương"),
                    IngredientDosage("Nhân sâm", 10f, "g", "Thần - Đại bổ nguyên khí"),
                    IngredientDosage("Bạch truật", 10f, "g", "Thần - Kiện tỳ táo thấp"),
                    IngredientDosage("Cam thảo", 5f, "g", "Sứ - Điều hòa các vị"),
                    IngredientDosage("Đương quy", 10f, "g", "Tá - Bổ huyết"),
                    IngredientDosage("Trần bì", 6f, "g", "Tá - Lý khí"),
                    IngredientDosage("Thăng ma", 6f, "g", "Sứ - Thăng đề dương khí"),
                    IngredientDosage("Sài hồ", 6f, "g", "Sứ - Thăng dương giải uất")
                ),
                preparationMethod = PreparationMethod(
                    step1_soak = "Ngâm thuốc trong 500ml nước lạnh trong 30 phút",
                    step2_firstBoil = "Đun sôi, sau đó giảm lửa nhỏ, sắc trong 30 phút",
                    step3_strain = "Lọc lấy nước thứ nhất (khoảng 200ml)",
                    step4_secondBoil = "Thêm 400ml nước, sắc lần 2 trong 20 phút",
                    step5_combine = "Trộn 2 lần nước, chia làm 2 phần"
                ),
                dosageInstruction = DosageInstruction(
                    singleDose = "150-200ml mỗi lần",
                    frequency = "2 lần/ngày",
                    timing = "Uống ấm, sau bữa ăn 30 phút",
                    duration = "7-14 ngày (hoặc theo chỉ định thầy thuốc)",
                    adjustForAge = mapOf(
                        "Trẻ 3-6 tuổi" to "1/3 liều người lớn",
                        "Trẻ 6-12 tuổi" to "1/2 liều người lớn",
                        "Người cao tuổi" to "2/3 liều người lớn"
                    )
                ),
                storageInstruction = "Bảo quản nước thuốc trong tủ lạnh, dùng trong 24 giờ. Hâm nóng trước khi uống.",
                warnings = listOf(
                    "⚠️ Không dùng cho người thực nhiệt, sốt cao",
                    "⚠️ Phụ nữ mang thai cần tham khảo thầy thuốc",
                    "⚠️ Ngưng dùng nếu có dấu hiệu dị ứng"
                )
            )
            
            else -> getDefaultGuide(formulaId)
        }
    }

    private fun getDefaultGuide(formulaId: String): PreparationGuide {
        return PreparationGuide(
            formulaName = "Bài thuốc $formulaId",
            ingredients = emptyList(),
            preparationMethod = PreparationMethod(
                step1_soak = "Ngâm thuốc trong 500ml nước lạnh 30 phút",
                step2_firstBoil = "Đun sôi, hạ lửa nhỏ, sắc 30-40 phút",
                step3_strain = "Lọc lấy nước thuốc lần 1",
                step4_secondBoil = "Thêm 400ml nước, sắc lần 2 trong 20 phút",
                step5_combine = "Trộn đều 2 lần nước"
            ),
            dosageInstruction = DosageInstruction(
                singleDose = "150-200ml",
                frequency = "2 lần/ngày",
                timing = "Sáng và tối, sau ăn 30 phút",
                duration = "Theo chỉ định thầy thuốc",
                adjustForAge = emptyMap()
            ),
            storageInstruction = "Bảo quản lạnh, dùng trong 24h",
            warnings = listOf("⚠️ Vui lòng tham khảo thầy thuốc trước khi sử dụng")
        )
    }

    /**
     * Calculate dosage based on patient weight/age
     */
    fun calculatePersonalizedDosage(
        baseIngredients: List<IngredientDosage>,
        patientWeight: Float, // kg
        patientAge: Int
    ): List<IngredientDosage> {
        val adjustmentFactor = when {
            patientAge < 6 -> 0.33f
            patientAge < 12 -> 0.5f
            patientAge < 18 -> 0.75f
            patientAge > 65 -> 0.67f
            patientWeight < 50 -> 0.8f
            patientWeight > 80 -> 1.2f
            else -> 1.0f
        }

        return baseIngredients.map { ingredient ->
            ingredient.copy(
                amount = (ingredient.amount * adjustmentFactor).let { 
                    // Round to nearest 0.5g
                    (Math.round(it * 2) / 2f)
                }
            )
        }
    }

    /**
     * Create a reminder schedule for taking medicine
     */
    fun createMedicineSchedule(
        formulaName: String,
        frequency: Int, // times per day
        durationDays: Int,
        startTimeHour: Int = 8 // Default start at 8 AM
    ): List<MedicineReminder> {
        val reminders = mutableListOf<MedicineReminder>()
        val intervalHours = if (frequency > 1) (12 / (frequency - 1)) else 0
        
        for (day in 1..durationDays) {
            for (dose in 0 until frequency) {
                val hour = startTimeHour + (dose * intervalHours)
                reminders.add(
                    MedicineReminder(
                        formulaName = formulaName,
                        day = day,
                        timeHour = hour.coerceIn(6, 22),
                        instruction = when (dose) {
                            0 -> "Uống sau bữa sáng 30 phút"
                            frequency - 1 -> "Uống sau bữa tối 30 phút"
                            else -> "Uống sau bữa trưa 30 phút"
                        }
                    )
                )
            }
        }
        return reminders
    }
    /**
     * Suggest high-quality tools for decocting medicine (Multi-Platform Support)
     */
    fun getRecommendedTools(region: String = "VN"): List<com.example.herbai.model.AffiliateProduct> {
        val currency = if (region == "VN") "VND" else "USD"
        
        return listOf(
            com.example.herbai.model.AffiliateProduct(
                "T001", 
                if (region == "VN") "Siêu sắc thuốc gốm sứ Bát Tràng" else "Ceramic Herb Decoction Pot",
                "Automatic, safe, authentic ceramic",
                if (region == "VN") 650000.0 else 55.0,
                currency, "https://example.com/pot1.jpg",
                if (region == "VN") com.example.herbai.config.AffiliateConfig.getLink("T001", region) else com.example.herbai.config.AffiliateConfig.getLink("T001", "INTL"),
                "Automatic ceramic herb decoction pot",
                if (region == "VN") "Shopee" else "AliExpress",
                "Decoction Pot", region, 5.0f, 150, true
            ),
            com.example.herbai.model.AffiliateProduct(
                "T002",
                if (region == "VN") "Túi lọc thuốc thảo dược Cotton" else "Natural Cotton Herb Filter Bags",
                "Extra fine, heat resistant",
                if (region == "VN") 85000.0 else 9.99,
                currency, "https://example.com/bag1.jpg",
                if (region == "VN") com.example.herbai.config.AffiliateConfig.getLink("T002", region) else com.example.herbai.config.AffiliateConfig.getLink("T002", "INTL"),
                "Natural cotton herb filter bags",
                if (region == "VN") "Shopee" else "eBay",
                "Filter Bag", region, 4.9f, 3200, true
            )
        )
    }
}

data class PreparationGuide(
    val formulaName: String,
    val ingredients: List<IngredientDosage>,
    val preparationMethod: PreparationMethod,
    val dosageInstruction: DosageInstruction,
    val storageInstruction: String,
    val warnings: List<String>
)

data class IngredientDosage(
    val name: String,
    val amount: Float,
    val unit: String,
    val role: String // Quân/Thần/Tá/Sứ and function
)

data class PreparationMethod(
    val step1_soak: String,
    val step2_firstBoil: String,
    val step3_strain: String,
    val step4_secondBoil: String,
    val step5_combine: String
)

data class DosageInstruction(
    val singleDose: String,
    val frequency: String,
    val timing: String,
    val duration: String,
    val adjustForAge: Map<String, String>
)

data class MedicineReminder(
    val formulaName: String,
    val day: Int,
    val timeHour: Int,
    val instruction: String
)
