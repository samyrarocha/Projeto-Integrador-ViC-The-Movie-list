package com.example.projeto_integrador.domain.model

import java.io.IOException

class NoMoreMoviesException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)