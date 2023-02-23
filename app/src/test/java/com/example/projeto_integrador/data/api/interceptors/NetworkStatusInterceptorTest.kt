package com.example.projeto_integrador.data.api.interceptors

import com.example.projeto_integrador.data.api.models.ApiConstants
import com.example.projeto_integrador.data.api.models.ConnectionManager
import com.example.projeto_integrador.domain.model.NetworkUnavailableException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class NetworkStatusInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var connectionManager: ConnectionManager
    private lateinit var networkStatusInterceptor: NetworkStatusInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val getMoviesEndpoint = ApiConstants.BASE_ENDPOINT + ApiConstants.MOVIES_ENDPOINT

    @Before
    fun setup() {
        connectionManager = mock(ConnectionManager::class.java)
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        networkStatusInterceptor = NetworkStatusInterceptor(connectionManager)
        okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(networkStatusInterceptor)
            .build()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `networkStatusInterceptor should return isConnected true`() {
        //Given
        `when`(connectionManager.isConnected()).thenReturn(true)

        //When
        okHttpClient.newCall(Request.Builder().url(getMoviesEndpoint).build()).execute()

        //Then
        assertTrue(connectionManager.isConnected())
    }

    @Test
    fun `networkStatusInterceptor should return isConnected false`() {
        //Given
        val fakeNetworkUnavailableException = mock(NetworkUnavailableException::class.java)

        //When
        `when`(connectionManager.isConnected()).thenReturn(false)

        //Then
        verify(fakeNetworkUnavailableException)
    }

}