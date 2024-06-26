package com.duyle.tutorkot104.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.duyle.tutorkot104.R
import com.duyle.tutorkot104.ui.theme.Pink40
import com.duyle.tutorkot104.ui.theme.navigation.ROUTE_SCREEN_NAME
import kotlinx.coroutines.delay

//@Preview
@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(Pink40)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            Modifier.size(200.dp)
        )

        Text(
            text = "PH32561",
            fontSize = 30.sp,
            fontWeight = FontWeight(500)
        )

        Text(
            text = "Nguyen Nhu Hieu",
            fontSize = 30.sp,
            fontWeight = FontWeight(500)

        )

        LaunchedEffect(Unit) {
            delay(2000L)
            navController.navigate(ROUTE_SCREEN_NAME.HOME.name) {
                popUpTo(ROUTE_SCREEN_NAME.WELCOME.name) { inclusive = true }
            }
        }
    }
}