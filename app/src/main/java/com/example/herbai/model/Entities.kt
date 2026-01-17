package com.example.herbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey val id: Int,
    val han_name: String,
    val pinyin: String,
    val latin_name: String,
    val vietnamese_name: String,
    val name_en: String? = null,
    val name_zh: String? = null,
    val name_ko: String? = null,
    val family: String,
    val used_part: String,
    val main_image_url: String,
    val image_url_en: String? = null,
    val image_url_zh: String? = null,
    val image_url_ko: String? = null,
    val effects: String,
    val effects_en: String? = null,
    val effects_zh: String? = null,
    val effects_ko: String? = null,
    val indications: String,
    val indications_en: String? = null,
    val indications_zh: String? = null,
    val indications_ko: String? = null,
    val safety_tag: String
)

@Entity(tableName = "formulas")
data class Formula(
    @PrimaryKey val formula_id: String,
    val name: String,
    val name_en: String? = null,
    val name_zh: String? = null,
    val name_ko: String? = null,
    val indication: String,
    val indication_en: String? = null,
    val indication_zh: String? = null,
    val indication_ko: String? = null,
    val contraindications: String
)

@Entity(tableName = "formula_ingredients")
data class FormulaIngredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val formula_id: String,
    val plant_id: Int,
    val role: String // Quân, Thần, Tá, Sứ
)

@Entity(tableName = "incompatibilities")
data class Incompatibility(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plant_a: String,
    val plant_b: String,
    val rule: String,
    val warning: String
)

@Entity(tableName = "icd_mapping")
data class IcdMapping(
    @PrimaryKey val icd_code: String,
    val icd_name: String,
    val tcm_syndrome: String,
    val recommended_formula_id: String?
)

@Entity(tableName = "acupoints")
data class Acupoint(
    @PrimaryKey val id: String,
    val name_vietnamese: String,
    val name_en: String? = null,
    val name_zh: String? = null,
    val name_ko: String? = null,
    val name_han: String,
    val location: String,
    val location_en: String? = null,
    val location_zh: String? = null,
    val location_ko: String? = null,
    val indication: String,
    val indication_en: String? = null,
    val indication_zh: String? = null,
    val indication_ko: String? = null,
    val technique: String,
    val technique_en: String? = null,
    val technique_zh: String? = null,
    val technique_ko: String? = null,
    val image_url: String? = null,
    val image_url_en: String? = null,
    val image_url_zh: String? = null,
    val image_url_ko: String? = null
)

@Entity(tableName = "syndrome_acupoints")
data class SyndromeAcupoint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val syndrome_name: String,
    val acupoint_id: String,
    val role: String // e.g., "Chính huyệt", "Phối huyệt"
)

@Entity(tableName = "affiliate_products")
data class AffiliateProduct(
    @PrimaryKey val product_id: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val image_url: String,
    val affiliate_link: String?, // Nullable if not yet approved
    val fallback_search_query: String?, // e.g., "Automatic herb decoction pot"
    val platform: String, // e.g., "Shopee", "AliExpress", "eBay", "Amazon"
    val category: String,
    val region: String,
    val rating: Float,
    val review_count: Int,
    val is_top_rated: Boolean = false
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val region: String = "VN", // Default to VN
    val preferredCurrency: String = "VND"
)
