package org.example.diploma.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.example.diploma.adapters.ExpandableListAdapter
import org.example.diploma.database.AppApplication
import org.example.diploma.databinding.FragmentSelectLaserMediumBinding
import org.example.diploma.databinding.FragmentSettingBinding
import org.example.diploma.fragments.setting.SettingViewModel
import org.example.diploma.fragments.setting.SettingViewModelFactory


class SelectLaserMediumFragment : Fragment() {

    private var binding: FragmentSelectLaserMediumBinding? = null

    private val header: MutableList<String> = ArrayList()
    private val body: MutableList<MutableList<String>> = ArrayList()

    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory((activity?.applicationContext as AppApplication).hostRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectLaserMediumBinding.inflate(inflater, container, false)

        header.clear()
        body.clear()

        header.add("Er:Host")
        header.add("Nd:Host")
        header.add("Yb:Host")

        val erHost: MutableList<String> = ArrayList()
        erHost.add("Er:Yb:glass")

        val ndHost: MutableList<String> = ArrayList()
        ndHost.add("Nd:YAG")
        ndHost.add("Nd:YLF-PI")
        ndHost.add("Nd:YLF-S")
        ndHost.add("Nd:YAP")
        ndHost.add("Nd:YVO")
        ndHost.add("Nd:KGW")

        val ybHost: MutableList<String> = ArrayList()
        ybHost.add("Yb:YAG")

        body.add(erHost)
        body.add(ndHost)
        body.add(ybHost)

        val expandableListView = binding!!.expandableListView

        val fab = binding!!.floatingActionButton
        fab.hide()

        expandableListView.setAdapter(ExpandableListAdapter(this.context, header, body, expandableListView, fab, settingViewModel))
        settingViewModel.hostId.value = 10L
        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}