package com.example.gbsb.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gbsb.MainActivity
import com.example.gbsb.databinding.ActivityTodolistBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TodolistActivity : AppCompatActivity() , TodoDialogFragment.TodoDialogListener {

    lateinit var binding : ActivityTodolistBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: TodoAdapter
    lateinit var query:Query
    private var selectedDateTime = LocalDateTime.now()

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
        query = MainActivity.getRDB().limitToLast(listSizeLimit)
            .orderByChild("date")
            .equalTo(formatToDateString(selectedDateTime))

        // Replace "recyclerView" with "textView" if there is no schedule on the selected date
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.hasChildren()){
                    Log.d("TodolistActivity", "There is a schedule for that date")
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.noScheduleText.visibility = View.GONE
                }else{
                    Log.d("TodolistActivity", "No schedule for that date")
                    binding.recyclerView.visibility = View.GONE
                    binding.noScheduleText.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        val option = FirebaseRecyclerOptions.Builder<Schedule>()
            .setQuery(query, Schedule::class.java)
            .build()

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = TodoAdapter(option)
        adapter.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@TodolistActivity, "itemclicked", Toast.LENGTH_SHORT).show()
            }

            override fun onCheckedChange(scheduleID: String, isChecked: Boolean) {
                MainActivity.getRDB().child(scheduleID).child("done").setValue(isChecked)
                    .addOnSuccessListener {
                        Log.d("TodolistActivity", "is Done check success")
                    }
                    .addOnFailureListener {
                        Log.e("TodolistActivity", "is Done check fail")
                    }
                adapter.onDataChanged()
            }
        }


        binding.apply {
            // recyclerView
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            // SwipeHelper
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            // floatingActionButton
            floatingActionButton.setOnClickListener {
                val dialogFragment = TodoDialogFragment.newInstance(selectedDateTime)

                dialogFragment.show(supportFragmentManager, "todo_item_add_dialog")
            }


            // calendarView
            calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                if(formatToDateString(selectedDateTime) != formatToDateString(LocalDateTime.of(year, month+1, dayOfMonth,0,0))){
                    selectedDateTime = LocalDateTime.now()
                        .withYear(year)
                        .withMonth(month + 1)
                        .withDayOfMonth(dayOfMonth)
//                Toast.makeText(this@TodolistActivity, selectedDateTime.toString(), Toast.LENGTH_SHORT).show()
                    refreshData()
                    Log.d("TodolistActivity", "date changed")
                }else{
                    Log.d("TodolistActivity", "date not changed")

                }
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
        query = MainActivity.getRDB().limitToLast(listSizeLimit)
            .orderByChild("date")
            .equalTo(formatToDateString(selectedDateTime))
        // Replace "recyclerView" with "textView" if there is no schedule on the selected date
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.hasChildren()){
                    Log.d("TodolistActivity", "There is a schedule for that date")
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.noScheduleText.visibility = View.GONE
                }else{
                    Log.d("TodolistActivity", "No schedule for that date")
                    binding.recyclerView.visibility = View.GONE
                    binding.noScheduleText.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        val option = FirebaseRecyclerOptions.Builder<Schedule>()
            .setQuery(query, Schedule::class.java)
            .build()
        adapter.updateOptions(option)
        adapter.notifyDataSetChanged()
    }

    // Store Schedule data in firebase
    override fun onDialogClosed(chosenDateTime: LocalDateTime, content: String) {
        val newChildRef= MainActivity.getRDB().push()
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