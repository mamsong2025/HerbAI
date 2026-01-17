package com.example.herbai.data

import androidx.room.*
import com.example.herbai.model.*

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>

    @Query("SELECT * FROM plants WHERE vietnamese_name LIKE '%' || :query || '%' OR han_name LIKE '%' || :query || '%'")
    suspend fun searchPlants(query: String): List<Plant>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: Int): Plant?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)
}

@Dao
interface FormulaDao {
    @Query("SELECT * FROM formulas")
    suspend fun getAllFormulas(): List<Formula>

    @Transaction
    @Query("SELECT * FROM formulas WHERE formula_id = :formulaId")
    suspend fun getFormulaWithIngredients(formulaId: String): FormulaWithIngredients

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulas(formulas: List<Formula>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<FormulaIngredient>)
}

data class FormulaWithIngredients(
    @Embedded val formula: Formula,
    @Relation(
        parentColumn = "formula_id",
        entityColumn = "formula_id"
    )
    val ingredients: List<FormulaIngredient>
)

@Dao
interface SafetyDao {
    @Query("SELECT * FROM incompatibilities")
    suspend fun getAllIncompatibilities(): List<Incompatibility>

    @Query("SELECT * FROM incompatibilities WHERE (plant_a = :nameA AND plant_b = :nameB) OR (plant_a = :nameB AND plant_b = :nameA)")
    suspend fun checkConflict(nameA: String, nameB: String): Incompatibility?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncompatibilities(list: List<Incompatibility>)
}

@Dao
interface IcdMappingDao {
    @Query("SELECT * FROM icd_mapping")
    suspend fun getAllMappings(): List<IcdMapping>

    @Query("SELECT * FROM icd_mapping WHERE icd_code = :code")
    suspend fun getMappingByCode(code: String): IcdMapping?

    @Query("SELECT * FROM icd_mapping WHERE icd_name LIKE '%' || :query || '%'")
    suspend fun searchMappings(query: String): List<IcdMapping>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMappings(mappings: List<IcdMapping>)
}

@Dao
interface HealthJournalDao {
    @Query("SELECT * FROM health_journal ORDER BY timestamp DESC")
    suspend fun getAllEntries(): List<HealthJournalEntry>

    @Query("SELECT * FROM health_journal WHERE date = :date")
    suspend fun getEntryByDate(date: String): HealthJournalEntry?

    @Query("SELECT * FROM health_journal WHERE date BETWEEN :startDate AND :endDate ORDER BY date")
    suspend fun getEntriesInRange(startDate: String, endDate: String): List<HealthJournalEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: HealthJournalEntry)

    @Delete
    suspend fun deleteEntry(entry: HealthJournalEntry)
}

@Dao
interface DailyAdviceDao {
    @Query("SELECT * FROM daily_advice WHERE date = :date")
    suspend fun getAdviceForDate(date: String): DailyAdvice?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvice(advice: DailyAdvice)
}

@Dao
interface AcupointDao {
    @Query("SELECT * FROM acupoints")
    suspend fun getAllAcupoints(): List<Acupoint>

    @Query("SELECT * FROM acupoints WHERE id = :id")
    suspend fun getAcupointById(id: String): Acupoint?

    @Query("""
        SELECT a.* FROM acupoints a
        INNER JOIN syndrome_acupoints sa ON a.id = sa.acupoint_id
        WHERE sa.syndrome_name = :syndromeName
    """)
    suspend fun getAcupointsForSyndrome(syndromeName: String): List<Acupoint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcupoints(acupoints: List<Acupoint>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyndromeMappings(mappings: List<SyndromeAcupoint>)
}

@Dao
interface AffiliateDao {
    @Query("SELECT * FROM affiliate_products")
    suspend fun getAllProducts(): List<AffiliateProduct>

    @Query("SELECT * FROM affiliate_products WHERE region = :region OR region = 'GLOBAL'")
    suspend fun getProductsByRegion(region: String): List<AffiliateProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<AffiliateProduct>)
}
