package com.example.herbai.engine

import com.example.herbai.model.IcdMapping

/**
 * Symptom Checker Engine - Phân biệt bệnh theo triệu chứng
 * Hỗ trợ chẩn đoán sơ bộ dựa trên triệu chứng người dùng nhập vào
 */
class SymptomCheckerEngine {

    // Database of symptoms mapped to possible syndromes
    private val symptomDatabase = mapOf(
        // Ho (Cough) variants
        "ho khan" to listOf(
            DifferentialDiagnosis("Phế âm hư", listOf("ho khan", "họng khô", "sốt nhẹ về chiều"), "Lục vị địa hoàng hoàn"),
            DifferentialDiagnosis("Táo nhiệt phạm phế", listOf("ho khan", "họng đau", "khát nước"), "Tang hạnh thang")
        ),
        "ho có đờm" to listOf(
            DifferentialDiagnosis("Đàm thấp trở phế", listOf("ho có đờm trắng", "ngực tức", "chán ăn"), "Nhị trần thang"),
            DifferentialDiagnosis("Đàm nhiệt", listOf("ho đờm vàng đặc", "sốt", "khát nước"), "Thanh kim hóa đàm thang")
        ),
        
        // Đau đầu (Headache) variants
        "đau đầu" to listOf(
            DifferentialDiagnosis("Can dương thượng kháng", listOf("đau đầu", "chóng mặt", "mặt đỏ", "dễ cáu"), "Thiên ma câu đằng ẩm"),
            DifferentialDiagnosis("Huyết hư", listOf("đau đầu âm ỉ", "mệt mỏi", "da xanh xao"), "Tứ vật thang"),
            DifferentialDiagnosis("Phong hàn", listOf("đau đầu", "sợ lạnh", "chảy nước mũi trong"), "Xuyên khung trà điều tán")
        ),
        
        // Mất ngủ (Insomnia) variants
        "mất ngủ" to listOf(
            DifferentialDiagnosis("Tâm tỳ lưỡng hư", listOf("mất ngủ", "hay quên", "mệt mỏi", "chán ăn"), "Quy tỳ thang"),
            DifferentialDiagnosis("Tâm thận bất giao", listOf("khó ngủ", "hồi hộp", "đau lưng", "ù tai"), "Lục vị địa hoàng hoàn gia vị"),
            DifferentialDiagnosis("Can hỏa vượng", listOf("mất ngủ", "dễ cáu", "miệng đắng", "mắt đỏ"), "Long đởm tả can thang")
        ),
        
        // Đau bụng (Abdominal pain) variants
        "đau bụng" to listOf(
            DifferentialDiagnosis("Tỳ vị hư hàn", listOf("đau bụng âm ỉ", "thích xoa ấm", "tiêu lỏng"), "Lý trung hoàn"),
            DifferentialDiagnosis("Can khí phạm vị", listOf("đau bụng lan ra ngực sườn", "ợ hơi", "stress"), "Sài hồ sơ can tán"),
            DifferentialDiagnosis("Thực tích", listOf("đau bụng đầy trướng", "ợ chua", "ăn không tiêu"), "Bảo hòa hoàn")
        ),
        
        // Mệt mỏi (Fatigue) variants
        "mệt mỏi" to listOf(
            DifferentialDiagnosis("Khí hư", listOf("mệt mỏi", "thở ngắn", "hay ra mồ hôi", "ăn kém"), "Bổ trung ích khí thang"),
            DifferentialDiagnosis("Huyết hư", listOf("mệt mỏi", "da xanh", "chóng mặt", "móng tay nhợt"), "Tứ vật thang"),
            DifferentialDiagnosis("Thận dương hư", listOf("mệt mỏi", "sợ lạnh", "đau lưng", "tiểu đêm"), "Kim quỹ thận khí hoàn")
        )
    )

    /**
     * Analyzes symptoms and returns differential diagnoses
     */
    fun analyzeSymptoms(symptoms: List<String>): List<DiagnosisResult> {
        val results = mutableListOf<DiagnosisResult>()
        
        for (symptom in symptoms) {
            val normalizedSymptom = symptom.lowercase().trim()
            
            // Find matching symptom patterns
            for ((key, diagnoses) in symptomDatabase) {
                if (normalizedSymptom.contains(key) || key.contains(normalizedSymptom)) {
                    for (diagnosis in diagnoses) {
                        val matchScore = calculateMatchScore(symptoms, diagnosis.relatedSymptoms)
                        results.add(DiagnosisResult(
                            syndrome = diagnosis.syndrome,
                            matchScore = matchScore,
                            matchedSymptoms = diagnosis.relatedSymptoms.filter { s -> 
                                symptoms.any { it.lowercase().contains(s) } 
                            },
                            suggestedFormula = diagnosis.suggestedFormula
                        ))
                    }
                }
            }
        }
        
        // Remove duplicates and sort by match score
        return results
            .distinctBy { it.syndrome }
            .sortedByDescending { it.matchScore }
            .take(5) // Top 5 most likely
    }

    private fun calculateMatchScore(userSymptoms: List<String>, diseaseSymptoms: List<String>): Float {
        val userLower = userSymptoms.map { it.lowercase() }
        val matchCount = diseaseSymptoms.count { ds ->
            userLower.any { us -> us.contains(ds) || ds.contains(us) }
        }
        return matchCount.toFloat() / diseaseSymptoms.size
    }

    /**
     * Get similar diseases for differentiation
     */
    fun getDifferentialDiagnoses(syndrome: String): List<DifferentialDiagnosis> {
        return symptomDatabase.values.flatten().filter { 
            it.syndrome != syndrome && 
            it.relatedSymptoms.any { s -> 
                symptomDatabase.values.flatten()
                    .find { it.syndrome == syndrome }
                    ?.relatedSymptoms?.contains(s) == true
            }
        }
    }
}

data class DifferentialDiagnosis(
    val syndrome: String,
    val relatedSymptoms: List<String>,
    val suggestedFormula: String
)

data class DiagnosisResult(
    val syndrome: String,
    val matchScore: Float,
    val matchedSymptoms: List<String>,
    val suggestedFormula: String
)
