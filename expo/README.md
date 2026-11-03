# CEASER Mobile — React Native (Expo) Cross-Platform App

This directory (`/expo`) contains the complete **React Native + Expo** project for CEASER, ready to run on both **iOS** and **Android**.

---

## 🚀 Quick Start (iOS & Android)

### 1. Prerequisites
- Node.js (v18+)
- npm or yarn
- Expo Go app on your physical iPhone / Android device (or an iOS Simulator / Android Emulator on Mac/PC)

### 2. Install Dependencies
```bash
cd expo
npm install
```

### 3. Start Expo Development Server
```bash
npx expo start
```

- **iOS:** Press `i` to open in iOS Simulator, or scan the QR code with your iPhone camera (via the **Expo Go** app).
- **Android:** Press `a` to open in Android Emulator, or scan the QR code via **Expo Go** on Android.
- **Web:** Press `w` to open in your browser.

---

## 📁 Architecture & Screen Structure

- `App.tsx` — Root tab navigation and ecosystem state coordinator
- `src/theme/colors.ts` — CEASER Obsidian & Electric Violet theme tokens
- `src/types/index.ts` — Hardware telemetry and messaging type contracts
- `src/screens/HomeScreen.tsx` — Dashboard, smart glasses hero card, battery gauges
- `src/screens/DevicesScreen.tsx` — Wearable device controls, 3D audio & sensor toggles
- `src/screens/NixScreen.tsx` — AI chat with seamlessly docked input, prompt pills, and Glasses HUD Mirror Mode
- `src/screens/ProfileScreen.tsx` — Hero identity stage, verified badge, and segmented runtime mode switcher
- `src/screens/PairingScreen.tsx` — BLE device discovery, scanning, and handshake onboarding
