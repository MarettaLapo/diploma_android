package org.example.diploma.fragments.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.example.diploma.MainViewModel
import org.example.diploma.MainViewModelFactory
import org.example.diploma.R
import org.example.diploma.adapters.SelectMediumAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.database.host.HostEntity
import org.example.diploma.databinding.FragmentSelectLaserMediumBinding
import org.koin.androidx.scope.fragmentScope


class SelectLaserMediumFragment : Fragment() {

    private var binding: FragmentSelectLaserMediumBinding? = null

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectLaserMediumBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = binding!!.recyclerView
        val adapter = SelectMediumAdapter()
        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.adapter = adapter

        adapter.setOnClickListener(object :
            SelectMediumAdapter.OnClickListener {
            override fun onClick(position: Int, model: HostEntity, view: View) {

                viewModel.selectedHost(model)
                Navigation.findNavController(view)
                    .navigate(R.id.action_selectLaserMediumFragment_to_settingFragment)

            }
        })

        lifecycleScope.launch {
            viewModel.allHosts.collect { hosts ->
                adapter.setListHosts(ArrayList(hosts))
                adapter.notifyDataSetChanged()
            }
        }

        val activity = activity as AppCompatActivity
        val headline = activity.findViewById<TextView>(R.id.textView6)
        headline.text = "Выбор среды"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}