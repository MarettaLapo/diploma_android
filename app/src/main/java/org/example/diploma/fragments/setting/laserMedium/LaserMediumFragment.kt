package org.example.diploma.fragments.setting.laserMedium

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentLaserMediumBinding
import org.example.diploma.databinding.FragmentSettingBinding


class LaserMediumFragment : Fragment() {

    private var binding: FragmentLaserMediumBinding? = null

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
        binding = FragmentLaserMediumBinding.inflate(inflater, container, false)
        return binding!!.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val main = binding!!.linMain
//        val host = viewModel.laserDataFlow.value.laserMedium.host
//        when (host) {
//            "Er" -> {
//                val cardViewEr = MaterialCardView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    addView(createLinearLayoutEr(context)) // Добавление LinearLayout в качестве дочернего элемента
//                }
//
//                val cardViewYb = MaterialCardView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    addView(createLinearLayoutYb(context)) // Добавление LinearLayout в качестве дочернего элемента
//                }
//
//                main.addView(cardViewEr)
//                main.addView(cardViewYb)
//            }
//            "Nd" -> {
//                val cardViewNd = MaterialCardView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    addView(createLinearLayoutNd(context)) // Добавление LinearLayout в качестве дочернего элемента
//                }
//                main.addView(cardViewNd)
//            }
//            "Yb" -> {
//                val cardViewYb = MaterialCardView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    addView(createLinearLayoutYbOnly(context)) // Добавление LinearLayout в качестве дочернего элемента
//                }
//                main.addView(cardViewYb)
//            }
//            else -> {
//                val cardViewGeneral = MaterialCardView(context).apply {
//                    layoutParams = ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    addView(createLinearLayoutGeneral(context)) // Добавление LinearLayout в качестве дочернего элемента
//                }
//                main.addView(cardViewGeneral)
//            }
//
//        }
//
//        val cardViewAddition = MaterialCardView(context).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            addView(createLinearLayoutAdditions(context)) // Добавление LinearLayout в качестве дочернего элемента
//        }
//        main.addView(cardViewAddition)
//    }

//    private fun createLinearLayoutEr(context: Context?): LinearLayout {
//
//    }
//
//    private fun createLinearLayoutYb(context: Context?): LinearLayout {
//
//    }
//
//    private fun createLinearLayoutNd(context: Context?): LinearLayout {
//
//    }
//
//    private fun createLinearLayoutYbOnly(context: Context?): LinearLayout {
//
//    }
//
//    private fun createLinearLayoutGeneral(context: Context?): LinearLayout {
//
//    }




    // Создание LinearLayout
    fun createLinearLayoutAdditions(context: Context): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        // Создание и добавление TextInputLayout с TextInputEditText в LinearLayout
        lateinit var textInputLayouts: Array<String>
        lateinit var editTextIds : Array<Int>

        val textInputLayoutsEr = arrayOf(
            "Active element refractive index",
            "Lasing wavelength, nm",
            "Degeneracy factor",
            "Absorption coefficient, cm^-1",
            "Spectra overlap coefficient",
            "Operation temperature, K",
        ) // Список подсказок для TextInputLayout
        val editTextIdsEr = arrayOf(
            R.id.ne,
            R.id.l0,
            R.id.m,
            R.id.ac,
            R.id.ks,
            R.id.ot
        ) // Список идентификаторов для TextInputEditText

        val textInputLayoutsNd = arrayOf(
            "Active element refractive index",
            "Lasing wavelength, nm",
            "Excitation quantum yield",
            "Degeneracy factor",
            "Branching of luminescence",
            "Operation temperature, K",
        ) // Список подсказок для TextInputLayout
        val editTextIdsNd = arrayOf(
            R.id.ne,
            R.id.l0,
            R.id.hl,
            R.id.m,
            R.id.lb,
            R.id.ot
        ) // Список идентификаторов для TextInputEditText

        val textInputLayoutsYb = arrayOf(
            "Active element refractive index",
            "Lasing wavelength, nm",
            "Degeneracy factor",
            "Operation temperature, K",
        ) // Список подсказок для TextInputLayout
        val editTextIdsYb = arrayOf(
            R.id.ne,
            R.id.l0,
            R.id.m,
            R.id.ot
        ) // Список идентификаторов для TextInputEditText

        val textInputLayoutsGeneral = arrayOf(
            "Active element refractive index",
            "Lasing wavelength, nm",
            "Absorption coefficient, cm^-1",
            "Spectra overlap coefficient",
            "Degeneracy factor",
            "Transfer coefficient, cm^3 s^-1",
            "Back transfer coefficient, a.u.",
            "Peak absorption cross-section, cm^2=",
            "Stimulated emission cross-section, cm^2",
            "Concentration of sensitizer ion, cm^-3=",
            "Concentration of working ion, cm^-3=",
            "Excited state absorption cross section for lasing wavelengh, cm^2=",
            "Excited state absorption cross section for pump wavelengh, cm^2=",
            "Operation temperature, K",
        ) // Список подсказок для TextInputLayout
        val editTextIdsGeneral = arrayOf(
            R.id.ne,
            R.id.l0,
            R.id.ac,
            R.id.ks,
            R.id.m,
            R.id.k,
            R.id.a,
            R.id.s0p,
            R.id.se,
            R.id.nsion,
            R.id.nwion,
            R.id.sa_wi,
            R.id.sa_wip,
            R.id.ot
        ) // Список идентификаторов для TextInputEditText

        val host = viewModel.laserDataFlow.value.laserMedium.host

        when (host) {
            "Er" -> {
                textInputLayouts = textInputLayoutsEr
                editTextIds = editTextIdsEr
            }
            "Nd" -> {
                textInputLayouts = textInputLayoutsNd
                editTextIds = editTextIdsNd
            }
            "Yb" -> {
                textInputLayouts = textInputLayoutsYb
                editTextIds = editTextIdsYb
            }
            else -> {
                textInputLayouts = textInputLayoutsGeneral
                editTextIds = editTextIdsGeneral
            }

        }

        textInputLayouts.forEachIndexed { index, hint1 ->
            val textInputLayout = TextInputLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            }

            val textInputEditText = TextInputEditText(context).apply {
                id = editTextIds[index]
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = hint1
            }

            textInputLayout.addView(textInputEditText)
            linearLayout.addView(textInputLayout)
        }

        return linearLayout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}