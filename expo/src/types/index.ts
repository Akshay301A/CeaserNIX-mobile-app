export type ConnectionStatus = 'CONNECTED' | 'CONNECTING' | 'DISCONNECTED' | 'NOT_PAIRED';

export interface CeaserDevice {
  id: string;
  name: string;
  model: string;
  status: ConnectionStatus;
  batteryGlasses: number;
  batteryCase: number;
  firmwareVersion: string;
  spatialAudio: boolean;
  ancMode: 'Adaptive' | 'Transparency' | 'Off';
  micActive: boolean;
  wearDetection: boolean;
  touchControls: boolean;
}

export interface EcosystemDevice {
  id: string;
  name: string;
  type: string;
  status: string;
  detail: string;
}

export interface ChatMessage {
  id: string;
  sender: 'USER' | 'NIX';
  text: string;
  timestamp: string;
  codeSnippet?: string;
  suggestedActions?: string[];
  hardwareNote?: string;
}

export interface UserProfile {
  name: string;
  email: string;
  role: string;
  avatarInitial: string;
}
