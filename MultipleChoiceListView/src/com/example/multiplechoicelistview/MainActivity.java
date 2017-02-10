package com.example.multiplechoicelistview;

import java.util.ArrayList;

import com.example.multiplechoicelistview.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//ListView는 MVC 디자인 패턴으로 구현되어 있고, Activity는 그것을 사용한다.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	//id 가져올 것
	private TextView messageView;
	private EditText inputView;
	private ListView list;

	//MVC 디자인 패턴 중, Control 역할을 어댑터가 한다.
	private ArrayAdapter<MyData> mAdater;  //MyAdapter를 만들지 않고, 그냥 ArrayAdapter를 사용한다.
	//Model 역할을 MyData가, 그것을 ArrayList에 담아서 사용한다.
	private ArrayList<MyData> mData = new ArrayList<MyData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate()");
		setContentView(R.layout.activity_main);

		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);

		initData();  //데이터 초기화

		//어댑터 생성								   //리스트 아이템의 레이아웃 id
		//mAdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
		//mAdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mData);
		
												 //리스트 아이템의 레이아웃 id       //리스트 아이템의 텍스트뷰 id
		mAdater = new ArrayAdapter<MyData>(this, R.layout.list_item_layout, R.id.textView1, mData);  //Control이 Model과 View를 다 가지고 있는 상황
		list.setAdapter(mAdater);  //리스트에 어댑터 set
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//리스트 아이템 클릭 처리
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.i(TAG, "리스트뷰, onItemClick(), item" + position);
				MyData item = mAdater.getItem(position);
				String str = item.name;
				messageView.setText(str);
				Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

//				view.setBackgroundResource(R.drawable.item_selector);
//				view.setBackgroundColor(0xff000000);
				view.setBackgroundResource(R.color.ics);
//				view.setFocusable(true);
			}

		});

//		list.setSelector(R.color.ics);
//		list.setDrawSelectorOnTop(false);

		//어댑터에 추가로 아이템을 add
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "버튼 Add, onClick()");
				
				String addString = inputView.getText().toString();
				mAdater.add(new MyData(addString, 30, "desc : added item"));  //새로운 MyData 객체를 어댑터에 추가
			}
		});

		//위의 btn과는 이름은 같지만 참조하는 주소가 다르다.
		btn = (Button)findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "버튼 Choice, onClick()");

				/* SparseBooleanArray는 해당 리스트뷰의 체크상태를 bool값 배열로 담아옵니다.
				 * 예로 리스트가 5개고 1,3번 체크 2,4,5번이 체크가 아닐 경우
				 * {true, false, true, false, false}라는 배열이 옵니다. */
				SparseBooleanArray selectedArray = list.getCheckedItemPositions();
				Log.d(TAG, "onClick(), SparseBooleanArray selectedArray\n"
						+ "{" + selectedArray + "}");
				StringBuilder sb = new StringBuilder();
				sb.append("selected items : ");

				for(int i = 0; i < mAdater.getCount(); i++){
					boolean isSelected = selectedArray.get(i);
					Log.d(TAG, "boolean isSelected = " + isSelected);
					if(isSelected){
						sb.append(mAdater.getItem(i) + ", ");
					}
				}
				messageView.setText(sb.toString());
			}
		});
	}

	//데이터 초기화 메소드
	private void initData(){
		Log.i(TAG, "initData()");
		String[] arrays = getResources().getStringArray(R.array.list_item); //리소스형태의 String 배열을 자바 코드 상의 arrays로 가져온다.

		//ArrayList에 오브젝트를 add
		for(int i = 0; i < arrays.length; i++){
			mData.add(new MyData(arrays[i], 28, "desc : description" + (i+1) ));  //MyData 객체가 생성되는 시점
		}
	}

}
