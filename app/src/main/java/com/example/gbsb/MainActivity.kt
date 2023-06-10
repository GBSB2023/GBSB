package com.example.gbsb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.community.board.Board
import com.example.gbsb.databinding.ActivityMainBinding
import com.example.gbsb.main.RecentCommunityAdapter
import com.example.gbsb.main.TodayScheduleAdapter
import com.example.gbsb.todolist.Schedule
import com.example.gbsb.todolist.TodolistActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var todayScheduleAdapter: TodayScheduleAdapter
    lateinit var recentCommunityAdapter : RecentCommunityAdapter
    var todayScheduleData: ArrayList<Schedule> = ArrayList()
    var recentCommunityData : ArrayList<Board> = ArrayList()
    private var userFirebasePath = "TodoList/"
    lateinit var communityDB:DatabaseReference
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
                    .take(5)

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
                val newList = todayList.filter { schedule ->
                    schedule.time >= curTime
                }.sortedBy { schedule ->
                    schedule.time
                }.take(3)


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
        // TodoList firebase reference
        val curUser = FirebaseAuth.getInstance().currentUser
        userFirebasePath += curUser?.uid
        setRDB(Firebase.database.getReference(userFirebasePath))

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
            communityRecentRecyclerView.adapter = recentCommunityAdapter


            // TodoList Click
            todoListArea.setOnClickListener {
                if(FirebaseAuth.getInstance().currentUser?.isAnonymous == true){
                    Toast.makeText(this@MainActivity, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this@MainActivity, TodolistActivity::class.java)
                    startActivity(intent)
                }
            }


            // Account Click
            accountBtn.setOnClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if(currentUser?.isAnonymous==true){ // 익명 사용자일 경우
                    Toast.makeText(this@MainActivity, "익명 로그인의 경우 해당 기능을 이용할 수 없습니다.", Toast.LENGTH_LONG).show()
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
        // No action when clicking the previous button
    }

}
