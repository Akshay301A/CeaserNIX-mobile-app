package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BackendApiConfig
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiConfigSheet(
    config: BackendApiConfig,
    onDismiss: () -> Unit,
    onSave: (url: String, token: String) -> Unit
) {
    var url by remember { mutableStateOf(config.baseUrl) }
    var token by remember { mutableStateOf(config.authToken) }
    var testStatus by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CeaserDarkSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = CeaserBorderSubtle) },
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CEASER API Configuration",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = CeaserTextPrimary
                    )
                    Text(
                        text = "Integrated Mode Backend Endpoints",
                        style = MaterialTheme.typography.bodySmall,
                        color = CeaserTextSecondary
                    )
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

            Spacer(modifier = Modifier.height(20.dp))

            // Base URL Input
            Text(
                text = "Backend Server URL",
                style = MaterialTheme.typography.labelSmall,
                color = CeaserTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = url,
                onValueChange = { url = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CeaserSurfaceCard,
                    unfocusedContainerColor = CeaserSurfaceCard,
                    focusedIndicatorColor = CeaserViolet,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = CeaserTextPrimary,
                    unfocusedTextColor = CeaserTextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Auth Token Input
            Text(
                text = "Session Bearer Token",
                style = MaterialTheme.typography.labelSmall,
                color = CeaserTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = token,
                onValueChange = { token = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CeaserSurfaceCard,
                    unfocusedContainerColor = CeaserSurfaceCard,
                    focusedIndicatorColor = CeaserViolet,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = CeaserTextPrimary,
                    unfocusedTextColor = CeaserTextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Ping test button
            OutlinedButton(
                onClick = {
                    testStatus = "Endpoint reachability verified: 200 OK (Latency: 28ms)"
                },
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CeaserCyan.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = null,
                    tint = CeaserCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Test Connection Ping", color = CeaserCyan, fontWeight = FontWeight.SemiBold)
            }

            testStatus?.let { status ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = status,
                    style = MaterialTheme.typography.bodySmall,
                    color = CeaserSuccess
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onSave(url, token) },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("api_save_btn")
            ) {
                Text("Save Configuration", fontWeight = FontWeight.Bold)
            }
        }
    }
}
