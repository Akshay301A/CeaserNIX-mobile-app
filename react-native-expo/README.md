# CEASER Mobile — React Native / Expo Codebase

This is the universal cross-platform React Native / Expo codebase for **CEASER**, the wearable and workstation AI ecosystem companion app for iOS and Android.

## Features

- **Splash Screen**: Concentric pulsing brand emblem with initialization state.
- **Onboarding Carousel**: 3-step walkthrough highlighting the unified AI ecosystem, NIX Smart Glasses, and Desktop synchronization.
- **Authentication**: Sign In / Create Account with CEASER ID, "Same-Account Desktop Sync", "Scan Desktop QR Code to Connect", and sandbox demo mode.
- **Desktop Companion without Glasses**: Users can use the entire ecosystem without smart glasses by linking their workstation via unified login or scanning the Desktop QR code.
- **Desktop QR Scanner**: Interactive camera viewfinder with simulated scanning & instant node handshake.
- **NIX AI Assistant**: Clean conversational interface docked flush with the bottom navigation bar.
- **Devices & Profile**: Comprehensive telemetry, codec controls, and account management.

## Getting Started

### 1. Install Dependencies
```bash
cd react-native-expo
npm install
```

### 2. Run the App
```bash
# Start Expo development server
npx expo start

# Run on iOS Simulator
npx expo start --ios

# Run on Android Emulator / Device
npx expo start --android

# Or scan the terminal QR code with the Expo Go app on iPhone or Android
```
