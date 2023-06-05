package com.example.gbsb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.databinding.RecommandBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class RecommandAcitivty : AppCompatActivity() { //진로 추천 화면 
    lateinit var binding: RecommandBinding
    lateinit var adapter : MyAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    companion object {
        val RecommandUserList: ArrayList<RecommandUser> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecommandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("users")

        inputData()
        initLayout()
        if(intent.getBooleanExtra("anonymous",false)){
            loadDataFromFirebase()
        }
    }
    private fun loadDataFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                RecommandUserList.clear()

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(RecommandUser::class.java)
                    user?.let {
                        RecommandUserList.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {// 오류 처리
                Toast.makeText(this@RecommandAcitivty,"오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveCareerData(careerData: RecommandUser) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val careerRef = databaseRef.child(userId).child("Career")
            val careerKey = careerRef.push().key
            careerKey?.let {
                careerRef.child(it).setValue(careerData)
            }
        }
    }
    fun inputData(){
        val receivedData = intent.getStringExtra("RecommandUser")
        if(receivedData != null){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            RecommandUserList.add(RecommandUser(receivedData,currentDate))
            if(intent.getBooleanExtra("anonymous",false))
                saveCareerData(RecommandUser(receivedData,currentDate))
        }
    }

    private fun initLayout(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(RecommandUserList)

        binding.back.setOnClickListener{ // 메인화면으로 돌아가기
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.id.setOnClickListener{  // 사용자 정보 창으로 넘어가기
            val i= Intent(this, AccountActivity::class.java)
            startActivity(i)
        }
        binding.add.setOnClickListener{ // 진로 탐색 시작
            val intent = Intent(this, FindCourse::class.java)
            startActivity(intent)
        }

        adapter.btn1ClickListener = object:MyAdapter.OnBtn1ClickListener{  // 첫번째 언어 설명 페이지로 이동
            override fun OnBtn1ClickListener( holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button1.text))
                startActivity(intent)
            }

        }
        adapter.btn2ClickListener = object:MyAdapter.OnBtn2ClickListener{ // 두번째 언어 설명 페이지로 이동
            override fun OnBtn2ClickListener(holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button2.text))
                startActivity(intent)
            }

        }
        adapter.btn3ClickListener = object:MyAdapter.OnBtn3ClickListener{ // 세번째 언어 설명 페이지로 이동
            override fun OnBtn3ClickListener( holder: MyAdapter.ViewHolder, viewHolder: MyAdapter.ViewHolder){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/"
                        + holder.binding.button3.text))
                startActivity(intent)
            }
        }
        adapter.btnClickListener = object:MyAdapter.OnBtnClickListener{ // 자세히 보기 클릭
            override fun OnBtnClickListener(holder: MyAdapter.ViewHolder,viewHolder: MyAdapter.ViewHolder){
                // 해당 직업으로 이동
                if(holder.binding.recommend.text == "SI/SM 개발자"){
                    navigateToMain2Activity(8)
                }
                else  if(holder.binding.recommend.text == "데이터 베이스 개발자"){
                    navigateToMain2Activity(7)
                }
                else  if(holder.binding.recommend.text == "웹 개발자"){
                    navigateToMain2Activity(2)
                }
                else  if(holder.binding.recommend.text == "AI 개발자"){
                    navigateToMain2Activity(3)
                }
                else  if(holder.binding.recommend.text == "보안 개발자"){
                    navigateToMain2Activity(6)
                }
                else  if(holder.binding.recommend.text == "APP 개발자"){
                      navigateToMain2Activity(1)
                }   
                else  if(holder.binding.recommend.text == "클라우드 네트워크 엔지니어"){
                    navigateToMain2Activity(4)
                }
                else {
                    navigateToMain2Activity(5)
                }
            }
        }

        binding.recyclerview.adapter = adapter
    }
    
    private fun navigateToMain2Activity(buttonId: Int) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("buttonId", buttonId)
        startActivity(intent)
    }

}
