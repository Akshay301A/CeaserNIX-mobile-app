import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  ScrollView,
  Switch,
} from 'react-native';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { CeaserDevice } from '../types';

interface GlassesDetailsModalProps {
  visible: boolean;
  device: CeaserDevice;
  onClose: () => void;
  onUpdateDevice: (updated: Partial<CeaserDevice>) => void;
  onToggleConnection: () => void;
}

export const GlassesDetailsModal: React.FC<GlassesDetailsModalProps> = ({
  visible,
  device,
  onClose,
  onUpdateDevice,
  onToggleConnection,
}) => {
  const isConnected = device.status === 'CONNECTED';
  const [brightnessLevel, setBrightnessLevel] = useState<'LOW' | 'MED' | 'HIGH'>('MED');
  const [eqPreset, setEqPreset] = useState<'BALANCED' | 'VOICE' | 'SPATIAL'>('SPATIAL');

  return (
    <Modal visible={visible} animationType="slide" transparent>
      <View style={styles.modalOverlay}>
        <View style={styles.sheetContainer}>
          {/* Grabber Handle */}
          <View style={styles.grabber} />

          {/* Header */}
          <View style={styles.header}>
            <View>
              <Text style={styles.title}>{device.name}</Text>
              <Text style={styles.modelSubtitle}>{device.model}</Text>
            </View>
            <TouchableOpacity style={styles.closeBtn} onPress={onClose} activeOpacity={0.7}>
              <Feather name="x" size={18} color={colors.textSecondary} />
            </TouchableOpacity>
          </View>

          <ScrollView showsVerticalScrollIndicator={false} contentContainerStyle={styles.scrollBody}>
            {/* Battery & Hardware Status */}
            <View style={styles.metricGrid}>
              <View style={styles.metricCard}>
                <View style={styles.metricIconRow}>
                  <Feather
                    name={device.batteryLevel > 20 ? 'battery-charging' : 'battery'}
                    size={18}
                    color={device.batteryLevel > 20 ? colors.success : colors.warning}
                  />
                  <Text style={styles.metricValue}>{isConnected ? `${device.batteryLevel}%` : '--'}</Text>
                </View>
                <Text style={styles.metricLabel}>Battery Charge</Text>
              </View>

              <View style={styles.metricCard}>
                <View style={styles.metricIconRow}>
                  <MaterialCommunityIcons
                    name={device.wearDetected ? 'face-recognition' : 'glasses'}
                    size={18}
                    color={colors.cyan}
                  />
                  <Text style={styles.metricValue}>{device.wearDetected ? 'On Face' : 'Idle'}</Text>
                </View>
                <Text style={styles.metricLabel}>Wear State</Text>
              </View>

              <View style={styles.metricCard}>
                <View style={styles.metricIconRow}>
                  <Feather name="wifi" size={18} color={isConnected ? colors.violetLight : colors.textMuted} />
                  <Text style={[styles.metricValue, { fontSize: 13 }]}>{isConnected ? 'Active' : 'Unpaired'}</Text>
                </View>
                <Text style={styles.metricLabel}>BLE 5.3 Mesh</Text>
              </View>
            </View>

            {/* Optical HUD Controls */}
            <Text style={styles.sectionTitle}>Optical Waveguide HUD</Text>
            <View style={styles.card}>
              <View style={styles.controlRow}>
                <View style={styles.controlInfo}>
                  <Text style={styles.controlTitle}>Micro-OLED Display</Text>
                  <Text style={styles.controlDesc}>Projects spatial intelligence in your peripheral view</Text>
                </View>
                <Switch
                  value={device.opticalDisplayActive}
                  onValueChange={(val) => onUpdateDevice({ opticalDisplayActive: val })}
                  trackColor={{ false: colors.darkSurface, true: colors.violet }}
                  thumbColor={colors.textPrimary}
                />
              </View>

              <View style={styles.divider} />

              <View style={styles.subControlCol}>
                <Text style={styles.subControlLabel}>HUD Brightness Preset</Text>
                <View style={styles.segmentRow}>
                  {(['LOW', 'MED', 'HIGH'] as const).map((lvl) => (
                    <TouchableOpacity
                      key={lvl}
                      style={[styles.segmentBtn, brightnessLevel === lvl && styles.segmentBtnActive]}
                      onPress={() => setBrightnessLevel(lvl)}
                    >
                      <Text style={[styles.segmentText, brightnessLevel === lvl && styles.segmentTextActive]}>
                        {lvl}
                      </Text>
                    </TouchableOpacity>
                  ))}
                </View>
              </View>
            </View>

            {/* Audio & Acoustic Profiles */}
            <Text style={styles.sectionTitle}>Acoustics & ANC</Text>
            <View style={styles.card}>
              <View style={styles.subControlCol}>
                <Text style={styles.subControlLabel}>Noise Control Mode</Text>
                <View style={styles.segmentRow}>
                  {(['ADAPTIVE', 'TRANSPARENCY', 'OFF'] as const).map((mode) => (
                    <TouchableOpacity
                      key={mode}
                      style={[styles.segmentBtn, device.ancMode === mode && styles.segmentBtnActive]}
                      onPress={() => onUpdateDevice({ ancMode: mode })}
                    >
                      <Text style={[styles.segmentText, device.ancMode === mode && styles.segmentTextActive]}>
                        {mode === 'ADAPTIVE' ? 'Adaptive' : mode === 'TRANSPARENCY' ? 'Transparency' : 'Off'}
                      </Text>
                    </TouchableOpacity>
                  ))}
                </View>
              </View>

              <View style={styles.divider} />

              <View style={styles.controlRow}>
                <View style={styles.controlInfo}>
                  <Text style={styles.controlTitle}>Spatial Head Tracking</Text>
                  <Text style={styles.controlDesc}>Lossless 3D bone conduction spatial audio</Text>
                </View>
                <Switch
                  value={device.spatialAudioEnabled}
                  onValueChange={(val) => onUpdateDevice({ spatialAudioEnabled: val })}
                  trackColor={{ false: colors.darkSurface, true: colors.violet }}
                  thumbColor={colors.textPrimary}
                />
              </View>
            </View>

            {/* Temple Touch Gestures */}
            <Text style={styles.sectionTitle}>Temple Gestures</Text>
            <View style={styles.card}>
              <View style={styles.gestureRow}>
                <View style={styles.gestureIcon}>
                  <Feather name="touch-pad" size={16} color={colors.textSecondary} />
                </View>
                <Text style={styles.gestureAction}>Single Tap Right</Text>
                <Text style={styles.gestureTarget}>Play / Pause Media</Text>
              </View>
              <View style={styles.gestureRow}>
                <View style={styles.gestureIcon}>
                  <Feather name="zap" size={16} color={colors.violetLight} />
                </View>
                <Text style={styles.gestureAction}>Double Tap Temple</Text>
                <Text style={styles.gestureTarget}>Trigger NIX Voice</Text>
              </View>
              <View style={styles.gestureRow}>
                <View style={styles.gestureIcon}>
                  <Feather name="sliders" size={16} color={colors.cyan} />
                </View>
                <Text style={styles.gestureAction}>Swipe Forward / Back</Text>
                <Text style={styles.gestureTarget}>Volume & Telemetry</Text>
              </View>
            </View>

            {/* Firmware & Connection Action */}
            <View style={styles.footerActions}>
              <View style={styles.firmwareRow}>
                <Feather name="cpu" size={14} color={colors.textMuted} />
                <Text style={styles.firmwareText}>Firmware: {device.firmwareVersion} • Up to date</Text>
              </View>

              <TouchableOpacity
                style={[styles.disconnectBtn, !isConnected && styles.connectBtn]}
                onPress={onToggleConnection}
                activeOpacity={0.8}
              >
                <Feather
                  name={isConnected ? 'power' : 'plus-circle'}
                  size={16}
                  color={isConnected ? colors.error : colors.textPrimary}
                />
                <Text style={[styles.disconnectText, !isConnected && styles.connectText]}>
                  {isConnected ? 'Disconnect Glasses' : 'Connect Glasses'}
                </Text>
              </TouchableOpacity>
            </View>
          </ScrollView>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.75)',
    justifyContent: 'flex-end',
  },
  sheetContainer: {
    backgroundColor: '#0D111A',
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    maxHeight: '90%',
    paddingBottom: 32,
  },
  grabber: {
    width: 36,
    height: 4,
    borderRadius: 2,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    alignSelf: 'center',
    marginTop: 12,
    marginBottom: 8,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: 'rgba(255, 255, 255, 0.06)',
  },
  title: {
    fontSize: 18,
    fontWeight: '700',
    color: colors.textPrimary,
  },
  modelSubtitle: {
    fontSize: 12,
    color: colors.textMuted,
    marginTop: 2,
  },
  closeBtn: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: colors.surfaceCard,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scrollBody: {
    padding: 20,
    gap: 16,
  },
  metricGrid: {
    flexDirection: 'row',
    gap: 10,
  },
  metricCard: {
    flex: 1,
    backgroundColor: colors.surfaceCard,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.06)',
    padding: 12,
    alignItems: 'center',
    gap: 6,
  },
  metricIconRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  metricValue: {
    color: colors.textPrimary,
    fontSize: 15,
    fontWeight: '700',
  },
  metricLabel: {
    color: colors.textMuted,
    fontSize: 10.5,
  },
  sectionTitle: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    letterSpacing: 1,
    textTransform: 'uppercase',
    marginTop: 4,
  },
  card: {
    backgroundColor: colors.surfaceCard,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.06)',
    padding: 14,
    gap: 12,
  },
  controlRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  controlInfo: {
    flex: 1,
    paddingRight: 10,
  },
  controlTitle: {
    color: colors.textPrimary,
    fontSize: 14,
    fontWeight: '600',
  },
  controlDesc: {
    color: colors.textSecondary,
    fontSize: 11,
    marginTop: 2,
  },
  subControlCol: {
    gap: 8,
  },
  subControlLabel: {
    fontSize: 12,
    color: colors.textSecondary,
    fontWeight: '500',
  },
  segmentRow: {
    flexDirection: 'row',
    backgroundColor: colors.darkSurface,
    borderRadius: 10,
    padding: 3,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.06)',
    gap: 4,
  },
  segmentBtn: {
    flex: 1,
    paddingVertical: 7,
    borderRadius: 8,
    alignItems: 'center',
  },
  segmentBtnActive: {
    backgroundColor: colors.surfaceElevated,
    borderWidth: 1,
    borderColor: 'rgba(124, 58, 237, 0.5)',
  },
  segmentText: {
    fontSize: 11,
    color: colors.textMuted,
    fontWeight: '600',
  },
  segmentTextActive: {
    color: colors.textPrimary,
  },
  divider: {
    height: 1,
    backgroundColor: 'rgba(255, 255, 255, 0.06)',
  },
  gestureRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
    paddingVertical: 4,
  },
  gestureIcon: {
    width: 28,
    height: 28,
    borderRadius: 8,
    backgroundColor: colors.darkSurface,
    justifyContent: 'center',
    alignItems: 'center',
  },
  gestureAction: {
    flex: 1,
    color: colors.textPrimary,
    fontSize: 12.5,
    fontWeight: '500',
  },
  gestureTarget: {
    color: colors.textMuted,
    fontSize: 11,
  },
  footerActions: {
    marginTop: 10,
    gap: 12,
    alignItems: 'center',
  },
  firmwareRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  firmwareText: {
    color: colors.textMuted,
    fontSize: 11,
  },
  disconnectBtn: {
    width: '100%',
    height: 46,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: 'rgba(239, 68, 68, 0.4)',
    backgroundColor: 'rgba(239, 68, 68, 0.08)',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  connectBtn: {
    borderColor: 'rgba(124, 58, 237, 0.5)',
    backgroundColor: 'rgba(124, 58, 237, 0.15)',
  },
  disconnectText: {
    color: colors.error,
    fontSize: 13,
    fontWeight: '600',
  },
  connectText: {
    color: colors.textPrimary,
  },
});
