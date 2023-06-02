package com.example.gbsb.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.databinding.RowContentBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class BoardAdapter(options: FirebaseRecyclerOptions<Board>):
    FirebaseRecyclerAdapter<Board, BoardAdapter.ViewHolder>(options) {
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: RowContentBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.rowContent.setOnClickListener {
                itemClickListener!!.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=RowContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Board) {
        holder.binding.contentName.text=model.name
        holder.binding.contentDate.text=model.date
        holder.binding.contentWriter.text=model.writer
        holder.binding.contentLike.text=model.like.toString()
        holder.binding.contentComment.text=model.comment.toString()
    }
}