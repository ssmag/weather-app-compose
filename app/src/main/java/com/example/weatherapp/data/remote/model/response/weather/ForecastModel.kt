package com.example.weatherapp.data.remote.model.response.weather

import com.google.gson.annotations.SerializedName

data class WeatherModel(

	@SerializedName("city")
	val city: City? = null,

	@SerializedName("cnt")
	val cnt: Int? = null,

	@SerializedName("cod")
	val cod: String? = null,

	@SerializedName("message")
	val message: Int? = null,

	@SerializedName("list")
	val list: List<DayOfForecast?>? = null
)

data class Coord(

	@SerializedName("lon")
	val lon: Any? = null,

	@SerializedName("lat")
	val lat: Any? = null
)

data class Wind(

	@SerializedName("deg")
	val deg: Int? = null,

	@SerializedName("speed")
	val speed: Any? = null,

	@SerializedName("gust")
	val gust: Any? = null
)

data class Clouds(

	@SerializedName("all")
	val all: Int? = null
)

data class WeatherItem(

	@SerializedName("icon")
	val icon: String? = null,

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("main")
	val main: String? = null,

	@SerializedName("id")
	val id: Int? = null
)

data class Sys(

	@SerializedName("pod")
	val pod: String? = null
)

data class City(

	@SerializedName("country")
	val country: String? = null,

	@SerializedName("coord")
	val coord: Coord? = null,

	@SerializedName("sunrise")
	val sunrise: Int? = null,

	@SerializedName("timezone")
	val timezone: Int? = null,

	@SerializedName("sunset")
	val sunset: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("population")
	val population: Int? = null
)

data class Main(

	@SerializedName("temp")
	val temp: Any? = null,

	@SerializedName("temp_min")
	val tempMin: Any? = null,

	@SerializedName("grnd_level")
	val grndLevel: Int? = null,

	@SerializedName("temp_kf")
	val tempKf: Any? = null,

	@SerializedName("humidity")
	val humidity: Int? = null,

	@SerializedName("pressure")
	val pressure: Int? = null,

	@SerializedName("sea_level")
	val seaLevel: Int? = null,

	@SerializedName("feels_like")
	val feelsLike: Any? = null,

	@SerializedName("temp_max")
	val tempMax: Any? = null
)

data class DayOfForecast(

	@SerializedName("dt")
	val dt: Int? = null,

	@SerializedName("pop")
	val pop: Double? = null,

	@SerializedName("visibility")
	val visibility: Int? = null,

	@SerializedName("dt_txt")
	val dtTxt: String? = null,

	@SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@SerializedName("main")
	val main: Main? = null,

	@SerializedName("clouds")
	val clouds: Clouds? = null,

	@SerializedName("sys")
	val sys: Sys? = null,

	@SerializedName("wind")
	val wind: Wind? = null
)
