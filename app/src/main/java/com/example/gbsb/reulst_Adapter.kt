package com.example.gbsbwork

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsbwork.databinding.AnotherResultDataBinding

class reulst_Adapter (val items : ArrayList<RecommandResultData>): // 전체 진로 추천 결과 보여 주는 Adapter
    RecyclerView.Adapter<reulst_Adapter.ViewHolder>(){


    inner class ViewHolder(val binding : AnotherResultDataBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reulst_Adapter.ViewHolder {
        val view = AnotherResultDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.explain1.text = items[position].type+" 개발자"
        if(items[position].type == "SI/SM"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.si)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "DataBase"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.database)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "WebFront"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.frontend)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "AI"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.ai)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "Security"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.security)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "App"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.app)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else if(items[position].type == "Cloud"){
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.cloud)
            holder.binding.imageView1.setImageDrawable(drawable)
        }
        else{
            val drawable = holder.binding.imageView1.context.getDrawable(R.drawable.game)
            holder.binding.imageView1.setImageDrawable(drawable)
        }


        holder.binding.explain2.text = items[position].explain
    }

    override fun getItemCount(): Int {
        return items.size
    }
}