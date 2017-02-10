package com.example.customlistsample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//View
public class MyItemView extends FrameLayout {

	private static final String TAG = "MyItemView";

	private ImageView iconView;
	private TextView nameView;
	private TextView descView;
	private Drawable happy, neutral, sad;

	private MyData mData;
	private boolean isChecked;

	private OnItemImageClickListener mListener;
	//�������̽� ���ǿ� �޼ҵ� ������ ���⼭
	public interface OnItemImageClickListener {
		public void onItemImageClick(MyData data);
	}
	//������ set�ϴ� �޼ҵ�
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
		Log.d(TAG, "setOnItemImageClickListener()\n" +
				"MyItemView�� ��� ���� mListener = listener");
	}

	public MyItemView(Context context) {
		super(context);
		Log.i(TAG, "MyItemView(Context context) ������");
		
		//context�� Activity�κ��� ���޵� �� context
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);

		//getView�� �����ִ� MyItemView�� ImageView Ŭ�� ó���� ���� ������ ���
		iconView = (ImageView)findViewById(R.id.imageView1);
		iconView.setOnClickListener(new View.OnClickListener() {  //�����ڿ��� �� Ŭ�� �����ʸ� �̹� ���..

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick iconView...!");

				if (mListener != null) {
					Log.d(TAG, "mListener is not null");
					Log.e(TAG, "OnItemImageClickListener �������̽���\n" +
							"onItemImageClick() ȣ��\n" +
							"������ ����� ���� �Ǿ��ְ�, ������ ->");
					mListener.onItemImageClick(mData);  //mListener�� is not null�� ��, onItemImageClick() �޼ҵ� ȣ��..
				}
			}
		});
		nameView = (TextView)findViewById(R.id.textView1);
		descView = (TextView)findViewById(R.id.textView2);

		happy = context.getResources().getDrawable(R.drawable.stat_happy);
		neutral = context.getResources().getDrawable(R.drawable.stat_neutral);
		sad = context.getResources().getDrawable(R.drawable.stat_sad);
	}

	public void setMyData(MyData data) {
		Log.i(TAG, "setMyData()");

		mData = data;
		nameView.setText(data.name);  //view�� ������ �����͸� set�Ѵ�.
		descView.setText(data.desc);
		if (data.age < 25) {
			iconView.setImageDrawable(happy);
		} else if (data.age >= 25 && data.age < 30) {
			iconView.setImageDrawable(neutral);
		} else {
			iconView.setImageDrawable(sad);
		}
		isChecked=false;
		redrawBackground();
	}

	//isChecked�� ���� ���� BackgroundColor ����
	private void redrawBackground() {
		Log.i(TAG, "redrawBackground()");
		if (isChecked) {
			Log.e(TAG, "redrawBackground(), isChecked is " + isChecked);
			setBackgroundColor(Color.RED);
		} else {
			Log.e(TAG, "redrawBackground(), isChecked is " + isChecked);
			setBackgroundColor(Color.CYAN);
		}
	}

	///////////////////////////////////////////
	//	���� ������Ʈ������ ������� ����
	//	@Override
	//	public boolean isChecked() {
	//		
	//		return isChecked;
	//	}
	//
	//	@Override
	//	public void setChecked(boolean checked) {
	//		isChecked = checked;
	//		redrawBackground();
	//	}
	//
	//	@Override
	//	public void toggle() {
	//		isChecked = !isChecked;
	//		redrawBackground();
	//	}
	////////////////////////////////////////////

}
