package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 3D Asset Placeholders for CEASER Ecosystem:
 * 1. Neural Orb (img_placeholder_orb)
 * 2. Smart Glasses (img_placeholder_glasses)
 * 3. Desktop Workstation (img_placeholder_desktop)
 * 4. Mobile Node (img_placeholder_mobile)
 * 
 * Replace these drawables in res/drawable/ with your custom 3D model renders.
 */
@Composable
fun AuthScreen(
    onAuthSuccess: (email: String, name: String) -> Unit,
    onOpenQrScanner: () -> Unit,
    onContinueGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val placeholderDrawables = remember {
        listOf(
            R.drawable.img_placeholder_orb,
            R.drawable.img_placeholder_glasses,
            R.drawable.img_placeholder_desktop,
            R.drawable.img_placeholder_mobile
        )
    }

    var currentIndex by remember { mutableStateOf(0) }
    var showEmailModal by remember { mutableStateOf(false) }

    // Auto-cycle through the 4 3D placeholders
    LaunchedEffect(Unit) {
        while (true) {
            delay(3800)
            currentIndex = (currentIndex + 1) % placeholderDrawables.size
        }
    }

    // 3D Levitation continuous animation
    val infiniteTransition = rememberInfiniteTransition(label = "3d_float")
    val floatY by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY"
    )
    val tiltY by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tiltY"
    )

    val density = LocalDensity.current.density

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .testTag("auth_screen"),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Minimal Top Bar with 3-line violet indicator matching reference
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .clickable { onOpenQrScanner() }
                    .padding(6.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(3.5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 20.dp, height = 2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color(0xFF9333EA))
                )
                Box(
                    modifier = Modifier
                        .size(width = 15.dp, height = 2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color(0xFF9333EA))
                )
                Box(
                    modifier = Modifier
                        .size(width = 9.dp, height = 2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color(0xFF9333EA))
                )
            }
        }

        // Central Stage: 3D Animated Floating Placeholder Stage
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 3D Object Stage with continuous levitation
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .graphicsLayer {
                        translationY = floatY * density
                        rotationY = tiltY
                        cameraDistance = 12f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                // Soft Ambient Radial Halo
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF9333EA).copy(alpha = 0.18f))
                )

                // Smooth morphing cross-fade between 3D placeholders
                AnimatedContent(
                    targetState = placeholderDrawables[currentIndex],
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(340, easing = FastOutSlowInEasing)) +
                                scaleIn(initialScale = 0.9f, animationSpec = tween(340))) togetherWith
                                (fadeOut(animationSpec = tween(260)) +
                                        scaleOut(targetScale = 0.9f, animationSpec = tween(260)))
                    },
                    label = "placeholder_morph"
                ) { targetRes ->
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .clickable {
                                currentIndex = (currentIndex + 1) % placeholderDrawables.size
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = targetRes),
                            contentDescription = "3D Ecosystem Node Placeholder",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Clean, Subtle Pagination Dots (NO NAMES / NO PILLS)
            Row(
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                placeholderDrawables.forEachIndexed { idx, _ ->
                    val isActive = idx == currentIndex
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .width(if (isActive) 16.dp else 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isActive) Color(0xFFA855F7) else Color.White.copy(alpha = 0.2f))
                            .clickable { currentIndex = idx }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Steady High-Fashion Editorial Typography matching reference
            Text(
                text = "HEAR YOUR IDEAS\nCOME TO LIFE",
                color = Color(0xFFD8B4FE),
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Transform your voice into dynamic spatial intelligence and bring your imagination to life",
                color = Color(0xFF818CF8),
                fontSize = 12.5.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }

        // Bottom Action Controls matching reference
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primary Purple Pill: Log in with Email
            Button(
                onClick = { showEmailModal = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("auth_email_pill_btn"),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B5CF6),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Log in with Email",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.2.sp
                )
            }

            // Divider: or continue with
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.08f)
                )
                Text(
                    text = "or continue with",
                    color = Color(0xFF64748B),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.08f)
                )
            }

            // Dark Pill: Continue with Google
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { onAuthSuccess("alex.mercer@gmail.com", "Alex Mercer") },
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF12141F),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.07f))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Continue with Google",
                        color = Color(0xFFF1F5F9),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Dark Pill: Continue with Apple
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { onAuthSuccess("alex.mercer@icloud.com", "Alex Mercer") },
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF12141F),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.07f))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Continue with Apple",
                        color = Color(0xFFF1F5F9),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Secondary Options: QR Scanner & Guest Mode
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Scan Desktop QR",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable { onOpenQrScanner() }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(3.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF475569))
                )

                Text(
                    text = "Guest Mode",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable { onContinueGuest() }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
        }
    }

    // Email / Password Login Sheet Modal
    if (showEmailModal) {
        var isRegisterMode by remember { mutableStateOf(false) }
        var emailInput by remember { mutableStateOf("alex.mercer@ceaser.ai") }
        var passwordInput by remember { mutableStateOf("••••••••••••") }
        var passwordVisible by remember { mutableStateOf(false) }
        var nameInput by remember { mutableStateOf("Alex Mercer") }
        var isSubmitting by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Dialog(
            onDismissRequest = { showEmailModal = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable { showEmailModal = false },
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = false) {},
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = Color(0xFF0D0F18),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Grabber
                        Box(
                            modifier = Modifier
                                .size(width = 38.dp, height = 4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .align(Alignment.CenterHorizontally)
                        )

                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CEASER ID",
                                    color = Color(0xFFF8F9FD),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Sign in to your ecosystem",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(onClick = { showEmailModal = false }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFF94A3B8)
                                )
                            }
                        }

                        // Mode Switcher
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF141724),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(3.dp)) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(9.dp))
                                        .background(if (!isRegisterMode) Color(0xFF262D42) else Color.Transparent)
                                        .clickable { isRegisterMode = false }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Sign In",
                                        color = if (!isRegisterMode) Color(0xFFF8F9FD) else Color(0xFF64748B),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(9.dp))
                                        .background(if (isRegisterMode) Color(0xFF262D42) else Color.Transparent)
                                        .clickable { isRegisterMode = true }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Create Account",
                                        color = if (isRegisterMode) Color(0xFFF8F9FD) else Color(0xFF64748B),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        if (isRegisterMode) {
                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
                                label = { Text("Name") },
                                leadingIcon = { Icon(Icons.Default.Person, null, tint = Color(0xFF64748B)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text("Email Address") },
                            leadingIcon = { Icon(Icons.Default.Email, null, tint = Color(0xFF64748B)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            label = { Text("Password") },
                            leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color(0xFF64748B)) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        null,
                                        tint = Color(0xFF64748B)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = {
                                isSubmitting = true
                                scope.launch {
                                    delay(350)
                                    isSubmitting = false
                                    showEmailModal = false
                                    onAuthSuccess(emailInput, nameInput)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                        ) {
                            if (isSubmitting) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                Text(
                                    text = if (isRegisterMode) "Create CEASER Account" else "Sign In to Ecosystem",
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
