package com.example.gbsb.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.MainActivity
import com.example.gbsb.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountBinding
    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser
    private lateinit var accountdb: DatabaseReference
    private lateinit var infodb: DatabaseReference
    val model: AccountViewModel by viewModels()
    lateinit var info: Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        initBtn()
        initAccount()
    }

    private fun initAccount() {
        currentUser=auth!!.currentUser!!
        accountdb = Firebase.database.getReference("Accounts")
        val uid = currentUser?.uid!!
        infodb = Firebase.database.getReference("Info").child(uid)

        if(uid != null){
            val query = infodb
            query.addValueEventListener(object: ValueEventListener { // infodb의 내용이 변경될때마다 발생되는 이벤트
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val data = snapshot.getValue(Info::class.java)
                        var email = data!!.email
                        var call = data!!.call
                        var birth = data!!.birth
                        var major = data!!.major
                        var introduce = data!!.introduce
                        binding.infoEmail.text = email
                        binding.infoCall.text = call
                        binding.infoBirth.text = birth
                        binding.infoMajor.text = major
                        binding.infoIntroduce.text = introduce

                        info= Info(email, call, birth, major, introduce)
                        model.setData(info)
                        model.setuId(uid)
                    }
                    else{
                        Toast.makeText(this@AccountActivity, "error : snapshot이 존재하지않음", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AccountActivity, "DB 조회 중 에러 발생", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    private fun initBtn() {
        binding.apply {
            registerBack.setOnClickListener {
                val i= Intent(this@AccountActivity, MainActivity::class.java)
                startActivity(i)
            }
            accountinfoBtn.setOnClickListener {
                model.setData(info)
                model.setuId(currentUser?.uid!!)
                val dialog = EditInfoFragment()
                dialog.show(supportFragmentManager,"EditInfoFragment")
            }
            accountintroduceBtn.setOnClickListener {
                model.setData(info)
                model.setuId(currentUser?.uid!!)
                val dialog = EditIntroduceFragment()
                dialog.show(supportFragmentManager,"EditIntroduceFragment")
            }
        }
    }

}