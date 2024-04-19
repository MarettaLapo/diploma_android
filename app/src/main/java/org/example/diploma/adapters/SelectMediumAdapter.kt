package org.example.diploma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.diploma.R
import org.example.diploma.database.host.HostEntity
import org.example.diploma.databinding.LayoutChildBinding

class SelectMediumAdapter :RecyclerView.Adapter<SelectMediumAdapter.MediumViewHolder>() {

    private var hosts = ArrayList<HostEntity>()
    private var onClickListener: OnClickListener? = null
    lateinit var view : View

    fun setListHosts(data: ArrayList<HostEntity>){
        this.hosts = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.layout_child, parent, false)
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
        val binding = LayoutChildBinding.bind(item)
        fun bind(host: HostEntity) = with(binding){
            tvTitle.text = host.type
        }
    }

//    interface CustomItemClickListener{
//        fun OnItemClickListener(host: HostEntity)
//    }
}