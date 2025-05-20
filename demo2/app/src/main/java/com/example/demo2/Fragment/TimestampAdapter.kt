package com.example.demo2.Fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo2.DB.TimeData
import com.example.demo2.databinding.ItemTimestampBinding

class TimestampAdapter(
    private var timestamps: List<TimeData>,
    private val onDecreaseTimestamp: (Int, Long) -> Unit // Pass both id & timestamp
) : RecyclerView.Adapter<TimestampAdapter.TimestampViewHolder>() {

    class TimestampViewHolder(private val binding: ItemTimestampBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timeData: TimeData, onDecreaseTimestamp: (Int, Long) -> Unit) {
            binding.timestampText.text = timeData.timestamp.toString()
            binding.decreaseTimestamp.setOnClickListener {
                onDecreaseTimestamp(timeData.id, timeData.timestamp) // Pass both parameters
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimestampViewHolder {
        val binding = ItemTimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimestampViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimestampViewHolder, position: Int) {
        holder.bind(timestamps[position], onDecreaseTimestamp)
    }

    override fun getItemCount() = timestamps.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTimestamps: List<TimeData>) {
        timestamps = newTimestamps
        notifyDataSetChanged()
    }
}

