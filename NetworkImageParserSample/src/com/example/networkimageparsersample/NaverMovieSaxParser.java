package com.example.networkimageparsersample;

import android.util.Log;

import com.example.networkimageparsersample.parser.SaxParserHandler;
import com.example.networkimageparsersample.parser.SaxResultParser;

public class NaverMovieSaxParser extends SaxResultParser {
	private static final String TAG = "NaverMovieSaxParser";
	
	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() 메소드 시작");
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public NaverMovies getResult() {
		Log.e(TAG, "getResult() 메소드 시작, NaverMovies movies 객체 리턴");
		return movies;
	}

}
