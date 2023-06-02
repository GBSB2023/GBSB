package com.example.gbsb.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.R
import com.example.gbsb.databinding.ActivityLoginBinding

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
}