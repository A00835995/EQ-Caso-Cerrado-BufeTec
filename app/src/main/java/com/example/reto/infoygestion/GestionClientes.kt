package com.example.reto.infoygestion

//Vanessa Juarez A00834795
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
import androidx.navigation.NavHostController
import com.example.reto.ui.theme.MediumBlue
import com.example.reto.ui.theme.TecBlue

@Composable
fun GestionClientesScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

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

        // Search Bar
        SearchBar(searchText) { newText ->
            searchText = newText
        }

        Spacer(modifier = Modifier.height(8.dp))

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

                    // Fila de datos
                    items(clientsList()) { client ->
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
fun SearchBar(searchText: TextFieldValue, onTextChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onTextChange,
        label = { Text("Buscar en la tabla") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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

data class Client(
    val id: String,
    val clientName: String,
    val number: String,
    val email: String,
    val tramite: String,
    val expediente: String,
    val seguimiento: String,
    val alumno: String,
    val folio: String,
    val lastUpdated: String
)

fun clientsList(): List<Client> {
    return listOf(
        Client("1", "Alex Lopéz Peniche", "8116287695", "alexlo@gmail.com", "Sucesorio Especial Testamentario", "Expediente 1875/2023, Juzgado 4º Familiar", "Pendiente cita para desglose de testamento", "Alumno A", "6133370", "01 de julio"),
        Client("2", "Diana Flores Luna", "8110567467", "n/a", "Juicio Oral de Alimentos", "Expediente 376/2023, Juzgado 2° Oral Familiar", "Monitorear modificación de sentencia", "Alumno B", "n/a", "22 de mayo"),
        Client("3", "Diana Flores Luna", "8110567467", "n/a", "Apelación del Juicio Oral de Alimentos", "Toca 165/2024, Quinta Sala Familiar", "Revisar estado procesal completo", "Alumno B", "n/a", "22 de mayo"),
        Client("4", "Emilio Tello Arcudia", "813258769", "elloemilio@gmail.com", "Convivencias", "Expediente 099/2023, Juzgado 7° Oral Familiar", "Pendiente respuesta de CEC para horario de convivencias", "Jesús Mata", "6133370", "01 de julio"),
        Client("5", "Brenda Judith Cortez Perez", "8115256614", "n/a", "Incidente de Modificación de Convenio de Convivencias", "Expediente 814/2020, Juzgado 9º Oral Familiar", "Juez desestima petición de suspensión de convivencias", "n/a", "n/a", "01 de julio"),
        Client("6", "Melissa Espinoza Paz", "8117666423", "n/a", "Ejecución de Sentencia", "Expediente 886/2020, Juzgado 6° Oral Familiar", "Monitorear oficio al IMSS y ratificación de desistimiento", "Rebeca", "n/a", "12 de agosto"),
        Client("7", "Liliana Cantu Pedraza", "8119787356", "n/a", "D.J.V. sobre Nombramiento de Tutor y Ad-Perpetuam", "Expediente 486/2024, Juzgado 5º Familiar", "Revisar estado procesal", "n/a", "n/a", "01 de julio"),
        Client("8", "Cristobal Avila Zavala", "8135998644", "cristizav@gmail.com", "Juicio Oral de Alimentos", "Expediente 352/2024, Juzgado 4° Oral Familiar en San Nicolás", "Pendiente audiencia preliminar y demanda de convivencias", "n/a", "n/a", "28 de junio"),
        Client("9", "Michelle Gonzalez Espinoza", "8110076301", "n/a", "Juicio Oral de Alimentos", "Expediente 1031/2023, Juzgado 3º Oral Familiar", "Pendiente revisar ejecución", "n/a", "n/a", "01 de julio"),
        Client("10", "Guadalupe Perez Mendoza", "8115828742", "guadaluperez@gmail.com", "Inscripción de Sentencia de Divorcio", "Expediente 1756/2022, Juzgado Primero Familiar Oral de Cadereyta", "Monitorear pagos de inscripción de sentencia", "n/a", "n/a", "n/a"),
        // Agregar el resto de los clientes de la lista...
    )
}

@Composable
fun GestionClientesScreenPreview(navController: NavHostController) {
    GestionClientesScreen(navController)
}