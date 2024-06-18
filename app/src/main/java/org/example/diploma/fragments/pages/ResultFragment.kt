package org.example.diploma.fragments.pages

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentResultBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


class ResultFragment : Fragment() {


    private var binding: FragmentResultBinding? = null

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


    var heh = 0
    var uFile: String = ""
    var lossFile: String = ""
    var tshFile: String? = null
    var pumpFile: String = ""
    var output_powerFile: String = ""

    var del_sout: Double = 0.0
    var del_u: Double = 0.0
    var del_loss: Double = 0.0
    var del_tsh: Double? = null
    var del_p: Double = 0.0

    var f = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)
        viewModel.flag = false
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            combine(
                viewModel.output,
                viewModel.laserOutput,
                viewModel.giantPulse
            ) { output, laser, pulse ->
                Triple(output, laser, pulse)
            }.collect { (output, laser, pulse) ->
                Log.d("resultError", viewModel.hehe.toString())
                if (output.outputId == null || viewModel.hehe == 1){
                    viewModel.forceUpdate()
                    Log.d("resultError", viewModel.hehe.toString())

                }
                Log.d("resultError", "heh $heh")
                if (heh == 1){
                    viewModel.flag = false
                }
                if (output.outputId != null && viewModel.hehe == 2) {
                    uFile = output.u!!
                    output_powerFile = output.output_power!!

                    //отрисовка графика
                    if (output_powerFile.isNotEmpty() && uFile.isNotEmpty() && !viewModel.flag) {
                        val lineChart = binding!!.lineChart
                        val data1Entries = readDataFromFile(output_powerFile)
                        val data2Entries = readDataFromFile(uFile)

                        val dataSet1 = LineDataSet(data1Entries, "Output power").apply {
                            color = R.color.black
                            setDrawCircles(false)
                            setDrawValues(false)
                            mode = LineDataSet.Mode.LINEAR
                            lineWidth = 2f// Или Mode.CUBIC_BEZIER для сглаживания
                        }
                        val dataSet2 = LineDataSet(data2Entries, "U*").apply {
                            color = R.color.purple_700
                            setDrawCircles(false)
                            setDrawValues(false)
                            mode = LineDataSet.Mode.LINEAR // Или Mode.CUBIC_BEZIER для сглаживания
                        }

                        // Добавление DataSet в LineData
                        val lineData = LineData(dataSet1, dataSet2)
                        lineChart.data = lineData

                        lineChart.xAxis.apply {
                            setDrawLabels(false) // Отключение меток на оси X
                            setDrawAxisLine(false) // Отключение линии оси X
                        }

                        lineChart.axisLeft.apply {
                            setDrawLabels(false) // Отключение меток на левой оси Y
                            setDrawAxisLine(false) // Отключение линии левой оси Y
                        }

                        lineChart.axisRight.apply {
                            setDrawLabels(false) // Отключение меток на правой оси Y
                            setDrawAxisLine(false) // Отключение линии правой оси Y
                        }

                        // Обновление графика
                        lineChart.invalidate()
                        viewModel.flag = true
                    }

                    binding!!.imaxInput.setText(laser.imax.toString())
                    binding!!.efgInput.setText(laser.efg.toString())
                    binding!!.hfInput.setText(laser.hf.toString())
                    binding!!.aopInput.setText(laser.aop.toString())

                    if (output.pump_type == 1) {
                        binding!!.upCardText.text = "CW pump mode"
                    }

                    //добавление доп. карточки
                    if (pulse?.giantPulseId == null) {
                        binding!!.tmUpLayout.visibility = View.VISIBLE
                        binding!!.cardPulse.visibility = View.GONE
                        binding!!.tmUpInput.setText(laser.tm.toString())
                    } else {
                        binding!!.cardPulse.visibility = View.VISIBLE
                        if (output.shutter == 1) {
                            binding!!.tmDownInputlayout.hint =
                                "Peak pulse delay after Q-switch on, ns"
                        }
                        binding!!.tmInput.setText(pulse?.tm.toString())
                        binding!!.tgInput.setText(pulse?.tg.toString())
                        binding!!.eqgInput.setText(pulse?.eqg.toString())
                        binding!!.hqInput.setText(pulse?.hq.toString())
                        binding!!.estInput.setText(pulse?.est.toString())

                        if (output.shutter == 2) {
                            binding!!.cardSecondPulse.visibility = View.VISIBLE
                            binding!!.eqg2Input.setText(pulse?.eqg2.toString())
                            binding!!.contrastInput.setText(pulse?.contrast.toString())
                        }
                    }

                }
                heh++
            }
        }

        binding!!.allGraphButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_resultFragment_to_allGraph)
        }
    }

    private fun readDataFromFile(fileName: String): List<Entry> {
        val entries = mutableListOf<Entry>()
        val resId = resources.getIdentifier(fileName, "raw", requireContext().packageName)
        val inputStream = resources.openRawResource(resId)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var line: String? = reader.readLine()
        var x = 0
        while (line != null) {
            val y = line.toFloat()
            entries.add(Entry(x.toFloat(), y))
            x++
            line = reader.readLine()
        }

        reader.close()
        return entries
    }

    override fun onResume() {
        super.onResume()
        updateToolbar()
    }

    override fun onPause() {
        super.onPause()
        resetToolbar()
    }

    private fun updateToolbar() {
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
        toolbar?.let {
            val titleTextView: TextView? = it.findViewById(R.id.textView6)
            titleTextView?.text = "Результат симуляции"
        }
    }

    private fun resetToolbar() {
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
        toolbar?.let {
            val titleTextView: TextView? = it.findViewById(R.id.textView6)
            titleTextView?.text = "Default Title"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("resultError", "это я)")
        viewModel.flag = false
        viewModel.hehe = 1
        binding = null
    }
}