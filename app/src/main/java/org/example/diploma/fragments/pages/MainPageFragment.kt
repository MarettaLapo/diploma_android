package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentMainPageBinding
import org.example.diploma.fragments.MainViewModel
import org.example.diploma.fragments.setting.SettingViewModel
import org.example.diploma.fragments.setting.SettingViewModelFactory


class MainPageFragment : Fragment() {

    private var binding: FragmentMainPageBinding? = null
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory((activity?.applicationContext as AppApplication).hostRepository)
    }

    val mainViewModel : MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(inflater, container, false)

        mainViewModel.hehe.value = 10L

        binding!!.selectMediumButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_selectLaserMediumFragment) }

        binding!!.examplesButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_usageExamplesFragment) }

        binding!!.informationButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_referenceInformationFragment) }


        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}