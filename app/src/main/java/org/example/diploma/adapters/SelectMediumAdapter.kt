package org.example.diploma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.diploma.R
import org.example.diploma.database.host.HostEntity
import org.example.diploma.databinding.LayoutChildBinding
import org.example.diploma.databinding.LayoutSavingBinding
import org.example.diploma.databinding.LayoutSelectBinding

class SelectMediumAdapter :RecyclerView.Adapter<SelectMediumAdapter.MediumViewHolder>() {

    private var hosts = ArrayList<HostEntity>()
    private var onClickListener: OnClickListener? = null
    lateinit var view : View

    fun setListHosts(data: ArrayList<HostEntity>){
        this.hosts = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.layout_select, parent, false)
        return MediumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hosts.size
    }

    override fun onBindViewHolder(holder: MediumViewHolder, position: Int) {
        val host = hosts[position]
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, host, view)
            }
        }
        holder.bind(host)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: HostEntity, view: View)
    }

    class MediumViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val binding = LayoutSelectBinding.bind(item)
        fun bind(host: HostEntity) = with(binding){
            when(host.host){
                "Er" -> idIVCourseImage.setImageResource(R.drawable.yber)
                "Nd" -> idIVCourseImage.setImageResource(R.drawable.nd)
                "Yb" -> idIVCourseImage.setImageResource(R.drawable.yb_host)
                else -> idIVCourseImage.setImageResource(R.drawable.general_withsensetizer_3level)
            }
            textt.text = host.host + ": " + host.type
        }
    }
}