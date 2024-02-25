package org.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.example.diploma.adapters.ExpandableListAdapter
import org.example.diploma.databinding.FragmentSelectLaserMediumBinding
import org.example.diploma.databinding.FragmentSettingBinding


class SelectLaserMediumFragment : Fragment() {

    private var binding: FragmentSelectLaserMediumBinding? = null

    private val header: MutableList<String> = ArrayList()
    private val body: MutableList<MutableList<String>> = ArrayList()


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

        expandableListView.setAdapter(ExpandableListAdapter(this.context, header, body, expandableListView, fab))

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}