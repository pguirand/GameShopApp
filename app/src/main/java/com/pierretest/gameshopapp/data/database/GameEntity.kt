package com.pierretest.gameshopapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pierretest.gameshopapp.data.model.gamesModel.MinimumSystemRequirementsModel
import com.pierretest.gameshopapp.data.model.gamesModel.ScreenshotModel


@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id:Int,
    val title : String,
    val thumbnail : String,
    val description: String? = "",
    val developer: String? = "",
    val freetogameProfileUrl: String? = "",
    val gameUrl: String? = "",
    val genre: String? = "",
    val minimumSystemRequirements: MinimumSystemRequirementsModel? = MinimumSystemRequirementsModel(),
    val platform: String? = "",
    val publisher: String? = "",
    val releaseDate: String? = "",
    val screenshots: List<ScreenshotModel>? = listOf(),
    val shortDescription: String? = "",
    val status: String? = "",
)
