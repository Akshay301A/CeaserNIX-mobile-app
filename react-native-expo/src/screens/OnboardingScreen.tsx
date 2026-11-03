import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';
import { Feather } from '@expo/vector-icons';
import { colors } from '../theme/colors';

interface OnboardingScreenProps {
  onFinish: () => void;
}

const { width } = Dimensions.get('window');

interface OnboardingSlide {
  kicker: string;
  title: string;
  description: string;
  icon: keyof typeof Feather.glyphMap;
  chips: string[];
}

const slides: OnboardingSlide[] = [
  {
    kicker: 'SPATIAL CONTINUUM',
    title: 'Unified Ecosystem',
    description:
      'Effortlessly mirror your active workspace context, clipboard memory tokens, and AI state between your phone, desktop, and optical wearables.',
    icon: 'layers',
    chips: ['Multi-Node Mesh', 'State Mirroring'],
  },
  {
    kicker: 'FLAGSHIP WEARABLE',
    title: 'CEASER NIX Optical Glasses',
    description:
      'Micro-OLED spatial waveguides and binaural bone-conduction transducers overlay neural intelligence seamlessly in your visual field.',
    icon: 'eye',
    chips: ['Micro-OLED HUD', '3D Bone Conduction'],
  },
  {
    kicker: 'WORKSTATION READY',
    title: 'No Glasses Needed',
    description:
      'Harness the full power of CEASER on your PC. Sign in with your CEASER ID or scan the Desktop QR code to turn any workstation into a synchronized AI node.',
    icon: 'monitor',
    chips: ['Instant QR Link', 'Single Account Bridge'],
  },
];

export const OnboardingScreen: React.FC<OnboardingScreenProps> = ({ onFinish }) => {
  const [index, setIndex] = useState(0);
  const slide = slides[index];

  const handleNext = () => {
    if (index < slides.length - 1) {
      setIndex(index + 1);
    } else {
      onFinish();
    }
  };

  const handleBack = () => {
    if (index > 0) {
      setIndex(index - 1);
    }
  };

  return (
    <View style={styles.container}>
      {/* Minimal Top Bar */}
      <View style={styles.topBar}>
        <View style={styles.stepIndicator}>
          <Text style={styles.stepText}>
            0{index + 1} <Text style={styles.stepTotal}>/ 0{slides.length}</Text>
          </Text>
        </View>

        <TouchableOpacity onPress={onFinish} activeOpacity={0.6} style={styles.skipBtn}>
          <Text style={styles.skipText}>Skip</Text>
        </TouchableOpacity>
      </View>

      {/* Hero Visual Area */}
      <View style={styles.centerSection}>
        {/* Subtle Matte Card */}
        <View style={styles.visualCard}>
          <View style={styles.iconCircle}>
            <Feather name={slide.icon} size={38} color={colors.textPrimary} />
          </View>

          {/* Minimal capability chips */}
          <View style={styles.chipsRow}>
            {slide.chips.map((chip, i) => (
              <View key={i} style={styles.chip}>
                <View style={styles.chipDot} />
                <Text style={styles.chipText}>{chip}</Text>
              </View>
            ))}
          </View>
        </View>

        {/* Typographic Story */}
        <View style={styles.textContainer}>
          <Text style={styles.kicker}>{slide.kicker}</Text>
          <Text style={styles.title}>{slide.title}</Text>
          <Text style={styles.description}>{slide.description}</Text>
        </View>
      </View>

      {/* Bottom Navigation & Action Controls */}
      <View style={styles.bottomSection}>
        {/* Minimal Pagination Dots */}
        <View style={styles.paginationRow}>
          {slides.map((_, i) => (
            <TouchableOpacity
              key={i}
              onPress={() => setIndex(i)}
              activeOpacity={0.7}
              style={[
                styles.dot,
                i === index ? styles.activeDot : styles.inactiveDot,
              ]}
            />
          ))}
        </View>

        {/* Buttons Row */}
        <View style={styles.buttonsRow}>
          {index > 0 ? (
            <TouchableOpacity style={styles.backBtn} onPress={handleBack} activeOpacity={0.7}>
              <Feather name="arrow-left" size={18} color={colors.textSecondary} />
            </TouchableOpacity>
          ) : (
            <View style={{ width: 48 }} />
          )}

          <TouchableOpacity style={styles.primaryBtn} onPress={handleNext} activeOpacity={0.85}>
            <Text style={styles.primaryBtnText}>
              {index < slides.length - 1 ? 'Continue' : 'Get Started'}
            </Text>
            <Feather name="arrow-right" size={16} color="#FFFFFF" />
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#090A0F',
    paddingHorizontal: 24,
    paddingTop: 56,
    paddingBottom: 40,
    justifyContent: 'space-between',
  },
  topBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  stepIndicator: {
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 8,
    backgroundColor: '#121622',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
  },
  stepText: {
    color: colors.textPrimary,
    fontSize: 12,
    fontWeight: '700',
    letterSpacing: 0.5,
  },
  stepTotal: {
    color: colors.textMuted,
    fontWeight: '400',
  },
  skipBtn: {
    paddingVertical: 6,
    paddingHorizontal: 12,
  },
  skipText: {
    color: colors.textSecondary,
    fontSize: 13,
    fontWeight: '500',
  },
  centerSection: {
    alignItems: 'center',
    paddingVertical: 12,
  },
  visualCard: {
    width: width - 48,
    height: 220,
    borderRadius: 24,
    backgroundColor: '#111520',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 32,
    gap: 16,
  },
  iconCircle: {
    width: 84,
    height: 84,
    borderRadius: 42,
    backgroundColor: '#171D2B',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.1)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  chipsRow: {
    flexDirection: 'row',
    gap: 8,
  },
  chip: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.04)',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.06)',
    paddingHorizontal: 10,
    paddingVertical: 5,
    borderRadius: 12,
    gap: 6,
  },
  chipDot: {
    width: 5,
    height: 5,
    borderRadius: 2.5,
    backgroundColor: colors.violetLight,
  },
  chipText: {
    color: colors.textSecondary,
    fontSize: 11,
    fontWeight: '500',
  },
  textContainer: {
    alignItems: 'center',
    paddingHorizontal: 10,
  },
  kicker: {
    fontSize: 11,
    fontWeight: '700',
    letterSpacing: 1.5,
    color: colors.violetLight,
    marginBottom: 8,
    textTransform: 'uppercase',
  },
  title: {
    fontSize: 24,
    fontWeight: '700',
    color: colors.textPrimary,
    textAlign: 'center',
    marginBottom: 12,
    letterSpacing: -0.3,
  },
  description: {
    fontSize: 14,
    color: colors.textSecondary,
    textAlign: 'center',
    lineHeight: 22,
    maxWidth: 320,
  },
  bottomSection: {
    gap: 24,
  },
  paginationRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  dot: {
    height: 5,
    borderRadius: 2.5,
  },
  activeDot: {
    width: 24,
    backgroundColor: colors.textPrimary,
  },
  inactiveDot: {
    width: 6,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
  },
  buttonsRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  backBtn: {
    width: 48,
    height: 48,
    borderRadius: 14,
    backgroundColor: '#121622',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  primaryBtn: {
    flex: 1,
    height: 50,
    borderRadius: 14,
    backgroundColor: colors.violet,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  primaryBtnText: {
    color: '#FFFFFF',
    fontSize: 14.5,
    fontWeight: '600',
    letterSpacing: 0.2,
  },
});
