package org.example.diploma.fragments.pages.graph

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.example.diploma.adapters.ResultAdapter
import org.example.diploma.databinding.FragmentAllGraphBinding
import org.example.diploma.databinding.FragmentResultBinding

class AllGraph : Fragment() {

    private lateinit var adapter: ResultAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var resultsTab: TabLayout

    private var binding: FragmentAllGraphBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllGraphBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ResultAdapter(this)
        viewPager = binding!!.viewPager
        viewPager.adapter = adapter
        resultsTab = binding!!.tabLayout
        TabLayoutMediator(resultsTab, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}