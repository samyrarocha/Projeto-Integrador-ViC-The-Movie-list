package com.example.projeto_integrador.features.feed.uttils


import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


inline fun CoroutineScope.createExceptionHandler(
    message: Int,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
    launch {
        action(throwable)
    }
}