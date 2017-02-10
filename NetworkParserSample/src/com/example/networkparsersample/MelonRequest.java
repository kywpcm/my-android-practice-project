package com.example.networkparsersample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.networkparsersample.parser.InputStreamParserException;

//멜론 실시간 차트 xml로 요청하는 클래스
//마찬가지로 NetworkRequest 클래스 상속
public class MelonRequest extends NetworkRequest {
	private static final String TAG = "MelonRequest";
	
	public MelonRequest() {
		Log.i(TAG, "MelonRequest() 생성자");
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
		Log.d(TAG, "URL 객체 얻어 오기 성공");
		return url;
	}
	
	//request 헤더 필드 속성 set
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
		//request 헤더 필드의 속성을 set하는 메소드
		//(field, value) 순서쌍
		conn.setRequestProperty("Accept", "application/xml"); //xml 형태..
		//App Key 있는 url => https://developers.skplanetx.com/my-center/app-station/
		conn.setRequestProperty("appKey", "fdbb2b9a-9164-352d-8339-eef338967b63");
	}

	Melon melon;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		MelonSaxParser parser = new MelonSaxParser();  //아직 Melon 객체 생성되지 않음..
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
