package com.kywpcm.android.chatwithcarlyraejepsen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//View..
public class SendItemView extends FrameLayout {
	private static final String TAG = "MyItemView";

	ImageView iconView;
	TextView nameView;
	TextView messageView;

	ItemData mData;
	Boolean isSend;  //MainActivity�� isSend�ʹ� �ٸ��� View���� bool ���� ���� �۾��� ó���ϱ� ���ؼ�..

	public SendItemView(Context context) {
		super(context);
		Log.i(TAG, "SendItemView(Context context) ������");

		//context�� Activity�κ��� ���޵� �� context
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.send_item_layout, this);

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

		nameView = (TextView)findViewById(R.id.myName);
		messageView = (TextView)findViewById(R.id.myMessage);

	}

	public void setItemData(ItemData data) {
		Log.i(TAG, "setItemData()");

		mData = data;
		messageView.setText(data.message);
		isSend=true;
		redrawBackground();
	}

	//isSend�� ���� ���� BackgroundColor ����
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
