package com.pierretest.gameshopapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pierretest.gameshopapp.data.model.gamesModel.MinimumSystemRequirementsModel
import com.pierretest.gameshopapp.data.model.gamesModel.ScreenshotModel

class Converters {
    @TypeConverter
    fun fromString(value: String?): MinimumSystemRequirementsModel? {
        return value?.let {
            Gson().fromJson(it, MinimumSystemRequirementsModel::class.java)
        }
    }

    @TypeConverter
    fun toString(value: MinimumSystemRequirementsModel?): String? {
        return value?.let {
            Gson().toJson(it, MinimumSystemRequirementsModel::class.java)
        }
    }

    @TypeConverter
    fun fromScreenshotList(screenshotList: List<ScreenshotModel>?): String? {
        if (screenshotList == null) {
            return null
        }
        return Gson().toJson(screenshotList)
    }

    // Convert a JSON string to a List of ScreenshotModel
    @TypeConverter
    fun toScreenshotList(screenshotListString: String?): List<ScreenshotModel>? {
        if (screenshotListString.isNullOrEmpty()) {
            return null
        }
        return Gson().fromJson(screenshotListString, object : TypeToken<List<ScreenshotModel>>() {}.type)
    }
}