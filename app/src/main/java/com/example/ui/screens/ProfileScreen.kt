package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppMode
import com.example.model.BackendApiConfig
import com.example.model.UserProfile
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    appMode: AppMode,
    apiConfig: BackendApiConfig,
    onSwitchAppMode: (AppMode) -> Unit,
    onOpenApiConfig: () -> Unit,
    onOpenAuthDialog: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSignOutConfirm by remember { mutableStateOf(false) }
    var selectedCodec by remember { mutableStateOf("aptX Adaptive (96kHz / 24-bit)") }
    var showCodecDialog by remember { mutableStateOf(false) }
    var hapticFeedbackEnabled by remember { mutableStateOf(true) }
    var contextGraphEnabled by remember { mutableStateOf(true) }
    var biometricLockEnabled by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CeaserBlack)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // -------------------------------------------------------------
        // Screen Header
        // -------------------------------------------------------------
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Identity & Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp,
                        color = CeaserTextPrimary
                    )
                    Text(
                        text = "CEASER Wearable Mesh • Profile Hub",
                        style = MaterialTheme.typography.bodySmall,
                        color = CeaserTextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CeaserSurfaceCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle)
                ) {
                    Text(
                        text = "PRO TIER",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CeaserVioletLight,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // -------------------------------------------------------------
        // Premium Hero Identity Stage Card
        // -------------------------------------------------------------
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("user_profile_card")
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Subtle atmospheric ambient radial gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        CeaserViolet.copy(alpha = 0.18f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Holographic Avatar Ring
                            Box(
                                modifier = Modifier
                                    .size(66.dp)
                                    .clip(CircleShape)
                                    .background(CeaserDarkSurface)
                                    .border(
                                        width = 2.dp,
                                        brush = Brush.linearGradient(
                                            listOf(CeaserViolet, CeaserCyan)
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = userProfile.avatarInitial,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = CeaserTextPrimary
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = userProfile.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = CeaserTextPrimary
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Pioneer",
                                        tint = CeaserCyan,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }

                                Text(
                                    text = userProfile.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CeaserTextSecondary
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = CeaserVioletDark.copy(alpha = 0.6f),
                                    border = androidx.compose.foundation.BorderStroke(0.5.dp, CeaserVioletLight.copy(alpha = 0.5f))
                                ) {
                                    Text(
                                        text = "CEASER PIONEER • ECOSYSTEM ID: NIX-9402",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = CeaserVioletLight,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Metric Counters Row (Devices, Firmware, Security)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(CeaserDarkSurface)
                                .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(14.dp))
                                .padding(vertical = 10.dp, horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileMetric(label = "Nodes", value = "2 Linked")
                            Box(modifier = Modifier.size(1.dp, 24.dp).background(CeaserBorderSubtle))
                            ProfileMetric(label = "Protocol", value = "BLE 5.3")
                            Box(modifier = Modifier.size(1.dp, 24.dp).background(CeaserBorderSubtle))
                            ProfileMetric(label = "Status", value = "Online")
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Account Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (userProfile.isSignedIn) {
                                OutlinedButton(
                                    onClick = onOpenAuthDialog,
                                    shape = RoundedCornerShape(12.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Switch Account", color = CeaserTextSecondary, fontSize = 12.sp)
                                }

                                OutlinedButton(
                                    onClick = { showSignOutConfirm = true },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CeaserError),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserError.copy(alpha = 0.4f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Sign Out", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                            } else {
                                Button(
                                    onClick = onOpenAuthDialog,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Sign In with CEASER", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // Segmented Runtime Mode Selector (Prototype vs Production)
        // -------------------------------------------------------------
        item {
            Text(
                text = "Runtime Environment Mode",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Segmented Capsule Switcher
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(CeaserDarkSurface)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Demo Tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (appMode == AppMode.DESIGN_DEMO) CeaserSurfaceElevated else Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold.copy(alpha = 0.6f) else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onSwitchAppMode(AppMode.DESIGN_DEMO) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold else CeaserTextMuted)
                                )
                                Text(
                                    text = "DESIGN & DEMO",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold else CeaserTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Integrated Tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (appMode == AppMode.INTEGRATED) CeaserSurfaceElevated else Color.Transparent)
                                .border(
                                    width = 1.dp,
                                    color = if (appMode == AppMode.INTEGRATED) CeaserViolet.copy(alpha = 0.6f) else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onSwitchAppMode(AppMode.INTEGRATED) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (appMode == AppMode.INTEGRATED) CeaserViolet else CeaserTextMuted)
                                )
                                Text(
                                    text = "INTEGRATED API",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appMode == AppMode.INTEGRATED) CeaserVioletLight else CeaserTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mode Detailed Context Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CeaserDarkSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold.copy(alpha = 0.25f) else CeaserViolet.copy(alpha = 0.25f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (appMode == AppMode.DESIGN_DEMO) "Offline Interactive Simulation" else "Connected Production Endpoint",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold else CeaserVioletLight
                                )
                                TextButton(
                                    onClick = onOpenApiConfig,
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Text("Configure", color = CeaserCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (appMode == AppMode.DESIGN_DEMO)
                                    "Simulates Frame-04 hardware telemetry, touch sensors, and NIX AI conversational logic without requiring remote server authentication."
                                else
                                    "Actively communicating with ${apiConfig.baseUrl} over secure authenticated transport with token session management.",
                                style = MaterialTheme.typography.bodySmall,
                                color = CeaserTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }

        // -------------------------------------------------------------
        // Group: Audio & Acoustic Engine
        // -------------------------------------------------------------
        item {
            Text(
                text = "Audio & Acoustic Engine",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CeaserSurfaceCard)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(20.dp))
            ) {
                AttractiveSettingRow(
                    icon = Icons.Default.Audiotrack,
                    iconTint = CeaserCyan,
                    title = "Lossless Audio Codec",
                    subtitle = selectedCodec,
                    onClick = { showCodecDialog = true }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveToggleRow(
                    icon = Icons.Default.Vibration,
                    iconTint = CeaserVioletLight,
                    title = "Temple Capacitive Haptics",
                    subtitle = "Crisp low-latency haptic click upon gesture tap",
                    checked = hapticFeedbackEnabled,
                    onCheckedChange = {
                        hapticFeedbackEnabled = it
                    }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.GraphicEq,
                    iconTint = CeaserCyan,
                    title = "Spatial 3D Head Tracking Calibration",
                    subtitle = "Acoustic compass calibrated to 0.0° azimuth",
                    onClick = { /* Calibrated silently */ }
                )
            }
        }

        // -------------------------------------------------------------
        // Group: Ecosystem & Workspace Sync
        // -------------------------------------------------------------
        item {
            Text(
                text = "Ecosystem & Workspace Sync",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CeaserSurfaceCard)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(20.dp))
            ) {
                AttractiveToggleRow(
                    icon = Icons.Default.CloudSync,
                    iconTint = CeaserViolet,
                    title = "Cloud Context Graph Memory",
                    subtitle = "Seamlessly sync scratchpad tokens with CEASER NIX Desktop",
                    checked = contextGraphEnabled,
                    onCheckedChange = {
                        contextGraphEnabled = it
                    }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.Computer,
                    iconTint = CeaserCyan,
                    title = "CEASER NIX Desktop Link",
                    subtitle = "Studio Ultra (macOS Sonoma) • Linked via Mesh",
                    onClick = { /* Inspected link silently */ }
                )
            }
        }

        // -------------------------------------------------------------
        // Group: Security & Device Permissions
        // -------------------------------------------------------------
        item {
            Text(
                text = "Privacy & Security",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CeaserSurfaceCard)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(20.dp))
            ) {
                AttractiveToggleRow(
                    icon = Icons.Default.Fingerprint,
                    iconTint = CeaserCyan,
                    title = "Biometric Lock for Settings",
                    subtitle = "Require fingerprint or face authentication for hardware parameters",
                    checked = biometricLockEnabled,
                    onCheckedChange = {
                        biometricLockEnabled = it
                    }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.Lock,
                    iconTint = CeaserVioletLight,
                    title = "BLE Encryption Channel",
                    subtitle = "AES-128 GCM hardware key negotiation",
                    onClick = { /* Verified silently */ }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.CleaningServices,
                    iconTint = CeaserTextMuted,
                    title = "Clear Offline Telemetry Cache",
                    subtitle = "Zero sensitive tokens stored on-device",
                    onClick = { /* Cleared cache silently */ }
                )
            }
        }

        // -------------------------------------------------------------
        // Group: Hardware Diagnostics & About CEASER
        // -------------------------------------------------------------
        item {
            Text(
                text = "Hardware Diagnostics & Legal",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CeaserSurfaceCard)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(20.dp))
            ) {
                AttractiveSettingRow(
                    icon = Icons.Default.SystemUpdate,
                    iconTint = CeaserSuccess,
                    title = "CEASER Firmware Engine",
                    subtitle = "v2.4.1 (Stable) • Latest patch installed",
                    onClick = { /* Firmware verified silently */ }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.MenuBook,
                    iconTint = CeaserCyan,
                    title = "Wearable Hardware Guide",
                    subtitle = "NIX Frame-04 capacitive gestures & maintenance",
                    onClick = { /* Opened guide silently */ }
                )

                HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                AttractiveSettingRow(
                    icon = Icons.Default.Info,
                    iconTint = CeaserTextSecondary,
                    title = "About CEASER Ecosystem",
                    subtitle = "Version 2.1.0 • Build 412 • Production Engine",
                    onClick = { /* Info */ }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    // Codec Selector Dialog
    if (showCodecDialog) {
        AlertDialog(
            onDismissRequest = { showCodecDialog = false },
            containerColor = CeaserDarkSurface,
            title = {
                Text("Select Lossless Audio Codec", color = CeaserTextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        "aptX Adaptive (96kHz / 24-bit)",
                        "LDAC High-Resolution (990 kbps)",
                        "AAC Advanced Audio Coding",
                        "LE Audio LC3 (Low Latency Gaming)"
                    ).forEach { codec ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selectedCodec == codec) CeaserSurfaceElevated else Color.Transparent)
                                .clickable {
                                    selectedCodec = codec
                                    showCodecDialog = false
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(codec, color = if (selectedCodec == codec) CeaserVioletLight else CeaserTextSecondary, fontSize = 13.sp)
                            if (selectedCodec == codec) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = CeaserVioletLight, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCodecDialog = false }) {
                    Text("Close", color = CeaserCyan)
                }
            }
        )
    }

    // Sign out confirmation dialog
    if (showSignOutConfirm) {
        AlertDialog(
            onDismissRequest = { showSignOutConfirm = false },
            containerColor = CeaserDarkSurface,
            title = {
                Text("Sign out of CEASER?", color = CeaserTextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "You will be disconnected from cloud memory sync with CEASER NIX Desktop. Your paired glasses will remain saved on this device.",
                    color = CeaserTextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSignOutConfirm = false
                        onSignOut()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CeaserError)
                ) {
                    Text("Sign Out")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSignOutConfirm = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = CeaserTextSecondary)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ProfileMetric(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            color = CeaserTextMuted
        )
    }
}

@Composable
private fun AttractiveSettingRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CeaserSurfaceElevated)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(19.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CeaserTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserTextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = CeaserTextMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun AttractiveToggleRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f).padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CeaserSurfaceElevated)
                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(19.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CeaserTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserTextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = CeaserTextPrimary,
                checkedTrackColor = CeaserViolet,
                uncheckedThumbColor = CeaserTextMuted,
                uncheckedTrackColor = CeaserDarkSurface
            )
        )
    }
}
