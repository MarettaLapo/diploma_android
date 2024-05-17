package org.example.diploma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.diploma.R
import org.example.diploma.database.save.SaveEntity
import org.example.diploma.databinding.LayoutChildBinding
import org.example.diploma.databinding.LayoutSavingBinding

class SavingAdapter : RecyclerView.Adapter<SavingAdapter.SavesViewHolder>() {

    private var saves = ArrayList<SaveEntity>()
    private var onClickListener: OnClickListener? = null
    lateinit var view : View

    fun setListSaves(data: ArrayList<SaveEntity>){
        this.saves = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavesViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.layout_saving, parent, false)
        return SavesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return saves.size
    }

    override fun onBindViewHolder(holder: SavesViewHolder, position: Int) {
        val save = saves[position]
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, save, view)
            }
        }
        holder.bind(save)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: SaveEntity, view: View)
    }

    class SavesViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val binding = LayoutSavingBinding.bind(item)
        fun bind(save: SaveEntity) = with(binding){
            when(save.host){
                "Er" -> idIVCourseImage.setImageResource(R.drawable.yber)
                "Nd" -> idIVCourseImage.setImageResource(R.drawable.nd)
                "Yb" -> idIVCourseImage.setImageResource(R.drawable.yb_host)
                else -> idIVCourseImage.setImageResource(R.drawable.general_withsensetizer_4level)
            }
            binding.idTVCourseName.text = save.host + ": " + save.type
            binding.idTVCourseRating.text = save.date ?: "hehe"
        }
    }
}