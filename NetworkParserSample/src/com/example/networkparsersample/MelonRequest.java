package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.networkparsersample.parser.InputStreamParserException;

//��� �ǽð� ��Ʈ xml�� ��û�ϴ� Ŭ����
//���������� NetworkRequest Ŭ���� ���
public class MelonRequest extends NetworkRequest {
	private static final String TAG = "MelonRequest";
	
	public MelonRequest() {
		Log.i(TAG, "MelonRequest() ������");
	}

	@Override
	public URL getRequestURL() {
		URL url = null;
		try {
			//SKP ��� �ǽð� ��Ʈ Open API
			url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "URL ��ü ��� ���� ����");
		return url;
	}
	
	//request ��� �ʵ� �Ӽ� set
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
		//request ��� �ʵ��� �Ӽ��� set�ϴ� �޼ҵ�
		//(field, value) ������
		conn.setRequestProperty("Accept", "application/xml"); //xml ����..
		//App Key �ִ� url => https://developers.skplanetx.com/my-center/app-station/
		conn.setRequestProperty("appKey", "fdbb2b9a-9164-352d-8339-eef338967b63");
	}

	Melon melon;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() �޼ҵ� ����");
		MelonSaxParser parser = new MelonSaxParser();  //���� Melon ��ü �������� ����..
		try {
			parser.doParse(is);
			melon = (Melon)parser.getResult();
		} catch (InputStreamParserException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

	@Override
	public Melon getResult() {
		return melon;
	}

}
