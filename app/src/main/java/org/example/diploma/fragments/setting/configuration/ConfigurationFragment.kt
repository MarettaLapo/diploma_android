package org.example.diploma.fragments.setting.configuration

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentConfigurationBinding

class ConfigurationFragment : Fragment() {

    private var binding: FragmentConfigurationBinding? = null
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
        binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                Log.d("laser", (laser.timestamp > lastTimestampDisplayed).toString())
                if (laser.timestamp > lastTimestampDisplayed) {
                    Log.d("laser", "yaya")
                    val ld = binding!!.ldEditText
                    val lb = binding!!.lbEditText
                    val dia = binding!!.diaEditText
                    if (viewModel.laserDataFlow.value.configuration.isCylinder == true) {
                        ld.focusable = View.NOT_FOCUSABLE
                        ld.inputType = InputType.TYPE_NULL

                        lb.focusable = View.NOT_FOCUSABLE
                        lb.inputType = InputType.TYPE_NULL
                        ld.setBackgroundColor(Color.parseColor("#E6E0E9"))
                        lb.setBackgroundColor(Color.parseColor("#E6E0E9"))
                    } else {
                        dia.focusable = View.NOT_FOCUSABLE
                        dia.inputType = InputType.TYPE_NULL
                        dia.setBackgroundColor(Color.parseColor("#E6E0E9"))
                    }

                    binding!!.switchCompat.isChecked =
                        viewModel.laserDataFlow.value.configuration.isCylinder!!
                    binding!!.laEditText.setText(viewModel.laserDataFlow.value.configuration.la.toString())
                    ld.setText(viewModel.laserDataFlow.value.configuration.ld.toString())
                    lb.setText(viewModel.laserDataFlow.value.configuration.lb.toString())
                    dia.setText(viewModel.laserDataFlow.value.configuration.dia.toString())
                    binding!!.lcEditText.setText(viewModel.laserDataFlow.value.configuration.lc.toString())
                    binding!!.rocEditText.setText(viewModel.laserDataFlow.value.configuration.roc.toString())
                    binding!!.lcEditText.setText(viewModel.laserDataFlow.value.configuration.lc.toString())
                    binding!!.gaEditText.setText(viewModel.laserDataFlow.value.configuration.ga.toString())
                    binding!!.gcEditText.setText(viewModel.laserDataFlow.value.configuration.gc.toString())
                    binding!!.hovEditText.setText(viewModel.laserDataFlow.value.configuration.hov.toString())
                    binding!!.agEditText.setText(viewModel.laserDataFlow.value.ag.toString())
                    binding!!.tmEditText.setText(viewModel.laserDataFlow.value.TM.toString())

                    val im = binding!!.imageScheme
                    if (viewModel.laserDataFlow.value.configuration.isCylinder == true) {
                        if (viewModel.laserDataFlow.value.pump.scheme == 0) {
                            when (viewModel.laserDataFlow.value.shutter) {
                                0 -> im.setImageResource(
                                    R.drawable.schema_free_cyl_side
                                )

                                1 ->
                                    im.setImageResource(
                                        R.drawable.schema_aqs_cyl_side
                                    )

                                2 -> im.setImageResource(
                                    R.drawable.schema_pqs_cyl_side
                                )

                                else -> im.setImageResource(
                                    R.drawable.schema_aqs_pqs_cyl_side
                                )
                            }
                        } else {
                            when (viewModel.laserDataFlow.value.shutter) {
                                0 -> im.setImageResource(
                                    R.drawable.schema_free_cyl_end
                                )

                                1 -> im.setImageResource(
                                    R.drawable.schema_aqs_cyl_end
                                )

                                2 -> im.setImageResource(
                                    R.drawable.schema_pqs_cyl_end
                                )

                                else -> im.setImageResource(
                                    R.drawable.schema_aqs_pqs_cyl_end
                                )
                            }
                        }

                    } else {
                        if (viewModel.laserDataFlow.value.pump.scheme == 0) {
                            when (viewModel.laserDataFlow.value.shutter) {
                                0 -> im.setImageResource(
                                    R.drawable.schema_free_rect_side
                                )

                                1 -> im.setImageResource(
                                    R.drawable.schema_aqs_rect_side
                                )

                                2 -> im.setImageResource(
                                    R.drawable.schema_pqs_rect_side
                                )

                                else -> im.setImageResource(
                                    R.drawable.schema_aqs_pqs_rect_side
                                )
                            }
                        } else {
                            when (viewModel.laserDataFlow.value.shutter) {
                                0 -> im.setImageResource(
                                    R.drawable.schema_free_rect_end
                                )

                                1 -> im.setImageResource(
                                    R.drawable.schema_aqs_rect_end
                                )

                                2 -> im.setImageResource(
                                    R.drawable.schema_pqs_rect_end
                                )

                                else -> im.setImageResource(
                                    R.drawable.schema_aqs_pqs_rect_end
                                )
                            }
                        }
                    }

                    if (viewModel.laserDataFlow.value.pump.scheme == 0) {
                        binding!!.viewText.text = "Active element sidepump orientation."
                    } else {
                        binding!!.viewText.text = "Active element endpump orientation."
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