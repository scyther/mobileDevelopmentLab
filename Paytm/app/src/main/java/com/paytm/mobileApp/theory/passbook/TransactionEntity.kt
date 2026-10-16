package com.paytm.mobileApp.theory.passbook

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Entity representing a financial transaction in the Paytm local SQLite database.
 */
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val type: String, // "DEBIT" or "CREDIT"
    val category: String, // "Food", "Shopping", "Bills", "Recharge", "Entertainment", "Other"
    val timestamp: Long = System.currentTimeMillis()
) {
    val formattedDate: String
        get() {
            val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            return formatter.format(Date(timestamp))
        }
}
