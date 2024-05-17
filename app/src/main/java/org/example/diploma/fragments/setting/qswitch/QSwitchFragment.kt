package org.example.diploma.fragments.setting.qswitch

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentQSwitchBinding


class QSwitchFragment : Fragment() {
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

    private var binding: FragmentQSwitchBinding? = null
    private var lastTimestampDisplayed: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQSwitchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = resources.getStringArray(R.array.modes_array)
        val items2 = resources.getStringArray(R.array.front_types_array)
        val adapter1 = ArrayAdapter(requireContext(), R.layout.layout_adapter, items.toList())
        val adapter2 = ArrayAdapter(requireContext(), R.layout.layout_adapter, items2.toList())
        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    Log.d("laser", "yaya")
                    binding!!.lshEditText.setText(laser.qSwitch.lsh.toString())
                    binding!!.nshEditText.setText(laser.qSwitch.nsh.toString())
                    binding!!.st0EditText.setText(laser.qSwitch.st0.toString())
                    binding!!.stmaxEditText.setText(laser.qSwitch.stmax.toString())
                    binding!!.stsEditText.setText(laser.qSwitch.sts.toString())
                    binding!!.stfEditText.setText(laser.qSwitch.stf.toString())
                    binding!!.stoffEditText.setText(laser.qSwitch.stoff.toString())
                    binding!!.acEditText.setText(laser.qSwitch.absCoef.toString())

                    binding!!.lpshEditText.setText(laser.qSwitch.lpsh.toString())
                    binding!!.npshEditText.setText(laser.qSwitch.npsh.toString())
                    binding!!.spt0EditText.setText(laser.qSwitch.spt0.toString())
                    binding!!.sptdEditText.setText(laser.qSwitch.sptd.toString())
                    binding!!.sptEditText.setText(laser.qSwitch.spt.toString())

                    binding!!.sshEditText.setText(laser.qSwitch.ssh.toString())

                    binding!!.fomEditText.setText(laser.qSwitch.fom.toString())

                    binding!!.spinMode.adapter = adapter1
                    binding!!.spinMode.setSelection(laser.qSwitch.mode!!)

                    binding!!.spinType.adapter = adapter2
                    binding!!.spinType.setSelection(laser.qSwitch.AQStype!!)

                    if(laser.qSwitch.sFrontType == 0){
                        binding!!.schemeTypeLeading.check(binding!!.schemeType1.id)
                    }
                    else {
                        binding!!.schemeTypeLeading.check(binding!!.schemeType2.id)
                    }

                    if(laser.qSwitch.isQSwitch == true){
                        binding!!.switchCompat1.isChecked =
                            laser.qSwitch.isPQS!!
                        binding!!.switchCompat.isChecked = laser.qSwitch.isAQS!!
                        if(laser.qSwitch.isAQS != true){
                            Log.d("laser1", "here")
                            doGrayAQS()
                        }
                        if(laser.qSwitch.isPQS != true){
                            Log.d("laser1", "isPQS")
                            doGrayPQS()
                        }
                    }
                    else{
                        doGrayAQS()
                        doGrayPQS()
                    }
                }
                lastTimestampDisplayed = laser.timestamp
            }
        }
    }
    fun doGrayAQS(){
        binding!!.cardAqs.setBackgroundColor(Color.parseColor("#E6E0E9"))
        binding!!.lshEditText.focusable = View.NOT_FOCUSABLE
        binding!!.lshEditText.inputType = InputType.TYPE_NULL
        binding!!.lshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.nshEditText.focusable = View.NOT_FOCUSABLE
        binding!!.nshEditText.inputType = InputType.TYPE_NULL
        binding!!.nshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.st0EditText.focusable = View.NOT_FOCUSABLE
        binding!!.st0EditText.inputType = InputType.TYPE_NULL
        binding!!.st0EditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.stmaxEditText.focusable = View.NOT_FOCUSABLE
        binding!!.stmaxEditText.inputType = InputType.TYPE_NULL
        binding!!.stmaxEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.stsEditText.focusable = View.NOT_FOCUSABLE
        binding!!.stsEditText.inputType = InputType.TYPE_NULL
        binding!!.stsEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.stfEditText.focusable = View.NOT_FOCUSABLE
        binding!!.stfEditText.inputType = InputType.TYPE_NULL
        binding!!.stfEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.stoffEditText.focusable = View.NOT_FOCUSABLE
        binding!!.stoffEditText.inputType = InputType.TYPE_NULL
        binding!!.stoffEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.acEditText.focusable = View.NOT_FOCUSABLE
        binding!!.acEditText.inputType = InputType.TYPE_NULL
        binding!!.acEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
    }

    fun doGrayPQS(){
        binding!!.cardPqs.setBackgroundColor(Color.parseColor("#E6E0E9"))
        binding!!.lpshEditText.focusable = View.NOT_FOCUSABLE
        binding!!.lpshEditText.inputType = InputType.TYPE_NULL
        binding!!.lpshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.npshEditText.focusable = View.NOT_FOCUSABLE
        binding!!.npshEditText.inputType = InputType.TYPE_NULL
        binding!!.npshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.spt0EditText.focusable = View.NOT_FOCUSABLE
        binding!!.spt0EditText.inputType = InputType.TYPE_NULL
        binding!!.spt0EditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.sptdEditText.focusable = View.NOT_FOCUSABLE
        binding!!.sptdEditText.inputType = InputType.TYPE_NULL
        binding!!.sptdEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.sptEditText.focusable = View.NOT_FOCUSABLE
        binding!!.sptEditText.inputType = InputType.TYPE_NULL
        binding!!.sptEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.fomEditText.focusable = View.NOT_FOCUSABLE
        binding!!.fomEditText.inputType = InputType.TYPE_NULL
        binding!!.fomEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

