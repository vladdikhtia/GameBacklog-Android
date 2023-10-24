package nl.hva.madlevel6task1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.madlevel6task1.model.Game
import nl.hva.madlevel6task1.repository.GameRepository

class GameViewModel(application : Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val gameBacklog =
        gameRepository.getGames() // LiveData object exposes the games from the room database.

    /**
     * Insert a game into the repository.
     */
    fun insertGame(game : Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insert(game)
            }
        }
    }

    /**
     * Delete a game from the repository.
     */
    fun deleteGame(game : Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.delete(game)
            }
        }
    }

    /**
     * Delete the game backlog from the repository.
     */
    fun deleteGameBacklog() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAll()
            }
        }
    }


}

//Dispatchers.Main: Main thread on Android, interact with the UI and perform light work.
//Dispatchers.IO: Optimized for disk and network IO.