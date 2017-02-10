package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//��Ʈ��ũ ��û�� ���� ���� Ŭ����
public abstract class NetworkRequest {
	
	private static final String TAG = "NetworkRequest";
	
	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;
	
	OnProcessCompleteListener mListener;
	Handler mHandler;
	
	//������ �������̽� ���� �� �޼ҵ� ����
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	//������ set �޼ҵ� ����
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	//�ڽ� Ŭ�������� ����� �߻� �޼ҵ��..
	public abstract URL getRequestURL();
	public abstract void parsing(InputStream is) throws ParsingException;
	public abstract Object getResult();
	
	//��û ��� set..
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
	
	//parsing() �޼ҵ� ȣ��
	public void process(InputStream is) {
		Log.d(TAG, "process() �޼ҵ� ����");
		
		try {
			
			parsing(is);  //���� �� ��û Ŭ�������� parsing..
			
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Log.d(TAG, "���� ������");
						if (mListener != null) {
							mListener.onCompleted(NetworkRequest.this);  //������ �޼ҵ� ȣ��
						}
					}
				});
			}
		} catch (ParsingException e) {
			e.printStackTrace();
			notifyError("parsing error");  //�����ϸ� notifyError() �޼ҵ� ȣ��
		}
	}
	
	public void notifyError(final String errorMessage) {
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "���� ������");
					if (mListener != null) {
						mListener.onError(NetworkRequest.this, errorMessage);
					}
				}
			});
		}
	}

}
