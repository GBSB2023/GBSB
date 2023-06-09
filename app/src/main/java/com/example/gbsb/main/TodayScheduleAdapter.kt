package com.example.gbsb.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.databinding.RowTodayScheduleBinding
import com.example.gbsb.todolist.Schedule

class TodayScheduleAdapter(private var items : ArrayList<Schedule>) : RecyclerView.Adapter<TodayScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : RowTodayScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowTodayScheduleBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            todayScheduleContent.text = items[position].content
            todayScheduleTime.text = items[position].time
            todayScheduleDone.isChecked = items[position].done
        }
    }

    // Method to update the data list
    @SuppressLint("NotifyDataSetChanged")
    public fun updateItemList(newList: ArrayList<Schedule>) {
        items = newList
        notifyDataSetChanged()
    }

}