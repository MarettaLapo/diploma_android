package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.example.diploma.R

/**
 * A simple [Fragment] subclass.
 * Use the [LaserMediumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaserMediumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laser_medium, container, false)
    }
}