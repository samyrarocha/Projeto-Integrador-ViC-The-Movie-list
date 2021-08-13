package com.example.projeto_integrador.features.search.presentation

import android.hardware.camera2.CaptureFailure
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.UIMovie
import com.example.projeto_integrador.features.search.domain.usecase.SearchMovies
import java.util.Collections.copy


class SearchViewState(
    val noSearchQuery: Boolean = true,
    val searchResults: List<UIMovie> = emptyList(),
    val searchingMovies: Boolean = false,
    val failure: Event<Throwable>? = null
) {


}