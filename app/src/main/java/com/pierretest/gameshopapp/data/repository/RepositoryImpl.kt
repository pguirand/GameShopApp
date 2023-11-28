package com.pierretest.gameshopapp.data.repository

import com.pierretest.gameshopapp.data.database.GameDao
import com.pierretest.gameshopapp.data.database.GameEntity
import com.pierretest.gameshopapp.data.model.gamesModel.MinimumSystemRequirementsModel
import com.pierretest.gameshopapp.data.model.gamesModel.ScreenshotModel
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel
import com.pierretest.gameshopapp.data.remote.ApiCall

class RepositoryImpl(val apiCall: ApiCall, val gameDao: GameDao) : Repository {


/*

    //In-memory cache for single games
    private val singleGameCache = mutableMapOf<Int, SingleGameModel>()


    override suspend fun getSingleGameById(idGame: Int): SingleGameModel {

        //check if game is already in cache
        singleGameCache[idGame]?.let {
            return it
        }

        //Data not found in cache
        val singleGame = apiCall.getSingleGameById(idGame)
        singleGameCache[idGame] = singleGame

        return singleGame
    }

    }*/

    //Room Database Caching
    override suspend fun getSingleGameById(idGame: Int): SingleGameModel {

        //check if game is already in room database
        val cachedGame = gameDao.getGameBydId(idGame)
        if (cachedGame!=null) {
            return mapGameEntityToModel(cachedGame)
        }

        //Data not found in room, fetch from API
        val apiSingleGame = apiCall.getSingleGameById(idGame)

        //Update Room Database
        val gameEntity = mapGameModelToEntity(apiSingleGame)
        if (gameEntity != null) {
            gameDao.insertGame(gameEntity)
        }

        return apiSingleGame
    }




    override suspend fun getAllGames(): List<SingleGameModel> {
        return apiCall.getAllGames()
    }

    override suspend fun getAllCategories(): List<String?> {

        val allGames = apiCall.getAllGames()
        val groupedNames = allGames.groupBy { it.genre?.trim() }
        return groupedNames.keys.toList()

    }

    override suspend fun getCategoryByName(catName: String): List<SingleGameModel> {
        return apiCall.getCategoryByName(catName)
    }

    private fun mapGameModelToEntity(gameModel: SingleGameModel): GameEntity? {
        return gameModel.id?.let {
            gameModel.title?.let { it1 ->
                gameModel.thumbnail?.let { it2 ->
                    GameEntity(
                        id = it,
                        title = it1,
                        thumbnail = it2,
                        description = gameModel.description,
                        developer = gameModel.developer,
                        // Map other properties...
                        freetogameProfileUrl = gameModel.freetogameProfileUrl,
                        gameUrl = gameModel.gameUrl,
                        genre = gameModel.genre,
                        minimumSystemRequirements = gameModel.minimumSystemRequirements,
                        platform = gameModel.platform,
                        publisher = gameModel.publisher,
                        releaseDate = gameModel.releaseDate,
                        screenshots = gameModel.screenshots,
                        shortDescription = gameModel.shortDescription,
                        status = gameModel.status
                    )
                }
            }
        }
    }

    private fun mapGameEntityToModel(gameEntity: GameEntity): SingleGameModel {
        return SingleGameModel(
            id = gameEntity.id,
            title = gameEntity.title,
            thumbnail = gameEntity.thumbnail,
            description = gameEntity.description,
            developer = gameEntity.developer,
            freetogameProfileUrl = gameEntity.freetogameProfileUrl,
            gameUrl = gameEntity.gameUrl,
            genre = gameEntity.genre,
            minimumSystemRequirements = gameEntity.minimumSystemRequirements,
            platform = gameEntity.platform,
            publisher = gameEntity.publisher,
            releaseDate = gameEntity.releaseDate,
            screenshots = gameEntity.screenshots,
            shortDescription = gameEntity.shortDescription,
            status = gameEntity.status
        )
    }

}

