import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  Switch,
  Alert,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { UserProfile } from '../types';

interface ProfileScreenProps {
  userProfile: UserProfile;
}

export const ProfileScreen: React.FC<ProfileScreenProps> = ({ userProfile }) => {
  const [appMode, setAppMode] = useState<'DEMO' | 'API'>('DEMO');
  const [haptics, setHaptics] = useState(true);
  const [contextGraph, setContextGraph] = useState(true);
  const [biometric, setBiometric] = useState(false);

  const handleSignOut = () => {
    Alert.alert(
      'Sign Out of CEASER',
      'You will be disconnected from cloud memory sync with CEASER NIX Desktop.',
      [
        { text: 'Cancel', style: 'cancel' },
        { text: 'Sign Out', style: 'destructive' },
      ]
    );
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content} showsVerticalScrollIndicator={false}>
        {/* Header */}
        <View style={styles.headerRow}>
          <View>
            <Text style={styles.headerTitle}>Identity & Settings</Text>
            <Text style={styles.headerSubtitle}>CEASER Wearable Mesh • Profile Hub</Text>
          </View>
          <View style={styles.tierBadge}>
            <Text style={styles.tierBadgeText}>PRO TIER</Text>
          </View>
        </View>

        {/* Hero Identity Stage Card */}
        <View style={styles.heroCard}>
          <View style={styles.heroRow}>
            {/* Holographic Avatar */}
            <View style={styles.avatarCircle}>
              <Text style={styles.avatarText}>{userProfile.avatarInitial}</Text>
            </View>

            <View style={{ flex: 1, marginLeft: 14 }}>
              <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
                <Text style={styles.userName}>{userProfile.name}</Text>
                <Ionicons name="checkmark-circle" size={16} color={Colors.cyan} />
              </View>
              <Text style={styles.userEmail}>{userProfile.email}</Text>
              <View style={styles.rolePill}>
                <Text style={styles.rolePillText}>CEASER PIONEER • NIX-9402</Text>
              </View>
            </View>
          </View>

          {/* Metric Counters */}
          <View style={styles.metricRow}>
            <View style={styles.metricItem}>
              <Text style={styles.metricVal}>2 Linked</Text>
              <Text style={styles.metricLbl}>Nodes</Text>
            </View>
            <View style={styles.metricDivider} />
            <View style={styles.metricItem}>
              <Text style={styles.metricVal}>BLE 5.3</Text>
              <Text style={styles.metricLbl}>Protocol</Text>
            </View>
            <View style={styles.metricDivider} />
            <View style={styles.metricItem}>
              <Text style={styles.metricVal}>Online</Text>
              <Text style={styles.metricLbl}>Status</Text>
            </View>
          </View>

          {/* Account Actions */}
          <View style={styles.accountActionRow}>
            <TouchableOpacity style={styles.switchAccountBtn}>
              <Text style={styles.switchAccountBtnText}>Switch Account</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.signOutBtn} onPress={handleSignOut}>
              <Text style={styles.signOutBtnText}>Sign Out</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* Segmented Runtime Mode Selector */}
        <Text style={styles.sectionHeader}>RUNTIME ENVIRONMENT MODE</Text>

        <View style={styles.modeCard}>
          <View style={styles.segmentedCapsule}>
            <TouchableOpacity
              style={[
                styles.segmentItem,
                appMode === 'DEMO' && styles.segmentItemActiveDemo,
              ]}
              onPress={() => setAppMode('DEMO')}
            >
              <View
                style={[
                  styles.segDot,
                  { backgroundColor: appMode === 'DEMO' ? Colors.demoGold : Colors.textMuted },
                ]}
              />
              <Text
                style={[
                  styles.segmentText,
                  appMode === 'DEMO' && { color: Colors.demoGold },
                ]}
              >
                DESIGN & DEMO
              </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={[
                styles.segmentItem,
                appMode === 'API' && styles.segmentItemActiveApi,
              ]}
              onPress={() => setAppMode('API')}
            >
              <View
                style={[
                  styles.segDot,
                  { backgroundColor: appMode === 'API' ? Colors.violetLight : Colors.textMuted },
                ]}
              />
              <Text
                style={[
                  styles.segmentText,
                  appMode === 'API' && { color: Colors.violetLight },
                ]}
              >
                INTEGRATED API
              </Text>
            </TouchableOpacity>
          </View>

          <View style={styles.modeDescBox}>
            <Text style={styles.modeDescTitle}>
              {appMode === 'DEMO' ? 'Offline Interactive Simulation' : 'Connected Production Endpoint'}
            </Text>
            <Text style={styles.modeDescBody}>
              {appMode === 'DEMO'
                ? 'Simulates Frame-04 hardware telemetry, touch sensors, and NIX AI conversational logic.'
                : 'Actively communicating with https://api.ceaser.ecosystem.net over secure authenticated transport.'}
            </Text>
          </View>
        </View>

        {/* Audio & Acoustic Engine Group */}
        <Text style={styles.sectionHeader}>AUDIO & ACOUSTIC ENGINE</Text>

        <View style={styles.groupCard}>
          <TouchableOpacity style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="musical-notes-outline" size={18} color={Colors.cyan} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>Lossless Audio Codec</Text>
              <Text style={styles.settingSubtitle}>aptX Adaptive (96kHz / 24-bit)</Text>
            </View>
            <Ionicons name="chevron-forward" size={16} color={Colors.textMuted} />
          </TouchableOpacity>

          <View style={styles.divider} />

          <View style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="radio-outline" size={18} color={Colors.violetLight} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>Temple Capacitive Haptics</Text>
              <Text style={styles.settingSubtitle}>Crisp low-latency click upon touch</Text>
            </View>
            <Switch
              value={haptics}
              onValueChange={setHaptics}
              trackColor={{ false: Colors.darkSurface, true: Colors.violet }}
              thumbColor={Colors.textPrimary}
            />
          </View>

          <View style={styles.divider} />

          <TouchableOpacity style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="compass-outline" size={18} color={Colors.cyan} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>Spatial 3D Head Tracking</Text>
              <Text style={styles.settingSubtitle}>Acoustic compass calibrated to 0.0°</Text>
            </View>
            <Ionicons name="chevron-forward" size={16} color={Colors.textMuted} />
          </TouchableOpacity>
        </View>

        {/* Ecosystem & Workspace Sync */}
        <Text style={styles.sectionHeader}>ECOSYSTEM & WORKSPACE SYNC</Text>

        <View style={styles.groupCard}>
          <View style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="cloud-sync-outline" size={18} color={Colors.violet} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>Cloud Context Graph Memory</Text>
              <Text style={styles.settingSubtitle}>Sync scratchpad tokens with Desktop</Text>
            </View>
            <Switch
              value={contextGraph}
              onValueChange={setContextGraph}
              trackColor={{ false: Colors.darkSurface, true: Colors.violet }}
              thumbColor={Colors.textPrimary}
            />
          </View>

          <View style={styles.divider} />

          <TouchableOpacity style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="desktop-outline" size={18} color={Colors.cyan} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>CEASER NIX Desktop Link</Text>
              <Text style={styles.settingSubtitle}>Studio Ultra • Linked via Mesh (12ms)</Text>
            </View>
            <Ionicons name="chevron-forward" size={16} color={Colors.textMuted} />
          </TouchableOpacity>
        </View>

        {/* Hardware Diagnostics & About */}
        <Text style={styles.sectionHeader}>HARDWARE DIAGNOSTICS & LEGAL</Text>

        <View style={styles.groupCard}>
          <View style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="shield-checkmark-outline" size={18} color={Colors.success} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>CEASER Firmware Engine</Text>
              <Text style={styles.settingSubtitle}>v2.4.1 (Stable) • Latest patch installed</Text>
            </View>
          </View>

          <View style={styles.divider} />

          <TouchableOpacity style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="book-outline" size={18} color={Colors.cyan} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>Wearable Hardware Guide</Text>
              <Text style={styles.settingSubtitle}>NIX Frame-04 capacitive gestures guide</Text>
            </View>
            <Ionicons name="chevron-forward" size={16} color={Colors.textMuted} />
          </TouchableOpacity>

          <View style={styles.divider} />

          <View style={styles.settingItem}>
            <View style={styles.settingIconBox}>
              <Ionicons name="information-circle-outline" size={18} color={Colors.textSecondary} />
            </View>
            <View style={{ flex: 1, marginLeft: 12 }}>
              <Text style={styles.settingTitle}>About CEASER Ecosystem</Text>
              <Text style={styles.settingSubtitle}>Version 2.1.0 • Build 412 • Production Engine</Text>
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
    marginBottom: 4,
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
  tierBadge: {
    backgroundColor: Colors.surfaceElevated,
    paddingHorizontal: 10,
    paddingVertical: 5,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  tierBadgeText: {
    color: Colors.violetLight,
    fontSize: 10,
    fontWeight: 'bold',
  },
  heroCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 22,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 18,
  },
  heroRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  avatarCircle: {
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 2,
    borderColor: Colors.violet,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: {
    color: Colors.textPrimary,
    fontSize: 20,
    fontWeight: 'bold',
  },
  userName: {
    color: Colors.textPrimary,
    fontSize: 18,
    fontWeight: 'bold',
  },
  userEmail: {
    color: Colors.textSecondary,
    fontSize: 12,
  },
  rolePill: {
    backgroundColor: Colors.violetDark,
    paddingHorizontal: 6,
    paddingVertical: 2,
    borderRadius: 6,
    alignSelf: 'flex-start',
    marginTop: 4,
  },
  rolePillText: {
    color: Colors.violetLight,
    fontSize: 9.5,
    fontWeight: 'bold',
  },
  metricRow: {
    flexDirection: 'row',
    backgroundColor: Colors.darkSurface,
    borderRadius: 12,
    paddingVertical: 10,
    marginTop: 14,
    alignItems: 'center',
    justifyContent: 'space-around',
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  metricItem: {
    alignItems: 'center',
  },
  metricVal: {
    color: Colors.textPrimary,
    fontSize: 12.5,
    fontWeight: 'bold',
  },
  metricLbl: {
    color: Colors.textMuted,
    fontSize: 9.5,
  },
  metricDivider: {
    width: 1,
    height: 20,
    backgroundColor: Colors.borderSubtle,
  },
  accountActionRow: {
    flexDirection: 'row',
    gap: 10,
    marginTop: 14,
  },
  switchAccountBtn: {
    flex: 1,
    height: 38,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    alignItems: 'center',
    justifyContent: 'center',
  },
  switchAccountBtnText: {
    color: Colors.textSecondary,
    fontSize: 12,
  },
  signOutBtn: {
    flex: 1,
    height: 38,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: 'rgba(244, 63, 94, 0.4)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  signOutBtnText: {
    color: Colors.error,
    fontSize: 12,
    fontWeight: 'bold',
  },
  sectionHeader: {
    color: Colors.textMuted,
    fontSize: 11,
    fontWeight: 'bold',
    letterSpacing: 1,
    marginTop: 6,
  },
  modeCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 14,
  },
  segmentedCapsule: {
    flexDirection: 'row',
    backgroundColor: Colors.darkSurface,
    borderRadius: 12,
    padding: 3,
    gap: 4,
  },
  segmentItem: {
    flex: 1,
    paddingVertical: 8,
    borderRadius: 9,
    alignItems: 'center',
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 6,
  },
  segmentItemActiveDemo: {
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.demoGold,
  },
  segmentItemActiveApi: {
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.violet,
  },
  segDot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
  segmentText: {
    color: Colors.textMuted,
    fontSize: 10.5,
    fontWeight: 'bold',
  },
  modeDescBox: {
    backgroundColor: Colors.darkSurface,
    borderRadius: 10,
    padding: 10,
    marginTop: 10,
  },
  modeDescTitle: {
    color: Colors.textPrimary,
    fontSize: 12,
    fontWeight: 'bold',
  },
  modeDescBody: {
    color: Colors.textSecondary,
    fontSize: 11,
    marginTop: 2,
    lineHeight: 16,
  },
  groupCard: {
    backgroundColor: Colors.surfaceCard,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    padding: 14,
  },
  settingItem: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  settingIconBox: {
    width: 36,
    height: 36,
    borderRadius: 10,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  settingTitle: {
    color: Colors.textPrimary,
    fontSize: 13,
    fontWeight: '600',
  },
  settingSubtitle: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  divider: {
    height: 1,
    backgroundColor: Colors.borderSubtle,
    marginVertical: 12,
  },
});
