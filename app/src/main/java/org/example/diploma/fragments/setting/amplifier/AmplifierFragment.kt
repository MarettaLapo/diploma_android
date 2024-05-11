package org.example.diploma.fragments.setting.amplifier

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentAmplifierBinding

class AmplifierFragment : Fragment() {

    private var binding: FragmentAmplifierBinding? = null
    private var lastTimestampDisplayed: Long = 0

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
        binding = FragmentAmplifierBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    binding!!.checks.isChecked = viewModel.laserDataFlow.value.amplifier.isUse!!
                    binding!!.ampLengthEditText.setText(viewModel.laserDataFlow.value.amplifier.ampLength.toString())
                    binding!!.ampPulseEnergyEditText.setText(viewModel.laserDataFlow.value.amplifier.ampPulseEnergy.toString())
                    binding!!.lcEditText.setText(viewModel.laserDataFlow.value.amplifier.ampPulseDuration.toString())
                    if(viewModel.laserDataFlow.value.amplifier.waveform == 0){
                        binding!!.currencySwitchers.check(binding!!.radioButton1.id)
                    }
                    else{
                        binding!!.currencySwitchers.check(binding!!.radioButton2.id)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}