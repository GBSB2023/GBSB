package com.example.gbsb

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.gbsb.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.my_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        // 메인 액티비티에서 전달한 버튼 ID 가져오기
        val buttonId = intent.getIntExtra("buttonId", 0)

        // 버튼 ID에 따라 필요한 동작 수행
        when (buttonId) {
            1 -> {
                init()
                button1()
            }
            2 -> {
                init()
                button2()
            }
            3 -> {
                init()
                button3()
            }
            4 -> {
                init()
                button4()
            }
            5 -> {
                init()
                button5()
            }
            6 -> {
                init()
                button6()
            }
            7 -> {
                init()
                button7()
            }
            8 -> {
                init()
                button8()
            }
        }

        // 스피너 아이템 선택 이벤트 리스너 설정
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // 선택된 항목에 따라 해당하는 함수 호출
                when (selectedItem) {
                    "모바일 앱" -> {
                        init()
                        button1()
                    }
                    "웹" -> {
                        init()
                        button2()
                    }
                    "인공지능" -> {
                        init()
                        button3()
                    }
                    "클라우드" -> {
                        init()
                        button4()
                    }
                    "게임" -> {
                        init()
                        button5()
                    }
                    "보안" -> {
                        init()
                        button6()
                    }
                    "데이터베이스" -> {
                        init()
                        button7()
                    }
                    "전산시스템" -> {
                        init()
                        button8()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 항목도 선택되지 않았을 때의 동작
            }
        }

        // 뒤로 가기 버튼
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun init(){
        binding.button3.visibility = View.VISIBLE
        binding.bottomButton1.visibility = View.VISIBLE
        binding.button1.isChecked = false
        binding.button2.isChecked = false
        binding.button3.isChecked = false
    }

    private fun button1view(){
        binding.spinner.setSelection(0)
        binding.button1.text = "안드로이드"
        binding.button2.text = "iOS"
        binding.button3.text = "하이브리드"
        binding.textView1.text = "모바일 앱(App)이란?"
        binding.textView2.text = "모바일 앱 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text =
            "모바일 앱(Mobile Application)은 스마트폰에서 다운로드를 통해 프로그램을 설치하고 서비스를 즐기도록 만든 응용프로그램입니다.\n" +
                    "ex) sns, 캘린더, 쇼핑, 예약 앱 등"
        binding.addtextView2.text =
            "모바일 앱 개발자는 기획자가 기획한 앱을 구체화해 스마트폰에서 사용이 가능하도록 프로그램을 개발하고 오류를 수정하고 업데이트합니다."
        binding.addtextView3.text = "자바, 코틀린, 스위프트, 오브젝트 C"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl1 =
                "https://namu.wiki/w/%EB%B6%84%EB%A5%98:%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl1))
            startActivity(intent)
        }
    }
    private fun button1() {
        button1view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "안드로이드란?"
                binding.textView2.text = "안드로이드 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "안드로이드(Android)는 스마트폰, 태블릿 PC 같은 터치스크린 모바일 장치 용으로 디자인된 운영 체제입니다."
                binding.addtextView2.text = "안드로이드 개발자는 안드로이드 운영체제 안에서 모바일 앱을 구현합니다."
                binding.addtextView3.text = "■ 필요한 기술\n" +
                        "- 안드로이드 프레임워크 : 폰갭, 코로나(Corona) SDK, 앱셀 레이터, 더 앱 빌더, jQuery 모바일\n" +
                        "- 안드로이드 스튜디오 : 구글에서 만든 안드로이드 프로그래밍 개발 환경입니다.\n" +
                        "- 이클립스 : 자바를 비롯한 다양한 언어를 지원하는 프로그래밍 통합 개발 환경(IDE)이자 프로그래밍 도구입니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "- 자바(Java) : 안드로이드 운영체제를 이해하고 안드로이드 환경에서 앱을 개발하기 위해서 알아야 합니다.\n" +
                        "- 코틀린(Kotlin) : 구글에서 안드로이드 공식 언어로 지정해 많이 사용되고 있습니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl11 =
                        "https://namu.wiki/w/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl11))
                    startActivity(intent)
                }
            }
            else{
                button1view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "iOS란?"
                binding.textView2.text = "iOS 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text = "iOS는 Apple의 모바일 장치용 운영 체제입니다."
                binding.addtextView2.text = "iOS 개발자는 아이폰에서 쓸 수 있는 애플리케이션을 구현합니다."
                binding.addtextView3.text = "■ 필요한 기술\n" +
                        "- Mac 컴퓨터 : iOS 앱 개발에 사용되는 소프트웨어는 Mac 운영체제에서만 호환이 됩니다.\n" +
                        "- iOS SDK : iOS 운영체제에 기반한 앱 개발도구입니다.\n" +
                        "- iOS 프레임워크 : 애플에서 제공하는 프레임워크입니다.\n" +
                        "- Xcode : Apple의 macOS, iOS, watchOS 등의 소프트웨어 개발을 위한 통합 개발 환(IDE)입니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "- 스위프트(Swift) : Apple의 iOS와 macOS를 위한 프로그래밍 언어입니다.\n" +
                        "- 오브젝트 C : 애플의 iOS와 OS 운영체제에 기반한 앱 개발에 필요한 프로그밍 언어입니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl12 = "https://namu.wiki/w/Swift"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl12))
                    startActivity(intent)
                }
            }
            else{
                button1view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "하이브리드 앱이란?"
                binding.textView2.text = "하이브리드 앱 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "하이브리드(Hybrid)앱은 기본 기능은 HTML 등의 웹 표준 기술을 기반으로 구현하고, 앱을 만든 후 최종 앱 배포에 필요한 패키징 처리만 아이폰, 안드로이드 등 모바일 운영체제 별로 구현하는 앱입니다."
                binding.addtextView2.text = "하이브리드 앱 개발자는 웹 개발 기술로 앱을 구현합니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "웹 프로그래밍 언어 : 하이브리드 프레임워크가 자바스크립트, HTML, CSS를 기초로 하고 있습니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "1. 하이브리드 앱 개발 플랫폼을 사용할 수 있습니다.\n" +
                        "- 폰갭(PhoneGap) : 자바스크립트, HTML, CSS로 만들 수 있어 웹 개발자들도 쉽게 만들 수 있습니다. SDK 설치 없이 클라우드 컴파일러로 앱을 빌드할 수 있습니다.\n" +
                        "- 아이오닉(ionic) : 디자인과 그래픽 효과에 있어서 다양한 라이브러리가 있습니다.\n" +
                        "- 리액트 네이티브(React Native) : 안드로이드, iOS 등의 영역을 대상으로 다양한 기능을 제공합니다.\n" +
                        "- 구글 플러터(Flutter) : 자바와 코틀린이라는 안드로이드 개발 언어를 조합해 만들어진 DART라는 프로그래밍 언어로 개발하는 프레임워크입니다.\n" +
                        "- 사마린(Xamarin) : C#을 사용하며 사용가능한 라이브러리가 많습니다.\n" +
                        "2. 웹뷰에 올려서 개발할 수 있습니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "크로스 플랫폼 지원 언어를 사용합니다.\n" +
                        "1. 안드로이드\n" +
                        "- Java 또는 Kotlin + 웹뷰를 사용합니다.\n" +
                        "2. iOS\n" +
                        "- Swift 또는 Objective-C + 웹뷰를 사용합니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl13 =
                        "https://namu.wiki/w/%ED%95%98%EC%9D%B4%EB%B8%8C%EB%A6%AC%EB%93%9C%20%EC%95%B1"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl13))
                    startActivity(intent)
                }
            }
            else{
                button1view()
            }
        }
    }
    private fun button2view() {
        binding.spinner.setSelection(1)
        binding.button1.text = "프론트엔드"
        binding.button2.text = "UX디자인"
        binding.button3.text = "백엔드"
        binding.textView1.text = "웹이란?"
        binding.textView2.text = "웹 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "웹(Web)은 www(World Wide Web)의 약자로, 인터넷을 통해 정보와 자원을 공유하기 위한 전세계적인 컴퓨터 네트워크입니다. 우리는 서버로 요청하여 원하는 웹페이지를 열람할 수 있습니다.\n" +
                "ex) 검색 사이트, 홈페이지, 쇼핑몰 등"
        binding.addtextView2.text = "웹 개발자는 웹 페이지, 웹 사이트 등을 개발합니다. 클라이언트 측과 서버 측의 개발을 담당하며, 웹 기술과 프로그래밍 언어로 동적이고 상호작용하는 WWW 기반 소프트웨어를 구축합니다."
        binding.addtextView3.text = "HTML, CSS, JavaScript, PHP, Python"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl2 = "https://namu.wiki/w/%EC%9B%94%EB%93%9C%20%EC%99%80%EC%9D%B4%EB%93%9C%20%EC%9B%B9"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl2))
            startActivity(intent)
        }
    }
    private fun button2() {
        button2view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "프론트엔드란?"
                binding.textView2.text = "프론트엔드 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "프론트엔드(Frontend)는 웹 애플리케이션의 사용자 인터페이스(UI)를 개발하는 영역입니다. 주로 사용자에게 보여지는 화면 구성, 디자인, 레이아웃, 데이터 출력이 있습니다."
                binding.addtextView2.text =
                    "프론트엔드 개발자는 백엔드 API에서 가져온 데이터의 출력, 입력을 통한 비즈니스 로직 구성과 사용자와 대화하는 사용자 인터페이스 부분을 작업합니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "1. 웹 표준과 접근성\n" +
                        ": 웹 표준을 준수하고 웹 접근성을 고려하는 능력이 필요합니다. 웹 표준을 준수하여 다양한 브라우저와 기기에서 웹 페이지가 일관되게 작동하도록 해야 하며, 웹 접근성 지침을 따라 사용자가 웹 페이지에 쉽게 접근하고 이용할 수 있도록 해야 합니다.\n" +
                        "2. 디자인 도구 \n" +
                        ": Photoshop, Figma 등의 디자인 도구를 사용하여 디자인 파일을 이해하고 웹 페이지에 반영할 수 있어야 합니다.\n" +
                        "3. 웹 성능 최적화 \n" +
                        ": 웹 페이지의 로딩 속도와 성능 최적화에 대한 이해가 필요합니다. 파일 최적화, 캐싱, 압축, 비동기 로딩 등의 기술을 사용하여 웹 페이지의 성능을 향상시킬 수 있어야 합니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "필요한 프론트엔드 프레임워크/라이브러리에는 React, Angular, Vue.js 등이 있습니다. 이러한 도구들을 이용하여 웹 애플리케이션을 구축하고 관리할 수 있어야 합니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "HTML, CSS, JavaScript"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl21 =
                        "https://namu.wiki/w/%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%94%EB%93%9C"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl21))
                    startActivity(intent)
                }
            }
            else{
                button2view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "UX란?"
                binding.textView2.text = "UX 디자이너란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "UX(User Experience)는 사용자 경험으로, 사용자가 웹 사이트 또는 애플리케이션을 사용할 때의 경험과 인터페이스를 개선하는데 초점을 맞추고 있습니다. UX는 사용자의 목표 달성, 효율성, 만족도, 사용 편의성 등과 관련이 있습니다. 즉, 사용자가 원하는 것을 파악해 사용자가 원하는 서비스를 만듭니다."
                binding.addtextView2.text =
                    "UX 디자이너는 사용자 경험과 인터페이스를 개선하기 위해 UX 디자인을 개발합니다. 디자이너의 영역만으로 생각할 수 있지만, 실제로 UX는 서비스를 개발하는 모든 직군에 적용됩니다. 따라서 다른 직무와 결합되는 경우도 있습니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 사용자 요구사항 분석\n" +
                        ": 사용자의 니즈와 목표를 파악하고, 사용자 조사, 피드백 수집 등을 통해 요구사항을 분석합니다.\n" +
                        "- 정보 구조 및 내비게이션 설계\n" +
                        ": 웹 페이지나 애플리케이션의 정보 구조를 계획하고, 사용자가 쉽게 탐색할 수 있는 내비게이션을 설계합니다.\n" +
                        "- 상호작용 디자인\n" +
                        ": 사용자와의 상호작용을 위한 인터페이스 요소, 애니메이션, 트랜지션 등을 설계하여 사용자 경험을 향상시킵니다.\n" +
                        "- 웹 퍼포먼스 최적화\n" +
                        ": 웹 페이지의 로딩 속도와 성능을 최적화하여 사용자 경험을 향상시킵니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "1. 사용자 조사 및 분석:\n" +
                        "    - 사용자 조사 기법과 도구: 인터뷰, 설문조사, 페르소나 생성, 사용자 시나리오 작성 등 사용자 행동을 이해하기 위한 다양한 조사 기법과 도구를 활용합니다.\n" +
                        "    - 사용자 행동 분석 도구: 웹 분석 도구(예: Google Analytics)나 사용자 행동 분석 도구(예: Hotjar, Mixpanel)를 사용하여 사용자의 행동 패턴과 인사이트를 분석합니다.\n" +
                        "2. 프로토타이핑 및 디자인 도구:\n" +
                        "    - 프로토타이핑 도구: 프로토타이핑 도구(예: Adobe XD, Figma, InVision)를 사용하여 아이디어를 시각화하고 사용자 인터랙션을 모방하는 프로토타입을 제작합니다.\n" +
                        "    - 와이어프레임 도구: 사용자 인터페이스의 기본 구조와 레이아웃을 나타내는 와이어프레임 도구(예: Balsamiq, Axure)를 사용합니다.\n" +
                        "\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "HTML, CSS\n"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl22 =
                        "https://namu.wiki/w/%EC%82%AC%EC%9A%A9%EC%9E%90%20%EA%B2%BD%ED%97%98"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl22))
                    startActivity(intent)
                }
            }
            else{
                button2view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "백엔드란?"
                binding.textView2.text = "백엔드 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "백엔드(Backend)는 웹 또는 소프트웨어의 서버 측 구성 요소를 말합니다. 백엔드는 프론트엔드와 달리 사용자에게 직접적으로 보이지 않지만, 기능과 데이터 관리를 담당하여 전체적인 시스템의 동작을 지원합니다."
                binding.addtextView2.text =
                    "백엔드 개발자는 웹 또는 소프트웨어의 서버 측 구성 요소를 개발합니다. 데이터 처리, 비즈니스 로직 구현, 서버 관리 등의 작업을 수행하여 웹이 원활하게 동작할 수 있도록 합니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "1. 데이터베이스 관리: 백엔드 개발자는 데이터베이스를 설계하고 관리하여 애플리케이션의 데이터를 저장하고 조작합니다. 주로 사용되는 관계형 데이터베이스 관리 시스템(RDBMS)에는 MySQL, PostgreSQL, Oracle 등이 있으며, NoSQL 데이터베이스(MongoDB, Redis)도 사용될 수 있습니다.\n" +
                        "2. 서버 관리: 백엔드 개발자는 애플리케이션의 서버를 관리하고 배포해야 합니다. 이를 위해 다음과 같은 기술을 알아야 합니다:\n" +
                        "    - 리눅스/유닉스 환경: 리눅스 또는 유닉스 기반 운영 체제에 대한 기본적인 이해와 명령어 사용을 학습합니다.\n" +
                        "    - 클라우드 플랫폼: AWS, Google Cloud, Microsoft Azure와 같은 클라우드 플랫폼을 이용하여 서버를 관리하고 배포하는 방법을 알아야 합니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "1. 웹 프레임워크: 백엔드 개발자는 웹 애플리케이션을 개발하기 위해 주로 웹 프레임워크를 사용합니다. 이러한 프레임워크는 개발 생산성을 높이고 기본 기능을 제공합니다. 대표적인 웹 프레임워크로는 다음이 있습니다:\n" +
                        "- Java: Spring, JavaServer Faces (JSF), Struts 등\n" +
                        "- Python: Django, Flask, Pyramid 등\n" +
                        "- JavaScript: Express.js, Node.js, Meteor 등\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "Java, Python"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl23 = "https://namu.wiki/w/%EB%B0%B1%EC%97%94%EB%93%9C"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl23))
                    startActivity(intent)
                }
            }
            else{
                button2view()
            }
        }
    }
    private fun button3view() {
        binding.spinner.setSelection(2)
        binding.button1.text = "머신 러닝"
        binding.button2.text = "사물인터넷"
        binding.button3.text = "자연어 처리"
        binding.textView1.text = "인공지능(AI)이란?"
        binding.textView2.text = "인공지능 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "인공지능(Artificial Intelligence)은 컴퓨터 시스템이 인간의 지능적인 작업을 수행할 수 있는 능력입니다. 인공지능은 문제를 해결하고 의사 결정을 내리는 데에 있어서 인간과 유사한 능력을 갖추고 있습니다. 이를 위해 기계 학습, 패턴 인식 및 자연어 처리 등의 기술이 사용됩니다. 데이터를 분석하고 학습하여 더 나은 성능과 예측력을 갖출 수 있으며, 사람들의 생활과 업무에 널리 활용됩니다."
        binding.addtextView2.text = "인공지능 개발자는 인공지능 시스템을 설계, 개발 및 유지보수합니다. 프로그래밍, 데이터 분석 및 기계 학습 기술에 대한 깊은 이해와 숙련된 기술이 필요합니다. 또한, 문제 해결을 위한 알고리즘 개발, 데이터 모델링, 테스트 및 성능 최적화 등의 작업을 수행합니다. 실제 사용자 요구에 맞춰 사용자 경험을 개선하고 인공지능 시스템의 안정성과 보안을 유지하기 위해 노력합니다."
        binding.addtextView3.text = "Python, JAVA, C/C++, R, Lisp"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl3 = "https://namu.wiki/w/%EC%9D%B8%EA%B3%B5%EC%A7%80%EB%8A%A5"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl3))
            startActivity(intent)
        }
    }
    private fun button3() {
        button3view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "머신 러닝(ML)이란?"
                binding.textView2.text = "머신 러닝 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "머신 러닝(Machine Learning)이란 기계 학습으로 사용하는 데이터를 기반으로 학습 또는 성능 향상을 지원하는 시스템을 구축하는데 초점을 맞추는 인공지능(AI)의 하위 집합입니다.\n"
                binding.addtextView2.text = "머신 러닝 개발자는 데이터를 활용하여 현실 문제를 머신 러닝으로 해결합니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 수학 및 통계학(선형대수학, 확률론,통계학)의 이해가 필요합니다.\n" +
                        "- 알고리즘에 대한 이해와 적용 능력이 필요합니다.\n" +
                        "- 자연어 처리(NLP) 또는 컴퓨터 비전중 하나에 대한 전문 지식이 필요합니다.\n" +
                        "- 데이터 분석 및 처리에 대한 기본 지식이 필요합니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "라이브러리, 데이터 모델, 신경망 구축\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "Python, JAVA, C++, R\n"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl31 =
                        "https://namu.wiki/w/%EA%B8%B0%EA%B3%84%ED%95%99%EC%8A%B5?from=%EB%A8%B8%EC%8B%A0%EB%9F%AC%EB%8B%9D"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl31))
                    startActivity(intent)
                }
            }
            else{
                button3view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "사물인터넷(IoT)란?"
                binding.textView2.text = "사물인터넷 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "사물 인터넷(Internet of Things)은 연결된 디바이스의 공통 네트워크로 디바이스와 클라우드 및 디바이스 간 통신을 용이하게 합니다. 가전 제품이나 생산 설비, 각종 부품의 사물에 각종 센서를 부착하여 이들 사물이 서로 정보를 인터넷으로 주고 받는 기술입니다."
                binding.addtextView2.text =
                    "사물인터넷 전문가는 IoT 기술 환경을 개발 및 구축하거나 IOT 서비스를 기획하는 개발자입니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 데이터 분석 및 처리에 대한 기본 지식\n" +
                        "- 네트워크의 지식\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "라이브러리, 데이터 모델, 신경망 구축\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "JAVA, C, Python"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl32 =
                        "https://namu.wiki/w/%EC%82%AC%EB%AC%BC%EC%9D%B8%ED%84%B0%EB%84%B7"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl32))
                    startActivity(intent)
                }
            }
            else{
                button3view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "자연어 처리(NLP)란?"
                binding.textView2.text = "AI 자연어 처리 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "자연어 처리(Natural Language Processing)는 컴퓨터가 인간의 언어를 이해, 생성, 조작할 수 있도록 해주는 처리 과정입니다.\n"
                binding.addtextView2.text = "AI 자연어 처리 전문가는 AI가 자연어를 처리 할 수 있도록 환경을 구축합니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 데이터 분석 및 기계학습의 지식\n" +
                        "- 선형대수학 기초 지식\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "데이터 모델, 신경망 구축\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "Python, JAVA, C++"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl33 =
                        "https://namu.wiki/w/%EC%9E%90%EC%97%B0%20%EC%96%B8%EC%96%B4%20%EC%B2%98%EB%A6%AC"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl33))
                    startActivity(intent)
                }
            }
            else{
                button3view()
            }
        }
    }
    private fun button4view() {
        binding.spinner.setSelection(3)
        binding.button1.text = "시스템 관리"
        binding.button2.text = "네트워크"
        binding.button3.text = "Cloud 아키텍처"
        binding.textView1.text = "클라우드란?"
        binding.textView2.text = "클라우드 엔지니어란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "클라우드(Cloud)는 인터넷을 통해 컴퓨터 리소스(하드웨어, 소프트웨어, 네트워크 등)를 제공하는 서비스입니다. 즉, 자신의 컴퓨터의 자원을 사용하는 것이 아닌 다른 컴퓨터의 자원을 활용하는 것을 의미합니다. 실제 개발에서 주로 사용하는 클라우드 서비스는 AWS(Amazon Web Service)가 있습니다.\n" +
                "ex) Google Photo, iCloud 등"
        binding.addtextView2.text = "클라우드 엔지니어는 클라우드 컴퓨팅 환경을 설계, 구축, 운영 및 유지보수하는 역할을 담당합니다. 주로 고객이나 기업의 비즈니스 요구 사항에 맞춰 클라우드 솔루션을 개발하고 구현하는 업무를 수행합니다."
        binding.addtextView3.text = "Python, Java, JavaScript, Go"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl4 = "https://namu.wiki/w/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C%20%EC%BB%B4%ED%93%A8%ED%8C%85"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl4))
            startActivity(intent)
        }
    }
    private fun button4() {
        button4view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "클라우드 시스템이란?"
                binding.textView2.text = "클라우드 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "클라우드 시스템(Cloud System)은 인터넷을 통해 서버, 스토리지, 데이터베이스, 네트워크, 소프트웨어 및 다른 컴퓨팅 리소스에 대한 접근을 제공하는 컴퓨팅 모델입니다. 기존의 온프레미스(On-Premises) 시스템과 달리, 클라우드 시스템은 인터넷을 통해 이러한 리소스를 필요한 만큼 요청하고 사용할 수 있습니다."
                binding.addtextView2.text =
                    "클라우드 전문가는 클라우드 인프라 구조를 관리하고 운영하는 역할을 수행합니다. 주로 클라우드 환경에서 서버, 네트워크, 데이터베이스, 스토리지 등의 시스템을 구성하고 관리하여 안정적인 운영을 유지합니다. 클라우드 시스템 관리자는 클라우드 인프라와 시스템의 운영과 유지보수에 초점을 맞춥니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "1. 클라우드 인프라스트럭처 관리: 클라우드 서비스를 사용하여 가상 서버, 스토리지, 네트워크, 데이터베이스 등의 인프라를 프로비저닝하고 구성합니다. 이는 리소스의 할당, 네트워크 설정, 데이터베이스 관리, 보안 설정 등을 포함합니다.\n" +
                        "2. 시스템 모니터링 및 성능 최적화: 클라우드 시스템의 성능과 가용성을 모니터링하고 문제를 해결하며, 성능 최적화를 위한 조치를 취합니다. 모니터링 도구를 사용하여 리소스 사용량, 성능 지표, 로그 등을 분석하고 최적화 방안을 제시합니다.\n" +
                        "3. 보안 및 데이터 관리: 클라우드 시스템의 보안을 유지하고 데이터 관리를 수행합니다. 액세스 제어, 보안 그룹 설정, 데이터 백업 및 복구, 암호화 등의 보안 조치를 시행하며, 데이터베이스 백업, 복제, 복구 등을 관리합니다.\n" +
                        "4. 이슈 대응 및 문제 해결: 클라우드 시스템에서 발생하는 이슈와 장애에 대응하고, 문제를 신속하게 해결합니다. 시스템 장애, 성능 저하, 네트워크 문제 등을 해결하기 위해 문제 분석 및 해결 능력이 필요합니다.\n" +
                        "5. 자동화 및 스크립팅: 클라우드 시스템을 자동화하기 위해 스크립팅 언어 (예: PowerShell, Bash)를 사용하여 작업을 자동화합니다. 자동화를 통해 시스템 관리 작업의 효율성을 높일 수 있습니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "PowerShell, Bash, Python, YAML, JSON"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl4 =
                        "https://namu.wiki/w/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C%20%EC%BB%B4%ED%93%A8%ED%8C%85"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl4))
                    startActivity(intent)
                }
            }
            else{
                button4view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "클라우드 네트워크란?"
                binding.textView2.text = "클라우드 네트워크 엔지니어란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "클라우드 네트워크(Cloud Network)는 클라우드 컴퓨팅 환경에서 네트워크 리소스를 관리하고 제공하는 시스템입니다. 클라우드 네트워크는 다양한 사용자 및 서비스 간의 통신을 지원하고 클라우드 리소스에 대한 접근을 가능하게 합니다."
                binding.addtextView2.text =
                    "클라우드 네트워크 엔지니어는 클라우드 환경에서 네트워크 인프라를 설계, 구축 및 관리하는 역할을 수행합니다. 클라우드 기반 인프라에서 네트워크 연결성, 보안, 성능 및 가용성을 유지하고 향상시키는데 중점을 둡니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "1. 클라우드 네트워크 아키텍처 설계: 클라우드 네트워크 엔지니어는 클라우드 인프라의 네트워크 아키텍처를 설계합니다. 이는 가상 네트워크, 서브넷, 라우팅, 보안 그룹, 네트워크 게이트웨이 등을 포함합니다. 이를 통해 클라우드 리소스 간의 연결성과 보안을 구성하고, 효율적인 데이터 흐름을 지원합니다.\n" +
                        "2. 네트워크 보안 구성: 클라우드 네트워크 엔지니어는 클라우드 환경에서의 네트워크 보안을 구성합니다. 방화벽, 네트워크 접근 제어 리스트(NACL), 보안 그룹 등을 설정하여 인바운드 및 아웃바운드 트래픽을 관리하고, 네트워크의 기밀성, 무결성, 가용성을 유지합니다.\n" +
                        "3. 가상 네트워크 관리: 클라우드 네트워크 엔지니어는 가상 네트워크를 관리합니다. 가상 네트워크를 프로비저닝하고, 서브넷을 구성하며, IP 주소 할당과 관리를 수행합니다. 이를 통해 가상 머신, 컨테이너 및 기타 클라우드 리소스의 네트워크 연결성을 제공합니다.\n" +
                        "4. 네트워크 성능 최적화: 클라우드 네트워크 엔지니어는 클라우드 네트워크의 성능을 최적화하기 위해 작업합니다. 로드 밸런싱, 가용성 영역 복제, 지연 시간 최적화, 대역폭 관리 등을 수행하여 안정적이고 빠른 네트워크 연결을 제공합니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "PowerShell, Bash, Python, YAML, JSON"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl42 =
                        "https://namu.wiki/w/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C%20%EC%84%9C%EB%B9%84%EC%8A%A4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl42))
                    startActivity(intent)
                }
            }
            else{
                button4view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "클라우드 아키텍처란?"
                binding.textView2.text = "클라우드 아키텍트란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "클라우드 아키텍처(Cloud Architecture)는 클라우드 환경에서 컴퓨팅 리소스를 구성하고 조직화하는 방법과 구조를 의미합니다. 이는 클라우드 서비스 제공 업체가 클라우드 인프라를 설계, 구축 및 관리하는 데 사용되는 체계적인 접근 방식입니다."
                binding.addtextView2.text =
                    "클라우드 아키텍트는 클라우드 기반 시스템을 설계하고 구축합니다. 주로 기업이나 조직의 비즈니스 요구 사항을 이해하고, 클라우드 아키텍처를 계획하고 구현하여 비즈니스 목표를 달성하는 데 도움을 주는 역할을 수행합니다. 클라우드 아키텍트는 클라우드 시스템의 설계와 구현에 초점을 맞춥니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "1. 클라우드 플랫폼 지식: 주요 클라우드 플랫폼(AWS, Azure, Google Cloud 등)에 대한 이해와 경험이 필요합니다. 클라우드 서비스의 특징, 기능, 관리 및 보안 기능을 이해해야 합니다.\n" +
                        "2. 클라우드 아키텍처 패턴: 클라우드 아키텍처 패턴을 이해하고 적용할 수 있어야 합니다. 예를 들어, 가용성을 높이기 위한 로드 밸런싱, 스케일링 전략, 데이터 복제 및 백업, 멀티 리전 및 멀티 AZ 아키텍처 등을 이해해야 합니다.\n" +
                        "3. 인프라스트럭처 지식: 가상화 기술, 네트워킹, 서버 및 스토리지 관리 등과 같은 인프라스트럭처 지식이 필요합니다. 가상 서버(가상 머신), 컨테이너, 스토리지 및 데이터베이스 관리 등에 대한 이해가 필요합니다.\n" +
                        "4. 보안 및 컴플라이언스: 클라우드 환경에서의 보안과 컴플라이언스 요구 사항을 이해하고 구현할 수 있어야 합니다. 데이터 보호, 액세스 제어, 네트워크 보안, 보안 감사 및 규정 준수를 위한 조치 등을 알고 있어야 합니다.\n" +
                        "5. 자동화 및 오케스트레이션: 클라우드 자원을 자동화하고 오케스트레이션할 수 있는 기술과 도구에 대한 이해가 필요합니다. 인프라 자동화, 배포 자동화, 구성 관리, 인프라스트럭처 코드 등을 다룰 수 있어야 합니다. 이를 위해 툴과 스크립트 언어 (예: Terraform, Ansible, PowerShell)를 사용합니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "스크립팅 언어와 프로그래밍 언어의 기본 원리에 대한 이해\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "Python, Java, JavaScript, Go"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl4 =
                        "https://namu.wiki/w/%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C%20%EC%BB%B4%ED%93%A8%ED%8C%85"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl4))
                    startActivity(intent)
                }
            }
            else{
                button4view()
            }
        }
    }
    private fun button5view(){
        binding.spinner.setSelection(4)
        binding.button1.text = "서버"
        binding.button2.text = "클라이언트"
        binding.button3.visibility = View.GONE
        binding.textView1.text = "게임이란?"
        binding.textView2.text = "게임 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "게임(Game)은 다양한 플랫폼에서 다양한 형태로 사용자에게 즐거움을 선사합니다. PC 게임은 컴퓨터를 기반으로 동작하며, 다양한 장르와 그래픽 품질로 인해 광범위한 게임 경험을 제공합니다. 모바일 게임은 스마트폰이나 태블릿과 같은 모바일 기기에서 플레이할 수 있으며, 간편하고 이동 중에도 즐길 수 있는 특징을 갖고 있습니다. 콘솔 게임은 특정 게임 기기(예: PlayStation, Xbox)에서 실행되는 게임으로, 강력한 하드웨어와 독특한 컨트롤러로 인해 몰입도 높은 게임 플레이를 제공합니다.\n" +
                "ex) PC게임, 온라인 게임, 모바일 게임, 콘솔 게임 등"
        binding.addtextView2.text = "게임 개발자는 창의적인 아이디어와 기술적인 능력을 바탕으로 게임을 개발합니다. 게임의 기획, 디자인, 프로그래밍, 그래픽 및 음향 제작 등 다양한 역할을 수행합니다. 게임 개발자들은 사용자 경험을 최적화하고, 게임의 균형과 재미를 조절하여 흥미로운 게임 환경을 구축합니다. 또한, 팀원들과 협력하여 프로젝트를 관리하고, 문제를 해결하며, 시간과 예산을 효율적으로 관리하는 역할도 맡습니다. 최신 기술과 트렌드를 학습하며 게임 엔진과 도구를 사용하여 게임을 개발하고 테스트하는 것도 게임 개발자의 중요한 업무입니다. 새로운 아이디어와 혁신적인 기술을 통해 플레이어들에게 즐거움과 도전을 선사하는 역할을 수행합니다."
        binding.addtextView3.text = "언리얼 엔진(C++, blueprint): 성능 최적화가 요구되는 대규모 고사양의 게임\n" +
                "유니티 엔진(C#): 캐주얼 또는 모바일 게임\n" +
                "python, Lua: 게임 플레이 로직의 빠른 변경이 필요한 경우"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl5 = "https://namu.wiki/w/PC%20%EA%B2%8C%EC%9D%B4%EB%B0%8D"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl5))
            startActivity(intent)
        }
    }
    private fun button5() {
        button5view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "게임 서버란?"
                binding.textView2.text = "게임 서버 프로그래머란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "게임 서버(Game Server)는 게임 클라이언트들이 접속하여 게임을 진행하는 공간입니다. 다수의 클라이언트들이 함께 게임을 즐길 수 있도록 게임의 로직을 실행하고, 클라이언트와의 데이터 통신을 처리합니다. 보안, 안정성, 확장성 등을 고려하여 설계되어야 합니다."
                binding.addtextView2.text =
                    "게임 서버 프로그래머는 게임 서버의 성능과 안정성을 담당하므로, 게임 서버의 장애나 성능 이슈에 대응할 수 있어야 합니다. 이를 위해서는 다양한 기술과 전문 지식이 필요하며, 지속적인 학습과 경험이 필요합니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 네트워크: TCP/IP, UDP 등의 프로토콜과 네트워크 통신 방식 이해, 서버와 클라이언트 간의 효율적인 데이터 교환 구현 \n" +
                        "- 데이터베이스 관리: 플레이어 정보, 게임 진행 상태, 경쟁 순위 등 저장하고 관리\n" +
                        "- 스케일링과 성능 최적화에 대한 지식 : 많은 플레이어들을 동시 처리, 성능 저하 없이 확장 가능한 아키텍처 필요\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "1. 네트워크 프로그래밍: 클라이언트와 서버 간의 데이터 교환 및 신뢰성 있는 통신을 구현할 수 있어야 합니다.\n" +
                        "2. 데이터베이스 관리: 데이터베이스 시스템을 다루는 경험이 필요하며, 데이터의 안정성과 일관성을 보장할 수 있어야 합니다.\n" +
                        "3. 다중 스레딩 및 병렬 처리: 게임 서버는 동시에 많은 플레이어 요청을 처리해야 합니다. 다중 스레딩과 병렬 처리 기술을 활용하여 효율적으로 작업을 분산하고, 성능을 최적화할 수 있어야 합니다.\n" +
                        "4. 보안 및 암호화: 게임 서버는 플레이어들의 개인 정보와 게임 데이터를 안전하게 보호해야 합니다. 보안 기술 및 암호화 알고리즘에 대한 이해와 적용 능력이 필요합니다.\n" +
                        "5. 스케일링 및 성능 최적화: 게임 서버는 플레이어 수가 증가할 때도 안정적으로 동작해야 합니다. 확장 가능한 아키텍처를 설계하고, 성능 모니터링 및 최적화 기술을 활용하여 서버의 확장성과 성능을 유지해야 합니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "1. C++: C++은 게임 서버 개발에 널리 사용되는 언어입니다. 이는 높은 성능과 저수준 제어가 가능하며, 메모리 관리에 대한 직접적인 제어가 가능하기 때문입니다. 게임 서버의 핵심 로직이나 네트워크 통신, 데이터베이스 연동 등에 C++을 활용하여 빠르고 효율적인 서버를 개발할 수 있습니다.\n" +
                        "2. Python: Python은 게임 서버 개발에서 접근성과 생산성을 높여줄 수 있는 언어입니다. Python은 간결하고 읽기 쉬운 문법을 가지며, 빠른 프로토타이핑과 개발 속도를 지원합니다. 게임 서버의 간단한 스크립팅, 게임 로직 개발, 데이터 처리 등 다양한 부분에서 Python을 활용할 수 있습니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl51 = "https://namu.wiki/w/%EA%B2%8C%EC%9E%84%20%EC%84%9C%EB%B2%84"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl51))
                    startActivity(intent)
                }
            }
            else{
                button5view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "게임 클라이언트란?"
                binding.textView2.text = "게임 클라이언트 프로그래머란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "게임 클라이언트(Game Client)는 게임을 실행하는 플레이어가 설치하여 사용하는 프로그램입니다. 게임 클라이언트는 게임 서버에서 제공되는 게임 데이터와 로직을 받아와 플레이어에게 보여주고, 플레이어의 입력을 게임 서버로 전송합니다. 이러한 기능을 수행하기 위해서 게임 클라이언트는 여러가지 기술과 구성 요소를 포함하고 있습니다."
                binding.addtextView2.text =
                    "게임 클라이언트 프로그래머는 게임의 사용자 인터페이스(UI), 그래픽 렌더링, 애니메이션, 오디오, 사용자 입력 등을 구현합니다. 클라이언트 프로그래머는 다양한 플랫폼(PC, 모바일, 콘솔)에 맞게 클라이언트 애플리케이션을 개발하고, 게임 서버와의 통신을 담당합니다. 또한, 성능 최적화와 사용자 경험 향상을 위해 그래픽 엔진과 도구를 활용하여 게임 클라이언트를 개선합니다."
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "C++,C#,Java와 같은 프로그래밍 언어에 대한 응용\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "그래픽 API (예: DirectX, OpenGL)와 3D 그래픽 엔진 (예: Unity, Unreal Engine)을 활용한 그래픽 및 렌더링에 대한 이해, 플레이어가 느끼는 사용자 경험 및 UI 디자인에 대한 이해\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "1. C++:\n" +
                        "C++은 게임 클라이언트 개발에서 가장 많이 사용되는 언어 중 하나입니다. C++은 메모리 및 프로세서 사용을 효율적으로 제어할 수 있으며, 빠른 속도와 뛰어난 성능을 제공합니다. C++을 사용하여 게임 엔진을 만들고, 그래픽 및 오디오 드라이버와 같은 하드웨어 인터페이스를 제어할 수 있습니다.\n" +
                        "2. C#:\n" +
                        "C#은 Unity와 같은 게임 엔진에서 많이 사용되는 언어입니다. C#은 C++보다 상대적으로 쉽고 간단한 구문을 가지고 있으며, 개발 시간을 단축할 수 있습니다. 또한 .NET 프레임워크를 기반으로 하므로 윈도우즈 플랫폼에서 높은 호환성을 제공합니다.\n"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl52 =
                        "https://namu.wiki/w/%ED%81%B4%EB%9D%BC%EC%9D%B4%EC%96%B8%ED%8A%B8"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl52))
                    startActivity(intent)
                }
            }
            else{
                button5view()
            }
        }
    }
    private fun button6view(){
        binding.spinner.setSelection(5)
        binding.button1.text = "화이트 해킹"
        binding.button2.text = "악성코드 분석"
        binding.button3.text = "디지털 포렌식"
        binding.textView1.text = "보안이란?"
        binding.textView2.text = "보안 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "보안(Security)은 시스템, 네트워크 또는 정보를 외부로부터 불법적인 침입이나 피해로부터 보호하는 조치입니다. 기밀성, 무결성, 가용성을 보장하여 데이터의 비밀성 유지, 변조 방지 및 서비스 제공을 보장합니다. 이를 위해 암호화, 인증, 접근 제어 등의 기술과 정책이 사용됩니다. 또한, 보안은 개인 정보 보호, 시스템 및 네트워크의 취약점 탐지 및 대응 등을 포함하여 종합적인 접근이 필요합니다."
        binding.addtextView2.text = "보안 개발자는 시스템 및 소프트웨어의 보안 측면을 설계, 개발 및 유지보수합니다. 이들은 해킹, 악성 코드 및 다른 보안 위협으로부터 시스템을 보호하기 위해 보안 기술과 암호화 알고리즘을 이해하고 활용합니다. 취약점 분석, 보안 테스트 및 패치 관리와 같은 작업을 수행하여 시스템의 취약성을 최소화하고 새로운 보안 도구와 기술에 대한 연구 및 개발을 수행하여 신속하고 효과적인 보안 솔루션을 제공합니다. 정보 보안 정책 및 규정을 준수하며, 사용자 데이터의 기밀성과 무결성을 보장하는 역할을 담당합니다."
        binding.addtextView3.text = "C, 어셈블리어"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl6 = "https://namu.wiki/w/%EC%A0%95%EB%B3%B4%EB%B3%B4%ED%98%B8"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl6))
            startActivity(intent)
        }
    }
    private fun button6() {
        button6view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "해킹이란?"
                binding.textView2.text = "화이트 해커란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text = "해킹(Hacking)은 타인의 시스템에 불법적으로 칩입하는 것을 의미합니다.\n"
                binding.addtextView2.text =
                    "화이트 해커는 컴퓨터 내의 시스템이나 프로그래밍에 관해 전문적인 지식을 가진 사람으로 해킹을 방어 목적, 보안 목적으로 하는 해커입니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "- 이산 수학, 자료구조, 알고리즘\n" +
                        "- 운영체제\n" +
                        "- 네트워크 해킹 또는 시스템 해킹\n" +
                        "- 자격증 : 정보보안기사\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "네트워크 흐름 파악\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "Python, Ruby, C, Bash\n"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl61 = "https://namu.wiki/w/%ED%95%B4%EC%BB%A4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl61))
                    startActivity(intent)
                }
            }
            else{
                button6view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "악성 코드란?"
                binding.textView2.text = "악성 코드 분석가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text = "악성 코드(Malware)는 사용자에게 “악의적” 목적으로 만들어진 모든 코드를 말합니다.\n" +
                        "ex) 바이러스, 웜, 트로이 목마 등"
                binding.addtextView2.text = "악성 코드 분석가는 악성 코드를 분석하여 백신을 개발합니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "서버, 네트워크, 정보 보안, 데이터 베이스\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "- 악성 코드 분석\n" +
                        "- API동작 방식, 다양한 옵션 사용의 성능 영향, 효율적인 코딩 방법\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "C, Assembly, Python"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl62 = "https://namu.wiki/w/%EC%95%85%EC%84%B1%EC%BD%94%EB%93%9C"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl62))
                    startActivity(intent)
                }
            }
            else{
                button6view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "디지털 포렌식이란?"
                binding.textView2.text = "디지털 포렌식 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "디지털 포렌식(Digital Forensics)은 범죄에 대한 증거를 찾기 위해 진행하는 과학적인 증거 수집과 수사를 말합니다.\n"
                binding.addtextView2.text =
                    "디지털 포렌식 전문가는 범죄수사의 단서인 디지털 자료를 확보하고 이를 법적인 증거 자료로 생성하는 일을 전문적으로 진행합니다.\n"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "컴퓨터의 전반적인 지식\n" +
                        "ex) 컴퓨터 시스템, 하드웨어, 운영체제, 정보보안 등\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "라이브러리\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "C, Java"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl63 =
                        "https://namu.wiki/w/%EB%94%94%EC%A7%80%ED%84%B8%20%ED%8F%AC%EB%A0%8C%EC%8B%9D"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl63))
                    startActivity(intent)
                }
            }
            else{
                button6view()
            }
        }
    }
    private fun button7view(){
        binding.spinner.setSelection(6)
        binding.button1.text = "db 관리"
        binding.button2.text = "db 보안"
        binding.button3.text = "db 아키텍처"
        binding.textView1.text = "데이터베이스란?"
        binding.textView2.text = "데이터베이스 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "데이터베이스(Database)는 체계화된 데이터의 모임으로, 여러 사용자가 공유하여 사용할 수 있도록 구성한 데이터의 집합을 의미합니다. 데이터베이스는 관계형 데이터베이스(RDBMS)와 비관계형 데이터베이스(NoSQL) 등의 종류가 있으며, 데이터베이스 시스템은 대부분 SQL(Structured Query Language)을 사용하여 데이터를 검색, 추가, 수정, 삭제할 수 있도록 지원합니다."
        binding.addtextView2.text = "데이터베이스 개발자는 데이터베이스 시스템을 설계, 구축, 유지보수하는 전문가입니다. 데이터 모델링과 스키마 설계를 수행하여 데이터 구조를 최적화하고 효율적인 데이터 액세스를 보장합니다. SQL과 NoSQL 등의 데이터베이스 query 언어를 다루며, 데이터베이스 성능 튜닝과 인덱스 관리를 통해 최적화를 달성합니다. 보안, 백업 및 복구, 데이터 일관성 유지 등 데이터베이스의 안정성과 신뢰성을 유지하는 역할을 담당합니다.\n"+
                "1. MySQL: MySQL은 가장 널리 사용되는 오픈 소스 관계형 데이터베이스 관리 시스템(RDBMS) 중 하나입니다. 표준 SQL 문법을 따르며, 데이터베이스의 구축, 관리, 조작을 위해 사용됩니다.\n" +
                "2. Oracle SQL: Oracle SQL은 Oracle Database에서 사용되는 SQL 언어입니다. Oracle은 대규모 엔터프라이즈급 데이터베이스 시스템으로 널리 알려져 있으며, Oracle SQL은 이 시스템과의 상호작용을 위해 사용됩니다.\n" +
                "3. MongoDB : MongoDB는 오픈 소스 NoSQL 데이터베이스 시스템으로, 비관계형 데이터를 저장하고 관리하는 데 사용됩니다. MongoDB는 도큐먼트 지향 데이터 모델을 사용하며, JSON과 비슷한 BSON(Binary JSON) 형식으로 데이터를 저장합니다."
        binding.addtextView3.text = "1. MySQL: MySQL은 가장 널리 사용되는 오픈 소스 관계형 데이터베이스 관리 시스템(RDBMS) 중 하나입니다. 표준 SQL 문법을 따르며, 데이터베이스의 구축, 관리, 조작을 위해 사용됩니다.\n" +
                "2. Oracle SQL: Oracle SQL은 Oracle Database에서 사용되는 SQL 언어입니다. Oracle은 대규모 엔터프라이즈급 데이터베이스 시스템으로 널리 알려져 있으며, Oracle SQL은 이 시스템과의 상호작용을 위해 사용됩니다.\n" +
                "3. MongoDB : MongoDB는 오픈 소스 NoSQL 데이터베이스 시스템으로, 비관계형 데이터를 저장하고 관리하는 데 사용됩니다. MongoDB는 도큐먼트 지향 데이터 모델을 사용하며, JSON과 비슷한 BSON(Binary JSON) 형식으로 데이터를 저장합니다."
        binding.bottomButton2.setOnClickListener {
            val wikiUrl7 = "https://namu.wiki/w/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl7))
            startActivity(intent)
        }
    }
    private fun button7() {
        button7view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "데이터베이스 관리란?"
                binding.textView2.text = "데이터베이스 관리자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "데이터베이스 관리(Database Management)는 데이터베이스 시스템을 설계, 구축, 운영 및 유지보수하는 과정을 의미합니다. 데이터베이스는 조직이나 기업에서 사용되는 중요한 데이터를 저장, 관리 및 액세스하기 위한 구조화된 방법을 제공합니다. 데이터베이스 관리는 이러한 데이터베이스 시스템을 효율적으로 운영하고 관리하는 프로세스와 도구를 포함합니다."
                binding.addtextView2.text =
                    "데이터베이스 관리자는 조직이 사용하는 데이터베이스 시스템을 설계, 구축, 유지보수하는 역할을 수행합니다. 그들은 데이터의 효율적인 관리와 보안을 보장하고, 데이터베이스의 가용성과 성능을 최적화하는 것을 목표로 합니다."
                binding.addtextView3.text = "■ 사용 언어\n" +
                        "- SQL(Structured Query Language): 데이터베이스 관리와 관련된 작업을 수행하기 위한 표준 언어로, 데이터의 검색, 수정, 삭제, 추가 등을 수행할 수 있습니다.\n" +
                        "- 스크립트 언어인 Python, PowerShell, Bash: 자동화된 작업을 수행합니다.\n" +
                        "- Perl, Ruby 등 다양한 언어를 사용할 수 있습니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl71 = "https://namu.wiki/w/DBMS"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl71))
                    startActivity(intent)
                }
            }
            else{
                button7view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "데이터베이스 보안이란?"
                binding.textView2.text = "데이터베이스 보안 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "데이터베이스 보안(Database Security)은 데이터베이스 시스템과 저장된 데이터를 보호하기 위해 적용되는 기술, 정책, 절차 등의 전반적인 보안 대책을 의미합니다. 데이터베이스 보안은 데이터의 기밀성, 무결성, 가용성을 유지하고 데이터베이스 시스템에 대한 불법적인 액세스, 변조, 파괴 등을 방지하기 위해 중요한 역할을 합니다."
                binding.addtextView2.text =
                    "데이터 보안 전문가는 데이터베이스 시스템과 관련된 보안 측면을 담당하는 전문가입니다. 데이터베이스의 기밀성, 무결성 및 가용성을 유지하기 위해 다양한 보안 정책과 절차를 수립하고 구현합니다. 이를 통해 데이터베이스 시스템이 내부 및 외부 위협으로부터 안전하게 보호될 수 있습니다.\n" +
                            "\n" +
                            "■ 하는 일\n" +
                            "1. 데이터 암호화: 데이터베이스에 저장되는 중요한 정보를 보호하기 위해 암호화 기술을 사용합니다. 데이터베이스 전송 및 저장 시 암호화 알고리즘과 키 관리를 구현하여 데이터의 안전성을 보장합니다.\n" +
                            "2. 액세스 제어: 데이터베이스에 접근하는 사용자의 신원을 확인하고, 권한을 관리하여 불법적인 데이터 액세스를 방지합니다. 사용자 인증 및 권한 부여 메커니즘을 구현하고, 접근 제어 정책을 관리합니다.\n" +
                            "3. 취약점 관리: 데이터베이스 시스템의 보안 취약점을 모니터링하고 조치합니다. 보안 패치와 업데이트를 적용하고, 취약점 스캐닝 도구를 사용하여 시스템의 취약점을 탐지하고 해결합니다.\n" +
                            "4. 감사 및 모니터링: 데이터베이스 시스템에서 발생하는 로그와 이벤트를 모니터링하고 감사합니다. 이를 통해 불법적인 활동, 데이터 유출 및 기타 보안 위협을 탐지하고 조치할 수 있습니다.\n" +
                            "5. 보안 정책 및 절차 개발: 데이터베이스 보안 정책과 절차를 개발하고 문서화하여 조직 내에서 보안을 일관되게 유지할 수 있도록 합니다. 데이터 기밀성 및 무결성을 유지하기 위한 데이터 보안 표준을 수립하고 교육을 제공합니다.\n" +
                            "6. 위협 대응: 데이터베이스 시스템에 대한 내부 및 외부 위협에 대응하기 위해 사고 대응 계획을 개발하고, 사고 발생 시 조사 및 대응을 수행합니다. 데이터 유출, 해킹 및 기타 보안 사고에 대비하여 예방 및 대응을 계획합니다."
                binding.addtextView3.text = "■ 사용 언어\n" +
                        "1. SQL (Structured Query Language): 데이터베이스 관리 시스템과 상호 작용하기 위해 SQL을 사용합니다. SQL은 데이터베이스에서 데이터를 쿼리, 조작, 보안 설정 등 다양한 작업을 수행하는 데에 널리 사용되는 언어입니다.\n" +
                        "2. Python: Python은 데이터베이스 접근, 데이터 처리, 암호화, 로깅 및 감사 등 다양한 작업을 수행하는 데에 유용합니다. 또한 Python에는 데이터베이스 관련 라이브러리와 프레임워크가 풍부하게 제공되어 있습니다.\n" +
                        "3. Shell 스크립팅 언어 (예: Bash): 데이터베이스 보안 전문가는 데이터베이스 서버를 관리하고 모니터링하는 데에 Shell 스크립팅 언어를 사용할 수 있습니다. Bash 스크립트를 작성하여 데이터베이스 관련 작업을 자동화하고 일관된 작업을 수행할 수 있습니다.\n" +
                        "4. 데이터베이스 관리 도구: 데이터베이스 보안 전문가는 데이터베이스 관리 도구를 활용하여 보안 작업을 수행할 수 있습니다. 이러한 도구는 데이터베이스 쿼리, 보안 설정, 로깅 및 감사 기능 등을 제공하며, 데이터베이스 관리 및 보안 작업을 용이하게 합니다. 예를 들어, Oracle 데이터베이스의 경우 SQL*Plus, SQL Developer 등의 도구를 사용할 수 있습니다.\n"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl72 =
                        "https://namu.wiki/w/%EB%B3%B4%EC%95%88%20%EA%B3%84%EC%A0%95%20%EA%B4%80%EB%A6%AC%EC%9E%90"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl72))
                    startActivity(intent)
                }
            }
            else{
                button7view()
            }
        }
        binding.button3.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button3.isChecked) {
                binding.button1.isChecked = false
                binding.button2.isChecked = false
                binding.textView1.text = "데이터 아키텍처란?"
                binding.textView2.text = "데이터 아키텍트란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "데이터 아키텍처(Data Architecture)는 조직이나 기업에서 사용되는 데이터를 관리하고 조직의 목표에 맞게 구성하기 위한 구조와 방법을 의미합니다. 데이터 아키텍처는 데이터의 구조, 저장소, 통합, 흐름, 관리, 보안 등을 설계하고 구현하는 프로세스를 다룹니다."
                binding.addtextView2.text =
                    "데이터 아키텍트는 조직의 데이터 아키텍처를 기획, 설계하고 구축하는 역할을 담당합니다. 데이터 아키텍트는 비즈니스 요구사항을 이해하고, 데이터베이스 시스템의 구조, 구성 요소, 데이터 흐름 및 통합을 설계하여 데이터 관리와 활용을 최적화합니다. 이를 통해 조직의 데이터가 효과적으로 활용되고 가치를 창출할 수 있도록 지원합니다.\n" +
                            "\n" +
                            "■ 하는 일\n" +
                            "1. 데이터 모델링: 비즈니스 요구사항을 바탕으로 데이터 모델을 설계합니다. 이는 개념적, 논리적, 물리적 수준의 데이터 모델링을 포함할 수 있으며, 데이터 요구사항과 관련된 엔터티, 속성, 관계 등을 정의합니다.\n" +
                            "2. 데이터 통합: 조직 내 다양한 데이터 소스로부터 데이터를 추출하고, 변환하여 데이터 통합을 수행합니다. 이는 데이터 웨어하우스, 데이터 마트, 데이터 허브 등 다양한 데이터 통합 아키텍처를 설계하고 구현하는 것을 포함합니다.\n" +
                            "3. 데이터 품질 관리: 데이터 품질을 평가하고 향상시키기 위한 정책, 절차 및 메커니즘을 개발합니다. 데이터 아키텍트는 데이터 품질 관리 요구사항을 파악하고, 데이터 품질 검증, 오류 처리, 일관성 유지 등을 위한 기준을 수립합니다.\n" +
                            "4. 데이터 보안 및 개인정보 보호: 데이터 아키텍트는 데이터 보안 및 개인정보 보호를 고려하여 데이터 아키텍처를 설계합니다. 데이터 암호화, 액세스 제어, 데이터 마스킹 등의 보안 메커니즘을 구현하고 데이터 보안 정책을 수립합니다.\n" +
                            "5. 데이터 마이그레이션: 기존 시스템으로부터 데이터를 추출하여 새로운 데이터베이스 시스템으로 이전하는 데이터 마이그레이션 전략을 개발하고 실행합니다. 데이터의 일관성, 정합성 및 효율성을 유지하며, 마이그레이션 프로세스를 관리합니다.\n" +
                            "6. 데이터 아키텍처 관리: 데이터 아키텍처 관련 문서화, 표준 및 가이드라인 개발, 아키텍처"
                binding.addtextView3.text = "■ 사용 언어\n" +
                        "1. SQL (Structured Query Language): 데이터베이스 관리 시스템과 상호 작용하기 위해 SQL을 사용합니다. SQL은 데이터베이스에서 데이터를 쿼리, 조작, 관리하는 데에 널리 사용되는 언어입니다. 데이터 아키텍트는 SQL을 사용하여 데이터 모델링, 데이터 통합, 데이터 품질 관리, 데이터 마이그레이션 등을 수행합니다.\n" +
                        "2. Python: Python은 데이터 아키텍트가 사용하는 주요 프로그래밍 언어 중 하나입니다. Python은 데이터 처리, 데이터 분석, 데이터 모델링 등 다양한 작업에 유용하며, 데이터베이스 시스템과의 상호 작용을 지원하는 다양한 라이브러리와 프레임워크가 있습니다.\n" +
                        "3. ETL 도구: Extract, Transform, Load (ETL)은 데이터 통합을 위한 과정으로, ETL 도구는 데이터 아키텍트가 사용하는 중요한 도구입니다. ETL 도구에는 Talend, Informatica, SSIS 등이 있으며, 이러한 도구는 데이터 추출, 변환, 적재 작업을 자동화하고 데이터 통합 아키텍처를 구현하는 데에 사용됩니다.\n" +
                        "4. UML (Unified Modeling Language): UML은 시스템 및 소프트웨어 모델링을 위한 표준 언어입니다. 데이터 아키텍트는 UML을 사용하여 데이터 모델링 및 아키텍처 설계를 문서화하고 시각화하는 데에 활용할 수 있습니다.\n" +
                        "5. Shell 스크립팅 언어: 데이터 아키텍트는 데이터 통합, 데이터 마이그레이션, 작업 자동화 등을 위해 Shell 스크립팅 언어를 사용할 수 있습니다. Bash 스크립트를 사용하여 데이터 아키텍처와 관련된 작업을 자동화하고 일관성 있게 수행할 수 있습니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl7 =
                        "https://namu.wiki/w/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl7))
                    startActivity(intent)
                }
            }
            else{
                button7view()
            }
        }
    }
    private fun button8view() {
        binding.spinner.setSelection(7)
        binding.button1.text = "SI"
        binding.button2.text = "SM"
        binding.button3.visibility = View.GONE
        binding.textView1.text = "전산 시스템이란?"
        binding.textView2.text = "전산 시스템 개발자란?"
        binding.textView3.text = "사용 언어"
        binding.addtextView1.text = "전산 시스템(Computer System)은 업무를 전산적으로 처리하는데 사용되는 어플리케이션 프로그램과 상용화된 소프트웨어 및 이들의 운영에 사용되는 하드웨어 등을 말합니다."
        binding.addtextView2.text = "전산 시스템 개발자는 컴퓨터시스템에서 운용되는 각종 유틸리티 소프트웨어를 설계하고 개발합니다. 운영체계를 해당 컴퓨터 하드웨어 시스템에 이식(Potting)하고 기능과 성능을 종합적으로 평가합니다.\n"
        binding.addtextView3.text = "Java, JavaScript, PHP"
        binding.bottomButton2.setOnClickListener {
            val wikiUrl8 = "https://namu.wiki/w/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl8))
            startActivity(intent)
        }
    }
    private fun button8() {
        button8view()
        binding.button1.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button1.isChecked) {
                binding.button2.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "SI란?"
                binding.textView2.text = "SI 개발자란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "SI(System Integration)는 시스템 통합으로 다양한 하위 시스템이나 컴포넌트들을 하나의 통합된 시스템으로 구축하고 연결하는 과정을 말합니다. 이를 통해 다양한 시스템이 상호 작용하고 데이터를 공유할 수 있도록 하며, 조직의 업무 효율성을 향상시키고 비용을 절감할 수 있습니다."
                binding.addtextView2.text =
                    "SI 개발자는 기업이 필요로 하는 정보시스템에 관한 기획에서부터 개발과 구축, 나아가서는 운영까지의 모든 서비스를 제공하는 업무기업에서 사용할 프로그램을 구축합니다.\n" +
                            "\n" +
                            "■ SI 업무 진행 프로세스\n" +
                            "전산시스템이 복잡해지고 전문성이 필요해짐에 따라 시스템의 개발, 기획, 유지보수, 운영 등을 대신합니다.\n" +
                            "1단계 분석 - 고객의 요구 사항을 수집하는 단계\n" +
                            "2단계 설계 - 개발 가능한 형태로 설계하는 단계\n" +
                            "3단계 개발 - 분석, 설계를 통해 정해진 규격으로 프로그램을 구축하는 단계\n" +
                            "4단계 테스트 - 완성된 프로그램을 테스트하고 안정화하는 단계\n" +
                            "5단계 검수 - 계약대로 결과물이 구축되었는지 확인하고 인증받는 단계\n" +
                            "\n" +
                            "■ SI 세부 직무\n" +
                            "요구사항 분석, 분석/설계, 개발, 테스트, 검수"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "각 개발 언어에 대한 공부가 필요합니다. Html5, Javascript(jQuery), Java, 스프링 프레임워크, SQL 등에 대한 기본적인 문법 공부가 요구됩니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "국내 SI 업계는 주로 스프링 프레임워크(전자정부표준프레임워크)를 기반으로 하는 Java 웹 개발이나 모바일 앱 개발을 합니다. JavaScript와 PHP도 가능하고 비주얼 베이직도 일부 사용하고 있습니다.\n" +
                        "\n" +
                        "■ 사용 언어\n" +
                        "- Java\n" +
                        "- JavaScript\n" +
                        "- PHP : 대표적인 서버 사이드 스크립트 언어로 전 세계 수많은 웹 시스템의 기반이 되는 언어입니다."
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl81 =
                        "https://namu.wiki/w/%EC%8B%9C%EC%8A%A4%ED%85%9C%20%ED%86%B5%ED%95%A9"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl81))
                    startActivity(intent)
                }
            }
            else{
                button8view()
            }
        }
        binding.button2.setOnCheckedChangeListener { group, checkedId ->
            if (binding.button2.isChecked) {
                binding.button1.isChecked = false
                binding.button3.isChecked = false
                binding.textView1.text = "SM란?"
                binding.textView2.text = "SM 전문가란?"
                binding.textView3.text = "되기 위해서는?"
                binding.addtextView1.text =
                    "SM(System Management)은 전산 시스템의 운영, 모니터링, 유지보수, 문제 해결 등과 같은 일련의 활동을 포함하는 관리 절차입니다. SM은 전산 시스템을 효율적으로 운영하여 안정성과 가용성을 유지하고, 문제가 발생했을 때 신속하게 대응하여 시스템의 지속적인 운영을 보장하는데 중점을 둡니다."
                binding.addtextView2.text =
                    "SM 전문가는 시스템의 운영에 대한 전반적인 업무를 담당합니다. 회사 시스템이 문제없이 작동하도록 유지하는 업무를 수행합니다.\n" +
                            "\n" +
                            "■ SM 업무 진행 프로세스\n" +
                            "1. 오류 수정 - 시스템 오류를 수정\n" +
                            "2. 기능 개선 - 기능을 수정하거나 불편 요소를 개선\n" +
                            "3. 기능 추가 - 필요한 기능을 추가 개발\n" +
                            "4. 데이터 제공 - 요구하는 데이터를 DB에서 추출\n" +
                            "5. 시스템 안정화 - 시스템 운영을 위한 최적화\n" +
                            "\n" +
                            "■ SM 세부 직무\n" +
                            "- 오류 수정, 기능 개선, 기능 추가, 데이터 제공, 시스템 안정화\n" +
                            "- SI를 통해 오픈한 시스템을 유지보수"
                binding.addtextView3.text = "■ 갖추어야 할 지식\n" +
                        "금융시스템의 SM을 담당하는 개발자들은 프로그램 개발 뿐만 아니라, 금융업에 대한 지식이 있어야 합니다.\n" +
                        "\n" +
                        "■ 필요한 기술\n" +
                        "분야마다 제일 중요시되는 역량이 조금씩 다릅니다.\n" +
                        "\n" +
                        "1. 계정계\n" +
                        "- 금융 업무 지식(특상)\n" +
                        "- 커뮤니케이션 및 협업(특상)\n" +
                        "- SQL(상)\n" +
                        "- IT 능력(중)\n" +
                        "\n" +
                        "2. 인프라\n" +
                        "- IT 능력(특상)\n" +
                        "- 외국어(상)\n" +
                        "- DB 및 SQL(상)\n" +
                        "\n" +
                        "3. 정보계\n" +
                        "- IT 능력(특상)\n" +
                        "- SQL(특상)\n" +
                        "- 커뮤니케이션 및 협업(중)\n" +
                        "\n" +
                        "4. 채널\n" +
                        "- IT 능력(특상)\n" +
                        "- 커뮤니케이션 및 협업(특상)"
                binding.bottomButton1.visibility = View.GONE
                binding.bottomButton2.setOnClickListener {
                    val wikiUrl82 =
                        "https://namu.wiki/w/%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EA%B4%80%EB%A6%AC"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl82))
                    startActivity(intent)
                }
            }
            else{
                button8view()
            }
        }
    }

}
