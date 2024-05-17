package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import org.example.diploma.MainActivity
import org.example.diploma.R
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentMainPageBinding
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory


class MainPageFragment : Fragment() {

    private var binding: FragmentMainPageBinding? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(inflater, container, false)


        binding!!.selectMediumButton.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_mainPageFragment_to_selectLaserMediumFragment)
        }

        binding!!.examplesButton.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_mainPageFragment_to_usageExamplesFragment)
        }

        binding!!.informationButton.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_mainPageFragment_to_referenceInformationFragment)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setToolbarTitle("Главная страница")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}