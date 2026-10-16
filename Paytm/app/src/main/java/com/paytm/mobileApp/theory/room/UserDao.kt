package com.paytm.mobileApp.theory.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Step 2: Create the DAO (Data Access Object)
 * Defines methods for inserting, deleting, and querying users.
 * Returning Flow<List<User>> enables real-time reactive UI updates in Jetpack Compose.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}
