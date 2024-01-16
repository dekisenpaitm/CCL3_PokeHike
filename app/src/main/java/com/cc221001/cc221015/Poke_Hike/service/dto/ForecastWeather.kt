package com.cc221001.cc221015.Poke_Hike.service.dto


import com.google.gson.annotations.SerializedName

data class ForecastWeather(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<Forecast>,
    @SerializedName("message")
    val message: Int
) {
    data class Forecast (
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("dt_txt")
        val dtTxt: String,
        @SerializedName("main")
        val main: MainForecast,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("rain")
        val rain: Rain,
        @SerializedName("sys")
        val sys: SysForecast,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weather: List<WeatherForecast>,
        @SerializedName("wind")
        val wind: Wind
    ) {
        data class MainForecast(
            @SerializedName("feels_like")
            val feelsLike: Double,
            @SerializedName("grnd_level")
            val grndLevel: Int,
            @SerializedName("humidity")
            val humidity: Int,
            @SerializedName("pressure")
            val pressure: Int,
            @SerializedName("sea_level")
            val seaLevel: Int,
            @SerializedName("temp")
            val temp: Double,
            @SerializedName("temp_kf")
            val tempKf: Double,
            @SerializedName("temp_max")
            val tempMax: Double,
            @SerializedName("temp_min")
            val tempMin: Double
        )
        data class Clouds(
            @SerializedName("all")
            val all: Int
        )
            data class Wind(
                @SerializedName("speed")
                val speed: Double,
                @SerializedName("deg")
                val deg: Int,
                @SerializedName("gust")
                val gust: Double
            )
    }
    data class WeatherForecast(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
    ) {

    }
}