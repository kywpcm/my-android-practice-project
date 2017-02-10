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

		//�˸������ �ý��� ����(���� ����̱� ����)�̸�, �ش� �˸� �ý��� ���񽺸�
		//����ϴ� ��ü�� ���� �޴´�.
		notifier = 
				(NotificationManager) 
				getSystemService(Context.NOTIFICATION_SERVICE);     
		/*
		 *  ���� �˸� ���� ��ü�� ����ϴ� ��ü�� ����
		 *  ����ǥ���ٿ� ������ �̹���,ƽĿ �ؽ�Ʈ, �˸� �ð�
		 */
		final Notification notify = 
				new Notification(R.drawable.android_32,
						"�����޼���!", 
						System.currentTimeMillis());
		//���� ���� ����(���뺯����  notify��ü���� ������ API ����)
		//  notify.icon = R.drawable.android_32;
		//   notify.tickerText = "�����޼���!";
		//  notify.when = System.currentTimeMillis();

		Button notify1 = (Button) findViewById(R.id.notify1);
		notify1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//���� �˸� �޼����� �߻��� Ƚ��
				notify.number++;
				//�˸��� �÷��װ�(���⼱ ������ �߻����� ����)
				//�ݺ������� FLAG_INSITENT
				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				Intent toLaunch = 
						new Intent(NotificationActivity.this, NotificationActivity.class);
				//��� ����Ʈ ����
				PendingIntent intentBack = 
						PendingIntent.getActivity(NotificationActivity.this, 0, toLaunch, 0);

				//intentBack��ü�� �˸��� ������ ����Ǵ� ����Ʈ ��
				notify.setLatestEventInfo(NotificationActivity.this, 
						"��� ���!", "�޼��� �� ���� �Դϴ�.", intentBack);
				//���� ������ �˸������� ����
				notifier.notify(NOTIFY_1, notify);          
			}
		});
		//���� �˸� ����    
		Button notify2 = (Button) findViewById(R.id.notify2);
		notify2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Notification notify =
						new Notification(android.R.drawable.stat_notify_chat,
								"�����˸�!", System.currentTimeMillis());
				// ����ڰ� �˸��� Ȯ�� �ϸ� �ٷ� ����
				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				//Ȧ��/¦�� ���� = ����/����(�и���)
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
				//��� ����Ʈ ����
				PendingIntent intentBack =
						PendingIntent.getActivity(
								NotificationActivity.this, 0, toLaunch, 0);

				notify.setLatestEventInfo(NotificationActivity.this,
						"¡¡!", "������ ������ �˸� �Դϴ�.",
						intentBack);

				notifier.notify(NOTIFY_2, notify); 
				//android.permission.VIBRATE ���� �ʿ�
				Vibrator vibe = 
						(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				//�ϴ� ������ 0.5�� ���� �˸�.
				vibe.vibrate(500);             
			}        
		});
		//���õ��� ������, ����, �ӵ� ����.
		Button notify3 = (Button) findViewById(R.id.notify3);
		notify3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				notify.flags |= Notification.FLAG_AUTO_CANCEL;
				//�߻� Ƚ��
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
						"���õ��!", "������ Ȯ�� �ϼ���.", intentBack);

				notifier.notify(NOTIFY_3, notify);       
			}
		});
		// ���� ����
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
						"�Ҹ�����!", "������� ������ �Ҹ� ��!.", intentBack);          
				notifier.notify(NOTIFY_4, notify);
			}            
		});
		//�˸��� Ȯ��
		Button notifyRemote = (Button) findViewById(R.id.notifyRemote);
		notifyRemote.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				notify.flags |= Notification.FLAG_AUTO_CANCEL;

				RemoteViews remote = 
						new RemoteViews(getPackageName(), R.layout.remote);
				remote.setTextViewText(R.id.text1, "ū �۾�!");
				remote.setTextViewText(R.id.text2, "���ڷ��̼� �޼���!");

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