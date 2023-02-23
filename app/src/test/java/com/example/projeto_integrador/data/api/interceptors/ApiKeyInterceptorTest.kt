package com.example.projeto_integrador.data.api.interceptors

import com.example.projeto_integrador.BuildConfig
import com.example.projeto_integrador.data.api.models.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ApiKeyInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiKeyInterceptor: ApiKeyInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val getMoviesEndpoint = "/discover/movie?api_key=${BuildConfig.API_KEY}"


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        apiKeyInterceptor = ApiKeyInterceptor()

        okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `check GET Request`() {
        //Given
        val apiKeyQueryName = "api_key"
        val apiKeyValue = BuildConfig.API_KEY
        mockWebServer.dispatcher = getDispatcher()

        //When
        okHttpClient.newCall(
            Request.Builder()
                .url(mockWebServer.url(ApiConstants.DISCOVER_ENDPOINT))
                .addHeader(apiKeyQueryName, apiKeyValue)
                .build()
        ).execute()

        //Then
        val request = mockWebServer.takeRequest()

        with(request) {
            assertEquals(method, "GET")
            assertEquals(path, getMoviesEndpoint)
        }
    }

    private fun getDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                getMoviesEndpoint -> { MockResponse().setResponseCode(200) }
                else -> { MockResponse().setResponseCode(404) }
            }
        }
    }
}