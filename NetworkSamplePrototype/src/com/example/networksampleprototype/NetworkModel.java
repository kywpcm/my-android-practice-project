package com.example.networksampleprototype;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

//Model..
public class NetworkModel {
	
	private static final String TAG = "NetworkModel";

	private String userName;
	private String password;

	private static NetworkModel instance;
	
	//Single Tone Pattern
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	//생성자
	//=> 네트워크 데이터 얻기전, 할 일..
	private NetworkModel() {
		Log.i(TAG, "NetworkModel 생성자");

		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});

		CookieHandler.setDefault(new CookieManager());
	}

	/**
	 *실제 데이터 모델을 얻는 메소드
	 *@param request 요청하는 객체, 실매개변수는 NetworkRequest로 받는다.
	 *@param listener NetworkRequest의 인터페이스 OnProcessCompleteListener 리스너 객체
	 *@param handler 전달 받은 handler
	 */
	public boolean getNetworkData(final NetworkRequest request, //부모의 타입으로 받는다. polymorphism
			NetworkRequest.OnProcessCompleteListener listener, 
			Handler handler) {
		Log.d(TAG, "getNetworkData() 시작");
		
		request.setOnProcessCompleteListener(listener);
		request.setHandler(handler);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d(TAG, "getNetworkData(), 네트워크 요청 스레드, connection 객체 오픈");
				
				URL url = request.getRequestURL();
				
				try {
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();  //HttpURLConnection 객체 얻어와서,
					
					request.setRequestMethod(conn);  //요청 방식 set
					request.setRequestProperty(conn);  //요청 속성 set
					request.setOutput(conn);  //output set
					//타임아웃 set
					conn.setConnectTimeout(request.getConnectionTimeout());  
					conn.setReadTimeout(request.getReadTimeout());

					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) { //응답이 HTTP_OK이면,
						Log.e(TAG, "responseCode is " + responseCode);
						
						InputStream is = conn.getInputStream(); //URLConnection 객체가 가리키고 있는 리소스로부터 데이터를 읽어온다.
						request.process(is);  //try문 모두 성공,
						
						return;  //그리고 return
					} else Log.e(TAG, "responseCode is " + responseCode);
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");
			}
		}).start();
		
		Log.d(TAG, "getNetworkData() 끝");
		return true;
	}
}
