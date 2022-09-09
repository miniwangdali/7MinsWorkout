package io.github.miniwangdali.a7minsworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.miniwangdali.a7minsworkout.databinding.ItemHistoryRowBinding

class HistoryItemAdapter(private val items: ArrayList<HistoryEntity>) :
    RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val historyItem = binding.historyItemLinearLayout
        val historyText = binding.historyTextView
        val indexText = binding.indexTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.indexText.text = (position + 1).toString()
        holder.historyText.text = item.date
        if (position % 2 == 0) {
            holder.historyItem.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.historyItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}