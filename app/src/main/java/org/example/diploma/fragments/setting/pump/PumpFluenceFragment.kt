package org.example.diploma.fragments.setting.pump

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentPumpBinding
import org.example.diploma.databinding.FragmentPumpFluenceBinding


class PumpFluenceFragment : DialogFragment() {

    private var binding: FragmentPumpFluenceBinding? = null
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
        binding = FragmentPumpFluenceBinding.inflate(inflater, container, false)
        Log.d("hehe", "ya tyt")
        return binding!!.root
    }

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    Log.d("laser", "yaya")
                    binding!!.p0EditText.setText(viewModel.laserDataFlow.value.p0.toString())
                    binding!!.isspEditText.setText(viewModel.laserDataFlow.value.issp.toString())
                    binding!!.abcEditText.setText(viewModel.laserDataFlow.value.laserMedium.ac.toString())
                    binding!!.refEditText.setText(viewModel.laserDataFlow.value.pump.rp.toString())
                    binding!!.plEditText.setText(viewModel.laserDataFlow.value.lp.toString())
                    binding!!.pavEditText.setText(viewModel.laserDataFlow.value.pav.toString())
                }
                lastTimestampDisplayed = laser.timestamp
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

