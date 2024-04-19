package org.example.diploma.fragments.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentReferenceInformationBinding
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ReferenceInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReferenceInformationFragment : Fragment() {

    private var binding: FragmentReferenceInformationBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReferenceInformationBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}