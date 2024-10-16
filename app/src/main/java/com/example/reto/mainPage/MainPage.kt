package com.example.reto.mainPage

import UserViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reto.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

// Página principal
@Composable
fun MainPageContent(navController: NavController,
                    userViewModel: UserViewModel = viewModel(),
                    modifier: Modifier = Modifier) {

    // Llamar a fetchUserData() solo una vez cuando el Composable se carga
    LaunchedEffect(Unit) {
        userViewModel.fetchUserData()
    }

    // Obtenemos el rol del usuario desde el ViewModel
    val userRelation = userViewModel.userRelation.collectAsState().value

    //Imágenes del slider
    val images = listOf(
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4,
    )

    val pagerState = rememberPagerState()

    //Cambio automático del slider
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(180.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            HorizontalPager(
                state = pagerState,
                count = images.size, // Define cuántas páginas tiene el pager
                modifier = Modifier.fillMaxSize()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Indicador del slider
        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Botones de navegación
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            CircularNavigationButton(
                imageRes = R.drawable.lawyer,
                text = " Nuestros\nAbogados",
                onClick = {
                    navController.navigate("infoAbogados")
                }
            )

            // Mostrar el botón "Catálogo de clientes" solo si el usuario es Practicante o Colaborador
            if (userRelation == "Practicante" || userRelation == "Colaborador") {
                CircularNavigationButton(
                    imageRes = R.drawable.client,
                    text = " Catálogo\nde clientes",
                    onClick = {
                        navController.navigate("gestionClientes")
                    }
                )
            }

            CircularNavigationButton(
                imageRes = R.drawable.legal,
                text = "Procesos\n Legales",
                onClick = {
                    navController.navigate("ProcesosLegalesScreen")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            CircularNavigationButton(
                imageRes = R.drawable.forum,
                text = "Foro",
                onClick = {
                    navController.navigate("foro")
                }
            )

            CircularNavigationButton(
                imageRes = R.drawable.chat,
                text = "Chat AI",
                onClick = {
                    navController.navigate("chatbot")
                }
            )
            if(userRelation=="Colaborador")
            {
                CircularNavigationButton(
                    imageRes = R.drawable.me,
                    text = "Administrar",
                    onClick = {
                        navController.navigate("adminUsuarios")
                    }
                )
            }
        }
    }
}

// Indicador del slider
@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = Modifier)
        }
    }
}

// Los círculos del indicador
@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xBA373737))
    )
}

// Botones de navegación
@Composable
fun CircularNavigationButton(imageRes: Int, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, // Asegura que el contenido esté centrado verticalmente
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(Alignment.Center) // Centrar el contenido
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0x1D1D32CC))
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize() // Asegura que la imagen llene el espacio disponible
                    .padding(8.dp) // Añadir algo de padding si la imagen no se ajusta bien
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(max = 100.dp) // Controla el ancho del texto para evitar desbordes
                .align(Alignment.CenterHorizontally) // Mantiene el texto centrado horizontalmente
        )
    }
}
