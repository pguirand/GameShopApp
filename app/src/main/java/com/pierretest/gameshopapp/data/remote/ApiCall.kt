package com.pierretest.gameshopapp.data.remote

import com.pierretest.gameshopapp.common.ApiDetails
import com.pierretest.gameshopapp.data.model.gamesModel.SingleCategoryModel
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCall {

    @GET(ApiDetails.SINGLE_END_POINT)
    suspend fun getSingleGameById(@Query("id") id:Int) : SingleGameModel

    @GET(ApiDetails.ALL_GAMES_END_POINT)
    suspend fun getAllGames() : List<SingleGameModel>

    @GET(ApiDetails.ALL_CATEGORIES_END_POINT)
    suspend fun getCategoryByName(@Query("category") catName:String) : List<SingleGameModel>


}