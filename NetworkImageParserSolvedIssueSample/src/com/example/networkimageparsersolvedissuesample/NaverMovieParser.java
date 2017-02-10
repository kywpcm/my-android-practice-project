package com.example.networkimageparsersolvedissuesample;

import com.example.networkimageparsersolvedissuesample.parser.SaxParserHandler;
import com.example.networkimageparsersolvedissuesample.parser.SaxResultParser;

public class NaverMovieParser extends SaxResultParser {

	NaverMovies movies;
	
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		// TODO Auto-generated method stub
		movies = new NaverMovies();
		return movies;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return movies;
	}

}
