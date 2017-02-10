package com.example.networkimageparsersample;

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
import android.widget.Toast;

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
				
				String urlString = urlView.getText().toString();
				if (urlString != null && !urlString.equals("")) {

					NaverMovieRequest request = new NaverMovieRequest(urlString);
					
					NetworkModel.getInstance().getNetworkData(request,
							new NetworkRequest.OnProcessCompleteListener() {
						
						@Override
						public void onCompleted(NetworkRequest request) {
							Log.d(TAG, "onCompleted() 메소드 시작");
							NaverMovieRequest rq = (NaverMovieRequest)request;
							NaverMovies naverMovies = rq.getResult();
							MyAdapter ma = new MyAdapter(MainActivity.this, naverMovies.items);
							list.setAdapter(ma);
							Log.d(TAG, "메인 스레드, UI처리, 리스트뷰에 어댑터 set");
						}
						
						@Override
						public void onError(NetworkRequest request, String errorMessage) {
							Log.e(TAG, "onError() 메소드 시작");
							Toast.makeText(MainActivity.this, "Error~!", Toast.LENGTH_SHORT).show();
							Log.e(TAG, "메인 스레드, UI처리, Erorr Toast 띄움");
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
