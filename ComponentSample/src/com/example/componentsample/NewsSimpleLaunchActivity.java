package com.example.componentsample;

import com.example.kywpart1homework01.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsSimpleLaunchActivity extends Activity {

	public static final String VIEW_NEWS = "kyw.intent.action.VIEW_NEWS";
	
	private LinearLayout rootLayout;
	private Button startService;
	private Button stopService;
	private TextView newsMessage;
	private ImageView newsImage;
	
	private NewsBroadcastReceiver newsReceiverObj;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//루트 레이아웃  생성
		rootLayout = new LinearLayout(this);

		LinearLayout.LayoutParams matchParams = new 
				LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);

		LinearLayout.LayoutParams wrapParams = new 
				LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

		rootLayout.setLayoutParams(matchParams);
		rootLayout.setOrientation(LinearLayout.VERTICAL);
//		rootLayout.setGravity(Gravity.CENTER);

		startService = new Button(this);
		startService.setLayoutParams(wrapParams);
		startService.setText("서비스시작");

		stopService = new Button(this);
		stopService.setLayoutParams(wrapParams);
		stopService.setText("서비스정지");
		
		newsMessage = new TextView(this);
		newsMessage.setLayoutParams(wrapParams);
		newsMessage.setText("텍스트");

		newsImage = new ImageView(this);
		newsImage.setLayoutParams(wrapParams);
		newsImage.setImageResource(R.drawable.ic_launcher);
		
		rootLayout.addView(startService);
		rootLayout.addView(stopService);
		rootLayout.addView(newsMessage);
		rootLayout.addView(newsImage);

		setContentView(rootLayout);

		startService.setOnClickListener(serviceControlListener);
		stopService.setOnClickListener(serviceControlListener);
	}

	@Override
	public void onStart(){
		super.onStart();
		//...
		newsReceiverObj = new NewsBroadcastReceiver();
    	IntentFilter brFilter = 
    			new IntentFilter(VIEW_NEWS);
    	registerReceiver(newsReceiverObj, brFilter);
	}
	
	public void onPause(){
		super.onPause();
		
		Intent serviceIntent = new Intent(this, NewsSimpleService.class);
		stopService(serviceIntent);
		
		if( newsReceiverObj != null){
			unregisterReceiver(newsReceiverObj);  //리시버 등록 해제
		}
	}

	private View.OnClickListener serviceControlListener = new View.OnClickListener() {	
		@Override
		public void onClick(View view) {
			String btnLabel = ((Button)view).getText().toString();

			Intent serviceIntent = new 
					Intent(NewsSimpleLaunchActivity.this, NewsSimpleService.class);

			if(btnLabel.equals("서비스시작")){
				serviceIntent.putExtra("newsSubject", 3);
				startService(serviceIntent);
			}else{
				stopService(serviceIntent);
			}
		}
	};

	private class NewsBroadcastReceiver extends BroadcastReceiver{
		
		public void onReceive(Context context, Intent intent){
			String brActionName = intent.getAction();
			
			if(brActionName.equals(VIEW_NEWS)){
				newsMessage.setText("");
				newsMessage.setText(intent.getStringExtra("newsSection") +
						":\n" +
						intent.getStringExtra("newsHeadLine"));
				newsImage.setImageResource(intent.getIntExtra("newsImage", 0));
			}
		}

	}

}