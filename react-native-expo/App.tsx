import React, { useState } from 'react';
import { View, StyleSheet, SafeAreaView, StatusBar } from 'react-native';
import { colors } from './src/theme/colors';
import { AppFlowState, AppTab, CeaserDevice, EcosystemDevice, UserProfile } from './src/types';
import { SplashScreen } from './src/screens/SplashScreen';
import { OnboardingScreen } from './src/screens/OnboardingScreen';
import { AuthScreen } from './src/screens/AuthScreen';
import { HomeScreen } from './src/screens/HomeScreen';
import { DevicesScreen } from './src/screens/DevicesScreen';
import { NixScreen } from './src/screens/NixScreen';
import { ProfileScreen } from './src/screens/ProfileScreen';
import { DesktopQrScannerModal } from './src/screens/DesktopQrScannerModal';
import { GlassesDetailsModal } from './src/screens/GlassesDetailsModal';
import { PairingModal } from './src/screens/PairingModal';
import { CeaserTopBar } from './src/components/CeaserTopBar';
import { CeaserBottomNav } from './src/components/CeaserBottomNav';

export default function App() {
  const [flowState, setFlowState] = useState<AppFlowState>('SPLASH');
  const [selectedTab, setSelectedTab] = useState<AppTab>('HOME');
  const [qrModalVisible, setQrModalVisible] = useState(false);
  const [glassesModalVisible, setGlassesModalVisible] = useState(false);
  const [pairingModalVisible, setPairingModalVisible] = useState(false);
  const [initialNixPrompt, setInitialNixPrompt] = useState<string | undefined>(undefined);

  // Ecosystem State
  const [user, setUser] = useState<UserProfile>({
    id: 'usr_ceaser_992',
    name: 'Alex Mercer',
    email: 'alex.mercer@ceaser.ai',
    isSignedIn: true,
    tier: 'PRO TIER',
  });

  const [glassesDevice, setGlassesDevice] = useState<CeaserDevice>({
    id: 'dev_glasses_01',
    name: 'CEASER NIX Frames',
    model: 'Model NIX-04 Prism Edition',
    status: 'NOT_PAIRED',
    batteryLevel: 84,
    firmwareVersion: 'v2.4.1-rc3',
    ancMode: 'ADAPTIVE',
    spatialAudioEnabled: true,
    micMuted: false,
    opticalDisplayActive: true,
    wearDetected: true,
  });

  const [desktopDevice, setDesktopDevice] = useState<EcosystemDevice>({
    id: 'dev_desktop_01',
    name: 'CEASER NIX Desktop',
    type: 'WORKSTATION',
    os: 'macOS Sonoma • Apple Silicon',
    isOnline: true,
    ipAddress: '192.168.1.142',
    activeContext: 'AI Ecosystem Studio',
    syncedTokens: 24,
  });

  // Handlers
  const handleAuthSuccess = (email: string, name: string) => {
    setUser((prev) => ({
      ...prev,
      email,
      name: name || 'Alex Mercer',
      isSignedIn: true,
    }));
    setFlowState('MAIN');
  };

  const handleGuestMode = () => {
    setUser((prev) => ({
      ...prev,
      email: 'guest@ceaser.ai',
      name: 'Guest Explorer',
      isSignedIn: true,
    }));
    setFlowState('MAIN');
  };

  const handleQrScanSuccess = (nodeId: string) => {
    setQrModalVisible(false);
    if (flowState === 'AUTH') {
      setUser((prev) => ({
        ...prev,
        email: 'desktop.node@ceaser.ai',
        name: 'Desktop Synced User',
        isSignedIn: true,
      }));
      setFlowState('MAIN');
    }
  };

  const handleSignOut = () => {
    setUser((prev) => ({ ...prev, isSignedIn: false }));
    setFlowState('AUTH');
  };

  const handleSyncDesktop = () => {
    // Triggers desktop synchronization
  };

  const handleNavigateToNix = (prompt?: string) => {
    setInitialNixPrompt(prompt);
    setSelectedTab('NIX');
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <StatusBar barStyle="light-content" backgroundColor={colors.black} />

      {flowState === 'SPLASH' && (
        <SplashScreen onFinish={() => setFlowState('ONBOARDING')} />
      )}

      {flowState === 'ONBOARDING' && (
        <OnboardingScreen onFinish={() => setFlowState('AUTH')} />
      )}

      {flowState === 'AUTH' && (
        <AuthScreen
          onSuccess={handleAuthSuccess}
          onOpenQrScanner={() => setQrModalVisible(true)}
          onGuestMode={handleGuestMode}
        />
      )}

      {flowState === 'MAIN' && (
        <View style={styles.mainContainer}>
          {selectedTab !== 'NIX' && (
            <CeaserTopBar
              connectionStatus={glassesDevice.status}
              deviceName={glassesDevice.name}
              isDesktopLinked={desktopDevice.isOnline}
              onDevicePress={() => setSelectedTab('DEVICES')}
              onSyncPress={handleSyncDesktop}
            />
          )}

          <View style={styles.screenContent}>
            {selectedTab === 'HOME' && (
              <HomeScreen
                device={glassesDevice}
                desktop={desktopDevice}
                user={user}
                onOpenDeviceDetails={() => setGlassesModalVisible(true)}
                onOpenPairing={() => setPairingModalVisible(true)}
                onNavigateToNix={handleNavigateToNix}
                onOpenQrScanner={() => setQrModalVisible(true)}
                onSyncDesktop={handleSyncDesktop}
              />
            )}

            {selectedTab === 'DEVICES' && (
              <DevicesScreen
                device={glassesDevice}
                desktop={desktopDevice}
                onOpenGlassesDetails={() => setGlassesModalVisible(true)}
                onPairNew={() => setPairingModalVisible(true)}
                onOpenQrScanner={() => setQrModalVisible(true)}
              />
            )}

            {selectedTab === 'NIX' && (
              <NixScreen
                initialPrompt={initialNixPrompt}
                onClearInitialPrompt={() => setInitialNixPrompt(undefined)}
              />
            )}

            {selectedTab === 'PROFILE' && (
              <ProfileScreen
                user={user}
                onSignOut={handleSignOut}
                onOpenQrScanner={() => setQrModalVisible(true)}
              />
            )}
          </View>

          <CeaserBottomNav selectedTab={selectedTab} onTabSelect={setSelectedTab} />
        </View>
      )}

      {/* Desktop QR Scanner Modal */}
      <DesktopQrScannerModal
        visible={qrModalVisible}
        onClose={() => setQrModalVisible(false)}
        onSuccess={handleQrScanSuccess}
      />

      {/* Glasses Details Modal */}
      <GlassesDetailsModal
        visible={glassesModalVisible}
        device={glassesDevice}
        onClose={() => setGlassesModalVisible(false)}
        onUpdateDevice={(updated) => setGlassesDevice((prev) => ({ ...prev, ...updated }))}
        onToggleConnection={() => {
          setGlassesDevice((prev) => ({
            ...prev,
            status: prev.status === 'CONNECTED' ? 'NOT_PAIRED' : 'CONNECTED',
          }));
        }}
      />

      {/* Pairing Modal */}
      <PairingModal
        visible={pairingModalVisible}
        onClose={() => setPairingModalVisible(false)}
        onPairSuccess={() => {
          setGlassesDevice((prev) => ({
            ...prev,
            status: 'CONNECTED',
          }));
        }}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.black,
  },
  mainContainer: {
    flex: 1,
    backgroundColor: colors.black,
  },
  screenContent: {
    flex: 1,
  },
});
