package com.example.gbsb.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.databinding.RowTodoBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TodoAdapter(options: FirebaseRecyclerOptions<Schedule>)
    : FirebaseRecyclerAdapter<Schedule, TodoAdapter.ViewHolder>(options) {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: RowTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                itemClickListener!!.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Schedule) {
        holder.binding.apply {
            content.text = model.content
            time.text = model.date
            checkBox.isChecked = model.isDone
        }
    }

    public fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).ref.removeValue()
    }
}