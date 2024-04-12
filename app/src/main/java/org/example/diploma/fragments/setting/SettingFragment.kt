package org.example.diploma.fragments.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.example.diploma.R
import org.example.diploma.adapters.SettingAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.fragments.pages.UGViewModel
import org.example.diploma.fragments.pages.UGViewModelFactory
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var adapter: SettingAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var settingTab: TabLayout

    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory((activity?.applicationContext as AppApplication).hostRepository)
    }

    private var binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        //Log.d("hehe", settingViewModel.hostId.toString())
        settingViewModel.hostId.observe(activity as LifecycleOwner) { host ->
            // Update the cached copy of the words in the adapter.
            Log.d("catt", host.toString())
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SettingAdapter(this)
        viewPager = binding!!.settingPager
        viewPager.adapter = adapter

        settingTab = binding!!.settingTab
        TabLayoutMediator(settingTab, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}