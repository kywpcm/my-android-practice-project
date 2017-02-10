package com.example.networkimageparsersample;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

import com.example.networkimageparsersample.parser.InputStreamParserException;

//요청 클래스
//네이버 영화 검색 Open API 사용
//parsing..
public class NaverMovieRequest extends NetworkRequest {
	private static final String TAG = "NaverMovieRequest";
	
	String keyword;
	
	public NaverMovieRequest(String keyword) {
		Log.i(TAG, "NaverMovieRequest(\"" + keyword + "\") 생성자");
		this.keyword = keyword;
	}
	
	@Override
	public URL getRequestURL() {
		Log.d(TAG, "getRequestURL() 메소드 시작");
		try {
			//네이버 영화 검색 Open API
			String urlString = "http://openapi.naver.com/search?key=eec2c621d17bfa0d1e625d59bca50c15"
					+ "&query=" + URLEncoder.encode(keyword, "utf-8") + "&display=10&start=1&target=movie";
			URL url = new URL(urlString);
			return url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	NaverMovies movies;
	
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		NaverMovieSaxParser parser = new NaverMovieSaxParser();
		try {
			parser.doParse(is);
			movies = parser.getResult();
		} catch (InputStreamParserException e) {
			e.printStackTrace();
		}

	}

	@Override
	public NaverMovies getResult() {
		Log.d(TAG, "getResult() 메소드 시작, NaverMovies movies 객체 return");
		return movies;
	}

}
