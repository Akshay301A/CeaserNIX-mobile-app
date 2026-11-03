import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  StatusBar,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { CeaserDevice, EcosystemDevice } from '../types';

interface HomeScreenProps {
  navigation: any;
  device: CeaserDevice;
  setDevice: React.Dispatch<React.SetStateAction<CeaserDevice>>;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({ navigation, device, setDevice }) => {
  const isConnected = device.status === 'CONNECTED';

  const toggleConnection = () => {
    setDevice((prev) => ({
      ...prev,
      status: prev.status === 'CONNECTED' ? 'DISCONNECTED' : 'CONNECTED',
    }));
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor={Colors.black} />

      {/* Top Bar */}
      <View style={styles.topBar}>
        <View style={styles.brandRow}>
          <View style={styles.logoBadge}>
            <Text style={styles.logoText}>C</Text>
          </View>
          <View>
            <Text style={styles.brandTitle}>CEASER</Text>
            <Text style={styles.brandSubtitle}>ECOSYSTEM</Text>
          </View>
        </View>

        <View style={styles.statusPillsRow}>
          <View style={styles.demoPill}>
            <View style={styles.demoDot} />
            <Text style={styles.demoPillText}>EXPO</Text>
          </View>

          <TouchableOpacity style={styles.connectionPill} onPress={toggleConnection}>
            <View
              style={[
                styles.connectionDot,
                { backgroundColor: isConnected ? Colors.success : Colors.error },
              ]}
            />
            <Ionicons
              name={isConnected ? 'bluetooth' : 'bluetooth-outline'}
              size={14}
              color={isConnected ? Colors.cyan : Colors.textMuted}
            />
          </TouchableOpacity>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent} showsVerticalScrollIndicator={false}>
        {/* Welcome Greeting */}
        <View style={styles.welcomeBox}>
          <Text style={styles.welcomeTitle}>Good day, Alex</Text>
          <Text style={styles.welcomeSubtitle}>CEASER Wearable Ecosystem • Active Session</Text>
        </View>

        {/* Primary Wearable Device Card */}
        <View style={styles.deviceCard}>
          <View style={styles.deviceCardHeader}>
            <View>
              <Text style={styles.deviceName}>{device.name}</Text>
              <Text style={styles.deviceModel}>{device.model}</Text>
            </View>

            <View
              style={[
                styles.statusBadge,
                {
                  backgroundColor: isConnected ? Colors.successMuted : Colors.errorMuted,
                  borderColor: isConnected ? Colors.success : Colors.error,
                },
              ]}
            >
              <View
                style={[
                  styles.statusBadgeDot,
                  { backgroundColor: isConnected ? Colors.success : Colors.error },
                ]}
              />
              <Text
                style={[
                  styles.statusBadgeText,
                  { color: isConnected ? Colors.success : Colors.error },
                ]}
              >
                {isConnected ? 'Connected' : 'Disconnected'}
              </Text>
            </View>
          </View>

          {/* Product Banner Stage */}
          <View style={styles.productStage}>
            <Ionicons name="glasses-outline" size={72} color={Colors.violetLight} />
            <Text style={styles.stageLabel}>CEASER NIX OPTICAL PRISM</Text>
            <View style={styles.fwPill}>
              <Text style={styles.fwPillText}>FW {device.firmwareVersion}</Text>
            </View>
          </View>

          {/* Real Telemetry Info */}
          {isConnected && (
            <View style={styles.telemetryRow}>
              <View style={styles.telemetryPill}>
                <Ionicons name="battery-charging-outline" size={16} color={Colors.cyan} />
                <View style={{ marginLeft: 6 }}>
                  <Text style={styles.telemetryVal}>{device.batteryGlasses}%</Text>
                  <Text style={styles.telemetryLbl}>Glasses</Text>
                </View>
              </View>

              <View style={styles.telemetryPill}>
                <Ionicons name="flash-outline" size={16} color={Colors.violetLight} />
                <View style={{ marginLeft: 6 }}>
                  <Text style={styles.telemetryVal}>{device.batteryCase}%</Text>
                  <Text style={styles.telemetryLbl}>Case</Text>
                </View>
              </View>

              <View style={styles.telemetryPill}>
                <Ionicons name="headset-outline" size={16} color={Colors.textPrimary} />
                <View style={{ marginLeft: 6 }}>
                  <Text style={styles.telemetryVal}>{device.ancMode}</Text>
                  <Text style={styles.telemetryLbl}>Spatial ANC</Text>
                </View>
              </View>
            </View>
          )}

          {/* Card Actions */}
          <View style={styles.actionRow}>
            <TouchableOpacity
              style={styles.primaryBtn}
              onPress={() => navigation.navigate('Devices')}
            >
              <Ionicons name="settings-outline" size={16} color={Colors.textPrimary} />
              <Text style={styles.primaryBtnText}>Manage Device</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.secondaryBtn} onPress={toggleConnection}>
              <Ionicons
                name="power-outline"
                size={18}
                color={isConnected ? Colors.error : Colors.success}
              />
            </TouchableOpacity>
          </View>
        </View>

        {/* NIX AI Quick-Access Card */}
        <View style={styles.nixCard}>
          <View style={styles.nixHeader}>
            <View style={styles.nixIconBadge}>
              <Ionicons name="sparkles" size={16} color={Colors.violetLight} />
            </View>
            <View style={{ flex: 1, marginLeft: 10 }}>
              <Text style={styles.nixTitle}>NIX Assistant</Text>
              <Text style={styles.nixSubtitle}>Neural Link Active • Temple Mic Live</Text>
            </View>
            <TouchableOpacity onPress={() => navigation.navigate('NIX')}>
              <Text style={styles.openChatLink}>Open Chat</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.quickPromptRow}>
            <TouchableOpacity
              style={styles.promptPill}
              onPress={() => navigation.navigate('NIX')}
            >
              <Text style={styles.promptPillText}>Check battery & latency</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.promptPill}
              onPress={() => navigation.navigate('NIX')}
            >
              <Text style={styles.promptPillText}>Sync notes to Desktop</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* Ecosystem Desktop Card */}
        <View style={styles.ecosystemCard}>
          <View style={styles.ecoRow}>
            <View style={styles.ecoIconBox}>
              <Ionicons name="desktop-outline" size={18} color={Colors.cyan} />
            </View>
            <View style={{ flex: 1, marginLeft: 10 }}>
              <Text style={styles.ecoTitle}>CEASER NIX Desktop</Text>
              <Text style={styles.ecoSubtitle}>Studio Ultra • macOS Sonoma (Linked)</Text>
            </View>
            <View style={styles.linkedBadge}>
              <Text style={styles.linkedBadgeText}>Active</Text>
            </View>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.black,
  },
  topBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingVertical: 12,
  },
  brandRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  logoBadge: {
    width: 34,
    height: 34,
    borderRadius: 8,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: 10,
  },
  logoText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.violetLight,
  },
  brandTitle: {
    color: Colors.textPrimary,
    fontSize: 15,
    fontWeight: 'bold',
    letterSpacing: 1.5,
  },
  brandSubtitle: {
    color: Colors.textMuted,
    fontSize: 9.5,
    letterSpacing: 1,
  },
  statusPillsRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  demoPill: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 12,
    backgroundColor: Colors.demoBg,
    borderWidth: 1,
    borderColor: Colors.demoGold,
  },
  demoDot: {
    width: 5,
    height: 5,
    borderRadius: 3,
    backgroundColor: Colors.demoGold,
    marginRight: 5,
  },
  demoPillText: {
    color: Colors.demoGold,
    fontSize: 10,
    fontWeight: 'bold',
  },
  connectionPill: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 5,
    borderRadius: 14,
    backgroundColor: Colors.surfaceCard,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    gap: 5,
  },
  connectionDot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
  scrollContent: {
    paddingHorizontal: 20,
    paddingBottom: 40,
    gap: 16,
  },
  welcomeBox: {
    marginTop: 4,
  },
  welcomeTitle: {
    color: Colors.textPrimary,
    fontSize: 22,
    fontWeight: 'bold',
  },
  welcomeSubtitle: {
    color: Colors.textSecondary,
    fontSize: 13,
  },
  deviceCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 22,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 18,
  },
  deviceCardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  deviceName: {
    color: Colors.textPrimary,
    fontSize: 18,
    fontWeight: 'bold',
  },
  deviceModel: {
    color: Colors.textSecondary,
    fontSize: 12,
    marginTop: 2,
  },
  statusBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 12,
    borderWidth: 1,
    gap: 5,
  },
  statusBadgeDot: {
    width: 5,
    height: 5,
    borderRadius: 3,
  },
  statusBadgeText: {
    fontSize: 10,
    fontWeight: '600',
  },
  productStage: {
    height: 140,
    backgroundColor: Colors.darkSurface,
    borderRadius: 16,
    marginTop: 14,
    alignItems: 'center',
    justifyContent: 'center',
    position: 'relative',
  },
  stageLabel: {
    color: Colors.textMuted,
    fontSize: 10,
    fontWeight: 'bold',
    letterSpacing: 1.5,
    marginTop: 6,
  },
  fwPill: {
    position: 'absolute',
    bottom: 8,
    left: 8,
    backgroundColor: Colors.surfaceElevated,
    paddingHorizontal: 8,
    paddingVertical: 3,
    borderRadius: 8,
  },
  fwPillText: {
    color: Colors.textMuted,
    fontSize: 9.5,
  },
  telemetryRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 14,
    gap: 8,
  },
  telemetryPill: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surfaceElevated,
    paddingHorizontal: 10,
    paddingVertical: 8,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  telemetryVal: {
    color: Colors.textPrimary,
    fontSize: 13,
    fontWeight: 'bold',
  },
  telemetryLbl: {
    color: Colors.textMuted,
    fontSize: 9,
  },
  actionRow: {
    flexDirection: 'row',
    marginTop: 16,
    gap: 10,
  },
  primaryBtn: {
    flex: 1,
    height: 44,
    backgroundColor: Colors.violet,
    borderRadius: 12,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 6,
  },
  primaryBtnText: {
    color: Colors.textPrimary,
    fontWeight: 'bold',
    fontSize: 13,
  },
  secondaryBtn: {
    width: 44,
    height: 44,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  nixCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 16,
  },
  nixHeader: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  nixIconBadge: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.violet,
    alignItems: 'center',
    justifyContent: 'center',
  },
  nixTitle: {
    color: Colors.textPrimary,
    fontSize: 15,
    fontWeight: 'bold',
  },
  nixSubtitle: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  openChatLink: {
    color: Colors.violetLight,
    fontWeight: 'bold',
    fontSize: 12,
  },
  quickPromptRow: {
    flexDirection: 'row',
    gap: 8,
    marginTop: 12,
  },
  promptPill: {
    flex: 1,
    backgroundColor: Colors.darkSurface,
    paddingVertical: 8,
    paddingHorizontal: 10,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    alignItems: 'center',
  },
  promptPillText: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  ecosystemCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 14,
  },
  ecoRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  ecoIconBox: {
    width: 34,
    height: 34,
    borderRadius: 10,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  ecoTitle: {
    color: Colors.textPrimary,
    fontSize: 14,
    fontWeight: 'bold',
  },
  ecoSubtitle: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  linkedBadge: {
    backgroundColor: Colors.successMuted,
    paddingHorizontal: 8,
    paddingVertical: 3,
    borderRadius: 8,
  },
  linkedBadgeText: {
    color: Colors.success,
    fontSize: 10,
    fontWeight: 'bold',
  },
});
