import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  Switch,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { CeaserDevice } from '../types';

interface DevicesScreenProps {
  device: CeaserDevice;
  setDevice: React.Dispatch<React.SetStateAction<CeaserDevice>>;
  onOpenPairing: () => void;
}

export const DevicesScreen: React.FC<DevicesScreenProps> = ({
  device,
  setDevice,
  onOpenPairing,
}) => {
  const isConnected = device.status === 'CONNECTED';

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        {/* Header */}
        <View style={styles.headerRow}>
          <View>
            <Text style={styles.headerTitle}>Devices</Text>
            <Text style={styles.headerSubtitle}>Connected Wearables & Workstations</Text>
          </View>

          <TouchableOpacity style={styles.pairBtn} onPress={onOpenPairing}>
            <Ionicons name="add" size={16} color={Colors.textPrimary} />
            <Text style={styles.pairBtnText}>Pair Device</Text>
          </TouchableOpacity>
        </View>

        {/* Primary Glasses Card */}
        <Text style={styles.sectionHeader}>PRIMARY WEARABLE</Text>

        <View style={styles.deviceCard}>
          <View style={styles.deviceCardRow}>
            <View style={styles.glassesIconBox}>
              <Ionicons name="glasses-outline" size={28} color={Colors.violetLight} />
            </View>

            <View style={{ flex: 1, marginLeft: 12 }}>
              <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
                <Text style={styles.deviceName}>{device.name}</Text>
                <View
                  style={[
                    styles.dot,
                    { backgroundColor: isConnected ? Colors.success : Colors.error },
                  ]}
                />
              </View>
              <Text style={styles.deviceModel}>{device.model}</Text>
              <Text style={styles.deviceMeta}>
                Frame: {device.batteryGlasses}% • Case: {device.batteryCase}% • FW {device.firmwareVersion}
              </Text>
            </View>
          </View>

          {/* Quick Hardware Controls inside Card */}
          <View style={styles.divider} />

          <View style={styles.toggleRow}>
            <View>
              <Text style={styles.toggleTitle}>Spatial 3D Audio</Text>
              <Text style={styles.toggleSubtitle}>Dynamic acoustic head tracking</Text>
            </View>
            <Switch
              value={device.spatialAudio}
              onValueChange={(val) => setDevice((p) => ({ ...p, spatialAudio: val }))}
              trackColor={{ false: Colors.darkSurface, true: Colors.violet }}
              thumbColor={Colors.textPrimary}
            />
          </View>

          <View style={styles.divider} />

          <View style={styles.toggleRow}>
            <View>
              <Text style={styles.toggleTitle}>Smart Wear Detection</Text>
              <Text style={styles.toggleSubtitle}>Auto-pause when glasses are taken off</Text>
            </View>
            <Switch
              value={device.wearDetection}
              onValueChange={(val) => setDevice((p) => ({ ...p, wearDetection: val }))}
              trackColor={{ false: Colors.darkSurface, true: Colors.violet }}
              thumbColor={Colors.textPrimary}
            />
          </View>

          <View style={styles.divider} />

          <View style={styles.toggleRow}>
            <View>
              <Text style={styles.toggleTitle}>Capacitive Temple Gestures</Text>
              <Text style={styles.toggleSubtitle}>Double tap for NIX AI query</Text>
            </View>
            <Switch
              value={device.touchControls}
              onValueChange={(val) => setDevice((p) => ({ ...p, touchControls: val }))}
              trackColor={{ false: Colors.darkSurface, true: Colors.violet }}
              thumbColor={Colors.textPrimary}
            />
          </View>
        </View>

        {/* Ecosystem Nodes */}
        <Text style={[styles.sectionHeader, { marginTop: 10 }]}>PAIRED ECOSYSTEM NODES</Text>

        <View style={styles.nodeCard}>
          <View style={styles.nodeIconBox}>
            <Ionicons name="desktop-outline" size={20} color={Colors.cyan} />
          </View>
          <View style={{ flex: 1, marginLeft: 12 }}>
            <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
              <Text style={styles.nodeTitle}>CEASER NIX Desktop</Text>
              <View style={[styles.dot, { backgroundColor: Colors.success }]} />
            </View>
            <Text style={styles.nodeSubtitle}>Studio Ultra • macOS Sonoma</Text>
            <Text style={styles.nodeSyncText}>Synced 2m ago</Text>
          </View>
          <View style={styles.nodeBadge}>
            <Text style={styles.nodeBadgeText}>Workstation</Text>
          </View>
        </View>

        <View style={styles.nodeCard}>
          <View style={styles.nodeIconBox}>
            <Ionicons name="hardware-chip-outline" size={20} color={Colors.violetLight} />
          </View>
          <View style={{ flex: 1, marginLeft: 12 }}>
            <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
              <Text style={styles.nodeTitle}>NIX Smart Dock</Text>
              <View style={[styles.dot, { backgroundColor: Colors.success }]} />
            </View>
            <Text style={styles.nodeSubtitle}>Wireless Induction Stand • Desk 01</Text>
            <Text style={styles.nodeSyncText}>Active Charging</Text>
          </View>
          <View style={styles.nodeBadge}>
            <Text style={styles.nodeBadgeText}>Dock</Text>
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
  content: {
    paddingHorizontal: 20,
    paddingTop: 16,
    paddingBottom: 40,
    gap: 14,
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  headerTitle: {
    color: Colors.textPrimary,
    fontSize: 22,
    fontWeight: 'bold',
  },
  headerSubtitle: {
    color: Colors.textSecondary,
    fontSize: 12,
  },
  pairBtn: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.violet,
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 12,
    gap: 4,
  },
  pairBtnText: {
    color: Colors.textPrimary,
    fontSize: 12,
    fontWeight: 'bold',
  },
  sectionHeader: {
    color: Colors.textMuted,
    fontSize: 11,
    fontWeight: 'bold',
    letterSpacing: 1,
  },
  deviceCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 16,
  },
  deviceCardRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  glassesIconBox: {
    width: 52,
    height: 52,
    borderRadius: 14,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  deviceName: {
    color: Colors.textPrimary,
    fontSize: 16,
    fontWeight: 'bold',
  },
  deviceModel: {
    color: Colors.textSecondary,
    fontSize: 12,
  },
  deviceMeta: {
    color: Colors.cyan,
    fontSize: 11,
    marginTop: 4,
  },
  dot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
  divider: {
    height: 1,
    backgroundColor: Colors.borderSubtle,
    marginVertical: 12,
  },
  toggleRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  toggleTitle: {
    color: Colors.textPrimary,
    fontSize: 13,
    fontWeight: '600',
  },
  toggleSubtitle: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  nodeCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
  },
  nodeIconBox: {
    width: 40,
    height: 40,
    borderRadius: 10,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  nodeTitle: {
    color: Colors.textPrimary,
    fontSize: 14,
    fontWeight: 'bold',
  },
  nodeSubtitle: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  nodeSyncText: {
    color: Colors.textMuted,
    fontSize: 10,
    marginTop: 2,
  },
  nodeBadge: {
    backgroundColor: Colors.darkSurface,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 8,
  },
  nodeBadgeText: {
    color: Colors.textSecondary,
    fontSize: 10,
  },
});
