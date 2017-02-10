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
		Log.i(TAG, "NetworkModel() ������");
		
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
		Log.d(TAG, "getNetworkData() �޼ҵ� ����");
		
		request.setOnProcessCompleteListener(listener);
		request.setHandler(handler);
		
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				Log.d(TAG, "getNetworkData(), ��Ʈ��ũ ��û ������");
				
				URL url = request.getRequestURL();
				try {
					//Ŀ�ؼ� ��ü ����
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					
					request.setRequestMethod(conn);
					request.setRequestProperty(conn);  //request ��� �ʵ� �Ӽ� set
					request.setOutput(conn);
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) { //������ HTTP_OK�̸�,
						
						InputStream is = conn.getInputStream();
						request.process(is);  //try�� ��� �����ϸ� process
						
						return;  //�׸��� ���⼭ ����
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");  //�ƴϸ� notifyError
			}
		}).start();
		
		Log.d(TAG, "getNetworkData() �޼ҵ� ��");
		return true;
	}
}
