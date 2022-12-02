package com.nextgen.newsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nextgen.newsapp.databinding.ActivityMainBinding
import com.nextgen.newsapp.ui.home.HomeFragment
import com.nextgen.newsapp.ui.news.SaveNewsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destionation: NavDestination, _ ->
            when(destionation.id){
                R.id.navigation_home -> {
                    visible()
                }
                R.id.navigation_news -> {
                    visible()
                }
                R.id.navigation_profile -> {
                    visible()
                }
                R.id.navigation_save -> {
                    visible()
                }
                else -> {
                    inVisible()
                }
            }
        }
    }

    private fun inVisible() {
        binding.navView.visibility = View.GONE
    }

    private fun visible() {
        binding.navView.visibility = View.VISIBLE
    }


}