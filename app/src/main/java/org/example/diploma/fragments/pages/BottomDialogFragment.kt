package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.example.diploma.R
import org.example.diploma.databinding.BottomSheetDialogBinding
import org.example.diploma.fragments.setting.NavigationListener
import org.example.diploma.fragments.setting.amplifier.AmplifierFragment
import org.example.diploma.fragments.setting.pump.PumpWaveformFragment

class BottomDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.item1.setOnClickListener {
            val hehe = AmplifierFragment()
            hehe.show(parentFragmentManager, "AmplifierDialog")
        }

        binding.item2.setOnClickListener {
            navigationListener?.navigateToReferenceInformation()
            dismiss()
        }

        binding.item3.setOnClickListener {
            navigationListener?.navigateToUsageExamplesFragment()
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