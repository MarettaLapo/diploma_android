package org.example.diploma.fragments.setting.pump

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentPumpFluenceBinding
import org.example.diploma.databinding.FragmentPumpWaveformBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PumpWaveformFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PumpWaveformFragment : DialogFragment() {

    private var binding: FragmentPumpWaveformBinding? = null
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
        binding = FragmentPumpWaveformBinding.inflate(inflater, container, false)
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
                    binding!!.tpEditText.setText(viewModel.laserDataFlow.value.pump.tp.toString())
                    binding!!.wpEditText.setText(viewModel.laserDataFlow.value.pump.wp.toString())
                    binding!!.epEditText.setText(viewModel.laserDataFlow.value.ep.toString())
                    binding!!.pEditText.setText(viewModel.laserDataFlow.value.p.toString())

                    if(laser.pump.pformId == 0){
                        binding!!.pumpWaveform.check(binding!!.waveform1.id)
                    }
                    else{
                        binding!!.pumpWaveform.check(binding!!.waveform2.id)
                        val textInputLayout1 = TextInputLayout(requireContext())
                        val editText1 = TextInputEditText(requireContext())
                        editText1.hint = "t1, us"
                        textInputLayout1.addView(editText1)

                        // Создание второго TextInputLayout
                        val textInputLayout2 = TextInputLayout(requireContext())
                        val editText2 = TextInputEditText(requireContext())
                        editText2.hint = "t2, us"
                        textInputLayout2.addView(editText2)

                        binding!!.lin.addView(textInputLayout1)
                        binding!!.lin.addView(textInputLayout2)
                    }
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