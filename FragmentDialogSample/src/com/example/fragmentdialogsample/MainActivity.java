package com.example.fragmentdialogsample;

import com.example.fragmentdialogsample.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	public static final int DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button)findViewById(R.id.showDialog);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MyDialogFragment f = new MyDialogFragment();
				//show(FragmentManager manager, String tag) �޼ҵ�
				//show(FragmentTransaction transaction, String tag) �޼ҵ�� ������
				//�����׸�Ʈ�� �齺�ÿ� ���� �ʴ´�.
				f.show(getSupportFragmentManager(), "myDialog");
				//				f.show(getSupportFragmentManager().beginTransaction(), "myDialog");
			}
		});
	}

	//���̾ƿ� xml�� ��ư �� onclick �Ӽ��� mOnClick �޼ҵ� ���
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			//���߿� setStyle �޼ҵ� ȣ�� ��, theme ���� 0�� style ���ڿ� ��︮�� �׸��� �ȵ���̵尡 �ڵ����� ����ش�.
			showDialog(DialogFragment.STYLE_NORMAL, 0);
			break;
		case R.id.button2:
			showDialog(DialogFragment.STYLE_NO_TITLE, 0);
			break;
		case R.id.button3:
			showDialog(DialogFragment.STYLE_NO_FRAME, 0);
			break;
		case R.id.button4:
			showDialog(DialogFragment.STYLE_NO_INPUT, 0);
			break;
		case R.id.button5:
			showDialog(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo);
			break;
		case R.id.button6:
			showDialog(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
			break;
		case R.id.button7:
			showDialog(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light);
			break;
		case R.id.button8:
			showDialog(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Panel);
			break;
		}
	}
	
	void showDialog(int style, int theme) {
		FragmentManager fm = getSupportFragmentManager();  //android.support.v4�� �����׸�Ʈ �Ŵ��� ���ϴ� �޼ҵ�
		//�׳�(android.app)�� getFragmentManager() �޼ҵ�..
		FragmentTransaction tr = fm.beginTransaction();
		
		//������ "dialog"��� �±��� �����׸�Ʈ�� ������ ����
		//�����׸�Ʈ ���̾�αװ� �ߺ��ؼ� �ߴ� ���� ���� ���ؼ�.. 
		Fragment prev = fm.findFragmentByTag("dialog");
		if(prev != null) {
			tr.remove(prev);
		}
		
		//�����׸�Ʈ�� ���������� �ʾҴµ� �齺�ÿ� �߰�?
		//���� Ʈ������� commit ���� �ʾұ� ������ ������ �� �ǹ̰� ����..
		//commit ��, �Ѳ����� �۾� �̷����
		tr.addToBackStack(null);
		
		MyDialogFragment newFragment = MyDialogFragment.newInstance(style, theme);
		newFragment.show(tr, "dialog");  //Ʈ����ǿ� ������ �����׸�Ʈ�� add�ϰ�, commit���� �ڵ�����..
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
