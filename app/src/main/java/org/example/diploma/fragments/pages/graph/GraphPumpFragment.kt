package org.example.diploma.fragments.pages.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentGraphPumpBinding
import org.example.diploma.databinding.FragmentGraphUBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class GraphPumpFragment : Fragment(){
    private var binding: FragmentGraphPumpBinding? = null

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

    var hehe = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraphPumpBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.output.collect{
//                if (hehe != 0){
                val lineChart = binding!!.lineChart
                //Log.d("resultHHHH", uFile)
                val data1Entries = readDataFromFile(it.output_power!!)
                val data2Entries = readDataFromFile(it.pump!!)

                val dataSet1 = LineDataSet(data1Entries, "Output power, ΔP = " + it.del_sout.toString() + " kW").apply {
                    color = R.color.black
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.LINEAR
                    lineWidth = 2f// Или Mode.CUBIC_BEZIER для сглаживания
                }
                val dataSet2 = LineDataSet(data2Entries, "Pump, ΔP = " + it.del_p.toString()).apply {
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
//                }
//                hehe++
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
