package com.example.gbsbwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsbwork.databinding.CareerExplorationBinding

class Career_Exploration : AppCompatActivity() {  // 진로 탐색 화면 (질문 포함)
    lateinit var binding: CareerExplorationBinding
    private var number = 0
    private val questionFragmentTag = "questionFragment"
    val UserChoiceList: ArrayList<String> = ArrayList<String>(6).apply {
        repeat(6) { add("") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CareerExplorationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        updateFragment()

        // "이전" 버튼을 클릭할 때마다 이전 질문으로 이동합니다.
        binding.previous.setOnClickListener {
            if (number == 0) {
                Toast.makeText(this, "현재 맨 앞 설문지입니다.", Toast.LENGTH_SHORT).show()
            } else {
                number--
                updateFragment()
            }
        }

        // "다음" 버튼을 클릭할 때마다 다음 질문으로 이동합니다.
        // 마지막 질문일 경우, 결과 화면으로 이동합니다.
        binding.next.setOnClickListener {
            if (number == 5) {
                val intent = Intent(this, Career_Result_Activity::class.java)
                intent.putStringArrayListExtra("userChoiceList", UserChoiceList)
                startActivity(intent)
                finish()
            } else {
                // 현재 질문에서 선택된 옵션을 가져옵니다.
                val questionFragment =
                    supportFragmentManager.findFragmentByTag(questionFragmentTag) as? questionFragment
                val selectedOption = questionFragment?.getSelectedOption()
                if (selectedOption != null) {
                    UserChoiceList.add(number, selectedOption)
                    number++
                    updateFragment()
                } else Toast.makeText(
                    this,
                    "아무것도 선택하지 않았습니다.\n선택 후 다음 창으로 이동해 주세요",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // "뒤로 가기" 버튼을 클릭하면 FindCourse 액티비티로 돌아갑니다.
        binding.back.setOnClickListener {
            val intent = Intent(this, FindCourse::class.java)
            startActivity(intent)
            finish()
        }

        // "사용자 정보" 버튼을 클릭하면 UserInfor 액티비티로 이동합니다.
        binding.id.setOnClickListener {
            val intent = Intent(this, UserInfor::class.java)
            startActivity(intent)
        }
    }

    // 질문 프래그먼트를 생성합니다.
    private fun createQuestionFragment(): questionFragment {
        val fragment = questionFragment()
        val bundle = Bundle()
        bundle.putInt("number", number)
        fragment.arguments = bundle
        return fragment
    }

    // 화면을 업데이트하여 현재 질문을 표시합니다.
    private fun updateFragment() {
        val fragment = createQuestionFragment()
       
