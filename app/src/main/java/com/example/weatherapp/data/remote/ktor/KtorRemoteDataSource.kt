package com.example.weatherapp.data.remote.ktor

import com.example.weatherapp.data.remote.model.RequestMethod
import com.example.weatherapp.data.remote.model.RequestModel
import com.example.weatherapp.data.remote.model.ForecastRequest
import com.example.weatherapp.data.remote.model.response.weather.WeatherResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getForecast(zipCode: String): WeatherResponseModel {
        return makeRequest<WeatherResponseModel>(
            requestModel = ForecastRequest(
                queryParams = mapOf(ZIP_KEY to zipCode),
            )
        )
    }

    fun getForecastFlow(zipCode: String): Flow<WeatherResponseModel?> = flow { getForecast(zipCode) }


    private suspend inline fun <reified T> makeRequest(requestModel: RequestModel): T {
        return client.request(urlString = requestModel.url) {
            setMethod(requestModel.method)
            setHeaders(requestModel.headers)
            setQueryParams(mapOf(API_SECRET_KEY to API_SECRET_VALUE))
            setQueryParams(requestModel.queryParams)
            requestModel.body?.let { body ->
                setBody(body)
            }
        }.body()
    }

    private fun HttpRequestBuilder.setMethod(method: RequestMethod) {
        this@setMethod.method = when (method) {
            RequestMethod.POST -> HttpMethod.Post
            RequestMethod.GET -> HttpMethod.Get
            RequestMethod.PUT -> HttpMethod.Put
            RequestMethod.UPDATE -> HttpMethod.Patch
            RequestMethod.DELETE -> HttpMethod.Delete
        }
    }

    private fun HttpRequestBuilder.setHeaders(headers: Map<String, String>) {
        headers.forEach { (key, value) ->
            header(key, value)
        }
    }

    private fun HttpRequestBuilder.setQueryParams(queryParams: Map<String, String>) {
        url {
            queryParams.forEach { (key, value) ->
                parameters.append(key, value)
            }
        }
    }

    companion object {
        private const val API_SECRET_KEY = "appid"
        private const val API_SECRET_VALUE = "1d5608c08a64d6322cae078afa472927"
        private const val ZIP_KEY = "zip"
    }

}