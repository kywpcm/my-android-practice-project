package com.kywpcm.android.naverbooksearch;

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

//MVC, Model..
public class NetworkModel {
	private static final String TAG = "NetworkModel";

	private static NetworkModel instance;
	
	private String userName;
	private String password;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		Log.i(TAG, "싱글톤, NetworkModel() 생성자");
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});
		
		CookieHandler.setDefault(new CookieManager());
	}
	
	public boolean getNetworkData(final NetworkRequest request, 
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
				Log.e(TAG, "url is " + url);
				try {
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					request.setRequestMethod(conn);
					request.setRequestProperty(conn);
					request.setOutput(conn);
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						Log.e(TAG, "responseCode is " + HttpURLConnection.HTTP_OK + "=> HTTP_OK");
						InputStream is = conn.getInputStream();
						request.process(is);
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");
			}
		}).start();
		Log.d(TAG, "getNetworkData() 메소드 끝");
		return true;
	}
}
