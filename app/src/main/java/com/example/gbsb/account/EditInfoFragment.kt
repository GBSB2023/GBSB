package com.example.gbsb.account

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gbsb.databinding.EditInfoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditInfoFragment: DialogFragment() {
    private var _binding: EditInfoBinding?= null
    private val binding get() = _binding!!
    val model: AccountViewModel by activityViewModels()
    lateinit var info: Info
    private lateinit var infodb: DatabaseReference

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

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}