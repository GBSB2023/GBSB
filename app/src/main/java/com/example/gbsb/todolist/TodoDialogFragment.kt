package com.example.gbsb.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gbsb.databinding.FragmentTodoDialogBinding
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.min

class TodoDialogFragment : DialogFragment() {

    private lateinit var binding : FragmentTodoDialogBinding
    private var selectedDate:LocalDate? = null
    
    // month start with 1
    private var chooseLocalDateTime = LocalDateTime.now()

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        fun newInstance(selectedDate: LocalDate): TodoDialogFragment {
            val fragment = TodoDialogFragment()
            val args = Bundle().apply {
                putSerializable(ARG_SELECTED_DATE, selectedDate)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var todoListener: TodoDialogListener?= null

    interface TodoDialogListener{
        fun onDialogClosed(choosedDate : LocalDateTime, content:String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            todoListener = context as TodoDialogListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement TodoDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let {
            selectedDate = it.getSerializable(ARG_SELECTED_DATE) as LocalDate?
        }

        binding = FragmentTodoDialogBinding.inflate(layoutInflater)

        // Create Builder
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("일정 추가")
            .setView(binding.root)
            .setPositiveButton("저장") { dialog, which ->

                val inputContent = binding.contentEdit.text.toString()
                if(inputContent.isNotEmpty()){
                    todoListener?.onDialogClosed(chooseLocalDateTime, inputContent)
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "일정 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }

        initialSetting()
        return builder.create()
    }

    private fun setChoosedDateTime(y:Int = -1, m:Int = -1, d:Int = -1, hour:Int = -1, minute:Int = -1) {
        // month start with 0
        if(y!= -1) chooseLocalDateTime = chooseLocalDateTime.withYear(y)
        if(m!= -1) chooseLocalDateTime = chooseLocalDateTime.withMonth(m)
        if(d!= -1) chooseLocalDateTime = chooseLocalDateTime.withDayOfMonth(d)
        if(hour!= -1) chooseLocalDateTime = chooseLocalDateTime.withHour(hour)
        if(minute!= -1) chooseLocalDateTime = chooseLocalDateTime.withMinute(minute)
    }

    // Set Date to selected date
    private fun initialSetting() {

        // Get date from todolistActivity
        val curYear = selectedDate!!.year
        val curMonth = selectedDate!!.monthValue // monthValue is start with 1
        val curDay = selectedDate!!.dayOfMonth

//        Log.d("selected Date", selectedDate.toString())
//        Log.d("selected Date monthValue", selectedDate!!.monthValue.toString())
        // Set initial date to selected date
        setChoosedDateTime(y=curYear, m= curMonth, d= curDay)

        binding.apply {
            datePicker1.init(curYear, curMonth-1, curDay
            ) { view, year, monthOfYear, dayOfMonth ->
                // 날짜가 변경되었을 때의 동작
                setChoosedDateTime(y=year, m= monthOfYear+1, d= dayOfMonth)
//                Log.d("choosed Date monthValue", (monthOfYear+1).toString())

            }
            timePicker.setOnTimeChangedListener {
                    timePicker, hourOfDay, minute ->
                setChoosedDateTime(hour= hourOfDay, minute = minute)
            }
        }

    }
}