package com.example.reto.procesosLegales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(itemTitle: String, onBackClick: () -> Unit) {
    // Definir una descripción personalizada más larga para cada proceso legal
    val description = when (itemTitle) {
        "Penales" -> """
            En Buffetec te podemos ayudar en el proceso legal de Penales, donde defendemos tus derechos en casos graves como homicidios, robos y delitos contra la integridad física o moral.
            Nuestro equipo de abogados especializados en derecho penal te acompañará durante todo el proceso, asegurando una defensa sólida en cada etapa del juicio.
            Nos aseguramos de que se respeten tus derechos humanos y te brindamos asesoría legal en situaciones de alto impacto.
            Si necesitas asistencia legal, por favor haz clic en el botón de abajo para mandar un mensaje.
        """
        "Civiles" -> """
            En Buffetec te podemos ayudar en el proceso legal de Civiles para resolver disputas entre personas o entidades privadas de manera justa y legal.
            Ya sea que se trate de contratos, daños y perjuicios, propiedad o cualquier otra cuestión civil, nos enfocamos en ofrecer soluciones eficientes y justas para nuestros clientes.
            Nuestro objetivo es resolver los conflictos de manera pacífica y lograr acuerdos que beneficien a ambas partes involucradas.
            Si necesitas asistencia legal, por favor haz clic en el botón de abajo para mandar un mensaje.
        """
        "Laborales" -> """
            En Buffetec te podemos ayudar en el proceso legal de Laborales, donde representamos tanto a trabajadores como empleadores en disputas laborales.
            Ya sea por despidos injustificados, acoso laboral, o problemas con contratos, te ofrecemos asesoría legal personalizada.
            Nos aseguramos de que tus derechos como trabajador o empleador sean respetados en todo momento, buscando soluciones justas y equitativas.
            Si necesitas asistencia legal, por favor haz clic en el botón de abajo para mandar un mensaje.
        """
        "Familiares" -> """
            En Buffetec te podemos ayudar en el proceso legal de Familiares, abarcando una amplia gama de asuntos como divorcios, custodia de hijos, adopciones y más.
            Entendemos la sensibilidad de estos temas y ofrecemos un enfoque compasivo y profesional para resolver conflictos familiares.
            Trabajamos con cada cliente para asegurarnos de que se logren acuerdos que sean en el mejor interés de todas las partes involucradas, especialmente cuando hay niños.
            Si necesitas asistencia legal, por favor haz clic en el botón de abajo para mandar un mensaje.
        """
        "Administrativos" -> """
            En Buffetec te podemos ayudar en el proceso legal de Administrativos, donde asistimos en la resolución de conflictos entre los ciudadanos y las entidades gubernamentales.
            Ya sea que se trate de una disputa relacionada con permisos, regulaciones o multas administrativas, te brindamos asesoría experta para garantizar que tus derechos sean defendidos frente a las instituciones gubernamentales.
            Nuestro equipo está altamente capacitado en derecho administrativo y conoce los procedimientos legales para impugnar decisiones injustas.
            Si necesitas asistencia legal, por favor haz clic en el botón de abajo para mandar un mensaje.
        """
        else -> "En Buffetec te podemos ayudar con diferentes procesos legales, adaptándonos a tus necesidades y brindándote asesoría especializada en cada área del derecho."
    }

    Scaffold(
        topBar = {
            // Barra superior azul con el título dinámico
            TopAppBar(
                title = { Text(text = itemTitle, color = Color.White, fontSize = 20.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2541B2)),
                navigationIcon = {
                    // Botón de retroceso
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2541B2) // Azul oscuro
                )
            )
        },
        bottomBar = {
            // Botón en la parte inferior
            Button(
                onClick = { /* Acción para mandar mensaje */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C4CC)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Mandar Mensaje", color = Color.White, fontSize = 16.sp)
            }
        }
    ) { paddingValues ->
        // Columna que contiene la descripción detallada
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cuadro de texto gris con bordes redondeados
            Box(
                modifier = Modifier
                    .background(Color(0xFFD9E3F0), shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth(0.95f)
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "¿Cómo te podemos ayudar?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2541B2),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = description.trimIndent(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(itemTitle = "Penales", onBackClick = {})
}