package com.ltimindtree.famconnector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ltimindtree.famconnector.ui.components.AppCard
import com.ltimindtree.famconnector.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            Column {
                SectionHeader("Privacy & Security")
                AppCard {
                    SettingsItem("Account Privacy", Icons.Default.Lock, "Manage who can see your family")
                    SettingsItem("Data Sharing", Icons.Default.Share, "Control AI similarity feedback")
                    SettingsItem("Location Settings", Icons.Default.MyLocation, "Customize alert range")
                }
            }

            Column {
                SectionHeader("App Settings")
                AppCard {
                    SettingsItem("Notifications", Icons.Default.Notifications, "Alerts and sighting updates")
                    SettingsItem("Data Retention", Icons.Default.AutoDelete, "Images auto-delete policy")
                    SettingsItem("Appearance", Icons.Default.Palette, "Dark mode & accents")
                }
            }

            Column {
                SectionHeader("Legal & Info")
                AppCard {
                    SettingsItem("About Family Connector", Icons.Default.Info, "Version 1.0.0 Stable")
                    SettingsItem("Ethical AI Policy", Icons.Default.Gavel, "Privacy-first vision similarity")
                    SettingsItem("Terms of Service", Icons.Default.Description, "Standard legal notice")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.DeleteForever, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Text("Delete Account & Purge Data", fontWeight = FontWeight.Bold)
            }

            Text(
                text = "Securely encrypted with end-to-end privacy for all family data.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SettingsItem(title: String, icon: ImageVector, subtitle: String? = null) {
    Surface(
        onClick = { /* TODO */ },
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                if (subtitle != null) {
                    Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
        }
    }
}
