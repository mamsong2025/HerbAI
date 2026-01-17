package com.example.herbai.engine

import java.util.Locale

/**
 * Localization Engine - Quản lý đa ngôn ngữ
 * Hỗ trợ: Tiếng Việt (VI), Tiếng Trung (ZH), Tiếng Anh (EN), Tiếng Hàn (KO)
 * Tự động nhận diện hệ thống, mặc định Tiếng Anh nếu không thuộc danh sách.
 */
object LocalizationEngine {

    enum class AppLanguage(val code: String) {
        VIETNAMESE("vi"),
        CHINESE("zh"),
        ENGLISH("en"),
        KOREAN("ko")
    }

    /**
     * Detect current system language
     */
    fun getCurrentLanguage(): AppLanguage {
        val locale = Locale.getDefault().language
        return when {
            locale.startsWith("vi") -> AppLanguage.VIETNAMESE
            locale.startsWith("zh") -> AppLanguage.CHINESE
            locale.startsWith("ko") -> AppLanguage.KOREAN
            else -> AppLanguage.ENGLISH // Default fallback
        }
    }

    /**
     * Helper to getLocalized string from entity fields
     */
    fun getLocalizedString(
        vi: String,
        en: String? = null,
        zh: String? = null,
        ko: String? = null
    ): String {
        return when (getCurrentLanguage()) {
            AppLanguage.VIETNAMESE -> vi
            AppLanguage.CHINESE -> zh ?: en ?: vi
            AppLanguage.KOREAN -> ko ?: en ?: vi
            AppLanguage.ENGLISH -> en ?: vi
        }
    }

    /**
     * Helper to getLocalized image path/URL
     */
    fun getLocalizedImage(
        vi: String,
        en: String? = null,
        zh: String? = null,
        ko: String? = null
    ): String {
        return when (getCurrentLanguage()) {
            AppLanguage.VIETNAMESE -> vi
            AppLanguage.CHINESE -> zh ?: en ?: vi
            AppLanguage.KOREAN -> ko ?: en ?: vi
            AppLanguage.ENGLISH -> en ?: vi
        }
    }
}
