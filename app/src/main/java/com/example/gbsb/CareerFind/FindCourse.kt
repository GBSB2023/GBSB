package com.example.gbsb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gbsb.databinding.FindcourseBinding

class FindCourse : AppCompatActivity() { // 진로 찾기 첫 번째 화면
    lateinit var binding: FindcourseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FindcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }
    private fun initLayout(){

        binding.back.setOnClickListener{// 진로 추천 화면으로 넘어 가기
            val intent = Intent(this, RecommandAcitivty::class.java)
            startActivity(intent)
            finish()
        }
        binding.id.setOnClickListener{// 사용자 정보 창으로 넘어 가기
            val i= Intent(this@FindCourse, AccountActivity::class.java)
            startActivity(i)
        }
        binding.start.setOnClickListener{// 진로 탐색 시작
            val intent = Intent(this, Career_Exploration::class.java)
            startActivity(intent)
        }
    }
}
