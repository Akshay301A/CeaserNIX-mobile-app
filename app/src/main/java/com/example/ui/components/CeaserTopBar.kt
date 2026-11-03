package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppMode
import com.example.model.ConnectionStatus
import com.example.ui.theme.*

@Composable
fun CeaserTopBar(
    appMode: AppMode,
    connectionStatus: ConnectionStatus,
    deviceName: String,
    onModeBadgeClick: () -> Unit,
    onDeviceStatusClick: () -> Unit,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CeaserBlack,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CeaserSurfaceElevated)
                        .border(1.dp, CeaserBorderSubtle, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = CeaserVioletLight
                    )
                }

                Column {
                    Text(
                        text = "CEASER",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = CeaserTextPrimary
                    )
                    Text(
                        text = "ECOSYSTEM",
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 1.5.sp,
                        color = CeaserTextMuted
                    )
                }
            }

            // Status Badges & Quick Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mode indicator badge (DEMO vs INTEGRATED)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoBg else CeaserSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold.copy(alpha = 0.5f) else CeaserViolet.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onModeBadgeClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold else CeaserViolet)
                        )
                        Text(
                            text = if (appMode == AppMode.DESIGN_DEMO) "DEMO" else "API LIVE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = if (appMode == AppMode.DESIGN_DEMO) CeaserDemoGold else CeaserVioletLight
                        )
                    }
                }

                // Connection pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = CeaserSurfaceCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onDeviceStatusClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val statusColor = when (connectionStatus) {
                            ConnectionStatus.CONNECTED -> CeaserSuccess
                            ConnectionStatus.CONNECTING -> CeaserWarning
                            ConnectionStatus.DISCONNECTED -> CeaserError
                            ConnectionStatus.NOT_PAIRED -> CeaserTextMuted
                            ConnectionStatus.ERROR -> CeaserError
                        }

                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )

                        Icon(
                            imageVector = if (connectionStatus == ConnectionStatus.CONNECTED) Icons.Default.BluetoothConnected else Icons.Default.Bluetooth,
                            contentDescription = "Bluetooth Status",
                            tint = if (connectionStatus == ConnectionStatus.CONNECTED) CeaserCyan else CeaserTextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Sync trigger
                IconButton(
                    onClick = onSyncClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Sync Ecosystem",
                        tint = CeaserTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
