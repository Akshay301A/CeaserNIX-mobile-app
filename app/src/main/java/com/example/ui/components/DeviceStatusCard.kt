package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CeaserDevice
import com.example.model.ConnectionStatus
import com.example.ui.theme.*

@Composable
fun DeviceStatusCard(
    device: CeaserDevice,
    onPrimaryAction: () -> Unit,
    onManageClick: () -> Unit,
    onToggleConnection: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPaired = device.status != ConnectionStatus.NOT_PAIRED
    val isConnected = device.status == ConnectionStatus.CONNECTED

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = modifier
            .fillMaxWidth()
            .testTag("device_status_card")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Header of the Card (Device Name & Status Pill)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isPaired) device.name else "No Glasses Paired",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = CeaserTextPrimary
                    )
                    Text(
                        text = if (isPaired) device.model else "CEASER NIX Smart Glasses",
                        style = MaterialTheme.typography.bodySmall,
                        color = CeaserTextSecondary
                    )
                }

                // Connection status chip
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = when (device.status) {
                        ConnectionStatus.CONNECTED -> CeaserSuccessMuted
                        ConnectionStatus.CONNECTING -> CeaserWarningMuted
                        ConnectionStatus.DISCONNECTED -> CeaserErrorMuted
                        ConnectionStatus.NOT_PAIRED -> CeaserSurfaceElevated
                        ConnectionStatus.ERROR -> CeaserErrorMuted
                    },
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = when (device.status) {
                            ConnectionStatus.CONNECTED -> CeaserSuccess.copy(alpha = 0.6f)
                            ConnectionStatus.CONNECTING -> CeaserWarning.copy(alpha = 0.6f)
                            ConnectionStatus.DISCONNECTED -> CeaserError.copy(alpha = 0.6f)
                            ConnectionStatus.NOT_PAIRED -> CeaserBorderSubtle
                            ConnectionStatus.ERROR -> CeaserError.copy(alpha = 0.6f)
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(
                                    when (device.status) {
                                        ConnectionStatus.CONNECTED -> CeaserSuccess
                                        ConnectionStatus.CONNECTING -> CeaserWarning
                                        ConnectionStatus.DISCONNECTED -> CeaserError
                                        ConnectionStatus.NOT_PAIRED -> CeaserTextMuted
                                        ConnectionStatus.ERROR -> CeaserError
                                    }
                                )
                        )
                        Text(
                            text = when (device.status) {
                                ConnectionStatus.CONNECTED -> "Connected"
                                ConnectionStatus.CONNECTING -> "Connecting..."
                                ConnectionStatus.DISCONNECTED -> "Disconnected"
                                ConnectionStatus.NOT_PAIRED -> "Not paired"
                                ConnectionStatus.ERROR -> "Error"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = when (device.status) {
                                ConnectionStatus.CONNECTED -> CeaserSuccess
                                ConnectionStatus.CONNECTING -> CeaserWarning
                                ConnectionStatus.DISCONNECTED -> CeaserError
                                ConnectionStatus.NOT_PAIRED -> CeaserTextMuted
                                ConnectionStatus.ERROR -> CeaserError
                            }
                        )
                    }
                }
            }

            // Hero Product Visual Stage
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(CeaserDarkSurface)
            ) {
                // Background subtle ambient radial lighting
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    CeaserViolet.copy(alpha = 0.12f),
                                    Color.Transparent,
                                    CeaserDarkSurface.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                // High-resolution realistic smart glasses product render
                Image(
                    painter = painterResource(id = R.drawable.img_ceaser_glasses),
                    contentDescription = "CEASER NIX Smart Glasses product render",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(18.dp))
                )

                // Bottom subtle fade overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    CeaserDarkSurface.copy(alpha = 0.85f)
                                ),
                                startY = 100f
                            )
                        )
                )

                // Overlay pill for firmware info if connected
                if (isConnected) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CeaserSurfaceElevated.copy(alpha = 0.85f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "FW ${device.firmwareVersion}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CeaserTextSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Real Battery & Telemetry (Only shown when paired and available)
            if (isPaired && isConnected) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Glasses battery
                    TelemetryPill(
                        label = "Glasses",
                        value = "${device.batteryGlasses}%",
                        icon = Icons.Default.BatteryFull,
                        tint = CeaserCyan,
                        modifier = Modifier.weight(1f)
                    )

                    // Case battery
                    TelemetryPill(
                        label = "Case",
                        value = "${device.batteryCase}%",
                        icon = Icons.Default.Bolt,
                        tint = CeaserVioletLight,
                        modifier = Modifier.weight(1f)
                    )

                    // Audio Mode
                    TelemetryPill(
                        label = "ANC",
                        value = device.ancMode.label.substringBefore(" "),
                        icon = Icons.Default.Settings,
                        tint = CeaserTextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else if (isPaired && !isConnected) {
                // Disconnected info state
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = null,
                        tint = CeaserTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Glasses in standby or out of Bluetooth range.",
                        style = MaterialTheme.typography.bodySmall,
                        color = CeaserTextMuted
                    )
                }
            } else {
                // Not paired helpful onboarding prompt
                Text(
                    text = "Pair your CEASER NIX glasses to unlock head-tracking spatial audio and hands-free NIX AI assistance.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserTextSecondary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }

            HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

            // Primary & Secondary Action Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primary Action Button (Connect device / Pair glasses / Manage device)
                Button(
                    onClick = onPrimaryAction,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CeaserViolet,
                        contentColor = CeaserTextPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("device_primary_action_btn")
                ) {
                    Icon(
                        imageVector = if (!isPaired) Icons.Default.Add else Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            !isPaired -> "Pair Glasses"
                            !isConnected -> "Connect Device"
                            else -> "Manage Device"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // If paired, provide quick connect/disconnect toggle icon
                if (isPaired) {
                    OutlinedButton(
                        onClick = onToggleConnection,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isConnected) CeaserError else CeaserSuccess
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = if (isConnected) CeaserError.copy(alpha = 0.5f) else CeaserSuccess.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("device_toggle_connection_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PowerSettingsNew,
                            contentDescription = if (isConnected) "Disconnect" else "Connect",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TelemetryPill(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = CeaserSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(15.dp)
            )
            Column(modifier = Modifier.weight(1f, fill = false)) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = CeaserTextPrimary,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.5.sp,
                    color = CeaserTextMuted,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}
