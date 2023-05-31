package com.example.gbsb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.gbsb.databinding.ActivityTodolistBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TodolistActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTodolistBinding
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: TodoAdapter

    val userFirebasePath = "pid/todolist"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rdb = Firebase.database.getReference(userFirebasePath)
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<TodoItem>()
            .setQuery(query, TodoItem::class.java)
            .build()

        adapter = TodoAdapter(option)
        adapter.itemClickListener = object : TodoAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int) {
                Toast.makeText(this@TodolistActivity, "itemclicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.apply {
            // recyclerView
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter


            // For debugging
            floatingActionButton.setOnClickListener {
                val newChildRef= rdb.push()
                val childKey = newChildRef.key
                val item = TodoItem(childKey!!, "this is content", "23.06.21", "17:40", false)
                newChildRef.setValue(item)
            }


            // calendarView
            calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // 선택된 날짜를 이용하여 필요한 작업을 수행합니다.
                // 예를 들어, 선택된 날짜를 문자열로 변환하여 출력하거나, 다른 기능에 활용할 수 있습니다.

                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val dateString = dateFormat.format(selectedDate.time)

                Log.d("SelectedDate", dateString)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
}