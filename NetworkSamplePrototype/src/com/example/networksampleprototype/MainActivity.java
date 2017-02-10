package com.example.networksampleprototype;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	EditText urlView;
	TextView messageView;
	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_main);
		
		urlView = (EditText)findViewById(R.id.urlText);
		messageView = (TextView)findViewById(R.id.message);
		
		Button btn = (Button)findViewById(R.id.go);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "go Button click..!");
				
				String urlString = urlView.getText().toString();
				if (urlString != null && !urlString.equals("")) {
					String url = null;
					
					//문자열을 url 형태로 만드는 간단한 로직
					if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
						url = urlString;
					} else {
						url = "http://" + urlString;
					}
					
					UrlRequest request = new UrlRequest(url);  //요청 클래스 생성
					NetworkModel.getInstance().getNetworkData(request, new NetworkRequest.OnProcessCompleteListener() {
						
						@Override
						public void onError(NetworkRequest request, String errorMessage) {
							Log.d(TAG, "onError(), UI 처리, 토스트~!");
							Toast.makeText(MainActivity.this, "error Message : " + errorMessage, Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onCompleted(NetworkRequest request) {
							Log.d(TAG, "onCompleted(), UI 처리, TextView set~!");
//							String str = "";
//							UrlRequest urlRequest = new UrlRequest(str);
//							request = urlRequest;  //자식은 부모의 타입으로 자동 형 변환이 된다. implicit cast
							UrlRequest rq = (UrlRequest)request;  //하지만 부모는 자식의 타입으로 강제 형 변환해야 한다. explicit cast
							String message = rq.getResult();
							messageView.setText(message);
						}
					}, mHandler);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
