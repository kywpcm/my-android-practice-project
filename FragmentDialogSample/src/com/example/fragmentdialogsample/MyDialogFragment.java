package com.example.fragmentdialogsample;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {

	private static final String TAG = "MyDialogFragment";

	public MyDialogFragment() {
		Log.i(TAG, "MyDialogFragment() 생성자");
	}

	public static MyDialogFragment newInstance(int style, int theme) {
		Log.i(TAG, "newInstance() 메소드");

		MyDialogFragment df = new MyDialogFragment();

		Bundle args = new Bundle();
		args.putInt("style", style);
		args.putInt("theme", theme);
		df.setArguments(args);  //나중에 getArguments() 메소드로 언제든지 번들 객체의 내용을 꺼내올 수 있다.

		return df;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate() 메소드");
		
		if(getTag() != "myDialog") {
			int style = getArguments().getInt("style");
			int theme = getArguments().getInt("theme");

			//드디어 setStyle() 메소드 호출..
			//setStyle() 메소드는 대화상자가 생성되기 전(show 하기 전)에 setting 되야 한다.
			setStyle(style, theme);	
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView() 메소드");

		//선언적 기법이 아닌, 런타임 시 뷰 생성
		//프래그먼트의 id가 없음
		TextView tv = new TextView(getActivity());
		tv.setText("MyDialog");
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		setCancelable(true);
		return tv;
	}

	//기존의 다이얼로그 생성 방법도 그대로 쓸 수 있다.
	//	@Override
	//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//			builder.setIcon(R.drawable.ic_launcher);
//			builder.setTitle("My Dialog");
//			builder.setMessage("My Dialog Message");
//			builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Toast.makeText(getActivity(), "clicked YES", Toast.LENGTH_SHORT).show();
//				}
//			});
//			builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					//...
//				}
//			});
//			builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					//...
//				}
//			});
//			
//			AlertDialog dlg = builder.create();
//			return dlg;
	//	}

}
