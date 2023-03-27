package com.bashirli.buyme.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bashirli.buyme.R
import com.bashirli.buyme.databinding.ActivityProductBinding
import com.bashirli.buyme.databinding.ActivityScreenBinding
import com.bashirli.buyme.factory.AppFactory
import com.bashirli.buyme.view.fragment.CartFragmentDirections
import com.bashirli.buyme.view.fragment.MainFragmentDirections
import com.bashirli.buyme.view.fragment.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreenBinding

    private lateinit var navController: NavController
  /*
    @Inject
    lateinit var factory: AppFactory

   */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   supportFragmentManager.fragmentFactory = factory
        binding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()



        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
          navController=navHostFragment.findNavController()
        val appBarConfiguration= AppBarConfiguration(setOf(
            R.id.mainFragment,
            R.id.favoritesFragment,
            R.id.cartFragment,
            R.id.locationsFragment,
            R.id.mapsFragment
        ))
        binding.bottomAppBar.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }

}