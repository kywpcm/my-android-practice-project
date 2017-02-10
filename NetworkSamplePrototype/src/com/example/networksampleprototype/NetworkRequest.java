package com.example.networksampleprototype;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//General한 네트워크 요청 클래스
//Specific한 요청 클래스에서 상속할 것이다.
//OnProcessCompleteListener를 달았다.
public abstract class NetworkRequest {
	
	private static final String TAG = "NetworkRequest";
	
	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;

	OnProcessCompleteListener mListener;
	Handler mHandler;

	//Inner 인터페이스와 on~() 메소드 선언
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	//리스너 받아서 멤버 필드에 저장하고,
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	//핸들러도 받아서 저장한다.
	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	//각 요청 클래스들이 따로 사용해야 할 것 같은 기능들..
	public abstract URL getRequestURL();
	public abstract void parsing(InputStream is) throws ParsingException;
	public abstract Object getResult();
	//여기서 사용해도 되고, 자식 클래스에서 사용해도 되고..
	public void setRequestProperty(HttpURLConnection conn) {

	}
	public void setOutput(HttpURLConnection conn) {

	}

	//요청 방식 set
	public void setRequestMethod(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}

	public int getConnectionTimeout() {
		return CONNECTION_TIME_OUT;
	}

	public int getReadTimeout() {
		return READ_TIME_OUT;
	}

	//파싱도 하고,
	//핸들러로 전송도 하고,
	//리스너의 on~() 메소드도 호출한다.
	public void process(InputStream is) {
		Log.d(TAG, "process() 메소드 시작");
		
		try {
			parsing(is);  //각 요청 클래스에 맞는 parsing
			
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG, "메인 스레드");
						if (mListener != null) {  
							//리스너 메소드 호출                      //자기 자신의 객체를 넘겨준다. => 이후 활용을 위하여..
							mListener.onCompleted(NetworkRequest.this);  //구현은 인터페이스(리스너 객체)를 생성한 곳에서..
						}
					}
				});
			}
			
		} catch (ParsingException e) {
			e.printStackTrace();
			notifyError("parsing error");  //시도가 실패하면 error
		}
	}

	public void notifyError(final String errorMessage) {
		Log.d(TAG, "notifyError() 메소드 시작");
		
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "메인 스레드");
					if (mListener != null) {
						//리스너 메소드 호출
						mListener.onError(NetworkRequest.this, errorMessage);  //이것으로 on~() 메소드가 마치 콜백 함수처럼 구현이 되었다.
					}
				}
			});
		}
	}

}
