package nl.hva.madlevel6task1.ui.screens

import android.app.Application
import android.content.Context
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import nl.hva.madlevel6task1.R
import nl.hva.madlevel6task1.model.Game
import nl.hva.madlevel6task1.utils.Utils
import nl.hva.madlevel6task1.viewModel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController, viewModel : GameViewModel) {

    //The games variable holds the current list of games.
    val context = LocalContext.current
    val games = viewModel.gameBacklog
    val snackbarHostState = remember { SnackbarHostState() } // Needed for the Snackbar object.
    val scope = rememberCoroutineScope() // Also needed for the Snackbar object.
    //And the deletedBackLog indicator is used when the delete all button is selected by the user.
    // It serves as a trigger for displaying the Snackbar message.
    // So it needs to be switched on and off in your code.
    var deletedBacklog by remember { mutableStateOf(false) } // Switch to decide whether Snackbar "undo delete all" option is used.


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        backgroundColor = Color.DarkGray,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.headlineSmall, color = Color.White)
                        IconButton(onClick = {
                            // Game backlog should be cleared from the database  only after confirmation i.e."UNDO" option NOT
                            // selected on the "Snackbar". For that purpose we have introduced this switch.
                            deletedBacklog = true
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.snackbar_msg),
                                    actionLabel = context.getString(R.string.undo),
                                    duration = SnackbarDuration.Long
                                )
                                if (result != SnackbarResult.ActionPerformed) {
                                    // TODO: remove all games from the database!
                                    viewModel.deleteGameBacklog()
                                }
                                deletedBacklog = false
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete all games",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddGameScreen.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)

            if (!deletedBacklog) {
                Games(
                    context = context,
                    games,
                    modifier = Modifier.padding(16.dp),
                    snackbarHostState
                )
            }
        })
}

@Composable
fun Games(
    context : Context,
    games : LiveData<List<Game>>,
    modifier : Modifier,
    snackbarHostState : SnackbarHostState
) {
    val gamesState by games.observeAsState()

    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        gamesState?.sortedBy { it.release }.let { games ->
            if (games != null) {
                itemsIndexed(
                    items = games,
                    key = { _, game -> game.hashCode() }
                ) { _, game ->
                    GameCard(context, game = game, snackbarHostState = snackbarHostState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameCard(
    context : Context,
    game : Game,
    viewModel : GameViewModel = viewModel(),
    snackbarHostState : SnackbarHostState
) {

    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(DismissDirection.StartToEnd) || dismissState.isDismissed(
            DismissDirection.EndToStart
        )
    ) {
        LaunchedEffect(Unit) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.deleted_game, game.title),
                actionLabel = context.getString(R.string.undo),
                duration = SnackbarDuration.Short
            )
            if (result != SnackbarResult.ActionPerformed) {
                // TODO: remove the game from the database!!
                viewModel.deleteGame(game)
            } else {
                dismissState.reset()
            }
        }
    }

    SwipeToDismiss(
        state = dismissState,
        background = {},
        dismissContent = {
            Card{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = game.title, 
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(text = game.platform)
                        Text(text = "Release: " + Utils.dateToString(game.release))

                    }
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
    )
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController(), viewModel = GameViewModel(Application()))
}