package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.networkparsersample.parser.GsonResultParser;
import com.example.networkparsersample.parser.InputStreamParserException;

//��� �ǽð� ��Ʈ json���� ��û�ϴ� Ŭ����
//���������� NetworkRequest Ŭ���� ���
public class MelonJsonRequest extends NetworkRequest {
	private static final String TAG = "MelonJsonRequest";
	
	public MelonJsonRequest() {
		Log.i(TAG, "MelonJsonRequest ������");
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
		return url;
	}
	
	//request ��� �ʵ� �Ӽ� set
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
		
		//request ��� �ʵ��� �Ӽ��� set�ϴ� �޼ҵ�
		//(field, value) ������
		conn.setRequestProperty("Accept", "application/json");  //json ����..
		//App Key �ִ� url => https://developers.skplanetx.com/my-center/app-station/
		conn.setRequestProperty("appKey", "fdbb2b9a-9164-352d-8339-eef338967b63");
	}
	
	Melon melon;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() �޼ҵ� ����");
		
		//GsonResultParser<T> ���..
		GsonResultParser<DummyMelon> parser = new GsonResultParser<DummyMelon>(DummyMelon.class);
		try {
			parser.doParse(is);
			DummyMelon dm = parser.getResult();
			melon = dm.melon;
		} catch (InputStreamParserException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Melon getResult() {
		return melon;
	}

}
