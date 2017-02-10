package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.networkparsersample.parser.GsonResultParser;
import com.example.networkparsersample.parser.InputStreamParserException;

//멜론 실시간 차트 json으로 요청하는 클래스
//마찬가지로 NetworkRequest 클래스 상속
public class MelonJsonRequest extends NetworkRequest {
	private static final String TAG = "MelonJsonRequest";
	
	public MelonJsonRequest() {
		Log.i(TAG, "MelonJsonRequest 생성자");
	}
	
	@Override
	public URL getRequestURL() {
		URL url = null;
		try {
			//SKP 멜론 실시간 차트 Open API
			url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	//request 헤더 필드 속성 set
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
		
		//request 헤더 필드의 속성을 set하는 메소드
		//(field, value) 순서쌍
		conn.setRequestProperty("Accept", "application/json");  //json 형태..
		//App Key 있는 url => https://developers.skplanetx.com/my-center/app-station/
		conn.setRequestProperty("appKey", "fdbb2b9a-9164-352d-8339-eef338967b63");
	}
	
	Melon melon;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		
		//GsonResultParser<T> 사용..
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
