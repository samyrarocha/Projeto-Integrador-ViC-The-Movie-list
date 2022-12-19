package com.example.projeto_integrador.domain.uttils


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.projeto_integrador.presentation.feed.AllMoviesEvent
import com.example.projeto_integrador.presentation.feed.AllMoviesViewState
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