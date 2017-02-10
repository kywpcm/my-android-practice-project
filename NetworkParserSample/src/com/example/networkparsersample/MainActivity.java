package com.example.networkparsersample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	EditText urlView;
	TextView messageView;
	ListView list;

	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_main);
		
		urlView = (EditText)findViewById(R.id.urlText);
		messageView = (TextView)findViewById(R.id.message);
		list = (ListView)findViewById(R.id.listView1);
		
		Button btn = (Button)findViewById(R.id.go);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "go Button click..!");

				//xml 파싱 요청 request..
//				MelonRequest request = new MelonRequest();  //요청 클래스 객체 생성
				//json 파싱 요청 request..
				MelonJsonRequest request = new MelonJsonRequest();
				
				NetworkModel.getInstance().getNetworkData(request,
						new NetworkRequest.OnProcessCompleteListener() {
					
					@Override
					public void onCompleted(NetworkRequest request) {
//						MelonRequest rq = (MelonRequest)request;  //polymorphism..
						MelonJsonRequest rq = (MelonJsonRequest)request;  //polymorphism..
						
						Melon melon = rq.getResult();  //request 객체의 결과 get
						
						ArrayAdapter<Artist> aa = new ArrayAdapter<Artist>(MainActivity.this,
								android.R.layout.simple_list_item_1,
								melon.songs.song.get(0).artists.artist);
//						ArrayAdapter<Song> aa = new ArrayAdapter<Song>(MainActivity.this,
//								android.R.layout.simple_list_item_1,
//								melon.songs.song);
						list.setAdapter(aa);
						
						Log.d(TAG, "메인 스레드, UI처리, 리스트뷰에 어댑터 set");
					}

					@Override
					public void onError(NetworkRequest request, String errorMessage) {
						//...
					}

				}, mHandler);

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
