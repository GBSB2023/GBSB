package com.example.gbsb.community.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.gbsb.R
import com.example.gbsb.community.CommentViewModel
import com.example.gbsb.databinding.FragmentCommentAddBinding
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

class CommentAddFragment : Fragment() {
    var binding: FragmentCommentAddBinding?=null
    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser
    lateinit var uid:String
    val model: CommentViewModel by activityViewModels()
    var boardId=""
    private lateinit var accountdb: DatabaseReference
    private lateinit var communitydb: DatabaseReference
    private lateinit var commentdb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentAddBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser=auth!!.currentUser!!
        uid = currentUser?.uid!!
        accountdb = Firebase.database.getReference("Accounts")
        communitydb = Firebase.database.getReference("Community")

        boardId = model.getBoardId()
        commentdb = Firebase.database.getReference("Comments").child(boardId)

        initBtn()
    }

    private fun initBtn() {
        binding!!.apply {
            var writer = ""
            var content = ""

            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = currentDateTime.format(formatter)

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
                content = addComment.text.toString()
                if(content.isNullOrEmpty()){
                    Toast.makeText(context, "내용을 입력하세요.", Toast.LENGTH_LONG).show()
                }else{
                    var commentId = (Random().nextInt(10000000)+10000000).toString()
                    var comment: UserComment = UserComment(writer, content, date, 0, uid, commentId)
                    commentdb.child(commentId).setValue(comment)
                        .addOnCompleteListener {
                                task->
                            if(!task.isSuccessful){
                                Toast.makeText(activity, "댓글 DB 등록 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                    var userRef = communitydb.child(boardId).child("comment")
                    userRef.get().addOnCompleteListener {
                            task->
                        if(task.isSuccessful){
                            val snapshot = task.result
                            if(snapshot.exists()){
                                val commentCount = task.result.value.toString().toInt()+1
                                userRef.setValue(commentCount)
                                    .addOnCompleteListener {
                                            task->
                                        if(!task.isSuccessful){
                                            Toast.makeText(activity, "board comment DB 수정 실패2", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }else{
                            Toast.makeText(activity, "board comment DB 수정 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                    val commentFragment = CommentFragment()
                    fragment.addToBackStack(null)
                    fragment.replace(R.id.contentLayout, commentFragment)
                    fragment.commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}