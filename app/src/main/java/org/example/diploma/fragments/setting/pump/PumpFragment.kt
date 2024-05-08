package org.example.diploma.fragments.setting.pump

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentPumpBinding


class PumpFragment : Fragment() {

    private var binding: FragmentPumpBinding? = null
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
        // Inflate the layout for this fragment
        binding = FragmentPumpBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    Log.d("laser", "yaya")
                    binding!!.wpInput.setText(laser.pump.wp.toString())
                    binding!!.tpInput.setText(laser.pump.tp.toString())
                    binding!!.epInput.setText(laser.ep.toString())
                    binding!!.lpInput.setText(laser.pump.lp.toString())
                    binding!!.hcInput.setText(laser.pump.hc.toString())
                    binding!!.apInput.setText(laser.ap.toString())
                    binding!!.pInput.setText(laser.p.toString())
                    binding!!.favInput.setText(laser.fav.toString())
                    binding!!.pformInput.setText(laser.pump.pformText)
                    binding!!.fFormInput.setText(laser.fform.toString())
                }
                lastTimestampDisplayed = laser.timestamp
            }
        }

        binding!!.wpInput.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                lifecycleScope.launch {
                    viewModel.updateDataPumpWp(binding!!.wpInput.text.toString())
//                    viewModel.laserDataFlow.collect { laser ->
//                        binding!!.epInput.setText(laser.ep.toString())
//                    }
                }
            }
        }

        binding!!.but1.setOnClickListener {
            Log.d("hehe", "ya tyt")
            val hehe = PumpFluenceFragment()
            hehe.show(parentFragmentManager, "PumpFluenceDialog")
        }

        binding!!.but2.setOnClickListener{
            val hehe = PumpWaveformFragment()
            hehe.show(parentFragmentManager, "PumpWaveformDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
