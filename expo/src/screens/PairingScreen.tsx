import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  SafeAreaView,
  ActivityIndicator,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

interface PairingScreenProps {
  onClose: () => void;
  onPaired: () => void;
}

export const PairingScreen: React.FC<PairingScreenProps> = ({ onClose, onPaired }) => {
  const [step, setStep] = useState<'INTRO' | 'SCANNING' | 'DISCOVERED' | 'PAIRING' | 'SUCCESS'>('INTRO');

  useEffect(() => {
    let timer: NodeJS.Timeout;
    if (step === 'SCANNING') {
      timer = setTimeout(() => {
        setStep('DISCOVERED');
      }, 2000);
    } else if (step === 'PAIRING') {
      timer = setTimeout(() => {
        setStep('SUCCESS');
      }, 2200);
    }
    return () => clearTimeout(timer);
  }, [step]);

  return (
    <SafeAreaView style={styles.container}>
      {/* Top Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.closeBtn} onPress={onClose}>
          <Ionicons name="close" size={20} color={Colors.textSecondary} />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>PAIR CEASER DEVICE</Text>
        <View style={{ width: 36 }} />
      </View>

      <View style={styles.content}>
        {step === 'INTRO' && (
          <View style={styles.stepBox}>
            <View style={styles.iconCircle}>
              <Ionicons name="glasses-outline" size={64} color={Colors.violetLight} />
            </View>
            <Text style={styles.title}>Prepare CEASER Glasses</Text>
            <Text style={styles.desc}>
              Press and hold the temple button on the left frame for 3 seconds until the violet LED indicator begins pulsing rhythmically.
            </Text>
            <TouchableOpacity style={styles.btn} onPress={() => setStep('SCANNING')}>
              <Text style={styles.btnText}>LED is Pulsing — Scan Now</Text>
            </TouchableOpacity>
          </View>
        )}

        {step === 'SCANNING' && (
          <View style={styles.stepBox}>
            <ActivityIndicator size="large" color={Colors.violet} />
            <Text style={styles.title}>Scanning for Hardware...</Text>
            <Text style={styles.desc}>Keep your CEASER glasses within 1 meter of your phone.</Text>
          </View>
        )}

        {step === 'DISCOVERED' && (
          <View style={styles.stepBox}>
            <Text style={styles.title}>Discovered Hardware</Text>
            <Text style={styles.desc}>Select your device model to initiate pairing:</Text>

            <TouchableOpacity style={styles.deviceOption} onPress={() => setStep('PAIRING')}>
              <Ionicons name="bluetooth" size={24} color={Colors.violetLight} />
              <View style={{ flex: 1, marginLeft: 12 }}>
                <Text style={styles.deviceName}>CEASER NIX Frame-04</Text>
                <Text style={styles.deviceMeta}>RSSI -42 dBm • Official Hardware</Text>
              </View>
              <Text style={styles.pairLink}>Pair</Text>
            </TouchableOpacity>
          </View>
        )}

        {step === 'PAIRING' && (
          <View style={styles.stepBox}>
            <ActivityIndicator size="large" color={Colors.cyan} />
            <Text style={styles.title}>Pairing in Progress...</Text>
            <Text style={styles.desc}>Exchanging encryption keys and calibrating temple sensors.</Text>
          </View>
        )}

        {step === 'SUCCESS' && (
          <View style={styles.stepBox}>
            <View style={[styles.iconCircle, { borderColor: Colors.success }]}>
              <Ionicons name="checkmark" size={48} color={Colors.success} />
            </View>
            <Text style={styles.title}>Successfully Connected!</Text>
            <Text style={styles.desc}>
              CEASER NIX Frame-04 is calibrated and ready. Spatial head tracking and NIX assistant are live.
            </Text>
            <TouchableOpacity
              style={styles.btn}
              onPress={() => {
                onPaired();
                onClose();
              }}
            >
              <Text style={styles.btnText}>Open Dashboard</Text>
            </TouchableOpacity>
          </View>
        )}
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.black,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  closeBtn: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerTitle: {
    color: Colors.textMuted,
    fontSize: 12,
    fontWeight: 'bold',
    letterSpacing: 1.5,
  },
  content: {
    flex: 1,
    paddingHorizontal: 24,
    justifyContent: 'center',
    alignItems: 'center',
  },
  stepBox: {
    alignItems: 'center',
    width: '100%',
    gap: 16,
  },
  iconCircle: {
    width: 120,
    height: 120,
    borderRadius: 60,
    backgroundColor: Colors.surfaceCard,
    borderWidth: 1.5,
    borderColor: Colors.borderSubtle,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    color: Colors.textPrimary,
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  desc: {
    color: Colors.textSecondary,
    fontSize: 13,
    textAlign: 'center',
    lineHeight: 18,
    paddingHorizontal: 16,
  },
  btn: {
    width: '100%',
    height: 48,
    backgroundColor: Colors.violet,
    borderRadius: 14,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 10,
  },
  btnText: {
    color: Colors.textPrimary,
    fontWeight: 'bold',
    fontSize: 14,
  },
  deviceOption: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surfaceCard,
    borderRadius: 16,
    padding: 16,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    width: '100%',
    marginTop: 12,
  },
  deviceName: {
    color: Colors.textPrimary,
    fontSize: 15,
    fontWeight: 'bold',
  },
  deviceMeta: {
    color: Colors.cyan,
    fontSize: 11,
    marginTop: 2,
  },
  pairLink: {
    color: Colors.violetLight,
    fontWeight: 'bold',
    fontSize: 13,
  },
});
