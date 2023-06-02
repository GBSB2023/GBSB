package com.example.gbsb

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.gbsb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 각 버튼에 클릭 리스너 설정
        binding.button1.setOnClickListener {
            navigateToMain2Activity(1)
        }
        binding.button2.setOnClickListener {
            navigateToMain2Activity(2)
        }
        binding.button3.setOnClickListener {
            navigateToMain2Activity(3)
        }
        binding.button4.setOnClickListener {
            navigateToMain2Activity(4)
        }
        binding.button5.setOnClickListener {
            navigateToMain2Activity(5)
        }
        binding.button6.setOnClickListener {
            navigateToMain2Activity(6)
        }
        binding.button7.setOnClickListener {
            navigateToMain2Activity(7)
        }
        binding.button8.setOnClickListener {
            navigateToMain2Activity(8)
        }

        initLayout()
    }
    private fun initLayout() {
        binding.apply {
            calendarBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, TodolistActivity::class.java)
                startActivity(intent)
            }

        }
    }
    private fun navigateToMain2Activity(buttonId: Int) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("buttonId", buttonId)
        startActivity(intent)
    }

}
