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
				//show(FragmentManager manager, String tag) 메소드
				//show(FragmentTransaction transaction, String tag) 메소드와 같으나
				//프래그먼트가 백스택에 들어가지 않는다.
				f.show(getSupportFragmentManager(), "myDialog");
				//				f.show(getSupportFragmentManager().beginTransaction(), "myDialog");
			}
		});
	}

	//레이아웃 xml의 버튼 뷰 onclick 속성에 mOnClick 메소드 명시
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			//나중에 setStyle 메소드 호출 시, theme 인자 0은 style 인자에 어울리는 테마를 안드로이드가 자동으로 잡아준다.
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
		FragmentManager fm = getSupportFragmentManager();  //android.support.v4의 프래그먼트 매니저 구하는 메소드
		//그냥(android.app)은 getFragmentManager() 메소드..
		FragmentTransaction tr = fm.beginTransaction();
		
		//이전에 "dialog"라는 태그의 프래그먼트가 있으면 제거
		//프래그먼트 다이얼로그가 중복해서 뜨는 것을 막기 위해서.. 
		Fragment prev = fm.findFragmentByTag("dialog");
		if(prev != null) {
			tr.remove(prev);
		}
		
		//프래그먼트가 생성되지도 않았는데 백스택에 추가?
		//아직 트랜잭션이 commit 하지 않았기 때문에 순서는 별 의미가 없다..
		//commit 시, 한꺼번에 작업 이루어짐
		tr.addToBackStack(null);
		
		MyDialogFragment newFragment = MyDialogFragment.newInstance(style, theme);
		newFragment.show(tr, "dialog");  //트랜잭션에 생성된 프래그먼트를 add하고, commit까지 자동으로..
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
