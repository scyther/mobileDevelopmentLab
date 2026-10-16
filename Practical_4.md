# Practical 4: Local Storage in Jetpack Compose using SQLite Room & APK Generation

> **Course:** Mobile Application Development  
> **Practical:** Practical 4  
> **Topic:** Local Data Persistence with SQLite & Room Database, Coroutines Flow, Reactive UI State, and APK Packaging  
> **Project:** `Paytm` (Packages: `com.paytm.mobileApp.theory.room` & `com.paytm.mobileApp.theory.passbook`)

---

## 🎯 Practical Objectives

By completing this practical, students will be able to:
1. **Understand Local Persistence in Android:** Contrast primitive storage options (SharedPreferences, DataStore) with relational databases (SQLite and Room).
2. **Master the Room Persistence Library:** Learn why Google introduced Room as an abstraction layer over SQLite to provide compile-time SQL verification, eliminate manual `Cursor` parsing, and enable reactive data streams.
3. **Configure Gradle with KSP (Kotlin Symbol Processing):** Set up modern annotation processing via KSP in `libs.versions.toml`, root `build.gradle.kts`, and app-module `build.gradle.kts`.
4. **Implement Room’s Core Building Blocks:**
   - **`@Entity`:** Map Kotlin data classes to SQLite database tables.
   - **`@Dao` (Data Access Object):** Declare CRUD operations (`@Insert`, `@Delete`, `@Query`) with Kotlin Coroutines `suspend` functions and reactive `Flow<List<T>>`.
   - **`@Database`:** Define an abstract `RoomDatabase` class utilizing a thread-safe Singleton pattern.
5. **Architect with MVVM & StateFlow:** Connect the Room database to Jetpack Compose through a `ViewModel`, hoisting UI state via `StateFlow` and `stateIn()`.
6. **Build Reactive Compose UIs:** Collect database state using `collectAsState()` so UI automatically re-composes on every database insert or delete without manual refresh logic.
7. **Explore a Real-World Capstone:** Examine the **Paytm Passbook** module (`passbook/`) featuring live balance calculations, category filtering, and transaction logs.
8. **Build and Export Android APKs:** Generate both **Debug APKs** and **Signed Release APKs** using Android Studio and the Gradle CLI, and deploy them to physical Android smartphones.

---

## 🧭 Section 1: Architecture & Theoretical Overview

### 1.1 Why SQLite and Why Room?

Android includes a full-featured, zero-configuration relational database engine called **SQLite** built directly into the operating system since API Level 1.

Historically, Android developers used `SQLiteOpenHelper` to run raw SQL statements:
```kotlin
// ❌ Legacy Raw SQLite Approach (High Boilerplate & Error-Prone)
val db = dbHelper.writableDatabase
val cursor = db.rawQuery("SELECT * FROM users", null)
while (cursor.moveToNext()) {
    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
    // Manual cursor closing, manual type mapping, zero compile-time checks!
}
```

Google introduced the **Room Persistence Library** as part of Android Jetpack to modernize database operations.

```
+-----------------------------------------------------------------------------------+
|                                JETPACK COMPOSE UI                                 |
|   +---------------------------------------------------------------------------+   |
|   |   UserScreen.kt                                                           |   |
|   |   - Inputs: Name, Age                                                     |   |
|   |   - Live User List via collectAsState()                                   |   |
|   +-------------------------------------▲-------------------------------------+   |
+-----------------------------------------│-----------------------------------------+
                                          │ Observes StateFlow<List<User>>
+-----------------------------------------▼-----------------------------------------+
|                                  VIEWMODEL LAYER                                  |
|   +---------------------------------------------------------------------------+   |
|   |   UserViewModel.kt                                                        |   |
|   |   - allUsers: StateFlow<List<User>> = dao.getAllUsers().stateIn(...)      |   |
|   |   - addUser(name, age) -> Launches coroutine on viewModelScope            |   |
|   |   - deleteUser(user)   -> Launches coroutine on viewModelScope            |   |
|   +-------------------------------------▲-------------------------------------+   |
+-----------------------------------------│-----------------------------------------+
                                          │ Emits Flow<List<User>>
+-----------------------------------------▼-----------------------------------------+
|                               ROOM PERSISTENCE LAYER                              |
|   +-------------------+     +--------------------+     +----------------------+   |
|   |      @Entity      |     |        @Dao        |     |      @Database       |   |
|   |      User.kt      |     |     UserDao.kt     |     |    AppDatabase.kt    |   |
|   | (Table Definition)|     |   (CRUD Queries)   |     | (Singleton Instance) |   |
|   +-------------------+     +--------------------+     +----------------------+   |
+-----------------------------------------▲-----------------------------------------+
                                          │ Compile-Time Checked SQL
+-----------------------------------------▼-----------------------------------------+
|                              UNDERLYING SQLITE ENGINE                             |
|                               (File: user_database)                               |
+-----------------------------------------------------------------------------------+
```

### 1.2 Comparison: Raw SQLite vs Room

| Feature | Raw SQLite (`SQLiteOpenHelper`) | Room Persistence Library |
| :--- | :--- | :--- |
| **SQL Syntax Verification** | Runtime only (spelling mistakes crash the app during execution) | **Compile-time** (build fails immediately if table/column does not exist) |
| **Object Relational Mapping (ORM)** | Manual: extract column indices from `Cursor` row-by-row | **Automatic:** directly returns Kotlin data objects (`User`, `List<User>`) |
| **Asynchronous & Reactive** | Manual thread executors; requires manual queries to refresh | **Native Flow support:** UI automatically re-emits when database changes |
| **Coroutines Integration** | No native support | Native `suspend` functions for non-blocking I/O |
| **Boilerplate Code** | Heavy (50+ lines for basic CRUD) | Minimal (declarative annotations) |

---

## 📦 Section 2: Step 1 — Configuring Dependencies with KSP & Version Catalog

Room uses an annotation processor to generate SQLite implementation code at compile time. Modern Android projects use **KSP (Kotlin Symbol Processing)** instead of legacy `kapt` because it compiles up to 2x faster.

### 2.1 Version Catalog Configuration (`gradle/libs.versions.toml`)

Open `gradle/libs.versions.toml` and configure the exact versions and library aliases:

```toml
[versions]
agp = "9.4.1"
coreKtx = "1.19.1"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.11.0"
activityCompose = "1.13.0"
kotlin = "2.4.21"
composeBom = "2026.09.00"
roomVersion = "2.8.5"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }

# Room Database
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "roomVersion" }
androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "roomVersion" }
androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "roomVersion" }

# Compose BOM & UI
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

> [!TIP]
> **Pro-Tip for Upgrading Dependencies (The Yellow Underline):**  
> In Android Studio, whenever a newer, stable release of a plugin or library is available, Android Studio flags the version in `libs.versions.toml` with a **yellow wavy underline** (lint inspection).  
> - Hover over the yellow underlined version, or place your cursor on it and press **`Alt + Enter`** (Windows) or **`Option + Return`** (macOS).
> - Select **"Change to [version]"** to automatically bump Kotlin, Room, or serialization to the latest verified release.

---

### 2.2 Root Project Gradle (`build.gradle.kts`)

Open the root `build.gradle.kts` file and declare the Kotlin compiler, serialization plugin, and KSP plugin matching your Kotlin version:

```kotlin
// Top-level build file: build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Kotlin & Serialization Plugins (Upgraded to 2.4.21)
    kotlin("jvm") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"

    // KSP (Kotlin Symbol Processing) plugin for Room database compiler
    id("com.google.devtools.ksp") version "2.3.10" apply false
}
```

---

### 2.3 App Module Gradle (`app/build.gradle.kts`)

Open `app/build.gradle.kts`, apply the serialization and KSP plugins, and connect the version catalog dependencies:

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // 1. Serialization plugin (matching Kotlin 2.4.21)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.21"

    // 2. Apply KSP plugin
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.paytm.mobileApp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.paytm.mobileApp"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // ...
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // For Navigation & Serialization
    implementation("androidx.navigation:navigation-compose:2.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // 3. Room Database (SQLite abstraction from TOML)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)          // Flow & Coroutines support
    ksp(libs.androidx.room.compiler)                // KSP Code generator

    // 4. ViewModel Compose Support
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
```

> [!IMPORTANT]
> Always perform a **Gradle Sync** (`File > Sync Project with Gradle Files` or click the elephant icon) after editing build scripts. Ensure no version mismatch errors appear in the Build window before running the app.

---

## 🏛️ Section 3: Step 2 — Define the Data Entity (The Database Table)

An **Entity** represents an SQLite table. Each instance of the class represents a single row in that table.

Create the file `User.kt` in package `com.paytm.mobileApp.theory.room`:

```kotlin
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
```

### 🔍 Key Annotations Explained:
- **`@Entity(tableName = "users")`**: Tells Room to create a table named `users`. If `tableName` is omitted, Room defaults to the class name.
- **`@PrimaryKey(autoGenerate = true)`**: Marks `id` as the primary key. With `autoGenerate = true`, SQLite generates auto-incrementing unique IDs (`1, 2, 3...`) whenever a new record is inserted.
- **Default Arguments (`id: Int = 0`)**: Allows creating a new `User("Alice", 21)` without having to manually supply an ID.

---

## 🔌 Section 4: Step 3 — Create the DAO (Data Access Object)

The **DAO** is an interface that defines how your application reads and writes data to the SQLite database. Room automatically generates the implementation class during compilation.

Create the file `UserDao.kt` in package `com.paytm.mobileApp.theory.room`:

```kotlin
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
 */
@Dao
interface UserDao {

    /**
     * Query returning a Kotlin Coroutines Flow.
     * Whenever the "users" table is modified (insert, update, delete),
     * Room automatically re-executes this query and emits the updated list to the UI!
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    /**
     * Inserts a user. If a record with the same primary key exists, it is replaced.
     * Marked with 'suspend' so it runs on a background thread.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Deletes a specific user record from the SQLite table.
     */
    @Delete
    suspend fun deleteUser(user: User)
}
```

### 💡 Why does `getAllUsers()` return a `Flow`?
In raw SQLite, after inserting or deleting a row, you must manually run the query again to update the UI.  
With Room and Kotlin Coroutines, returning **`Flow<List<User>>`** turns the query into a **reactive stream**: Room monitors the database table and pushes the updated data to the observer automatically!

---

## 🗄️ Section 5: Step 4 — Set up the Room Database

The database class ties your entities and DAOs together. It extends `RoomDatabase` and provides a thread-safe Singleton instance.

Create the file `AppDatabase.kt` in package `com.paytm.mobileApp.theory.room`:

```kotlin
package com.paytm.mobileApp.theory.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Step 3: Set up the Room Database
 * Abstract class linking entities and DAOs using a thread-safe Singleton pattern.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns the Singleton database instance.
         * Uses synchronized(this) to prevent multiple threads from initializing the DB at the same time.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database" // The SQLite file name on disk
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

### 🔍 Key Concepts:
- **`@Database(entities = [...], version = 1)`**: Informs Room which tables belong to this database and specifies the schema version.
- **`@Volatile`**: Ensures that changes made by one thread to `INSTANCE` are immediately visible to all other threads.
- **Singleton Pattern**: Opening an SQLite database is resource-intensive. Reusing a single instance throughout the application lifecycle prevents memory leaks and file locks.

---

## 🌉 Section 6: Step 5 — Bridge with a ViewModel

Directly accessing the database from Composable functions violates Android architectural best practices and can cause UI freezes (jank). A **ViewModel** survives configuration changes (such as screen rotations) and manages background coroutines safely.

Create the file `UserViewModel.kt` in package `com.paytm.mobileApp.theory.room`:

```kotlin
package com.paytm.mobileApp.theory.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Step 4: Bridge Room with a ViewModel
 * Exposes a StateFlow for Compose to observe and dispatches coroutine operations safely.
 */
class UserViewModel(private val userDao: UserDao) : ViewModel() {

    /**
     * Converts cold Flow from Room into a hot StateFlow for Compose.
     * SharingStarted.WhileSubscribed(5000) keeps the upstream Flow alive for 5 seconds
     * after the last UI subscriber leaves (handles screen rotations smoothly).
     */
    val allUsers: StateFlow<List<User>> = userDao.getAllUsers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Inserts a user asynchronously using viewModelScope.
     */
    fun addUser(name: String, age: Int) {
        viewModelScope.launch {
            userDao.insertUser(User(name = name, age = age))
        }
    }

    /**
     * Deletes a user asynchronously.
     */
    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUser(user)
        }
    }
}

/**
 * Factory class required to pass UserDao into the UserViewModel constructor.
 */
class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(dao = userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
```

---

## 🎨 Section 7: Step 6 — Build the Jetpack Compose UI

Now, build a clean, reactive Jetpack Compose interface. Whenever a user is added or deleted, the UI automatically updates without any manual refresh call.

Create the file `UserScreen.kt` in package `com.paytm.mobileApp.theory.room`:

```kotlin
package com.paytm.mobileApp.theory.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Entry point composable: Initializes Database, DAO, and ViewModel, then renders the screen.
 */
@Composable
fun RoomPracticalApp() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val dao = remember { database.userDao() }
    val viewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(dao)
    )

    UserScreen(viewModel = viewModel)
}

/**
 * Step 5: Build the Jetpack Compose UI
 * Collects state reactively and provides basic CRUD (Insert, Read, Delete).
 */
@Composable
fun UserScreen(viewModel: UserViewModel) {
    // Collect the StateFlow reactively; Compose will recompose whenever userList changes
    val userList by viewModel.allUsers.collectAsState()

    var nameText by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Room SQLite Database Practical",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Input: Name
            OutlinedTextField(
                value = nameText,
                onValueChange = { nameText = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Input: Age
            OutlinedTextField(
                value = ageText,
                onValueChange = { ageText = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Button: Insert into SQLite
            Button(
                onClick = {
                    val age = ageText.toIntOrNull()
                    if (nameText.isNotBlank() && age != null) {
                        viewModel.addUser(name = nameText.trim(), age = age)
                        nameText = ""
                        ageText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add User to SQLite")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Stored Users (${userList.size}):",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            // Live reactive display of SQLite database records
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userList, key = { it.id }) { user ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = user.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Age: ${user.age}  •  ID: ${user.id}",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            OutlinedButton(
                                onClick = { viewModel.deleteUser(user) }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
```

### 7.1 Running the App in `MainActivity.kt`

Open `MainActivity.kt` and set `RoomPracticalApp()` as the root composable:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaytmTheme {
                RoomPracticalApp() // Launches Practical 4 Room SQLite Demo
            }
        }
    }
}
```

---

## 🚀 Section 8: Practical App - The Paytm Passbook

You have to create **Paytm Passbook App** located in package `com.paytm.mobileApp.theory.passbook`.

### 8.1 Data Model

```
+-----------------------------------+         +-------------------------------------+
|        Classroom Demo: User       |         |   Real-World Capstone: Transaction  |
+-----------------------------------+         +-------------------------------------+
| - id: Int (PrimaryKey autoGen)    |         | - id: Int (PrimaryKey autoGen)      |
| - name: String                    |         | - title: String (e.g., "Zomato")    |
| - age: Int                        |         | - amount: Double (e.g., 350.00)     |
+-----------------------------------+         | - type: String ("DEBIT"/"CREDIT")   |
                                              | - category: String ("Food","Bills") |
                                              | - timestamp: Long                   |
                                              +-------------------------------------+
```

### 8.2 Passbook DAO Enhancements
In addition to basic CRUD, `passbook/TransactionDao.kt` demonstrates SQL aggregate functions:
```kotlin
@Query("SELECT SUM(amount) FROM transactions WHERE type = 'CREDIT'")
fun getTotalCredit(): Flow<Double?>

@Query("SELECT SUM(amount) FROM transactions WHERE type = 'DEBIT'")
fun getTotalDebit(): Flow<Double?>
```

### 8.3 Running the Passbook App
Switch to the Passbook feature anytime in `MainActivity.kt`:
```kotlin
setContent {
    PaytmTheme {
        PassbookApp() // Real-World Paytm Passbook
    }
}
```

---

## 📦 Section 9: Step-by-Step Guide to Generating APKs

An **APK (Android Package Kit)** is the compiled package file format used by Android to distribute and install applications.

### 9.1 Debug APK vs Release APK

| Characteristic | Debug APK | Release APK |
| :--- | :--- | :--- |
| **Purpose** | Development, testing, fast iteration | Production deployment, Play Store, public distribution |
| **Signing** | Automatically signed with Android's default debug keystore | Signed with a private cryptographic KeyStore (`.jks`) |
| **Optimization** | Unminified, contains full debugging symbols | Minified (R8/ProGuard), obfuscated, optimized |
| **Security** | Insecure (not allowed on Google Play) | Secure (verifies developer authenticity) |

---

### 9.2 Method 1: Generating a Debug APK

1. In Android Studio, click **Build** in the top menu bar.
2. Navigate to **Build Bundle(s) / APK(s)** > **Build APK(s)**.
3. Android Studio runs the Gradle build task `assembleDebug`.
4. When finished, a notification popup appears in the bottom-right corner:  
   `APK(s) generated successfully for module 'Paytm.app'`.
5. Click **locate** inside the notification to open the folder containing `app-debug.apk`.

```
Project Root Folder
└── app
    └── build
        └── outputs
            └── apk
                └── debug
                    └── app-debug.apk  <-- Share this file!
```

### 9.3 Method 2: Generating a Signed Release APK

For a standalone distribution APK that can be installed on any device:

1. In Android Studio, go to menu: **Build** > **Generate Signed Bundle / APK...**.
2. In the modal, select **APK** and click **Next**.
3. Under **Key store path**, click **Create new...** to create a signing key:
   - **Key store path:** Choose a safe folder on your PC (e.g., `C:/Users/chirag/my-release-key.jks`).
   - **Password:** Enter a secure password (e.g., `password123`).
   - **Alias:** Enter a key alias (e.g., `paytm_key`).
   - **Validity (years):** Leave default `25`.
   - **Certificate:** Enter your name / organization. Click **OK**.
4. Click **Next**.
5. Select Build Variant: **`release`**.
6. Under **Signature Versions**, check both:
   - ✅ **V1 (Jar Signature)** — for backward compatibility with older Android devices.
   - ✅ **V2 / V3 (Full APK Signature)** — modern Android verification scheme.
7. Click **Create** / **Finish**.
8. The signed release APK is output to:  
   `app/release/app-release.apk`.


---

## 📋 Section 11: Student Verification Checklist

- [ ] Added `com.google.devtools.ksp` plugin in both root and module `build.gradle.kts`.
- [ ] Added `room-runtime`, `room-ktx`, and `room-compiler` dependencies in Gradle.
- [ ] Successfully created `User.kt` with `@Entity` and auto-generated `@PrimaryKey`.
- [ ] Created `UserDao.kt` with reactive `getAllUsers(): Flow<List<User>>`, `@Insert`, and `@Delete`.
- [ ] Built thread-safe Singleton `AppDatabase.kt` extending `RoomDatabase`.
- [ ] Connected `UserViewModel.kt` utilizing `viewModelScope` and `stateIn()`.
- [ ] Built reactive UI in `UserScreen.kt` observing state with `collectAsState()`.
- [ ] Verified that inserted data **persists after completely closing and reopening the app**.
- [ ] Successfully generated `app-debug.apk` and located the file in `app/build/outputs/apk/debug/`.

---

## 📁 Reference Code in This Repository

- **Simple User Package:** [`Paytm/.../theory/room/`](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/)
  - [**`User.kt`** (Entity)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/User.kt)
  - [**`UserDao.kt`** (DAO)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/UserDao.kt)
  - [**`AppDatabase.kt`** (Database Singleton)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/AppDatabase.kt)
  - [**`UserViewModel.kt`** (ViewModel + StateFlow)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/UserViewModel.kt)
  - [**`UserScreen.kt`** (Compose UI)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/room/UserScreen.kt)
- **Passbook Package:** [`Paytm/.../theory/passbook/`](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/)
  - [**`TransactionEntity.kt`**](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/TransactionEntity.kt)
  - [**`TransactionDao.kt`**](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/TransactionDao.kt)
  - [**`PassbookDatabase.kt`**](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/PassbookDatabase.kt)
  - [**`TransactionViewModel.kt`**](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/TransactionViewModel.kt)
  - [**`PassbookScreen.kt`**](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/passbook/PassbookScreen.kt)
- **App Entry Point:** [`MainActivity.kt`](./Paytm/app/src/main/java/com/paytm/mobileApp/MainActivity.kt)

---

## 🌐 Helpful Resources

- 📖 **[Google Android: Save Data in a Local Database Using Room](https://developer.android.com/training/data-storage/room)**
- ⚡ **[Android Developers Codelab: Persisting Data with Room in Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room)**
- 🛠️ **[Google KSP (Kotlin Symbol Processing) Documentation](https://developer.android.com/build/migrate-to-ksp)**
- 📱 **[Android Studio: Build and Sign your APK](https://developer.android.com/build/building-cmdline)**
