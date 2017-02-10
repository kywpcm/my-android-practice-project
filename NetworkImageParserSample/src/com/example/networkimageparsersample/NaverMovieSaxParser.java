package com.example.networkimageparsersample;

import android.util.Log;

import com.example.networkimageparsersample.parser.SaxParserHandler;
import com.example.networkimageparsersample.parser.SaxResultParser;

public class NaverMovieSaxParser extends SaxResultParser {
	private static final String TAG = "NaverMovieSaxParser";
	
	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() �޼ҵ� ����");
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public NaverMovies getResult() {
		Log.e(TAG, "getResult() �޼ҵ� ����, NaverMovies movies ��ü ����");
		return movies;
	}

}
