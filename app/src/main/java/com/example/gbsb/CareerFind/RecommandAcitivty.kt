package com.example.gbsb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.databinding.RecommandBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class RecommandAcitivty : AppCompatActivity() { //진로 추천 화면
    lateinit var binding: RecommandBinding
    lateinit var adapter : MyAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    val currentUser = FirebaseAuth.getInstance().currentUser

    companion object {
        val RecommandUserList: ArrayList<RecommandUser> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecommandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseRef = Firebase.database.getReference("Career")

        initLayout()
        inputData()
        if (currentUser?.isAnonymous == false){
            loadDataFromFirebase()
        }
    }


    private fun loadDataFromFirebase() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userCareerRef = databaseRef.child("Career").child(userId)
            userCareerRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    RecommandUserList.clear() // 초기화
                    for (careerSnapshot in dataSnapshot.children) {
                        val date = careerSnapshot.child("date").getValue(String::class.java)
                        val type = careerSnapshot.child("type").getValue(String::class.java)

                        if (type != null && date != null) {
                            val user = RecommandUser(type, date)
                            RecommandUserList.add(user)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@RecommandAcitivty, "오류 발생", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    private fun saveCareerData(careerData: RecommandUser) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val careerRef = databaseRef.child("Career").child(userId).push()
            careerRef.setValue(careerData)
                .addOnSuccessListener {
                    // 성공적으로 데이터가 저장되었을 때의 처리
                    Log.d("Firebase", "Data saved successfully")
                    RecommandUserList.add(careerData) // RecommandUserList에 데이터 추가
                    adapter.notifyDataSetChanged() // 데이터 변경 알림
                }
                .addOnFailureListener { exception ->
                    // 데이터 저장 실패 시의 처리
                    Log.e("Firebase", "Data saving failed", exception)
                }
        }
    }




    fun inputData() {
        val receivedData = intent.getStringExtra("RecommandUser")
        if (receivedData != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            val newRecommandUser = RecommandUser(receivedData, currentDate)
            RecommandUserList.add(newRecommandUser)

            if (currentUser?.isAnonymous == false) {
                saveCareerData(newRecommandUser)
            }

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
            if(intent.getBooleanExtra("anonymous",false)){
                val i= Intent(this, AccountActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(this@RecommandAcitivty,"익명 로그인의 경우 해당 기능을 이용할 수 없습니다",Toast.LENGTH_SHORT).show()
            }
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
