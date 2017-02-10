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

//ListView�� MVC ������ �������� �����Ǿ� �ְ�, Activity�� �װ��� ����Ѵ�.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	//id ������ ��
	private TextView messageView;
	private EditText inputView;
	private ListView list;

	//MVC ������ ���� ��, Control ������ ����Ͱ� �Ѵ�.
	private ArrayAdapter<MyData> mAdater;  //MyAdapter�� ������ �ʰ�, �׳� ArrayAdapter�� ����Ѵ�.
	//Model ������ MyData��, �װ��� ArrayList�� ��Ƽ� ����Ѵ�.
	private ArrayList<MyData> mData = new ArrayList<MyData>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate()");
		setContentView(R.layout.activity_main);

		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);

		initData();  //������ �ʱ�ȭ

		//����� ����								   //����Ʈ �������� ���̾ƿ� id
		//mAdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
		//mAdater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mData);
		
												 //����Ʈ �������� ���̾ƿ� id       //����Ʈ �������� �ؽ�Ʈ�� id
		mAdater = new ArrayAdapter<MyData>(this, R.layout.list_item_layout, R.id.textView1, mData);  //Control�� Model�� View�� �� ������ �ִ� ��Ȳ
		list.setAdapter(mAdater);  //����Ʈ�� ����� set
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//����Ʈ ������ Ŭ�� ó��
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.i(TAG, "����Ʈ��, onItemClick(), item" + position);
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

		//����Ϳ� �߰��� �������� add
		Button btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "��ư Add, onClick()");
				
				String addString = inputView.getText().toString();
				mAdater.add(new MyData(addString, 30, "desc : added item"));  //���ο� MyData ��ü�� ����Ϳ� �߰�
			}
		});

		//���� btn���� �̸��� ������ �����ϴ� �ּҰ� �ٸ���.
		btn = (Button)findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "��ư Choice, onClick()");

				/* SparseBooleanArray�� �ش� ����Ʈ���� üũ���¸� bool�� �迭�� ��ƿɴϴ�.
				 * ���� ����Ʈ�� 5���� 1,3�� üũ 2,4,5���� üũ�� �ƴ� ���
				 * {true, false, true, false, false}��� �迭�� �ɴϴ�. */
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

	//������ �ʱ�ȭ �޼ҵ�
	private void initData(){
		Log.i(TAG, "initData()");
		String[] arrays = getResources().getStringArray(R.array.list_item); //���ҽ������� String �迭�� �ڹ� �ڵ� ���� arrays�� �����´�.

		//ArrayList�� ������Ʈ�� add
		for(int i = 0; i < arrays.length; i++){
			mData.add(new MyData(arrays[i], 28, "desc : description" + (i+1) ));  //MyData ��ü�� �����Ǵ� ����
		}
	}

}
