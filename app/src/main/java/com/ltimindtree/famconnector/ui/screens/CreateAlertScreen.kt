package com.ltimindtree.famconnector.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ltimindtree.famconnector.ui.components.AppCard
import com.ltimindtree.famconnector.ui.components.SectionHeader
import com.ltimindtree.famconnector.ui.viewmodel.ProfileEditorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlertScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileEditorViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    
    var lastSeenLocation by remember { mutableStateOf("") }
    var lastSeenTime by remember { mutableStateOf("") }
    var clothingDescription by remember { mutableStateOf("") }
    var generalDescription by remember { mutableStateOf("") }
    var notifyAuthorities by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Emergency Broadcast", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        profile?.let { p ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "This is a formal Emergency Alert. Information provided will be shared with local law enforcement and broadcasted to the community.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                AppCard {
                    SectionHeader("Critical Details")
                    
                    OutlinedTextField(
                        value = lastSeenLocation,
                        onValueChange = { lastSeenLocation = it },
                        label = { Text("Last Seen Location") },
                        placeholder = { Text("Exact address or landmark") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = lastSeenTime,
                        onValueChange = { lastSeenTime = it },
                        label = { Text("Time Disappeared") },
                        placeholder = { Text("e.g. 15 minutes ago, or 2:00 PM") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                AppCard {
                    SectionHeader("Physical Description")
                    
                    OutlinedTextField(
                        value = clothingDescription,
                        onValueChange = { clothingDescription = it },
                        label = { Text("Clothing at Time of Missing") },
                        placeholder = { Text("Color of shirt, shoes, any bags etc.") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.AccessibilityNew, contentDescription = null) },
                        minLines = 2,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = generalDescription,
                        onValueChange = { generalDescription = it },
                        label = { Text("Identifying Marks / Behavior") },
                        placeholder = { Text("Glasses, tattoos, or specific walking style") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                AppCard {
                    SectionHeader("Broadcast Channels")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = notifyAuthorities, onCheckedChange = { notifyAuthorities = it })
                        Text("Notify Local Police Station", style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = true, onCheckedChange = { }, enabled = false)
                        Text("Instant Mobile Community Notification", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            viewModel.publishAlert(
                                clothingDescription = clothingDescription,
                                description = "Last seen at: $lastSeenLocation. Time: $lastSeenTime. Notes: $generalDescription"
                            )
                            onNavigateBack()
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("ACTIVATE EMERGENCY BROADCAST", fontWeight = FontWeight.ExtraBold)
                    }

                    OutlinedButton(
                        onClick = {
                            val shareText = "EMERGENCY: ${p.name} (Age ${p.ageRange}) is MISSING. " +
                                    "Last seen at $lastSeenLocation around $lastSeenTime. " +
                                    "Wearing: $clothingDescription. If seen, contact authorities immediately. #MissingPerson #Emergency"
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share Emergency Alert"))
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Share to Social Media", fontWeight = FontWeight.Bold)
                    }
                    
                    if (notifyAuthorities) {
                        Button(
                            onClick = { /* Mock API call to police */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Icon(Icons.Default.LocalPolice, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Direct Report to Police", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
