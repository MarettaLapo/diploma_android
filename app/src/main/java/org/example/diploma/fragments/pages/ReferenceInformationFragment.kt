package org.example.diploma.fragments.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentReferenceInformationBinding
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.fragments.MainViewModel
import org.example.diploma.fragments.setting.SettingViewModel
import org.example.diploma.fragments.setting.SettingViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ReferenceInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReferenceInformationFragment : Fragment() {

    private var binding: FragmentReferenceInformationBinding? = null
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory((activity?.applicationContext as AppApplication).hostRepository)
    }

    val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReferenceInformationBinding.inflate(inflater, container, false)

        mainViewModel.hehe.observe(viewLifecycleOwner) {
            Log.d("catt", it.toString())
        }
        return binding!!.root
    }


    
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}