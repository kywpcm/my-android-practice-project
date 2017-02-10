package com.example.networksampleprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

//��û Ŭ����
//Url�� ��û�ϴ� Ŭ����
public class UrlRequest extends NetworkRequest {
	
	private static final String TAG = "UrlRequest";

	URL url;  //URL Ÿ��
	String result;

	//������
	public UrlRequest(String urlText) {
		Log.i(TAG, "UrlRequest ������");
		try {
			url = new URL(urlText);  //������ ��, Url �ް�..
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//��û �Ӽ� set
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
//		conn.setRequestProperty("accept", "text/plain");
	}
	//output set..
	@Override
	public void setOutput(HttpURLConnection conn) {
		super.setOutput(conn);
		//...
	}

	//override�� �߻� �޼ҵ��..
	@Override
	public URL getRequestURL() {
		return url;
	}

	//�Ľ�..
	//BufferedReader ����ؼ� character ������ �������� �о���δ�.
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() �޼ҵ� ����");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			//readLine() �޼ҵ�
			//���� ���� �ؽ�Ʈ�� �д´�. ������ �����ϴ� ���� LF('\n')�� CR('\r') ���� �ִ�.
			//��Ʈ���� ��(EOF)�� �����ϸ� null�� �����Ѵ�.
			while( (line=br.readLine()) != null ) {  //�ݺ��� ������ line�� ���Ӱ� ���ŵȴ�.
				sb.append(line);  //StringBuilder�� �ݺ��ؼ� ����..
				sb.append("\n\r");
			}
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

	@Override
	public String getResult() {
		return result;
	}

}
