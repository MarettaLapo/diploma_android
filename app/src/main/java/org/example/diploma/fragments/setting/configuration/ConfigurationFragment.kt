package org.example.diploma.fragments.setting.configuration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentConfigurationBinding
import org.example.diploma.fragments.setting.SettingViewModel
import org.example.diploma.fragments.setting.SettingViewModelFactory

class ConfigurationFragment : Fragment() {

    private var binding: FragmentConfigurationBinding? = null
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory((activity?.applicationContext as AppApplication).hostRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        binding!!.settingModel = settingViewModel
//        Log.d("catt", settingViewModel.host.toString())
//        settingViewModel.hostId.observe(viewLifecycleOwner) { host ->
//            // Update the cached copy of the words in the adapter.
////            binding!!.jeje.setText(host.host)
//            Log.d("catt", host.toString())
//        }
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}