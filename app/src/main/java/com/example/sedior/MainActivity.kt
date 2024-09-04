package com.example.sedior

import DetailsScreen
import HomePage
import LoginScreen
import SplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sedior.ui.theme.SediorTheme
import com.example.sedior.utlisChars.Screens
import com.example.sedior.viewmodel.CharacterViewmodel

class MainActivity : ComponentActivity() {
    private lateinit var yourViewModel: CharacterViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            SediorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val myApplication = application as MyApplication
                    yourViewModel = myApplication.characterViewmodel            MyAppNavHost(navController = navController, viewModel = yourViewModel)
                }
            }
        }
    }


}

@Composable
private fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CharacterViewmodel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen (navController = navController)
        }
        composable(route = Screens.loginScreen.route) {
            LoginScreen (navController = navController, viewmodel = viewModel)
        }
        composable(route = Screens.homeScreen.route) {
            HomePage (navController = navController, viewmodel = viewModel)
        }
        composable(route = Screens.Details.route) {
            DetailsScreen (navController = navController, viewmodel = viewModel)
        }

    }
}