package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DesktopQrScannerScreen(
    onScanSuccess: (nodeId: String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "laser")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_y"
    )

    var isConnecting by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Surface(
        color = CeaserBlack,
        modifier = modifier
            .fillMaxSize()
            .testTag("desktop_qr_scanner")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
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
                        contentDescription = "Close Scanner",
                        tint = CeaserTextSecondary
                    )
                }

                Text(
                    text = "DESKTOP QR LINK",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = CeaserTextMuted
                )

                Box(modifier = Modifier.size(40.dp))
            }

            // Viewfinder & Laser Center Area
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Link with CEASER NIX Desktop",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CeaserTextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Point camera at the QR code on your desktop screen\n(CEASER NIX Desktop > Devices > Link Mobile)",
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserTextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Viewfinder Bounding Box
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(CeaserDarkSurface)
                        .border(1.5.dp, if (isSuccess) CeaserSuccess else CeaserCyan, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Scanning Laser Line
                    if (!isSuccess) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val yPos = size.height * laserY
                            drawLine(
                                color = CeaserCyan,
                                start = Offset(x = 10f, y = yPos),
                                end = Offset(x = size.width - 10f, y = yPos),
                                strokeWidth = 3.dp.toPx()
                            )
                        }
                    }

                    // Simulated QR target
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isSuccess) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = CeaserSuccess,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Desktop Linked!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CeaserSuccess
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                tint = CeaserTextMuted.copy(alpha = 0.5f),
                                modifier = Modifier.size(90.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "NODE: CEASER-DESKTOP-MAC",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = CeaserCyan.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Discovered Workstation Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CeaserSurfaceCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Computer,
                            contentDescription = null,
                            tint = CeaserCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Target Node: Studio Ultra (macOS Sonoma)",
                            style = MaterialTheme.typography.labelSmall,
                            color = CeaserTextSecondary
                        )
                    }
                }
            }

            // Bottom Trigger Button
            Button(
                onClick = {
                    isConnecting = true
                    scope.launch {
                        delay(900)
                        isConnecting = false
                        isSuccess = true
                        delay(600)
                        onScanSuccess("CEASER-NODE-MAC-984")
                    }
                },
                enabled = !isConnecting && !isSuccess,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CeaserCyan),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("simulate_qr_scan_btn")
            ) {
                if (isConnecting) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = CeaserBlack,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = if (isSuccess) "Connected" else "Simulate Scan & Connect Desktop",
                        color = CeaserBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
