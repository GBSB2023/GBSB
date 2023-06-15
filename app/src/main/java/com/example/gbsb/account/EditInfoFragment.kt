package com.example.gbsb.account

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gbsb.databinding.EditInfoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditInfoFragment:DialogFragment() {
    private var _binding: EditInfoBinding?= null
    private val binding get() = _binding!!
    val model: AccountViewModel by activityViewModels()
    lateinit var info: Info
    private lateinit var infodb: DatabaseReference
    lateinit var dialog:AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = EditInfoBinding.inflate(layoutInflater)
        val view = binding.root

        var uid=model.getuId()
        infodb = Firebase.database.getReference("Info").child(uid)

        info=model.getData()
        binding.apply {
            editEmail.text = info.email
            editCall.setText(info.call)
            editBirth.setText(info.birth)
            editMajor.setText(info.major)
        }

        initPattern()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        builder.setPositiveButton("저장"){
                dialog, _ ->
            binding.apply {
                info.call=editCall.text.toString()
                info.birth=editBirth.text.toString()
                info.major=editMajor.text.toString()
            }
            model.setData(info)
            infodb.setValue(info)
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") {
                dialog, _ ->
            dialog.dismiss()
        }

        dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
        }

        initBtn()
        return dialog
    }

    private fun initBtn() {
        binding.validBtn.setOnClickListener {
            if(validateRegister()){
                Toast.makeText(activity, "올바른 양식입니다!", Toast.LENGTH_LONG).show()
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = true
            }else{
                Toast.makeText(activity, "양식을 올바르게 작성해주세요.", Toast.LENGTH_LONG).show()
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
            }
        }
    }

    private fun initPattern() {
        val callPattern = "^0\\d{1,2}-\\d{3,4}-\\d{4}$"
        val callText = binding.editCall
        callText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(callPattern.toRegex())) {
                    callText.error = null
                } else {
                    callText.error = "010-1234-5678 또는 02-1234-5678 과 같은 전화번호 형식을 입력해주세요."
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            }

        })

        val birthPattern = "^\\d{4}-\\d{2}-\\d{2}$"
        val birthText = binding.editBirth
        birthText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()

                if (input.matches(birthPattern.toRegex())) {
                    birthText.error = null
                } else {
                    birthText.error = "2000-01-01 과 같은 생일 형식으로 입력해주세요."
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            }

        })

    }

    private fun validateRegister(): Boolean {
        var isValid = true

        binding.apply {
            if(!editCall.error.isNullOrEmpty()||!editBirth.error.isNullOrEmpty()){
                isValid = false
            }
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}