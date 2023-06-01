package com.example.gbsb

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.databinding.ActivityTodolistBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

class TodolistActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTodolistBinding
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: TodoAdapter
    lateinit var dialogBuilder : AlertDialog.Builder
    var selectedDate : String = ""
    var userFirebasePath = "pid/todolist"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initDialog()
    }

    private fun initDialog() {
        // Add todoitem dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_todoitem, null)
        dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)
            .setTitle("일정 등록")
            .setPositiveButton("저장"){ dialog: DialogInterface, pos: Int ->
                Toast.makeText(this@TodolistActivity, "저장됨", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        //YearSpinner
        val minYear = 1980
        val maxYear = 2023
        val yearList = (minYear..maxYear).toList()
        val yearSpinner = dialogView.findViewById<Spinner>(R.id.yearSpinner)

        val yearAdapter = ArrayAdapter(this@TodolistActivity, android.R.layout.simple_spinner_item, yearList)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter
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
        adapter.itemClickListener = object : TodoAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int) {
                Toast.makeText(this@TodolistActivity, "itemclicked", Toast.LENGTH_SHORT).show()
            }
        }


        binding.apply {
            // recyclerView
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter


            // floatingActionButton
            floatingActionButton.setOnClickListener {

                val addDialog = dialogBuilder.create()
                addDialog.show()



                val newChildRef= rdb.push()
                val childKey = newChildRef.key
                val item = TodoItem(childKey!!, "this is content", "2023.06.21", "17:40", false)
                newChildRef.setValue(item)
            }


            // calendarView
            calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                val selectedDateData = Calendar.getInstance()
                selectedDateData.set(year, month, dayOfMonth)

                // 선택된 날짜를 이용하여 필요한 작업을 수행합니다.
                // 예를 들어, 선택된 날짜를 문자열로 변환하여 출력하거나, 다른 기능에 활용할 수 있습니다.

                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val dateString = dateFormat.format(selectedDateData.time)

                Log.d("SelectedDate", dateString)
                selectedDate = dateString

                updateList()
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
}