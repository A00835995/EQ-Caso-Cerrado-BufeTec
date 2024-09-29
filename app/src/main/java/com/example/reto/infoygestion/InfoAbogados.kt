package com.example.reto.infoygestion
//Vanessa Juarez A00834795
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reto.R
import com.example.reto.ui.theme.Cyan
import com.example.reto.ui.theme.DarkestBlue
import com.example.reto.ui.theme.IceBlue
import com.example.reto.ui.theme.MediumBlue
import com.example.reto.ui.theme.TecBlue

@Composable
fun InfoAbogadosScreen() {
    // Header de ejemplo xd
    Header(title = "Pag info abogados")

    Spacer(modifier = Modifier.height(100.dp))

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Nuestros Abogados",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MediumBlue
            )

            Text(
                text = "Si buscas asesoría legal personalizada, revisa la información de contacto presionando sobre el abogado especializado en el área que necesites.",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = DarkestBlue
            )

            AbogadoCard(
                name = "Mtra. Vibiana Agramont",
                description = "Especialista en Derecho Civil y Mercantil",
                imageId = R.drawable.mtra_vibiana_agramont,
                contactInfo = "Contacto: 987-654-3210, vibiana@example.com"
            )
            AbogadoCard(
                name = "Mtro. Manolo Martinez",
                description = "Especialista en Derecho Familiar",
                imageId = R.drawable.mtro_manolo_martinez,
                contactInfo = "Contacto: 555-444-3333, manolo@example.com"
            )
            AbogadoCard(
                name = "Lic. Veronica Gonzalez",
                description = "Especialista en Derecho Familiar",
                imageId = R.drawable.lic_veronica_gonzalez,
                contactInfo = "Contacto: 123-456-7890, veronica@example.com"
            )
        }
    }
}
@Composable
fun Header(title: String) {
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
fun AbogadoCard(name: String, description: String, imageId: Int, contactInfo: String) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { showDialog = true },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = IceBlue,
            contentColor = DarkestBlue
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "$name Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = description, fontSize = 16.sp, color = Color.DarkGray)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = name) },
            text = { Text(text = contactInfo) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Cyan,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InfoAbogadosScreenPreview() {
    InfoAbogadosScreen()
}

