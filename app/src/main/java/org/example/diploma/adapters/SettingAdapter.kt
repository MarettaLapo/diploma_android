package org.example.diploma.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.example.diploma.fragments.ConfigurationFragment
import org.example.diploma.fragments.LaserMediumFragment
import org.example.diploma.fragments.PumpFragment
import org.example.diploma.fragments.QSwitchFragment

class SettingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4;

    override fun createFragment(position: Int): Fragment {
        Log.v("mine", position.toString())
        var fragment : Fragment = LaserMediumFragment();
        when (position) {
            1 -> fragment = PumpFragment();
            2 -> fragment = ConfigurationFragment();
            3 -> fragment = QSwitchFragment();
        }
        return fragment;
    }
}