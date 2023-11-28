package com.pierretest.gameshopapp.data.repository

import com.pierretest.gameshopapp.data.model.gamesModel.SingleCategoryModel
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel

interface Repository {

    suspend fun getSingleGameById(idGame:Int) : SingleGameModel

    suspend fun getAllGames() : List<SingleGameModel>

    suspend fun getAllCategories() : List<String?>

    suspend fun getCategoryByName(catName:String) : List<SingleGameModel>

}