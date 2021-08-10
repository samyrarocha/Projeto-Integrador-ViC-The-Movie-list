package com.example.projeto_integrador.common

import android.app.Application
import com.example.projeto_integrador.common.data.di.initApiMoviesDependencies
import com.example.projeto_integrador.features.feed.di.initAllMoviesDependencies
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
        }
        initApiMoviesDependencies()
        initAllMoviesDependencies()
        setupPicassoInstance()
    }

    private fun setupPicassoInstance() {
        val picassoBuilder = Picasso.Builder(this)
        picassoBuilder.downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
        val picasso = picassoBuilder.build()
        picasso.setIndicatorsEnabled(true)
        picasso.isLoggingEnabled = true
        Picasso.setSingletonInstance(picasso)
    }
}