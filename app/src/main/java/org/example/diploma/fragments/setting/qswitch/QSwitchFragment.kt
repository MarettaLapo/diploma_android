package org.example.diploma.fragments.setting.qswitch

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.combine
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
            (activity?.applicationContext as AppApplication).saveRepository,
            (activity?.applicationContext as AppApplication).outputRepository,
            (activity?.applicationContext as AppApplication).laserOutputRepository,
            (activity?.applicationContext as AppApplication).giantPulseRepository,
        )
    }

    private var binding: FragmentQSwitchBinding? = null
    private var lastTimestampDisplayed: Long = 0

    var currentAQS: Boolean = false
    var currentPQS: Boolean = false

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

                    binding!!.freqEditText.setText(laser.qSwitch.sf.toString())

                    binding!!.periodEditText.setText("200.00")

                    binding!!.kEditText.setText(laser.qSwitch.sk.toString())

                    binding!!.bEditText.setText(laser.qSwitch.sb.toString())

                    binding!!.spinMode.adapter = adapter1
                    binding!!.spinMode.setSelection(laser.qSwitch.mode!!)

                    binding!!.spinType.adapter = adapter2
                    binding!!.spinType.setSelection(laser.qSwitch.AQStype!!)

                }
                lastTimestampDisplayed = laser.timestamp
            }
        }

        lifecycleScope.launch {
            viewModel.qSwitchFrontType.collect { sFrontType ->
                if (sFrontType == 0) {
                    binding!!.schemeTypeLeading.check(binding!!.schemeType1.id)
                } else {
                    binding!!.schemeTypeLeading.check(binding!!.schemeType2.id)
                }
            }
        }

        binding!!.schemeTypeLeading.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.scheme_type1 -> {
                    viewModel.updateFrontType(0)
                }

                R.id.scheme_type2 -> {
                    viewModel.updateFrontType(1)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.qSwitchType.collect { qSwitchType ->
                binding!!.spinType.setSelection(qSwitchType)
            }
        }

        lifecycleScope.launch {
            viewModel.qSwitchMode.collect { qSwitchMode ->
                binding!!.spinMode.setSelection(qSwitchMode)
                when(qSwitchMode){
                    0 -> {
                        Log.d("hehe", "yo 0")
                        binding!!.freqEditText.visibility = View.GONE
                        binding!!.periodEditText.visibility = View.GONE
                        binding!!.kEditText.visibility = View.GONE
                        binding!!.bEditText.visibility = View.GONE
                    }
                    1 -> {
                        Log.d("hehe", "yo 1")
                        binding!!.freqEditText.visibility = View.VISIBLE
                        binding!!.periodEditText.visibility = View.VISIBLE
                        binding!!.kEditText.visibility = View.GONE
                        binding!!.bEditText.visibility = View.GONE
                    }
                    2 -> {
                        Log.d("hehe", "yo 2")
                        binding!!.freqEditText.visibility = View.VISIBLE
                        binding!!.periodEditText.visibility = View.VISIBLE
                        binding!!.kEditText.visibility = View.VISIBLE
                        binding!!.bEditText.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding!!.spinMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Обработка изменений, вызванных пользователем
                Log.d("hehe", position.toString())
                viewModel.updateQSwitchMode(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ничего не делаем
            }
        }

        binding!!.spinType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Обработка изменений, вызванных пользователем
                viewModel.updateQSwitchType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ничего не делаем
            }
        }

        lifecycleScope.launch {
            combine(
                viewModel.isQSwitch,
                viewModel.isAQS,
                viewModel.isPQS
            ) { isQSwitch, isAQS, isPQS ->
                Triple(isQSwitch, isAQS, isPQS)
            }.collect { (isQSwitch, isAQS, isPQS) ->
                currentAQS = isAQS
                currentPQS = isPQS
                if (isQSwitch) {
                    binding!!.switchCompat1.isChecked =
                        isPQS
                    binding!!.switchCompat.isChecked = isAQS
                    if (!isAQS) {
                        doGrayAQS()
                    } else {
                        stopGrayAQS()
                    }
                    if (!isPQS) {
                        doGrayPQS()
                    } else {
                        stopGrayPQS()
                    }
                } else {
                    doGrayAQS()
                    doGrayPQS()
                }
            }
        }


        //AQS
        binding!!.switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.updateIsAQS(isChecked)
            if (isChecked) {
                viewModel.updateIsQSwitch(true)
            } else {
                if (!currentPQS) {
                    viewModel.updateIsQSwitch(false)
                }
            }
        }

        binding!!.switchCompat1.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.updateIsPQS(isChecked)
            if (isChecked) {
                viewModel.updateIsQSwitch(true)
            } else {
                if (!currentAQS) {
                    viewModel.updateIsQSwitch(false)
                }
            }
        }
    }
    // FEF7FF

    fun stopGrayAQS() {
        binding!!.cardAqs.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.lshEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.nshEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.st0EditText.setBackgroundColor(Color.parseColor("#FEF7FF"))


        binding!!.stmaxEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))


        binding!!.stsEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))


        binding!!.stfEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))


        binding!!.stoffEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))


        binding!!.acEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.freqEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))
        binding!!.periodEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))
        binding!!.kEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))
        binding!!.bEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))
    }

    fun doGrayAQS() {
        binding!!.cardAqs.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.lshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.nshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.st0EditText.setBackgroundColor(Color.parseColor("#E6E0E9"))


        binding!!.stmaxEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))


        binding!!.stsEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))


        binding!!.stfEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))


        binding!!.stoffEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))


        binding!!.acEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.freqEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
        binding!!.periodEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
        binding!!.kEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
        binding!!.bEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
    }

    fun stopGrayPQS() {
        binding!!.cardPqs.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.lpshEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.npshEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.spt0EditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.sptdEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.sptEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))

        binding!!.fomEditText.setBackgroundColor(Color.parseColor("#FEF7FF"))
    }

    fun doGrayPQS() {
        binding!!.cardPqs.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.lpshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.npshEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.spt0EditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.sptdEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.sptEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))

        binding!!.fomEditText.setBackgroundColor(Color.parseColor("#E6E0E9"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

