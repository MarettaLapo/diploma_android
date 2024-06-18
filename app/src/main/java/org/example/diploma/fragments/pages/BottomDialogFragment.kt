package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.BottomSheetDialogBinding
import org.example.diploma.fragments.setting.NavigationListener
import org.example.diploma.fragments.setting.amplifier.AmplifierFragment
import org.example.diploma.fragments.setting.pump.PumpWaveformFragment

class BottomDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var navigationListener: NavigationListener? = null

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
    ): View {
        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.laserDataFlow.value.laserMedium.host != "Nd"){
            binding.item1.visibility = View.GONE
        }
        binding.item1.setOnClickListener {
            val hehe = AmplifierFragment()
            hehe.show(parentFragmentManager, "AmplifierDialog")
        }

        binding.item2.setOnClickListener {
            navigationListener?.navigateToReferenceInformation()
            dismiss()
        }
    }

    fun setNavigationListener(listener: NavigationListener) {
        navigationListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}