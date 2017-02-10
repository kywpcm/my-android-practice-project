package com.kywpcm.android.chatwithcarlyraejepsen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiveItemView extends FrameLayout {
	private static final String TAG = "MyItemView";

	ImageView iconView;
	TextView nameView;
	TextView messageView;

	ItemData mData;
	Boolean isSend;

	public ReceiveItemView(Context context) {
		super(context);
		Log.i(TAG, "ReceiveItemView(Context context) 생성자");

		//context는 Activity로부터 전달돼 온 context
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.receive_item_layout, this);

		/*//getView가 보여주는 MyItemView의 ImageView 클릭 처리를 위한 리스너 등록
		iconView = (ImageView)findViewById(R.id.imageView1);
		iconView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onClick iconView...!");
				if (mListener != null) {
					Log.d(TAG, "mListener is not null");
					Log.e(TAG, "OnItemImageClickListener 인터페이스의\n" +
							"onItemImageClick() 호출\n" +
							"하지만 여기는 선언만 되어있고, 구현은 ->");
					mListener.onItemImageClick(mData);
				}
			}
		});*/

		nameView = (TextView)findViewById(R.id.carlyName);
		messageView = (TextView)findViewById(R.id.carlyMessage);

	}

	public void setItemData(ItemData data) {
		Log.d(TAG, "setItemData()");

		mData = data;
		Log.e(TAG, "data.message = " + data.message);
		messageView.setText(data.message);
		isSend=false;
		redrawBackground();
	}

	//isChecked에 따른 뷰의 BackgroundColor 변경
	//여기서 그리는 것은 아님
	private void redrawBackground() {
//		Log.d(TAG, "redrawBackground()");
//		if (isSend) {
//			Log.d(TAG, "redrawBackground(), isSend is " + isSend);
//			setBackgroundColor(Color.RED);
//		} else {
//			Log.d(TAG, "redrawBackground(), isSend is " + isSend);
//			setBackgroundColor(Color.CYAN);
//		}
	}
}
