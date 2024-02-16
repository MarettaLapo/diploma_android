package org.example.diploma.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.example.diploma.fragments.GraphOneFragment
import org.example.diploma.fragments.GraphThreeFragment
import org.example.diploma.fragments.GraphTwoFragment

class ResultAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3;

    override fun createFragment(position: Int): Fragment {
        Log.v("mine", position.toString())
        var fragment : Fragment = GraphOneFragment();
        when (position) {
            1 -> fragment = GraphTwoFragment();
            2 -> fragment = GraphThreeFragment();
        }
        return fragment;
    }
}