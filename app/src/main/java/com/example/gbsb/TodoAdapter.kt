package com.example.gbsb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.databinding.RowTodoBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TodoAdapter(options: FirebaseRecyclerOptions<TodoItem>)
    : FirebaseRecyclerAdapter<TodoItem, TodoAdapter.ViewHolder>(options) {

    interface OnItemClickListener{
        fun OnItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val view = RowTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int, model: TodoItem) {
        holder.binding.apply {
            content.text = model.content
            time.text = model.time
            checkBox.isChecked = model.isDone
        }
    }


}