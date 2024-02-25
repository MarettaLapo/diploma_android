package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.example.diploma.R
import org.example.diploma.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {

    private var binding: FragmentMainPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(inflater, container, false)

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