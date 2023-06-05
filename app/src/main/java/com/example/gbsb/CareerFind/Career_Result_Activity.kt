package com.example.gbsb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gbsb.databinding.CareerResultBinding
import java.util.*
import kotlin.collections.ArrayList

class Career_Result_Activity : AppCompatActivity() {
    lateinit var binding : CareerResultBinding
    var userChoiceList : ArrayList<String> = ArrayList()
    var SI :Int = 0
    var DataBase : Int = 0
    var Web : Int = 0
    var AI : Int = 0
    var Security : Int = 0
    var App : Int = 0
    var Cloud : Int = 0
    var Game : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CareerResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userChoiceList = intent.getStringArrayListExtra("userChoiceList") as ArrayList<String>
        initLayout()
    }

    private fun initLayout() {
        val type = setInfor()
        binding.back.setOnClickListener{// 뒤로 가기
            val intent = Intent(this, FindCourse::class.java)
            startActivity(intent)
            finish()
        }
        binding.id.setOnClickListener { // 사용자 정보 이동
            val i= Intent(this, AccountActivity::class.java)
            startActivity(i)
        }
        binding.check.setOnClickListener{// 다른 결과 확인
            val intent = Intent(this, another_result::class.java)
            intent.putExtra("RecommandUser", type)
            startActivity(intent)
            finish()
        }
        binding.save.setOnClickListener{//결과 저장하기
            val intent = Intent(this, RecommandAcitivty::class.java)
            intent.putExtra("RecommandUser", type)
            startActivity(intent)
            finish()
        }
    }
    @SuppressLint("SetTextI18n")
    fun setInfor() : String{
        val set = setCareer()
        binding.explain1.text =set+" 개발자"
        if (set == "SI/SM"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.si)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "컴퓨터시스템에서 운용되는 각종 유틸리티 소프트웨어를 설계하고 개발합니다. "
            return "1"
        }
        else if (set == "DataBase"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.database)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "데이터베이스 개발자는 데이터베이스 시스템을 설계, 구축, 유지보수하는 전문가입니다."
            return "2"
        }
        else if (set == "Web"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.frontend)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "백엔드 API에서 가져온 데이터의 출력, 입력을 통한 비즈니스 로직 구성과 사용자와 대화하는 사용자 인터페이스 부분을 작업하는 개발자를 말합니다."
            return "3"
        }
        else if (set == "AI"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.ai)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "인공지능 개발자는 인공지능 시스템을 설계, 개발 및 유지보수하는 전문가입니다.\n"
            return "4"
        }
        else if (set == "Security"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.security)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "보안 개발자는 시스템 및 소프트웨어의 보안 측면을 설계, 개발 및 유지보수하는 전문가입니다.\n"
            return "5"
        }
        else if (set == "App"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.app)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "기획자가 기획한 앱을 구체화해 스마트폰에서 사용이 가능하도록 프로그램을 개발하고 오류를 수정하고 업데이트하는 업무를 수행합니다."
            return "6"
        }
        else if (set == "Cloud"){
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.cloud)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "클라우드 컴퓨팅 환경을 설계, 구축, 운영 및 유지보수하는 역할을 담당하는 전문가입니다."
            return "7"
        }
        else {
            val imageView: ImageView = findViewById(R.id.imageView1)
            val drawable = imageView.context.getDrawable(R.drawable.game)
            imageView.setImageDrawable(drawable)
            binding.explain2.text = "게임 개발자는 창의적인 아이디어와 기술적인 능력을 바탕으로 게임을 개발하는 전문가입니다."
            return "8"
        }
    }
    fun setCareer(): String {
        when(userChoiceList[0]){
            "1" -> Cloud++
            "2" -> DataBase++
            "3" -> Web++
            "4" -> Security++
            "5" -> Game++
        }
        when(userChoiceList[1]){
            "1" -> DataBase++
            "2" -> SI++
            "3" -> Game++
            "4" -> Cloud++
            "5" -> Security++
        }
        when(userChoiceList[2]){
            "1" -> Web++
            "2" -> SI++
            "3" -> AI++
            "4" -> Security++
            "5" -> Game++
        }
        when(userChoiceList[3]){
            "1" -> Web++
            "2" -> SI++
            "3" -> AI++
            "4" -> Security++
            "5" -> Game++
        }
        when(userChoiceList[4]){
            "1" -> SI++
            "2" -> Web++
            "3" -> DataBase++
            "4" -> Security++
            "5" -> AI++
        }
        when(userChoiceList[5]){
            "1" -> DataBase++
            "2" -> AI++
            "3" -> SI++
            "4" -> Web++
            "5" -> Security++
        }

        return findMaxValue()
    }
    fun findMaxValue(): String {
        val max = listOf(SI, DataBase, Web, AI, Security, App, Cloud, Game).maxOrNull()

        return when (max) {
            SI -> "SI/SM"
            DataBase -> "DataBase"
            Web -> "Web"
            AI -> "AI"
            Security -> "Security"
            App -> "App"
            Cloud -> "Cloud"
            Game -> "Game"
            else -> ""
        }
    }
}
