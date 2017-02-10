package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//네트워크 요청에 대한 상위 클래스
public abstract class NetworkRequest {
	
	private static final String TAG = "NetworkRequest";
	
	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;
	
	OnProcessCompleteListener mListener;
	Handler mHandler;
	
	//리스너 인터페이스 정의 및 메소드 선언
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	//리스너 set 메소드 정의
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	//자식 클래스에서 사용할 추상 메소드들..
	public abstract URL getRequestURL();
	public abstract void parsing(InputStream is) throws ParsingException;
	public abstract Object getResult();
	
	//요청 방식 set..
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
	
	//parsing() 메소드 호출
	public void process(InputStream is) {
		Log.d(TAG, "process() 메소드 시작");
		
		try {
			
			parsing(is);  //실제 각 요청 클래스마다 parsing..
			
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG, "메인 스레드");
						if (mListener != null) {
							mListener.onCompleted(NetworkRequest.this);  //리스너 메소드 호출
						}
					}
				});
			}
		} catch (ParsingException e) {
			e.printStackTrace();
			notifyError("parsing error");  //실패하면 notifyError() 메소드 호출
		}
	}
	
	public void notifyError(final String errorMessage) {
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "메인 스레드");
					if (mListener != null) {
						mListener.onError(NetworkRequest.this, errorMessage);
					}
				}
			});
		}
	}

}
