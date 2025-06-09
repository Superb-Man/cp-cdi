package com.example.demo2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.demo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        toolbar.setupWithNavController(navController, appBarConfiguration)
//        toolbar.setTitle("Demo App 2.0")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        when (item.itemId) {
            R.id.action_go_to_history -> {
                if (navController.currentDestination?.id != R.id.healthCardsFragment) {
                    this.title = getString(R.string.records_page)
                    navController.navigate(R.id.healthCardsFragment)
                }
                return true
            }
            R.id.go_to_add_record -> {
                if (navController.currentDestination?.id != R.id.inputFragment) {
                    this.title = getString(R.string.record_input)
                    navController.navigate(R.id.inputFragment)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
