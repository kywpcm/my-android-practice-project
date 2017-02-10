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
	//인터페이스 정의와 메소드 선언은 여기서
	public interface OnItemImageClickListener {
		public void onItemImageClick(MyData data);
	}
	//리스너 set하는 메소드
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
		Log.d(TAG, "setOnItemImageClickListener()\n" +
				"MyItemView의 멤버 변수 mListener = listener");
	}

	public MyItemView(Context context) {
		super(context);
		Log.i(TAG, "MyItemView(Context context) 생성자");
		
		//context는 Activity로부터 전달돼 온 context
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);

		//getView가 보여주는 MyItemView의 ImageView 클릭 처리를 위한 리스너 등록
		iconView = (ImageView)findViewById(R.id.imageView1);
		iconView.setOnClickListener(new View.OnClickListener() {  //생성자에서 온 클릭 리스너를 이미 등록..

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick iconView...!");

				if (mListener != null) {
					Log.d(TAG, "mListener is not null");
					Log.e(TAG, "OnItemImageClickListener 인터페이스의\n" +
							"onItemImageClick() 호출\n" +
							"하지만 여기는 선언만 되어있고, 구현은 ->");
					mListener.onItemImageClick(mData);  //mListener가 is not null일 때, onItemImageClick() 메소드 호출..
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
		nameView.setText(data.name);  //view에 적절한 데이터를 set한다.
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

	//isChecked에 따른 뷰의 BackgroundColor 변경
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
	//	현재 프로젝트에서는 사용하지 않음
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
