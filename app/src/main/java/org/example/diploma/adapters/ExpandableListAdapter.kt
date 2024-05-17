package org.example.diploma.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.example.diploma.R
import org.w3c.dom.Text

class ExpandableListAdapter(
    var context: Context?,
    var header: MutableList<String>,
    var body: MutableList<MutableList<String>>,
    var expandableListView: ExpandableListView,
    var fab: FloatingActionButton
) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return body[p0].size
    }

    override fun getGroup(p0: Int): String {
        return header[p0]
    }

    override fun getChild(p0: Int, p1: Int): String {
        Log.d("hee", p1.toString())
        return body[p0][p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View? {
        var convertView = p2
        if (convertView == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_group, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_title)
        title?.text = getGroup(p0)
        title?.setOnClickListener {
            if (expandableListView.isGroupExpanded(p0)) {
                expandableListView.collapseGroup(p0)
            } else {
                expandableListView.expandGroup(p0)
            }
        }
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View? {
        var convertView = p3
        if (convertView == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_child, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_title)
        title?.text = getChild(p0, p1)
        title?.setOnClickListener {
            fab.show()
        }
//        fab.setOnClickListener {
//            view: View ->
//            Navigation.findNavController(view).navigate(R.id.action_selectLaserMediumFragment_to_settingFragment)
//        }

        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}