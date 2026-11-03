export type AppFlowState = 'SPLASH' | 'ONBOARDING' | 'AUTH' | 'MAIN';

export type AppTab = 'HOME' | 'DEVICES' | 'NIX' | 'PROFILE';

export type ConnectionStatus = 'CONNECTED' | 'CONNECTING' | 'DISCONNECTED' | 'NOT_PAIRED';

export interface CeaserDevice {
  id: string;
  name: string;
  model: string;
  status: ConnectionStatus;
  batteryLevel: number;
  firmwareVersion: string;
  ancMode: 'ADAPTIVE' | 'TRANSPARENCY' | 'OFF';
  spatialAudioEnabled: boolean;
  micMuted: boolean;
  opticalDisplayActive: boolean;
  wearDetected: boolean;
}

export interface EcosystemDevice {
  id: string;
  name: string;
  type: 'WORKSTATION' | 'SMART_DISPLAY' | 'EDGE_HUB';
  os: string;
  isOnline: boolean;
  ipAddress: string;
  activeContext: string;
  syncedTokens: number;
}

export interface ChatMessage {
  id: string;
  sender: 'USER' | 'NIX_ASSISTANT' | 'SYSTEM';
  text: string;
  timestamp: string;
  codeSnippet?: string;
}

export interface UserProfile {
  id: string;
  name: string;
  email: string;
  isSignedIn: boolean;
  tier: string;
}
