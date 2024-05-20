package org.example.diploma.fragments.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.adapters.SettingAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.databinding.MainActivityBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var adapter: SettingAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var settingTab: TabLayout


    private var binding: FragmentSettingBinding? = null

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
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SettingAdapter(this)
        viewPager = binding!!.settingPager
        viewPager.adapter = adapter

        settingTab = binding!!.settingTab
        TabLayoutMediator(settingTab, viewPager) { tab, position ->
            Log.d("laser", position.toString())
            when (position) {
                0 -> tab.text = "Medium"
                1 -> tab.text = "Config"
                2 -> tab.text = "Pump"
                3 -> tab.text = "Q-Switch"
            }

        }.attach()

        val activity = activity as AppCompatActivity
        val calculationButton = activity.findViewById<ImageButton>(R.id.button)
        val saveButton = activity.findViewById<ImageButton>(R.id.button2)
        val additionButton = activity.findViewById<ImageButton>(R.id.button3)

        calculationButton.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        additionButton.visibility = View.VISIBLE

        val bottomSheetDialog = BottomSheetDialog(requireContext())

// Создаем MaterialAlertDialogBuilder
        val builder = MaterialAlertDialogBuilder(requireContext())

// Устанавливаем заголовок
        builder.setTitle("Заголовок")

// Устанавливаем сообщение
        builder.setMessage("Сообщение")

// Добавляем кнопки
        builder.setPositiveButton("Подтвердить") { dialog, which ->
            // Обработка нажатия на кнопку "Подтвердить"
        }

        builder.setNegativeButton("Отмена") { dialog, which ->
            // Обработка нажатия на кнопку "Отмена"
        }

// Получаем диалог из билдера и устанавливаем его в BottomSheetDialog
        val dialog = builder.create()
        bottomSheetDialog.setContentView(dialog.window!!.decorView.rootView)

// Показываем BottomSheetDialog


        additionButton.setOnClickListener {
            Log.d("laser", "hehe")
            bottomSheetDialog.show()
        }

        calculationButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_settingFragment_to_resultFragment)
        }


        val headline = activity.findViewById<TextView>(R.id.textView6)
        headline.text = "Настройка лазера"
    }

    override fun onStop() {
        super.onStop()
        val activity = activity as AppCompatActivity
        val calculationButton = activity.findViewById<ImageButton>(R.id.button)
        val saveButton = activity.findViewById<ImageButton>(R.id.button2)
        val additionButton = activity.findViewById<ImageButton>(R.id.button3)

        calculationButton.visibility = View.GONE
        saveButton.visibility = View.GONE
        additionButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}