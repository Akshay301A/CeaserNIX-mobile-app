import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { Feather } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { CeaserDevice, EcosystemDevice } from '../types';

interface DevicesScreenProps {
  device: CeaserDevice;
  desktop: EcosystemDevice;
  onOpenGlassesDetails: () => void;
  onPairNew: () => void;
  onOpenQrScanner: () => void;
}

export const DevicesScreen: React.FC<DevicesScreenProps> = ({
  device,
  desktop,
  onOpenGlassesDetails,
  onPairNew,
  onOpenQrScanner,
}) => {
  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <Text style={styles.title}>Connected Devices</Text>
        <Text style={styles.subtitle}>CEASER Hardware Mesh & Ecosystem Nodes</Text>
      </View>

      {/* Flagship Wearable Card */}
      <Text style={styles.sectionHeader}>Wearables</Text>
      <TouchableOpacity
        style={styles.deviceRow}
        onPress={onOpenGlassesDetails}
        activeOpacity={0.8}
      >
        <View style={styles.deviceIconBox}>
          <Feather name="eye" size={20} color={colors.violetLight} />
        </View>
        <View style={styles.deviceInfo}>
          <Text style={styles.deviceName}>{device.name}</Text>
          <Text style={styles.deviceMeta}>
            {device.model} • {device.status === 'CONNECTED' ? `${device.batteryLevel}% Battery` : 'Not connected'}
          </Text>
        </View>
        <View style={[styles.statusBadge, device.status === 'CONNECTED' ? styles.statusBadgeConnected : styles.statusBadgeMuted]}>
          <Text style={[styles.statusBadgeText, device.status === 'CONNECTED' ? styles.statusTextConnected : styles.statusTextMuted]}>
            {device.status === 'CONNECTED' ? 'Active' : 'Unpaired'}
          </Text>
        </View>
      </TouchableOpacity>

      {/* Ecosystem Workstation Node */}
      <Text style={[styles.sectionHeader, { marginTop: 20 }]}>Workstations & Desktops</Text>
      <View style={styles.deviceRow}>
        <View style={styles.desktopIconBox}>
          <Feather name="monitor" size={20} color={colors.cyan} />
        </View>
        <View style={styles.deviceInfo}>
          <Text style={styles.deviceName}>{desktop.name}</Text>
          <Text style={styles.deviceMeta}>{desktop.os} • {desktop.activeContext}</Text>
        </View>
        <View style={[styles.statusBadge, styles.statusBadgeConnected]}>
          <Text style={[styles.statusBadgeText, styles.statusTextConnected]}>Synced</Text>
        </View>
      </View>

      {/* Action Buttons: Pair New Device or Scan Desktop QR */}
      <View style={styles.actionsContainer}>
        <TouchableOpacity style={styles.pairNewBtn} onPress={onPairNew} activeOpacity={0.85}>
          <Feather name="plus-circle" size={18} color={colors.textPrimary} />
          <Text style={styles.pairNewText}>Pair New Wearable Device</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.linkDesktopBtn} onPress={onOpenQrScanner} activeOpacity={0.85}>
          <Feather name="maximize" size={18} color={colors.cyan} />
          <Text style={styles.linkDesktopText}>Link Workstation via Desktop QR</Text>
        </TouchableOpacity>
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
    paddingBottom: 40,
  },
  header: {
    marginBottom: 20,
  },
  title: {
    fontSize: 22,
    fontWeight: '800',
    color: colors.textPrimary,
  },
  subtitle: {
    fontSize: 12,
    color: colors.textSecondary,
    marginTop: 4,
  },
  sectionHeader: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 1,
    marginBottom: 10,
  },
  deviceRow: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    borderRadius: 16,
    padding: 14,
    gap: 12,
  },
  deviceIconBox: {
    width: 44,
    height: 44,
    borderRadius: 12,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: colors.violet,
  },
  desktopIconBox: {
    width: 44,
    height: 44,
    borderRadius: 12,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: colors.cyan,
  },
  deviceInfo: {
    flex: 1,
  },
  deviceName: {
    color: colors.textPrimary,
    fontWeight: '700',
    fontSize: 14,
  },
  deviceMeta: {
    color: colors.textSecondary,
    fontSize: 11,
    marginTop: 3,
  },
  statusBadge: {
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 8,
  },
  statusBadgeConnected: {
    backgroundColor: colors.successMuted,
  },
  statusBadgeMuted: {
    backgroundColor: colors.surfaceElevated,
  },
  statusBadgeText: {
    fontSize: 10,
    fontWeight: '700',
  },
  statusTextConnected: {
    color: colors.success,
  },
  statusTextMuted: {
    color: colors.textMuted,
  },
  actionsContainer: {
    marginTop: 32,
    gap: 12,
  },
  pairNewBtn: {
    backgroundColor: colors.violet,
    height: 50,
    borderRadius: 14,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  pairNewText: {
    color: colors.textPrimary,
    fontWeight: '700',
    fontSize: 14,
  },
  linkDesktopBtn: {
    borderWidth: 1,
    borderColor: 'rgba(6, 182, 212, 0.4)',
    height: 50,
    borderRadius: 14,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  linkDesktopText: {
    color: colors.cyan,
    fontWeight: '700',
    fontSize: 14,
  },
});
