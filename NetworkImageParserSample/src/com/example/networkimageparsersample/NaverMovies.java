package com.example.networkimageparsersample;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.networkimageparsersample.parser.SaxParserHandler;
import com.example.networkimageparsersample.parser.SaxResultParser;

public class NaverMovies implements SaxParserHandler {

	String title;
	String description;
	ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();
	
	@Override
	public String getTagName() {
		return "channel";  //처음으로 찾는 태그 네임은 "channel"이다.
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("item")) {
			NaverMovieItem item = new NaverMovieItem();
			parser.pushHandler(item);
		}
		
	}
	
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("title")) {
			this.title = (String)content;
		} else if (tagName.equalsIgnoreCase("description")) {
			this.description = (String)content;
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((NaverMovieItem)content);
		}
	}
	
	@Override
	public Object getParseResult() {
		return this;
	}
	
}
