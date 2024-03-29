package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.example.diploma.R
import org.example.diploma.adapters.ResultAdapter
import org.example.diploma.adapters.SettingAdapter
import org.example.diploma.databinding.FragmentResultBinding
import org.example.diploma.databinding.FragmentSettingBinding


class ResultFragment : Fragment() {

    private lateinit var adapter: ResultAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var resultsTab: TabLayout

    private var binding: FragmentResultBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ResultAdapter(this)
        viewPager = binding!!.resultPager
        viewPager.adapter = adapter
        resultsTab = binding!!.resultsTab
        TabLayoutMediator(resultsTab, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}