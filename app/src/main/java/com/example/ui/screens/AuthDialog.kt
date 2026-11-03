package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun AuthDialog(
    onDismiss: () -> Unit,
    onSignIn: (email: String, name: String) -> Unit
) {
    var email by remember { mutableStateOf("alex.mercer@ceaser.ai") }
    var password by remember { mutableStateOf("••••••••••••") }
    var name by remember { mutableStateOf("Alex Mercer") }
    var isLoading by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CeaserDarkSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CEASER ID",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = CeaserTextPrimary
                        )
                        Text(
                            text = "Wearable Ecosystem Authentication",
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

                // Name input
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.labelSmall,
                    color = CeaserTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
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

                // Email input
                Text(
                    text = "Account Email",
                    style = MaterialTheme.typography.labelSmall,
                    color = CeaserTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
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

                // Password input
                Text(
                    text = "Password / Security Key",
                    style = MaterialTheme.typography.labelSmall,
                    color = CeaserTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
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

                Spacer(modifier = Modifier.height(24.dp))

                // Sign In Button
                Button(
                    onClick = {
                        isLoading = true
                        onSignIn(email, name)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CeaserViolet),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("auth_submit_btn")
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            color = CeaserTextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Sign In with CEASER", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Demo Quick Pass
                OutlinedButton(
                    onClick = { onSignIn("alex.mercer@ceaser.ai", "Alex Mercer") },
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CeaserBorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue with Demo Session", color = CeaserCyan)
                }
            }
        }
    }
}
