import React, { useState, useEffect, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Dimensions,
  Animated,
  Platform,
  Modal,
  TextInput,
  ActivityIndicator,
  KeyboardAvoidingView,
  ScrollView,
  Easing,
} from 'react-native';
import { Feather, FontAwesome5, Ionicons } from '@expo/vector-icons';
import { colors } from '../theme/colors';

interface AuthScreenProps {
  onSuccess: (email: string, name: string) => void;
  onOpenQrScanner: () => void;
  onGuestMode: () => void;
}

/**
 * 3D Asset Placeholders for CEASER Ecosystem:
 * 1. Neural Orb
 * 2. Smart Glasses
 * 3. Desktop Workstation
 * 4. Mobile Node
 * 
 * You can replace any of these 4 files in `react-native-expo/assets/models/`
 * with your own 3D renders, GLTF models, or custom assets.
 */
const ecosystem3DPlaceholders = [
  require('../../assets/models/placeholder_orb.png'),
  require('../../assets/models/placeholder_glasses.png'),
  require('../../assets/models/placeholder_desktop.png'),
  require('../../assets/models/placeholder_mobile.png'),
];

export const AuthScreen: React.FC<AuthScreenProps> = ({
  onSuccess,
  onOpenQrScanner,
  onGuestMode,
}) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  // Smooth continuous levitation
  const floatAnim = useRef(new Animated.Value(0)).current;

  // Morph transition values
  const fadeAnim = useRef(new Animated.Value(1)).current;
  const scaleAnim = useRef(new Animated.Value(1)).current;
  const rotateAnim = useRef(new Animated.Value(0)).current;

  // Email Login Modal Sheet State
  const [emailModalVisible, setEmailModalVisible] = useState(false);
  const [isRegister, setIsRegister] = useState(false);
  const [email, setEmail] = useState('alex.mercer@ceaser.ai');
  const [password, setPassword] = useState('••••••••••••');
  const [showPassword, setShowPassword] = useState(false);
  const [name, setName] = useState('Alex Mercer');
  const [isLoading, setIsLoading] = useState(false);

  // Continuous subtle 3D breathing float
  useEffect(() => {
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(floatAnim, {
          toValue: -6,
          duration: 2200,
          easing: Easing.inOut(Easing.sin),
          useNativeDriver: true,
        }),
        Animated.timing(floatAnim, {
          toValue: 6,
          duration: 2200,
          easing: Easing.inOut(Easing.sin),
          useNativeDriver: true,
        }),
      ])
    );
    loop.start();
    return () => loop.stop();
  }, [floatAnim]);

  // Smooth 3D morph transition
  const transitionTo = (nextIdx: number) => {
    Animated.parallel([
      Animated.timing(fadeAnim, {
        toValue: 0,
        duration: 260,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
      Animated.timing(scaleAnim, {
        toValue: 0.9,
        duration: 260,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
      Animated.timing(rotateAnim, {
        toValue: 1,
        duration: 260,
        easing: Easing.out(Easing.quad),
        useNativeDriver: true,
      }),
    ]).start(() => {
      setCurrentIndex(nextIdx);
      rotateAnim.setValue(-1);
      Animated.parallel([
        Animated.timing(fadeAnim, {
          toValue: 1,
          duration: 340,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
        Animated.timing(scaleAnim, {
          toValue: 1,
          duration: 340,
          easing: Easing.out(Easing.back(1.1)),
          useNativeDriver: true,
        }),
        Animated.timing(rotateAnim, {
          toValue: 0,
          duration: 340,
          easing: Easing.out(Easing.quad),
          useNativeDriver: true,
        }),
      ]).start();
    });
  };

  // Auto-cycle through the 3D model placeholders: Orb -> Glasses -> Desktop -> Mobile
  useEffect(() => {
    const timer = setInterval(() => {
      transitionTo((currentIndex + 1) % ecosystem3DPlaceholders.length);
    }, 3800);
    return () => clearInterval(timer);
  }, [currentIndex]);

  const rotateYInterpolate = rotateAnim.interpolate({
    inputRange: [-1, 0, 1],
    outputRange: ['-30deg', '0deg', '30deg'],
  });

  const handleEmailSubmit = () => {
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      setEmailModalVisible(false);
      onSuccess(email, name);
    }, 400);
  };

  const handleSocialLogin = (provider: 'Google' | 'Apple') => {
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      onSuccess(
        provider === 'Google' ? 'alex.mercer@gmail.com' : 'alex.mercer@icloud.com',
        'Alex Mercer'
      );
    }, 350);
  };

  return (
    <View style={styles.container}>
      {/* Top Bar with Minimal Indicator matching reference */}
      <View style={styles.topBar}>
        <View style={{ width: 32 }} />

        {/* Minimal 3-line violet indicator menu from reference */}
        <TouchableOpacity
          style={styles.menuBtn}
          onPress={onOpenQrScanner}
          activeOpacity={0.7}
        >
          <View style={styles.menuLine} />
          <View style={[styles.menuLine, { width: 15 }]} />
          <View style={[styles.menuLine, { width: 9 }]} />
        </TouchableOpacity>
      </View>

      {/* Main 3D Floating Stage with Placeholders */}
      <View style={styles.centerStage}>
        {/* Soft Ambient Radial Violet Glow */}
        <View style={styles.ambientGlow} />

        {/* 3D Model Placeholder Container */}
        <TouchableOpacity
          activeOpacity={0.95}
          onPress={() => transitionTo((currentIndex + 1) % ecosystem3DPlaceholders.length)}
          style={styles.modelTouchable}
        >
          <Animated.View
            style={[
              styles.modelWrapper,
              {
                opacity: fadeAnim,
                transform: [
                  { translateY: floatAnim },
                  { scale: scaleAnim },
                  { perspective: 800 },
                  { rotateY: rotateYInterpolate },
                ],
              },
            ]}
          >
            <Image
              source={ecosystem3DPlaceholders[currentIndex]}
              style={styles.modelImage}
              resizeMode="contain"
            />
          </Animated.View>
        </TouchableOpacity>

        {/* Clean, Subtle Pagination Dots (NO NAMES / NO PILLS) */}
        <View style={styles.dotsRow}>
          {ecosystem3DPlaceholders.map((_, idx) => {
            const isActive = idx === currentIndex;
            return (
              <TouchableOpacity
                key={idx}
                onPress={() => transitionTo(idx)}
                activeOpacity={0.6}
                hitSlop={{ top: 8, bottom: 8, left: 6, right: 6 }}
              >
                <View style={[styles.dot, isActive ? styles.dotActive : styles.dotInactive]} />
              </TouchableOpacity>
            );
          })}
        </View>

        {/* Steady High-Fashion Editorial Typography (Matching Reference) */}
        <View style={styles.typographyContainer}>
          <Text style={styles.headline}>HEAR YOUR IDEAS{'\n'}COME TO LIFE</Text>
          <Text style={styles.subheadline}>
            Transform your voice into dynamic spatial intelligence and bring your imagination to life
          </Text>
        </View>
      </View>

      {/* Bottom Auth Buttons (Directly from reference image) */}
      <View style={styles.bottomSection}>
        {/* Primary Purple Pill: Log in with Email */}
        <TouchableOpacity
          style={styles.emailPillBtn}
          onPress={() => setEmailModalVisible(true)}
          activeOpacity={0.88}
        >
          <Text style={styles.emailPillText}>Log in with Email</Text>
        </TouchableOpacity>

        {/* Subtle Divider: or continue with */}
        <View style={styles.dividerRow}>
          <View style={styles.dividerLine} />
          <Text style={styles.dividerText}>or continue with</Text>
          <View style={styles.dividerLine} />
        </View>

        {/* Dark Pill: Continue with Google */}
        <TouchableOpacity
          style={styles.socialPillBtn}
          onPress={() => handleSocialLogin('Google')}
          activeOpacity={0.8}
        >
          <FontAwesome5 name="google" size={15} color="#FFFFFF" style={styles.socialIcon} />
          <Text style={styles.socialPillText}>Continue with Google</Text>
        </TouchableOpacity>

        {/* Dark Pill: Continue with Apple */}
        <TouchableOpacity
          style={styles.socialPillBtn}
          onPress={() => handleSocialLogin('Apple')}
          activeOpacity={0.8}
        >
          <Ionicons name="logo-apple" size={18} color="#FFFFFF" style={styles.socialIcon} />
          <Text style={styles.socialPillText}>Continue with Apple</Text>
        </TouchableOpacity>

        {/* Auxiliary Links */}
        <View style={styles.auxRow}>
          <TouchableOpacity onPress={onOpenQrScanner} activeOpacity={0.7}>
            <Text style={styles.auxText}>Scan Desktop QR</Text>
          </TouchableOpacity>
          <View style={styles.auxDot} />
          <TouchableOpacity onPress={onGuestMode} activeOpacity={0.7}>
            <Text style={styles.auxText}>Guest Mode</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Email / Password Sign In Bottom Sheet Modal */}
      <Modal
        visible={emailModalVisible}
        animationType="slide"
        transparent
        onRequestClose={() => setEmailModalVisible(false)}
      >
        <KeyboardAvoidingView
          style={styles.modalOverlay}
          behavior={Platform.OS === 'ios' ? 'padding' : undefined}
        >
          <View style={styles.modalSheet}>
            <View style={styles.sheetGrabber} />

            <View style={styles.modalHeader}>
              <View>
                <Text style={styles.modalTitle}>CEASER ID</Text>
                <Text style={styles.modalSubtitle}>Sign in to your ecosystem</Text>
              </View>
              <TouchableOpacity
                style={styles.modalCloseBtn}
                onPress={() => setEmailModalVisible(false)}
              >
                <Feather name="x" size={18} color={colors.textSecondary} />
              </TouchableOpacity>
            </View>

            <ScrollView contentContainerStyle={styles.modalBody} showsVerticalScrollIndicator={false}>
              {/* Mode Toggle */}
              <View style={styles.modeToggleRow}>
                <TouchableOpacity
                  style={[styles.modeToggleBtn, !isRegister && styles.modeToggleBtnActive]}
                  onPress={() => setIsRegister(false)}
                >
                  <Text style={[styles.modeToggleText, !isRegister && styles.modeToggleTextActive]}>
                    Sign In
                  </Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={[styles.modeToggleBtn, isRegister && styles.modeToggleBtnActive]}
                  onPress={() => setIsRegister(true)}
                >
                  <Text style={[styles.modeToggleText, isRegister && styles.modeToggleTextActive]}>
                    Create Account
                  </Text>
                </TouchableOpacity>
              </View>

              {isRegister && (
                <View style={styles.inputGroup}>
                  <Text style={styles.inputLabel}>Name</Text>
                  <View style={styles.inputWrapper}>
                    <Feather name="user" size={16} color={colors.textMuted} style={{ marginRight: 8 }} />
                    <TextInput
                      style={styles.textInput}
                      value={name}
                      onChangeText={setName}
                      placeholder="Your full name"
                      placeholderTextColor={colors.textMuted}
                    />
                  </View>
                </View>
              )}

              <View style={styles.inputGroup}>
                <Text style={styles.inputLabel}>Email Address</Text>
                <View style={styles.inputWrapper}>
                  <Feather name="mail" size={16} color={colors.textMuted} style={{ marginRight: 8 }} />
                  <TextInput
                    style={styles.textInput}
                    value={email}
                    onChangeText={setEmail}
                    autoCapitalize="none"
                    keyboardType="email-address"
                    placeholder="name@ceaser.ai"
                    placeholderTextColor={colors.textMuted}
                  />
                </View>
              </View>

              <View style={styles.inputGroup}>
                <Text style={styles.inputLabel}>Password</Text>
                <View style={styles.inputWrapper}>
                  <Feather name="lock" size={16} color={colors.textMuted} style={{ marginRight: 8 }} />
                  <TextInput
                    style={[styles.textInput, { flex: 1 }]}
                    value={password}
                    onChangeText={setPassword}
                    secureTextEntry={!showPassword}
                    placeholder="••••••••••••"
                    placeholderTextColor={colors.textMuted}
                  />
                  <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
                    <Feather
                      name={showPassword ? 'eye' : 'eye-off'}
                      size={16}
                      color={colors.textMuted}
                    />
                  </TouchableOpacity>
                </View>
              </View>

              <TouchableOpacity
                style={styles.modalSubmitBtn}
                onPress={handleEmailSubmit}
                disabled={isLoading}
                activeOpacity={0.85}
              >
                {isLoading ? (
                  <ActivityIndicator color="#FFFFFF" size="small" />
                ) : (
                  <Text style={styles.modalSubmitText}>
                    {isRegister ? 'Create CEASER Account' : 'Sign In to Ecosystem'}
                  </Text>
                )}
              </TouchableOpacity>
            </ScrollView>
          </View>
        </KeyboardAvoidingView>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000000',
    paddingHorizontal: 24,
    paddingTop: Platform.OS === 'ios' ? 52 : 36,
    paddingBottom: 28,
    justifyContent: 'space-between',
  },
  topBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: 36,
  },
  menuBtn: {
    padding: 6,
    alignItems: 'flex-end',
    gap: 3.5,
  },
  menuLine: {
    height: 2,
    width: 20,
    backgroundColor: '#9333EA',
    borderRadius: 1,
  },
  centerStage: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 4,
  },
  ambientGlow: {
    position: 'absolute',
    top: 15,
    width: 220,
    height: 220,
    borderRadius: 110,
    backgroundColor: 'rgba(147, 51, 234, 0.18)',
  },
  modelTouchable: {
    width: 210,
    height: 210,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 16,
  },
  modelWrapper: {
    width: 210,
    height: 210,
    justifyContent: 'center',
    alignItems: 'center',
  },
  modelImage: {
    width: 210,
    height: 210,
  },
  dotsRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 7,
    marginBottom: 24,
  },
  dot: {
    height: 4,
    borderRadius: 2,
  },
  dotActive: {
    width: 16,
    backgroundColor: '#A855F7',
  },
  dotInactive: {
    width: 4,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
  },
  typographyContainer: {
    alignItems: 'center',
    paddingHorizontal: 12,
  },
  headline: {
    color: '#D8B4FE',
    fontSize: 23,
    fontWeight: '800',
    textAlign: 'center',
    letterSpacing: 2,
    lineHeight: 30,
    fontFamily: Platform.OS === 'ios' ? 'Georgia' : 'serif',
    marginBottom: 10,
  },
  subheadline: {
    color: '#818CF8',
    fontSize: 12.5,
    textAlign: 'center',
    lineHeight: 18,
    maxWidth: 300,
    opacity: 0.88,
  },
  bottomSection: {
    width: '100%',
    gap: 11,
  },
  emailPillBtn: {
    width: '100%',
    height: 52,
    borderRadius: 26,
    backgroundColor: '#8B5CF6',
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#8B5CF6',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.35,
    shadowRadius: 10,
    elevation: 6,
  },
  emailPillText: {
    color: '#FFFFFF',
    fontSize: 15,
    fontWeight: '700',
    letterSpacing: 0.2,
  },
  dividerRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 10,
    marginVertical: 3,
  },
  dividerLine: {
    flex: 1,
    height: 1,
    backgroundColor: 'rgba(255, 255, 255, 0.08)',
  },
  dividerText: {
    color: '#64748B',
    fontSize: 11,
    fontWeight: '500',
  },
  socialPillBtn: {
    width: '100%',
    height: 48,
    borderRadius: 24,
    backgroundColor: '#12141F',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.07)',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 10,
  },
  socialIcon: {
    marginRight: 2,
  },
  socialPillText: {
    color: '#F1F5F9',
    fontSize: 14,
    fontWeight: '600',
  },
  auxRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 12,
    marginTop: 4,
  },
  auxText: {
    color: '#94A3B8',
    fontSize: 12,
    fontWeight: '500',
  },
  auxDot: {
    width: 3,
    height: 3,
    borderRadius: 1.5,
    backgroundColor: '#475569',
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.8)',
    justifyContent: 'flex-end',
  },
  modalSheet: {
    backgroundColor: '#0D0F18',
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.1)',
    paddingBottom: 36,
    maxHeight: '85%',
  },
  sheetGrabber: {
    width: 38,
    height: 4,
    borderRadius: 2,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    alignSelf: 'center',
    marginTop: 12,
    marginBottom: 8,
  },
  modalHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 22,
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: 'rgba(255, 255, 255, 0.06)',
  },
  modalTitle: {
    color: '#F8F9FD',
    fontSize: 17,
    fontWeight: '700',
  },
  modalSubtitle: {
    color: '#94A3B8',
    fontSize: 12,
    marginTop: 2,
  },
  modalCloseBtn: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: '#1E2333',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalBody: {
    padding: 22,
    gap: 14,
  },
  modeToggleRow: {
    flexDirection: 'row',
    backgroundColor: '#141724',
    borderRadius: 12,
    padding: 3,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    marginBottom: 6,
  },
  modeToggleBtn: {
    flex: 1,
    paddingVertical: 8,
    alignItems: 'center',
    borderRadius: 9,
  },
  modeToggleBtnActive: {
    backgroundColor: '#262D42',
    borderWidth: 1,
    borderColor: 'rgba(168, 85, 247, 0.4)',
  },
  modeToggleText: {
    fontSize: 12.5,
    fontWeight: '500',
    color: '#64748B',
  },
  modeToggleTextActive: {
    color: '#F8F9FD',
    fontWeight: '600',
  },
  inputGroup: {
    gap: 6,
  },
  inputLabel: {
    fontSize: 11.5,
    fontWeight: '600',
    color: '#94A3B8',
  },
  inputWrapper: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#090B12',
    borderRadius: 12,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    paddingHorizontal: 12,
    height: 46,
  },
  textInput: {
    flex: 1,
    height: '100%',
    color: '#F8F9FD',
    fontSize: 13.5,
  },
  modalSubmitBtn: {
    width: '100%',
    height: 48,
    borderRadius: 24,
    backgroundColor: '#8B5CF6',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 10,
  },
  modalSubmitText: {
    color: '#FFFFFF',
    fontSize: 14.5,
    fontWeight: '600',
  },
});
