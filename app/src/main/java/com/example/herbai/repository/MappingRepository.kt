package com.example.herbai.repository

import com.example.herbai.data.IcdMappingDao
import com.example.herbai.data.PlantDao
import com.example.herbai.model.IcdMapping
import com.example.herbai.model.Plant

class MappingRepository(
    private val plantDao: PlantDao,
    private val icdMappingDao: IcdMappingDao
) {

    /**
     * Maps an ICD code to a TCM Syndrome and suggests plants.
     */
    suspend fun getMappingSuggestions(icdCode: String): Pair<String, List<Plant>> {
        val mapping = icdMappingDao.getMappingByCode(icdCode)
        
        val syndrome = mapping?.tcm_syndrome ?: "Chưa rõ hội chứng"
        val suggestedPlantIds = mapping?.recommended_formula_id?.let {
            // Logic to get plants based on formula (simplified here)
            listOf(1, 2, 4) // Example placeholder
        } ?: emptyList()

        val plants = suggestedPlantIds.mapNotNull { plantDao.getPlantById(it) }
        return Pair(syndrome, plants)
    }

    /**
     * Search for ICD codes and syndromes.
     */
    suspend fun searchIcd(query: String): List<IcdMapping> {
        return icdMappingDao.searchMappings(query)
    }
}
