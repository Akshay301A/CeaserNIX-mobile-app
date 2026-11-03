package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

enum class NixViewMode {
    CHAT,
    HUD_PROJECTION
}

@Composable
fun NixScreen(
    device: CeaserDevice,
    messages: List<ChatMessage>,
    chatInput: String,
    isGenerating: Boolean,
    isVoiceListening: Boolean,
    onInputChange: (String) -> Unit,
    onSendMessage: (String?) -> Unit,
    onToggleVoice: () -> Unit,
    onClearChat: () -> Unit,
    onActionChipClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var viewMode by remember { mutableStateOf(NixViewMode.CHAT) }

    // Pulsing aura for active NIX core
    val infiniteTransition = rememberInfiniteTransition(label = "nix_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    LaunchedEffect(messages.size, isGenerating) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CeaserBlack)
            .statusBarsPadding()
    ) {
        // -------------------------------------------------------------
        // Futuristic AI Header with Glowing Orb & View Mode Toggle
        // -------------------------------------------------------------
        Surface(
            color = CeaserDarkSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pulsing NIX Neural Core Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(38.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            CeaserViolet.copy(alpha = pulseAlpha),
                                            Color.Transparent
                                        )
                                    ),
                                    radius = size.minDimension / 1.5f
                                )
                                drawCircle(
                                    color = CeaserViolet,
                                    radius = size.minDimension / 2.8f,
                                    style = Stroke(width = 2.dp.toPx())
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "NIX Core",
                                tint = CeaserVioletLight,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "NIX AI CORE",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp,
                                    color = CeaserTextPrimary
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = CeaserVioletDark.copy(alpha = 0.5f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserViolet.copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = "NEURAL LINK",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CeaserVioletLight,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(if (device.status == ConnectionStatus.CONNECTED) CeaserCyan else CeaserTextMuted)
                                )
                                Text(
                                    text = if (device.status == ConnectionStatus.CONNECTED)
                                        "Frame-04 Live • Temple Mic • 14ms"
                                    else
                                        "Offline Standby Mode",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (device.status == ConnectionStatus.CONNECTED) CeaserCyan else CeaserTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Header Action Buttons (HUD Toggle & Clear)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Switch between Chat and HUD Projection view
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (viewMode == NixViewMode.HUD_PROJECTION) CeaserVioletDark else CeaserSurfaceElevated,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (viewMode == NixViewMode.HUD_PROJECTION) CeaserVioletLight else CeaserBorderSubtle
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    viewMode = if (viewMode == NixViewMode.CHAT) NixViewMode.HUD_PROJECTION else NixViewMode.CHAT
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (viewMode == NixViewMode.HUD_PROJECTION) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "HUD Mirror",
                                    tint = if (viewMode == NixViewMode.HUD_PROJECTION) CeaserVioletLight else CeaserTextSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (viewMode == NixViewMode.HUD_PROJECTION) "HUD Mode" else "Glasses HUD",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (viewMode == NixViewMode.HUD_PROJECTION) CeaserVioletLight else CeaserTextSecondary
                                )
                            }
                        }

                        IconButton(
                            onClick = onClearChat,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Clear Chat",
                                tint = CeaserTextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // Active Voice Listening Floating Visualizer
        AnimatedVisibility(visible = isVoiceListening) {
            SleekVoiceWaveformBanner(onCancel = onToggleVoice)
        }

        // -------------------------------------------------------------
        // Main Content Area: Chat Stream OR HUD Projection Lens Mirror
        // -------------------------------------------------------------
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (viewMode == NixViewMode.HUD_PROJECTION) {
                // HUD Lens Mirror View
                HudProjectionMirrorView(
                    device = device,
                    lastMessage = messages.lastOrNull { it.sender == Sender.NIX_ASSISTANT },
                    onExitHud = { viewMode = NixViewMode.CHAT }
                )
            } else {
                // Streamlined Chat Message List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        RedesignedChatMessageItem(
                            message = msg,
                            onCopyCode = { code ->
                                clipboardManager.setText(AnnotatedString(code))
                            },
                            onSuggestedActionClick = onActionChipClick
                        )
                    }

                    if (isGenerating) {
                        item {
                            SynthesizingIndicator()
                        }
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // Quick Action Chips Carousel
        // -------------------------------------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SleekPromptPill(
                icon = Icons.Default.BatteryFull,
                label = "Battery & Telemetry",
                onClick = { onSendMessage("Check glasses battery & audio latency") }
            )
            SleekPromptPill(
                icon = Icons.Default.Computer,
                label = "Sync NIX Desktop",
                onClick = { onSendMessage("Sync active workspace notes to CEASER NIX Desktop") }
            )
            SleekPromptPill(
                icon = Icons.Default.TouchApp,
                label = "Temple Gestures",
                onClick = { onSendMessage("Explain spatial audio gesture shortcuts") }
            )
            SleekPromptPill(
                icon = Icons.Default.SurroundSound,
                label = "Acoustic ANC",
                onClick = { onSendMessage("Switch glasses to Adaptive ANC mode") }
            )
            SleekPromptPill(
                icon = Icons.Default.Code,
                label = "BLE Peripheral API",
                onClick = { onSendMessage("Show CEASER peripheral BLE connection script") }
            )
        }

        // -------------------------------------------------------------
        // Bottom Input Capsule (Docked flush directly above bottom nav - No gap!)
        // -------------------------------------------------------------
        Surface(
            color = CeaserDarkSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
            modifier = Modifier
                .fillMaxWidth()
                // Removed padding(bottom = 56.dp) and navigationBars insets to eliminate the gap!
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CeaserSurfaceCard)
                    .border(1.dp, if (chatInput.isNotBlank()) CeaserViolet.copy(alpha = 0.6f) else CeaserBorderSubtle, RoundedCornerShape(24.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Voice / Mic Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isVoiceListening) CeaserViolet else CeaserSurfaceElevated)
                        .clickable { onToggleVoice() }
                        .testTag("nix_mic_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isVoiceListening) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = "Temple Mic Input",
                        tint = if (isVoiceListening) CeaserBlack else CeaserVioletLight,
                        modifier = Modifier.size(19.dp)
                    )
                }

                // Clean Text Input Field
                TextField(
                    value = chatInput,
                    onValueChange = onInputChange,
                    placeholder = {
                        Text(
                            text = "Ask NIX or speak to glasses...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CeaserTextMuted,
                            fontSize = 13.5.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = CeaserTextPrimary,
                        unfocusedTextColor = CeaserTextPrimary,
                        cursorColor = CeaserVioletLight
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 40.dp)
                        .testTag("nix_input_field"),
                    singleLine = true
                )

                // Send Button with active electric violet glow
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (chatInput.isNotBlank() && !isGenerating) CeaserViolet else CeaserSurfaceElevated
                        )
                        .clickable(enabled = chatInput.isNotBlank() && !isGenerating) {
                            onSendMessage(null)
                        }
                        .testTag("nix_send_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Send",
                        tint = if (chatInput.isNotBlank() && !isGenerating) CeaserTextPrimary else CeaserTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Redesigned Chat Message Item
// -------------------------------------------------------------
@Composable
private fun RedesignedChatMessageItem(
    message: ChatMessage,
    onCopyCode: (String) -> Unit,
    onSuggestedActionClick: (String) -> Unit
) {
    val isUser = message.sender == Sender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(if (isUser) 0.88f else 0.96f)
        ) {
            // NIX Avatar on Assistant message
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(CeaserSurfaceElevated)
                        .border(1.dp, CeaserVioletLight.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = CeaserVioletLight,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isUser) 18.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 18.dp
                        )
                    )
                    .background(
                        if (isUser) CeaserVioletDark.copy(alpha = 0.8f) else CeaserSurfaceCard
                    )
                    .border(
                        1.dp,
                        if (isUser) CeaserViolet.copy(alpha = 0.5f) else CeaserBorderSubtle,
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isUser) 18.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 18.dp
                        )
                    )
                    .padding(14.dp)
            ) {
                // Hardware routing badge tag
                message.hardwareContextNote?.let { note ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CeaserDarkSurface,
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, CeaserCyan.copy(alpha = 0.4f)),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(CeaserCyan)
                            )
                            Text(
                                text = note,
                                style = MaterialTheme.typography.labelSmall,
                                color = CeaserCyan,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Message Text
                SelectionContainer {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CeaserTextPrimary,
                        lineHeight = 22.sp
                    )
                }

                // Syntax Highlighted Code Snippet Block
                message.codeSnippet?.let { snippet ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CeaserDarkSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(CeaserViolet))
                                    Text(
                                        text = "CEASER BLE PROTOCOL",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CeaserTextMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                TextButton(
                                    onClick = { onCopyCode(snippet) },
                                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = CeaserCyan,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Copy Code", color = CeaserCyan, fontSize = 11.sp)
                                }
                            }
                            SelectionContainer {
                                Text(
                                    text = snippet,
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CeaserTextSecondary,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Timestamp
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = CeaserTextMuted,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        // Suggested Follow-up Actions
        if (!isUser && message.suggestedActions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                message.suggestedActions.forEach { action ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CeaserSurfaceElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserViolet.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSuggestedActionClick(action) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = action,
                                style = MaterialTheme.typography.labelSmall,
                                color = CeaserVioletLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = CeaserVioletLight,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HUD Glasses Mirror Projection View (Unique Wearable Experience!)
// -------------------------------------------------------------
@Composable
private fun HudProjectionMirrorView(
    device: CeaserDevice,
    lastMessage: ChatMessage?,
    onExitHud: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF030708))
            .border(1.5.dp, Color(0xFF10B981).copy(alpha = 0.6f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // HUD Top Bar (Prism Display Simulation)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF10B981)))
                    Text(
                        text = "HUD PRISM MIRROR [RIGHT TEMPLE]",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )
                }

                Text(
                    text = "BATT: ${device.batteryGlasses}% | ANC: ON",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = Color(0xFF10B981).copy(alpha = 0.8f)
                )
            }

            HorizontalDivider(color = Color(0xFF10B981).copy(alpha = 0.3f), thickness = 1.dp)

            // HUD Center Transcript
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "> NIX REALTIME AUDIO BUFFER:",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = Color(0xFF10B981).copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lastMessage?.text ?: "NIX neural channel active. Ready to display real-time transcriptions and navigation overlay.",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF34D399)
                )
            }

            // HUD Bottom Telemetry
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HEAD TRACKING: 0.0° AZIMUTH • BEAMFORMING ACTIVE",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    color = Color(0xFF10B981).copy(alpha = 0.6f)
                )

                Button(
                    onClick = onExitHud,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B981).copy(alpha = 0.2f),
                        contentColor = Color(0xFF10B981)
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Exit HUD View", fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Voice Waveform Floating Banner
// -------------------------------------------------------------
@Composable
private fun SleekVoiceWaveformBanner(onCancel: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val h1 by infiniteTransition.animateFloat(
        initialValue = 6f,
        targetValue = 26f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 22f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(420, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(tween(300, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "h3"
    )

    Surface(
        color = CeaserSurfaceCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserViolet.copy(alpha = 0.7f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(modifier = Modifier.size(3.5.dp, h1.dp).background(CeaserVioletLight, RoundedCornerShape(2.dp)))
                Box(modifier = Modifier.size(3.5.dp, h2.dp).background(CeaserCyan, RoundedCornerShape(2.dp)))
                Box(modifier = Modifier.size(3.5.dp, h3.dp).background(CeaserViolet, RoundedCornerShape(2.dp)))
                Box(modifier = Modifier.size(3.5.dp, h1.dp).background(CeaserCyan, RoundedCornerShape(2.dp)))

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Listening via Glasses Beamforming Mic...",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = CeaserTextPrimary
                )
            }

            TextButton(
                onClick = onCancel,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Text("Cancel", color = CeaserError, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -------------------------------------------------------------
// Small Prompt Pill
// -------------------------------------------------------------
@Composable
private fun SleekPromptPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = CeaserSurfaceCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CeaserVioletLight,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = CeaserTextSecondary,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun SynthesizingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 38.dp, top = 4.dp)
    ) {
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            color = CeaserViolet,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = "NIX synthesizing response via Frame-04...",
            style = MaterialTheme.typography.bodySmall,
            color = CeaserTextMuted,
            fontSize = 12.sp
        )
    }
}
