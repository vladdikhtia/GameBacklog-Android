package nl.hva.madlevel6task1.repository

import android.content.Context
import nl.hva.madlevel6task1.database.GameDao
import nl.hva.madlevel6task1.database.GameRoomDatabase
import nl.hva.madlevel6task1.model.Game

//The class constructor takes a Context object
// because we need this to access the database.
class GameRepository(context : Context) {
    private val gameDao : GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun insert(game : Game) = gameDao.insert(game)
    suspend fun delete(game : Game) = gameDao.delete(game)
    fun getGames() = gameDao.getGames()

    suspend fun deleteAll() = gameDao.deleteAll()

}