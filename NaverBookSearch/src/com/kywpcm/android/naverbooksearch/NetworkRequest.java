package com.kywpcm.android.naverbooksearch;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//부모 요청 클래스
//process..
public abstract class NetworkRequest {
	private static final String TAG = "NetworkRequest";

	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;
	
	OnProcessCompleteListener mListener;
	Handler mHandler;
	
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	public abstract URL getRequestURL();
	public abstract void parsing(InputStream is) throws ParsingException;
	public abstract Object getResult();
	
	public void setRequestMethod(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	public void setRequestProperty(HttpURLConnection conn) {
		
	}
	
	public void setOutput(HttpURLConnection conn) {
		
	}
	
	public int getConnectionTimeout() {
		return CONNECTION_TIME_OUT;
	}
	
	public int getReadTimeout() {
		return READ_TIME_OUT;
	}
	
	public void process(InputStream is) {
		Log.d(TAG, "process() 메소드 시작");
		try {
			parsing(is);  //parsing..!
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						Log.d(TAG, "메인 스레드");
						if (mListener != null) {
							mListener.onCompleted(NetworkRequest.this);
						}
					}
				});
			}
		} catch (ParsingException e) {
			e.printStackTrace();
			notifyError("parsing error");
		}
	}
	
	public void notifyError(final String errorMessage) {
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					Log.d(TAG, "메인 스레드");
					if (mListener != null) {
						mListener.onError(NetworkRequest.this,errorMessage);
					}
				}
			});
		}
	}

}
