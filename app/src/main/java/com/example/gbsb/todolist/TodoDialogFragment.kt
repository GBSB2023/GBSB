package com.example.gbsb.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gbsb.databinding.FragmentTodoDialogBinding
import java.time.LocalDateTime

class TodoDialogFragment : DialogFragment() {

    private lateinit var binding : FragmentTodoDialogBinding
    private lateinit var selectedCalendarDate:LocalDateTime
    
    // month start with 1
    private var selectedAddScheduleDate = LocalDateTime.now()

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"

        fun newInstance(selectedDate: LocalDateTime): TodoDialogFragment {
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
        fun onDialogClosed(chosenDateTime : LocalDateTime, content:String)
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
            selectedCalendarDate = (it.getSerializable(ARG_SELECTED_DATE) as LocalDateTime?)!!
        }

        binding = FragmentTodoDialogBinding.inflate(layoutInflater)

        // Create Builder
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("일정 추가")
            .setView(binding.root)
                // Run when the Save button is pressed
            .setPositiveButton("저장") { dialog, which ->

                val inputContent = binding.contentEdit.text.toString()
                if(inputContent.isNotEmpty()){
                    todoListener?.onDialogClosed(selectedAddScheduleDate, inputContent)
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "일정 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
                // Run when the Cancel button is pressed
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        initialSetting()
        return builder.create()
    }

    private fun setChoosedDateTime(y:Int = -1, m:Int = -1, d:Int = -1, hour:Int = -1, minute:Int = -1) {
        // month start with 0
        if(y!= -1) selectedAddScheduleDate = selectedAddScheduleDate.withYear(y)
        if(m!= -1) selectedAddScheduleDate = selectedAddScheduleDate.withMonth(m)
        if(d!= -1) selectedAddScheduleDate = selectedAddScheduleDate.withDayOfMonth(d)
        if(hour!= -1) selectedAddScheduleDate = selectedAddScheduleDate.withHour(hour)
        if(minute!= -1) selectedAddScheduleDate = selectedAddScheduleDate.withMinute(minute)
    }

    // Set Date to selected date
    private fun initialSetting() {

        // Get date from todolistActivity
        val curYear = selectedCalendarDate.year
        val curMonth = selectedCalendarDate.monthValue // monthValue is start with 1
        val curDay = selectedCalendarDate.dayOfMonth

        // Set initial date to selected date
        setChoosedDateTime(y=curYear, m= curMonth, d= curDay)

        binding.apply {
            datePicker.init(curYear, curMonth-1, curDay
            ) { view, year, monthOfYear, dayOfMonth ->

                setChoosedDateTime(y=year, m= monthOfYear+1, d= dayOfMonth)

            }
            timePicker.setOnTimeChangedListener {
                    timePicker, hourOfDay, minute ->

                setChoosedDateTime(hour= hourOfDay, minute = minute)

            }
        }

    }
}