package org.example.diploma

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import org.example.diploma.databinding.MainActivityBinding
import org.example.diploma.fragments.pages.MainPageFragment


class MainActivity : AppCompatActivity(){

    private  var binding: MainActivityBinding? = null
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val toolbar = binding!!.toolbar;
        setSupportActionBar(toolbar);
        val bottom = binding!!.bottomNavigation

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainPageFragment,
                R.id.savedSettingsFragment)
        )

        navController = this.findNavController(R.id.appNavHostFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragContainer = findViewById<FragmentContainerView>(R.id.appNavHostFragment)
            when (destination.id) {
                R.id.settingFragment, R.id.resultFragment, R.id.allGraph-> {
                    fragContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = 0
                    }
                    bottom.visibility = View.GONE
                }
                else -> {
                    fragContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = 100
                    }
                    bottom.visibility = View.VISIBLE
                }
            }
        }

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottom, navController);


        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setToolbarTitle(title: String) {
        if(binding != null){
            val textView = binding!!.textView6
            textView.text = title
        }

    }
}