package org.example.diploma.fragments.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.example.diploma.R
import org.example.diploma.databinding.FragmentAmpResultBinding
import org.example.diploma.databinding.FragmentAmplifierBinding

class AmpResultFragment : Fragment() {
    private var binding: FragmentAmpResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmpResultBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as AppCompatActivity
        val headline = activity.findViewById<TextView>(R.id.textView6)
        headline.text = "Результат симуляции"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}