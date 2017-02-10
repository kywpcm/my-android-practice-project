package com.example.networksampleprototype;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//General�� ��Ʈ��ũ ��û Ŭ����
//Specific�� ��û Ŭ�������� ����� ���̴�.
//OnProcessCompleteListener�� �޾Ҵ�.
public abstract class NetworkRequest {
	
	private static final String TAG = "NetworkRequest";
	
	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;

	OnProcessCompleteListener mListener;
	Handler mHandler;

	//Inner �������̽��� on~() �޼ҵ� ����
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	//������ �޾Ƽ� ��� �ʵ忡 �����ϰ�,
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	//�ڵ鷯�� �޾Ƽ� �����Ѵ�.
	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	//�� ��û Ŭ�������� ���� ����ؾ� �� �� ���� ��ɵ�..
	public abstract URL getRequestURL();
	public abstract void parsing(InputStream is) throws ParsingException;
	public abstract Object getResult();
	//���⼭ ����ص� �ǰ�, �ڽ� Ŭ�������� ����ص� �ǰ�..
	public void setRequestProperty(HttpURLConnection conn) {

	}
	public void setOutput(HttpURLConnection conn) {

	}

	//��û ��� set
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

	//�Ľ̵� �ϰ�,
	//�ڵ鷯�� ���۵� �ϰ�,
	//�������� on~() �޼ҵ嵵 ȣ���Ѵ�.
	public void process(InputStream is) {
		Log.d(TAG, "process() �޼ҵ� ����");
		
		try {
			parsing(is);  //�� ��û Ŭ������ �´� parsing
			
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG, "���� ������");
						if (mListener != null) {  
							//������ �޼ҵ� ȣ��                      //�ڱ� �ڽ��� ��ü�� �Ѱ��ش�. => ���� Ȱ���� ���Ͽ�..
							mListener.onCompleted(NetworkRequest.this);  //������ �������̽�(������ ��ü)�� ������ ������..
						}
					}
				});
			}
			
		} catch (ParsingException e) {
			e.printStackTrace();
			notifyError("parsing error");  //�õ��� �����ϸ� error
		}
	}

	public void notifyError(final String errorMessage) {
		Log.d(TAG, "notifyError() �޼ҵ� ����");
		
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "���� ������");
					if (mListener != null) {
						//������ �޼ҵ� ȣ��
						mListener.onError(NetworkRequest.this, errorMessage);  //�̰����� on~() �޼ҵ尡 ��ġ �ݹ� �Լ�ó�� ������ �Ǿ���.
					}
				}
			});
		}
	}

}
