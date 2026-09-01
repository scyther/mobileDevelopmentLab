# Practical 1: Installation of Android SDK, Developer Tools, and First Kotlin Project

This guide details the step-by-step process for installing and configuring **Android Studio**, understanding the project structure, and running your first Kotlin test application. Please ensure you have a stable internet connection, as the setup requires downloading several gigabytes of data.

---

### 1. Downloading and Installing Android Studio
* **Download:** Visit the official *Android Developer* website ([https://developer.android.com/studio](https://developer.android.com/studio)) and download the latest version of *Android Studio*.
* **Installation Wizard:** Run the downloaded installer. During the setup, ensure the **Android Virtual Device (AVD)** component is selected, which is necessary for the emulator.
* **Finish Setup:** Choose your preferred installation directory and complete the installation process.

---

### 2. SDK and Environment Configuration
* **Initial Setup:** Launch *Android Studio* and follow the setup wizard. Selecting the **Custom** installation type allows you to verify and select all necessary components.
* **Components:** Ensure the *Android SDK* and *Android SDK Platform* (API 36) are checked. The wizard will also download the emulator and performance drivers.
* **Troubleshooting:** If the download fails (common due to network issues), simply hit **Retry**. If errors persist later, navigate to your user folder, enable 'Hidden items' in Windows, and clear the *temp* folder in `AppData/Local/Android/Sdk`.

---

### 3. Creating Your First Project
* **New Project:** On the welcome screen, click **New Project**.
* **Select Template:** Choose **Empty Activity** (recommended for *Jetpack Compose* projects using Kotlin).
* **Configuration:** Provide an application name, define a unique package name, select **Kotlin** as the language, and set the **Minimum SDK**. Selecting *Android 7.0 (API 24)* is a recommended safe choice to ensure compatibility with ~99% of devices.
* **Project Structure:** Understand the generated project directory structure (`manifests`, `kotlin+java` source files, `res` resources, and `Gradle Scripts`).

---

### 4. Interface and Emulator Setup
* **Project Build:** After creation, wait for *Gradle* to finish building the project and downloading necessary dependencies.
* **Device Manager:** Click the **Device Manager** icon to add a new virtual device. Select a device model (e.g., *Pixel 7 Pro*) that includes the *Play Store* icon.
* **System Image:** Download the required system image for the virtual device.

---

### 5. Running Your First App
* **Execution:** Once the emulator is active, click the green **Run** button in the top toolbar.
* **Verification:** The emulator will launch your application. A successful build will display "Hello Android" on the virtual device screen.
