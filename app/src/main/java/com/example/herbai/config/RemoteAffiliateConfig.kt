package com.example.herbai.config

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await

/**
 * Remote Affiliate Configuration - Sử dụng Firebase Remote Config
 * Cho phép cập nhật link affiliate từ xa mà không cần phát hành bản cập nhật app
 * 
 * Cách sử dụng:
 * 1. Gọi RemoteAffiliateConfig.initialize() trong Application.onCreate()
 * 2. Sử dụng RemoteAffiliateConfig.getLink() thay vì AffiliateConfig.getLink()
 */
object RemoteAffiliateConfig {
    
    private const val TAG = "RemoteAffiliateConfig"
    
    // Unified Remote Config Keys (Firebase handles conditions)
    private const val KEY_WEARABLES = "affiliate_wearables"
    private const val KEY_HERBS = "affiliate_herbs"
    private const val KEY_TOOLS = "affiliate_tools"
    private const val KEY_AFFILIATE_ENABLED = "affiliate_enabled"
    
    // Cache for parsed links
    private var wearables: Map<String, String> = emptyMap()
    private var herbs: Map<String, String> = emptyMap()
    private var tools: Map<String, String> = emptyMap()
    private var isEnabled: Boolean = true
    
    /**
     * Initialize Remote Config with default values
     */
    suspend fun initialize() {
        try {
            val remoteConfig = Firebase.remoteConfig
            
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            remoteConfig.setConfigSettingsAsync(configSettings).await()
            remoteConfig.setDefaultsAsync(getDefaultValues()).await()
            remoteConfig.fetchAndActivate().await()
            
            parseRemoteValues()
            Log.d(TAG, "Remote Config initialized with unified keys")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Remote Config", e)
            useLocalDefaults()
        }
    }
    
    private fun getDefaultValues(): Map<String, Any> {
        return mapOf(
            KEY_AFFILIATE_ENABLED to true,
            KEY_WEARABLES to """
                {
                    "W001": "https://shopee.vn/product/mi-band-8?aff_id=FALLBACK",
                    "W002": "https://shopee.vn/product/galaxy-watch?aff_id=FALLBACK"
                }
            """.trimIndent(),
            KEY_HERBS to """
                {
                    "Hoàng kỳ": "https://shopee.vn/search?keyword=Hoang+Ky",
                    "Nhân sâm": "https://shopee.vn/search?keyword=Nhan+Sam"
                }
            """.trimIndent(),
            KEY_TOOLS to "{}"
        )
    }
    
    private fun parseRemoteValues() {
        val remoteConfig = Firebase.remoteConfig
        isEnabled = remoteConfig.getBoolean(KEY_AFFILIATE_ENABLED)
        
        wearables = parseJsonToMap(remoteConfig.getString(KEY_WEARABLES))
        herbs = parseJsonToMap(remoteConfig.getString(KEY_HERBS))
        tools = parseJsonToMap(remoteConfig.getString(KEY_TOOLS))
    }
    
    private fun parseJsonToMap(json: String): Map<String, String> {
        return try {
            val result = mutableMapOf<String, String>()
            if (json.isEmpty() || json == "{}") return emptyMap()
            
            val cleanJson = json.trim().removeSurrounding("{", "}")
            cleanJson.split(",").forEach { pair ->
                val parts = pair.split(":")
                if (parts.size >= 2) {
                    val key = parts[0].trim().removeSurrounding("\"")
                    val value = parts.drop(1).joinToString(":").trim().removeSurrounding("\"")
                    result[key] = value
                }
            }
            result
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    private fun useLocalDefaults() {
        isEnabled = true
    }
    
    fun getWearableLink(productId: String, region: String = "VN"): String {
        if (!isEnabled) return ""
        return wearables[productId] ?: AffiliateConfig.getWearableLink(productId, region)
    }
    
    fun getHerbLink(herbName: String, region: String = "VN"): String {
        if (!isEnabled) return ""
        return herbs[herbName] ?: AffiliateConfig.getHerbLink(herbName, region)
    }
    
    /**
     * Force refresh remote config (call after user action or on app resume)
     */
    suspend fun forceRefresh() {
        try {
            val remoteConfig = Firebase.remoteConfig
            remoteConfig.fetch(0).await() // Force fetch
            remoteConfig.activate().await()
            parseRemoteValues()
            Log.d(TAG, "Force refresh successful")
        } catch (e: Exception) {
            Log.e(TAG, "Force refresh failed", e)
        }
    }
}
