package com.example.networkimageparsersolvedissuesample;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.networkimageparsersolvedissuesample.parser.SaxParserHandler;
import com.example.networkimageparsersolvedissuesample.parser.SaxResultParser;

public class NaverMovies implements SaxParserHandler {

	String title;
	ArrayList<NaverMovieItem> items = new ArrayList<NaverMovieItem>();
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "channel";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("item")) {
			NaverMovieItem item = new NaverMovieItem();
			parser.pushHandler(item);
		} else if (tagName.equalsIgnoreCase("a")) {
			String imageurl = attributes.getValue("img");
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		// TODO Auto-generated method stub
		if (tagName.equalsIgnoreCase("title")) {
			title = (String)content;
		} else if (tagName.equalsIgnoreCase("item")) {
			items.add((NaverMovieItem)content);
		}
		
	}

	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}

}
