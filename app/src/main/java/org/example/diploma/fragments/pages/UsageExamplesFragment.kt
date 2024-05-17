package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.example.diploma.R

import org.example.diploma.databinding.FragmentUsageExamplesBinding


class UsageExamplesFragment : Fragment() {

    private var binding: FragmentUsageExamplesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsageExamplesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        val headline  = activity.findViewById<TextView>(R.id.textView6)
        headline.text = "Примеры использования"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}