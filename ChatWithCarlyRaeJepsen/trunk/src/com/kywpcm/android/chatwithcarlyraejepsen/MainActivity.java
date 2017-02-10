package com.kywpcm.android.chatwithcarlyraejepsen;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//Activity가 M, V, C를 모두 사용한다.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	EditText receiveMessage;
	EditText sendMessage;
	Button receiveBtn;
	Button sendBtn;
	ListView list;

	MyAdapter mAdapter;
	ArrayList<ItemData> mData = new ArrayList<ItemData>();  //데이터 생성

	boolean isFirst = true;
	boolean isSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_main);

		receiveMessage = (EditText)findViewById(R.id.receiveEditText);
		sendMessage = (EditText)findViewById(R.id.sendEditText);
		receiveBtn = (Button)findViewById(R.id.receiveBtn);
		sendBtn = (Button)findViewById(R.id.sendBtn);
		list = (ListView)findViewById(R.id.listView);

		//그냥 일반적인 ListView 클릭 처리를 위한  AdapterView OnItemClickListener
		list.setOnItemClickListener(new OnItemClickListener() {
			//어댑터가 position에 있는 아이템을 get해서 그 아이템의 name을 TextView에 보여줌
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d(TAG, "onItemClick ListView...!\n"
						+ "position is " + position);
				ItemData item = mAdapter.getItem(position);
				Toast.makeText(MainActivity.this, "Message is " + item.message, Toast.LENGTH_SHORT).show();
			}
		});

		//버튼 리스너 등록
		receiveBtn.setOnClickListener(btnListener);
		sendBtn.setOnClickListener(btnListener);

		//EditText 리스너 등록
		receiveMessage.addTextChangedListener(receiveWatcher);
		sendMessage.addTextChangedListener(sendWatcher);
	}

	OnClickListener btnListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.receiveBtn:
				isSend = false;
				if(isFirst){
					initListView(isSend);
					isFirst = false;
				} else {
					String message = receiveMessage.getText().toString();
					/*MyAdapter의 add() 메소드에 ItemData 객체를 전달하고,
					거기서, 리스트에 아이템을 add한다.*/
					//어댑터에 데이터 아이템을 추가			   //ItemData의 isSend로 전달됨
					mAdapter.add(new ItemData(message, isSend));
				}
				receiveMessage.setText("");
				break;
			case R.id.sendBtn:
				isSend = true;
				if(isFirst){
					initListView(isSend);
					isFirst = true;
				} else {
					String message = sendMessage.getText().toString();
					/*MyAdapter의 add() 메소드에 ItemData 객체를 전달하고,
					거기서, 리스트에 아이템을 add한다.*/
					//어댑터에 데이터 아이템을 추가			   //ItemData의 isSend로 전달됨
					mAdapter.add(new ItemData(message, isSend));
				}
				sendMessage.setText("");
				break;
			}
		}
	};
	
	//TextWatcher
	//아직 엔터키를 못 막겠다..
	TextWatcher receiveWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			String messageStr = receiveMessage.getText().toString();
			Log.d(TAG, "messageStr.replace() = "+ messageStr.replace(" ", ""));
//			messageStr.replace("\n", "");
			if(messageStr.replace(" ", "").equals("")){
				receiveBtn.setClickable(false);
			} else {
				receiveBtn.setClickable(true);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher sendWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			String messageStr = sendMessage.getText().toString();
			Log.d(TAG, "messageStr.replace() = "+ messageStr.replace(" ", ""));
			if(messageStr.replace(" ", "").equals("")){
				sendBtn.setClickable(false);
			} else {
				sendBtn.setClickable(true);
			}	
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume");
		receiveBtn.setClickable(false);
		sendBtn.setClickable(false);
		Toast.makeText(this, "Input the Message :D", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	//데이터 초기화
	private void initData(boolean isSend) {
		Log.d(TAG, "initData()");
		if(!isSend){
			String message = receiveMessage.getText().toString();
			mData.add(new ItemData(message, false));
		} else {
			String message = sendMessage.getText().toString();
			mData.add(new ItemData(message, true));
		}

	}

	//버튼 첫 클릭 시, 리스트뷰에 어댑터 등록.
	private void initListView(boolean isSend) {
		Log.d(TAG, "initListView()");
		initData(isSend);  //데이터 초기화
		//어댑터 생성, Control이 Model과 View를 가지고 제어한다.
		mAdapter = new MyAdapter(this, mData); //데이터를 Control한테 전달하고, Control은 데이터를 View에게 전달한다.
		list.setAdapter(mAdapter);  //리스트뷰에 어댑터를 등록
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
