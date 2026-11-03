import React, { useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ActivityIndicator } from 'react-native';
import { colors } from '../theme/colors';

interface SplashScreenProps {
  onFinish: () => void;
}

export const SplashScreen: React.FC<SplashScreenProps> = ({ onFinish }) => {
  useEffect(() => {
    const timer = setTimeout(() => {
      onFinish();
    }, 2200);
    return () => clearTimeout(timer);
  }, [onFinish]);

  return (
    <TouchableOpacity style={styles.container} activeOpacity={1} onPress={onFinish}>
      <View style={styles.centerContent}>
        {/* Animated concentric emblem */}
        <View style={styles.pulseOuter}>
          <View style={styles.pulseInner}>
            <Text style={styles.emblemText}>C</Text>
          </View>
        </View>

        <Text style={styles.brandTitle}>CEASER</Text>
        <Text style={styles.brandSubtitle}>WEARABLE & WORKSTATION ECOSYSTEM</Text>

        <View style={styles.statusRow}>
          <ActivityIndicator size="small" color={colors.cyan} />
          <Text style={styles.statusText}>Initializing Neural Transport Mesh...</Text>
        </View>
      </View>

      <Text style={styles.bottomHint}>Tap anywhere to continue</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.black,
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 50,
  },
  centerContent: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  pulseOuter: {
    width: 120,
    height: 120,
    borderRadius: 60,
    backgroundColor: 'rgba(124, 58, 237, 0.15)',
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: 'rgba(124, 58, 237, 0.3)',
    marginBottom: 28,
  },
  pulseInner: {
    width: 76,
    height: 76,
    borderRadius: 24,
    backgroundColor: colors.surfaceCard,
    borderWidth: 2,
    borderColor: colors.violet,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: colors.violet,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.5,
    shadowRadius: 10,
    elevation: 8,
  },
  emblemText: {
    fontSize: 40,
    fontWeight: '900',
    color: colors.textPrimary,
  },
  brandTitle: {
    fontSize: 28,
    fontWeight: '900',
    color: colors.textPrimary,
    letterSpacing: 6,
    marginBottom: 6,
  },
  brandSubtitle: {
    fontSize: 10,
    fontWeight: '700',
    color: colors.violetLight,
    letterSpacing: 2,
    marginBottom: 36,
  },
  statusRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
  },
  statusText: {
    fontSize: 12,
    color: colors.textMuted,
  },
  bottomHint: {
    fontSize: 11,
    color: 'rgba(148, 163, 184, 0.5)',
  },
});
