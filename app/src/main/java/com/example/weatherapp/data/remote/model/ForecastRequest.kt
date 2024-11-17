package com.example.weatherapp.data.remote.model

data class ForecastRequest(
    override val body: Any? = null,
    override var method: RequestMethod = RequestMethod.GET,
    override val headers: Map<String, String> = mapOf(),
    override val url: String = "https://api.openweathermap.org/data/2.5/forecast",
    override val queryParams: Map<String, String> = mapOf(),
) : RequestModel(body)