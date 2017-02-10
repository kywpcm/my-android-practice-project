package com.example.networkimageparsersolvedissuesample;

import com.example.networkimageparsersolvedissuesample.parser.SaxParserHandler;
import com.example.networkimageparsersolvedissuesample.parser.SaxResultParser;

public class NaverBookParser extends SaxResultParser {

	NaverBooks books;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		books = new NaverBooks();
		return books;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return books;
	}

}
