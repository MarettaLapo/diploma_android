package org.example.diploma.fragments.setting.amplifier

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentAmplifierBinding

class AmplifierFragment : DialogFragment() {

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
            (activity?.applicationContext as AppApplication).saveRepository,
            (activity?.applicationContext as AppApplication).outputRepository,
            (activity?.applicationContext as AppApplication).laserOutputRepository,
            (activity?.applicationContext as AppApplication).giantPulseRepository,
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmplifierBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    binding!!.ampLengthEditText.setText(viewModel.laserDataFlow.value.amplifier.ampLength.toString())
                    binding!!.ampPulseEnergyEditText.setText(viewModel.laserDataFlow.value.amplifier.ampPulseEnergy.toString())
                    binding!!.lcEditText.setText(viewModel.laserDataFlow.value.amplifier.ampPulseDuration.toString())
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isAmplifier.collect { isUse ->
                binding!!.checks.isChecked = isUse
            }
        }

        lifecycleScope.launch {
            viewModel.waveform.collect { waveform ->
                if (waveform == 0) {
                    binding!!.currencySwitchers.check(binding!!.radioButton1.id)
                } else {
                    binding!!.currencySwitchers.check(binding!!.radioButton2.id)
                }
            }
        }

        binding!!.currencySwitchers.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button1 -> {
                    viewModel.updateWaveform(0)
                }
                R.id.radio_button2 -> {
                    viewModel.updateWaveform(1)
                }
            }
        }

        binding!!.checks.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.updateIsAmplifier(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}