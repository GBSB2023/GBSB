package com.example.gbsb.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.R
import com.example.gbsb.databinding.ActivityLoginBinding
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }



    private fun initLayout(){
        val fragment = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        fragment.replace(R.id.frameLayout, loginFragment)
        fragment.commit()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("GBSB를 종료할까요?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { dialog, which ->
            moveTaskToBack(true) // 태스크를 백그라운드로 이동
            finishAndRemoveTask() // 액티비티 종료 + 태스크 리스트에서 지우기
            exitProcess(0)
        }

        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing
        }

        builder.show()
    }
}