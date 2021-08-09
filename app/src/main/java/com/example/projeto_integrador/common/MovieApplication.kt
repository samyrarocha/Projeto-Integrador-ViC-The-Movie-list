package com.example.projeto_integrador.common

import android.app.Application
import com.example.projeto_integrador.common.data.di.initApiMoviesDependencies
import com.example.projeto_integrador.common.data.di.initPicassoDependencies
import com.example.projeto_integrador.features.feed.di.initAllMoviesDependencies
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
        initPicassoDependencies()
    }
}