package com.example.gbsbwork

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsbwork.databinding.RecommandBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecommandAcitivty : AppCompatActivity() { //진로 추천 화면
    lateinit var binding: RecommandBinding
    lateinit var adapter : MyAdapter

    companion object {
        val RecommandUserList: ArrayList<RecommandUser> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecommandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //exampleData()
        inputData()
        initLayout()
    }

    fun exampleData(){ // 확인용 임시 데이터
        RecommandUserList.add(RecommandUser("7","2023-05-25"))
        RecommandUserList.add(RecommandUser("2","2023-05-24"))
        RecommandUserList.add(RecommandUser("5","2023-05-21"))
        RecommandUserList.add(RecommandUser("1","2023-05-20"))
        RecommandUserList.add(RecommandUser("4","2023-05-12"))
    }
    fun inputData(){
        val receivedData = intent.getStringExtra("RecommandUser")
        if(receivedData != null){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            RecommandUserList.add(RecommandUser(receivedData,currentDate))
        }
    }



    private fun initLayout(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(RecommandUserList)

        binding.back.setOnClickListener{ // 메인화면으로 돌아가기
            val intent = Intent(this, mainWindow::class.java)
            startActivity(intent)
            finish()
        }
        binding.id.setOnClickListener{  // 사용자 정보 창으로 넘어가기
            val intent = Intent(this, UserInfor::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{ // 진로 탐색 시작
            val intent = Intent(this, FindCourse::class.java)
            startActivity(intent)
        }

        adapter.btn1ClickListener = object:MyAdapter.OnBtn1ClickListener{  // 첫번째 언어 설명 페이지로 이동
            override fun OnBtn1ClickListener( holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button1.text))
                startActivity(intent)
            }

        }
        adapter.btn2ClickListener = object:MyAdapter.OnBtn2ClickListener{ // 두번째 언어 설명 페이지로 이동
            override fun OnBtn2ClickListener(holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button2.text))
                startActivity(intent)
            }

        }
        adapter.btn3ClickListener = object:MyAdapter.OnBtn3ClickListener{ // 세번째 언어 설명 페이지로 이동
            override fun OnBtn3ClickListener( holder: MyAdapter.ViewHolder, viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button3.text))
                startActivity(intent)
            }
        }
        adapter.btnClickListener = object:MyAdapter.OnBtnClickListener{ // 자세히 보기 클릭
            override fun OnBtnClickListener(holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                // 해당 직업으로 이동

            }

        }

        binding.recyclerview.adapter = adapter
    }
}
