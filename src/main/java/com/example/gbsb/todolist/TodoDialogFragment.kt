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
        fun addSchedule(chosenDateTime : LocalDateTime, content:String)
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

                val inputContent = binding.addContent.text.toString()

                if(inputContent.isNotEmpty()){
                    todoListener?.addSchedule(selectedAddScheduleDate, inputContent)
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


    // Set Date to selected date
    private fun initialSetting() {

        // Get date from todolistActivity
        val curYear = selectedCalendarDate.year
        val curMonth = selectedCalendarDate.monthValue // monthValue is start with 1
        val curDay = selectedCalendarDate.dayOfMonth


        // Set initial date to selected date
        selectedAddScheduleDate = selectedAddScheduleDate
            .withYear(curYear)
            .withMonth(curMonth)
            .withDayOfMonth(curDay)


        binding.apply {
            addDatePicker.init(curYear, curMonth-1, curDay
            ) { _, year, monthOfYear, dayOfMonth ->

                selectedAddScheduleDate = selectedAddScheduleDate
                    .withYear(year)
                    .withMonth(monthOfYear + 1)
                    .withDayOfMonth(dayOfMonth)

            }
            addTimePicker.setOnTimeChangedListener {
                    _, hourOfDay, minute ->

                selectedAddScheduleDate = selectedAddScheduleDate
                    .withHour(hourOfDay)
                    .withMinute(minute)

            }
        }

    }
}