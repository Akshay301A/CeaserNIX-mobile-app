import React, { useState } from 'react';
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

interface QrScannerProps {
  visible: boolean;
  onClose: () => void;
  onSuccess: (nodeId: string) => void;
}

export const DesktopQrScannerModal: React.FC<QrScannerProps> = ({
  visible,
  onClose,
  onSuccess,
}) => {
  const [isScanning, setIsScanning] = useState(false);
  const [scanned, setScanned] = useState(false);

  const handleSimulateScan = () => {
    setIsScanning(true);
    setTimeout(() => {
      setIsScanning(false);
      setScanned(true);
      setTimeout(() => {
        onSuccess('CEASER-NODE-MAC-984');
        setScanned(false);
      }, 500);
    }, 800);
  };

  return (
    <Modal visible={visible} animationType="slide" transparent={false}>
      <View style={styles.container}>
        {/* Header */}
        <View style={styles.header}>
          <TouchableOpacity style={styles.closeBtn} onPress={onClose}>
            <Feather name="x" size={20} color={colors.textPrimary} />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>DESKTOP QR LINK</Text>
          <View style={{ width: 36 }} />
        </View>

        {/* Instructions */}
        <View style={styles.content}>
          <Text style={styles.title}>Link with CEASER NIX Desktop</Text>
          <Text style={styles.description}>
            Point your camera at the QR code displayed on your desktop workstation screen
            (CEASER NIX Desktop &gt; Devices &gt; Link Mobile)
          </Text>

          {/* Viewfinder Bounding Box */}
          <View style={[styles.viewfinder, scanned && styles.viewfinderSuccess]}>
            {scanned ? (
              <View style={styles.successBox}>
                <Feather name="check-circle" size={54} color={colors.success} />
                <Text style={styles.successText}>Desktop Linked!</Text>
              </View>
            ) : (
              <View style={styles.qrTargetBox}>
                <Feather name="maximize" size={80} color="rgba(6, 182, 212, 0.4)" />
                <Text style={styles.nodeCode}>NODE: CEASER-DESKTOP-MAC</Text>
              </View>
            )}
          </View>

          {/* Workstation Target Pill */}
          <View style={styles.targetPill}>
            <Feather name="monitor" size={14} color={colors.cyan} />
            <Text style={styles.targetPillText}>Target Node: Studio Ultra (macOS Sonoma)</Text>
          </View>
        </View>

        {/* Bottom Button */}
        <View style={styles.bottomBar}>
          <TouchableOpacity
            style={styles.scanBtn}
            onPress={handleSimulateScan}
            disabled={isScanning || scanned}
            activeOpacity={0.85}
          >
            {isScanning ? (
              <ActivityIndicator color={colors.black} />
            ) : (
              <Text style={styles.scanBtnText}>
                {scanned ? 'Connected' : 'Simulate Scan & Connect Desktop'}
              </Text>
            )}
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.black,
    justifyContent: 'space-between',
    paddingTop: 54,
    paddingBottom: 36,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
  },
  closeBtn: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: colors.surfaceCard,
    justifyContent: 'center',
    alignItems: 'center',
  },
  headerTitle: {
    color: colors.textMuted,
    fontSize: 12,
    fontWeight: '700',
    letterSpacing: 1.5,
  },
  content: {
    alignItems: 'center',
    paddingHorizontal: 28,
  },
  title: {
    color: colors.textPrimary,
    fontSize: 20,
    fontWeight: '800',
    textAlign: 'center',
    marginBottom: 8,
  },
  description: {
    color: colors.textSecondary,
    fontSize: 13,
    textAlign: 'center',
    lineHeight: 19,
    marginBottom: 32,
  },
  viewfinder: {
    width: 250,
    height: 250,
    borderRadius: 24,
    backgroundColor: colors.darkSurface,
    borderWidth: 1.5,
    borderColor: colors.cyan,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 24,
  },
  viewfinderSuccess: {
    borderColor: colors.success,
  },
  qrTargetBox: {
    alignItems: 'center',
    gap: 12,
  },
  nodeCode: {
    color: colors.cyan,
    fontSize: 11,
    fontWeight: '600',
    letterSpacing: 1,
  },
  successBox: {
    alignItems: 'center',
    gap: 10,
  },
  successText: {
    color: colors.success,
    fontSize: 16,
    fontWeight: '700',
  },
  targetPill: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    borderRadius: 12,
    paddingHorizontal: 14,
    paddingVertical: 8,
    gap: 8,
  },
  targetPillText: {
    color: colors.textSecondary,
    fontSize: 11.5,
  },
  bottomBar: {
    paddingHorizontal: 20,
  },
  scanBtn: {
    backgroundColor: colors.cyan,
    height: 52,
    borderRadius: 16,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scanBtnText: {
    color: colors.black,
    fontSize: 15,
    fontWeight: '700',
  },
});
