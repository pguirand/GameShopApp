package com.pierretest.gameshopapp.data.model.gamesModel


import com.google.gson.annotations.SerializedName

data class SingleGameModel(
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("developer")
    val developer: String? = "",
    @SerializedName("freetogame_profile_url")
    val freetogameProfileUrl: String? = "",
    @SerializedName("game_url")
    val gameUrl: String? = "",
    @SerializedName("genre")
    val genre: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("minimum_system_requirements")
    val minimumSystemRequirements: MinimumSystemRequirementsModel? = MinimumSystemRequirementsModel(),
    @SerializedName("platform")
    val platform: String? = "",
    @SerializedName("publisher")
    val publisher: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("screenshots")
    val screenshots: List<ScreenshotModel>? = listOf(),
    @SerializedName("short_description")
    val shortDescription: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("thumbnail")
    val thumbnail: String? = "",
    @SerializedName("title")
    val title: String? = ""
)