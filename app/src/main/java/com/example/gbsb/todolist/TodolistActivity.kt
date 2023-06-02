package com.example.gbsb.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.MainActivity
import com.example.gbsb.databinding.ActivityTodolistBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TodolistActivity : AppCompatActivity() , TodoDialogFragment.TodoDialogListener {

    lateinit var binding : ActivityTodolistBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: TodoAdapter
    private var selectedDate = LocalDate.now()
    var userFirebasePath = "TodoList/uid"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        // database
        rdb = Firebase.database.getReference(userFirebasePath)
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<TodoItem>()
            .setQuery(query, TodoItem::class.java)
            .build()

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = TodoAdapter(option)
        adapter.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@TodolistActivity, "itemclicked", Toast.LENGTH_SHORT).show()
            }
        }


        binding.apply {
            // recyclerView
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter


            // floatingActionButton
            floatingActionButton.setOnClickListener {
                val dialogFragment = TodoDialogFragment.newInstance(
                    selectedDate
                )

//                Toast.makeText(this@TodolistActivity, selectedDate.monthValue, Toast.LENGTH_SHORT).show()
                dialogFragment.show(supportFragmentManager, "todo_item_add_dialog")
            }


            // calendarView
            calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                val selectedDateData = Calendar.getInstance()
                selectedDateData.set(year, month, dayOfMonth)

                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                updateList()
            }

            // BackBtn
            backBtn.setOnClickListener {
                val intent = Intent(this@TodolistActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
    // If you select a date, update the To Do list for that date
    private fun updateList() {

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDialogClosed(choosedDate: LocalDateTime, content: String) {
        val newChildRef= rdb.push()
        val childKey = newChildRef.key
        val item = TodoItem(childKey!!, content, formatToLocalDateTimeString(choosedDate), false)
        newChildRef.setValue(item)
    }

    fun formatToLocalDateTimeString(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        return localDateTime.format(formatter)// ex: "2023-06-02 15:30"
    }

}