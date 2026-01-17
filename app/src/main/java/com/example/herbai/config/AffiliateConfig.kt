package com.example.herbai.config

import com.example.herbai.engine.LocalizationEngine

/**
 * Centralized Affiliate Configuration
 * Update these links with your actual Affiliate IDs from Shopee, Amazon, etc.
 */
object AffiliateConfig {
    
    // Platform Base URLs
    const val SHOPEE_BASE = "https://shopee.vn"
    const val ACCESSTRADE_BASE = "https://pub.accesstrade.vn/deep_link"
    const val AMAZON_BASE = "https://www.amazon.com/dp"
    
    // ========== TOOLS & EQUIPMENT (Dụng cụ) ==========
    private val TOOLS_VN = mapOf(
        "P001" to "https://shopee.vn/product/12345/678910?aff_id=YOUR_VN_ID", // Head Massager
        "P003" to "https://shopee.vn/product/12345/678911?aff_id=YOUR_VN_ID", // Acupuncture Pen
        "T001" to "https://shopee.vn/product/12345/678912?aff_id=YOUR_VN_ID", // Ceramic Pot
        "T002" to "https://shopee.vn/product/12345/678913?aff_id=YOUR_VN_ID"  // Filter Bags
    )
    
    // ========== WEARABLE DEVICES (Vòng tay thông minh) ==========
    private val WEARABLES_VN = mapOf(
        "W001" to "https://shopee.vn/product/xiaomi-mi-band-8?aff_id=YOUR_VN_ID",
        "W002" to "https://shopee.vn/product/samsung-galaxy-watch?aff_id=YOUR_VN_ID",
        "W003" to "https://shopee.vn/product/amazfit-gtr?aff_id=YOUR_VN_ID",
        "W004" to "https://shopee.vn/product/huawei-band?aff_id=YOUR_VN_ID"
    )
    
    private val WEARABLES_INTL = mapOf(
        "W001" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/dp/B0C5QJ9BLR", // Mi Band
        "W002" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/dp/B0B9HLDMQG", // Galaxy Watch
        "W003" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/dp/B0B8J5Q1VK"  // Amazfit
    )
    
    // ========== HERBS (Thảo dược) ==========
    private val HERBS_VN = mapOf(
        "HERB_HOANG_KY" to "https://shopee.vn/search?keyword=Hoàng+Kỳ+nguyên+liệu&aff_id=YOUR_VN_ID",
        "HERB_NHAN_SAM" to "https://shopee.vn/search?keyword=Nhân+Sâm+Hàn+Quốc&aff_id=YOUR_VN_ID",
        "HERB_BACH_TRUAT" to "https://shopee.vn/search?keyword=Bạch+Truật+khô&aff_id=YOUR_VN_ID",
        "HERB_CAM_THAO" to "https://shopee.vn/search?keyword=Cam+Thảo+nguyên+liệu&aff_id=YOUR_VN_ID",
        "HERB_DUONG_QUY" to "https://shopee.vn/search?keyword=Đương+Quy+thảo+dược&aff_id=YOUR_VN_ID",
        "HERB_THANG_MA" to "https://shopee.vn/search?keyword=Thăng+Ma+thuốc&aff_id=YOUR_VN_ID",
        "HERB_SAI_HO" to "https://shopee.vn/search?keyword=Sài+Hồ+nguyên+liệu&aff_id=YOUR_VN_ID",
        "HERB_TRAN_BI" to "https://shopee.vn/search?keyword=Trần+Bì+thuốc&aff_id=YOUR_VN_ID"
    )
    
    private val HERBS_INTL = mapOf(
        "HERB_HOANG_KY" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/s?k=Astragalus+Root",
        "HERB_NHAN_SAM" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/s?k=Korean+Ginseng",
        "HERB_BACH_TRUAT" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/s?k=Atractylodes",
        "HERB_CAM_THAO" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/s?k=Licorice+Root",
        "HERB_DUONG_QUY" to "https://pub.accesstrade.vn/deep_link/xxx?url=https://amazon.com/s?k=Dong+Quai"
    )
    
    /**
     * Get affiliate link for tools/equipment
     */
    fun getToolLink(productId: String, region: String = "VN", fallbackQuery: String? = null): String {
        return if (region == "VN") {
            TOOLS_VN[productId] ?: "https://shopee.vn/search?keyword=${fallbackQuery ?: "Thiết bị y tế"}"
        } else {
            "https://www.google.com/search?q=${fallbackQuery ?: "TCM Equipment"}"
        }
    }
    
    /**
     * Get affiliate link for wearable devices
     */
    fun getWearableLink(productId: String, region: String = "VN"): String {
        return if (region == "VN") {
            WEARABLES_VN[productId] ?: "https://shopee.vn/search?keyword=Vòng+tay+thông+minh"
        } else {
            WEARABLES_INTL[productId] ?: "https://amazon.com/s?k=fitness+tracker"
        }
    }
    
    /**
     * Get affiliate link for herbs by Vietnamese name
     */
    fun getHerbLink(herbName: String, region: String = "VN"): String {
        val herbId = "HERB_${herbName.uppercase().replace(" ", "_")}"
        return if (region == "VN") {
            HERBS_VN[herbId] ?: "https://shopee.vn/search?keyword=${herbName}+thảo+dược"
        } else {
            HERBS_INTL[herbId] ?: "https://amazon.com/s?k=${herbName}+herb"
        }
    }
    
    // Legacy function for backward compatibility
    fun getLink(productId: String, region: String = "VN", fallbackQuery: String? = null): String {
        return getToolLink(productId, region, fallbackQuery)
    }
    
    /**
     * Wearable product data for UI
     */
    data class WearableProduct(
        val id: String,
        val name: String,
        val description: String,
        val price: String,
        val rating: Float,
        val features: List<String>
    )
    
    fun getRecommendedWearables(region: String = "VN"): List<WearableProduct> {
        val currency = if (region == "VN") "₫" else "$"
        return listOf(
            WearableProduct(
                "W001",
                LocalizationEngine.getLocalizedString("Xiaomi Mi Band 8", "Xiaomi Mi Band 8", "小米手环8", "샤오미 미밴드 8"),
                LocalizationEngine.getLocalizedString("Theo dõi nhịp tim, SpO2, giấc ngủ", "Heart rate, SpO2, sleep tracking", "心率、血氧、睡眠监测", "심박수, 산소포화도, 수면 추적"),
                if (region == "VN") "890.000${currency}" else "45${currency}",
                4.8f,
                listOf("❤️ HR", "🫁 SpO2", "😴 Sleep")
            ),
            WearableProduct(
                "W002",
                LocalizationEngine.getLocalizedString("Samsung Galaxy Watch 6", "Samsung Galaxy Watch 6", "三星Galaxy Watch 6", "삼성 갤럭시 워치 6"),
                LocalizationEngine.getLocalizedString("ECG, huyết áp, thành phần cơ thể", "ECG, blood pressure, body composition", "心电图、血压、身体成分", "ECG, 혈압, 체성분"),
                if (region == "VN") "6.990.000${currency}" else "299${currency}",
                4.9f,
                listOf("💓 ECG", "🩸 BP", "📊 Body")
            ),
            WearableProduct(
                "W003",
                LocalizationEngine.getLocalizedString("Amazfit GTR 4", "Amazfit GTR 4", "Amazfit GTR 4", "어메이즈핏 GTR 4"),
                LocalizationEngine.getLocalizedString("Pin 14 ngày, GPS, 150+ chế độ thể thao", "14-day battery, GPS, 150+ sports modes", "14天续航、GPS、150+运动模式", "14일 배터리, GPS, 150+ 운동 모드"),
                if (region == "VN") "4.290.000${currency}" else "199${currency}",
                4.7f,
                listOf("🔋 14d", "📍 GPS", "🏃 150+")
            )
        )
    }
}
