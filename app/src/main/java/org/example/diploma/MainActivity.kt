package org.example.diploma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import org.example.diploma.adapters.SettingAdapter
import org.example.diploma.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.appNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.appNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}