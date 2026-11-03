package com.example.data

import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class CeaserRepository {

    // Current App Mode
    private val _appMode = MutableStateFlow(AppMode.DESIGN_DEMO)
    val appMode: StateFlow<AppMode> = _appMode.asStateFlow()

    // User Profile
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // API Config
    private val _apiConfig = MutableStateFlow(BackendApiConfig())
    val apiConfig: StateFlow<BackendApiConfig> = _apiConfig.asStateFlow()

    // Paired CEASER Glasses
    private val _device = MutableStateFlow(
        CeaserDevice(
            id = "ceaser-nix-04",
            name = "CEASER NIX Frame-04",
            model = "CEASER NIX Smart Glasses",
            status = ConnectionStatus.CONNECTED,
            batteryGlasses = 84,
            batteryCase = 95,
            firmwareVersion = "v2.4.1",
            audioRouting = AudioRouting.GLASSES_AUTO,
            micActive = true,
            ancMode = AncMode.ADAPTIVE,
            wearDetection = true,
            touchControls = true,
            notificationSync = true,
            spatialAudioEnabled = true,
            lastSynced = "Just now"
        )
    )
    val device: StateFlow<CeaserDevice> = _device.asStateFlow()

    // Ecosystem devices
    private val _ecosystemDevices = MutableStateFlow(
        listOf(
            EcosystemDevice(
                id = "desktop-01",
                name = "CEASER NIX Desktop",
                type = "Workstation Node",
                isOnline = true,
                lastSync = "Synced 2m ago",
                detail = "Studio Ultra • macOS Sonoma"
            ),
            EcosystemDevice(
                id = "dock-01",
                name = "NIX Smart Dock",
                type = "Wireless Induction Stand",
                isOnline = true,
                lastSync = "Active",
                detail = "Fast Charging • Desk 01"
            )
        )
    )
    val ecosystemDevices: StateFlow<List<EcosystemDevice>> = _ecosystemDevices.asStateFlow()

    // Recent activity feed
    private val _activityLogs = MutableStateFlow(
        listOf(
            ActivityLog(
                id = "act-1",
                title = "Spatial Audio Active",
                description = "Routed dynamic 3D head-tracking stream to Frame-04",
                timestamp = "8m ago",
                tag = "AUDIO"
            ),
            ActivityLog(
                id = "act-2",
                title = "NIX Desktop Sync Completed",
                description = "Synchronized 14 workspace memory tokens & context graph",
                timestamp = "24m ago",
                tag = "CLOUD"
            ),
            ActivityLog(
                id = "act-3",
                title = "Double Tap Gesture Triggered",
                description = "Activated instant NIX AI voice buffer query",
                timestamp = "1h ago",
                tag = "WEARABLE"
            ),
            ActivityLog(
                id = "act-4",
                title = "Temple Wear Detection Sensor",
                description = "Auto-resumed playback when glasses were put on",
                timestamp = "2h ago",
                tag = "SENSOR"
            )
        )
    )
    val activityLogs: StateFlow<List<ActivityLog>> = _activityLogs.asStateFlow()

    // NIX AI Messages
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "m1",
                sender = Sender.NIX_ASSISTANT,
                text = "Welcome Alex. CEASER NIX Frame-04 is connected with 84% battery. Audio channel is live and temple touch gestures are calibrated. How can I assist your workflow today?",
                timestamp = "10:04 AM",
                suggestedActions = listOf(
                    "Summarize today's desktop notes",
                    "Check glasses microphone latency",
                    "Configure temple double-tap shortcut"
                ),
                hardwareContextNote = "Connected via CEASER BLE Protocol • Frame-04"
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun setAppMode(mode: AppMode) {
        _appMode.value = mode
    }

    fun updateApiConfig(baseUrl: String, token: String) {
        _apiConfig.value = _apiConfig.value.copy(
            baseUrl = baseUrl,
            authToken = token,
            isReachable = true
        )
    }

    fun toggleGlassesConnection() {
        val current = _device.value
        val newStatus = if (current.status == ConnectionStatus.CONNECTED) {
            ConnectionStatus.DISCONNECTED
        } else {
            ConnectionStatus.CONNECTED
        }
        _device.value = current.copy(
            status = newStatus,
            lastSynced = if (newStatus == ConnectionStatus.CONNECTED) "Just now" else "Disconnected"
        )
    }

    fun setAncMode(mode: AncMode) {
        _device.value = _device.value.copy(ancMode = mode)
    }

    fun setAudioRouting(routing: AudioRouting) {
        _device.value = _device.value.copy(audioRouting = routing)
    }

    fun toggleWearDetection() {
        _device.value = _device.value.copy(wearDetection = !_device.value.wearDetection)
    }

    fun toggleTouchControls() {
        _device.value = _device.value.copy(touchControls = !_device.value.touchControls)
    }

    fun toggleSpatialAudio() {
        _device.value = _device.value.copy(spatialAudioEnabled = !_device.value.spatialAudioEnabled)
    }

    fun toggleMic() {
        _device.value = _device.value.copy(micActive = !_device.value.micActive)
    }

    fun unpairDevice() {
        _device.value = _device.value.copy(
            status = ConnectionStatus.NOT_PAIRED,
            batteryGlasses = 0,
            batteryCase = 0
        )
    }

    fun pairNewGlasses(discoveredDevice: DiscoveredDevice) {
        _device.value = CeaserDevice(
            id = discoveredDevice.id,
            name = discoveredDevice.name,
            model = discoveredDevice.model,
            status = ConnectionStatus.CONNECTED,
            batteryGlasses = 92,
            batteryCase = 100,
            firmwareVersion = "v2.4.1",
            lastSynced = "Just paired"
        )
    }

    suspend fun sendNixMessage(userText: String): ChatMessage {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val now = timeFormat.format(Date())

        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = Sender.USER,
            text = userText,
            timestamp = now
        )
        _messages.value = _messages.value + userMsg

        // Simulate intelligent companion response
        delay(850)

        val reply = generateSmartNixResponse(userText, now)
        _messages.value = _messages.value + reply
        return reply
    }

    fun clearChat() {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val now = timeFormat.format(Date())
        _messages.value = listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = Sender.NIX_ASSISTANT,
                text = "New session initialized. Audio routing to CEASER NIX Frame-04 is standby. How can I assist?",
                timestamp = now
            )
        )
    }

    fun signOut() {
        _userProfile.value = _userProfile.value.copy(isSignedIn = false)
    }

    fun signIn(email: String, name: String = "Alex Mercer") {
        _userProfile.value = _userProfile.value.copy(
            isSignedIn = true,
            email = email,
            name = name
        )
    }

    private fun generateSmartNixResponse(prompt: String, timestamp: String): ChatMessage {
        val lower = prompt.lowercase()
        return when {
            lower.contains("battery") || lower.contains("power") -> {
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = Sender.NIX_ASSISTANT,
                    text = "Current CEASER NIX Frame-04 telemetry:\n• Left Temple: 84%\n• Right Temple: 86%\n• Charging Case: 95%\n• Estimated continuous runtime: 7.2 hours remaining with Adaptive Audio active.",
                    timestamp = timestamp,
                    hardwareContextNote = "Telemetry synced via Low-Energy Sub-channel",
                    suggestedActions = listOf("Enable Eco Mode", "Check Case Charge Status")
                )
            }
            lower.contains("desktop") || lower.contains("sync") -> {
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = Sender.NIX_ASSISTANT,
                    text = "CEASER NIX Desktop is currently synchronized over the secure mesh protocol. 3 active project contexts and 14 task reminders are up to date on your workstation.",
                    timestamp = timestamp,
                    suggestedActions = listOf("Open Desktop Mirror", "Review Shared Scratchpad")
                )
            }
            lower.contains("gesture") || lower.contains("touch") || lower.contains("shortcut") -> {
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = Sender.NIX_ASSISTANT,
                    text = "Here are your configured capacitive temple gestures:\n\n• Single Tap (Right): Play / Pause audio\n• Double Tap (Right): Summon NIX Voice Query\n• Slide Forward/Back: Volume level adjustments\n• Hold 2s (Left): Toggle Adaptive Spatial / Transparency mode",
                    timestamp = timestamp,
                    suggestedActions = listOf("Remap Gestures", "Test Touch Sensitivity")
                )
            }
            lower.contains("code") || lower.contains("api") || lower.contains("script") -> {
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = Sender.NIX_ASSISTANT,
                    text = "Here is the verified CEASER BLE Peripheral connection handler for integrating with NIX hardware protocol:",
                    timestamp = timestamp,
                    codeSnippet = """
// CEASER Peripheral BLE Connection
val serviceUuid = UUID.fromString("0000ce10-0000-1000-8000-00805f9b34fb")
val telemetryChar = UUID.fromString("0000ce11-0000-1000-8000-00805f9b34fb")

gatt.setCharacteristicNotification(telemetryChar, true)
println("CEASER NIX Frame-04 telemetry channel established.")
                    """.trimIndent(),
                    suggestedActions = listOf("Copy Code Snippet", "View Protocol Specs")
                )
            }
            else -> {
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = Sender.NIX_ASSISTANT,
                    text = "I've processed your query in the context of your active CEASER environment. All telemetry, audio buffers, and desktop sync endpoints are operating nominally. Let me know if you would like me to adjust any hardware parameters or coordinate with NIX Desktop.",
                    timestamp = timestamp,
                    hardwareContextNote = "Audio processed through dual beamforming microphones"
                )
            }
        }
    }
}
