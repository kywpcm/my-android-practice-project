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

	//������
	//=> ��Ʈ��ũ ������ �����, �� ��..
	private NetworkModel() {
		Log.i(TAG, "NetworkModel ������");

		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});

		CookieHandler.setDefault(new CookieManager());
	}

	/**
	 *���� ������ ���� ��� �޼ҵ�
	 *@param request ��û�ϴ� ��ü, �ǸŰ������� NetworkRequest�� �޴´�.
	 *@param listener NetworkRequest�� �������̽� OnProcessCompleteListener ������ ��ü
	 *@param handler ���� ���� handler
	 */
	public boolean getNetworkData(final NetworkRequest request, //�θ��� Ÿ������ �޴´�. polymorphism
			NetworkRequest.OnProcessCompleteListener listener, 
			Handler handler) {
		Log.d(TAG, "getNetworkData() ����");
		
		request.setOnProcessCompleteListener(listener);
		request.setHandler(handler);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d(TAG, "getNetworkData(), ��Ʈ��ũ ��û ������, connection ��ü ����");
				
				URL url = request.getRequestURL();
				
				try {
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();  //HttpURLConnection ��ü ���ͼ�,
					
					request.setRequestMethod(conn);  //��û ��� set
					request.setRequestProperty(conn);  //��û �Ӽ� set
					request.setOutput(conn);  //output set
					//Ÿ�Ӿƿ� set
					conn.setConnectTimeout(request.getConnectionTimeout());  
					conn.setReadTimeout(request.getReadTimeout());

					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) { //������ HTTP_OK�̸�,
						Log.e(TAG, "responseCode is " + responseCode);
						
						InputStream is = conn.getInputStream(); //URLConnection ��ü�� ����Ű�� �ִ� ���ҽ��κ��� �����͸� �о�´�.
						request.process(is);  //try�� ��� ����,
						
						return;  //�׸��� return
					} else Log.e(TAG, "responseCode is " + responseCode);
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");
			}
		}).start();
		
		Log.d(TAG, "getNetworkData() ��");
		return true;
	}
}
