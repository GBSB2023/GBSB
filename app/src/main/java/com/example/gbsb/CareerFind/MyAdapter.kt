package com.example.gbsbwork

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsbwork.databinding.DatafindBinding

class MyAdapter(val items : ArrayList<RecommandUser>): // 진로 추천 결과 보여주는 Adapter
    RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    interface OnBtn1ClickListener{ // 첫번째 언어 클릭
        fun OnBtn1ClickListener(holder: ViewHolder,viewHolder: ViewHolder)
    }
    interface OnBtn2ClickListener{ // 두번째 언어 클릭
        fun OnBtn2ClickListener(holder: ViewHolder,viewHolder: ViewHolder)
    }
    interface OnBtn3ClickListener{ // 세번째 언어 클릭
        fun OnBtn3ClickListener(holder: ViewHolder,viewHolder: ViewHolder)
    }
    interface OnBtnClickListener{
        fun OnBtnClickListener(holder: ViewHolder,viewHolder: ViewHolder)
    }

    var btn1ClickListener : OnBtn1ClickListener?=null
    var btn2ClickListener : OnBtn2ClickListener?=null
    var btn3ClickListener : OnBtn3ClickListener?=null
    var btnClickListener : OnBtnClickListener?=null

    inner class ViewHolder(val binding : DatafindBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.button1.setOnClickListener{
                btn1ClickListener?.OnBtn1ClickListener(this,this)
            }
            binding.button2.setOnClickListener{
                btn2ClickListener?.OnBtn2ClickListener(this,this)
            }
            binding.button3.setOnClickListener{
                btn3ClickListener?.OnBtn3ClickListener(this,this)
            }
            binding.more.setOnClickListener{
                btnClickListener?.OnBtnClickListener(this,this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = DatafindBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        // 검사 날짜 입력
        holder.binding.date.text = items[position].date
        //사용 언어 보이기 유무 설정
        if ("1".equals(items[position].type)) {  // SI/SM 개발자
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.si)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3, holder)
            holder.binding.recommend.text = "SI/SM 개발자"
            holder.binding.button1.text = "JavaScript"
            holder.binding.button1.textSize = 7f
            holder.binding.button2.text = "Java"
            holder.binding.button3.text = "PHP"
        }

        else if ("2".equals(items[position].type)){ // 데이터 베이스 개발자 데이터
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.database)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3,holder)
            holder.binding.recommend.text = "데이터 베이스 개발자"
            holder.binding.button1.text = "Python"
            holder.binding.button1.textSize = 9f
            holder.binding.button2.text = "Ruby"
            holder.binding.button3.text = "Bash"
        }
        else if ("3".equals(items[position].type)){ // 웹 프론트 개발자
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.frontend)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3,holder)
            holder.binding.recommend.text = "웹 프론트 개발자"
            holder.binding.button1.text = "HTML"
            holder.binding.button2.text = "CSS"
            holder.binding.button3.text = "C++"
        }
        else if ("4".equals(items[position].type)){ // AI 개발자
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.ai)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3,holder)
            holder.binding.recommend.text = "AI 개발자"
            holder.binding.button1.text = "Python"
            holder.binding.button1.textSize = 9f
            holder.binding.button2.text = "Java"
            holder.binding.button3.text = "C++"
        }
        else if ("5".equals(items[position].type)){ // 보안 (악성코드 전문가)
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.security)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3,holder)
            holder.binding.recommend.text = "보안 개발자"
            holder.binding.button1.text = "C"
            holder.binding.button2.text = "Assembly"
            holder.binding.button2.textSize = 7f
            holder.binding.button3.text = "Python"
            holder.binding.button3.textSize = 9f
        }
        else if ("6".equals(items[position].type)){ // APP 개발자
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.app)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(2,holder)
            holder.binding.recommend.text = "APP 개발자"
            holder.binding.button1.text = "Java"
            holder.binding.button2.text = "Kotlin"
        }
        else if ("7".equals(items[position].type)){ // 클라우드 엔지니어
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.cloud)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(3,holder)
            holder.binding.recommend.text = "클라우드 네트워크 엔지니어"
            holder.binding.button1.text = "Python"
            holder.binding.button1.textSize = 9f
            holder.binding.button2.text = "Bash"
            holder.binding.button3.text = "JSON"
        }
        else if ("8".equals(items[position].type)){ // 게임 서버 프로그래머
            val drawable = holder.binding.imageView.context.getDrawable(R.drawable.game)
            holder.binding.imageView.setImageDrawable(drawable)
            visible(2,holder)
            holder.binding.recommend.text = "게임 서버 프로그래머"
            holder.binding.button1.text = "Python"
            holder.binding.button1.textSize = 9f
            holder.binding.button2.text = "C++"
        }

    }
    fun visible(temp : Int,holder: MyAdapter.ViewHolder){ // 보여지는 개수를 토대로 설정
        if(temp == 1){
            holder.binding.button1.visibility = View.VISIBLE
            holder.binding.button2.visibility = View.INVISIBLE
            holder.binding.button3.visibility = View.INVISIBLE
        }
        else if(temp == 2){
            holder.binding.button1.visibility = View.VISIBLE
            holder.binding.button2.visibility = View.VISIBLE
            holder.binding.button3.visibility = View.INVISIBLE
        }
        else{
            holder.binding.button1.visibility = View.VISIBLE
            holder.binding.button2.visibility = View.VISIBLE
            holder.binding.button3.visibility = View.VISIBLE
        }
    }
}