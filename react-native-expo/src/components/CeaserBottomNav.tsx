import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { AppTab } from '../types';

interface BottomNavProps {
  selectedTab: AppTab;
  onTabSelect: (tab: AppTab) => void;
}

export const CeaserBottomNav: React.FC<BottomNavProps> = ({ selectedTab, onTabSelect }) => {
  const tabs: { key: AppTab; label: string; icon: keyof typeof Feather.glyphMap }[] = [
    { key: 'HOME', label: 'Home', icon: 'grid' },
    { key: 'DEVICES', label: 'Devices', icon: 'cpu' },
    { key: 'NIX', label: 'NIX AI', icon: 'zap' },
    { key: 'PROFILE', label: 'Profile', icon: 'user' },
  ];

  return (
    <View style={styles.container}>
      {tabs.map((tab) => {
        const isSelected = selectedTab === tab.key;
        const isNix = tab.key === 'NIX';

        return (
          <TouchableOpacity
            key={tab.key}
            style={[styles.tabButton, isSelected && styles.tabButtonActive]}
            onPress={() => onTabSelect(tab.key)}
            activeOpacity={0.7}
          >
            <View style={[styles.iconWrapper, isNix && styles.nixIconWrapper]}>
              <Feather
                name={tab.icon}
                size={20}
                color={isSelected ? (isNix ? colors.cyan : colors.violetLight) : colors.textMuted}
              />
            </View>
            <Text
              style={[
                styles.tabLabel,
                { color: isSelected ? colors.textPrimary : colors.textMuted, fontWeight: isSelected ? '700' : '500' },
              ]}
            >
              {tab.label}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    backgroundColor: colors.darkSurface,
    borderTopWidth: 1,
    borderTopColor: colors.borderSubtle,
    paddingVertical: 10,
    paddingBottom: 22,
    justifyContent: 'space-around',
    alignItems: 'center',
  },
  tabButton: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 4,
    paddingHorizontal: 14,
    borderRadius: 16,
  },
  tabButtonActive: {
    backgroundColor: 'rgba(255, 255, 255, 0.03)',
  },
  iconWrapper: {
    marginBottom: 4,
  },
  nixIconWrapper: {
    padding: 2,
  },
  tabLabel: {
    fontSize: 11,
  },
});
