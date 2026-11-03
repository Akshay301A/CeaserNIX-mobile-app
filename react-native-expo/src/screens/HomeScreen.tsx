import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { CeaserDevice, EcosystemDevice, UserProfile } from '../types';

interface HomeScreenProps {
  device: CeaserDevice;
  desktop: EcosystemDevice;
  user: UserProfile;
  onOpenDeviceDetails: () => void;
  onOpenPairing: () => void;
  onNavigateToNix: (prompt?: string) => void;
  onOpenQrScanner: () => void;
  onSyncDesktop: () => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({
  device,
  desktop,
  user,
  onOpenDeviceDetails,
  onOpenPairing,
  onNavigateToNix,
  onOpenQrScanner,
  onSyncDesktop,
}) => {
  const isGlassesConnected = device.status === 'CONNECTED';

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      {/* Welcome Header */}
      <View style={styles.header}>
        <Text style={styles.greeting}>Good day, {user.name.split(' ')[0]}</Text>
        <Text style={styles.subGreeting}>CEASER Ecosystem • Active Session</Text>
      </View>

      {/* Desktop Ecosystem Card - Highlighted when no glasses connected */}
      <View style={styles.desktopCard}>
        <View style={styles.desktopCardHeader}>
          <View style={styles.desktopBadgeRow}>
            <View style={styles.desktopIconWrapper}>
              <Feather name="monitor" size={18} color={colors.cyan} />
            </View>
            <View>
              <Text style={styles.desktopTitle}>CEASER Desktop Ecosystem</Text>
              <Text style={styles.desktopSubtitle}>Linked via Account: {user.email}</Text>
            </View>
          </View>
          <View style={styles.syncedPill}>
            <Text style={styles.syncedText}>Synced</Text>
          </View>
        </View>

        <Text style={styles.desktopDesc}>
          No glasses needed! Full NIX AI, workspace memory tokens, and clipboard sync are live
          with your workstation.
        </Text>

        <View style={styles.desktopBtnRow}>
          <TouchableOpacity style={styles.desktopSyncBtn} onPress={onSyncDesktop} activeOpacity={0.8}>
            <Feather name="refresh-cw" size={13} color={colors.cyan} />
            <Text style={styles.desktopSyncBtnText}>Sync Node</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.desktopQrBtn} onPress={onOpenQrScanner} activeOpacity={0.8}>
            <Feather name="maximize" size={13} color={colors.textPrimary} />
            <Text style={styles.desktopQrBtnText}>Scan QR</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Flagship Wearable Device Card (CEASER NIX Smart Glasses) */}
      <View style={styles.deviceCard}>
        <View style={styles.deviceHeader}>
          <View>
            <Text style={styles.deviceCategory}>FLAGSHIP WEARABLE</Text>
            <Text style={styles.deviceName}>{device.name}</Text>
          </View>
          <View style={[styles.devicePill, isGlassesConnected ? styles.devicePillConnected : styles.devicePillMuted]}>
            <Text style={[styles.devicePillText, isGlassesConnected ? styles.devicePillTextConnected : styles.devicePillTextMuted]}>
              {isGlassesConnected ? 'Connected' : 'Not Paired'}
            </Text>
          </View>
        </View>

        {isGlassesConnected ? (
          <View style={styles.telemetryRow}>
            <View style={styles.telemetryCol}>
              <Text style={styles.telemetryLabel}>Battery</Text>
              <Text style={styles.telemetryValue}>{device.batteryLevel}%</Text>
            </View>
            <View style={styles.divider} />
            <View style={styles.telemetryCol}>
              <Text style={styles.telemetryLabel}>ANC Mode</Text>
              <Text style={styles.telemetryValue}>{device.ancMode}</Text>
            </View>
            <View style={styles.divider} />
            <View style={styles.telemetryCol}>
              <Text style={styles.telemetryLabel}>Spatial Audio</Text>
              <Text style={styles.telemetryValue}>{device.spatialAudioEnabled ? 'Active' : 'Off'}</Text>
            </View>
          </View>
        ) : (
          <Text style={styles.glassesUnpairedDesc}>
            Connect your glasses to activate spatial prism optics, head-tracking audio, and temple gestures.
          </Text>
        )}

        <TouchableOpacity
          style={styles.deviceActionBtn}
          onPress={isGlassesConnected ? onOpenDeviceDetails : onOpenPairing}
          activeOpacity={0.85}
        >
          <Text style={styles.deviceActionBtnText}>
            {isGlassesConnected ? 'Manage Device Settings' : 'Pair CEASER Smart Glasses'}
          </Text>
          <Feather name="chevron-right" size={18} color={colors.textPrimary} />
        </TouchableOpacity>
      </View>

      {/* Quick Access: NIX AI Assistant */}
      <View style={styles.nixCard}>
        <View style={styles.nixHeader}>
          <View style={styles.nixBadgeRow}>
            <View style={styles.nixIcon}>
              <Feather name="zap" size={16} color={colors.violetLight} />
            </View>
            <Text style={styles.nixTitle}>NIX AI Assistant</Text>
          </View>
          <TouchableOpacity onPress={() => onNavigateToNix()} activeOpacity={0.7}>
            <Text style={styles.nixOpenText}>Open Chat</Text>
          </TouchableOpacity>
        </View>

        <Text style={styles.nixSubtitle}>Quick ecosystem prompts</Text>

        <View style={styles.promptsRow}>
          <TouchableOpacity
            style={styles.promptPill}
            onPress={() => onNavigateToNix('Sync active workspace notes to CEASER NIX Desktop')}
          >
            <Text style={styles.promptPillText}>Sync notes to Desktop</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.promptPill}
            onPress={() => onNavigateToNix('Explain spatial audio gesture shortcuts')}
          >
            <Text style={styles.promptPillText}>Glasses Gestures</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Ecosystem Device Status */}
      <View style={styles.ecosystemCard}>
        <Text style={styles.sectionTitle}>Ecosystem Nodes</Text>
        <View style={styles.nodeRow}>
          <Feather name="terminal" size={18} color={colors.cyan} />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.nodeName}>{desktop.name}</Text>
            <Text style={styles.nodeDetails}>
              {desktop.os} • {desktop.activeContext}
            </Text>
          </View>
          <View style={styles.onlineBadge}>
            <Text style={styles.onlineText}>Online</Text>
          </View>
        </View>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.black,
  },
  content: {
    padding: 20,
    gap: 18,
    paddingBottom: 40,
  },
  header: {
    paddingVertical: 4,
  },
  greeting: {
    fontSize: 24,
    fontWeight: '800',
    color: colors.textPrimary,
  },
  subGreeting: {
    fontSize: 13,
    color: colors.textSecondary,
    marginTop: 2,
  },
  desktopCard: {
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: 'rgba(6, 182, 212, 0.4)',
    borderRadius: 18,
    padding: 16,
  },
  desktopCardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 10,
  },
  desktopBadgeRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
  },
  desktopIconWrapper: {
    width: 36,
    height: 36,
    borderRadius: 8,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
  },
  desktopTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: colors.textPrimary,
  },
  desktopSubtitle: {
    fontSize: 11,
    color: colors.cyan,
    fontWeight: '500',
  },
  syncedPill: {
    backgroundColor: colors.successMuted,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 8,
  },
  syncedText: {
    color: colors.success,
    fontSize: 10,
    fontWeight: '700',
  },
  desktopDesc: {
    fontSize: 12.5,
    color: colors.textSecondary,
    lineHeight: 18,
    marginBottom: 14,
  },
  desktopBtnRow: {
    flexDirection: 'row',
    gap: 10,
  },
  desktopSyncBtn: {
    flex: 1,
    height: 38,
    borderRadius: 10,
    backgroundColor: colors.surfaceElevated,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 6,
  },
  desktopSyncBtnText: {
    color: colors.cyan,
    fontSize: 12,
    fontWeight: '700',
  },
  desktopQrBtn: {
    flex: 1,
    height: 38,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 6,
  },
  desktopQrBtnText: {
    color: colors.textPrimary,
    fontSize: 12,
    fontWeight: '600',
  },
  deviceCard: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    padding: 18,
  },
  deviceHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 14,
  },
  deviceCategory: {
    fontSize: 10,
    fontWeight: '700',
    letterSpacing: 1,
    color: colors.violetLight,
    marginBottom: 2,
  },
  deviceName: {
    fontSize: 18,
    fontWeight: '800',
    color: colors.textPrimary,
  },
  devicePill: {
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 12,
    borderWidth: 1,
  },
  devicePillConnected: {
    backgroundColor: colors.surfaceElevated,
    borderColor: colors.violet,
  },
  devicePillMuted: {
    backgroundColor: colors.surfaceElevated,
    borderColor: colors.borderSubtle,
  },
  devicePillText: {
    fontSize: 11,
    fontWeight: '600',
  },
  devicePillTextConnected: {
    color: colors.textPrimary,
  },
  devicePillTextMuted: {
    color: colors.textMuted,
  },
  telemetryRow: {
    flexDirection: 'row',
    backgroundColor: colors.darkSurface,
    borderRadius: 12,
    padding: 12,
    justifyContent: 'space-around',
    marginBottom: 16,
  },
  telemetryCol: {
    alignItems: 'center',
  },
  telemetryLabel: {
    fontSize: 10,
    color: colors.textMuted,
    marginBottom: 2,
  },
  telemetryValue: {
    fontSize: 13,
    fontWeight: '700',
    color: colors.textPrimary,
  },
  divider: {
    width: 1,
    height: 24,
    backgroundColor: colors.borderSubtle,
  },
  glassesUnpairedDesc: {
    fontSize: 12.5,
    color: colors.textSecondary,
    lineHeight: 18,
    marginBottom: 16,
  },
  deviceActionBtn: {
    backgroundColor: colors.surfaceElevated,
    height: 44,
    borderRadius: 12,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
  },
  deviceActionBtnText: {
    color: colors.textPrimary,
    fontWeight: '700',
    fontSize: 13,
  },
  nixCard: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    padding: 16,
  },
  nixHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 6,
  },
  nixBadgeRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  nixIcon: {
    width: 28,
    height: 28,
    borderRadius: 8,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
  },
  nixTitle: {
    color: colors.textPrimary,
    fontWeight: '700',
    fontSize: 14,
  },
  nixOpenText: {
    color: colors.violetLight,
    fontWeight: '600',
    fontSize: 12,
  },
  nixSubtitle: {
    color: colors.textMuted,
    fontSize: 11,
    marginBottom: 12,
  },
  promptsRow: {
    flexDirection: 'row',
    gap: 8,
    flexWrap: 'wrap',
  },
  promptPill: {
    backgroundColor: colors.darkSurface,
    paddingHorizontal: 12,
    paddingVertical: 7,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
  },
  promptPillText: {
    color: colors.textSecondary,
    fontSize: 11.5,
  },
  ecosystemCard: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    padding: 16,
  },
  sectionTitle: {
    fontSize: 12,
    fontWeight: '700',
    color: colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 1,
    marginBottom: 12,
  },
  nodeRow: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.darkSurface,
    padding: 12,
    borderRadius: 12,
  },
  nodeName: {
    color: colors.textPrimary,
    fontWeight: '700',
    fontSize: 13,
  },
  nodeDetails: {
    color: colors.textMuted,
    fontSize: 11,
    marginTop: 2,
  },
  onlineBadge: {
    backgroundColor: colors.successMuted,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 6,
  },
  onlineText: {
    color: colors.success,
    fontSize: 10,
    fontWeight: '700',
  },
});
