package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AppTab

@Composable
fun HomeScreen(
    device: CeaserDevice,
    desktopDevice: EcosystemDevice?,
    activityLogs: List<ActivityLog>,
    userProfile: UserProfile,
    onPrimaryDeviceAction: () -> Unit,
    onManageDeviceClick: () -> Unit,
    onToggleConnection: () -> Unit,
    onAncModeSelected: (AncMode) -> Unit,
    onToggleSpatialAudio: () -> Unit,
    onToggleMic: () -> Unit,
    onSyncDesktop: () -> Unit,
    onNavigateToTab: (AppTab) -> Unit,
    onQuickPromptClick: (String) -> Unit,
    onOpenQrScanner: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CeaserBlack)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Welcome Header
        item {
            Column(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                Text(
                    text = "Good day, ${userProfile.name.substringBefore(" ")}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = CeaserTextPrimary
                )
                Text(
                    text = "CEASER Ecosystem • Active Session",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CeaserTextSecondary
                )
            }
        }

        // Desktop-First Ecosystem Card (Active when no glasses or user focuses on desktop link)
        if (device.status != ConnectionStatus.CONNECTED) {
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = CeaserSurfaceCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserCyan.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("desktop_ecosystem_banner")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CeaserSurfaceElevated),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Computer,
                                        contentDescription = null,
                                        tint = CeaserCyan,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "CEASER Desktop Ecosystem",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CeaserTextPrimary
                                    )
                                    Text(
                                        text = "Linked via Account: ${userProfile.email}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CeaserCyan
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CeaserSuccessMuted
                            ) {
                                Text(
                                    text = "Synced",
                                    color = CeaserSuccess,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "No glasses needed! Full NIX AI, workspace memory tokens, and clipboard sync are live with your workstation.",
                            style = MaterialTheme.typography.bodySmall,
                            color = CeaserTextSecondary,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onSyncDesktop,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CeaserSurfaceElevated),
                                modifier = Modifier.weight(1f).height(38.dp)
                            ) {
                                Icon(Icons.Default.Sync, contentDescription = null, tint = CeaserCyan, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Sync Node", color = CeaserCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = onOpenQrScanner,
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                                modifier = Modifier.weight(1f).height(38.dp)
                            ) {
                                Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = CeaserTextPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Scan QR", color = CeaserTextPrimary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // 1. Dominant Smart-Device Card (CEASER NIX Smart Glasses - Optional)
        item {
            DeviceStatusCard(
                device = device,
                onPrimaryAction = onPrimaryDeviceAction,
                onManageClick = onManageDeviceClick,
                onToggleConnection = onToggleConnection
            )
        }

        // 2. Audio & Microphones Controls
        if (device.status == ConnectionStatus.CONNECTED) {
            item {
                AudioControlCard(
                    device = device,
                    onAncModeSelected = onAncModeSelected,
                    onToggleSpatialAudio = onToggleSpatialAudio,
                    onToggleMic = onToggleMic
                )
            }
        }

        // 3. Quick-Access Card for NIX AI Assistant
        item {
            NixQuickAccessCard(
                onOpenNix = { onNavigateToTab(AppTab.NIX) },
                onQuickPrompt = onQuickPromptClick
            )
        }

        // 4. CEASER NIX Desktop & Ecosystem Card
        item {
            EcosystemCard(
                desktopDevice = desktopDevice,
                onSyncNow = onSyncDesktop
            )
        }

        // 5. Recent Activity Feed
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CeaserTextPrimary
                    )

                    Text(
                        text = "Hardware Telemetry",
                        style = MaterialTheme.typography.labelSmall,
                        color = CeaserTextMuted
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(CeaserSurfaceCard)
                        .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    activityLogs.take(3).forEach { log ->
                        ActivityRowItem(log = log)
                    }
                }
            }
        }
    }
}

@Composable
private fun NixQuickAccessCard(
    onOpenNix: () -> Unit,
    onQuickPrompt: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("nix_quick_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(CeaserViolet.copy(alpha = 0.2f))
                            .border(1.dp, CeaserViolet.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "NIX",
                            tint = CeaserVioletLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "NIX Assistant",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CeaserTextPrimary
                        )
                        Text(
                            text = "Continuous Wearable AI Context",
                            style = MaterialTheme.typography.bodySmall,
                            color = CeaserTextSecondary
                        )
                    }
                }

                TextButton(
                    onClick = onOpenNix,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = CeaserVioletLight)
                ) {
                    Text(
                        text = "Open Chat",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Prompt Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickChip(
                    text = "Battery telemetry",
                    onClick = { onQuickPrompt("Check glasses battery & audio latency") },
                    modifier = Modifier.weight(1f)
                )
                QuickChip(
                    text = "Sync to desktop",
                    onClick = { onQuickPrompt("Sync active workspace notes to CEASER NIX Desktop") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun QuickChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CeaserDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = CeaserTextSecondary,
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ActivityRowItem(log: ActivityLog) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        when (log.tag) {
                            "AUDIO" -> CeaserCyan
                            "CLOUD" -> CeaserViolet
                            "WEARABLE" -> CeaserSuccess
                            else -> CeaserTextMuted
                        }
                    )
            )

            Column {
                Text(
                    text = log.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CeaserTextPrimary
                )
                Text(
                    text = log.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserTextSecondary,
                    maxLines = 1
                )
            }
        }

        Text(
            text = log.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = CeaserTextMuted,
            fontSize = 11.sp
        )
    }
}
