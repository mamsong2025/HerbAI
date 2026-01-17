package com.example.herbai.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.herbai.model.*

@Database(
    entities = [
        Plant::class,
        Formula::class,
        FormulaIngredient::class,
        Incompatibility::class,
        IcdMapping::class,
        HealthJournalEntry::class,
        DailyAdvice::class,
        Acupoint::class,
        SyndromeAcupoint::class,
        AffiliateProduct::class,
        UserProfile::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun formulaDao(): FormulaDao
    abstract fun safetyDao(): SafetyDao
    abstract fun icdMappingDao(): IcdMappingDao
    abstract fun healthJournalDao(): HealthJournalDao
    abstract fun dailyAdviceDao(): DailyAdviceDao
    abstract fun acupointDao(): AcupointDao
    abstract fun affiliateDao(): AffiliateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "herbai_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
