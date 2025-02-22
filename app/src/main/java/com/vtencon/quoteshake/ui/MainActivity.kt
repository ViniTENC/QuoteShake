package com.vtencon.quoteshake.ui

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.vtencon.quoteshake.R
import com.vtencon.quoteshake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuProvider {
    lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        (binding.bottomNavigationView as NavigationBarView).setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.favouriteFragment, R.id.settingsFragment, R.id.newQuotationFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Ajustar el espacio alrededor del NavigationBar
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = bars.left,
                top = 0,
                right = 0,
                bottom = bars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        // Ajustar el espacio alrededor del FragmentContainerView
        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = 0,
                top = 0,
                right = bars.right,
                bottom = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) bars.bottom else 0
            )
            WindowInsetsCompat.CONSUMED
        }

        // Ajustar el espacio alrededor del AppBarLayout
        ViewCompat.setOnApplyWindowInsetsListener(binding.appbar) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = bars.left,
                top = bars.top,
                right = 0,
                bottom = 0
            )
            WindowInsetsCompat.CONSUMED
        }
        addMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_about, menu)    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        navController.navigate(R.id.aboutDialogFragment)

        return when (menuItem.itemId) {
            R.id.aboutDialogFragment -> {
                navController.navigate(R.id.aboutDialogFragment)
                true
            }
            else -> false
        }
    }
}