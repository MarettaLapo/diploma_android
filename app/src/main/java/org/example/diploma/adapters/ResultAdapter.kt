package org.example.diploma.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.example.diploma.fragments.pages.graph.GraphLossFragment
import org.example.diploma.fragments.pages.graph.GraphPumpFragment
import org.example.diploma.fragments.pages.graph.GraphTshFragment
import org.example.diploma.fragments.pages.graph.GraphUFragment


class ResultAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4;

    override fun createFragment(position: Int): Fragment {
        Log.v("mine", position.toString())
        var fragment : Fragment = GraphUFragment();
        when (position) {
            1 -> fragment = GraphLossFragment();
            2 -> fragment = GraphTshFragment();
            3 -> fragment = GraphPumpFragment()
        }
        return fragment;
    }
}