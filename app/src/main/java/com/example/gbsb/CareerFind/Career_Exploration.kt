package com.example.gbsb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.account.AccountActivity
import com.example.gbsb.databinding.CareerExplorationBinding
import com.google.firebase.auth.FirebaseAuth

class Career_Exploration : AppCompatActivity() {  // 진로 탐색 화면 (질문 포함)
    lateinit var binding: CareerExplorationBinding
    private var number = 0
    private val questionFragmentTag = "questionFragment"
    val currentUser = FirebaseAuth.getInstance().currentUser

    val UserChoiceList: ArrayList<String> = ArrayList<String>(6).apply {
        repeat(6) { add("")}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CareerExplorationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        updateFragment()

        binding.previous.setOnClickListener {
            if (number == 0) {
                Toast.makeText(this, "현재 맨 앞 설문지입니다.", Toast.LENGTH_SHORT).show()
            } else {
                number--
                updateFragment()
            }
        }

        binding.next.setOnClickListener {
            val questionFragment = supportFragmentManager.findFragmentByTag(questionFragmentTag) as? questionFragment
            val selectedOption = questionFragment?.getSelectedOption()
            if (number == 5) {
                if (selectedOption != null)  {
                    val intent = Intent(this, Career_Result_Activity::class.java)
                    intent.putStringArrayListExtra("userChoiceList", UserChoiceList)
                    startActivity(intent)
                    finish()
                }
                else Toast.makeText(this,"아무것도 선택하지 않았습니다.\n선택 후 다음 창으로 이동해 주세요",Toast.LENGTH_SHORT).show()
            } else {
                if (selectedOption != null)  {
                    UserChoiceList.add(number, selectedOption)
                    number++
                    updateFragment()
                }
                else Toast.makeText(this,"아무것도 선택하지 않았습니다.\n선택 후 다음 창으로 이동해 주세요",Toast.LENGTH_SHORT).show()
            }
        }
        binding.back.setOnClickListener {//뒤로 가기
            val intent = Intent(this, FindCourse::class.java)
            startActivity(intent)
            finish()
        }

        binding.id.setOnClickListener { //사용자 정보 이동
            if (currentUser?.isAnonymous == false){
                val i= Intent(this, AccountActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(this@Career_Exploration,"익명 로그인의 경우 해당 기능을 이용할 수 없습니다",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createQuestionFragment(): questionFragment {
        val fragment = questionFragment()
        val bundle = Bundle()
        bundle.putInt("number", number)
        fragment.arguments = bundle
        return fragment
    }

    private fun updateFragment() {
        val fragment = createQuestionFragment()
        fragment.filter(number)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment, questionFragmentTag)
            .commit()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("number", number)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        number = savedInstanceState.getInt("number", 0)
        val fragment = supportFragmentManager.findFragmentByTag(questionFragmentTag) as? questionFragment
        fragment?.filter(number)
    }
}


