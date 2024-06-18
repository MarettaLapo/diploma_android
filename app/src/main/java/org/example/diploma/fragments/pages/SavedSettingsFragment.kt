package org.example.diploma.fragments.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import org.example.diploma.adapters.SavingAdapter
import org.example.diploma.adapters.SelectMediumAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.save.SaveEntity
import org.example.diploma.databinding.FragmentSavedSettingsBinding


class SavedSettingsFragment : Fragment() {

    private var binding: FragmentSavedSettingsBinding? = null

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            (activity?.applicationContext as AppApplication).amplifierRepository,
            (activity?.applicationContext as AppApplication).configurationRepository,
            (activity?.applicationContext as AppApplication).hostRepository,
            (activity?.applicationContext as AppApplication).laserMediumRepository,
            (activity?.applicationContext as AppApplication).optimizationRepository,
            (activity?.applicationContext as AppApplication).pumpRepository,
            (activity?.applicationContext as AppApplication).qSwitchRepository,
            (activity?.applicationContext as AppApplication).saveRepository,
            (activity?.applicationContext as AppApplication).outputRepository,
            (activity?.applicationContext as AppApplication).laserOutputRepository,
            (activity?.applicationContext as AppApplication).giantPulseRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = binding!!.recyclerView
        val adapter = SavingAdapter()
        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.adapter = adapter

        adapter.setOnClickListener(object :
            SavingAdapter.OnClickListener {
            override fun onClick(position: Int, model: SaveEntity, view: View) {
                viewModel.selectedSave(model)
                Navigation.findNavController(view)
                    .navigate(R.id.action_savedSettingsFragment_to_settingFragment)
            }

            override fun onDelete(model: SaveEntity) {
                viewModel.deleteSave(model)
            }
        })

        lifecycleScope.launch {
            viewModel.allSaves.collect { saves ->
                if(saves.isEmpty()){
                    binding!!.textIn.visibility = View.VISIBLE
                    binding!!.recyclerView.visibility = View.GONE
                }
                else{
                    binding!!.textIn.visibility = View.GONE
                    binding!!.recyclerView.visibility = View.VISIBLE
                    Log.d("saveError", saves.toString())
                    Log.d("saveError", saves.size.toString())
                    adapter.setListSaves(ArrayList(saves))
                    adapter.notifyDataSetChanged()
                }
            }
        }


        val activity = activity as AppCompatActivity
        val headline  = activity.findViewById<TextView>(R.id.textView6)
        headline.text = "Сохранения"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}