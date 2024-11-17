package com.example.weatherapp.data.remote.model

abstract class RequestModel(
    open val body: Any?
) {
    abstract var method: RequestMethod
    abstract val headers: Map<String, String>
    abstract val url: String
    abstract val queryParams: Map<String, String>
}


enum class RequestMethod {
    POST,
    GET,
    PUT,
    UPDATE,
    DELETE
}