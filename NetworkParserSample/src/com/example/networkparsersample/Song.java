package com.example.networkparsersample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Song implements SaxParserHandler {
	
	private static final String TAG = "Song";
	
	//가져 올 xml 컨텐츠에서 song 태그 밑의 하위 태그들
	int songId;
	String songName;
	Artists artists;
	
	//inherited abstract method 구현..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() 메소드 시작");
		Log.d(TAG, "get tag name is song..!");
		return "song";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("artists")) {
			Log.d(TAG, "start tag name is artists..!");
			Artists artists = new Artists();
			parser.pushHandler(artists);
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("songId")) {
			Log.d(TAG, "end tag name is songId..!");
			this.songId = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("songName")) {
			Log.d(TAG, "end tag name is songName..!");
			this.songName = (String)content;
		} else if (tagName.equalsIgnoreCase("artists")) {
			Log.d(TAG, "end tag name is artists..!");
			artists = (Artists)content;
		}
	}
	
	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() 메소드 시작, return this");
		return this;
	}
	
	//Song 객체는 실제 리스트에 보여질 데이터 객체이기 때문에 toString() 메소드를 override해서
	//어떤 데이터를 print할 지를 결정
	@Override
	public String toString() {
		return songName;
	}
}
