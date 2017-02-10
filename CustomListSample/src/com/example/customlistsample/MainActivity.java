package com.example.customlistsample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.customlistsample.R;

//Activity가 M, V, C를 모두 사용한다.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private TextView messageView;
	private EditText inputView;
	private ListView list;

	private MyAdapter mAdapter;
	private List<MyData> mData = new ArrayList<MyData>();  //ArrayList를 참조하는 List 객체 생성

	private CheckBox isSendView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		
		setContentView(R.layout.activity_main);

		initData();  //데이터 초기화

		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);
		isSendView = (CheckBox)findViewById(R.id.isSend);
		
		//어댑터 생성, Control이 Model과 View를 가지고 제어한다.
		mAdapter = new MyAdapter(this, mData); //데이터를 Control한테 전달하고, Control은 데이터를 View에게 전달한다.
		list.setAdapter(mAdapter);  //리스트뷰에 어댑터를 등록

		//그냥 일반적인 ListView 클릭 처리를 위한  AdapterView OnItemClickListener
		list.setOnItemClickListener(new OnItemClickListener() {
			//어댑터가 position에 있는 아이템을 get해서 그 아이템의 name을 TextView에 보여줌
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d(TAG, "onItemClick ListView...!\n"
						+ "position is " + position);
				MyData item = mAdapter.getItem(position);
				messageView.setText(item.name);
			}
		});
		
		//MyItemView의 이미지뷰가 클릭되었을 때, 어떤 처리를 해주기 위한 리스너
		//여기서는 토스트..
		Log.d(TAG, "setOnAdapterItemClickListener() 호출");
		//MyAdapter가 제공하는 인터페이스 서비스..
		mAdapter.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickListener() {

			//메소드의 실제 구현은 여기서!
			@Override
			public void onAdapterItemClick(MyData data) {  //마치 콜백 메소드 처럼..
				Log.e(TAG, "onAdapterItemClick() 구현은 여기서");
				Log.d(TAG, "onAdapterItemClick...!");
				Log.d(TAG, "Toast!!!");
				Toast.makeText(MainActivity.this, data.name + "item image clicked...", Toast.LENGTH_SHORT).show();
			}
		});
		Log.d(TAG, "mAdater에 setOnAdapterItemClickListener 등록 됨");

		//Button 클릭 처리를 위한 View의 OnClickListener
		Button btn = (Button)findViewById(R.id.add);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick Button...!\n"
						+ "isSendView.isChecked() -> " + isSendView.isChecked());
				String str = inputView.getText().toString();
				/*MyAdapter의 add() 메소드에 MyData 객체를 전달하고,
				거기서, 리스트에 아이템을 add한다.*/
				//어댑터에 데이터 아이템을 추가							  		  //isSend로 전달됨
				mAdapter.add(new MyData(str, 28, "description : " + str, isSendView.isChecked()));
			}
		});
	}

	//데이터 초기화
	private void initData() {
		Log.i(TAG, "initData()");
		String[] arrays = getResources().getStringArray(R.array.listItem); //리소스에서 string array를 get
		Log.d(TAG, "initData(), \n" + arrays[0] + " " + arrays[1] + " " + arrays[2]);

		for (int i = 0; i < arrays.length; i++) {
			String[] item = arrays[i].split(";");  //split() 메소드
			Log.d(TAG, "initData(), " + item[0] + " " + item[1] + " " + item[2]);
			mData.add(new MyData(item[0], Integer.parseInt(item[1]), item[2]));  //리스트에 데이터 추가. 데이터 초기화 및 생성 시점..
		}
	}

}
