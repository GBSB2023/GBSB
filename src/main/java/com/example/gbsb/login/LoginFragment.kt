package com.example.gbsb.login

import android.content.Context.MODE_PRIVATE
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
import com.example.gbsb.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    var binding: FragmentLoginBinding?=null
    var auth: FirebaseAuth?= null
    var googleSignInClient: GoogleSignInClient ?=null

    private lateinit var accountdb: DatabaseReference
    private lateinit var infodb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        accountdb = Firebase.database.getReference("Accounts")
        infodb = Firebase.database.getReference("Info")

        val currentUser = auth!!.currentUser
        if(currentUser!=null&&currentUser.isAnonymous==false){
            moveMainPage(currentUser)
        }

        // google 로그인
        //GoogleSignInClient 객체 초기화
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding!!.apply {
            registerBtn.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val registerFragment = RegisterFragment()
                fragment.replace(R.id.frameLayout, registerFragment)
                fragment.commit()
            }
            loginBtn.setOnClickListener {
                signInUser()
            }
            googleBtn.setOnClickListener {
                signInWithGoogle()
            }
            githubBtn.setOnClickListener {
                signInWithGithub()
            }
            anonymousBtn.setOnClickListener {
                signInAnonymous()
            }
        }

        initPattern()
    }

    private fun initPattern() {
        val emailPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+"
        val emailText = binding!!.loginEmail
        emailText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(emailPattern.toRegex())) {
                    // 이메일 형식이 맞는 경우
                    emailText.error = null
                } else {
                    // 이메일 형식이 아닌 경우
                    emailText.error = "올바른 이메일 형식이 아닙니다."
                }
            }

        })

        val passwordPattern = "^.{6,15}$"
        val passwordText = binding!!.loginPassword
        passwordText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(passwordPattern.toRegex())) {
                    // 이메일 형식이 맞는 경우
                    passwordText.error = null
                } else {
                    // 이메일 형식이 아닌 경우
                    passwordText.error = "비밀번호는 6 ~ 15자리입니다."
                }
            }

        })
    }

    private fun signInUser() {
        val email = binding!!.loginEmail.text.toString().trim()
        val password = binding!!.loginPassword.text.toString().trim()
        var isValid = true

        binding!!.apply {
            if(loginEmail.text.toString().isEmpty()){
                loginEmail.error="필수 입력칸입니다."
                isValid = false
            }
            if(loginPassword.text.toString().isEmpty()){
                loginPassword.error="필수 입력칸입니다."
                isValid = false
            }
            if(!loginEmail.error.isNullOrEmpty()||!loginPassword.error.isNullOrEmpty()){
                isValid = false
            }
        }
        if(!isValid){
            Toast.makeText(activity, "로그인 양식을 다시 확인해주세요.", Toast.LENGTH_LONG).show()
            return
        }
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    saveData(email)
                    moveMainPage(task.result?.user)
                }
                else{
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    // 로그인이 성공하면 다음 페이지로 넘어가는 함수
    private fun moveMainPage(user: FirebaseUser?) {
        // 파이어베이스 유저 상태가 있을 경우 다음 페이지로 넘어갈 수 있음
        if(user != null){
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun saveData(loginEmail :String) {
        val pref = requireContext().getSharedPreferences("userEmail", MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("email", loginEmail)
        edit.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // 구글 로그인
    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 9001)  // 9001 : GOOGLE_SIGN_IN_REQUEST_CODE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 9001){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // 구글 로그인이 성공한 경우 파이어베이스로 인증
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                // 구글 로그인 실패
                Toast.makeText(activity, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(requireActivity()){
                    task->
                if (task.isSuccessful){
                    val user=auth?.currentUser
                    checkAccountInDB(user)
                    moveMainPage(task.result?.user)
                }else{
                    Toast.makeText(activity, "Authentication 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // 구글 로그인 끝

    // Github로 로그인
    private fun signInWithGithub(){
        val provider = OAuthProvider.newBuilder("github.com")
        auth?.startActivityForSignInWithProvider(requireActivity(), provider.build())
            ?.addOnSuccessListener {
                // github 로그인이 성공한 경우
                firebaseAuthWithGithub(it.credential!!)
            }
            ?.addOnFailureListener {
                    e->
                Toast.makeText(activity, "GitHub 로그인 실패", Toast.LENGTH_SHORT).show()
            }
    }

    private fun firebaseAuthWithGithub(it: AuthCredential) {
        auth?.signInWithCredential(it)
            ?.addOnCompleteListener(requireActivity()){
                    task ->
                if(task.isSuccessful){
                    val user = auth?.currentUser
                    checkAccountInDB(user)
                    moveMainPage(task.result?.user)
                }else{
                    Toast.makeText(activity, "Authentication 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 익명으로 로그인
    private fun signInAnonymous(){
        auth?.signInAnonymously()
            ?.addOnCompleteListener(requireActivity()){
                    task->
                if(task.isSuccessful){
                    moveMainPage(task.result?.user)
                }else{
                    Toast.makeText(activity, "Authentication 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // DB에 추가
    private fun checkAccountInDB(user: FirebaseUser?) {
        val uId=user!!.uid
        val userRef = accountdb.child(uId)
        userRef.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(!snapshot.exists()){ // 중복 계정이 존재하지 않다면 다음을 수행
                    saveUserToDB(user)
                }
            }
            else{
                Toast.makeText(activity, "DB 계정 중복 검사 실패", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun saveUserToDB(user: FirebaseUser) {
        val email = user.email.toString().trim()
        val name = user.displayName.toString().trim()
        val uId = user.uid

        val userRef = accountdb.child(uId).setValue(Account(email,"",name,"건국이",uId))

        val infoRef = infodb.child(uId).setValue(Info(email,"010-0000-0000","","",""))
    }



}