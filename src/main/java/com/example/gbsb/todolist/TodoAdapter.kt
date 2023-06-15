package com.example.gbsb.todolist

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.R
import com.example.gbsb.databinding.RowTodoBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TodoAdapter(options: FirebaseRecyclerOptions<Schedule>)
    : FirebaseRecyclerAdapter<Schedule, TodoAdapter.ViewHolder>(options) {

    interface OnItemClickListener{
        fun onItemClick(schedule: Schedule)
        fun onCheckedChange(scheduleID: String, isChecked : Boolean)
    }

    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: RowTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                itemClickListener!!.onItemClick(
                    snapshots.getSnapshot(bindingAdapterPosition).getValue(Schedule::class.java)!!)
            }

            binding.todayScheduleDone.setOnCheckedChangeListener { _, isChecked ->
                val schedule =
                    snapshots.getSnapshot(bindingAdapterPosition).getValue(Schedule::class.java)

                if (schedule != null) {
                    itemClickListener?.onCheckedChange(schedule.id, isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Schedule) {

        // Get real-time information
        val snapshot = snapshots.getSnapshot(position)

        val contentStr = snapshot.child("content").getValue(String::class.java)!!
        val dateTimeStr = snapshot.child("date").getValue(String::class.java) +
                " " + snapshot.child("time").getValue(String::class.java)
        val doneValue = snapshot.child("done").getValue(Boolean::class.java) ?: false

        holder.binding.apply {
            content.text = contentStr
            time.text = dateTimeStr
            todayScheduleDone.isChecked = doneValue

            if(doneValue){
                content.paintFlags = content.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                content.paintFlags = content.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).ref.removeValue()
    }
}