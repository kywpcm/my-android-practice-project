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

//Activity�� M, V, C�� ��� ����Ѵ�.
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private TextView messageView;
	private EditText inputView;
	private ListView list;

	private MyAdapter mAdapter;
	private List<MyData> mData = new ArrayList<MyData>();  //ArrayList�� �����ϴ� List ��ü ����

	private CheckBox isSendView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		
		setContentView(R.layout.activity_main);

		initData();  //������ �ʱ�ȭ

		messageView = (TextView)findViewById(R.id.message);
		inputView = (EditText)findViewById(R.id.inputText);
		list = (ListView)findViewById(R.id.list);
		isSendView = (CheckBox)findViewById(R.id.isSend);
		
		//����� ����, Control�� Model�� View�� ������ �����Ѵ�.
		mAdapter = new MyAdapter(this, mData); //�����͸� Control���� �����ϰ�, Control�� �����͸� View���� �����Ѵ�.
		list.setAdapter(mAdapter);  //����Ʈ�信 ����͸� ���

		//�׳� �Ϲ����� ListView Ŭ�� ó���� ����  AdapterView OnItemClickListener
		list.setOnItemClickListener(new OnItemClickListener() {
			//����Ͱ� position�� �ִ� �������� get�ؼ� �� �������� name�� TextView�� ������
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d(TAG, "onItemClick ListView...!\n"
						+ "position is " + position);
				MyData item = mAdapter.getItem(position);
				messageView.setText(item.name);
			}
		});
		
		//MyItemView�� �̹����䰡 Ŭ���Ǿ��� ��, � ó���� ���ֱ� ���� ������
		//���⼭�� �佺Ʈ..
		Log.d(TAG, "setOnAdapterItemClickListener() ȣ��");
		//MyAdapter�� �����ϴ� �������̽� ����..
		mAdapter.setOnAdapterItemClickListener(new MyAdapter.OnAdapterItemClickListener() {

			//�޼ҵ��� ���� ������ ���⼭!
			@Override
			public void onAdapterItemClick(MyData data) {  //��ġ �ݹ� �޼ҵ� ó��..
				Log.e(TAG, "onAdapterItemClick() ������ ���⼭");
				Log.d(TAG, "onAdapterItemClick...!");
				Log.d(TAG, "Toast!!!");
				Toast.makeText(MainActivity.this, data.name + "item image clicked...", Toast.LENGTH_SHORT).show();
			}
		});
		Log.d(TAG, "mAdater�� setOnAdapterItemClickListener ��� ��");

		//Button Ŭ�� ó���� ���� View�� OnClickListener
		Button btn = (Button)findViewById(R.id.add);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick Button...!\n"
						+ "isSendView.isChecked() -> " + isSendView.isChecked());
				String str = inputView.getText().toString();
				/*MyAdapter�� add() �޼ҵ忡 MyData ��ü�� �����ϰ�,
				�ű⼭, ����Ʈ�� �������� add�Ѵ�.*/
				//����Ϳ� ������ �������� �߰�							  		  //isSend�� ���޵�
				mAdapter.add(new MyData(str, 28, "description : " + str, isSendView.isChecked()));
			}
		});
	}

	//������ �ʱ�ȭ
	private void initData() {
		Log.i(TAG, "initData()");
		String[] arrays = getResources().getStringArray(R.array.listItem); //���ҽ����� string array�� get
		Log.d(TAG, "initData(), \n" + arrays[0] + " " + arrays[1] + " " + arrays[2]);

		for (int i = 0; i < arrays.length; i++) {
			String[] item = arrays[i].split(";");  //split() �޼ҵ�
			Log.d(TAG, "initData(), " + item[0] + " " + item[1] + " " + item[2]);
			mData.add(new MyData(item[0], Integer.parseInt(item[1]), item[2]));  //����Ʈ�� ������ �߰�. ������ �ʱ�ȭ �� ���� ����..
		}
	}

}
