package com.example

import com.example.model.*
import com.example.viewmodel.AppTab
import com.example.viewmodel.CeaserViewModel
import com.example.viewmodel.PairingStep
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CeaserCompanionTest {

    private lateinit var viewModel: CeaserViewModel

    @Before
    fun setUp() {
        viewModel = CeaserViewModel()
    }

    @Test
    fun testInitialDeviceState() {
        val device = viewModel.device.value
        assertEquals("CEASER NIX Frame-04", device.name)
        assertEquals(ConnectionStatus.CONNECTED, device.status)
        assertEquals(84, device.batteryGlasses)
        assertEquals(95, device.batteryCase)
        assertEquals(AncMode.ADAPTIVE, device.ancMode)
    }

    @Test
    fun testToggleGlassesConnection() {
        viewModel.toggleGlassesConnection()
        assertEquals(ConnectionStatus.DISCONNECTED, viewModel.device.value.status)

        viewModel.toggleGlassesConnection()
        assertEquals(ConnectionStatus.CONNECTED, viewModel.device.value.status)
    }

    @Test
    fun testAudioAndAncControls() {
        viewModel.setAncMode(AncMode.TRANSPARENCY)
        assertEquals(AncMode.TRANSPARENCY, viewModel.device.value.ancMode)

        viewModel.setAudioRouting(AudioRouting.PHONE_SPEAKER)
        assertEquals(AudioRouting.PHONE_SPEAKER, viewModel.device.value.audioRouting)

        viewModel.toggleSpatialAudio()
        assertFalse(viewModel.device.value.spatialAudioEnabled)

        viewModel.toggleMic()
        assertFalse(viewModel.device.value.micActive)
    }

    @Test
    fun testAppModeSwitching() {
        assertEquals(AppMode.DESIGN_DEMO, viewModel.appMode.value)

        viewModel.switchAppMode(AppMode.INTEGRATED)
        assertEquals(AppMode.INTEGRATED, viewModel.appMode.value)

        viewModel.switchAppMode(AppMode.DESIGN_DEMO)
        assertEquals(AppMode.DESIGN_DEMO, viewModel.appMode.value)
    }

    @Test
    fun testPairingFlowSteps() {
        viewModel.openPairingFlow()
        assertTrue(viewModel.showPairingFlow.value)
        assertEquals(PairingStep.INTRO, viewModel.pairingStep.value)

        viewModel.proceedToPermissionCheck()
        assertEquals(PairingStep.PERMISSIONS, viewModel.pairingStep.value)

        viewModel.closePairingFlow()
        assertFalse(viewModel.showPairingFlow.value)
    }

    @Test
    fun testTabNavigation() {
        assertEquals(AppTab.HOME, viewModel.selectedTab.value)

        viewModel.selectTab(AppTab.DEVICES)
        assertEquals(AppTab.DEVICES, viewModel.selectedTab.value)

        viewModel.selectTab(AppTab.NIX)
        assertEquals(AppTab.NIX, viewModel.selectedTab.value)

        viewModel.selectTab(AppTab.PROFILE)
        assertEquals(AppTab.PROFILE, viewModel.selectedTab.value)
    }

    @Test
    fun testNixMessageResponse() = runTest {
        val repo = com.example.data.CeaserRepository()
        val initialCount = repo.messages.value.size
        val response = repo.sendNixMessage("What is the battery level?")
        assertNotNull(response)
        val finalCount = repo.messages.value.size
        assertEquals(initialCount + 2, finalCount)
    }
}
