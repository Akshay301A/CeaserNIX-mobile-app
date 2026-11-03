import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import { Feather } from '@expo/vector-icons';
import { colors } from '../theme/colors';
import { ChatMessage } from '../types';

interface NixScreenProps {
  initialPrompt?: string;
  onClearInitialPrompt?: () => void;
}

export const NixScreen: React.FC<NixScreenProps> = ({ initialPrompt, onClearInitialPrompt }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: '1',
      sender: 'NIX_ASSISTANT',
      text: 'Greetings. I am NIX, your ecosystem neural companion. CEASER NIX Desktop workstation memory is synchronized.',
      timestamp: '10:00 AM',
    },
    ...(initialPrompt
      ? [
          {
            id: '2',
            sender: 'USER' as const,
            text: initialPrompt,
            timestamp: '10:01 AM',
          },
          {
            id: '3',
            sender: 'NIX_ASSISTANT' as const,
            text: 'Processing ecosystem command. Context mirrored to connected workstation node.',
            timestamp: '10:01 AM',
          },
        ]
      : []),
  ]);
  const [input, setInput] = useState('');

  const handleSend = () => {
    if (!input.trim()) return;
    const userMsg: ChatMessage = {
      id: Date.now().toString(),
      sender: 'USER',
      text: input,
      timestamp: 'Now',
    };
    const replyMsg: ChatMessage = {
      id: (Date.now() + 1).toString(),
      sender: 'NIX_ASSISTANT',
      text: `Understood: "${input}". Syncing context token with CEASER NIX Desktop and connected peripherals.`,
      timestamp: 'Now',
    };
    setMessages((prev) => [...prev, userMsg, replyMsg]);
    setInput('');
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      {/* Header */}
      <View style={styles.header}>
        <View style={styles.orbBadge}>
          <Feather name="zap" size={16} color={colors.violetLight} />
        </View>
        <View style={{ flex: 1 }}>
          <Text style={styles.title}>NIX Neural Assistant</Text>
          <Text style={styles.subtitle}>Desktop & Wearable AI Agent</Text>
        </View>
        <View style={styles.liveIndicator}>
          <View style={styles.liveDot} />
          <Text style={styles.liveText}>Active</Text>
        </View>
      </View>

      {/* Messages */}
      <ScrollView style={styles.messagesContainer} contentContainerStyle={styles.messagesContent}>
        {messages.map((m) => {
          const isUser = m.sender === 'USER';
          return (
            <View
              key={m.id}
              style={[
                styles.messageRow,
                isUser ? styles.messageRowUser : styles.messageRowAssistant,
              ]}
            >
              {!isUser && (
                <View style={styles.assistantAvatar}>
                  <Text style={styles.avatarLetter}>N</Text>
                </View>
              )}
              <View
                style={[
                  styles.messageBubble,
                  isUser ? styles.messageBubbleUser : styles.messageBubbleAssistant,
                ]}
              >
                <Text
                  style={[
                    styles.messageText,
                    isUser ? styles.messageTextUser : styles.messageTextAssistant,
                  ]}
                >
                  {m.text}
                </Text>
                <Text style={styles.messageTime}>{m.timestamp}</Text>
              </View>
            </View>
          );
        })}
      </ScrollView>

      {/* Docked Flush Input Area (Directly above nav bar) */}
      <View style={styles.inputBar}>
        <View style={styles.inputCapsule}>
          <TextInput
            style={styles.textInput}
            value={input}
            onChangeText={setInput}
            placeholder="Ask NIX or trigger desktop action..."
            placeholderTextColor={colors.textMuted}
          />
          <TouchableOpacity
            style={[styles.sendBtn, input.trim() ? styles.sendBtnActive : styles.sendBtnInactive]}
            onPress={handleSend}
            disabled={!input.trim()}
            activeOpacity={0.8}
          >
            <Feather
              name="arrow-up"
              size={18}
              color={input.trim() ? colors.textPrimary : colors.textMuted}
            />
          </TouchableOpacity>
        </View>
      </View>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.black,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 18,
    paddingVertical: 12,
    backgroundColor: colors.darkSurface,
    borderBottomWidth: 1,
    borderBottomColor: colors.borderSubtle,
    gap: 10,
  },
  orbBadge: {
    width: 34,
    height: 34,
    borderRadius: 17,
    backgroundColor: colors.surfaceElevated,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: colors.violet,
  },
  title: {
    fontSize: 14,
    fontWeight: '700',
    color: colors.textPrimary,
  },
  subtitle: {
    fontSize: 11,
    color: colors.textSecondary,
  },
  liveIndicator: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surfaceElevated,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 8,
    gap: 5,
  },
  liveDot: {
    width: 6,
    height: 6,
    borderRadius: 3,
    backgroundColor: colors.success,
  },
  liveText: {
    fontSize: 10,
    color: colors.success,
    fontWeight: '700',
  },
  messagesContainer: {
    flex: 1,
  },
  messagesContent: {
    padding: 16,
    gap: 14,
  },
  messageRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    gap: 8,
  },
  messageRowUser: {
    justifyContent: 'flex-end',
  },
  messageRowAssistant: {
    justifyContent: 'flex-start',
  },
  assistantAvatar: {
    width: 28,
    height: 28,
    borderRadius: 14,
    backgroundColor: colors.surfaceElevated,
    borderWidth: 1,
    borderColor: colors.violet,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 2,
  },
  avatarLetter: {
    color: colors.violetLight,
    fontWeight: 'bold',
    fontSize: 12,
  },
  messageBubble: {
    maxWidth: '82%',
    paddingHorizontal: 14,
    paddingVertical: 10,
    borderRadius: 16,
  },
  messageBubbleUser: {
    backgroundColor: colors.surfaceElevated,
    borderWidth: 1,
    borderColor: colors.violet,
    borderBottomRightRadius: 4,
  },
  messageBubbleAssistant: {
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    borderBottomLeftRadius: 4,
  },
  messageText: {
    fontSize: 13.5,
    lineHeight: 19,
  },
  messageTextUser: {
    color: colors.textPrimary,
  },
  messageTextAssistant: {
    color: colors.textPrimary,
  },
  messageTime: {
    fontSize: 9.5,
    color: colors.textMuted,
    marginTop: 4,
    alignSelf: 'flex-end',
  },
  inputBar: {
    backgroundColor: colors.darkSurface,
    borderTopWidth: 1,
    borderTopColor: colors.borderSubtle,
    paddingHorizontal: 12,
    paddingVertical: 8,
  },
  inputCapsule: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surfaceCard,
    borderWidth: 1,
    borderColor: colors.borderSubtle,
    borderRadius: 24,
    paddingHorizontal: 8,
    paddingVertical: 4,
  },
  textInput: {
    flex: 1,
    color: colors.textPrimary,
    paddingHorizontal: 10,
    paddingVertical: 6,
    fontSize: 13.5,
  },
  sendBtn: {
    width: 34,
    height: 34,
    borderRadius: 17,
    justifyContent: 'center',
    alignItems: 'center',
  },
  sendBtnActive: {
    backgroundColor: colors.violet,
  },
  sendBtnInactive: {
    backgroundColor: colors.surfaceElevated,
  },
});
