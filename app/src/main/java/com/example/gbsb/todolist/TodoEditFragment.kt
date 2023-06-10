package com.example.gbsb.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gbsb.databinding.FragmentTodoEditBinding
import com.google.android.play.core.integrity.p
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.min

class TodoEditFragment : DialogFragment() {

    private lateinit var binding : FragmentTodoEditBinding

    // Schedule data
    private lateinit var selectedDate:LocalDate
    private lateinit var selectedTime:LocalTime
    private lateinit var initialContent:String
    private var initialDone = false

    private lateinit var editedSchedule:Schedule

    companion object {
        private const val ARG_SELECTED_SCHEDULE = "selected_schedule"

        fun newInstance(schedule: Schedule): TodoEditFragment {
            val fragment = TodoEditFragment()
            val args = Bundle().apply {
                putSerializable(ARG_SELECTED_SCHEDULE, schedule)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var todoEditListener: TodoEditListener?= null

    interface TodoEditListener{
        fun editSchedule(editedSchedule: Schedule)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            todoEditListener = context as TodoEditListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement TodoEditListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let {
            editedSchedule = (it.getSerializable(ARG_SELECTED_SCHEDULE) as Schedule?)!!
        }

        binding = FragmentTodoEditBinding.inflate(layoutInflater)

        // Create Builder
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("일정 수정")
            .setView(binding.root)
            // Run when the Save button is pressed
            .setPositiveButton("수정") { dialog, which ->
                if(binding.editContent.text.isNotEmpty()){

                    editedSchedule.date = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
                    editedSchedule.time = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString()
                    editedSchedule.content = binding.editContent.text.toString()
                    editedSchedule.done = binding.editCheckBox.isChecked

                    todoEditListener?.editSchedule(editedSchedule)
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
        // Get selected schedule information
        selectedDate = LocalDate.parse(editedSchedule.date, DateTimeFormatter.ISO_DATE)!!
        selectedTime = LocalTime.now().withHour(editedSchedule.time.slice(IntRange(0,1)).toInt()).withMinute(editedSchedule.time.slice(IntRange(3,4)).toInt())
        initialContent = editedSchedule.content
        initialDone = editedSchedule.done


        binding.apply {

            // DatePicker initialization and listener
            editDatePicker.init(selectedDate.year, selectedDate.monthValue-1, selectedDate.dayOfMonth
            ) { _, year, monthOfYear, dayOfMonth ->
                selectedDate = selectedDate
                    .withYear(year)
                    .withMonth(monthOfYear+1)
                    .withDayOfMonth(dayOfMonth)
            }


            // TimePicker initialization and listener
            editTimePicker.hour = selectedTime.hour
            editTimePicker.minute = selectedTime.minute
            editTimePicker.setOnTimeChangedListener {
                    _, hourOfDay, minute ->
                selectedTime = selectedTime
                    .withHour(hourOfDay)
                    .withMinute(minute)

            }


            // Content initialization
            editContent.setText(initialContent)


            // Done initialization
            editCheckBox.isChecked = initialDone
        }

    }
}