package com.example.gbsb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.databinding.FindcourseBinding
import com.google.firebase.auth.FirebaseAuth

class FindCourse : AppCompatActivity() { // 진로 찾기 첫 번째 화면
    lateinit var binding: FindcourseBinding
    val currentUser = FirebaseAuth.getInstance().currentUser


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
            if (currentUser?.isAnonymous == false){
                val i= Intent(this, AccountActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(this@FindCourse,"익명 로그인의 경우 해당 기능을 이용할 수 없습니다",Toast.LENGTH_SHORT).show()
            }
        }
        binding.start.setOnClickListener{// 진로 탐색 시작
            val intent = Intent(this, Career_Exploration::class.java)
            startActivity(intent)
        }
    }
}
