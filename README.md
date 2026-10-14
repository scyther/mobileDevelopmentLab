# Mobile Application Development Lab

## 🛠️ Tech Stack & Prerequisites
- **Language:** [Kotlin](https://kotlinlang.org/)
- **IDE:** [Android Studio](https://developer.android.com/studio)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) ([Material 3](https://m3.material.io/))
- **Target Platform:** Android (Minimum SDK API 24+)
- **Build System:** Gradle (Kotlin DSL - `build.gradle.kts`)

---

## 📚 Lab Practicals Index

| Practical No. | Session / Topic | Manual Link | Reference Code |
| :---: | :--- | :---: | :---: |
| **01 (Session 1)** | **Session 1: Environment Setup, Project Wizard Exploration & IDE Architecture** | [Session 1 Manual](./Practical_1_Session_1.md) | [Project `p1`](./p1/) |
| **01 (Session 2)** | **Session 2: Project Anatomy, Kotlin Tour, Code Deep Dive & App Execution** | [Session 2 Manual](./Practical_1_Session_2.md) | [Project `p1`](./p1/) |
| **02** | **Practical 2: Modern Restaurant Registration Form (Rows, Columns, RadioButtons & State)** | [Practical 2 Manual](./Practical_2.md) | [Project `p1` (`practical2`)](./p1/app/src/main/java/com/example/p1/practical2/) |
| **03** | **Practical 3: Production-Level Type-Safe Navigation (Routes, NavHost & Arguments)** | [Practical 3 Manual](./Practical_3.md) | [Project `Paytm` (`navigation`)](./Paytm/app/src/main/java/com/paytm/mobileApp/theory/navigation/) |

---

## 📂 Reference Projects

- **[`p1/`](./p1/)**:
  - **Practical 1:** Starter Jetpack Compose application demonstrating project structure, `AndroidManifest.xml` setup, Gradle build scripts, and basic Compose greetings on an Android Virtual Device (AVD).
  - **Practical 2 (`com.example.p1.practical2`):** Modern restaurant data entry form built with `Column`, `Row`, `ElevatedCard`, custom `RadioButton` groups, real-time input validation, and a dynamic reactive record ledger. Switch between practicals seamlessly using the dashboard menu buttons.
- **[`Paytm/`](./Paytm/)**:
  - **Practical 3 (`com.paytm.mobileApp.theory.navigation`):** Modern type-safe navigation implementation in Jetpack Compose using Kotlinx Serialization, sealed class routes, argument passing via `toRoute<T>()`, and decoupled screen composables.

---

## 🌐 Resources & Documentation

- 🎨 **[Material 3 Design Guidelines (`m3.material.io`)](https://m3.material.io/)**: Official specifications, design tokens, color system, and UI component catalog for Material Design 3.
- 📱 **[Jetpack Compose Material 3 Guide](https://developer.android.com/develop/ui/compose/designsystems/material3)**: Android developer documentation and best practices for implementing Material 3 in Compose.
- 📖 **[Android Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)**: Learn modern declarative UI development for Android.
- 🔄 **[Android Activity Lifecycle Guide](https://developer.android.com/guide/components/activities/activity-lifecycle)**: Understand activity states, transitions, state preservation, and callbacks like `onCreate()`.
- 💻 **[Kotlin Language Documentation](https://kotlinlang.org/docs/home.html)**: Syntax guides, tutorials, and standard library documentation.