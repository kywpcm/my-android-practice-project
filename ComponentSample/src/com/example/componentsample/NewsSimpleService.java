package com.example.componentsample;

import java.util.HashMap;
import java.util.Random;

import com.example.kywpart1homework01.R;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class NewsSimpleService extends Service {
	
	//인텐트로 넘어온 엑스트라 값.
	//초기 뉴스의 종류만 선택 함
	private int extraValue;

	private BackGroundSubThread subThread;

	/*
	 * startService() 후에 처음 호출되는 서비스 콜백 메소드
	 */
	@Override
	public void onCreate(){
		super.onCreate();
		Toast.makeText(this, "Service 콜백 메소드 onCreate() 실행! ", Toast.LENGTH_SHORT).show();
	}

	/*
	 * 원격 호출시에 넘겨준 원격 인터페이스 객체
	 * 로컬에서는 null을 넘겨 준다.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * 2.0 이후의 서비스 콜백 메소드
	 */
	@Override
	public  int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);
		
		if( flags == START_FLAG_RETRY){
			//정상종료가 아닌 경우 반드시 실행되어야 하는 인텐트의 정보를 여기서 다시 진행 함
			extraValue = 3;
		}
		if( intent == null && subThread == null ){
			//START_STICKY모드로 비정상 종료시 처리해야 하는 업무
			subThread = new BackGroundSubThread("BACK_THREAD_1"); 
		}
		if( intent != null){
			extraValue = intent.getIntExtra("newsSubject", 0); //현재 3..
		}   
		if( subThread == null){
			subThread = new BackGroundSubThread("BACK_THREAD_1");
		}

		subThread.start();
		Toast.makeText(this, "Service 콜백 메소드 onStartCommand() 실행! ", 
				Toast.LENGTH_SHORT).show();
		
		//서비스의 비정상적 종료시에 null 값을 가진 인텐트가 재실행 됨
		return START_STICKY;
	}

	/*
	 *  stopService 호출시  콜백 됨
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if( subThread.isAlive()){ //백그라운드가 살아 있다면
			subThread.interrupt();  //메인 스레드에서 subThread를 interrupt 발생 요청..
		}
		
		this.stopSelf(); //서비스 종료
	}

	/*
	 *  백그라운드 쓰레드는 서비스의 라이프 사이클을 벗어남
	 */
	private  class BackGroundSubThread extends Thread {

		private HashMap<String, String> newsMap;
		private Random  random ;
		private Intent newsIntent;

		public BackGroundSubThread(String threadName){
			super(threadName);

			random = new Random(System.currentTimeMillis());
			newsMap= new HashMap<String, String>();
			newsMap.put("정치", "한반도 극적 통일 이루어져~~~");
			newsMap.put("경제", "통일한국 GNP 10만 달러 이룩");
			newsMap.put("사회", "대한민국 노총각 결혼추진위원회 헌법에 명시화");
			newsMap.put("연예", "소녀시대 수영 아카데미 여우 주연상 수상");
			newsIntent = new Intent(NewsSimpleLaunchActivity.VIEW_NEWS);
		}

		public void run() {
			while(!isInterrupted()) {
				String newsHeadLine = "", newsSection = "";
				int rscId;

				if( extraValue == 0 ){
					newsHeadLine =  newsMap.get("정치");
					newsSection = "정치";
					rscId = R.drawable.politics;
				}else if( extraValue == 1){
					newsHeadLine=  newsMap.get("경제");
					newsSection = "경제";
					rscId = R.drawable.economy;
				}else if( extraValue == 2){
					newsHeadLine=  newsMap.get("사회");
					newsSection = "사회";
					rscId = R.drawable.society;
				}else{
					newsHeadLine=  newsMap.get("연예");
					newsSection = "연예";
					rscId = R.drawable.entertainment;
				}
				
				extraValue = random.nextInt(4);
				
				try{
					sleep(5000);  //무한 루프를 도는 while()문 같은데서는 반드시 일정 시간 block 해주는 코드가 필요! (cpu 과부하 등의 이유로..)
				}catch(InterruptedException ie){
					newsHeadLine = "서비스 및  백그라운드 쓰레드 종료!";
					newsSection = "서비스 종료";
					currentThread().interrupt();  //현재 스레드에서 다시 interrupt 발생을 요청해야 while() 문을 빠져 나올 수 있다.
				}
				
				newsIntent.putExtra("newsHeadLine", newsHeadLine);
				newsIntent.putExtra("newsSection", newsSection);
				newsIntent.putExtra("newsImage", rscId);
				sendBroadcast(newsIntent);
			}
		}
		
	}


}