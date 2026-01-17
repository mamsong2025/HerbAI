package com.example.herbai.engine

import com.example.herbai.data.SafetyDao
import com.example.herbai.model.Incompatibility
import com.example.herbai.model.Plant

class SafetyEngine(private val safetyDao: SafetyDao) {

    /**
     * Checks a list of plants for any TCM incompatibilities.
     * Returns a list of conflicts found.
     */
    suspend fun validateFormula(plants: List<Plant>): List<Incompatibility> {
        val conflicts = mutableListOf<Incompatibility>()
        val n = plants.size
        
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val plantA = plants[i]
                val plantB = plants[j]
                
                // Check database for conflict between these two names
                val conflict = safetyDao.checkConflict(plantA.vietnamese_name, plantB.vietnamese_name)
                if (conflict != null) {
                    conflicts.add(conflict)
                }
            }
        }
        return conflicts
    }

    /**
     * Checks if a new plant can be safely added to an existing list of plants.
     */
    suspend fun checkAddition(existingPlants: List<Plant>, newPlant: Plant): Incompatibility? {
        for (plant in existingPlants) {
            val conflict = safetyDao.checkConflict(plant.vietnamese_name, newPlant.vietnamese_name)
            if (conflict != null) return conflict
        }
        return null
    }
}
