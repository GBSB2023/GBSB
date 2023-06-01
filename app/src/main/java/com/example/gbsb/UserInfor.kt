package com.example.gbsbwork

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsbwork.databinding.MainlayoutBinding
import com.example.gbsbwork.databinding.UserlayoutBinding

class UserInfor : AppCompatActivity(){  // 유저 정보 확인 창 
    lateinit var binding: UserlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }
    private fun initLayout(){
        binding.user.setOnClickListener{// 메인 화면으로 이동
            val intent = Intent(this, mainWindow::class.java)
            startActivity(intent)
        }
    }
}
