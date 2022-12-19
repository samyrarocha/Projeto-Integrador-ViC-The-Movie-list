package com.example.projeto_integrador.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.projeto_integrador.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, null)
    }
}