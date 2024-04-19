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
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import kotlin.math.exp
import kotlin.math.sqrt

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var adapter: SettingAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var settingTab: TabLayout


    private var binding: FragmentSettingBinding? = null

    private val viewModel: MainViewModel by activityViewModels {

        MainViewModelFactory(
            (activity?.applicationContext as AppApplication).amplifierRepository,
            (activity?.applicationContext as AppApplication).configurationRepository,
            (activity?.applicationContext as AppApplication).hostRepository,
            (activity?.applicationContext as AppApplication).laserMediumRepository,
            (activity?.applicationContext as AppApplication).optimizationRepository,
            (activity?.applicationContext as AppApplication).pumpRepository,
            (activity?.applicationContext as AppApplication).qSwitchRepository,
            (activity?.applicationContext as AppApplication).saveRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SettingAdapter(this)
        viewPager = binding!!.settingPager
        viewPager.adapter = adapter

        Log.d("hehe", viewModel.pumpData.value.toString())
        calculateFields()
        settingTab = binding!!.settingTab
        TabLayoutMediator(settingTab, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun calculateFields() {

        viewModel.laserMediumData.observe(viewLifecycleOwner){
            Log.d("hehe", viewModel.laserMediumData.value.toString())
        }
        viewModel.configurationData.observe(viewLifecycleOwner) {
            it.ag = if (it.isCylinder == true) {
                (it.hov?.times(Math.PI) ?: 0.0) * it.dia!! * it.dia / 4.0
            } else {
                (it.ld?.let { it1 -> it.hov?.times(it1) } ?: 0.0) * it.lb!!
            }
        }

        viewModel.pumpData.observe(viewLifecycleOwner) {
            Log.d("hehe", viewModel.configurationData.value?.dia.toString())
            it.ap = if (it.scheme == 0) {
                if (viewModel.configurationData.value?.isCylinder == true) {
                    viewModel.configurationData.value?.la?.times((sqrt(Math.PI) * viewModel.configurationData.value?.dia!! / 2.0))
                } else {
                    viewModel.configurationData.value?.ld?.times(viewModel.configurationData.value?.la!!)
                }
            } else {
                Log.d("hehe", viewModel.configurationData.value?.ld.toString())
                if (viewModel.configurationData.value?.isCylinder == true) {
                    Math.PI * viewModel.configurationData.value?.dia!! * viewModel.configurationData.value?.dia!! / 4.0
                } else {
                    viewModel.configurationData.value?.ld?.times(viewModel.configurationData.value?.lb!!)
                }
            }

            it.pl = if (it.scheme != 0) {
                viewModel.configurationData.value?.la
            } else {
                if (viewModel.configurationData.value?.isCylinder == true) {
                    sqrt(Math.PI) * viewModel.configurationData.value?.dia!! / 2.0
                } else {
                    viewModel.configurationData.value?.lb
                }
            }
            Log.d("yeyetye", it.rp.toString() + " " + viewModel.laserMediumData.value.toString())
            it.fav =
                -(((it.rp?.times(exp(-viewModel.laserMediumData.value?.ac!! * it.pl!!)) ?: 0.0) + 1.0) *
                        (exp(-viewModel.laserMediumData.value?.ac!! * it.pl!!) - 1.0)) /
                        (viewModel.laserMediumData.value?.ac!! * it.pl!!)

            it.p0 = (it.wp?.times(it.hc!!) ?: 0.0) / it.ap!!

            it.p = it.fav!! * it.p0!!

//            it.issp = when (host) {
//                "Er:Host" -> hc / ne / lp / 1E-07 / (2.0 * sp * T31Yb) * 1000000.0
//                "Nd:Host" -> hc / ne / lp / 1E-07 / (sp * T43) * 1000000.0
//                "General" -> {
//                    if (sensitizer == 1) {
//                        hc / ne / lp / 1E-07 / (2.0 * sp * T31Yb) * 1000000.0
//                    } else {
//                        if (levels == "4 level") {
//                            hc / ne / lp / 1E-07 / (sp * T43) * 1000000.0
//                        } else {
//                            hc / ne / lp / 1E-07 / (sp * T32) * 1000000.0
//                        }
//                    }
//                }
//                "Yb:Host" -> hc / ne / lp / 1E-07 / (sp * T43) * 1000000.0
//                else -> {
//                    0.0
//                }
//            }
        }
    }
}