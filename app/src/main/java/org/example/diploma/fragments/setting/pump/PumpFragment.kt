package org.example.diploma.fragments.setting.pump

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentPumpBinding


class PumpFragment : Fragment() {

    private var binding: FragmentPumpBinding? = null
    private var lastTimestampDisplayed: Long = 0
    var canBe = 0
    var notMe = true
    var pumpPower = 0.0
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
                    pumpPower = laser.ep
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

        lifecycleScope.launch {
            viewModel.pumpScheme.collect { scheme ->
                if (scheme == 0) {
                    binding!!.schemeTypePump.check(binding!!.schemeType1.id)
                    binding!!.image.setImageResource(R.drawable.pump_rect_side)
                } else {
                    binding!!.schemeTypePump.check(binding!!.schemeType2.id)
                    binding!!.image.setImageResource(R.drawable.pump_rect_end)
                }
            }
        }

        lifecycleScope.launch {
            combine(viewModel.pumpType, viewModel.shutter){ type, shutter ->
                Pair(type, shutter)
            }.collect { (type, shutter) ->
                canBe = shutter
                notMe = true
                Log.d("heheLog", "type $type + shutter $shutter")
                if(type == 0){
                    binding!!.typePump.check(binding!!.type1.id)
                    binding!!.tpInput.setText(viewModel.laserDataFlow.value.pump.tp.toString())
                    binding!!.epInput.setText(viewModel.laserDataFlow.value.ep.toString())
                }
                else{
                    if(shutter != 0){
                        viewModel.updatePumpType(0)
                        binding!!.typePump.check(binding!!.type1.id)
                        binding!!.tpInput.setText(viewModel.laserDataFlow.value.pump.tp.toString())
                        binding!!.epInput.setText(viewModel.laserDataFlow.value.ep.toString())
                    }
                    else{
                        binding!!.tpInput.setText("10000.00")
                        binding!!.epInput.setText((pumpPower * 10000.0).toString())
                        binding!!.typePump.check(binding!!.type2.id)
                    }
                }
                notMe = false
            }
        }

        binding!!.schemeTypePump.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.scheme_type1 -> {
                    viewModel.updatePumpScheme(0)
                }
                R.id.scheme_type2 -> {
                    viewModel.updatePumpScheme(1)
                }
            }
        }

        binding!!.typePump.setOnCheckedChangeListener { group, checkedId ->
            if (!notMe){
                when (checkedId) {
                    R.id.type1 -> {
                        viewModel.updatePumpType(0)
                    }
                    R.id.type2 -> {
                        if(canBe == 0){
                            viewModel.updatePumpType(1)
                        }else{
                            binding!!.typePump.check(binding!!.type1.id)
                            val toast = Toast.makeText(requireContext(), "Перейдите в режим свободной генерации", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
            }
        }

        binding!!.but1.setOnClickListener {
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
