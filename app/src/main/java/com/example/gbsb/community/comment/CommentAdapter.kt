package com.example.gbsb.community.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.databinding.RowCommentBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class CommentAdapter(options: FirebaseRecyclerOptions<UserComment>):
    FirebaseRecyclerAdapter<UserComment, CommentAdapter.ViewHolder>(options) {
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
    var itemClickListener: OnItemClickListener?=null
    var deleteClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: RowCommentBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.commentLike.setOnClickListener {
                itemClickListener!!.onItemClick(adapterPosition)
            }
            binding.commentDelete.setOnClickListener {
                deleteClickListener!!.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= RowCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: UserComment) {
        holder.binding.commentWriter.text=model.writer
        holder.binding.commentContent.text=model.content
        holder.binding.commentDate.text=model.date
        holder.binding.commentLikeCount.text=model.like.toString()
    }
}