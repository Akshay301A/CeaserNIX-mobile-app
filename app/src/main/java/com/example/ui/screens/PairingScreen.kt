package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.DiscoveredDevice
import com.example.ui.theme.*
import com.example.viewmodel.PairingStep

@Composable
fun PairingScreen(
    currentStep: PairingStep,
    discoveredDevices: List<DiscoveredDevice>,
    selectedDevice: DiscoveredDevice?,
    pairingProgress: Float,
    onClose: () -> Unit,
    onProceedToPermissions: () -> Unit,
    onStartScan: () -> Unit,
    onSelectDevice: (DiscoveredDevice) -> Unit,
    onRetry: () -> Unit,
    onTroubleshooting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CeaserBlack,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp)
        ) {
            // Top Navigation & Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CeaserSurfaceCard)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel pairing",
                        tint = CeaserTextSecondary
                    )
                }

                Text(
                    text = "PAIR CEASER DEVICE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = CeaserTextMuted
                )

                IconButton(
                    onClick = onTroubleshooting,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CeaserSurfaceCard)
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "Help",
                        tint = CeaserCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Body based on PairingStep
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (currentStep) {
                    PairingStep.INTRO -> {
                        IntroStepView(onNext = onProceedToPermissions)
                    }
                    PairingStep.PERMISSIONS -> {
                        PermissionsStepView(onGrant = onStartScan)
                    }
                    PairingStep.SCANNING -> {
                        ScanningStepView(onDeviceFound = {})
                    }
                    PairingStep.DISCOVERED -> {
                        DiscoveredStepView(
                            devices = discoveredDevices,
                            onSelectDevice = onSelectDevice,
                            onRescan = onStartScan
                        )
                    }
                    PairingStep.PAIRING -> {
                        PairingProgressStepView(
                            device = selectedDevice,
                            progress = pairingProgress
                        )
                    }
                    PairingStep.SUCCESS -> {
                        SuccessStepView(
                            device = selectedDevice,
                            onFinish = onClose
                        )
                    }
                    PairingStep.TROUBLESHOOTING -> {
                        TroubleshootingStepView(
                            onRetry = onRetry,
                            onBackToIntro = onProceedToPermissions
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// Step 1: Intro & Device Preparation
// -------------------------------------------------------------------------
@Composable
private fun IntroStepView(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Product Hero
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(CeaserSurfaceCard)
                .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(24.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_ceaser_glasses),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Prepare CEASER NIX Glasses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Press and hold the temple button on the left frame for 3 seconds until the violet LED indicator begins pulsing rhythmically.",
            style = MaterialTheme.typography.bodyMedium,
            color = CeaserTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNext,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("pairing_intro_next_btn")
        ) {
            Text("LED is Pulsing — Continue", fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------------------
// Step 2: Bluetooth Permission Explanation
// -------------------------------------------------------------------------
@Composable
private fun PermissionsStepView(onGrant: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(CeaserSurfaceElevated)
                .border(1.dp, CeaserCyan.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.BluetoothSearching,
                contentDescription = null,
                tint = CeaserCyan,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bluetooth Low Energy Access",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "CEASER requires nearby device scanning permissions to discover the NIX Glasses peripheral and establish low-latency telemetry.",
            style = MaterialTheme.typography.bodyMedium,
            color = CeaserTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onGrant,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("pairing_allow_permissions_btn")
        ) {
            Text("Allow & Scan Nearby Devices", fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------------------
// Step 3: Radar Scanning Animation
// -------------------------------------------------------------------------
@Composable
private fun ScanningStepView(onDeviceFound: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(240.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val maxRadius = size.minDimension / 2
                // Concentric guide rings
                drawCircle(color = CeaserBorderSubtle, radius = maxRadius * 0.35f, style = Stroke(1.dp.toPx()))
                drawCircle(color = CeaserBorderSubtle, radius = maxRadius * 0.7f, style = Stroke(1.dp.toPx()))
                drawCircle(color = CeaserBorderSubtle, radius = maxRadius, style = Stroke(1.dp.toPx()))

                // Pulsing wave
                drawCircle(
                    color = CeaserViolet.copy(alpha = pulseAlpha),
                    radius = maxRadius * pulseRadius,
                    style = Stroke(2.5.dp.toPx())
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(CeaserViolet)
                    .border(2.dp, CeaserVioletLight, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = CeaserTextPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Scanning for CEASER Hardware...",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Keep your glasses within 1 meter of your phone",
            style = MaterialTheme.typography.bodyMedium,
            color = CeaserTextSecondary
        )
    }
}

// -------------------------------------------------------------------------
// Step 4: Discovered Device List
// -------------------------------------------------------------------------
@Composable
private fun DiscoveredStepView(
    devices: List<DiscoveredDevice>,
    onSelectDevice: (DiscoveredDevice) -> Unit,
    onRescan: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Discovered CEASER Devices",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Select your hardware model to initiate secure pairing",
            style = MaterialTheme.typography.bodySmall,
            color = CeaserTextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            devices.forEach { dev ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onSelectDevice(dev) }
                        .testTag("discovered_device_${dev.id}")
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
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CeaserSurfaceElevated)
                                .border(1.dp, CeaserViolet.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bluetooth,
                                contentDescription = null,
                                tint = CeaserVioletLight,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = dev.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CeaserTextPrimary
                            )
                            Text(
                                text = "${dev.model} • RSSI ${dev.rssi} dBm",
                                style = MaterialTheme.typography.bodySmall,
                                color = CeaserCyan
                            )
                        }

                        Button(
                            onClick = { onSelectDevice(dev) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet)
                        ) {
                            Text("Pair")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onRescan,
            colors = ButtonDefaults.textButtonColors(contentColor = CeaserTextSecondary)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Rescan for Devices")
        }
    }
}

// -------------------------------------------------------------------------
// Step 5: Pairing Handshake in Progress
// -------------------------------------------------------------------------
@Composable
private fun PairingProgressStepView(
    device: DiscoveredDevice?,
    progress: Float
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            progress = { progress },
            strokeWidth = 6.dp,
            color = CeaserViolet,
            trackColor = CeaserSurfaceElevated,
            modifier = Modifier.size(88.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Pairing with ${device?.name ?: "CEASER Device"}...",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Exchanging encryption keys and calibrating temple sensors (${(progress * 100).toInt()}%)",
            style = MaterialTheme.typography.bodyMedium,
            color = CeaserTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

// -------------------------------------------------------------------------
// Step 6: Pairing Success & Onboarding
// -------------------------------------------------------------------------
@Composable
private fun SuccessStepView(
    device: DiscoveredDevice?,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(CeaserSuccessMuted)
                .border(2.dp, CeaserSuccess, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = CeaserSuccess,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Successfully Connected!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${device?.name ?: "CEASER NIX Glasses"} is bound and ready. Spatial head-tracking and NIX continuous assistant are now active.",
            style = MaterialTheme.typography.bodyMedium,
            color = CeaserTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onFinish,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("pairing_finish_btn")
        ) {
            Text("Open Dashboard", fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------------------
// Step 7: Troubleshooting Guidance
// -------------------------------------------------------------------------
@Composable
private fun TroubleshootingStepView(
    onRetry: () -> Unit,
    onBackToIntro: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Pairing Troubleshooting",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CeaserTextPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        TroubleshootCard(
            title = "1. LED not pulsing violet?",
            body = "Ensure the glasses have charge. Place them in the charging case or hold the power button for a full 5 seconds."
        )

        Spacer(modifier = Modifier.height(10.dp))

        TroubleshootCard(
            title = "2. Device already paired with Desktop?",
            body = "CEASER glasses can remember 2 devices simultaneously, but will prioritize the active audio stream. Disconnect from Desktop first if searching."
        )

        Spacer(modifier = Modifier.height(10.dp))

        TroubleshootCard(
            title = "3. Bluetooth scan timed out?",
            body = "Toggle your phone's Bluetooth off and on, or verify Location and Nearby Devices permissions are granted."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Try Scanning Again", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackToIntro,
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Preparation Steps", color = CeaserTextSecondary)
        }
    }
}

@Composable
private fun TroubleshootCard(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CeaserSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CeaserVioletLight
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = CeaserTextSecondary
            )
        }
    }
}
