package com.example.networkparsersample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Artist implements SaxParserHandler {
	
	private static final String TAG = "Artist";

	//가져 올 xml 컨텐츠에서 artist 태그 밑의 하위 태그들
	int artistId;
	String artistName;
	
	//inherited abstract method 구현..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() 메소드 시작");
		Log.d(TAG, "get tag name is artist..!");
		return "artist";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() 메소드 시작");
		//여기는 할 일 없음..
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("artistId")) {
			Log.d(TAG, "end tag name is artistId..!");
			this.artistId = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("artistName")) {
			Log.d(TAG, "end tag name is artistName..!");
			this.artistName = (String)content;
		}
		
	}
	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() 메소드 시작, return this");
		return this;
	}
	@Override
	public String toString() {
		return artistId + "\n" + artistName;
	}
	
}
