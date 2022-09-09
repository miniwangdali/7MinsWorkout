package io.github.miniwangdali.a7minsworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.github.miniwangdali.a7minsworkout.databinding.ExerciseStatusItemBinding

class ExerciseStatusAdapter(private val exercises: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(binding: ExerciseStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTextView = binding.itemTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExerciseStatusItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = exercises[position]
        holder.itemTextView.text = model.id.toString()
        when {
            model.isSelected -> {
                holder.itemTextView.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_white_background_selected
                )
                holder.itemTextView.setTextColor(holder.itemView.context.getColor(R.color.colorPrimary))
            }
            model.isCompleted -> {
                holder.itemTextView.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_accent_background
                )
                holder.itemTextView.setTextColor(holder.itemView.context.getColor(R.color.white))
            }
            else -> {
                holder.itemTextView.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_gray_background
                )
                holder.itemTextView.setTextColor(holder.itemView.context.getColor(R.color.colorPrimary))
            }
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }
}