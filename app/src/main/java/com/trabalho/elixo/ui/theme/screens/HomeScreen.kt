package com.trabalho.elixo.ui.theme.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.trabalho.elixo.utils.LocationUtils
import com.trabalho.elixo.utils.DistanceUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.trabalho.elixo.R
import com.trabalho.elixo.data.locations
import com.trabalho.elixo.ui.theme.GreenPrimary
import com.trabalho.elixo.ui.theme.White
import com.trabalho.elixo.ui.theme.components.*
import com.trabalho.elixo.data.LocationModel
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToReciclagem: () -> Unit,
    onLocationClick: (LocationModel) -> Unit,
    onNavigateToMonetizacao: () -> Unit,
    onLogout: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLon by remember { mutableStateOf<Double?>(null) }
    var searchText by remember { mutableStateOf("") }

    val updatedLocations = remember(userLat, userLon) {
        if (userLat != null && userLon != null) {
            locations.map {
                it.copy(
                    distancia = DistanceUtils.calcularDistancia(
                        userLat!!,
                        userLon!!,
                        it.latitude,
                        it.longitude
                    )
                )
            }
        } else locations
    }

    val filteredLocations = updatedLocations.filter {
        it.nome.contains(searchText, ignoreCase = true) ||
                it.endereco.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.app_icon),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("TechCycle")
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(onSettingsClick = onNavigateToSettings,
                                onMonetizacaoClick = onNavigateToMonetizacao)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                Text(
                    "Pontos de Coleta Próximos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SearchBar(
                    text = searchText,
                    onTextChange = { searchText = it }
                )
            }

            items(filteredLocations) { location ->
                LocationCard(
                    location = location,
                    onClick = { onLocationClick(location) }
                )
            }

            item {
                Button(
                    onClick = {
                        locationUtils.getCurrentLocation { lat, lon ->
                            userLat = lat
                            userLon = lon
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text(
                        "Atualizar localização",
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                OutlinedButton(
                    onClick = onNavigateToReciclagem,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aprenda a Reciclar", color = GreenPrimary)
                }
            }
        }
    }
}