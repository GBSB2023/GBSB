package com.example.gbsbwork

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gbsbwork.databinding.ActivityMainBinding
import com.example.gbsbwork.databinding.MainlayoutBinding
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {  // 로그인으로 시작
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlayout()
    }

    fun initlayout(){
        binding.button.setOnClickListener{ // 메인 화면으로 넘어가기
            val intent = Intent(this, mainWindow::class.java)
            startActivity(intent)
        }

    }

}