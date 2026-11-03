import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { CeaserDevice, ChatMessage } from '../types';

interface NixScreenProps {
  device: CeaserDevice;
}

export const NixScreen: React.FC<NixScreenProps> = ({ device }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: 'm1',
      sender: 'NIX',
      text: 'Welcome Alex. CEASER NIX Frame-04 is connected with 84% battery. Audio channel is live and temple touch gestures are calibrated. How can I assist your workflow today?',
      timestamp: '10:04 AM',
      hardwareNote: 'Connected via CEASER BLE Protocol • Frame-04',
      suggestedActions: [
        'Check battery telemetry',
        'Sync notes to NIX Desktop',
        'Explain temple touch shortcuts',
      ],
    },
  ]);
  const [input, setInput] = useState('');
  const [isVoiceActive, setIsVoiceActive] = useState(false);
  const [viewMode, setViewMode] = useState<'CHAT' | 'HUD'>('CHAT');

  const sendMessage = (textToSend?: string) => {
    const query = textToSend || input.trim();
    if (!query) return;

    const userMsg: ChatMessage = {
      id: Date.now().toString(),
      sender: 'USER',
      text: query,
      timestamp: 'Just now',
    };

    setMessages((prev) => [...prev, userMsg]);
    setInput('');

    // Simulated Smart NIX Assistant response
    setTimeout(() => {
      let replyText = 'I have processed your query within your active CEASER environment. Telemetry and memory graphs are operational.';
      let code: string | undefined;

      if (query.toLowerCase().includes('battery')) {
        replyText = 'Current CEASER Frame-04 telemetry:\n• Left Temple: 84%\n• Right Temple: 86%\n• Smart Charging Case: 95%\n• Estimated continuous audio runtime: 7.2 hours remaining.';
      } else if (query.toLowerCase().includes('desktop') || query.toLowerCase().includes('sync')) {
        replyText = 'CEASER NIX Desktop is synchronized. 14 workspace context memory tokens and your active project scratchpad are mirrored.';
      } else if (query.toLowerCase().includes('code') || query.toLowerCase().includes('api')) {
        replyText = 'Here is the verified BLE peripheral connection handler:';
        code = '// CEASER BLE Connection\nval uuid = UUID.fromString("0000ce10-0000-1000-8000-00805f9b34fb")\ngatt.setCharacteristicNotification(char, true)';
      }

      const nixReply: ChatMessage = {
        id: (Date.now() + 1).toString(),
        sender: 'NIX',
        text: replyText,
        timestamp: 'Just now',
        codeSnippet: code,
        hardwareNote: 'Processed via dual beamforming microphones',
      };
      setMessages((prev) => [...prev, nixReply]);
    }, 700);
  };

  return (
    <SafeAreaView style={styles.container}>
      <KeyboardAvoidingView
        behavior={Platform.OS === 'ios' ? 'padding' : undefined}
        style={styles.keyboardAvoid}
      >
        {/* Top Neural Core Header */}
        <View style={styles.header}>
          <View style={styles.headerLeft}>
            <View style={styles.orbBox}>
              <Ionicons name="sparkles" size={16} color={Colors.violetLight} />
            </View>
            <View style={{ marginLeft: 10 }}>
              <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
                <Text style={styles.headerTitle}>NIX AI CORE</Text>
                <View style={styles.neuralPill}>
                  <Text style={styles.neuralPillText}>NEURAL LINK</Text>
                </View>
              </View>
              <Text style={styles.headerSubtitle}>
                {device.status === 'CONNECTED'
                  ? 'Frame-04 Live • Temple Mic • 14ms'
                  : 'Standby Mode'}
              </Text>
            </View>
          </View>

          <View style={{ flexDirection: 'row', gap: 6 }}>
            <TouchableOpacity
              style={[
                styles.hudToggle,
                viewMode === 'HUD' && { backgroundColor: Colors.violetDark, borderColor: Colors.violet },
              ]}
              onPress={() => setViewMode(viewMode === 'CHAT' ? 'HUD' : 'CHAT')}
            >
              <Ionicons
                name={viewMode === 'HUD' ? 'eye' : 'eye-outline'}
                size={14}
                color={viewMode === 'HUD' ? Colors.violetLight : Colors.textSecondary}
              />
              <Text
                style={[
                  styles.hudToggleText,
                  viewMode === 'HUD' && { color: Colors.violetLight },
                ]}
              >
                {viewMode === 'HUD' ? 'HUD Mode' : 'Glasses HUD'}
              </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.clearBtn}
              onPress={() =>
                setMessages([
                  {
                    id: 'm1',
                    sender: 'NIX',
                    text: 'Session reset. NIX standby for voice or text prompt.',
                    timestamp: 'Just now',
                  },
                ])
              }
            >
              <Ionicons name="trash-outline" size={16} color={Colors.textMuted} />
            </TouchableOpacity>
          </View>
        </View>

        {/* HUD Projection Simulation OR Chat Messages */}
        {viewMode === 'HUD' ? (
          <View style={styles.hudMirrorContainer}>
            <View style={styles.hudHeaderRow}>
              <Text style={styles.hudTerminalTitle}>HUD PRISM MIRROR [RIGHT TEMPLE]</Text>
              <Text style={styles.hudTerminalStatus}>BATT: {device.batteryGlasses}%</Text>
            </View>
            <View style={styles.hudDivider} />
            <Text style={styles.hudPrompt}>&gt; REALTIME AUDIO BUFFER:</Text>
            <Text style={styles.hudBodyText}>
              {messages[messages.length - 1]?.text || 'NIX neural buffer active.'}
            </Text>
            <View style={styles.hudFooter}>
              <Text style={styles.hudFooterText}>HEAD TRACKING: 0.0° AZIMUTH • BEAMFORMING ON</Text>
              <TouchableOpacity onPress={() => setViewMode('CHAT')} style={styles.exitHudBtn}>
                <Text style={styles.exitHudBtnText}>Exit HUD</Text>
              </TouchableOpacity>
            </View>
          </View>
        ) : (
          <ScrollView
            contentContainerStyle={styles.messagesList}
            showsVerticalScrollIndicator={false}
          >
            {messages.map((msg) => {
              const isUser = msg.sender === 'USER';
              return (
                <View
                  key={msg.id}
                  style={[
                    styles.messageWrapper,
                    isUser ? styles.userWrapper : styles.nixWrapper,
                  ]}
                >
                  {!isUser && (
                    <View style={styles.assistantAvatar}>
                      <Ionicons name="sparkles" size={13} color={Colors.violetLight} />
                    </View>
                  )}

                  <View
                    style={[
                      styles.messageBubble,
                      isUser ? styles.userBubble : styles.nixBubble,
                    ]}
                  >
                    {msg.hardwareNote && (
                      <Text style={styles.hardwareNoteText}>• {msg.hardwareNote}</Text>
                    )}

                    <Text style={styles.messageText}>{msg.text}</Text>

                    {msg.codeSnippet && (
                      <View style={styles.codeSnippetBox}>
                        <Text style={styles.codeSnippetTitle}>CODE BLOCK</Text>
                        <Text style={styles.codeSnippetCode}>{msg.codeSnippet}</Text>
                      </View>
                    )}

                    <Text style={styles.timestampText}>{msg.timestamp}</Text>
                  </View>

                  {/* Suggested action pills */}
                  {msg.suggestedActions && (
                    <View style={styles.suggestedActionsRow}>
                      {msg.suggestedActions.map((action, idx) => (
                        <TouchableOpacity
                          key={idx}
                          style={styles.actionPill}
                          onPress={() => sendMessage(action)}
                        >
                          <Text style={styles.actionPillText}>{action}</Text>
                          <Ionicons name="chevron-forward" size={12} color={Colors.violetLight} />
                        </TouchableOpacity>
                      ))}
                    </View>
                  )}
                </View>
              );
            })}
          </ScrollView>
        )}

        {/* Quick Suggestion Pills */}
        <ScrollView
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.pillsScroll}
        >
          <TouchableOpacity
            style={styles.chip}
            onPress={() => sendMessage('Check battery & telemetry')}
          >
            <Ionicons name="battery-charging-outline" size={12} color={Colors.violetLight} />
            <Text style={styles.chipText}>Battery Telemetry</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.chip}
            onPress={() => sendMessage('Sync active notes to CEASER NIX Desktop')}
          >
            <Ionicons name="desktop-outline" size={12} color={Colors.violetLight} />
            <Text style={styles.chipText}>Sync Desktop</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.chip}
            onPress={() => sendMessage('Explain temple gesture shortcuts')}
          >
            <Ionicons name="hand-left-outline" size={12} color={Colors.violetLight} />
            <Text style={styles.chipText}>Temple Gestures</Text>
          </TouchableOpacity>
        </ScrollView>

        {/* Seamless Docked Input Capsule (Directly above tab bar, no gap!) */}
        <View style={styles.inputContainer}>
          <View style={styles.inputCapsule}>
            <TouchableOpacity
              style={[
                styles.micBtn,
                isVoiceActive && { backgroundColor: Colors.violet },
              ]}
              onPress={() => setIsVoiceActive(!isVoiceActive)}
            >
              <Ionicons
                name={isVoiceActive ? 'mic-off' : 'mic'}
                size={18}
                color={isVoiceActive ? Colors.black : Colors.violetLight}
              />
            </TouchableOpacity>

            <TextInput
              value={input}
              onChangeText={setInput}
              placeholder="Ask NIX or speak to glasses..."
              placeholderTextColor={Colors.textMuted}
              style={styles.textInput}
            />

            <TouchableOpacity
              style={[
                styles.sendBtn,
                input.trim() ? { backgroundColor: Colors.violet } : null,
              ]}
              onPress={() => sendMessage()}
              disabled={!input.trim()}
            >
              <Ionicons
                name="arrow-up"
                size={18}
                color={input.trim() ? Colors.textPrimary : Colors.textMuted}
              />
            </TouchableOpacity>
          </View>
        </View>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.black,
  },
  keyboardAvoid: {
    flex: 1,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderColor: Colors.borderSubtle,
    backgroundColor: Colors.darkSurface,
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  orbBox: {
    width: 34,
    height: 34,
    borderRadius: 17,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1.5,
    borderColor: Colors.violet,
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerTitle: {
    color: Colors.textPrimary,
    fontSize: 14,
    fontWeight: 'bold',
    letterSpacing: 0.5,
  },
  neuralPill: {
    backgroundColor: Colors.violetDark,
    paddingHorizontal: 5,
    paddingVertical: 2,
    borderRadius: 6,
  },
  neuralPillText: {
    color: Colors.violetLight,
    fontSize: 8.5,
    fontWeight: 'bold',
  },
  headerSubtitle: {
    color: Colors.cyan,
    fontSize: 11,
  },
  hudToggle: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
    paddingHorizontal: 8,
    paddingVertical: 5,
    borderRadius: 10,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  hudToggleText: {
    color: Colors.textSecondary,
    fontSize: 10,
    fontWeight: '600',
  },
  clearBtn: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messagesList: {
    paddingHorizontal: 16,
    paddingTop: 12,
    paddingBottom: 8,
    gap: 12,
  },
  messageWrapper: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 8,
  },
  userWrapper: {
    justifyContent: 'flex-end',
  },
  nixWrapper: {
    justifyContent: 'flex-start',
  },
  assistantAvatar: {
    width: 28,
    height: 28,
    borderRadius: 14,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.violetLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  messageBubble: {
    maxWidth: '85%',
    borderRadius: 18,
    padding: 12,
  },
  userBubble: {
    backgroundColor: Colors.violetDark,
    borderColor: Colors.violet,
    borderWidth: 1,
  },
  nixBubble: {
    backgroundColor: Colors.surfaceCard,
    borderColor: Colors.borderSubtle,
    borderWidth: 1,
  },
  hardwareNoteText: {
    color: Colors.cyan,
    fontSize: 10,
    fontWeight: '600',
    marginBottom: 4,
  },
  messageText: {
    color: Colors.textPrimary,
    fontSize: 14,
    lineHeight: 20,
  },
  codeSnippetBox: {
    backgroundColor: Colors.darkSurface,
    borderRadius: 8,
    padding: 8,
    marginTop: 8,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
  },
  codeSnippetTitle: {
    color: Colors.textMuted,
    fontSize: 9.5,
    fontWeight: 'bold',
  },
  codeSnippetCode: {
    color: Colors.cyan,
    fontSize: 11,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
    marginTop: 4,
  },
  timestampText: {
    color: Colors.textMuted,
    fontSize: 9.5,
    alignSelf: 'flex-end',
    marginTop: 4,
  },
  suggestedActionsRow: {
    width: '100%',
    paddingLeft: 36,
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 6,
    marginTop: 4,
  },
  actionPill: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surfaceElevated,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    gap: 4,
  },
  actionPillText: {
    color: Colors.violetLight,
    fontSize: 11,
  },
  hudMirrorContainer: {
    flex: 1,
    margin: 16,
    backgroundColor: '#030708',
    borderRadius: 18,
    borderWidth: 1.5,
    borderColor: '#10B981',
    padding: 14,
    justifyContent: 'space-between',
  },
  hudHeaderRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  hudTerminalTitle: {
    color: '#10B981',
    fontSize: 10,
    fontWeight: 'bold',
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  hudTerminalStatus: {
    color: '#10B981',
    fontSize: 10,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  hudDivider: {
    height: 1,
    backgroundColor: 'rgba(16, 185, 129, 0.3)',
    marginVertical: 8,
  },
  hudPrompt: {
    color: '#10B981',
    fontSize: 11,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  hudBodyText: {
    color: '#34D399',
    fontSize: 13,
    lineHeight: 18,
    marginTop: 6,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  hudFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  hudFooterText: {
    color: '#10B981',
    fontSize: 9,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  exitHudBtn: {
    backgroundColor: 'rgba(16, 185, 129, 0.2)',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 6,
  },
  exitHudBtnText: {
    color: '#10B981',
    fontSize: 10,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  pillsScroll: {
    paddingHorizontal: 14,
    paddingVertical: 4,
    gap: 8,
  },
  chip: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surfaceCard,
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    gap: 4,
  },
  chipText: {
    color: Colors.textSecondary,
    fontSize: 11,
  },
  inputContainer: {
    backgroundColor: Colors.darkSurface,
    borderTopWidth: 1,
    borderColor: Colors.borderSubtle,
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  inputCapsule: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.surfaceCard,
    borderRadius: 22,
    borderWidth: 1,
    borderColor: Colors.borderSubtle,
    paddingHorizontal: 6,
    paddingVertical: 4,
  },
  micBtn: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
  textInput: {
    flex: 1,
    color: Colors.textPrimary,
    fontSize: 13.5,
    paddingHorizontal: 10,
    paddingVertical: Platform.OS === 'ios' ? 8 : 4,
  },
  sendBtn: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: Colors.surfaceElevated,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
