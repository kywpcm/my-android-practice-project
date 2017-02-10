package com.example.networksampleprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

//요청 클래스
//Url을 요청하는 클래스
public class UrlRequest extends NetworkRequest {
	
	private static final String TAG = "UrlRequest";

	URL url;  //URL 타입
	String result;

	//생성자
	public UrlRequest(String urlText) {
		Log.i(TAG, "UrlRequest 생성자");
		try {
			url = new URL(urlText);  //생성할 때, Url 받고..
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//요청 속성 set
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

	//override한 추상 메소드들..
	@Override
	public URL getRequestURL() {
		return url;
	}

	//파싱..
	//BufferedReader 사용해서 character 단위의 라인으로 읽어들인다.
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			//readLine() 메소드
			//라인 별로 텍스트를 읽는다. 라인을 구분하는 것은 LF('\n')나 CR('\r') 등이 있다.
			//스트림의 끝(EOF)에 도달하면 null을 리턴한다.
			while( (line=br.readLine()) != null ) {  //반복할 때마다 line은 새롭게 갱신된다.
				sb.append(line);  //StringBuilder에 반복해서 붙임..
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
