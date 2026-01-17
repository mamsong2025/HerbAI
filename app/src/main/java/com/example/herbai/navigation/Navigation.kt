package com.example.herbai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.herbai.ui.home.HomeDashboard
import com.example.herbai.ui.medicine.MedicinePreparationScreen
import com.example.herbai.ui.scanner.PlantScannerScreen
import com.example.herbai.ui.health.HealthConnectScreen

/**
 * Navigation Routes for the HerbAI App
 */
object Routes {
    const val HOME = "home"
    const val PLANT_SCANNER = "plant_scanner"
    const val SYMPTOM_CHECKER = "symptom_checker"
    const val FORMULA_LIST = "formula_list"
    const val FORMULA_DETAIL = "formula_detail/{formulaId}"
    const val MEDICINE_PREP = "medicine_prep/{formulaId}"
    const val HEALTH_JOURNAL = "health_journal"
    const val ICD_MAPPING = "icd_mapping"
    const val SAFETY_CHECK = "safety_check"
    const val TONGUE_DIAGNOSIS = "tongue_diagnosis"
    const val HEALTH_CONNECT = "health_connect"
    
    fun medicinePrep(formulaId: String) = "medicine_prep/$formulaId"
    fun formulaDetail(formulaId: String) = "formula_detail/$formulaId"
}

/**
 * Main Navigation Graph
 */
@Composable
fun HerbAINavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeDashboard(
                onScanClick = { navController.navigate(Routes.PLANT_SCANNER) },
                onHealthConnectClick = { navController.navigate(Routes.HEALTH_CONNECT) }
            )
        }
        
        composable(Routes.PLANT_SCANNER) {
            PlantScannerScreen(
                onBackClick = { navController.popBackStack() },
                onPlantIdentified = { result ->
                    // Navigate to plant detail or show in current screen
                }
            )
        }
        
        composable(Routes.SYMPTOM_CHECKER) {
            // SymptomCheckerScreen - TODO
        }
        
        composable(Routes.FORMULA_LIST) {
            // FormulaListScreen - TODO
        }
        
        composable(Routes.MEDICINE_PREP) { backStackEntry ->
            val formulaId = backStackEntry.arguments?.getString("formulaId") ?: "F001"
            MedicinePreparationScreen(
                formulaId = formulaId,
                onSetReminder = { reminders ->
                    // Handle reminder setting
                }
            )
        }
        
        composable(Routes.HEALTH_JOURNAL) {
            // HealthJournalScreen - TODO
        }
        
        composable(Routes.ICD_MAPPING) {
            // IcdMappingScreen - TODO
        }
        
        composable(Routes.SAFETY_CHECK) {
            // SafetyCheckScreen - TODO
        }
        
        composable(Routes.TONGUE_DIAGNOSIS) {
            // TongueDiagnosisScreen - TODO
        }
        
        composable(Routes.HEALTH_CONNECT) {
            HealthConnectScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
