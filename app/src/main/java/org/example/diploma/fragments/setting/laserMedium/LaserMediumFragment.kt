package org.example.diploma.fragments.setting.laserMedium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.example.diploma.R
import org.example.diploma.databinding.FragmentLaserMediumBinding
import org.example.diploma.databinding.FragmentSettingBinding


class LaserMediumFragment : Fragment() {

    private var binding: FragmentLaserMediumBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLaserMediumBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}