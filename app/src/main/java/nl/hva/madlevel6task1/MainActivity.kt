package nl.hva.madlevel6task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.hva.madlevel6task1.ui.screens.AddGameScreen
import nl.hva.madlevel6task1.ui.screens.HomeScreen
import nl.hva.madlevel6task1.ui.screens.Screen
import nl.hva.madlevel6task1.ui.theme.MadLevel6Task1Theme
import nl.hva.madlevel6task1.viewModel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MadLevel6Task1Theme {
                GameApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameApp() {
    val navController = rememberNavController()

    Scaffold { innerPadding -> GameNavHost(Modifier.padding(innerPadding), navController) }
}

@Composable
fun GameNavHost(modifier : Modifier = Modifier, navController : NavHostController) {

    val viewModel : GameViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AddGameScreen.route) {
            AddGameScreen(navController = navController, viewModel = viewModel)
        }
    }
}

