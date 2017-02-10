package com.pyo.android.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;


public class NotificationActivity extends Activity {

	private static final int NOTIFY_1 = 0x1001;
	private static final int NOTIFY_2 = 0x1002;
	private static final int NOTIFY_3 = 0x1003;
	private static final int NOTIFY_4 = 0x1004;
	private static final int NOTIFY_5 = 0x1005;

	private NotificationManager notifier = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//알림기능은 시스템 서비스(원격 통신이기 때문)이며, 해당 알림 시스템 서비스를
		//담당하는 객체를 리턴 받는다.
		notifier = 
				(NotificationManager) 
				getSystemService(Context.NOTIFICATION_SERVICE);     
		/*
		 *  실제 알림 내용 자체를 담당하는 객체를 생성
		 *  상태표시줄에 보여질 이미지,틱커 텍스트, 알림 시간
		 */
		final Notification notify = 
				new Notification(R.drawable.android_32,
						"통지메세지!", 
						System.currentTimeMillis());
		//직접 설정 가능(공용변수가  notify객체에는 많으니 API 참조)
		//  notify.icon = R.drawable.android_32;
		//   notify.tickerText = "통지메세지!";
		//  notify.when = System.currentTimeMillis();

		Button notify1 = (Button) findViewById(R.id.notify1);
		notify1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//현재 알림 메세지가 발생한 횟수
				notify.number++;
				//알림의 플래그값(여기선 진동을 발생할지 여부)
				//반복진동시 FLAG_INSITENT
				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				Intent toLaunch = 
						new Intent(NotificationActivity.this, NotificationActivity.class);
				//펜딩 인텐트 설정
				PendingIntent intentBack = 
						PendingIntent.getActivity(NotificationActivity.this, 0, toLaunch, 0);

				//intentBack객체는 알림을 누르면 실행되는 인텐트 임
				notify.setLatestEventInfo(NotificationActivity.this, 
						"요기 요기!", "메세지 상세 내용 입니다.", intentBack);
				//같은 종류의 알림인지를 구분
				notifier.notify(NOTIFY_1, notify);          
			}
		});
		//진동 알림 설정    
		Button notify2 = (Button) findViewById(R.id.notify2);
		notify2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Notification notify =
						new Notification(android.R.drawable.stat_notify_chat,
								"진동알림!", System.currentTimeMillis());
				// 사용자가 알림을 확인 하면 바로 삭제
				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				//홀수/짝수 색인 = 간격/진동(밀리초)
				notify.vibrate = new long[] 
						{200, 200,  600, 600,   
						600, 200,  200, 600,   600, 
						200, 200, 200, 200, 600,   
						200,200, 600,200, 200, 600, 
						600, 200, 600, 200, 600, 600,
						200, 200, 200, 600,   
						600, 200, 200, 200, 200, 600,}; 

				Intent toLaunch = 
						new Intent (NotificationActivity.this, NotificationActivity.class);
				//펜딩 인텐트 설정
				PendingIntent intentBack =
						PendingIntent.getActivity(
								NotificationActivity.this, 0, toLaunch, 0);

				notify.setLatestEventInfo(NotificationActivity.this,
						"징징!", "진동이 설정된 알림 입니다.",
						intentBack);

				notifier.notify(NOTIFY_2, notify); 
				//android.permission.VIBRATE 권한 필요
				Vibrator vibe = 
						(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				//일단 보내면 0.5초 진동 알림.
				vibe.vibrate(500);             
			}        
		});
		//지시등의 깜빡임, 색깔, 속도 설정.
		Button notify3 = (Button) findViewById(R.id.notify3);
		notify3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				notify.flags |= Notification.FLAG_AUTO_CANCEL;
				//발생 횟수
				notify.number++;

				notify.flags |= Notification.FLAG_SHOW_LIGHTS;

				if (notify.number < 2) {
					notify.ledARGB = Color.GREEN;
					notify.ledOnMS = 1000;
					notify.ledOffMS = 1000;
				} else if (notify.number < 3) {
					notify.ledARGB = Color.BLUE;
					notify.ledOnMS = 750;
					notify.ledOffMS = 750;
				} else if (notify.number < 4) {
					notify.ledARGB = Color.WHITE;
					notify.ledOnMS = 500;
					notify.ledOffMS = 500;
				} else {
					notify.ledARGB = Color.RED;
					notify.ledOnMS = 50;
					notify.ledOffMS = 50;
				}

				Intent toLaunch = new Intent (
						NotificationActivity.this, NotificationActivity.class);
				PendingIntent intentBack = 
						PendingIntent.getActivity(
								NotificationActivity.this, 0, toLaunch, 0);

				notify.setLatestEventInfo(NotificationActivity.this,
						"지시등설정!", "색깔을 확인 하세요.", intentBack);

				notifier.notify(NOTIFY_3, notify);       
			}
		});
		// 사운드 설정
		Button notify4 = (Button) findViewById(R.id.notify4);
		notify4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				notify.sound = 
						Uri.parse("file:/system/media/audio/ringtones/VeryAlarmed.ogg");

				Intent toLaunch = 
						new Intent (NotificationActivity.this, NotificationActivity.class);
				PendingIntent intentBack =
						PendingIntent.getActivity(NotificationActivity.this, 0, toLaunch, 0);

				notify.setLatestEventInfo(NotificationActivity.this,
						"소리설정!", "당신폰의 노이즈 소리 임!.", intentBack);          
				notifier.notify(NOTIFY_4, notify);
			}            
		});
		//알림뷰 확장
		Button notifyRemote = (Button) findViewById(R.id.notifyRemote);
		notifyRemote.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				RemoteViews remote = 
						new RemoteViews(getPackageName(), R.layout.remote);
				remote.setTextViewText(R.id.text1, "큰 글씨!");
				remote.setTextViewText(R.id.text2, "데코레이션 메세지!");

				notify.contentView = remote;

				Intent toLaunch = new Intent
						(NotificationActivity.this, NotificationActivity.class);
				PendingIntent intentBack = 
						PendingIntent.getActivity(NotificationActivity.this, 0, toLaunch, 0);
				notify.contentIntent = intentBack;           
				notifier.notify(NOTIFY_5, notify);             
			}        
		});
	} 
}