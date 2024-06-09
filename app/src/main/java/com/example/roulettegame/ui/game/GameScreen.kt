package com.example.roulettegame.ui.game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roulettegame.ui.theme.YellowLight

@Composable
fun GameScreen() {
    Text(
        text = "PLAY",
        color = YellowLight,
        fontSize = 40.sp,
        )
}
