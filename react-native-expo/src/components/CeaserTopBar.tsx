import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { ConnectionStatus } from '../types';

interface TopBarProps {
  connectionStatus: ConnectionStatus;
  deviceName: string;
  isDesktopLinked: boolean;
  onDevicePress: () => void;
  onSyncPress: () => void;
}

export const CeaserTopBar: React.FC<TopBarProps> = ({
  connectionStatus,
  deviceName,
  isDesktopLinked,
  onDevicePress,
  onSyncPress,
}) => {
  const isConnected = connectionStatus === 'CONNECTED';

  return (
    <View style={styles.container}>
      <View style={styles.brandRow}>
        <View style={styles.logoBadge}>
          <Text style={styles.logoText}>C</Text>
        </View>
        <View>
          <Text style={styles.brandTitle}>CEASER</Text>
          <Text style={styles.brandSubtitle}>NIX COMPANION</Text>
        </View>
      </View>

      <View style={styles.actionsRow}>
        {/* Device Status Pill */}
        <TouchableOpacity
          style={[styles.statusPill, isConnected ? styles.statusPillConnected : styles.statusPillMuted]}
          onPress={onDevicePress}
          activeOpacity={0.8}
        >
          <View style={[styles.dot, isConnected ? styles.dotConnected : styles.dotMuted]} />
          <Text style={[styles.statusText, isConnected ? styles.statusTextConnected : styles.statusTextMuted]}>
            {isConnected ? deviceName : isDesktopLinked ? 'Desktop Sync' : 'Not Paired'}
          </Text>
        </TouchableOpacity>

        {/* Sync Button */}
        <TouchableOpacity style={styles.iconBtn} onPress={onSyncPress} activeOpacity={0.8}>
          <Feather name="refresh-cw" size={16} color={colors.cyan} />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 18,
    paddingVertical: 12,
    backgroundColor: colors.black,
    borderBottomWidth: 1,
    borderBottomColor: colors.borderSubtle,
  },
  brandRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
  },
  logoBadge: {
    width: 32,
    height: 32,
    borderRadius: 8,
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.violet,
    justifyContent: 'center',
    alignItems: 'center',
  },
  logoText: {
    color: colors.violetLight,
    fontWeight: 'bold',
    fontSize: 16,
  },
  brandTitle: {
    color: colors.textPrimary,
    fontWeight: '800',
    fontSize: 15,
    letterSpacing: 1.5,
  },
  brandSubtitle: {
    color: colors.textMuted,
    fontSize: 9,
    letterSpacing: 1,
    fontWeight: '600',
  },
  actionsRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  statusPill: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 16,
    gap: 6,
    borderWidth: 1,
  },
  statusPillConnected: {
    backgroundColor: colors.surfaceCard,
    borderColor: colors.violet,
  },
  statusPillMuted: {
    backgroundColor: colors.surfaceCard,
    borderColor: colors.borderSubtle,
  },
  dot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
  dotConnected: {
    backgroundColor: colors.success,
  },
  dotMuted: {
    backgroundColor: colors.cyan,
  },
  statusText: {
    fontSize: 11,
    fontWeight: '600',
  },
  statusTextConnected: {
    color: colors.textPrimary,
  },
  statusTextMuted: {
    color: colors.cyan,
  },
  iconBtn: {
    width: 34,
    height: 34,
    borderRadius: 17,
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
