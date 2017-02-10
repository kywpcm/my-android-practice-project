package com.kywpcm.android.naverbooksearch;

import android.util.Log;

import com.kywpcm.android.naverbooksearch.parser.SaxParserHandler;
import com.kywpcm.android.naverbooksearch.parser.SaxResultParser;

public class NaverBookSaxParser extends SaxResultParser {
	private static final String TAG = "NaverMovieSaxParser";
	
	NaverBooks books;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() 메소드 시작");
		books = new NaverBooks();
		return books;
	}

	@Override
	public NaverBooks getResult() {
		Log.e(TAG, "getResult() 메소드 시작, NaverMovies books 객체 리턴");
		return books;
	}

}
