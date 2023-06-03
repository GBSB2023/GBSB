package com.example.gbsb.account

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gbsb.databinding.EditIntroduceBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditIntroduceFragment:DialogFragment() {
    private var _binding: EditIntroduceBinding?= null
    private val binding get() = _binding!!
    val model: AccountViewModel by activityViewModels()
    lateinit var info: Info
    private lateinit var infodb: DatabaseReference

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = EditIntroduceBinding.inflate(layoutInflater)
        val view = binding.root

        var uid=model.getuId()
        infodb = Firebase.database.getReference("Info").child(uid)

        info=model.getData()
        binding.apply {
            editIntroduce.setText(info.introduce, TextView.BufferType.EDITABLE)

            val currentLength = info.introduce.length
            textCount.text = "$currentLength / 500"
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        builder.setPositiveButton("저장"){
                dialog, _ ->
            binding.apply {
                info.introduce=editIntroduce.text.toString()
            }
            model.setData(info)
            infodb.setValue(info)
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") {
                dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()

        binding.editIntroduce?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val currentLength = p0?.length ?: 0
                binding.textCount.text = "$currentLength / 500"
            }

        })

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}