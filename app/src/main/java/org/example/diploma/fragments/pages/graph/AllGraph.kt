package org.example.diploma.fragments.pages.graph

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.example.diploma.R
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

//        val activity = activity as AppCompatActivity
//        val headline = activity.findViewById<TextView>(R.id.textView6)
//        if(headline != null){
//            headline.text = "Графики"
//        }


        adapter = ResultAdapter(this)
        viewPager = binding!!.viewPager
        viewPager.adapter = adapter
        resultsTab = binding!!.tabLayout

        TabLayoutMediator(resultsTab, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "U*"
                1 -> tab.text = "Losses"
                2 -> tab.text = "Tsh"
                3 -> tab.text = "Pump"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        updateToolbar()
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        resetToolbar()
    }


    private fun updateToolbar() {
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
        toolbar?.let {
            val titleTextView: TextView? = it.findViewById(R.id.textView6)
            titleTextView?.text = "Графики"
        }
    }

    private fun resetToolbar() {
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
        toolbar?.let {
            val titleTextView: TextView? = it.findViewById(R.id.textView6)
            titleTextView?.text = "Default Title"
        }
    }
}