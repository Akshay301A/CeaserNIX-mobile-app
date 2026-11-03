import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons';
import { colors } from '../theme/colors';

interface PairingModalProps {
  visible: boolean;
  onClose: () => void;
  onPairSuccess: () => void;
}

export const PairingModal: React.FC<PairingModalProps> = ({
  visible,
  onClose,
  onPairSuccess,
}) => {
  const [step, setStep] = useState<'SCANNING' | 'FOUND' | 'PAIRING' | 'CONNECTED'>('SCANNING');

  useEffect(() => {
    if (visible) {
      setStep('SCANNING');
      const timer1 = setTimeout(() => {
        setStep('FOUND');
      }, 1400);
      return () => clearTimeout(timer1);
    }
  }, [visible]);

  const handleConnect = () => {
    setStep('PAIRING');
    setTimeout(() => {
      setStep('CONNECTED');
      setTimeout(() => {
        onPairSuccess();
        onClose();
      }, 700);
    }, 1200);
  };

  return (
    <Modal visible={visible} animationType="slide" transparent>
      <View style={styles.modalOverlay}>
        <View style={styles.cardContainer}>
          <View style={styles.header}>
            <Text style={styles.headerTitle}>PAIR CEASER WEARABLE</Text>
            <TouchableOpacity style={styles.closeBtn} onPress={onClose}>
              <Feather name="x" size={18} color={colors.textSecondary} />
            </TouchableOpacity>
          </View>

          <View style={styles.body}>
            {step === 'SCANNING' && (
              <View style={styles.stateWrapper}>
                <View style={styles.radarRing}>
                  <ActivityIndicator size="small" color={colors.violetLight} />
                </View>
                <Text style={styles.stateTitle}>Scanning BLE Mesh...</Text>
                <Text style={styles.stateDesc}>Hold your CEASER NIX Frames near this phone</Text>
              </View>
            )}

            {step === 'FOUND' && (
              <View style={styles.stateWrapper}>
                <View style={styles.deviceFoundCard}>
                  <View style={styles.iconBox}>
                    <Feather name="eye" size={22} color={colors.violetLight} />
                  </View>
                  <View style={{ flex: 1 }}>
                    <Text style={styles.deviceName}>CEASER NIX Frames</Text>
                    <Text style={styles.deviceMeta}>Model NIX-04 • BLE-NIX-992</Text>
                    <Text style={styles.signalText}>Signal: -42 dBm (Excellent)</Text>
                  </View>
                  <View style={styles.foundDot} />
                </View>

                <TouchableOpacity style={styles.pairActionBtn} onPress={handleConnect} activeOpacity={0.85}>
                  <Text style={styles.pairActionText}>Connect Device</Text>
                  <Feather name="arrow-right" size={16} color="#FFFFFF" />
                </TouchableOpacity>
              </View>
            )}

            {step === 'PAIRING' && (
              <View style={styles.stateWrapper}>
                <ActivityIndicator size="large" color={colors.violet} style={{ marginVertical: 16 }} />
                <Text style={styles.stateTitle}>Pairing Secure Key...</Text>
                <Text style={styles.stateDesc}>Establishing encrypted Bluetooth 5.3 audio & spatial transport</Text>
              </View>
            )}

            {step === 'CONNECTED' && (
              <View style={styles.stateWrapper}>
                <View style={styles.successIconBox}>
                  <Feather name="check" size={28} color={colors.success} />
                </View>
                <Text style={styles.stateTitle}>Glasses Connected!</Text>
                <Text style={styles.stateDesc}>Spatial waveguide display & audio are online</Text>
              </View>
            )}
          </View>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.75)',
    justifyContent: 'center',
    padding: 24,
  },
  cardContainer: {
    backgroundColor: '#0D111A',
    borderRadius: 20,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    padding: 20,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  headerTitle: {
    fontSize: 11,
    fontWeight: '700',
    color: colors.textMuted,
    letterSpacing: 1.2,
  },
  closeBtn: {
    width: 28,
    height: 28,
    borderRadius: 14,
    backgroundColor: colors.surfaceCard,
    justifyContent: 'center',
    alignItems: 'center',
  },
  body: {
    paddingVertical: 10,
  },
  stateWrapper: {
    alignItems: 'center',
    gap: 12,
  },
  radarRing: {
    width: 68,
    height: 68,
    borderRadius: 34,
    backgroundColor: 'rgba(124, 58, 237, 0.12)',
    borderWidth: 1,
    borderColor: 'rgba(124, 58, 237, 0.3)',
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: 10,
  },
  stateTitle: {
    color: colors.textPrimary,
    fontSize: 16,
    fontWeight: '700',
  },
  stateDesc: {
    color: colors.textSecondary,
    fontSize: 12.5,
    textAlign: 'center',
    lineHeight: 18,
  },
  deviceFoundCard: {
    width: '100%',
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surfaceCard,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: 'rgba(124, 58, 237, 0.4)',
    padding: 14,
    gap: 12,
    marginVertical: 10,
  },
  iconBox: {
    width: 42,
    height: 42,
    borderRadius: 10,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
  },
  deviceName: {
    color: colors.textPrimary,
    fontSize: 14,
    fontWeight: '700',
  },
  deviceMeta: {
    color: colors.textSecondary,
    fontSize: 11,
    marginTop: 2,
  },
  signalText: {
    color: colors.success,
    fontSize: 10,
    fontWeight: '600',
    marginTop: 2,
  },
  foundDot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: colors.success,
  },
  pairActionBtn: {
    width: '100%',
    height: 46,
    borderRadius: 12,
    backgroundColor: colors.violet,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
    marginTop: 6,
  },
  pairActionText: {
    color: '#FFFFFF',
    fontSize: 14,
    fontWeight: '600',
  },
  successIconBox: {
    width: 56,
    height: 56,
    borderRadius: 28,
    backgroundColor: 'rgba(16, 185, 129, 0.15)',
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: 8,
  },
});
