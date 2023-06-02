package com.example.gbsb.community.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gbsb.R
import com.example.gbsb.databinding.FragmentBoardAddBinding
import com.example.gbsb.login.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BoardAddFragment : Fragment() {
    var binding: FragmentBoardAddBinding?=null
    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser
    lateinit var uid:String
    private lateinit var accountdb: DatabaseReference
    private lateinit var communitydb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardAddBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser=auth!!.currentUser!!
        uid = currentUser?.uid!!
        accountdb = Firebase.database.getReference("Accounts")
        communitydb = Firebase.database.getReference("Community")

        initBtn()
    }

    private fun initBtn() {
        binding!!.apply {
            var name = ""
            var content = ""
            var category = ""

            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = currentDateTime.format(formatter)
            var writer = ""

            // accountdb 탐색하여 writer 초기화
            accountdb.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val data = snapshot.getValue(Account::class.java)
                        writer = data!!.pName
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "DB 조회 중 에러 발생", Toast.LENGTH_LONG).show()
                }
            })

            addComplete.setOnClickListener {
                name = addName.text.toString()
                content = addContent.text.toString()
                category = addSpinner.selectedItem.toString()
                var boardId = Random().nextInt(10000000).toString()
                var board:Board=Board(name, content, date, writer, category, 0, 0, boardId, uid)
                communitydb.child(boardId).setValue(board)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val boardFragment = BoardFragment()
                fragment.replace(R.id.contentLayout, boardFragment)
                fragment.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}