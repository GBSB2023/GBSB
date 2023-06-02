package com.example.gbsb.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gbsb.MainActivity
import com.example.gbsb.R
import com.example.gbsb.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    var binding: FragmentRegisterBinding?=null
    var auth: FirebaseAuth?= null

    private lateinit var accountdb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        accountdb = Firebase.database.getReference("Accounts")

        binding!!.apply {
            registerBack.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val loginFragment = LoginFragment()
                fragment.replace(R.id.frameLayout, loginFragment)
                fragment.commit()
            }
            registerComplete.setOnClickListener {
                createUserId()
            }
        }
    }

    private fun createUserId() {
        var email = binding!!.registerId.text.toString().trim()
        email += "@gsbs.com"
        var password = binding!!.registerPw.text.toString().trim()
        var name = binding!!.registerName.text.toString().trim()
        var nickname = binding!!.registerNickname.text.toString().trim()
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    // Creating a user account

                    // DB에 계정 추가
                    addUserToDatabase(email, password, name, nickname, auth!!.currentUser!!.uid)
                    moveMainPage(task.result?.user)
                }else if(task.exception?.message.isNullOrEmpty()){
                    // Show the error message
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
                }else{
                    // Login if you have account
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                    Toast.makeText(activity, "이미 해당 계정이 존재합니다", Toast.LENGTH_LONG).show()
                    fragment.addToBackStack(null)
                    val loginFragment = LoginFragment()
                    fragment.replace(R.id.frameLayout, loginFragment)
                    fragment.commit()
                }
            }
    }

    private fun addUserToDatabase(email: String, password: String, name: String, nickname: String, uId:String) {
        accountdb.child(uId).setValue(Account(email, password, name, nickname, uId))
    }

    // 로그인이 성공하면 다음 페이지로 넘어가는 함수
    private fun moveMainPage(user: FirebaseUser?) {
        // 파이어베이스 유저 상태가 있을 경우 다음 페이지로 넘어갈 수 있음
        if(user != null){
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}