package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.example.diploma.R
import org.example.diploma.databinding.FragmentReferenceInformationBinding
import org.example.diploma.databinding.FragmentSettingBinding

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
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Справочная информация"
        }
        return binding!!.root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}