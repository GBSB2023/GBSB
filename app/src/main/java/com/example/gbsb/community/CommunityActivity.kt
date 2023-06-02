package com.example.gbsb.community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.MainActivity
import com.example.gbsb.R
import com.example.gbsb.community.board.BoardFragment
import com.example.gbsb.databinding.ActivityCommunityBinding

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBtn()
        initfragment()
    }

    private fun initBtn() {
        binding.apply {
            registerBack.setOnClickListener {
                val i= Intent(this@CommunityActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun initfragment() {
        val fragment = supportFragmentManager.beginTransaction()
        val boardFragment = BoardFragment()
        fragment.replace(R.id.contentLayout, boardFragment)
        fragment.commit()
    }


}