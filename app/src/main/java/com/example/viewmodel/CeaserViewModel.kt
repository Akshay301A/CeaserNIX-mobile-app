package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CeaserRepository
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppTab {
    HOME,
    DEVICES,
    NIX,
    PROFILE
}

enum class AppFlowState {
    SPLASH,
    ONBOARDING,
    AUTH,
    MAIN
}

enum class PairingStep {
    INTRO,
    PERMISSIONS,
    SCANNING,
    DISCOVERED,
    PAIRING,
    SUCCESS,
    TROUBLESHOOTING
}

class CeaserViewModel(
    private val repository: CeaserRepository = CeaserRepository()
) : ViewModel() {

    // App Flow Navigation (Splash -> Onboarding -> Auth -> Main)
    private val _flowState = MutableStateFlow(AppFlowState.SPLASH)
    val flowState: StateFlow<AppFlowState> = _flowState.asStateFlow()

    private val _showQrScanner = MutableStateFlow(false)
    val showQrScanner: StateFlow<Boolean> = _showQrScanner.asStateFlow()

    private val _isDesktopLinked = MutableStateFlow(true)
    val isDesktopLinked: StateFlow<Boolean> = _isDesktopLinked.asStateFlow()

    // Tab Navigation
    private val _selectedTab = MutableStateFlow(AppTab.HOME)
    val selectedTab: StateFlow<AppTab> = _selectedTab.asStateFlow()

    // Overlay Screens
    private val _showPairingFlow = MutableStateFlow(false)
    val showPairingFlow: StateFlow<Boolean> = _showPairingFlow.asStateFlow()

    private val _showDeviceDetails = MutableStateFlow(false)
    val showDeviceDetails: StateFlow<Boolean> = _showDeviceDetails.asStateFlow()

    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    private val _showApiConfigSheet = MutableStateFlow(false)
    val showApiConfigSheet: StateFlow<Boolean> = _showApiConfigSheet.asStateFlow()

    private val _showFirmwareSheet = MutableStateFlow(false)
    val showFirmwareSheet: StateFlow<Boolean> = _showFirmwareSheet.asStateFlow()

    // Status snackbar / toast message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Repository Flows
    val appMode: StateFlow<AppMode> = repository.appMode
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val apiConfig: StateFlow<BackendApiConfig> = repository.apiConfig
    val device: StateFlow<CeaserDevice> = repository.device
    val ecosystemDevices: StateFlow<List<EcosystemDevice>> = repository.ecosystemDevices
    val activityLogs: StateFlow<List<ActivityLog>> = repository.activityLogs
    val messages: StateFlow<List<ChatMessage>> = repository.messages

    // Pairing State
    private val _pairingStep = MutableStateFlow(PairingStep.INTRO)
    val pairingStep: StateFlow<PairingStep> = _pairingStep.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    val discoveredDevices: StateFlow<List<DiscoveredDevice>> = _discoveredDevices.asStateFlow()

    private val _selectedDeviceToPair = MutableStateFlow<DiscoveredDevice?>(null)
    val selectedDeviceToPair: StateFlow<DiscoveredDevice?> = _selectedDeviceToPair.asStateFlow()

    private val _pairingProgress = MutableStateFlow(0f)
    val pairingProgress: StateFlow<Float> = _pairingProgress.asStateFlow()

    private var scanJob: Job? = null

    // NIX Assistant UI State
    private val _chatInput = MutableStateFlow("")
    val chatInput: StateFlow<String> = _chatInput.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _isVoiceListening = MutableStateFlow(false)
    val isVoiceListening: StateFlow<Boolean> = _isVoiceListening.asStateFlow()

    fun selectTab(tab: AppTab) {
        _selectedTab.value = tab
    }

    fun finishSplash() {
        _flowState.value = AppFlowState.ONBOARDING
    }

    fun finishOnboarding() {
        _flowState.value = AppFlowState.AUTH
    }

    fun finishAuth(email: String, name: String) {
        repository.signIn(email, name)
        _flowState.value = AppFlowState.MAIN
    }

    fun logoutToAuth() {
        repository.signOut()
        _flowState.value = AppFlowState.AUTH
    }

    fun openQrScanner() {
        _showQrScanner.value = true
    }

    fun closeQrScanner() {
        _showQrScanner.value = false
    }

    fun linkDesktopViaQr(code: String = "CEASER-NODE-MAC-984") {
        _isDesktopLinked.value = true
        _showQrScanner.value = false
        if (_flowState.value == AppFlowState.AUTH) {
            repository.signIn("desktop.node@ceaser.ai", "Desktop Synced User")
            _flowState.value = AppFlowState.MAIN
        }
    }

    fun setFlowState(state: AppFlowState) {
        _flowState.value = state
    }

    fun openPairingFlow() {
        _pairingStep.value = PairingStep.INTRO
        _discoveredDevices.value = emptyList()
        _pairingProgress.value = 0f
        _showPairingFlow.value = true
    }

    fun closePairingFlow() {
        scanJob?.cancel()
        _showPairingFlow.value = false
    }

    fun openDeviceDetails() {
        _showDeviceDetails.value = true
    }

    fun closeDeviceDetails() {
        _showDeviceDetails.value = false
    }

    fun openAuthDialog() {
        _showAuthDialog.value = true
    }

    fun closeAuthDialog() {
        _showAuthDialog.value = false
    }

    fun openApiConfigSheet() {
        _showApiConfigSheet.value = true
    }

    fun closeApiConfigSheet() {
        _showApiConfigSheet.value = false
    }

    fun openFirmwareSheet() {
        _showFirmwareSheet.value = true
    }

    fun closeFirmwareSheet() {
        _showFirmwareSheet.value = false
    }

    fun showToast(msg: String) {
        // Suppressed onscreen toast popups per user request
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // Device actions
    fun toggleGlassesConnection() {
        repository.toggleGlassesConnection()
    }

    fun setAncMode(mode: AncMode) {
        repository.setAncMode(mode)
    }

    fun setAudioRouting(routing: AudioRouting) {
        repository.setAudioRouting(routing)
    }

    fun toggleWearDetection() {
        repository.toggleWearDetection()
    }

    fun toggleTouchControls() {
        repository.toggleTouchControls()
    }

    fun toggleSpatialAudio() {
        repository.toggleSpatialAudio()
    }

    fun toggleMic() {
        repository.toggleMic()
    }

    fun unpairDevice() {
        repository.unpairDevice()
        closeDeviceDetails()
    }

    // Pairing flow state machine
    fun proceedToPermissionCheck() {
        _pairingStep.value = PairingStep.PERMISSIONS
    }

    fun startBluetoothScanning() {
        _pairingStep.value = PairingStep.SCANNING
        _discoveredDevices.value = emptyList()

        scanJob?.cancel()
        scanJob = viewModelScope.launch {
            // Simulated realistic BLE peripheral discovery
            delay(1200)
            _discoveredDevices.value = listOf(
                DiscoveredDevice(
                    id = "ceaser-nix-04",
                    name = "CEASER NIX Frame-04",
                    model = "CEASER NIX Smart Glasses",
                    rssi = -42,
                    isCeaserOfficial = true
                )
            )
            delay(1000)
            _discoveredDevices.value = _discoveredDevices.value + listOf(
                DiscoveredDevice(
                    id = "ceaser-nix-02",
                    name = "CEASER NIX Frame-02 (Office)",
                    model = "CEASER NIX Smart Glasses",
                    rssi = -68,
                    isCeaserOfficial = true
                ),
                DiscoveredDevice(
                    id = "ceaser-clip-01",
                    name = "CEASER Clip-On HUD",
                    model = "Ecosystem Accessory",
                    rssi = -79,
                    isCeaserOfficial = true
                )
            )
            _pairingStep.value = PairingStep.DISCOVERED
        }
    }

    fun selectDeviceAndPair(device: DiscoveredDevice) {
        _selectedDeviceToPair.value = device
        _pairingStep.value = PairingStep.PAIRING

        viewModelScope.launch {
            _pairingProgress.value = 0.1f
            delay(500)
            _pairingProgress.value = 0.45f
            delay(600)
            _pairingProgress.value = 0.85f
            delay(500)
            _pairingProgress.value = 1.0f

            repository.pairNewGlasses(device)
            _pairingStep.value = PairingStep.SUCCESS
        }
    }

    fun openTroubleshooting() {
        _pairingStep.value = PairingStep.TROUBLESHOOTING
    }

    fun retryPairing() {
        startBluetoothScanning()
    }

    // NIX AI Assistant
    fun onChatInputChanged(newText: String) {
        _chatInput.value = newText
    }

    fun sendNixMessage(overrideText: String? = null) {
        val query = overrideText ?: _chatInput.value.trim()
        if (query.isBlank() || _isGenerating.value) return

        _chatInput.value = ""
        _isGenerating.value = true

        viewModelScope.launch {
            try {
                repository.sendNixMessage(query)
            } finally {
                _isGenerating.value = false
            }
        }
    }

    fun toggleVoiceListening() {
        val active = !_isVoiceListening.value
        _isVoiceListening.value = active
        if (active) {
            viewModelScope.launch {
                delay(3000)
                if (_isVoiceListening.value) {
                    _isVoiceListening.value = false
                    sendNixMessage("Summarize active notifications and battery telemetry")
                }
            }
        }
    }

    fun clearChat() {
        repository.clearChat()
    }

    // Mode and settings
    fun switchAppMode(mode: AppMode) {
        repository.setAppMode(mode)
    }

    fun updateApiConfig(url: String, token: String) {
        repository.updateApiConfig(url, token)
        closeApiConfigSheet()
    }

    fun triggerDesktopSync() {
        viewModelScope.launch {
            delay(800)
        }
    }

    fun signIn(email: String, name: String) {
        repository.signIn(email, name)
        closeAuthDialog()
    }

    fun signOut() {
        repository.signOut()
        closeAuthDialog()
    }
}
