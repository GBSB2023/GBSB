package com.example.gbsb.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.community.board.Board
import com.example.gbsb.databinding.RowRecentCommunityBinding


class RecentCommunityAdapter(private var items : ArrayList<Board>) : RecyclerView.Adapter<RecentCommunityAdapter.ViewHolder>() {
    var itemClickListener: OnRecentRowClickListener?= null

    interface OnRecentRowClickListener{
        fun onItemClick(boardId : String)
    }
    inner class ViewHolder(val binding : RowRecentCommunityBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(items[absoluteAdapterPosition].boardid)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowRecentCommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeSliceRange = IntRange(5,15)

        holder.binding.apply {
            recentCommunityTitle.text = items[position].name
            recentCommunityTime.text = items[position].date.slice(timeSliceRange)
            recentCommunityCommentNum.text = items[position].comment.toString()
        }
    }

    // Method to update the data list
    @SuppressLint("NotifyDataSetChanged")
    public fun updateItemList(newList: ArrayList<Board>) {
        items = newList
        notifyDataSetChanged()
    }

}