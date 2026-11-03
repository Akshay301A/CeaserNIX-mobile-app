package com.example.model

enum class ConnectionStatus {
    CONNECTED,
    CONNECTING,
    DISCONNECTED,
    NOT_PAIRED,
    ERROR
}

enum class AudioRouting(val label: String) {
    GLASSES_AUTO("Glasses (Adaptive)"),
    PHONE_SPEAKER("Phone Speakers"),
    HEADPHONES("External Device")
}

enum class AncMode(val label: String) {
    ADAPTIVE("Adaptive Spatial"),
    TRANSPARENCY("Transparency"),
    OFF("Passive Off")
}

data class CeaserDevice(
    val id: String = "ceaser-nix-04",
    val name: String = "CEASER NIX Frame-04",
    val model: String = "CEASER NIX Smart Glasses",
    val status: ConnectionStatus = ConnectionStatus.CONNECTED,
    val batteryGlasses: Int = 84,
    val batteryCase: Int = 95,
    val isCharging: Boolean = false,
    val firmwareVersion: String = "v2.4.1",
    val latestFirmwareAvailable: String = "v2.4.1",
    val audioRouting: AudioRouting = AudioRouting.GLASSES_AUTO,
    val micActive: Boolean = true,
    val ancMode: AncMode = AncMode.ADAPTIVE,
    val wearDetection: Boolean = true,
    val touchControls: Boolean = true,
    val notificationSync: Boolean = true,
    val spatialAudioEnabled: Boolean = true,
    val lastSynced: String = "Just now",
    val macAddress: String = "C8:4B:96:A2:14:FE"
)

data class DiscoveredDevice(
    val id: String,
    val name: String,
    val model: String,
    val rssi: Int,
    val isCeaserOfficial: Boolean = true
)

data class EcosystemDevice(
    val id: String,
    val name: String,
    val type: String,
    val isOnline: Boolean,
    val lastSync: String,
    val detail: String
)

data class ActivityLog(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val tag: String
)
