package com.example.roulettegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roulettegame.presentation.utils.Routes
import com.example.roulettegame.ui.game.GameScreen
import com.example.roulettegame.ui.menu.MenuScreen
import com.example.roulettegame.ui.results.ResultsScreen
import com.example.roulettegame.ui.theme.RouletteGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

         setContent {
            RouletteGameTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.MENU
                ) {
                    composable(Routes.MENU) {
                        MenuScreen(navController = navController)
                    }
                   composable(Routes.GAME) {
                        GameScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                    composable(Routes.RESULTS) {
                        ResultsScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}