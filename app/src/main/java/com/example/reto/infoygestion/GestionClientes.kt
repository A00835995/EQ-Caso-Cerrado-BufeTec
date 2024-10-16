package com.example.reto.infoygestion
//Vanessa Juarez A00834795
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.reto.ui.theme.MediumBlue
import com.example.reto.ui.theme.TecBlue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.firebase.database.ktx.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

@Composable
fun GestionClientesScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }

    val clientList = remember { mutableStateListOf<Client>() }

    LaunchedEffect(Unit) {
        loadClientsFromFirebase(clientList)
    }

    // Filtrar los clientes según la búsqueda
    val filteredClients = clientList.filter { client ->
        client.clientName.contains(searchText.text, ignoreCase = true) ||
                client.number.contains(searchText.text, ignoreCase = true) ||
                client.email.contains(searchText.text, ignoreCase = true) ||
                client.tramite.contains(searchText.text, ignoreCase = true) ||
                client.folio.contains(searchText.text, ignoreCase = true) ||
                client.id.contains(searchText.text, ignoreCase = true)
    }

    // Ordenar los clientes por el ID (No.)
    val sortedClients = filteredClients.sortedBy { it.id.toIntOrNull() ?: 0 }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header de ejemplo
        ClientesHeader(title = "Clientes pag")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nuestros Clientes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MediumBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(searchText) { newText -> searchText = newText }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de agregar cliente
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = TecBlue)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Cliente", tint = Color.White)
                Text(text = "Agregar Cliente", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(0.05.dp))

        if (showDialog) {
            AddClientDialog(
                onDismiss = { showDialog = false },
                onAddClient = { newClient ->
                    saveClientsToFirebase(newClient)
                    showDialog = false
                },
                clientList = clientList
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            // Scroll horizontal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                // Scroll vertical
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                ) {
                    // Encabezados
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TableHeader("No.", Modifier.width(50.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Nombre", Modifier.width(150.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Número", Modifier.width(120.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Correo", Modifier.width(180.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Trámite", Modifier.width(200.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Expediente y Juzgado", Modifier.width(220.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Seguimiento", Modifier.width(200.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Alumno", Modifier.width(100.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Folio", Modifier.width(100.dp), backgroundColor = MediumBlue, textColor = Color.White)
                            TableHeader("Última vez informada", Modifier.width(180.dp), backgroundColor = MediumBlue, textColor = Color.White)
                        }
                    }

                    // Fila de datos filtrada y ordenada por ID
                    items(sortedClients) { client ->
                        ClientRow(client = client)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}


@Composable
fun ClientesHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(TecBlue),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ClientRow(client: Client) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(client.id, Modifier.width(50.dp))
        TableCell(client.clientName, Modifier.width(150.dp))
        TableCell(client.number, Modifier.width(120.dp))
        TableCell(client.email, Modifier.width(180.dp))
        TableCell(client.tramite, Modifier.width(200.dp))
        TableCell(client.expediente, Modifier.width(220.dp))
        TableCell(client.seguimiento, Modifier.width(200.dp))
        TableCell(client.alumno, Modifier.width(100.dp))
        TableCell(client.folio, Modifier.width(100.dp))
        TableCell(client.lastUpdated, Modifier.width(180.dp))
    }
}

@Composable
fun TableCell(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun SearchBar(searchText: TextFieldValue, onTextChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onTextChange,
        label = { Text("Buscar en la tabla") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
fun TableHeader(text: String, modifier: Modifier, backgroundColor: Color, textColor: Color) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}


@Composable
fun AddClientDialog(onDismiss: () -> Unit, onAddClient: (Client) -> Unit, clientList: List<Client>) {
    var clientName by remember { mutableStateOf(TextFieldValue("")) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var tramite by remember { mutableStateOf(TextFieldValue("")) }
    var expediente by remember { mutableStateOf(TextFieldValue("")) }
    var seguimiento by remember { mutableStateOf(TextFieldValue("")) }
    var alumno by remember { mutableStateOf(TextFieldValue("")) }
    var folio by remember { mutableStateOf(TextFieldValue("")) }
    var lastUpdated by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Agregar Cliente", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = clientName, onValueChange = { clientName = it }, label = { Text("Nombre") })
                OutlinedTextField(value = number, onValueChange = { number = it }, label = { Text("Número") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
                OutlinedTextField(value = tramite, onValueChange = { tramite = it }, label = { Text("Trámite") })
                OutlinedTextField(value = expediente, onValueChange = { expediente = it }, label = { Text("Expediente y Juzgado") })
                OutlinedTextField(value = seguimiento, onValueChange = { seguimiento = it }, label = { Text("Seguimiento") })
                OutlinedTextField(value = alumno, onValueChange = { alumno = it }, label = { Text("Alumno") })
                OutlinedTextField(value = folio, onValueChange = { folio = it }, label = { Text("Folio") })
                OutlinedTextField(value = lastUpdated, onValueChange = { lastUpdated = it }, label = { Text("Último informe en:") })

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        // Crear un nuevo cliente y agregarlo
                        val newClient = Client(
                            id = (clientList.size + 1).toString(),
                            clientName = clientName.text,
                            number = number.text,
                            email = email.text,
                            tramite = tramite.text,
                            expediente = expediente.text,
                            seguimiento = seguimiento.text,
                            alumno = alumno.text,
                            folio = folio.text,
                            lastUpdated = lastUpdated.text
                        )
                        onAddClient(newClient)
                    }) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}


// Función para guardar la lista de clientes en Firebase
fun saveClientsToFirebase(newClient: Client) {
    val database = Firebase.database
    val clientsRef = database.getReference("clientes")

    // Añadir nuevo cliente sin borrar los existentes
    val clientRef = clientsRef.push()
    clientRef.setValue(newClient).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Firebase", "Cliente agregado correctamente.")
        } else {
            Log.e("Firebase", "Error al agregar el cliente.", task.exception)
        }
    }
}


fun loadClientsFromFirebase(clientList: MutableList<Client>) {
    val database = Firebase.database
    val clientsRef = database.getReference("clientes")

    clientsRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            clientList.clear() // Limpiar la lista actual para no duplicar datos

            for (clientSnapshot in snapshot.children) {
                val client = clientSnapshot.getValue(Client::class.java)
                client?.let {
                    clientList.add(it) // Agregar cliente a la lista
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("Firebase", "Error al leer los datos.", error.toException())
        }
    })
}


data class Client(
    val id: String = "",
    val clientName: String = "",
    val number: String = "",
    val email: String = "",
    val tramite: String = "",
    val expediente: String = "",
    val seguimiento: String = "",
    val alumno: String = "",
    val folio: String = "",
    val lastUpdated: String = ""
)