package com.example.herbai.engine

import com.example.herbai.data.AcupointDao
import com.example.herbai.model.Acupoint

/**
 * Acupoint Engine - Quản lý và gợi ý huyệt vị
 * Phối hợp huyệt vị theo hội chứng bệnh và hỗ trợ phương thuốc
 */
class AcupointEngine(private val acupointDao: AcupointDao) {

    /**
     * Suggest acupoints based on the diagnosed syndrome
     */
    suspend fun getTreatmentProtocol(syndrome: String): List<Acupoint> {
        return acupointDao.getAcupointsForSyndrome(syndrome)
    }

    /**
     * Get a static mapping for common syndromes if DB is not yet seeded
     */
    fun getCommonAcupoints(syndrome: String): List<Acupoint> {
        return when {
            syndrome.contains("Phế âm hư", ignoreCase = true) -> listOf(
                Acupoint("LU7", "Liệt khuyết", "Broken Sequence", "列缺", "列缺", "Trên lằn chỉ cổ tay 1.5 thốn", "1.5 cun above the wrist crease", "腕横纹上1.5寸", "Ho, suyễn", "Cough, asthma", "咳嗽，喘息", "Bấm huyệt", "Massage", "按摩", "https://example.com/lu7_vi.jpg", "https://example.com/lu7_en.jpg", "https://example.com/lu7_zh.jpg")
            )
            syndrome.contains("Can dương", ignoreCase = true) -> listOf(
                Acupoint("LR3", "Thái xung", "Great Surge", "太衝", "太衝", "Kẽ ngón chân 1-2 đo lên 2 thốn", "2 cun above the web between 1st/2nd toes", "第一、二趾跖骨结合部前方凹陷中", "Bình can giáng hỏa", "Calm Liver, drain Fire", "平肝降逆", "Bấm mạnh", "Strong pressure", "强力按压", "https://example.com/lr3_vi.jpg", "https://example.com/lr3_en.jpg", "https://example.com/lr3_zh.jpg")
            )
            else -> emptyList()
        }
    }
    /**
     * Suggest high-quality equipment for acupressure (Multi-Platform Support)
     */
    fun getRecommendedEquipments(syndrome: String, region: String = "VN"): List<com.example.herbai.model.AffiliateProduct> {
        val currency = if (region == "VN") "VND" else "USD"
        
        return when {
            syndrome.contains("Can dương", ignoreCase = true) || syndrome.contains("đầu", ignoreCase = true) -> listOf(
                com.example.herbai.model.AffiliateProduct(
                    "P001", 
                    if (region == "VN") "Máy massage đầu Premium" else "Premium Head Massager",
                    "Infrared, 4-mode therapeutic",
                    if (region == "VN") 550000.0 else 45.0,
                    currency, "https://example.com/p1.jpg",
                    if (region == "VN") com.example.herbai.config.AffiliateConfig.getLink("P001", region) else com.example.herbai.config.AffiliateConfig.getLink("P001", "INTL"), // AccessTrade for international
                    "Professional head massager infrared",
                    if (region == "VN") "Shopee" else "AliExpress",
                    "Acupressure", region, 4.9f, 2500, true
                )
            )
            else -> listOf(
                com.example.herbai.model.AffiliateProduct(
                    "P003",
                    if (region == "VN") "Bút dò huyệt thế hệ mới" else "Electronic Acupuncture Pen",
                    "Accurate acupoint detection",
                    if (region == "VN") 280000.0 else 19.99,
                    currency, "https://example.com/p3.jpg",
                    if (region == "VN") com.example.herbai.config.AffiliateConfig.getLink("P003", region) else com.example.herbai.config.AffiliateConfig.getLink("P003", "INTL"),
                    "Electronic Acupuncture Pen multi-functional",
                    if (region == "VN") "Shopee" else "eBay",
                    "Acupoint Tool", region, 4.7f, 3200, false
                )
            )
        }
    }

    /**
     * Explains the synergy between a formula and acupoints
     */
    fun getSynergyExplanation(formulaName: String, syndrome: String): String {
        return when {
            formulaName.contains("Bổ trung ích khí") && syndrome.contains("khí hư") -> 
                "Sự kết hợp: Bài thuốc Bổ trung ích khí giúp thăng đề dương khí từ bên trong, phối hợp huyệt Túc tam lý và Bách hội giúp tăng cường hiệu quả bổ khí và nâng tạng phủ bị sa."
            formulaName.contains("Lục vị") && syndrome.contains("âm hư") ->
                "Sự kết hợp: Bài thuốc Lục vị địa hoàng bổ thận âm, phối hợp huyệt Thái khê và Chiếu hải giúp tư âm giáng hỏa, đặc biệt hiệu quả trong các chứng họng khô, triều nhiệt."
            else -> "Sự kết hợp giữa thảo dược và châm cứu giúp thông kinh hoạt lạc, tiêu trừ tà khí và bồi bổ chính khí toàn diện."
        }
    }
}
