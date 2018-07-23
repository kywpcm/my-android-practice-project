# my-android-practice-project

### 1. MultipleChoiceListView
> 리스트뷰의 아이템 선택 시, 멀티 초이스 가능. 리스트 아이템의 백드라운드 컬러 변경 가능. 주석 처리 완료

### 2. CustomListSample
> 체크박스를 이용해서 리스트에 다른 모양의 아이템 뷰들을 보여줄 수 있다. ChatWithCarlyRaeJepsen 프로젝트의 기반. 인터페이스와 리스너 등록을 활용한 객체 지향에서의 중요한 개념이 들어있다. 또한, getViewTypeCount() 메소드와 getItemViewType() 메소드를 사용하고 있다. 주석 처리 완료

### 3. ChatWithCarlyRaeJepsen
> t아카데미, 안드로이드 어플리케이션 프로그래밍, 1차 과제, 카카오톡과 비슷한 로컬 채팅 서비스

### 4. BasicMenuTest
> 여러가지 메뉴들에 대한 테스트 샘플 프로젝트
- 안드로이드 버전과 API 레벨에 따라 여러가지 테스트가 필요함

### 5. AdditionalMenuTest
> 추가적인 메뉴들에 대한 테스트 샘플 프로젝트. 프래그먼트와 액션바를 활용한 예제. 주석 처리 완료

### 5-1. FragmentTabSample
> 프래그먼트를 활용한 간단한 탭 샘플. TabManager.java 파일 있음

### 5-2. FragmentTabsPager
> 프레그먼트를 활용한 간단한 탭 페이저 샘플 프로젝트. TabsAdapter.java 파일 있음. 주석 처리 완료

### 5-3. FragmentStackSample
> 간단한 프레그먼트 스택 샘플

### 5-4. FragmentDialogSample
> 간단한 프레그먼트 다이얼로그 샘플. 기존 AlertDialog 사용 방법도 있음. 주석 처리 완료

### 5-5. ViewPagerSample
> 간단한 뷰페이저 샘플

### 5-6. ViewAnimationSample
> 안드로이드 뷰/레이아웃 애니메이션 샘플 프로젝트. 쓸만한 애니메이션 효과 2개 정도 있음

### 5-7. OffScreenCanvasSample
> 캔버스를 활용한 간단한 그림판을 만든 프로젝트. 터치한 곳을 따라 빨간 점들이 그려진다.

### 5-8. BasicSurfaceViewSample
> 기본적인 SurfaceView 샘플 프로젝트

### 6. ComponentTest
> 액티비티, 서비스, 브로드캐스트 리시버와 같은 컴포넌트 사용 예제. 아직 Notification 기능 추가되기 전, ComponentNotiSample 프로젝트에서 추가 됨.

### 6-1. BackgroundThreadSample
> 기본적인 백그라운드 스레드, 핸들러 사용. Runnable 객체 구현. AsyncTask 구현. back키 두 번 누르는 처리 등

### 7. NetworkSamplePrototype
> 클라이언트에서 네트워크를 사용할 때의 기법, 프로토타입. 주석 처리 완료

### 8. NetworkParserSample
> 네트워크 파싱해서 리스트뷰에 보여주기, 여러 종류의 parser 코드(.java) 패키지 있음, 이미지 파싱은 없음. SKP 오픈 API - 멜론 오픈 API 사용. 여러 parser들 중, SAX parser, Gson parser 이용. xml, json 형태의 컨텐츠 파싱 사용법. 주석 처리 완료

### 9-1. NetworkImageParserSample
> 네트워크 이미지 파싱 샘플, 이미지 파싱할 때 고려되는 여러가지 이슈들을 보완하지 않은 버전. NetworkImageParserSolvedIssueSample 이슈 해결 프로젝트. NaverBookSearch 프로젝트의 base. 주석 처리 완료

### 9-2. NetworkImageParserSolvedIssueSample
> 이미지 캐싱 등 이미지 파싱 관련 이슈 해결된 프로젝트. NetworkImageParserSample - 이슈 미해결 프로젝트

### 9-3. NaverBookSearch
> 그냥 NetworkImageParserSample copy&paste한 프로젝트, 2차 과제

### 10. DatabaseSample
> 기본적인 SQLite 데이터베이스 사용 샘플 프로젝트, SQLiteOpenHelper 클래스 사용함. 주석 처리 완료

### 11. DatabaseSimpleCursorAdapterSample
> DatabaseSample 프로젝트에 Cursor만 추가 됨. SimpleCursorAdapter 사용. DatabaseManager 클래스에 onCompleted() 메소드를 가지고 있는 OnQueryCompleteListener 인터페이스 정의. DB 작업을 다른 스레드에서 처리함. 주석 처리 완료

### 12. SharedPreferencesSamplePrototype
> SharedPreferences 사용의 프로토타입, 클래스 따로 만들어서 사용

### 13. ContentGetSample
> 컨텐트 프로바이더와 로더 사용, 전화번호부 가져오기, 자동완성 같은 기능이 구현되어 있음

### 14. ComponentNotiSample
> ComponentTest 프로젝트에 Notification 기능 추가

### 15. AlarmSample
> AlarmManager를 사용한 알람 샘플 프로젝트, 서비스를 깨워줌

### 16. BasicAppWidgetSample
> AlarmManager와 서비스를 이용한 기본적인 앱 위젯 샘플 프로젝트

### 17. LocationSamplePrototype
> requestLocationUpdates(), requestSingleUpdate()->로케이션 리스너를 쓰는 경우->한번만 업데이트하고 그만 둘 경우, requestSingleUpdate()->펜딩 인텐트 쓰는 경우->서비스에서 지속적으로 업데이트하고 싶은 경우 등에 대한 프로토타입성 프로젝트

### 18. LocationSampleWithProximityAlert
> proximityAlert 사용 등 lbs 응용 프로젝트

### 19. SimpleTMapSample
> T map 오픈 API 이용, 아주 간단한 구현

### 20. TMapPOIAndRouteSample
> POI(Point Of Interest)와 경로 기능 적용된 T map 프로젝트

### 21. BasicGoogleMapSample
> BasicGoogleMapSample 프로젝트

### 22. ComponentSample
> 안드로이드 기본적인 컴포넌트 사용, t아카데미 안드로이드 전문가 과정 1차 과제

### 23. AndroidCustomIntentSample
> 자바 프로젝트, 자바로 간단한 인텐트를 직접 만들어 사용하는 예제

### 24. SimpleViewWidget
> 안드로이드의 기본적인 뷰 위젯들에 관한 예제. 프로토타입적이고, 양이 많아서 참고용으로 좋음

### 25. SimpleLayout
> 안드로이드 기본적인 레이아웃에 관한 예제. 프로토타입적. 강사님 계산기 리소스 있음

### 25-1. MyCalculator
> 표 강사님 리소스로 안드로이드 계산기 구현. 2차 과제. 무조건 수정 필요

### 26. AndroidThreadUsingActivity
> AsyncTask의 프로토타입적 구현. 참고하기 좋음

### 26-1. MassFileCopyExample
> 약 20MB의 파일을 sdcard에서 sdcard의 특정 폴더에 copy하는 예제. 백그라운드 스레드 사용과 파일 입출력을 활용함. 3차 과제

### 27. AndroidWidgetExpansion
> 안드로이드 custom 확장 위젯. ListView와 어댑터의 응용 및 확장 등

### 28. AndroidAnimationProject
> 안드로이드 애니메이션에 관한 다양한 예제. 양도 많고, 참고하기 좋음

### 29. TouchEventProject
> 안드로이드 터치 관련 예제 프로젝트. 멀티터치 줌인/줌아웃 등

### 30. SimplePreferences
> 간단한 Preferences 사용 예제

### 31. PreferenceActivity
> PreferenceActivity 사용 예제

### 32. SQLBasicDatabase
> 표 SQLite 데이터베이스 사용 예제. UI 없는 로그캣 버전. 테이블 2개 사용 함. CRUD 기능이 잘 구현 되어 있음. SQLiteOpenHelper는 사용 안함. 주석 처리 완료

### 33. SQLiteNoCursorAdapter
> 어댑터를 사용하지 않고, 위젯과 데이터의 연동을 수동으로 처리. AutoCompleteTextView 처리. SQLiteOpenHelper 사용. 테이블 2개 사용. 삭제 처리 등 좋은 참고 프로젝트. 주석 처리 완료

### 34. SQLiteSimpleCursorAdapter
> SQLiteNoCursorAdapter 프로젝트에 안드로이드 SimpleCursorAdapter를 적용한 프로젝트. deprecated 된 메소드 사용에 관한 주의할 내용이 들어 있음. 리스트뷰의 속성에 Devider 사용. 삭제 버튼 클릭 시 다이얼로그 뜨고 데이터 삭제 됨. 주석 처리 완료

### 34-1. SQLiteCustomCursorAdapter
> 사용자 정의 커서 어댑터를 적용한 예제. CursorAdapter를 상속 받은 CustomCursorAdapter 클래스 작성. newView, bindView 메소드 사용. 주석 처리 완료

### 35. SQLiteFinalProject
> SQLite 데이터베이스와 커서 사용의 마지막 프로젝트

### 36. CustomContentProvider
> 사용자 정의 컨텐트 프로바이더 예제

### 37. AndroidMultiMediaProject
> 프로젝트 내 MediaPlayer(mp3 player) 예제. 강사님 FM 실전 코딩 프로젝트. AudioRecoderActivity(custom 녹음기) 예제. SoundPoolUsing(게임 효과음 등) 예제

### 38. NotificationApp
> 간단한 noti에 관련된 괜찮은 예제 프로젝트

### 39. SimpleAlarmManagerActivity
> 간단한 AlarmManager 활용 예제

### 40-1. AndroidClientEchoActivity
> 안드로이드 에코 클라이언트 프로젝트

### 40-2. JavaServerEcho
> 자바 에코 서버 프로젝트

### 41-1. TCPChattingClient
> 안드로이드 TCP 채팅 클라이언트

### 41-2. TCPChattingServer
> 자바 TCP 채팅 서버

### 42. HTTPImageStream
> 톰캣 서버, XmlPullParser, 세마포어 기법을 통한 생산자 스레드와 소비자 스레드를 구현한 프로젝트. ArrayList를 큐 처럼 사용하는 팁도 있음. 소녀시대 이미지를 파싱하여 보여준다.

### 43. SensorList
> 단말에서 지원하는 센서들의 리스트를 출력하는 예제. 프로안드로이드4 예제. 주석 처리 완료

### 44. AccelerometerSensor
> 가속도 센서의 간단한 사용 예제. 프로안드로이드4 예제. 주석 처리 완료

### 45. GravityDemo
> 로우패스필터 적용. 더 정확한 중력값 추출. raw 값과 중력값의 차를 이용해 모션 가속도를 추출. 프로안드로이드4 예제. 주석 처리 완료

### 46. BluetoothChat
> 블루투스 사용의 프로토타입적 예제. 안드로이드 샘플 코드. 주석 처리 완료

### 48. JDBCTest
> MySQL JDBC 연결을 테스트한 자바 프로젝트. MySQL 커넥터 for Java 라이브러리 있음.

### 49. JSPTest
> JSPTest 자바 프로젝트

### 50. TAcademyNetworkExample
> t아카데미 네트워크 예제 샘플

### 51. MyGridViewTest
> 안드로이드 그리드뷰 샘플 프로젝트
