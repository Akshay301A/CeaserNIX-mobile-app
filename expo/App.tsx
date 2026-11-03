import React, { useState } from 'react';
import { View, StyleSheet, Modal } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Ionicons } from '@expo/vector-icons';
import { StatusBar } from 'expo-status-bar';

import { Colors } from './src/theme/colors';
import { CeaserDevice, UserProfile } from './src/types';
import { HomeScreen } from './src/screens/HomeScreen';
import { DevicesScreen } from './src/screens/DevicesScreen';
import { NixScreen } from './src/screens/NixScreen';
import { ProfileScreen } from './src/screens/ProfileScreen';
import { PairingScreen } from './src/screens/PairingScreen';

const Tab = createBottomTabNavigator();

export default function App() {
  const [device, setDevice] = useState<CeaserDevice>({
    id: 'frame-04',
    name: 'CEASER NIX Frame-04',
    model: 'CEASER NIX Smart Glasses',
    status: 'CONNECTED',
    batteryGlasses: 84,
    batteryCase: 95,
    firmwareVersion: 'v2.4.1',
    spatialAudio: true,
    ancMode: 'Adaptive',
    micActive: true,
    wearDetection: true,
    touchControls: true,
  });

  const [userProfile] = useState<UserProfile>({
    name: 'Alex Mercer',
    email: 'alex.mercer@ceaser.ai',
    role: 'Ecosystem Pro',
    avatarInitial: 'AM',
  });

  const [pairingModalVisible, setPairingModalVisible] = useState(false);

  return (
    <NavigationContainer>
      <StatusBar style="light" />

      <Tab.Navigator
        screenOptions={({ route }) => ({
          headerShown: false,
          tabBarStyle: {
            backgroundColor: Colors.darkSurface,
            borderTopColor: Colors.borderSubtle,
            borderTopWidth: 1,
            height: 60,
            paddingBottom: 8,
            paddingTop: 8,
          },
          tabBarActiveTintColor: Colors.violetLight,
          tabBarInactiveTintColor: Colors.textMuted,
          tabBarLabelStyle: {
            fontSize: 11,
            fontWeight: '600',
          },
          tabBarIcon: ({ color, size, focused }) => {
            if (route.name === 'Home') {
              return <Ionicons name={focused ? 'home' : 'home-outline'} size={size} color={color} />;
            } else if (route.name === 'Devices') {
              return <Ionicons name={focused ? 'hardware-chip' : 'hardware-chip-outline'} size={size} color={color} />;
            } else if (route.name === 'NIX') {
              return (
                <View style={[styles.nixIconBadge, focused && styles.nixIconBadgeActive]}>
                  <Ionicons name="sparkles" size={16} color={focused ? Colors.black : Colors.violetLight} />
                </View>
              );
            } else if (route.name === 'Profile') {
              return <Ionicons name={focused ? 'person' : 'person-outline'} size={size} color={color} />;
            }
            return null;
          },
        })}
      >
        <Tab.Screen name="Home">
          {(props) => <HomeScreen {...props} device={device} setDevice={setDevice} />}
        </Tab.Screen>

        <Tab.Screen name="Devices">
          {(props) => (
            <DevicesScreen
              {...props}
              device={device}
              setDevice={setDevice}
              onOpenPairing={() => setPairingModalVisible(true)}
            />
          )}
        </Tab.Screen>

        <Tab.Screen name="NIX">
          {(props) => <NixScreen {...props} device={device} />}
        </Tab.Screen>

        <Tab.Screen name="Profile">
          {(props) => <ProfileScreen {...props} userProfile={userProfile} />}
        </Tab.Screen>
      </Tab.Navigator>

      {/* Pairing Modal */}
      <Modal
        visible={pairingModalVisible}
        animationType="slide"
        presentationStyle="fullScreen"
        onRequestClose={() => setPairingModalVisible(false)}
      >
        <PairingScreen
          onClose={() => setPairingModalVisible(false)}
          onPaired={() => {
            setDevice((prev) => ({
              ...prev,
              status: 'CONNECTED',
              batteryGlasses: 92,
              batteryCase: 100,
            }));
          }}
        />
      </Modal>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  nixIconBadge: {
    width: 28,
    height: 28,
    borderRadius: 14,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    alignItems: 'center',
    justifyContent: 'center',
  },
  nixIconBadgeActive: {
    backgroundColor: Colors.violet,
    borderColor: Colors.violetLight,
  },
});
