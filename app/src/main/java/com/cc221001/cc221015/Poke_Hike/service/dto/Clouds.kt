package com.cc221001.cc221015.Poke_Hike.service.dto


import com.google.gson.annotations.SerializedName

/**
 * Data class representing cloud information in the response from the OpenWeatherMap API.
 *
 * @property all Cloudiness percentage.
 */
data class Clouds(
    @SerializedName("all")
    val all: Int
)