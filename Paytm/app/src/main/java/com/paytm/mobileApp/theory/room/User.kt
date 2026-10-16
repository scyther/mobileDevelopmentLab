package com.paytm.mobileApp.theory.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Step 1: Define the Data Entity (SQLite Table)
 * Represents a single row in the "users" table.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)
