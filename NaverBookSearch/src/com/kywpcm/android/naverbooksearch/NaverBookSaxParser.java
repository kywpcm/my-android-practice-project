package com.kywpcm.android.naverbooksearch;

import android.util.Log;

import com.kywpcm.android.naverbooksearch.parser.SaxParserHandler;
import com.kywpcm.android.naverbooksearch.parser.SaxResultParser;

public class NaverBookSaxParser extends SaxResultParser {
	private static final String TAG = "NaverMovieSaxParser";
	
	NaverBooks books;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() �޼ҵ� ����");
		books = new NaverBooks();
		return books;
	}

	@Override
	public NaverBooks getResult() {
		Log.e(TAG, "getResult() �޼ҵ� ����, NaverMovies books ��ü ����");
		return books;
	}

}
