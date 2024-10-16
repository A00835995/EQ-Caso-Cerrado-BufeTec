package com.example.reto.mainPage

import UserViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reto.R


@Composable
fun PerfilScreen(navController: NavController,
                 modifier: Modifier = Modifier,
                 userViewModel: UserViewModel = viewModel()) {
    // Llamar a fetchUserData() solo una vez cuando el Composable se carga
    LaunchedEffect(Unit) {
        userViewModel.fetchUserData()
    }

    // Obtenemos el rol del usuario desde el ViewModel
    val userName = userViewModel.userName.collectAsState().value
    val userMail = userViewModel.userEmail.collectAsState().value
    val userRelation = userViewModel.userRelation.collectAsState().value

    Column(
        modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout {
            val (topImg, profile) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.top_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.default_user),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(profile) {
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
        // Aquí va el nombre
        Text(
            text = userName,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = "Correo:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                //Aquí va el correo
                Text(
                    text = userMail,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = "Relación:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                //Aquí va la relación
                Text(
                    text = userRelation,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

    }
}



