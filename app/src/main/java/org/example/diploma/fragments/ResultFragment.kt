package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import org.example.diploma.R
import org.example.diploma.adapters.ResultAdapter
import org.example.diploma.adapters.SettingAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {

    private lateinit var adapter: ResultAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ResultAdapter(this)
        viewPager = view.findViewById(R.id.resultPager)
        viewPager.adapter = adapter
//        settingTab = view.findViewById(R.id.settingTab)
//        TabLayoutMediator(settingTab, viewPager) { tab, position ->
//            tab.text = "TAB ${(position + 1)}"
//        }.attach()
    }

}