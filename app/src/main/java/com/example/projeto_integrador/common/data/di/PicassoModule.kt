package com.example.projeto_integrador.common.data.di

import android.content.Context
import com.example.projeto_integrador.features.feed.di.allMoviesModule
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val picassoModule = module {

    factory {
        val downloader = okHttp3Downloader(get())  // 1
        picasso(
            get(), downloader  // 2
        )
    }
}
private fun okHttp3Downloader(client: OkHttpClient) = OkHttp3Downloader(client)
private fun picasso(context: Context, downloader: OkHttp3Downloader) = Picasso.Builder(context)
    .downloader(downloader)
    .build()

internal val picassoDependencies by lazy {
    loadKoinModules(picassoModule)
}
fun initPicassoDependencies() = picassoDependencies