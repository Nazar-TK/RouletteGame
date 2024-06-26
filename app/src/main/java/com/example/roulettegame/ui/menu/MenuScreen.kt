package com.example.roulettegame.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roulettegame.R
import com.example.roulettegame.presentation.utils.Routes
import com.example.roulettegame.ui.theme.Orange
import com.example.roulettegame.ui.theme.Yellow
import com.example.roulettegame.ui.theme.YellowLight
import com.example.roulettegame.ui.theme.fontFamily

@Composable
fun MenuScreen(navController: NavController) {

    Box(Modifier.fillMaxSize()){

        Image(painter = painterResource(
            id = R.drawable.app_background),
            contentDescription = "app background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(painter = painterResource(
                id = R.drawable.casino_menu_image),
                contentDescription = "menu image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp).height(400.dp).width(400.dp)
            )
            Button(onClick = { navController.navigate(Routes.GAME) },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                shape = CutCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 4.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                border = BorderStroke(5.dp, Yellow)
            ) {
                Text(
                    text = stringResource(id = R.string.play),
                    color = YellowLight,
                    fontSize = 40.sp,
                    fontFamily = fontFamily,
                    fontStyle = FontStyle.Normal,
                    )
            }
            Button(onClick = { navController.navigate(Routes.STORE) },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                shape = CutCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 4.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                border = BorderStroke(5.dp, Yellow)
            ) {
                Text(
                    text = stringResource(id = R.string.shop),
                    color = YellowLight,
                    fontSize = 40.sp,
                    fontFamily = fontFamily,
                    fontStyle = FontStyle.Normal,
                    )
            }
            Button(onClick = { navController.navigate(Routes.RESULTS) },
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                shape = CutCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 4.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                border = BorderStroke(5.dp, Yellow)
            ) {
                Text(
                    text = stringResource(id = R.string.results),
                    color = YellowLight,
                    fontSize = 40.sp,
                    fontFamily = fontFamily,
                    fontStyle = FontStyle.Normal,
                    )
            }
        }
    }
}