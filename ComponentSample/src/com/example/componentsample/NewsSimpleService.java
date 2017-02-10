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
	
	//����Ʈ�� �Ѿ�� ����Ʈ�� ��.
	//�ʱ� ������ ������ ���� ��
	private int extraValue;

	private BackGroundSubThread subThread;

	/*
	 * startService() �Ŀ� ó�� ȣ��Ǵ� ���� �ݹ� �޼ҵ�
	 */
	@Override
	public void onCreate(){
		super.onCreate();
		Toast.makeText(this, "Service �ݹ� �޼ҵ� onCreate() ����! ", Toast.LENGTH_SHORT).show();
	}

	/*
	 * ���� ȣ��ÿ� �Ѱ��� ���� �������̽� ��ü
	 * ���ÿ����� null�� �Ѱ� �ش�.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * 2.0 ������ ���� �ݹ� �޼ҵ�
	 */
	@Override
	public  int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);
		
		if( flags == START_FLAG_RETRY){
			//�������ᰡ �ƴ� ��� �ݵ�� ����Ǿ�� �ϴ� ����Ʈ�� ������ ���⼭ �ٽ� ���� ��
			extraValue = 3;
		}
		if( intent == null && subThread == null ){
			//START_STICKY���� ������ ����� ó���ؾ� �ϴ� ����
			subThread = new BackGroundSubThread("BACK_THREAD_1"); 
		}
		if( intent != null){
			extraValue = intent.getIntExtra("newsSubject", 0); //���� 3..
		}   
		if( subThread == null){
			subThread = new BackGroundSubThread("BACK_THREAD_1");
		}

		subThread.start();
		Toast.makeText(this, "Service �ݹ� �޼ҵ� onStartCommand() ����! ", 
				Toast.LENGTH_SHORT).show();
		
		//������ �������� ����ÿ� null ���� ���� ����Ʈ�� ����� ��
		return START_STICKY;
	}

	/*
	 *  stopService ȣ���  �ݹ� ��
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if( subThread.isAlive()){ //��׶��尡 ��� �ִٸ�
			subThread.interrupt();  //���� �����忡�� subThread�� interrupt �߻� ��û..
		}
		
		this.stopSelf(); //���� ����
	}

	/*
	 *  ��׶��� ������� ������ ������ ����Ŭ�� ���
	 */
	private  class BackGroundSubThread extends Thread {

		private HashMap<String, String> newsMap;
		private Random  random ;
		private Intent newsIntent;

		public BackGroundSubThread(String threadName){
			super(threadName);

			random = new Random(System.currentTimeMillis());
			newsMap= new HashMap<String, String>();
			newsMap.put("��ġ", "�ѹݵ� ���� ���� �̷����~~~");
			newsMap.put("����", "�����ѱ� GNP 10�� �޷� �̷�");
			newsMap.put("��ȸ", "���ѹα� ���Ѱ� ��ȥ��������ȸ ����� ���ȭ");
			newsMap.put("����", "�ҳ�ô� ���� ��ī���� ���� �ֿ��� ����");
			newsIntent = new Intent(NewsSimpleLaunchActivity.VIEW_NEWS);
		}

		public void run() {
			while(!isInterrupted()) {
				String newsHeadLine = "", newsSection = "";
				int rscId;

				if( extraValue == 0 ){
					newsHeadLine =  newsMap.get("��ġ");
					newsSection = "��ġ";
					rscId = R.drawable.politics;
				}else if( extraValue == 1){
					newsHeadLine=  newsMap.get("����");
					newsSection = "����";
					rscId = R.drawable.economy;
				}else if( extraValue == 2){
					newsHeadLine=  newsMap.get("��ȸ");
					newsSection = "��ȸ";
					rscId = R.drawable.society;
				}else{
					newsHeadLine=  newsMap.get("����");
					newsSection = "����";
					rscId = R.drawable.entertainment;
				}
				
				extraValue = random.nextInt(4);
				
				try{
					sleep(5000);  //���� ������ ���� while()�� ���������� �ݵ�� ���� �ð� block ���ִ� �ڵ尡 �ʿ�! (cpu ������ ���� ������..)
				}catch(InterruptedException ie){
					newsHeadLine = "���� ��  ��׶��� ������ ����!";
					newsSection = "���� ����";
					currentThread().interrupt();  //���� �����忡�� �ٽ� interrupt �߻��� ��û�ؾ� while() ���� ���� ���� �� �ִ�.
				}
				
				newsIntent.putExtra("newsHeadLine", newsHeadLine);
				newsIntent.putExtra("newsSection", newsSection);
				newsIntent.putExtra("newsImage", rscId);
				sendBroadcast(newsIntent);
			}
		}
		
	}


}