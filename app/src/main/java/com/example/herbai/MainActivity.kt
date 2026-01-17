package com.example.herbai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.herbai.config.RemoteAffiliateConfig
import com.example.herbai.ui.components.MedicalDisclaimerDialog
import com.example.herbai.ui.components.UpdateReadySnackbar
import com.example.herbai.ui.home.HomeDashboard
import com.example.herbai.ui.theme.HerbAITheme
import com.example.herbai.util.InAppUpdateManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var updateManager: InAppUpdateManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize In-App Update Manager
        updateManager = InAppUpdateManager(this)
        
        // Initialize Firebase Remote Config for dynamic affiliate links
        lifecycleScope.launch {
            try {
                RemoteAffiliateConfig.initialize()
            } catch (e: Exception) {
                // Silent fail - will use local defaults
            }
        }
        
        setContent {
            HerbAITheme {
                var showDisclaimer by remember { mutableStateOf(true) }
                var showUpdateSnackbar by remember { mutableStateOf(false) }
                
                // Listen for update downloaded
                updateManager.onUpdateDownloaded = {
                    showUpdateSnackbar = true
                }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        HomeDashboard(
                            onScanClick = {
                                // TODO: Navigate to Scanner screen
                            },
                            onHealthConnectClick = {
                                // TODO: Navigate to Health Connect screen
                            }
                        )
                        
                        // Update Ready Snackbar
                        if (showUpdateSnackbar) {
                            UpdateReadySnackbar(
                                onInstallClick = {
                                    updateManager.completeUpdate()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                    
                    // Show medical disclaimer on app launch
                    if (showDisclaimer) {
                        MedicalDisclaimerDialog(
                            onDismiss = { showDisclaimer = false },
                            onAccept = { showDisclaimer = false }
                        )
                    }
                }
            }
        }
        
        // Check for updates on app start
        updateManager.checkForUpdate()
    }
    
    override fun onResume() {
        super.onResume()
        // Resume update if it was in progress
        updateManager.resumeUpdateIfNeeded()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        updateManager.cleanup()
    }
}
