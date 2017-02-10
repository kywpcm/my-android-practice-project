package com.example.networkimageparsersample;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

//요청 클래스
//parsing..
public class ImageRequest extends NetworkRequest {
	private static final String TAG = "ImageRequest";
	
	String imageUrl;
	
	public ImageRequest(String imageUrl) {
		Log.i(TAG, "ImageRequest(String imageUrl) 생성자");
		this.imageUrl = imageUrl;
	}
	
	@Override
	public URL getRequestURL() {
		Log.d(TAG, "getRequestURL() 메소드 시작");
		try {
			URL url = new URL(imageUrl);
			return url;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	Bitmap mBitmap;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		mBitmap = BitmapFactory.decodeStream(is);
	}

	@Override
	public Object getResult() {
		Log.d(TAG, "getResult() 메소드 시작");
		return mBitmap;
	}

}
