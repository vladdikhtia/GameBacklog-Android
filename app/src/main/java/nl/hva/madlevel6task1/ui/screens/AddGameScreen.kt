package nl.hva.madlevel6task1.ui.screens

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.hva.madlevel6task1.R
import nl.hva.madlevel6task1.model.Game
import nl.hva.madlevel6task1.utils.Utils
import nl.hva.madlevel6task1.viewModel.GameViewModel
import java.util.Date

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
            FloatingActionButton(onClick = {
                val newGame = verifyInputAndCorrect(context, title, platform, day, month, year)
                if (newGame.title.isNotEmpty()) {
                    // TODO: insert the new game into the database!
                    viewModel.insertGame(newGame)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save")
            }
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)
            Column(
                modifier = Modifier
                    .padding(top = 80.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .fillMaxSize()
            ) {
                Card {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = "Title",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth()
                        )
                        CustomTextField(
                            value = platform,
                            onValueChange = { platform = it },
                            label = "Platform",
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            CustomTextField(
                                value = day,
                                onValueChange = { if (day.length <= 2) day = it },
                                label = "Day",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(0.33f)
                            )
                            CustomTextField(
                                value = month,
                                onValueChange = { if (month.length <= 2) month = it },
                                label = "Month",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(0.33f)
                            )
                            CustomTextField(
                                value = year,
                                onValueChange = { if (year.length <= 4) year = it },
                                label = "Year",
                                keyboardType = KeyboardType.Number,
                                modifier = Modifier.weight(0.33f)
                            )
                        }

                    }
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    keyboardType : KeyboardType,
    modifier : Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        ),
        singleLine = true,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

fun verifyInputAndCorrect(
    context : Context,
    title : String,
    platform : String,
    day : String,
    month : String,
    year : String
) : Game {
    var gameTitle = ""
    var gamePlatform = ""
    var releaseDay = "01"
    val releaseMonth : String
    val releaseYear : String
    var gameReleaseDate = Date()
    var resultingGame = Game(gameTitle, gamePlatform, gameReleaseDate)

    if (title.isEmpty()) {
        Toast.makeText(context, R.string.empty_title, Toast.LENGTH_SHORT).show()
        return resultingGame
    } else {

        if (day.isNotEmpty()) {
            if (day.length == 1) releaseDay = "0" + day else releaseDay = day
        }

        if (month.isEmpty()) {
            Toast.makeText(context, R.string.empty_month, Toast.LENGTH_SHORT).show()
            return resultingGame
        } else {
            if (month.length == 1) releaseMonth = "0" + month else releaseMonth = month
            if (releaseMonth < "01" || releaseMonth > "12") {
                Toast.makeText(context, R.string.wrong_month, Toast.LENGTH_SHORT).show()
                return resultingGame
            }
            if (year.length < 4) {
                Toast.makeText(context, R.string.wrong_year, Toast.LENGTH_SHORT).show()
                return resultingGame
            } else {
                releaseYear = year
                // Verify date
                try {
                    gameReleaseDate =
                        Utils.dayMonthYearToDate(releaseDay, releaseMonth, releaseYear)!!
                } catch (e : NumberFormatException) {
                    Toast.makeText(context, R.string.wrong_date, Toast.LENGTH_SHORT).show()
                    return resultingGame
                } catch (e : Exception) {
                    Toast.makeText(context, R.string.wrong_date, Toast.LENGTH_SHORT).show()
                    return resultingGame
                }
            }

        }

    }
    gameTitle = title
    gamePlatform = platform
    resultingGame = Game(gameTitle, gamePlatform, gameReleaseDate)
    return resultingGame
}

@Preview
@Composable
fun PreviewAddGameScreen() {
    AddGameScreen(navController = rememberNavController(), viewModel = GameViewModel(Application()))
}