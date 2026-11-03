package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailSheet(
    device: CeaserDevice,
    onDismiss: () -> Unit,
    onToggleConnection: () -> Unit,
    onAncModeSelected: (AncMode) -> Unit,
    onAudioRoutingSelected: (AudioRouting) -> Unit,
    onToggleWearDetection: () -> Unit,
    onToggleTouchControls: () -> Unit,
    onToggleSpatialAudio: () -> Unit,
    onToggleMic: () -> Unit,
    onCheckFirmware: () -> Unit,
    onUnpairDevice: () -> Unit
) {
    var showUnpairConfirmDialog by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CeaserDarkSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = CeaserBorderSubtle) },
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Device Visual & Model Info
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(CeaserSurfaceCard)
                            .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_ceaser_glasses),
                            contentDescription = device.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = device.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = CeaserTextPrimary
                        )
                        Text(
                            text = device.model,
                            style = MaterialTheme.typography.bodySmall,
                            color = CeaserTextSecondary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (device.status == ConnectionStatus.CONNECTED) CeaserSuccess else CeaserError
                                    )
                            )
                            Text(
                                text = if (device.status == ConnectionStatus.CONNECTED) "Connected via BLE" else "Disconnected",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (device.status == ConnectionStatus.CONNECTED) CeaserSuccess else CeaserError
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CeaserSurfaceElevated)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = CeaserTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Battery Telemetry Grid (Left temple, Right temple, Smart Case)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BatteryBox(label = "Left Temple", value = "${device.batteryGlasses}%", icon = Icons.Default.BatteryFull, modifier = Modifier.weight(1f))
                    BatteryBox(label = "Right Temple", value = "${device.batteryGlasses + 2}%", icon = Icons.Default.BatteryFull, modifier = Modifier.weight(1f))
                    BatteryBox(label = "Charging Case", value = "${device.batteryCase}%", icon = Icons.Default.Bolt, modifier = Modifier.weight(1f))
                }
            }

            // Group: Audio Routing
            item {
                SectionHeader("Audio Output Routing")
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CeaserSurfaceCard)
                        .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(16.dp))
                ) {
                    AudioRouting.values().forEachIndexed { index, routing ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAudioRoutingSelected(routing) }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = routing.label,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CeaserTextPrimary
                            )
                            RadioButton(
                                selected = device.audioRouting == routing,
                                onClick = { onAudioRoutingSelected(routing) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = CeaserVioletLight,
                                    unselectedColor = CeaserTextMuted
                                )
                            )
                        }
                        if (index < AudioRouting.values().size - 1) {
                            HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)
                        }
                    }
                }
            }

            // Group: Hardware Sensor Preferences
            item {
                SectionHeader("Sensors & Gestures")
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CeaserSurfaceCard)
                        .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(16.dp))
                ) {
                    SettingToggleRow(
                        title = "Smart Wear Detection",
                        subtitle = "Auto-pause media when glasses are removed from face",
                        checked = device.wearDetection,
                        onCheckedChange = { onToggleWearDetection() }
                    )

                    HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                    SettingToggleRow(
                        title = "Capacitive Temple Gestures",
                        subtitle = "Double tap for NIX AI, swipe for volume control",
                        checked = device.touchControls,
                        onCheckedChange = { onToggleTouchControls() }
                    )

                    HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                    SettingToggleRow(
                        title = "Spatial 3D Audio Head-Tracking",
                        subtitle = "Dynamic acoustic positioning matching head rotation",
                        checked = device.spatialAudioEnabled,
                        onCheckedChange = { onToggleSpatialAudio() }
                    )

                    HorizontalDivider(color = CeaserBorderSubtle, thickness = 1.dp)

                    SettingToggleRow(
                        title = "Beamforming Microphone",
                        subtitle = "Dual ambient noise suppression for clear voice queries",
                        checked = device.micActive,
                        onCheckedChange = { onToggleMic() }
                    )
                }
            }

            // Group: Device Diagnostics & Firmware
            item {
                SectionHeader("Device Information")
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CeaserSurfaceCard)
                        .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InfoRow("Hardware Model", device.model)
                    InfoRow("MAC Address", device.macAddress)
                    InfoRow("Firmware Version", device.firmwareVersion)

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedButton(
                        onClick = onCheckFirmware,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CeaserCyan),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserCyan.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.SystemUpdate, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Check for Firmware Updates", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Disconnect & Unpair Actions
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onToggleConnection,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (device.status == ConnectionStatus.CONNECTED) CeaserTextSecondary else CeaserSuccess
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(if (device.status == ConnectionStatus.CONNECTED) "Disconnect" else "Connect")
                    }

                    Button(
                        onClick = { showUnpairConfirmDialog = true },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CeaserErrorMuted,
                            contentColor = CeaserError
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("unpair_device_action_btn")
                    ) {
                        Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Unpair Device", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

    // Confirmation dialog before unpairing
    if (showUnpairConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showUnpairConfirmDialog = false },
            containerColor = CeaserDarkSurface,
            title = {
                Text("Unpair ${device.name}?", color = CeaserTextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "This will remove the Bluetooth bond and clear cached offline telemetry. You can re-pair at any time.",
                    color = CeaserTextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showUnpairConfirmDialog = false
                        onUnpairDevice()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CeaserError)
                ) {
                    Text("Unpair")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUnpairConfirmDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = CeaserTextSecondary)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = CeaserTextSecondary,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
    )
}

@Composable
private fun SettingToggleRow(
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
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = CeaserTextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = CeaserTextSecondary
            )
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

@Composable
private fun BatteryBox(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = CeaserSurfaceCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CeaserCyan,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CeaserTextPrimary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = CeaserTextMuted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = CeaserTextSecondary)
        Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = CeaserTextPrimary)
    }
}
