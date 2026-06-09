package com.trabalho.elixo.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trabalho.elixo.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit,
                   isDarkMode: Boolean,
                   onThemeChange: (Boolean) -> Unit) {

    var locationEnabled by remember { mutableStateOf(true) }
    var showDialogSettings by remember { mutableStateOf(false) }
    var showDialogFeedback by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Configurações", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                "Ajuste preferências básicas do aplicativo.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GreenPrimary,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Localização", fontWeight = FontWeight.Bold)
                        Text("Permissão ativada", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Switch(
                        checked = locationEnabled,
                        onCheckedChange = { locationEnabled = it},
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GreenPrimary,
                            checkedTrackColor = GreenPrimary.copy(alpha = 0.3f),

                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = GreenPrimary,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text("Aparência", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        OutlinedButton(
                            onClick = { onThemeChange(false) },
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Claro", color = GreenPrimary)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(
                            onClick = { onThemeChange(true) },
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Escuro", color = GreenPrimary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = GreenPrimary,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text("Sobre", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Versão 1.0 • Política de privacidade disponível online.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Button(
                            onClick = { showDialogSettings = true},
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                        ) {
                            Text("Ver detalhes", color = White)
                        }

                       if(showDialogSettings) {
                           AlertDialog(
                               onDismissRequest = { showDialogSettings = false },
                               confirmButton = {
                                   Button(
                                       onClick = {
                                           showDialogSettings = false
                                       },
                                       colors = ButtonDefaults.buttonColors(
                                           containerColor = GreenPrimary
                                       )
                                   )
                                   { Text("Entendi", color = White) }
                                               },
                               title = {
                                   Text("Não temos Política no momento")
                               }
                           )
                       }
                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(onClick = {showDialogFeedback = true}) {
                            Text("Feedback", color = GreenPrimary)
                        }

                        if(showDialogFeedback) {
                            AlertDialog(
                                onDismissRequest = { showDialogFeedback = false },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            showDialogFeedback = false
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = GreenPrimary
                                        )
                                    )
                                    { Text("Entendi", color = White) }
                                },
                                title = {
                                    Text("Não aceitamos Feedback \uD83D\uDC4D")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}