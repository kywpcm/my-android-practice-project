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

//Activity�� M, V, C�� ��� ����Ѵ�.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	EditText receiveMessage;
	EditText sendMessage;
	Button receiveBtn;
	Button sendBtn;
	ListView list;

	MyAdapter mAdapter;
	ArrayList<ItemData> mData = new ArrayList<ItemData>();  //������ ����

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

		//�׳� �Ϲ����� ListView Ŭ�� ó���� ����  AdapterView OnItemClickListener
		list.setOnItemClickListener(new OnItemClickListener() {
			//����Ͱ� position�� �ִ� �������� get�ؼ� �� �������� name�� TextView�� ������
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d(TAG, "onItemClick ListView...!\n"
						+ "position is " + position);
				ItemData item = mAdapter.getItem(position);
				Toast.makeText(MainActivity.this, "Message is " + item.message, Toast.LENGTH_SHORT).show();
			}
		});

		//��ư ������ ���
		receiveBtn.setOnClickListener(btnListener);
		sendBtn.setOnClickListener(btnListener);

		//EditText ������ ���
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
					/*MyAdapter�� add() �޼ҵ忡 ItemData ��ü�� �����ϰ�,
					�ű⼭, ����Ʈ�� �������� add�Ѵ�.*/
					//����Ϳ� ������ �������� �߰�			   //ItemData�� isSend�� ���޵�
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
					/*MyAdapter�� add() �޼ҵ忡 ItemData ��ü�� �����ϰ�,
					�ű⼭, ����Ʈ�� �������� add�Ѵ�.*/
					//����Ϳ� ������ �������� �߰�			   //ItemData�� isSend�� ���޵�
					mAdapter.add(new ItemData(message, isSend));
				}
				sendMessage.setText("");
				break;
			}
		}
	};
	
	//TextWatcher
	//���� ����Ű�� �� ���ڴ�..
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

	//������ �ʱ�ȭ
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

	//��ư ù Ŭ�� ��, ����Ʈ�信 ����� ���.
	private void initListView(boolean isSend) {
		Log.d(TAG, "initListView()");
		initData(isSend);  //������ �ʱ�ȭ
		//����� ����, Control�� Model�� View�� ������ �����Ѵ�.
		mAdapter = new MyAdapter(this, mData); //�����͸� Control���� �����ϰ�, Control�� �����͸� View���� �����Ѵ�.
		list.setAdapter(mAdapter);  //����Ʈ�信 ����͸� ���
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
