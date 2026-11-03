package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DevicesOther
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun DevicesScreen(
    device: CeaserDevice,
    ecosystemDevices: List<EcosystemDevice>,
    onDeviceSelected: () -> Unit,
    onPairNewDeviceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isGlassesPaired = device.status != ConnectionStatus.NOT_PAIRED

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CeaserBlack)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Title & Pair Button Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Devices",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = CeaserTextPrimary
                    )
                    Text(
                        text = "Connected Wearables & Workstations",
                        style = MaterialTheme.typography.bodySmall,
                        color = CeaserTextSecondary
                    )
                }

                FilledTonalButton(
                    onClick = onPairNewDeviceClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = CeaserViolet,
                        contentColor = CeaserTextPrimary
                    ),
                    modifier = Modifier.testTag("pair_new_device_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Pair Device",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Section: Active Wearable Device
        item {
            Text(
                text = "Primary Wearable",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 1.sp
            )
        }

        if (isGlassesPaired) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onDeviceSelected() }
                        .testTag("paired_device_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Thumbnail
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(CeaserDarkSurface)
                                    .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(14.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_ceaser_glasses),
                                    contentDescription = device.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = device.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = CeaserTextPrimary
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (device.status == ConnectionStatus.CONNECTED) CeaserSuccess else CeaserError
                                            )
                                    )
                                }

                                Text(
                                    text = device.model,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CeaserTextSecondary
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (device.status == ConnectionStatus.CONNECTED) {
                                        Text(
                                            text = "Frame: ${device.batteryGlasses}% • Case: ${device.batteryCase}%",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CeaserCyan,
                                            fontWeight = FontWeight.Medium
                                        )
                                    } else {
                                        Text(
                                            text = "Disconnected",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CeaserError
                                        )
                                    }

                                    Text(
                                        text = "FW ${device.firmwareVersion}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CeaserTextMuted
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "View Details",
                                tint = CeaserTextMuted
                            )
                        }
                    }
                }
            }
        } else {
            // Empty state when no wearable is paired
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
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(CeaserSurfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DevicesOther,
                                contentDescription = null,
                                tint = CeaserTextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "No CEASER Glasses Paired",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CeaserTextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Put your glasses into pairing mode by holding the power button for 3 seconds.",
                            style = MaterialTheme.typography.bodySmall,
                            color = CeaserTextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = onPairNewDeviceClick,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet)
                        ) {
                            Text("Pair Glasses Now")
                        }
                    }
                }
            }
        }

        // Section: Ecosystem Devices & Nodes
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Paired Ecosystem Nodes",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CeaserTextSecondary,
                letterSpacing = 1.sp
            )
        }

        items(ecosystemDevices) { echoDevice ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CeaserSurfaceElevated)
                            .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (echoDevice.type.contains("Workstation")) Icons.Default.Computer else Icons.Default.Sensors,
                            contentDescription = null,
                            tint = CeaserCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = echoDevice.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = CeaserTextPrimary
                            )
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(if (echoDevice.isOnline) CeaserSuccess else CeaserTextMuted)
                            )
                        }

                        Text(
                            text = echoDevice.detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = CeaserTextSecondary
                        )

                        Text(
                            text = echoDevice.lastSync,
                            style = MaterialTheme.typography.labelSmall,
                            color = CeaserTextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = CeaserDarkSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle)
                    ) {
                        Text(
                            text = echoDevice.type.substringBefore(" "),
                            style = MaterialTheme.typography.labelSmall,
                            color = CeaserTextSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
