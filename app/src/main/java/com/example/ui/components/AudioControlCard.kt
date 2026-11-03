package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.SpatialAudioOff
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AncMode
import com.example.model.CeaserDevice
import com.example.ui.theme.*

@Composable
fun AudioControlCard(
    device: CeaserDevice,
    onAncModeSelected: (AncMode) -> Unit,
    onToggleSpatialAudio: () -> Unit,
    onToggleMic: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = modifier
            .fillMaxWidth()
            .testTag("audio_control_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Audio & Microphones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CeaserTextPrimary
            )
            Text(
                text = "Temple beamforming drivers & ambient acoustic mode",
                style = MaterialTheme.typography.bodySmall,
                color = CeaserTextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // ANC Mode Selector Pills (Adaptive, Transparency, Off)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CeaserDarkSurface)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AncMode.values().forEach { mode ->
                    val isSelected = device.ancMode == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CeaserSurfaceElevated else CeaserDarkSurface)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) CeaserViolet.copy(alpha = 0.5f) else CeaserDarkSurface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onAncModeSelected(mode) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mode.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) CeaserVioletLight else CeaserTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Hardware Toggles (Spatial Audio & Mic Mute)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Spatial Audio Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (device.spatialAudioEnabled) CeaserSurfaceElevated else CeaserDarkSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (device.spatialAudioEnabled) CeaserCyan.copy(alpha = 0.4f) else CeaserBorderSubtle
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onToggleSpatialAudio() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (device.spatialAudioEnabled) Icons.Default.SurroundSound else Icons.Default.SpatialAudioOff,
                            contentDescription = "Spatial Audio",
                            tint = if (device.spatialAudioEnabled) CeaserCyan else CeaserTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text(
                                text = "Spatial 3D",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = CeaserTextPrimary
                            )
                            Text(
                                text = if (device.spatialAudioEnabled) "Active" else "Disabled",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (device.spatialAudioEnabled) CeaserCyan else CeaserTextMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Glasses Mic Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (device.micActive) CeaserSurfaceElevated else CeaserDarkSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (device.micActive) CeaserViolet.copy(alpha = 0.4f) else CeaserBorderSubtle
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onToggleMic() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (device.micActive) Icons.Default.Mic else Icons.Default.MicOff,
                            contentDescription = "Microphone",
                            tint = if (device.micActive) CeaserVioletLight else CeaserError,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text(
                                text = "Glasses Mic",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = CeaserTextPrimary
                            )
                            Text(
                                text = if (device.micActive) "Beamforming" else "Muted",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (device.micActive) CeaserVioletLight else CeaserError,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
