# Practical 1 (Session 1): Environment Setup, Project Wizard Exploration & IDE Architecture

> **Course:** Mobile Application Development  
> **Part:** Practical 1 — Session 1

---

## 🎯 Session 1 Objectives

- Install Android Studio along with the Android SDK, build tools, and emulator accelerators.
- Create an **Empty Activity** project while understanding what every configuration option does.
- Master the 9 mandatory tool windows and sidebars (**Project, Build, Problems, Terminal, Logcat, Notifications, Gradle, Device Manager, Running Devices**).
- Configure and boot an Android Virtual Device (AVD) emulator.

---

## 🛠️ Section 1: Downloading & Installing Android Studio

### 1.1 Installation Steps
1. **Download:** Navigate to the official Android Developers website: [developer.android.com/studio](https://developer.android.com/studio) and download the latest stable release of Android Studio.
2. **Launch Installer:** Run the downloaded `.exe` installer.
3. **Select Components to Install:**
   - ✅ **Android Studio:** The core IDE based on IntelliJ IDEA.
   - ✅ **Android Virtual Device (AVD):** Preconfigured virtual device emulator engines and system image frameworks.
4. **Choose Destination Folder:** Keep the default installation directory (`C:\Program Files\Android\Android Studio`) or select a custom path without spaces or special characters.
5. **Initial Setup Wizard:**
   - Launch Android Studio upon installation completion.
   - Choose **Standard** installation to download the latest stable Android SDK Platform, Build-Tools, and Intel/ARM emulator accelerators.
   - Review and accept the license agreements for `android-sdk-license` and `android-sdk-arm-dbt-license`.
   - Allow the download process to complete.

> [!TIP]
> **Troubleshooting Download Interruptions:**  
> If an SDK download halts due to connection timeout, click **Retry**. If an archive gets corrupted, enable "Hidden Items" in Windows File Explorer, navigate to `C:\Users\<YourUsername>\AppData\Local\Android\Sdk`, clear the `temp/` folder, and restart Android Studio.

---

## 🚀 Section 2: Creating Your First Project & Exploring All Options

On the Android Studio Welcome screen, click **New Project** (or from the top menu: **File > New > New Project**).

```
+-------------------------------------------------------------------------------+
|                             New Project Wizard                                |
+-------------------------------------------------------------------------------+
| 1. Select a Template:                                                         |
|    [ Phone and Tablet ] -> Select "Empty Activity" (Compose)                  |
|                                                                               |
| 2. Configure Project Details:                                                 |
|    - Name:                    p1                                              |
|    - Package name:            com.example.p1                                  |
|    - Save location:           C:\Users\...\mobileDevelopment\p1               |
|    - Language:                Kotlin                                          |
|    - Minimum SDK:             API 24 ("Android 7.0 (Nougat)") or API 33       |
|    - Build configuration:     Kotlin DSL (build.gradle.kts)                   |
+-------------------------------------------------------------------------------+
```

### 2.1 Project Templates Overview
Students must know the difference between the available templates:

| Template | Architecture & Use Case |
| :--- | :--- |
| **Empty Activity** *(Selected for this lab)* | Modern Android standard built with **Jetpack Compose** (declarative Kotlin UI). Contains minimal boilerplate with Material 3 styling. |
| **Empty Views Activity** | Traditional Android UI using XML layout files (`activity_main.xml`) and imperative Java/Kotlin code (`findViewById` / ViewBinding). |
| **No Activity** | Creates a blank module without any user interface screens; ideal for background daemons, broadcast services, or shared libraries. |
| **Navigation Drawer / Bottom Navigation Activity** | Generates pre-configured multi-screen scaffolds with Jetpack Navigation components. |

### 2.2 Deep Dive into Every Configuration Field

1. **Name:**
   - The user-visible title of the application shown on the launcher screen and app drawer.
   - Example for this lab: `"p1"`.
2. **Package Name:**
   - The globally unique identifier for your application across the Android OS and Google Play Store.
   - Follows reverse domain format: `com.<organization_or_developer>.<app_name>` (e.g., `com.example.p1`).
   - Determines the underlying Java/Kotlin source package and module namespace.
3. **Save Location:**
   - The physical filesystem path on disk.
   - **Crucial Recommendation:** Ensure the path contains no whitespace or special characters (`#`, `@`, `!`) to avoid Gradle compiler path parsing errors.
4. **Language:**
   - **Kotlin (Mandatory):** Android's official first-class programming language. Safer, cleaner, expressive, and the sole language of Jetpack Compose.
   - **Java:** Maintained for legacy compatibility.
5. **Minimum SDK (`minSdk`):**
   - The oldest version of Android OS on which your application is guaranteed to install and run.
   - **API 24 (Android 7.0 Nougat):** Supports approximately 99.5% of active devices worldwide.
   - **API 33 (Android 13):** Supports modern features out of the box with fewer compatibility checks.
   - Click **"Help me choose"** inside the wizard to inspect the real-time device distribution chart across Android versions.
6. **Build Configuration Language:**
   - **Kotlin DSL (`build.gradle.kts` - Recommended):** Written in Kotlin; offers compile-time type safety, immediate syntax validation, and code autocompletion.
   - **Groovy DSL (`build.gradle`):** Dynamic scripting language used in legacy Android tutorials.

Click **Finish** to let Android Studio generate your project scaffold.

---

## 🖥️ Section 3: Exploring the Android Studio Interface & Mandatory Tabs

After opening the project, Android Studio will initialize Gradle. Students are required to understand the layout and the following 9 mandatory tool windows:

```
+--------------------------------------------------------------------------------------+
| Top Menu: File  Edit  View  Navigate  Code  Analyze  Build  Run  Tools  Help         |
| Toolbar:  [Project: p1]  [Device: Pixel 8 API 34]  [▶ Run (Shift+F10)]  [Debug (Shift+F9)]|
+----------+-------------------------------------------------------------+-------------+
| Left     | Editor Area (MainActivity.kt / AndroidManifest.xml)         | Right       |
| Sidebar: |                                                             | Sidebar:    |
|          | @Composable                                                 |             |
| [Project]| fun Greeting(name: String) {                                | [Gradle]    |
|          |     Text(text = "Hello $name!")                             |             |
| [Problems| }                                                           | [Device     |
|   ]      |                                                             |  Manager]   |
|          +-------------------------------------------------------------+             |
|          | Bottom Panel:                                               | [Running    |
|          | [Build]  [Logcat]  [Terminal]  [App Quality Insights]       |  Devices]   |
+----------+-------------------------------------------------------------+-------------+
```

### 3.1 Left Sidebar
* **1. 📁 Project Tab (`Alt + 1` / `Cmd + 1`):**
  - Displays your files. Allows switching between:
    - **Android View:** A logical, simplified structure grouping manifests, Kotlin source files, resources, and Gradle scripts.
    - **Project Files View:** Shows the exact raw directory layout on your hard drive (including hidden files, `.idea`, and `build/` artifacts).
* **2. ⚠️ Problems Tab:**
  - Aggregates real-time compiler warnings, syntax errors, missing imports, and Android Lint inspections with links directly to offending lines.

### 3.2 Bottom Panel
* **3. 🔨 Build Tab:**
  - **The primary monitor for Gradle operations.**
  - Shows real-time progress for Gradle sync, compilation tasks (`:app:compileDebugKotlin`), packaging, and DEX transformation.
  - If code fails to compile, the Build tab details the exact error trace and file location.
* **4. 💻 Terminal Tab (`Alt + F12`):**
  - Integrated command-line shell (PowerShell on Windows) within the project's root folder.
  - Useful for running CLI commands, Git commands, or Gradle tasks:
    ```bash
    ./gradlew tasks
    ./gradlew assembleDebug
    ```
* **5. 📜 Logcat Tab (`Alt + 6`):**
  - Android's real-time diagnostic console.
  - Displays system logs, runtime warnings, garbage collection events, and your custom debug statements.
  - Provides search filters by package (`package:mine`), log level (`Error`, `Warn`, `Info`, `Debug`), and custom tags.

### 3.3 Right Sidebar
* **6. 🐘 Gradle Tab:**
  - Tree view displaying all Gradle tasks organized by module (`p1 > app > Tasks > build`).
  - Contains the **"Sync Project with Gradle Files"** button to re-evaluate dependencies.
* **7. 📱 Device Manager Tab:**
  - The management console for creating, editing, wiping, and deleting Android Virtual Devices (AVD) and managing physical Android devices connected via USB or Wi-Fi ADB.
* **8. 🖥️ Running Devices Tab:**
  - Hosts the interactive Android emulator directly inside an IDE pane alongside your source code.
* **9. 🔔 Notifications Tab:**
  - Alerts you about plugin updates, SDK updates, Gradle sync completions, and build warnings.

---

## 📲 Section 4: Android Virtual Device (AVD) Setup

To execute your application on a virtual Android device:

```
+-------------------------------------------------------------------------------+
|                         Device Manager - New Virtual Device                   |
+-------------------------------------------------------------------------------+
| Step 1: Select Hardware                                                       |
|         - Category: Phone                                                     |
|         - Device Profile: Pixel 8 or Pixel 7 Pro (with Google Play icon)      |
|         - Screen Size & Resolution: 1080 x 2400 (420 dpi)                     |
|                                                                               |
| Step 2: Select System Image                                                   |
|         - Release Name: VanillaIceCream (API 35) or UpsideDownCake (API 34)   |
|         - Architecture / ABI: x86_64                                          |
|         - Target: Android with Google APIs / Google Play                      |
|         - Click "Download" next to the release if not already cached.         |
|                                                                               |
| Step 3: Configuration Summary                                                 |
|         - AVD Name: Pixel 8 API 34                                            |
|         - Emulated Performance: Automatic / Hardware - GLES 2.0               |
|         - Click "Finish"                                                      |
+-------------------------------------------------------------------------------+
```

### Verifying the Setup:
1. Open the **Device Manager** tool window on the right.
2. Click the **Play (▶)** button next to your virtual device.
3. Observe the device booting up in the **Running Devices** tab.
4. Once the Android home screen appears with app icons, your virtual environment is ready.

---

### 🏁 Session 1 Checkpoint
Before concluding Session 1, verify that:
- [ ] Android Studio is fully installed and configured.
- [ ] Your first "p1" project has completed its initial Gradle sync without errors.
- [ ] You can locate and toggle all 9 mandatory tool windows.
- [ ] Your AVD emulator is successfully created and booted.

---
➡️ **Next Lab:** Proceed to [Practical 1 (Session 2)](./Practical_1_Session_2.md) for Project Architecture, `AndroidManifest.xml` deep dive, Kotlin fundamentals, and running your personalized application!
