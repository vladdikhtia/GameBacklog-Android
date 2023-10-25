package nl.hva.madlevel6task1.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.hva.madlevel6task1.R
import nl.hva.madlevel6task1.viewModel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(navController : NavController, viewModel : GameViewModel) {
    val context = LocalContext.current

    var title by remember {
        mutableStateOf("")
    }
    var platform by remember {
        mutableStateOf("")
    }
    var day by remember {
        mutableStateOf("")
    }
    var month by remember {
        mutableStateOf("")
    }
    var year by remember {
        mutableStateOf("")
    }


    Scaffold(
        containerColor = Color.DarkGray,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.add_game),
                        color = Color.White
                    )
                },
                //Arrow back implementation.
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to homescreen",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            //TODO()
        },
        content = {innerPadding ->
            Modifier.padding(innerPadding)
        }
    )
}

@Preview
@Composable
fun PreviewAddGameScreen() {
    AddGameScreen(navController = rememberNavController(), viewModel = GameViewModel(Application()))
}