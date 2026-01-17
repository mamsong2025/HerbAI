package com.example.herbai.ui.scanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.herbai.engine.LocalizationEngine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Plant Scanner Screen - Sử dụng CameraX để quét và nhận diện cây thuốc
 */
@Composable
fun PlantScannerScreen(
    onBackClick: () -> Unit,
    onPlantIdentified: (PlantRecognitionResult) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    
    var isAnalyzing by remember { mutableStateOf(false) }
    var recognitionResult by remember { mutableStateOf<PlantRecognitionResult?>(null) }
    var flashEnabled by remember { mutableStateOf(false) }
    
    val emeraldGreen = Color(0xFF10B981)
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            // Camera Preview
            CameraPreview(
                lifecycleOwner = lifecycleOwner,
                flashEnabled = flashEnabled,
                onImageCaptured = { imageProxy ->
                    isAnalyzing = true
                    // TODO: Analyze with TensorFlow Lite
                    analyzePlantImage(context, imageProxy) { result ->
                        recognitionResult = result
                        isAnalyzing = false
                    }
                }
            )
            
            // Header Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Quét Cây Thuốc",
                            en = "Scan Plant",
                            zh = "扫描草药",
                            ko = "약초 스캔"
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    
                    IconButton(onClick = { flashEnabled = !flashEnabled }) {
                        Icon(
                            if (flashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Flash",
                            tint = Color.White
                        )
                    }
                }
            }
            
            // Scanning Frame Overlay
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .border(3.dp, emeraldGreen, RoundedCornerShape(16.dp))
                )
                
                if (isAnalyzing) {
                    CircularProgressIndicator(
                        color = emeraldGreen,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            
            // Bottom Controls
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    LocalizationEngine.getLocalizedString(
                        vi = "Đưa camera hướng vào lá hoặc hoa cây thuốc",
                        en = "Point camera at the plant's leaf or flower",
                        zh = "将相机对准植物的叶子或花",
                        ko = "카메라를 약초의 잎이나 꽃을 향하게 하세요"
                    ),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Capture Button
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(emeraldGreen)
                        .border(4.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Capture",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            // Recognition Result Card
            recognitionResult?.let { result ->
                PlantResultCard(
                    result = result,
                    onDismiss = { recognitionResult = null },
                    onViewDetails = { 
                        onPlantIdentified(result)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
            
        } else {
            // Permission Request UI
            PermissionRequestUI(
                onRequestPermission = {
                    // In real app, use rememberLauncherForActivityResult
                    // For now, show instruction
                }
            )
        }
    }
}

@Composable
private fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    flashEnabled: Boolean,
    onImageCaptured: (ImageProxy) -> Unit
) {
    val context = LocalContext.current
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { previewView ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                
                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor) { imageProxy ->
                            // Auto-analyze every frame (or trigger manually)
                            onImageCaptured(imageProxy)
                        }
                    }
                
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                try {
                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                    
                    // Control flash
                    camera.cameraControl.enableTorch(flashEnabled)
                    
                } catch (e: Exception) {
                    Log.e("PlantScanner", "Camera binding failed", e)
                }
                
            }, ContextCompat.getMainExecutor(context))
        }
    )
}

@Composable
private fun PlantResultCard(
    result: PlantRecognitionResult,
    onDismiss: () -> Unit,
    onViewDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    val emeraldGreen = Color(0xFF10B981)
    
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        result.vietnameseName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = emeraldGreen
                    )
                    Text(
                        result.scientificName,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
                
                // Confidence Badge
                Box(
                    modifier = Modifier
                        .background(
                            if (result.confidence > 0.8f) emeraldGreen else Color(0xFFD97706),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "${(result.confidence * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Quick Info
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Công dụng: ${result.primaryEffect}",
                    en = "Effect: ${result.primaryEffect}",
                    zh = "功效: ${result.primaryEffect}",
                    ko = "효능: ${result.primaryEffect}"
                ),
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Quét lại",
                            en = "Scan Again",
                            zh = "重新扫描",
                            ko = "다시 스캔"
                        )
                    )
                }
                
                Button(
                    onClick = onViewDetails,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen)
                ) {
                    Text(
                        LocalizationEngine.getLocalizedString(
                            vi = "Xem chi tiết",
                            en = "View Details",
                            zh = "查看详情",
                            ko = "상세보기"
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PermissionRequestUI(onRequestPermission: () -> Unit) {
    val emeraldGreen = Color(0xFF10B981)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CameraAlt,
            contentDescription = null,
            tint = emeraldGreen,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            LocalizationEngine.getLocalizedString(
                vi = "Cần quyền truy cập Camera",
                en = "Camera Permission Required",
                zh = "需要相机权限",
                ko = "카메라 권한 필요"
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            LocalizationEngine.getLocalizedString(
                vi = "Để nhận diện cây thuốc, ứng dụng cần truy cập camera của bạn.",
                en = "To identify medicinal plants, the app needs access to your camera.",
                zh = "为了识别药用植物，应用程序需要访问您的相机。",
                ko = "약초를 식별하려면 앱에서 카메라에 액세스해야 합니다."
            ),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onRequestPermission,
            colors = ButtonDefaults.buttonColors(containerColor = emeraldGreen)
        ) {
            Text(
                LocalizationEngine.getLocalizedString(
                    vi = "Cho phép Camera",
                    en = "Allow Camera",
                    zh = "允许相机",
                    ko = "카메라 허용"
                )
            )
        }
    }
}

/**
 * Placeholder function for TensorFlow Lite analysis
 * In production, this would use a trained plant recognition model
 */
private fun analyzePlantImage(
    context: Context,
    imageProxy: ImageProxy,
    onResult: (PlantRecognitionResult) -> Unit
) {
    // TODO: Implement TensorFlow Lite inference
    // For now, return a mock result after a delay
    
    // Simulate processing time
    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
        onResult(
            PlantRecognitionResult(
                plantId = 1,
                vietnameseName = "Hoàng kỳ",
                scientificName = "Astragalus membranaceus",
                confidence = 0.92f,
                primaryEffect = "Bổ khí, thăng dương"
            )
        )
    }, 1500)
    
    imageProxy.close()
}

/**
 * Data class for plant recognition results
 */
data class PlantRecognitionResult(
    val plantId: Int,
    val vietnameseName: String,
    val scientificName: String,
    val confidence: Float,
    val primaryEffect: String
)
