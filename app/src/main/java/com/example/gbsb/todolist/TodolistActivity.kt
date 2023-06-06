package com.example.gbsb.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.MainActivity
import com.example.gbsb.databinding.ActivityTodolistBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TodolistActivity : AppCompatActivity() , TodoDialogFragment.TodoDialogListener {

    lateinit var binding : ActivityTodolistBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: TodoAdapter
    private var selectedDateTime = LocalDateTime.now()
    var userFirebasePath = "TodoList/uid"

    // Number of limits that can be expressed in the calendar list
    private val listSizeLimit = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun initLayout() {
        // database
        rdb = Firebase.database.getReference(userFirebasePath)
        val query = rdb.limitToLast(listSizeLimit)
            .orderByChild("date")
            .equalTo(formatToDateString(selectedDateTime))

        val option = FirebaseRecyclerOptions.Builder<Schedule>()
            .setQuery(query, Schedule::class.java)
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

            // SwipeHelper
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            recyclerView
            // floatingActionButton
            floatingActionButton.setOnClickListener {
                val dialogFragment = TodoDialogFragment.newInstance(selectedDateTime)

                dialogFragment.show(supportFragmentManager, "todo_item_add_dialog")
            }


            // calendarView
            calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                selectedDateTime = LocalDateTime.now()
                    .withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(dayOfMonth)
//                Toast.makeText(this@TodolistActivity, selectedDateTime.toString(), Toast.LENGTH_SHORT).show()
                refreshData()
            }

            // BackBtn
            backBtn.setOnClickListener {
                val intent = Intent(this@TodolistActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }

    // If you select a date, update the To Do list for that date
    @SuppressLint("NotifyDataSetChanged")
    private fun refreshData() {
        val query = rdb.limitToLast(listSizeLimit)
            .orderByChild("date")
            .equalTo(formatToDateString(selectedDateTime))

        val option = FirebaseRecyclerOptions.Builder<Schedule>()
            .setQuery(query, Schedule::class.java)
            .build()
        adapter.updateOptions(option)
        adapter.notifyDataSetChanged()
    }

    // Store Schedule data in firebase
    override fun onDialogClosed(chosenDateTime: LocalDateTime, content: String) {
        val newChildRef= rdb.push()
        val childKey = newChildRef.key
        val item = Schedule(childKey!!, content,
            formatToDateString(chosenDateTime), formatToTimeString(chosenDateTime),false)
        newChildRef.setValue(item)
    }

    // LocalDateTime -> "yyyy-MM-dd"
    private fun formatToDateString(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(formatter)// ex: "2023-06-02 15:30"
    }

    // LocalDateTime -> "HH:mm"
    private fun formatToTimeString(localDateTime: LocalDateTime):String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localDateTime.format(formatter)
    }


    // Swipe left to delete the schedule
    private val itemTouchHelperCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.deleteItem(viewHolder.absoluteAdapterPosition)
        }
    }
}