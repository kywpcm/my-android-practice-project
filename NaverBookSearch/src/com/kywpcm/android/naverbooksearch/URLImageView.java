package com.kywpcm.android.naverbooksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

//MVC, View..
//item_layout.xml �ȿ� ���� Custom View..
//setImaegURL() �޼ҵ尡 MainActivity�� go ��ư Ŭ���� ����� ���� �Ѵ�.
public class URLImageView extends ImageView {
	private static final String TAG = "URLImageView";

	ImageRequest mRequest;
	Handler mHandler = new Handler();

	//���� �䰡 ������ ��, �ش��ϴ� �����ڷ� ȣ��
	public URLImageView(Context context) {
		super(context);
		Log.e(TAG, "URLImageView(Context context)");
	}
	public URLImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.e(TAG, "URLImageView(Context context, AttributeSet attrs, int defStyle)");
	}
	public URLImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e(TAG, "URLImageView(Context context, AttributeSet attrs)");
	}

	//���ο� ImageRequest ��ü�� �����ϰ�,
	//NetworkModel ��ü�� ���� ��, getNetworkData() �޼ҵ带 ȣ���Ѵ�.
	public void setImaegURL(String image) {
		Log.e(TAG, "setImaegURL(String image) �޼ҵ� ����\n" + "imageUrl is " + image);
		mRequest = null;
		if (image != null && !image.equals("")) {
			mRequest = new ImageRequest(image);
			NetworkModel.getInstance().getNetworkData(mRequest,
					new NetworkRequest.OnProcessCompleteListener() {
				@Override
				public void onCompleted(NetworkRequest request) {
					Log.d(TAG, "onCompleted() �޼ҵ� ����");
					if (mRequest == request) {
						Bitmap bm = (Bitmap) request.getResult();
						setImageBitmap(bm);  //�� View�� ��Ʈ�� �̹����� set
						Log.d(TAG, "ImageView�� ��Ʈ�� �̹��� set");
					}
				}
				@Override
				public void onError(NetworkRequest request,
						String errorMessage) {
					Log.e(TAG, "onError() �޼ҵ� ����");
					//��Ƽ��Ƽ context�� ��� Toast �� ���..
//					Toast.makeText(URLImageView.this, "image set~!", Toast.LENGTH_SHORT).show();
				}

			}, mHandler);
		}
	}

}
