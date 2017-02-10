package com.kywpcm.android.naverbooksearch;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

import com.kywpcm.android.naverbooksearch.parser.InputStreamParserException;

//요청 클래스
//네이버 영화 검색 Open API 사용
//parsing..
public class NaverBookRequest extends NetworkRequest {
	private static final String TAG = "NaverMovieRequest";
	
	String keyword;
	
	public NaverBookRequest(String keyword) {
		Log.i(TAG, "NaverMovieRequest(\"" + keyword + "\") 생성자");
		this.keyword = keyword;
	}
	
	@Override
	public URL getRequestURL() {
		Log.d(TAG, "getRequestURL() 메소드 시작");
		try {
			//네이버 영화 검색 Open API
			String urlString = "http://openapi.naver.com/search?key=eec2c621d17bfa0d1e625d59bca50c15"
					+ "&query=" + URLEncoder.encode(keyword, "utf-8") + "&display=10&start=1&target=book";
			URL url = new URL(urlString);
			return url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	NaverBooks books;
	@Override
	public void parsing(InputStream is) throws ParsingException {
		Log.d(TAG, "parsing() 메소드 시작");
		NaverBookSaxParser parser = new NaverBookSaxParser();
		try {
			parser.doParse(is);
			books = parser.getResult();
		} catch (InputStreamParserException e) {
			e.printStackTrace();
		}

	}

	@Override
	public NaverBooks getResult() {
		Log.d(TAG, "getResult() 메소드 시작, NaverMovies books 객체 return");
		return books;
	}

}
