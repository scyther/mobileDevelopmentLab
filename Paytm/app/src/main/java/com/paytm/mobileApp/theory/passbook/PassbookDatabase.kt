package com.paytm.mobileApp.theory.passbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room Database for the Real-World Paytm Passbook.
 */
@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PassbookDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: PassbookDatabase? = null

        fun getDatabase(context: Context): PassbookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PassbookDatabase::class.java,
                    "paytm_passbook_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
