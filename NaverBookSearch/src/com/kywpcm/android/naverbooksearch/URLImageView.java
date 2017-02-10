package com.kywpcm.android.naverbooksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

//MVC, View..
//item_layout.xml 안에 들어가는 Custom View..
//setImaegURL() 메소드가 MainActivity의 go 버튼 클릭과 비슷한 역할 한다.
public class URLImageView extends ImageView {
	private static final String TAG = "URLImageView";

	ImageRequest mRequest;
	Handler mHandler = new Handler();

	//상위 뷰가 생성될 때, 해당하는 생성자로 호출
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

	//새로운 ImageRequest 객체를 생성하고,
	//NetworkModel 객체를 얻은 뒤, getNetworkData() 메소드를 호출한다.
	public void setImaegURL(String image) {
		Log.e(TAG, "setImaegURL(String image) 메소드 시작\n" + "imageUrl is " + image);
		mRequest = null;
		if (image != null && !image.equals("")) {
			mRequest = new ImageRequest(image);
			NetworkModel.getInstance().getNetworkData(mRequest,
					new NetworkRequest.OnProcessCompleteListener() {
				@Override
				public void onCompleted(NetworkRequest request) {
					Log.d(TAG, "onCompleted() 메소드 시작");
					if (mRequest == request) {
						Bitmap bm = (Bitmap) request.getResult();
						setImageBitmap(bm);  //이 View에 비트맵 이미지를 set
						Log.d(TAG, "ImageView에 비트맵 이미지 set");
					}
				}
				@Override
				public void onError(NetworkRequest request,
						String errorMessage) {
					Log.e(TAG, "onError() 메소드 시작");
					//액티비티 context가 없어서 Toast 못 띄움..
//					Toast.makeText(URLImageView.this, "image set~!", Toast.LENGTH_SHORT).show();
				}

			}, mHandler);
		}
	}

}
