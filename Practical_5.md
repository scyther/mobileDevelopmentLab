# Practical 5: Restaurant Table Management App

> **Course:** Mobile Application Development  
> **Practical:** Practical 5  
> **Topic:** Offline Restaurant Table Management App using Room SQLite, MVVM & Type-Safe Navigation  

---

## 📌 Problem Statement

Build a fully functional offline Android application using **Kotlin**, **Jetpack Compose**, **Material 3**, **Room SQLite**, **MVVM Architecture**, and **Navigation Compose**.

---

## 📱 Mandatory Screens

1. **Tables Screen (Floor Plan):**
   - Displays all restaurant tables in a grid layout with real-time status indicators (**Available** vs. **Occupied**).
   - Provides an option/dialog to add new tables (**Table Number**, **Seating Capacity**).

2. **Menu Screen:**
   - Allows adding, viewing, and deleting food items with **Name**, **Price**, and **Category**.

3. **Order Screen:**
   - Accessed by tapping any table from the Tables Screen.
   - Displays active order items for that specific table.
   - Allows selecting items from the saved menu.
   - Supports live quantity updates (`+` / `-`).
   - Calculates the live total bill.
   - Provides a **Checkout** option to complete the order and mark the table as **Available**.

---

## ⚙️ Requirements

1. **Bottom Navigation Bar:**
   - Implement a Material 3 `NavigationBar` to switch seamlessly between the **Tables Screen** and the **Menu Screen** while preserving back-stack state properly.

2. **Type-Safe Production Navigation Setup:**
   - Use Kotlin Serialization (`@Serializable` route objects/data classes) with Jetpack Navigation Compose for compile-time type-safe route definitions and argument passing (such as passing `tableId` to the Order Screen) instead of hardcoded string routes.

3. **Local Database Persistence:**
   - Implement a relational Room database (`MenuItems`, `RestaurantTables`, `Orders`, `OrderItems`) using DAOs, Flows, and ViewModels (`StateFlow`) so all data persists across app restarts.

4. **Clean State Management:**
   - Keep UI composables stateless by hoisting state to ViewModels and exposing immutable UI state classes.

5. **Proper File/Directory Structuring:**
   - Follow clean architecture and separation of concerns.
   - Ensure you don't create very large files having more than 200 lines of code.

6. **APK Generation & App Icon:**
   - Configure a custom app icon (adaptive launcher icon).
   - Generate a proper installable APK (`app-debug.apk` or signed `app-release.apk`).
