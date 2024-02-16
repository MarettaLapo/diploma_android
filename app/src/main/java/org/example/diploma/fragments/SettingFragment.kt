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
import org.example.diploma.adapters.SettingAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var adapter: SettingAdapter
    private lateinit var viewPager: ViewPager2
//    private lateinit var settingTab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SettingAdapter(this)
        viewPager = view.findViewById(R.id.settingPager)
        viewPager.adapter = adapter
//        settingTab = view.findViewById(R.id.settingTab)
//        TabLayoutMediator(settingTab, viewPager) { tab, position ->
//            tab.text = "TAB ${(position + 1)}"
//        }.attach()
    }
}