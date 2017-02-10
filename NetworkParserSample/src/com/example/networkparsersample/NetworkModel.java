package com.example.networkparsersample;

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

public class NetworkModel {
	
	private static final String TAG = "NetworkModel";
	
	private String userName;
	private String password;
	
	private static NetworkModel instance;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		Log.i(TAG, "NetworkModel() 생성자");
		
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});
		
		CookieHandler.setDefault(new CookieManager());
	}
	
	//
	public boolean getNetworkData(final NetworkRequest request,  //polymorphysm..
			NetworkRequest.OnProcessCompleteListener listener, 
			Handler handler) {
		Log.d(TAG, "getNetworkData() 메소드 시작");
		
		request.setOnProcessCompleteListener(listener);
		request.setHandler(handler);
		
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				Log.d(TAG, "getNetworkData(), 네트워크 요청 스레드");
				
				URL url = request.getRequestURL();
				try {
					//커넥션 객체 오픈
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					
					request.setRequestMethod(conn);
					request.setRequestProperty(conn);  //request 헤더 필드 속성 set
					request.setOutput(conn);
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) { //응답이 HTTP_OK이면,
						
						InputStream is = conn.getInputStream();
						request.process(is);  //try가 모두 성공하면 process
						
						return;  //그리고 여기서 리턴
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");  //아니면 notifyError
			}
		}).start();
		
		Log.d(TAG, "getNetworkData() 메소드 끝");
		return true;
	}
}
