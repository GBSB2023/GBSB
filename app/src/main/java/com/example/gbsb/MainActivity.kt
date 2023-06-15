package com.example.gbsb

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.community.CommunityActivity
import com.example.gbsb.community.board.Board
import com.example.gbsb.databinding.ActivityMainBinding
import com.example.gbsb.login.LoginActivity
import com.example.gbsb.main.RecentCommunityAdapter
import com.example.gbsb.main.TodayScheduleAdapter
import com.example.gbsb.todolist.Schedule
import com.example.gbsb.todolist.TodolistActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // Today Schedule
    lateinit var todayScheduleAdapter: TodayScheduleAdapter
    var todayScheduleData: ArrayList<Schedule> = ArrayList()

    // RecentCommunity
    lateinit var recentCommunityAdapter : RecentCommunityAdapter
    var recentCommunityData : ArrayList<Board> = ArrayList()

    // FireBase
    private var userFirebasePath = "TodoList/"
    lateinit var communityDB:DatabaseReference
    private var isAnonymousUser = false

    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser

    companion object {
        private lateinit var rdb: DatabaseReference

        fun getRDB(): DatabaseReference {
            return rdb
        }

        fun setRDB(ref: DatabaseReference) {
            rdb = ref
        }
    }

    private val areaIds = intArrayOf(
        R.id.career0, R.id.career1, R.id.career2, R.id.career3,
        R.id.career4, R.id.career5, R.id.career6, R.id.career7,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        currentUser=auth!!.currentUser!!

        initLayout()
    }

    override fun onStart() {
        super.onStart()
        refreshTodaySchedule()
        refreshRecentCommunity()
    }

    private fun refreshRecentCommunity() {
        Log.d("MainActivity", "refreshRecentCommunity call")

        val query = communityDB.orderByChild("date")

        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val recentList = ArrayList<Board>()
                for(snapshot in dataSnapshot.children){
                    val board = snapshot.getValue(Board::class.java)
                    board?.let{
                        recentList.add(it)
                    }
                }
                val newList = recentList
                    .reversed()
                    .take(6)

                Log.d("MainActivity", "recentCommunity Number : " + newList.size)
                if(newList.isEmpty()){
                    Log.d("MainActivity", "no recent data")
                    binding.noRecentCommunityText.visibility = View.VISIBLE
                    binding.communityRecentRecyclerView.visibility = View.GONE
                }else{
                    Log.d("MainActivity", "yes recent data")
                    binding.noRecentCommunityText.visibility = View.GONE
                    binding.communityRecentRecyclerView.visibility = View.VISIBLE
                }

                // Update the dataList with the new list
                recentCommunityData.clear()
                recentCommunityData.addAll(newList)
                recentCommunityAdapter.updateItemList(recentCommunityData)
                recentCommunityAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun refreshTodaySchedule() {
        Log.d("MainActivity", "refreshTodaySchedule call")

        // Get today's date in the format yyyy-MM-dd
        val todayDate = TodolistActivity().formatToDateString(LocalDateTime.now())

        val query = getRDB()
            .orderByChild("date")
            .equalTo(todayDate)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val todayList = ArrayList<Schedule>()
                val curTime = TodolistActivity().formatToTimeString(LocalDateTime.now())
                for (snapshot in dataSnapshot.children) {
                    val schedule = snapshot.getValue(Schedule::class.java)
                    schedule?.let {
                        todayList.add(it)
                    }
                }
                val newList = todayList.sortedBy { schedule ->
                    schedule.time
                }


                if(newList.isEmpty()){
                    Log.d("MainActivity", "no today data")
                    binding.noScheduleTextMain.visibility = View.VISIBLE
                    binding.todayScheduleRecyclerView.visibility = View.GONE
                    return
                }else{
                    Log.d("MainActivity", "yes today data")
                    binding.noScheduleTextMain.visibility = View.GONE
                    binding.todayScheduleRecyclerView.visibility = View.VISIBLE
                }


                // Update the dataList with the new list
                todayScheduleData.clear()
                todayScheduleData.addAll(newList)
                todayScheduleAdapter.updateItemList(todayScheduleData)
                todayScheduleAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
            }
        })
    }

    private fun initLayout() {

        // Check user anonymous
        val curUser = FirebaseAuth.getInstance().currentUser
        isAnonymousUser = curUser?.isAnonymous!!

        if(isAnonymousUser){
            setRDB(Firebase.database.reference)
        }else{
            userFirebasePath += curUser.uid
            setRDB(Firebase.database.getReference(userFirebasePath))
        }


        // Community firebase reference
        communityDB = Firebase.database.getReference("Community")

        // Connect listener to each career button
        for (areaId in areaIds) {
            val area = findViewById<LinearLayout>(areaId)
            area.setOnClickListener {
                navigateToMain2Activity(areaIds.indexOf(areaId) + 1)
            }
        }

        binding.apply {

            // TodoList Adapter
            todayScheduleRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
            todayScheduleAdapter = TodayScheduleAdapter(todayScheduleData)
            todayScheduleAdapter.itemClickListener = object : TodayScheduleAdapter.onItemClickListener{
                override fun onCheckedChange(schedule:Schedule, isChecked: Boolean) {
                    Log.d("MainActivity", "onCheckedChange called")
                    schedule.done = isChecked
                    getRDB().child(schedule.id).child("done").setValue(isChecked)
                        .addOnSuccessListener {
                            Log.d("TodolistActivity", "is Done check success")
                            todayScheduleAdapter.notifyDataSetChanged()

                        }
                        .addOnFailureListener {
                            Log.e("TodolistActivity", "is Done check fail")
                        }
                }

            }
            todayScheduleRecyclerView.adapter = todayScheduleAdapter


            // RecentCommunity Adapter
            communityRecentRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
            recentCommunityAdapter = RecentCommunityAdapter(recentCommunityData)
            recentCommunityAdapter.itemClickListener = object : RecentCommunityAdapter.OnRecentRowClickListener{
                override fun onItemClick(boardId: String) {

                    // Forward row click information and boardId to boardActivity
                    val intent = Intent(this@MainActivity, CommunityActivity::class.java)
                    intent.putExtra("RecentRowClicked", true)
                    intent.putExtra("ClickedBoardId", boardId)
                    startActivity(intent)

                }
            }
            communityRecentRecyclerView.adapter = recentCommunityAdapter

            // TodoList Click
            todoListEnterBtn.setOnClickListener {
                if(isAnonymousUser){
                    Toast.makeText(this@MainActivity, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this@MainActivity, TodolistActivity::class.java)
                    intent.putExtra("AddBtnClicked", false)
                    startActivity(intent)
                }
            }

            // CommunityBtn Click
            communityEnterBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, CommunityActivity::class.java)
                startActivity(intent)
            }


            // Account Click
            accountBtn.setOnClickListener {

                if(isAnonymousUser){ // 익명 사용자일 경우
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("익명 회원의 경우 해당 기능을 이용할 수 없습니다. 로그인 화면으로 이동할까요?")
                    builder.setCancelable(false)
                    builder.setPositiveButton("Yes") { dialog, which ->
                        if (currentUser.isAnonymous){
                            currentUser.delete().addOnCompleteListener {
                                    task->
                                if(!task.isSuccessful){
                                    Toast.makeText(this@MainActivity, "익명 회원 삭제 중 에러 발생", Toast.LENGTH_LONG).show()
                                }
                            }
                            val i= Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }

                    builder.setNegativeButton("No") { dialog, which ->
                        // Do nothing
                    }

                    builder.show()
                }else{
                    val i= Intent(this@MainActivity, AccountActivity::class.java)
                    startActivity(i)
                }
            }


            // CareerExplore Click
            careerExploreBtn.setOnClickListener{// 진로 탐색으로 넘어가기
                val intent = Intent(this@MainActivity, RecommandAcitivty::class.java)
                startActivity(intent)
            }

        }
    }
    private fun navigateToMain2Activity(buttonId: Int) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("buttonId", buttonId)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("GBSB를 종료할까요?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { dialog, which ->
            if (currentUser.isAnonymous){
                currentUser.delete().addOnCompleteListener {
                        task->
                    if(!task.isSuccessful){
                        Toast.makeText(this@MainActivity, "익명 회원 삭제 중 에러 발생", Toast.LENGTH_LONG).show()
                    }
                }
            }
            moveTaskToBack(true) // 태스크를 백그라운드로 이동
            finishAndRemoveTask() // 액티비티 종료 + 태스크 리스트에서 지우기
            exitProcess(0)
        }

        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing
        }

        builder.show()
    }

}

