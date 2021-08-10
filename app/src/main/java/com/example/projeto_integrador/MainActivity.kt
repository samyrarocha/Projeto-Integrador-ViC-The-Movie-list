package com.example.projeto_integrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projeto_integrador.features.feed.presentation.AllMoviesFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.fragment_all_movies, AllMoviesFragment()
        ).commit()


    }
}