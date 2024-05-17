package org.example.diploma.fragments.setting.laserMedium

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.core.widget.TextViewCompat.setTextAppearance
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentLaserMediumBinding
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.laser.Laser


class LaserMediumFragment : Fragment() {

    private var binding: FragmentLaserMediumBinding? = null
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
        binding = FragmentLaserMediumBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val main = binding!!.linMain
        lifecycleScope.launch {
            viewModel.laserDataFlow.collect { laser ->
                if (laser.timestamp > lastTimestampDisplayed) {
                    val host = laser.laserMedium.host
                    Log.d("laser1", host.toString())
                    when (host) {
                        "Er" -> {
                            val cardViewEr = MaterialCardView(context).apply {
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                ).apply {
                                    topMargin = 30
                                }
                                addView(
                                    createLinearLayoutEr(
                                        context,
                                        laser
                                    )
                                ) // Добавление LinearLayout в качестве дочернего элемента
                            }

                            val cardViewYb = MaterialCardView(context).apply {
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                ).apply {
                                    topMargin = 30
                                }
                                addView(
                                    createLinearLayoutYb(
                                        context,
                                        laser
                                    )
                                ) // Добавление LinearLayout в качестве дочернего элемента
                            }
                            Log.d("laser1", "Er here")
                            main.addView(cardViewEr)
                            main.addView(cardViewYb)
                        }

                        "Nd" -> {
                            val cardViewNd = MaterialCardView(context).apply {
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                ).apply {
                                    topMargin = 30
                                }
                                addView(
                                    createLinearLayoutNd(
                                        context,
                                        laser
                                    )
                                ) // Добавление LinearLayout в качестве дочернего элемента
                            }
                            Log.d("laser1", "Nd here")
                            main.addView(cardViewNd)
                        }

                        "Yb" -> {
                            val cardViewYb = MaterialCardView(context).apply {
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                ).apply {
                                    topMargin = 30
                                }
                                addView(
                                    createLinearLayoutYbOnly(
                                        context,
                                        laser
                                    )
                                ) // Добавление LinearLayout в качестве дочернего элемента
                            }
                            Log.d("laser1", "Yb here")
                            main.addView(cardViewYb)
                        }

                        else -> {
                            val cardViewGeneral = MaterialCardView(context).apply {
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                ).apply {
                                    topMargin = 30
                                }
                                addView(
                                    createLinearLayoutGeneral(
                                        context,
                                        laser
                                    )
                                ) // Добавление LinearLayout в качестве дочернего элемента
                            }
                            Log.d("laser1", "here")
                            main.addView(cardViewGeneral)
                        }

                    }

                    val cardViewAddition = MaterialCardView(context).apply {
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.MarginLayoutParams.MATCH_PARENT,
                            ViewGroup.MarginLayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = 30
                        }
                        addView(
                            createLinearLayoutAdditions(
                                context,
                                laser
                            )
                        ) // Добавление LinearLayout в качестве дочернего элемента
                    }
                    main.addView(cardViewAddition)
                }
                lastTimestampDisplayed = laser.timestamp
            }
        }
    }

    private fun createLinearLayoutEr(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "Er+"
            textSize = 20f // 18sp
        }
        linearLayout.addView(textView)

        val textInputLayouts = arrayOf(
            "Concentration Er, cm^-3",
            "Stimulated emission cross-section, cm^2",
            "Tau 32 (Er), us",
            "Tau 31 (Er), us",
            "Tau 21 (Er), us"
        ) // Список подсказок для TextInputLayout
        val editTextIds = arrayOf(
            R.id.ner,
            R.id.se,
            R.id.t32,
            R.id.t31,
            R.id.t21
        ) // Список идентификаторов для TextInputEditText

        val editTextValue = arrayOf(
            laser.laserMedium.ner,
            laser.laserMedium.se,
            laser.laserMedium.t32.toString(),
            laser.laserMedium.t31.toString(),
            laser.laserMedium.t21.toString()
        )

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
                setText(editTextValue[index])
                hint = hint1
            }




            textInputLayout.addView(textInputEditText)
            linearLayout.addView(textInputLayout)
        }

        return linearLayout
    }

    private fun createLinearLayoutYb(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "Yb+"
            textSize = 20f // 18sp
        }

        linearLayout.addView(textView)

        val textInputLayouts = arrayOf(
            "Concentration Yb, cm^-3",
            "Peak absorption cross-section, cm^2",
            "Tau 31 (Yb), us",
            "Transfer coefficient, cm^3 s^-1",
            "Back transfer coefficient, a.u."
        ) // Список подсказок для TextInputLayout
        val editTextIds = arrayOf(
            R.id.nyb,
            R.id.s0p,
            R.id.t31Yb,
            R.id.k,
            R.id.a
        ) // Список идентификаторов для TextInputEditText

        val editTextValue = arrayOf(

            laser.laserMedium.nyb,
            laser.laserMedium.s0p,
            laser.laserMedium.t31Yb.toString(),
            laser.laserMedium.k,
            laser.laserMedium.a.toString()
        ) // Список идентификаторов для TextInputEditText

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
                setText(editTextValue[index])
            }

            textInputLayout.addView(textInputEditText)
            linearLayout.addView(textInputLayout)
        }

        return linearLayout
    }

    private fun createLinearLayoutNd(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "Nd+"
            textSize = 20f // 18sp
        }

        linearLayout.addView(textView)

        val textInputLayouts = arrayOf(
            "Concentration Nd, cm^-3",
            "Concentration Nd, at %",
            "Peak absorption cross-section, cm^2",
            "Absorption coefficient, cm^-1",
            "Spectra overlap coefficient",
            "Stimulated emission cross-section, cm^2",
            "Stimulated emission cross-section(1.32 um), cm^2",
            "Stimulated emission cross-section(1.44 um), cm^2",
            "Tau 32, us",
            "Tau 43, us",
            "Tau 31, us",
            "Tau 21, us",
        ) // Список подсказок для TextInputLayout
        val editTextIds = arrayOf(
            R.id.nd,
            R.id.nd_proc,
            R.id.s0p,
            R.id.ac,
            R.id.ks,
            R.id.se,
            R.id.se32,
            R.id.se44,
            R.id.t32,
            R.id.t43,
            R.id.t31,
            R.id.t21,
        )
        val editTextValue = arrayOf(
            laser.laserMedium.nd.toString(),
            "1.0",
            laser.laserMedium.s0p.toString(),
            laser.laserMedium.ac.toString(),
            laser.laserMedium.ks.toString(),
            laser.laserMedium.se.toString(),
            "1.450E-19",
            "3.800E-20",
            laser.laserMedium.t32.toString(),
            laser.laserMedium.t43.toString(),
            laser.laserMedium.t31.toString(),
            laser.laserMedium.t21.toString(),
        )// Список идентификаторов для TextInputEditText

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
                setText(editTextValue[index])
            }

            textInputLayout.addView(textInputEditText)
            linearLayout.addView(textInputLayout)
        }
        return linearLayout
    }

    private fun createLinearLayoutYbOnly(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "Yb+"
            textSize = 20f // 18sp
        }

        linearLayout.addView(textView)

        val textInputLayouts = arrayOf(
            "Concentration Yb, cm^-3",
            "Concentration Yb, at %",
            "Peak absorption cross-section, cm^2",
            "Absorption coefficient, cm^-1",
            "Spectra overlap coefficient",
            "Stimulated emission cross-section, cm^2",
            "Tau 43, us",
            "Tau 31, us",
            "Tau 32, us",
            "Tau 21, us",
        ) // Список подсказок для TextInputLayout
        val editTextIds = arrayOf(
            R.id.nd,
            R.id.nd_proc,
            R.id.s0p,
            R.id.ac,
            R.id.ks,
            R.id.se,
            R.id.t43,
            R.id.t31,
            R.id.t32,
            R.id.t21,
        ) // Список идентификаторов для TextInputEditText

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

    private fun createLinearLayoutGeneral(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "General"
            textSize = 20f // 18sp
        }

        linearLayout.addView(textView)

        val textInputLayouts = arrayOf(
            "Concentration Nd, cm^-3",
            "Concentration Nd, at %",
            "Peak absorption cross-section, cm^2",
            "Absorption coefficient, cm^-1",
            "Spectra overlap coefficient",
            "Stimulated emission cross-section, cm^2",
            "Stimulated emission cross-section(1.32 um), cm^2",
            "Stimulated emission cross-section(1.44 um), cm^2",
            "Tau 32, us",
            "Tau 43, us",
            "Tau 31, us",
            "Tau 21, us",
        ) // Список подсказок для TextInputLayout
        val editTextIds = arrayOf(
            R.id.nd,
            R.id.nd_proc,
            R.id.s0p,
            R.id.ac,
            R.id.ks,
            R.id.se,
            R.id.se32,
            R.id.se44,
            R.id.t32,
            R.id.t43,
            R.id.t31,
            R.id.t21,
        ) // Список идентификаторов для TextInputEditText

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


    // Создание LinearLayout
    fun createLinearLayoutAdditions(context: Context, laser: Laser): LinearLayout {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
        }

        // Создание и добавление TextInputLayout с TextInputEditText в LinearLayout
        lateinit var textInputLayouts: Array<String>
        lateinit var editTextIds: Array<Int>
        lateinit var editTextValue: Array<String>

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

        val editTextValueEr = arrayOf(
            laser.laserMedium.ne.toString(),
            laser.laserMedium.l0.toString(),
            laser.laserMedium.m.toString(),
            laser.laserMedium.ac.toString(),
            laser.laserMedium.ks.toString(),
            laser.laserMedium.ot.toString()
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

        val editTextValueNd = arrayOf(
            laser.laserMedium.ne.toString(),
            laser.laserMedium.l0.toString(),
            laser.laserMedium.hl.toString(),
            laser.laserMedium.m.toString(),
            laser.laserMedium.lb.toString(),
            laser.laserMedium.ot.toString()
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

        val editTextValueYb = arrayOf(
            laser.laserMedium.ne.toString(),
            laser.laserMedium.l0.toString(),
            laser.laserMedium.m.toString(),
            laser.laserMedium.ot.toString()
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

        val editTextValueGeneral = arrayOf(
            laser.laserMedium.ne.toString(),
            laser.laserMedium.l0.toString(),
            laser.laserMedium.ac.toString(),
            laser.ks.toString(),
            laser.laserMedium.m.toString(),
            laser.laserMedium.k.toString(),
            laser.laserMedium.a.toString(),
            laser.laserMedium.s0p.toString(),
            laser.laserMedium.se.toString(),
            laser.laserMedium.nsion.toString(),
            laser.laserMedium.nwion.toString(),
            laser.laserMedium.sa_wi.toString(),
            laser.laserMedium.sa_wip.toString(),
            laser.laserMedium.ot.toString()
        ) // Список идентификаторов для TextInputEditText

        val host = viewModel.laserDataFlow.value.laserMedium.host

        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50 // 20dp
                topMargin = 20 // 5dp
                bottomMargin = 5 // 5dp
            }
            text = "Addiotional"
            textSize = 20f // 18sp
        }

        linearLayout.addView(textView)

        when (host) {
            "Er" -> {
                textInputLayouts = textInputLayoutsEr
                editTextIds = editTextIdsEr
                editTextValue = editTextValueEr
            }

            "Nd" -> {
                textInputLayouts = textInputLayoutsNd
                editTextIds = editTextIdsNd
                editTextValue = editTextValueNd
            }

            "Yb" -> {
                textInputLayouts = textInputLayoutsYb
                editTextIds = editTextIdsYb
                editTextValue = editTextValueYb
            }

            else -> {
                textInputLayouts = textInputLayoutsGeneral
                editTextIds = editTextIdsGeneral
                editTextValue = editTextValueGeneral
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
                setText(editTextValue[index])
            }

            textInputLayout.addView(textInputEditText)
            linearLayout.addView(textInputLayout)
        }

        return linearLayout
    }
//    fun clearLayout(layout: ViewGroup) {
//        // Удаляем все дочерние элементы из layout
//        Log.d("laser1", "ya")
//        while (layout.childCount > 0) {
//            val child = layout.getChildAt(0)
//            layout.removeView(child)
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        clearLayout(binding!!.linMain)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}