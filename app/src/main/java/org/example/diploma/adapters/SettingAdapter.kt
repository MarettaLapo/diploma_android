package org.example.diploma.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.example.diploma.fragments.setting.configuration.ConfigurationFragment
import org.example.diploma.fragments.setting.laserMedium.LaserMediumFragment
import org.example.diploma.fragments.setting.pump.PumpFragment

class SettingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4;

    override fun createFragment(position: Int): Fragment {
        Log.v("mine", position.toString())
        var fragment : Fragment = PumpFragment();
        when (position) {
            1 -> fragment = ConfigurationFragment();
            2 -> fragment = LaserMediumFragment();
//            3 -> fragment = QSwitchFragment();
        }
        return fragment;
    }
}