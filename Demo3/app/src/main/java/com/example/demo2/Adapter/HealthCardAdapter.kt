package com.example.demo2.Adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demo2.Fragment.HealthCardsFragmentDirections
import com.example.demo2.Models.HealthData
import com.example.demo2.ViewModel.HealthCardsViewModel
import com.example.demo2.databinding.ItemHealthCardBinding

/**
 * If not db operations, use callback functions, the adapter won't have unnecessary access to the viewmodel object?
 * But in that case fragment has to maintain a lot of actions by itself.
 * Really becomes complex when UI is so complex and one fragment contain multiples
 *
 */
class HealthCardAdapter(
    private var healthDataList: ArrayList<HealthData>,
    private val viewModel: HealthCardsViewModel
) : RecyclerView.Adapter<HealthCardAdapter.HealthCardViewHolder>() {

    class HealthCardViewHolder(val binding: ItemHealthCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(healthData: HealthData) {
            binding.dateText.text = "📅 Date: ${healthData.date}"
            binding.sleepDurationText.text = "💤 Sleep: ${healthData.sleepDuration} hrs"
            binding.sugarLevelText.text = "🍬 Sugar Level: ${healthData.sugarLevel} mg/dL"

            binding.sugarLevelText.setTextColor(
                if (healthData.sugarLevel > 130) Color.RED
                else Color.BLACK
            )
        }
    }

    init {
        // for debug purpose
        Log.d("Adapter", "${healthDataList.size} items in adapter constructor.")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthCardViewHolder {
        val binding = ItemHealthCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("TAG", "onViewCreated: ${healthDataList.size}")
        return HealthCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealthCardViewHolder, position: Int) {
        holder.bind(healthDataList[position])
        holder.binding.shortCardItem.setOnClickListener {
            Log.d("HealthCardAdapter", "Clicked on item with id: ${healthDataList[position].id}")
            val action = HealthCardsFragmentDirections.actionHealthCardsFragmentToDetailFragment(
                healthDataList[position].id)
            it.findNavController().navigate(action)
        }
        holder.binding.deleteIcon.setOnClickListener {
            viewModel.deleteHealthData(healthDataList[position].id)

            healthDataList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, healthDataList.size)

        }
    }

    override fun getItemCount() = healthDataList.size
}