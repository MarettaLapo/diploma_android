package org.example.diploma.fragments.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectLaserMediumBinding.inflate(inflater, container, false)
        val rv = binding!!.recyclerView
        val adapter = SelectMediumAdapter()
        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.adapter = adapter

        adapter.setOnClickListener(object :
            SelectMediumAdapter.OnClickListener{
                override fun onClick(position: Int, model: HostEntity, view: View) {
                    viewModel.selectedHost(model)
                    Log.d("host", model.toString())
                    Navigation.findNavController(view).navigate(R.id.action_selectLaserMediumFragment_to_settingFragment)
                }
            } )

        viewModel.allHosts.observe(viewLifecycleOwner) {
            adapter.setListHosts(ArrayList(it))
            adapter.notifyDataSetChanged()
        }

//        lifecycleScope.launch {
//            Log.d("hehe", viewModel.getCombinedDataFlow().value.toString())
//        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}