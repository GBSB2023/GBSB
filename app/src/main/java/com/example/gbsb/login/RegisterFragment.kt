package com.example.gbsb.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gbsb.MainActivity
import com.example.gbsb.R
import com.example.gbsb.account.Info
import com.example.gbsb.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    var binding: FragmentRegisterBinding?=null
    var auth: FirebaseAuth?= null
    var duplicate=true

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

        initBtn()
        initPattern()
    }

    private fun initBtn() {
        binding!!.apply {
            registerBack.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val loginFragment = LoginFragment()
                fragment.replace(R.id.frameLayout, loginFragment)
                fragment.commit()
            }
            registerComplete.setOnClickListener {
                if(validateRegister())
                    createUserId()
                else
                    Toast.makeText(activity, "회원가입에 실패했습니다. 양식을 확인해주세요.", Toast.LENGTH_LONG).show()
            }
            duplicateBtn.setOnClickListener {
                var checkid = registerId.text.toString()
                if(checkid.isNullOrEmpty()){
                    duplicate=true
                }else{
                    auth!!.fetchSignInMethodsForEmail(checkid+"@gsbs.com").addOnCompleteListener {
                            task->
                        if(task.isSuccessful){
                            val signInMethods = task.result?.signInMethods
                            if(signInMethods.isNullOrEmpty()){
                                duplicate = false
                                Toast.makeText(activity, "사용 가능한 아이디입니다.", Toast.LENGTH_LONG).show()
                                registerId.error=null
                            }else{
                                duplicate = true
                                Toast.makeText(activity, "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show()
                                registerId.error="중복검사 실패"
                            }
                        }else{
                            Toast.makeText(activity, "auth 조회 실패", Toast.LENGTH_LONG).show()
                        }
                    }
                }


            }
        }
    }

    private fun validateRegister(): Boolean {
        var isValid = true

        binding!!.apply {
            if(registerName.text.toString().isEmpty()){
                registerName.error="필수 입력칸입니다."
                isValid = false
            }
            if(registerId.text.toString().isEmpty()){
                registerId.error="필수 입력칸입니다."
                isValid = false
            }
            if(registerPw.text.toString().isEmpty()){
                registerPw.error="필수 입력칸입니다."
                isValid = false
            }
            if(registerPw.text.toString()!=registerPwRe.text.toString()){
                registerPwRe.error="비밀번호와 일치하지 않습니다."
                isValid = false
            }
            if(!binding!!.registerId.error.isNullOrEmpty()||!binding!!.registerPw.error.isNullOrEmpty()){
                isValid = false
            }
            if(duplicate){
                isValid = false
                binding!!.registerId.error = "중복검사를 진행해주세요"
            }
        }

        return isValid
    }

    private fun initPattern() {
        val idPattern = "^[a-zA-Z0-9]{4,16}\$"
        val idText = binding!!.registerId
        idText.error = "이메일은 제외하고 아이디만 입력해주세요."
        idText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(idPattern.toRegex())) {
                    idText.error = null
                } else {
                    idText.error = "4 ~ 16 자리의 영문, 숫자로 입력해주세요."
                }
                duplicate = true
            }

        })


        val pwPattern = "^.{6,15}$"
        val pwText = binding!!.registerPw
        pwText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(pwPattern.toRegex())) {
                    pwText.error = null
                } else {
                    pwText.error = "6 ~ 15자리로 입력해주세요."
                }

            }

        })
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
        Firebase.database.getReference("Info").child(uId).setValue(Info(email,"010-0000-0000","","",""))
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