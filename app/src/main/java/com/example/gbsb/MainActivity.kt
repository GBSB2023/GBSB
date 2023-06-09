package com.example.gbsb

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.databinding.ActivityMainBinding
import com.example.gbsb.todolist.TodolistActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val areaIds = intArrayOf(
        R.id.career0, R.id.career1, R.id.career2, R.id.career3,
        R.id.career4, R.id.career5, R.id.career6, R.id.career7,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initLayout()
    }
    private fun initLayout() {
        // Connect listener to each career button
        for (areaId in areaIds) {
            val area = findViewById<LinearLayout>(areaId)
            area.setOnClickListener {
                navigateToMain2Activity(areaIds.indexOf(areaId) + 1)
            }
        }

        binding.apply {
            todoListArea.setOnClickListener {
                if(FirebaseAuth.getInstance().currentUser?.isAnonymous == true){
                    Toast.makeText(this@MainActivity, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this@MainActivity, TodolistActivity::class.java)
                    startActivity(intent)
                }
            }
            accountBtn.setOnClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if(currentUser?.isAnonymous==true){ // 익명 사용자일 경우
                    Toast.makeText(this@MainActivity, "익명 로그인의 경우 해당 기능을 이용할 수 없습니다.", Toast.LENGTH_LONG).show()
                }else{
                    val i= Intent(this@MainActivity, AccountActivity::class.java)
                    startActivity(i)
                }
            }
            careerExploreBtn.setOnClickListener{// 진로 탐색으로 넘어가기
                val intent = Intent(this@MainActivity, RecommandAcitivty::class.java)
                startActivity(intent)
            }
        }
    }
    private fun navigateToMain2Activity(buttonId: Int) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("buttonId", buttonId)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // No action when clicking the previous button
    }

}
