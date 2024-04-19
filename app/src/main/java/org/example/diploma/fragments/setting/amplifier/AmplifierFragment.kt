package org.example.diploma.fragments.setting.amplifier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentAmplifierBinding
class AmplifierFragment : Fragment() {

    private var binding: FragmentAmplifierBinding? = null

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

        binding = FragmentAmplifierBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        binding!!.viewModel = viewModel

        val rg = binding!!.currencySwitchers

        rg.setOnCheckedChangeListener{group, checkedId ->
            val hehe = binding!!.root.findViewById<RadioButton>(checkedId)
            //Log.d("checkedId", hehe.toString())
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}