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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.adapters.SettingAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.databinding.MainActivityBinding
import org.example.diploma.fragments.pages.BottomDialogFragment


class SettingFragment : Fragment(), NavigationListener {
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
            (activity?.applicationContext as AppApplication).saveRepository,
            (activity?.applicationContext as AppApplication).outputRepository,
            (activity?.applicationContext as AppApplication).laserOutputRepository,
            (activity?.applicationContext as AppApplication).giantPulseRepository,
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

        val frag = activity.findViewById<FragmentContainerView>(R.id.appNavHostFragment)

        frag.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = 0
        }

        calculationButton.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        additionButton.visibility = View.VISIBLE

        saveButton.setOnClickListener{
            lifecycleScope.launch {
                viewModel.save()
                val toast = Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        additionButton.setOnClickListener {
            val bottomSheetDialog = BottomDialogFragment()
            bottomSheetDialog.setNavigationListener(this)
            bottomSheetDialog.show(parentFragmentManager, "MyBottomSheetDialog")
        }

        calculationButton.setOnClickListener {
            if(viewModel.isAmplifier.value){
                Navigation.findNavController(view)
                    .navigate(R.id.action_settingFragment_to_ampResultFragment)
            }
            else {
                viewModel.selectedGraph()
                Navigation.findNavController(view)
                    .navigate(R.id.action_settingFragment_to_resultFragment)
            }
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

    override fun navigateToReferenceInformation() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_settingFragment_to_referenceInformationFragment)
        }
    }

    override fun navigateToUsageExamplesFragment() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_settingFragment_to_usageExamplesFragment)
        }
    }
}

interface NavigationListener {
    fun navigateToReferenceInformation()
    fun navigateToUsageExamplesFragment()
}