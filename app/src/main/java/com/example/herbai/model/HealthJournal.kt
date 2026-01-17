package com.example.herbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Health Journal Entry - Theo dõi sức khỏe hàng ngày
 */
@Entity(tableName = "health_journal")
data class HealthJournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // Format: yyyy-MM-dd
    val timestamp: Long = System.currentTimeMillis(),
    
    // Triệu chứng hàng ngày
    val symptoms: String, // JSON array of symptoms
    val symptomSeverity: Int, // 1-10 scale
    
    // Chỉ số sức khỏe
    val heartRate: Int? = null,
    val sleepHours: Float? = null,
    val steps: Int? = null,
    val bloodPressureSystolic: Int? = null,
    val bloodPressureDiastolic: Int? = null,
    
    // Đông y indicators
    val tongueCoating: String? = null, // Rêu lưỡi: trắng mỏng, vàng đậm, etc.
    val tongueBody: String? = null, // Chất lưỡi: đỏ, nhạt, tím, etc.
    val pulseQuality: String? = null, // Mạch: phù, trầm, sác, trì, etc.
    
    // Ghi chú
    val notes: String? = null,
    
    // AI diagnosis result
    val aiSyndrome: String? = null,
    val aiConfidence: Float? = null
)

/**
 * Daily Health Advice - Lời khuyên theo dõi hàng ngày
 */
@Entity(tableName = "daily_advice")
data class DailyAdvice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val syndrome: String,
    val dietAdvice: String, // Chế độ ăn
    val lifestyleAdvice: String, // Sinh hoạt
    val teaRecommendation: String, // Trà/nước uống
    val exerciseAdvice: String, // Vận động
    val warningSign: String // Dấu hiệu cần gặp bác sĩ
)
