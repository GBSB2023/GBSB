package com.example.gbsb.community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.gbsb.MainActivity
import com.example.gbsb.R
import com.example.gbsb.community.board.BoardFragment
import com.example.gbsb.community.comment.CommentFragment
import com.example.gbsb.databinding.ActivityCommunityBinding

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBtn()

        // If a row in the recent list of posts is selected
        if(intent.getBooleanExtra("RecentRowClicked", false)){

            // ID of the clicked Board
            val clickedBoardId = intent.getStringExtra("ClickedBoardId")!!
            val destinationFragment = CommentFragment.newInstance(clickedBoardId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentLayout, destinationFragment)
                .commit()

        // Normal case
        }else{
            initfragment()
        }

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