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
		Log.i(TAG, "ReceiveItemView(Context context) ������");

		//context�� Activity�κ��� ���޵� �� context
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.receive_item_layout, this);

		/*//getView�� �����ִ� MyItemView�� ImageView Ŭ�� ó���� ���� ������ ���
		iconView = (ImageView)findViewById(R.id.imageView1);
		iconView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onClick iconView...!");
				if (mListener != null) {
					Log.d(TAG, "mListener is not null");
					Log.e(TAG, "OnItemImageClickListener �������̽���\n" +
							"onItemImageClick() ȣ��\n" +
							"������ ����� ���� �Ǿ��ְ�, ������ ->");
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

	//isChecked�� ���� ���� BackgroundColor ����
	//���⼭ �׸��� ���� �ƴ�
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
