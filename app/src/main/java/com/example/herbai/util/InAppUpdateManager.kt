package com.example.herbai.util

import android.app.Activity
import android.content.IntentSender
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed

/**
 * In-App Update Manager - Quản lý cập nhật ứng dụng từ trong app
 * 
 * Hai chế độ cập nhật:
 * 1. FLEXIBLE: Người dùng có thể tiếp tục dùng app trong khi tải (recommended)
 * 2. IMMEDIATE: Bắt buộc cập nhật, app bị chặn cho đến khi hoàn tất (cho critical bugs)
 * 
 * Sử dụng:
 * ```kotlin
 * val updateManager = InAppUpdateManager(activity)
 * updateManager.checkForUpdate()
 * ```
 */
class InAppUpdateManager(private val activity: Activity) {
    
    companion object {
        private const val TAG = "InAppUpdateManager"
        const val UPDATE_REQUEST_CODE = 1001
        
        // Số ngày kể từ khi bản cập nhật available mà user vẫn chưa update
        // Sau số ngày này, sẽ chuyển sang IMMEDIATE update (bắt buộc)
        private const val DAYS_FOR_FLEXIBLE_UPDATE = 3
    }
    
    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(activity)
    }
    
    private var updateInfo: AppUpdateInfo? = null
    
    // Callback khi update sẵn sàng để cài đặt (cho FLEXIBLE update)
    var onUpdateDownloaded: (() -> Unit)? = null
    
    // Callback khi có lỗi
    var onUpdateError: ((Exception) -> Unit)? = null
    
    // Listener cho trạng thái cài đặt
    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> {
                Log.d(TAG, "Update downloaded, ready to install")
                onUpdateDownloaded?.invoke()
            }
            InstallStatus.INSTALLED -> {
                Log.d(TAG, "Update installed successfully")
                appUpdateManager.unregisterListener(installStateUpdatedListener)
            }
            InstallStatus.FAILED -> {
                Log.e(TAG, "Update failed")
                appUpdateManager.unregisterListener(installStateUpdatedListener)
            }
            else -> {}
        }
    }
    
    /**
     * Kiểm tra xem có bản cập nhật mới không
     */
    fun checkForUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            updateInfo = info
            
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateInProgress = info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            
            if (isUpdateAvailable) {
                Log.d(TAG, "Update available! Version code: ${info.availableVersionCode()}")
                
                // Kiểm tra số ngày kể từ khi update available
                val daysSinceUpdate = info.clientVersionStalenessDays() ?: 0
                
                when {
                    // Nếu đã quá DAYS_FOR_FLEXIBLE_UPDATE ngày → Bắt buộc cập nhật
                    daysSinceUpdate >= DAYS_FOR_FLEXIBLE_UPDATE && info.isImmediateUpdateAllowed -> {
                        startImmediateUpdate(info)
                    }
                    // Nếu không, cho phép cập nhật linh hoạt
                    info.isFlexibleUpdateAllowed -> {
                        startFlexibleUpdate(info)
                    }
                    // Fallback to immediate nếu flexible không được hỗ trợ
                    info.isImmediateUpdateAllowed -> {
                        startImmediateUpdate(info)
                    }
                }
            } else if (isUpdateInProgress) {
                // Resume update nếu đang dở
                startImmediateUpdate(info)
            } else {
                Log.d(TAG, "No update available")
            }
            
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Failed to check for updates", exception)
            onUpdateError?.invoke(exception)
        }
    }
    
    /**
     * Bắt đầu cập nhật linh hoạt (người dùng vẫn dùng app được)
     */
    private fun startFlexibleUpdate(info: AppUpdateInfo) {
        try {
            Log.d(TAG, "Starting FLEXIBLE update")
            appUpdateManager.registerListener(installStateUpdatedListener)
            
            appUpdateManager.startUpdateFlowForResult(
                info,
                activity,
                AppUpdateOptions.defaultOptions(AppUpdateType.FLEXIBLE),
                UPDATE_REQUEST_CODE
            )
        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "Failed to start flexible update", e)
            onUpdateError?.invoke(e)
        }
    }
    
    /**
     * Bắt đầu cập nhật bắt buộc (chặn app cho đến khi xong)
     */
    private fun startImmediateUpdate(info: AppUpdateInfo) {
        try {
            Log.d(TAG, "Starting IMMEDIATE update")
            
            appUpdateManager.startUpdateFlowForResult(
                info,
                activity,
                AppUpdateOptions.defaultOptions(AppUpdateType.IMMEDIATE),
                UPDATE_REQUEST_CODE
            )
        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "Failed to start immediate update", e)
            onUpdateError?.invoke(e)
        }
    }
    
    /**
     * Hoàn tất cài đặt update (gọi sau khi FLEXIBLE update đã tải xong)
     * Sẽ restart app
     */
    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }
    
    /**
     * Gọi trong onResume() để kiểm tra nếu update đang chờ cài đặt
     */
    fun resumeUpdateIfNeeded() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            // Nếu update đã tải xong và đang chờ cài đặt
            if (info.installStatus() == InstallStatus.DOWNLOADED) {
                onUpdateDownloaded?.invoke()
            }
            
            // Nếu IMMEDIATE update đang dở → Resume
            if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startImmediateUpdate(info)
            }
        }
    }
    
    /**
     * Cleanup listener khi activity bị destroy
     */
    fun cleanup() {
        appUpdateManager.unregisterListener(installStateUpdatedListener)
    }
}
