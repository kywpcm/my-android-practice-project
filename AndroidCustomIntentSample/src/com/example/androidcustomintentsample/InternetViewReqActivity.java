package com.example.androidcustomintentsample;
import javax.activity.ActivityCompletedException;


public class InternetViewReqActivity extends Activity {
	
	public static void main(String[] args) {

		InternetViewReqActivity mainLauncher = new InternetViewReqActivity();
		mainLauncher.onCreate();
		
	}
	
	public void onCreate() {
		Intent intent = new Intent(this, InternetViewResAction.class);
		intent.setExtras("http://", "www.tstore.co.kr");
		startActivityForResult(intent);
	}
	
	public void onActivityResult(String resultMessage) {
		System.out.println(resultMessage);
	}

}
