package com.example.projeto_integrador.domain.model

import java.io.IOException

class NoMoreItemsException(message: String): IOException(message)

class NetworkUnavailableException(message: String = "No network available :("): IOException(message)

class NoSearchResultsException(message: String = "No search results :(") : IOException(message)