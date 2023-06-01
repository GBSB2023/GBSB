package com.example.gbsbwork

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsbwork.databinding.MainlayoutBinding
import com.example.gbsbwork.databinding.RecommandBinding

class mainWindow : AppCompatActivity(){  // 메인 화면
    lateinit var binding: MainlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }
    private fun initLayout(){ // 메인화면 레이아웃 설정
        binding.recommand.setOnClickListener{ // 진로 추천 화면으로 넘어가기
            val intent = Intent(this, RecommandAcitivty::class.java)
            startActivity(intent)
        }
    }
}
