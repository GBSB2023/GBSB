package com.example.gbsbwork

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsbwork.databinding.AnotherResultBinding

class another_result: AppCompatActivity() {  // 전체 진로 추천 결과 보여 주는 화면 
    lateinit var binding : AnotherResultBinding
    lateinit var adapter : reulst_Adapter
    val RecommandResultList: ArrayList<RecommandResultData> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AnotherResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datainput()
        initLayout()
    }
    fun datainput(){
        RecommandResultList.add(RecommandResultData("SI/SM","컴퓨터시스템에서 운용되는 각종 유틸리티 소프트웨어를 설계하고 개발합니다. "))
        RecommandResultList.add(RecommandResultData("DataBase","데이터베이스 개발자는 데이터베이스 시스템을 설계, 구축, 유지보수하는 전문가입니다."))
        RecommandResultList.add(RecommandResultData("WebFront","백엔드 API에서 가져온 데이터의 출력, 입력을 통한 비즈니스 로직 구성과 사용자와 대화하는 사용자 인터페이스 부분을 작업하는 개발자를 말합니다."))
        RecommandResultList.add(RecommandResultData("AI","인공지능 개발자는 인공지능 시스템을 설계, 개발 및 유지보수하는 전문가입니다.\n"))
        RecommandResultList.add(RecommandResultData("Security","보안 개발자는 시스템 및 소프트웨어의 보안 측면을 설계, 개발 및 유지보수하는 전문가입니다.\n"))
        RecommandResultList.add(RecommandResultData("App","기획자가 기획한 앱을 구체화해 스마트폰에서 사용이 가능하도록 프로그램을 개발하고 오류를 수정하고 업데이트하는 업무를 수행합니다."))
        RecommandResultList.add(RecommandResultData("Cloud","클라우드 컴퓨팅 환경을 설계, 구축, 운영 및 유지보수하는 역할을 담당하는 전문가입니다."))
        RecommandResultList.add(RecommandResultData("Game","게임 개발자는 창의적인 아이디어와 기술적인 능력을 바탕으로 게임을 개발하는 전문가입니다."))
    }


    private fun initLayout() {
        val receivedData = intent.getStringExtra("RecommandUser")
        binding.recyclerview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter = reulst_Adapter(RecommandResultList)
        binding.back.setOnClickListener{
            val intent = Intent(this, RecommandAcitivty::class.java)
            intent.putExtra("RecommandUser", receivedData)
            startActivity(intent)
            finish()
        }
        binding.recyclerview.adapter = adapter

//        binding.id.setOnClickListener{
//            val intent = Intent(this, RecommandAcitivty::class.java)
//            intent.putExtra("RecommandUser", receivedData)
//            startActivity(intent)
//            finish()
//        }

    }

}
