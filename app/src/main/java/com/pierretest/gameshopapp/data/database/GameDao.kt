package com.pierretest.gameshopapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getAllGames() : List<GameEntity>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameBydId(gameId : Int) : GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameEntity)

    @Delete
    fun deleteGame(game: GameEntity)

}