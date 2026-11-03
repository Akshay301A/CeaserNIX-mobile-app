package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.AppMode
import com.example.ui.components.CeaserBottomNav
import com.example.ui.components.CeaserTopBar
import com.example.ui.screens.*
import com.example.ui.theme.CeaserBlack
import com.example.ui.theme.CeaserTheme
import com.example.viewmodel.AppFlowState
import com.example.viewmodel.AppTab
import com.example.viewmodel.CeaserViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CeaserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CeaserTheme {
                CeaserApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CeaserApp(viewModel: CeaserViewModel) {
    val flowState by viewModel.flowState.collectAsStateWithLifecycle()
    val showQrScanner by viewModel.showQrScanner.collectAsStateWithLifecycle()

    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val appMode by viewModel.appMode.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val apiConfig by viewModel.apiConfig.collectAsStateWithLifecycle()
    val device by viewModel.device.collectAsStateWithLifecycle()
    val ecosystemDevices by viewModel.ecosystemDevices.collectAsStateWithLifecycle()
    val activityLogs by viewModel.activityLogs.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()

    val showPairingFlow by viewModel.showPairingFlow.collectAsStateWithLifecycle()
    val pairingStep by viewModel.pairingStep.collectAsStateWithLifecycle()
    val discoveredDevices by viewModel.discoveredDevices.collectAsStateWithLifecycle()
    val selectedDeviceToPair by viewModel.selectedDeviceToPair.collectAsStateWithLifecycle()
    val pairingProgress by viewModel.pairingProgress.collectAsStateWithLifecycle()

    val showDeviceDetails by viewModel.showDeviceDetails.collectAsStateWithLifecycle()
    val showAuthDialog by viewModel.showAuthDialog.collectAsStateWithLifecycle()
    val showApiConfigSheet by viewModel.showApiConfigSheet.collectAsStateWithLifecycle()
    val showFirmwareSheet by viewModel.showFirmwareSheet.collectAsStateWithLifecycle()

    val chatInput by viewModel.chatInput.collectAsStateWithLifecycle()
    val isGenerating by viewModel.isGenerating.collectAsStateWithLifecycle()
    val isVoiceListening by viewModel.isVoiceListening.collectAsStateWithLifecycle()

    // Android Predictive / Standard Back navigation handling
    BackHandler(
        enabled = showQrScanner || showPairingFlow || showDeviceDetails || (flowState == AppFlowState.MAIN && selectedTab != AppTab.HOME)
    ) {
        when {
            showQrScanner -> viewModel.closeQrScanner()
            showPairingFlow -> viewModel.closePairingFlow()
            showDeviceDetails -> viewModel.closeDeviceDetails()
            selectedTab != AppTab.HOME -> viewModel.selectTab(AppTab.HOME)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(CeaserBlack)) {
        when (flowState) {
            AppFlowState.SPLASH -> {
                SplashScreen(
                    onSplashFinished = { viewModel.finishSplash() }
                )
            }

            AppFlowState.ONBOARDING -> {
                OnboardingScreen(
                    onFinishOnboarding = { viewModel.finishOnboarding() }
                )
            }

            AppFlowState.AUTH -> {
                AuthScreen(
                    onAuthSuccess = { email, name ->
                        viewModel.finishAuth(email, name)
                    },
                    onOpenQrScanner = {
                        viewModel.openQrScanner()
                    },
                    onContinueGuest = {
                        viewModel.finishAuth("guest@ceaser.ai", "Guest Explorer")
                    }
                )
            }

            AppFlowState.MAIN -> {
                Scaffold(
                    topBar = {
                        // Keep top bar on Home, Devices, and Profile; NIX has its dedicated hardware header
                        if (selectedTab != AppTab.NIX) {
                            CeaserTopBar(
                                appMode = appMode,
                                connectionStatus = device.status,
                                deviceName = device.name,
                                onModeBadgeClick = {
                                    viewModel.openApiConfigSheet()
                                },
                                onDeviceStatusClick = {
                                    viewModel.openDeviceDetails()
                                },
                                onSyncClick = {
                                    viewModel.triggerDesktopSync()
                                }
                            )
                        }
                    },
                    bottomBar = {
                        CeaserBottomNav(
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                viewModel.selectTab(tab)
                            }
                        )
                    },
                    containerColor = CeaserBlack,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (selectedTab) {
                            AppTab.HOME -> {
                                HomeScreen(
                                    device = device,
                                    desktopDevice = ecosystemDevices.firstOrNull(),
                                    activityLogs = activityLogs,
                                    userProfile = userProfile,
                                    onPrimaryDeviceAction = {
                                        if (device.status == com.example.model.ConnectionStatus.NOT_PAIRED) {
                                            viewModel.openPairingFlow()
                                        } else {
                                            viewModel.openDeviceDetails()
                                        }
                                    },
                                    onManageDeviceClick = {
                                        viewModel.openDeviceDetails()
                                    },
                                    onToggleConnection = {
                                        viewModel.toggleGlassesConnection()
                                    },
                                    onAncModeSelected = { mode ->
                                        viewModel.setAncMode(mode)
                                    },
                                    onToggleSpatialAudio = {
                                        viewModel.toggleSpatialAudio()
                                    },
                                    onToggleMic = {
                                        viewModel.toggleMic()
                                    },
                                    onSyncDesktop = {
                                        viewModel.triggerDesktopSync()
                                    },
                                    onNavigateToTab = { tab ->
                                        viewModel.selectTab(tab)
                                    },
                                    onQuickPromptClick = { prompt ->
                                        viewModel.selectTab(AppTab.NIX)
                                        viewModel.sendNixMessage(prompt)
                                    },
                                    onOpenQrScanner = {
                                        viewModel.openQrScanner()
                                    }
                                )
                            }

                            AppTab.DEVICES -> {
                                DevicesScreen(
                                    device = device,
                                    ecosystemDevices = ecosystemDevices,
                                    onDeviceSelected = {
                                        viewModel.openDeviceDetails()
                                    },
                                    onPairNewDeviceClick = {
                                        viewModel.openPairingFlow()
                                    }
                                )
                            }

                            AppTab.NIX -> {
                                NixScreen(
                                    device = device,
                                    messages = messages,
                                    chatInput = chatInput,
                                    isGenerating = isGenerating,
                                    isVoiceListening = isVoiceListening,
                                    onInputChange = { text ->
                                        viewModel.onChatInputChanged(text)
                                    },
                                    onSendMessage = { overrideText ->
                                        viewModel.sendNixMessage(overrideText)
                                    },
                                    onToggleVoice = {
                                        viewModel.toggleVoiceListening()
                                    },
                                    onClearChat = {
                                        viewModel.clearChat()
                                    },
                                    onActionChipClick = { chipText ->
                                        viewModel.sendNixMessage(chipText)
                                    }
                                )
                            }

                            AppTab.PROFILE -> {
                                ProfileScreen(
                                    userProfile = userProfile,
                                    appMode = appMode,
                                    apiConfig = apiConfig,
                                    onSwitchAppMode = { newMode ->
                                        viewModel.switchAppMode(newMode)
                                    },
                                    onOpenApiConfig = {
                                        viewModel.openApiConfigSheet()
                                    },
                                    onOpenAuthDialog = {
                                        viewModel.openAuthDialog()
                                    },
                                    onSignOut = {
                                        viewModel.logoutToAuth()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Desktop QR Scanner Fullscreen Overlay (callable from Auth screen or Home screen)
        AnimatedVisibility(
            visible = showQrScanner,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            DesktopQrScannerScreen(
                onScanSuccess = { nodeId ->
                    viewModel.linkDesktopViaQr(nodeId)
                },
                onClose = {
                    viewModel.closeQrScanner()
                }
            )
        }

        // Full Screen Pairing Flow Overlay
        AnimatedVisibility(
            visible = showPairingFlow,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            PairingScreen(
                currentStep = pairingStep,
                discoveredDevices = discoveredDevices,
                selectedDevice = selectedDeviceToPair,
                pairingProgress = pairingProgress,
                onClose = { viewModel.closePairingFlow() },
                onProceedToPermissions = { viewModel.proceedToPermissionCheck() },
                onStartScan = { viewModel.startBluetoothScanning() },
                onSelectDevice = { dev -> viewModel.selectDeviceAndPair(dev) },
                onRetry = { viewModel.retryPairing() },
                onTroubleshooting = { viewModel.openTroubleshooting() }
            )
        }

        // Device Details Sheet
        if (showDeviceDetails) {
            DeviceDetailSheet(
                device = device,
                onDismiss = { viewModel.closeDeviceDetails() },
                onToggleConnection = { viewModel.toggleGlassesConnection() },
                onAncModeSelected = { mode -> viewModel.setAncMode(mode) },
                onAudioRoutingSelected = { routing -> viewModel.setAudioRouting(routing) },
                onToggleWearDetection = { viewModel.toggleWearDetection() },
                onToggleTouchControls = { viewModel.toggleTouchControls() },
                onToggleSpatialAudio = { viewModel.toggleSpatialAudio() },
                onToggleMic = { viewModel.toggleMic() },
                onCheckFirmware = { viewModel.openFirmwareSheet() },
                onUnpairDevice = { viewModel.unpairDevice() }
            )
        }

        // Firmware Details Sheet
        if (showFirmwareSheet) {
            FirmwareSheet(
                device = device,
                onDismiss = { viewModel.closeFirmwareSheet() }
            )
        }

        // API Configuration Sheet
        if (showApiConfigSheet) {
            ApiConfigSheet(
                config = apiConfig,
                onDismiss = { viewModel.closeApiConfigSheet() },
                onSave = { url, token -> viewModel.updateApiConfig(url, token) }
            )
        }

        // Auth Dialog
        if (showAuthDialog) {
            AuthDialog(
                onDismiss = { viewModel.closeAuthDialog() },
                onSignIn = { email, name -> viewModel.signIn(email, name) }
            )
        }
    }
}
