package com.trabalho.elixo.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.trabalho.elixo.ui.theme.GreenPrimary
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.trabalho.elixo.ui.theme.White

data class ItemMonetizavel(
    val nome: String,
    val valor: Double,
    var quantidade: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonetizacaoScreen(onBack: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }
    var valorResgate by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    var itens by remember {
        mutableStateOf(
            listOf(
                ItemMonetizavel("Monitor", 15.0),
                ItemMonetizavel("CPU", 20.0),
                ItemMonetizavel("Notebook", 30.0),
                ItemMonetizavel("Teclado", 5.0)
            )
        )
    }

    var saldo by remember { mutableStateOf(0.0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ganhe com Reciclagem", fontWeight = FontWeight.Bold)
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

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GreenPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(
                                "Saldo estimado", color = White
                            )

                            Text(
                                "R$ %.2f".format(saldo),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium,
                                color = White
                            )
                        }

                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White
                            )
                        ) {
                            Text("Resgatar", color = GreenPrimary)
                        }
                    }
                }
            }

            item {
                Text(
                    "Itens que você pode reciclar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(itens) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = GreenPrimary
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.nome, fontWeight = FontWeight.Bold)
                            Text("R$ %.2f".format(item.valor))
                        }

                        IconButton(
                            onClick = {
                                if (item.quantidade > 0) {
                                    itens = itens.map {
                                        if (it.nome == item.nome)
                                            it.copy(quantidade = it.quantidade - 1)
                                        else it
                                    }
                                }
                            }
                        ) {
                            Text("-")
                        }

                        Text("${item.quantidade}")

                        IconButton(
                            onClick = {
                                itens = itens.map {
                                    if (it.nome == item.nome)
                                        it.copy(quantidade = it.quantidade + 1)
                                    else it
                                }
                            }
                        ) {
                            Text("+")
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val totalSelecionado = itens.sumOf { it.quantidade }

                        if (totalSelecionado > 0) {

                            val valorTotal = itens.sumOf {
                                it.quantidade * it.valor
                            }

                            saldo += valorTotal

                            itens = itens.map { it.copy(quantidade = 0) }

                            showSuccessDialog = true
                        } else {
                            showErrorDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {
                    Text(
                        "Solicitar coleta", color = White
                    )
                }
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    Button(onClick = { showSuccessDialog = false },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                            )) {
                        Text("OK", color = White)
                    }
                },
                title = { Text("Sucesso ✅") },
                text = {
                    Text("Coleta solicitada com sucesso!")
                }
            )
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                            )) {
                        Text("OK", color = White)
                    }
                },
                title = { Text("Erro ❌") },
                text = {
                    Text("Selecione pelo menos um item para coleta.")
                }
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val valor = valorResgate.toDoubleOrNull() ?: 0.0

                            if (valor > 0 && valor <= saldo) {
                                saldo -= valor
                                showDialog = false
                                valorResgate = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                        )
                    ) {
                        Text("Confirmar", color = White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar", color = GreenPrimary)
                    }
                },
                title = {
                    Text("Resgatar saldo")
                },
                text = {
                    Column {
                        Text("Saldo disponível: R$ %.2f".format(saldo))

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = valorResgate,
                            onValueChange = { valorResgate = it },
                            label = { Text("Valor para resgatar") },
                            singleLine = true
                        )
                    }
                }
            )
        }
    }
}