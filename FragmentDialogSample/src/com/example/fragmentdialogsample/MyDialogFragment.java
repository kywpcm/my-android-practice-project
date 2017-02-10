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
		Log.i(TAG, "MyDialogFragment() ������");
	}

	public static MyDialogFragment newInstance(int style, int theme) {
		Log.i(TAG, "newInstance() �޼ҵ�");

		MyDialogFragment df = new MyDialogFragment();

		Bundle args = new Bundle();
		args.putInt("style", style);
		args.putInt("theme", theme);
		df.setArguments(args);  //���߿� getArguments() �޼ҵ�� �������� ���� ��ü�� ������ ������ �� �ִ�.

		return df;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate() �޼ҵ�");
		
		if(getTag() != "myDialog") {
			int style = getArguments().getInt("style");
			int theme = getArguments().getInt("theme");

			//���� setStyle() �޼ҵ� ȣ��..
			//setStyle() �޼ҵ�� ��ȭ���ڰ� �����Ǳ� ��(show �ϱ� ��)�� setting �Ǿ� �Ѵ�.
			setStyle(style, theme);	
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView() �޼ҵ�");

		//������ ����� �ƴ�, ��Ÿ�� �� �� ����
		//�����׸�Ʈ�� id�� ����
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

	//������ ���̾�α� ���� ����� �״�� �� �� �ִ�.
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
