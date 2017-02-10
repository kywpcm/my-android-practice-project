package com.example.networkparsersample;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Songs implements SaxParserHandler {
	
	private static final String TAG = "Songs";
	
	//가져 올 xml 컨텐츠에서 songs 태그 밑의 하위 태그들
	ArrayList<Song> song = new ArrayList<Song>();

	//inherited abstract method 구현..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() 메소드 시작");
		Log.d(TAG, "get tag name is songs..!");
		return "songs";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("song")) {  //start 태그 네임이 "song"과 일치하면,
			Log.d(TAG, "start tag name is song..!");
			Song song = new Song();  //Song 타입 클래스의 song 객체에게
			parser.pushHandler(song);  //파싱을 push한다. 할 일을 위임한다.
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("song")) {  //end 태그 네임이 "song"과 일치하면,
			Log.d(TAG, "end tag name is song..!");
			song.add((Song)content);  //content를 가져온다.
		}
		
	}

	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() 메소드 시작, return this");
		return this;
	}
}
