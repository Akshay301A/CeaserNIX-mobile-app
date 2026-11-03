import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Switch } from 'react-native';
import { Feather } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { UserProfile } from '../types';

interface ProfileScreenProps {
  user: UserProfile;
  onSignOut: () => void;
  onOpenQrScanner: () => void;
}

export const ProfileScreen: React.FC<ProfileScreenProps> = ({
  user,
  onSignOut,
  onOpenQrScanner,
}) => {
  const [desktopAutoSync, setDesktopAutoSync] = useState(true);
  const [spatialAudio, setSpatialAudio] = useState(true);
  const [biometrics, setBiometrics] = useState(false);

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      {/* Header */}
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>Identity & Settings</Text>
          <Text style={styles.subtitle}>CEASER Wearable Mesh • Profile Hub</Text>
        </View>
        <View style={styles.tierBadge}>
          <Text style={styles.tierText}>{user.tier}</Text>
        </View>
      </View>

      {/* Identity Card */}
      <View style={styles.profileCard}>
        <View style={styles.avatarRow}>
          <View style={styles.avatarRing}>
            <Text style={styles.avatarText}>{user.name[0]}</Text>
          </View>
          <View style={{ flex: 1 }}>
            <Text style={styles.userName}>{user.name}</Text>
            <Text style={styles.userEmail}>{user.email}</Text>
          </View>
        </View>

        {/* Metrics Row */}
        <View style={styles.metricsRow}>
          <View style={styles.metricItem}>
            <Text style={styles.metricLabel}>Nodes</Text>
            <Text style={styles.metricVal}>2 Linked</Text>
          </View>
          <View style={styles.metricDivider} />
          <View style={styles.metricItem}>
            <Text style={styles.metricLabel}>Protocol</Text>
            <Text style={styles.metricVal}>BLE 5.3</Text>
          </View>
          <View style={styles.metricDivider} />
          <View style={styles.metricItem}>
            <Text style={styles.metricLabel}>Status</Text>
            <Text style={[styles.metricVal, { color: colors.success }]}>Online</Text>
          </View>
        </View>

        {/* Action Buttons */}
        <View style={styles.accountActionRow}>
          <TouchableOpacity style={styles.qrLinkBtn} onPress={onOpenQrScanner} activeOpacity={0.8}>
            <Feather name="maximize" size={14} color={colors.cyan} />
            <Text style={styles.qrLinkText}>Scan Desktop QR</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.signOutBtn} onPress={onSignOut} activeOpacity={0.8}>
            <Text style={styles.signOutText}>Sign Out</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Settings Section */}
      <Text style={styles.sectionHeader}>Ecosystem Preferences</Text>
      <View style={styles.settingsGroup}>
        <View style={styles.settingRow}>
          <View style={styles.settingInfo}>
            <Text style={styles.settingTitle}>Desktop Auto-Sync</Text>
            <Text style={styles.settingSubtitle}>Mirror clipboard and active context tokens</Text>
          </View>
          <Switch
            value={desktopAutoSync}
            onValueChange={setDesktopAutoSync}
            trackColor={{ false: colors.darkSurface, true: colors.violet }}
            thumbColor={colors.textPrimary}
          />
        </View>

        <View style={styles.settingRow}>
          <View style={styles.settingInfo}>
            <Text style={styles.settingTitle}>Spatial 3D Audio</Text>
            <Text style={styles.settingSubtitle}>Dynamic head-tracking acoustics for glasses</Text>
          </View>
          <Switch
            value={spatialAudio}
            onValueChange={setSpatialAudio}
            trackColor={{ false: colors.darkSurface, true: colors.violet }}
            thumbColor={colors.textPrimary}
          />
        </View>

        <View style={styles.settingRow}>
          <View style={styles.settingInfo}>
            <Text style={styles.settingTitle}>Biometric Security Key</Text>
            <Text style={styles.settingSubtitle}>Require FaceID / Fingerprint on launch</Text>
          </View>
          <Switch
            value={biometrics}
            onValueChange={setBiometrics}
            trackColor={{ false: colors.darkSurface, true: colors.violet }}
            thumbColor={colors.textPrimary}
          />
        </View>
      </View>

      {/* About Box */}
      <View style={styles.aboutCard}>
        <Text style={styles.aboutTitle}>About CEASER Ecosystem</Text>
        <Text style={styles.aboutMeta}>Version 2.1.0 • Universal Client (iOS & Android)</Text>
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
    gap: 16,
    paddingBottom: 40,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 4,
  },
  title: {
    fontSize: 22,
    fontWeight: '800',
    color: colors.textPrimary,
  },
  subtitle: {
    fontSize: 12,
    color: colors.textSecondary,
    marginTop: 2,
  },
  tierBadge: {
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    paddingHorizontal: 10,
    paddingVertical: 5,
    borderRadius: 8,
  },
  tierText: {
    color: colors.violetLight,
    fontWeight: '700',
    fontSize: 10,
  },
  profileCard: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    padding: 18,
    gap: 16,
  },
  avatarRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 14,
  },
  avatarRing: {
    width: 56,
    height: 56,
    borderRadius: 28,
    backgroundColor: colors.surfaceElevated,
    borderWidth: 2,
    borderColor: colors.violet,
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: colors.textPrimary,
    fontWeight: '800',
    fontSize: 22,
  },
  userName: {
    color: colors.textPrimary,
    fontSize: 17,
    fontWeight: '700',
  },
  userEmail: {
    color: colors.textSecondary,
    fontSize: 12,
    marginTop: 2,
  },
  metricsRow: {
    flexDirection: 'row',
    backgroundColor: colors.darkSurface,
    borderRadius: 12,
    padding: 10,
    justifyContent: 'space-around',
    alignItems: 'center',
  },
  metricItem: {
    alignItems: 'center',
  },
  metricLabel: {
    color: colors.textMuted,
    fontSize: 10,
    marginBottom: 2,
  },
  metricVal: {
    color: colors.textPrimary,
    fontSize: 12,
    fontWeight: '700',
  },
  metricDivider: {
    width: 1,
    height: 20,
    backgroundColor: colors.borderSubtle,
  },
  accountActionRow: {
    flexDirection: 'row',
    gap: 10,
  },
  qrLinkBtn: {
    flex: 1,
    height: 40,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: 'rgba(6, 182, 212, 0.4)',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 6,
  },
  qrLinkText: {
    color: colors.cyan,
    fontSize: 12,
    fontWeight: '600',
  },
  signOutBtn: {
    flex: 1,
    height: 40,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: 'rgba(239, 68, 68, 0.4)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  signOutText: {
    color: colors.error,
    fontSize: 12,
    fontWeight: '600',
  },
  sectionHeader: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 1,
    marginTop: 8,
  },
  settingsGroup: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    padding: 6,
  },
  settingRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 12,
    borderBottomWidth: 1,
    borderBottomColor: colors.borderSubtle,
  },
  settingInfo: {
    flex: 1,
    paddingRight: 12,
  },
  settingTitle: {
    color: colors.textPrimary,
    fontSize: 13.5,
    fontWeight: '600',
  },
  settingSubtitle: {
    color: colors.textSecondary,
    fontSize: 11,
    marginTop: 2,
  },
  aboutCard: {
    backgroundColor: colors.darkSurface,
    borderRadius: 14,
    padding: 14,
    alignItems: 'center',
    marginTop: 8,
  },
  aboutTitle: {
    color: colors.textPrimary,
    fontSize: 12,
    fontWeight: '700',
  },
  aboutMeta: {
    color: colors.textMuted,
    fontSize: 10,
    marginTop: 4,
  },
});
