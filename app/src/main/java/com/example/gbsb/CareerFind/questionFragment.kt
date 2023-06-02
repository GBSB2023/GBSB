package com.example.gbsbwork

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class questionFragment : Fragment() { // 질문 Fragment
    val questionList : ArrayList<question_List> = ArrayList()
    var number : Int = 0
    private var selectedOption: String? = null
    lateinit var timeTextView: TextView
    lateinit var questionTextView: TextView
    lateinit var btn1 : RadioButton
    lateinit var btn2 : RadioButton
    lateinit var btn3 : RadioButton
    lateinit var btn4 : RadioButton
    lateinit var btn5 : RadioButton


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inputData()
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        timeTextView = view.findViewById(R.id.time)
        questionTextView = view.findViewById(R.id.question)
        btn1 = view.findViewById(R.id.radioButton1)
        btn2 = view.findViewById(R.id.radioButton2)
        btn3 = view.findViewById(R.id.radioButton3)
        btn4 = view.findViewById(R.id.radioButton4)
        btn5 = view.findViewById(R.id.radioButton5)

        // 텍스트 설정 예시
        timeTextView.text = (number+1).toString()+"/6"
        questionTextView.text = questionList[number].question
        btn1.text = questionList[number].frist
        btn2.text = questionList[number].second
        btn3.text = questionList[number].third
        btn4.text = questionList[number].fourth
        btn5.text = questionList[number].fiftth

        // 라디오 버튼 선택 시 동작할 리스너 등록
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedOption = when (checkedId) {
                R.id.radioButton1 -> "1"
                R.id.radioButton2 -> "2"
                R.id.radioButton3 -> "3"
                R.id.radioButton4 -> "4"
                R.id.radioButton5 -> "5"
                else -> null
            }
        }

        return view
    }

    fun filter(set: Int){
        number = set
    }
    fun getSelectedOption(): String? {
        return selectedOption
    }

    fun inputData(){ // 질문 데이터 삽입
        questionList.add(question_List("프로그램을 사용하다가 오류을 발견했다.\n모두의 중요도는 동일하다고 할때, 어떤 오류를 먼저 고치고 싶은가?",
            "1. 서브 페이지 화면 로딩 시간이 딜레이 되는 현상","2. 데이터 분석을 통한 맞춤형 광고 서비스 오류",
            "3. 홈페이지 화면의 뒤틀린 이미, 폰트 깨짐 현상","4. (개인정보 보호법에 저촉되지 않는 상황) 민감 정보가 노출되는 상황",
            "5. 오류가 발생했을때, 할 수 있는 게임"))
        questionList.add(question_List("여러분의 서비스가 성공 했을때, 서비스를 성공시킨 요소는?",
            "1. 데이터 분석을 통한 효과적인 개인 맞춤형 서비스","2. 화려하거나 품격있는 디자인, 흥미로운 콘텐츠 제작",
            "3. 게임적인 요소 도입","4. 안정적인 서비스 응용","5. 믿을수 있는 정보 관리"))
        questionList.add(question_List("당신은 음료 회사의 잘나가는 직원으로, 사장님이 당신에게 신메뉴 개발을 일임하겠다고 합니다. 당신의 음료 개발 방법은?"
            ,"1. 손님들의 눈길을 확 끌어보자! 요즘 유행하는 화려한 디자인으로 포장한 음료!",
            "2. 고객센터가 최우선! 프렌차이즈 매장마다 레시피가 다르면 안되니까 정확한 용량과 제조법은 필수!",
            "3. 인기 많은 음료 싹가지고 와! 왜 인기가 있었는지, 인기 요인을 알아보자",
            "4. 요즘은 홍보가 반은 먹고 간다! SNS 챌린지 이벤트로 사람들의 관심을 끌자!",
            "5. 레시피도 만드는 것도 중요하지만, 우리만의 독자적인 레시피가 새어나가지 않게 조심해야 해!"))
        questionList.add(question_List("내가 만든 프로그램에 새로운 기능을 추가하고 싶다, 어디부터 고칠까?",
            "1. 보기 좋고, 사용하기 편한게 최고야! ","2. 좀 더 빠르게 성능을 올릴 수 있을거 같은데..",
            "3. 나에게 딱 맞는 프로그램으로 만들고 싶어","4. 내 개인정보가 절대 유출되어서는 안돼!",
            "5. 재밌는 나만의 이스터에그를 만들어야지"))
        questionList.add(question_List("당신은 영양제를 구입하려고 한다. 당신이 가장 먼저 고려하는 것은?",
            "1. 일단 효과가 좋은 걸 사야지! 고농축 영양제 구매!","2. 화려한 상품 상세페이지가 내 눈을 사로잡는다. ",
            "3. 동일한 영양제 함량을 제조사 별로 비교해야 봐야지","4. 믿을 수 있는 영양제가 맞나? 꼼꼼하게 따져야지! ",
            "5. 맛없으면 결국 안 먹어! 젤리로 된 제품은 없나? "))
        questionList.add(question_List("망해가는 빵집을 인수했다. 뭐부터 개선해야 할까?",
            "1.  어떤 빵이 대세지? 수요 조사하기","2. 고객 맞춤 메뉴를 개발하자!","3. 빵집은 빵이 맛있어야지! 효율이 좋은 오븐 구매하기",
            "4. 요즘은 감성을 중요시 하니까~인테리어 바꾸기 ","5. 빵집의 위생과 안전을 위해! 업체 등록하기"))
    }
}