package com.example.networkimageparsersample;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

//��û Ŭ����
//parsing..
public class ImageRequest extends NetworkRequest {
	private static final String TAG = "ImageRequest";
	
	String imageUrl;
	
	public ImageRequest(String imageUrl) {
		Log.i(TAG, "ImageRequest(String imageUrl) ������");
		this.imageUrl = imageUrl;
	}
	
	@Override
	public URL getRequestURL() {
		Log.d(TAG, "getRequestURL() �޼ҵ� ����");
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
		Log.d(TAG, "parsing() �޼ҵ� ����");
		mBitmap = BitmapFactory.decodeStream(is);
	}

	@Override
	public Object getResult() {
		Log.d(TAG, "getResult() �޼ҵ� ����");
		return mBitmap;
	}

}
