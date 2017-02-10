package com.example.networkparsersample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Song implements SaxParserHandler {
	
	private static final String TAG = "Song";
	
	//���� �� xml ���������� song �±� ���� ���� �±׵�
	int songId;
	String songName;
	Artists artists;
	
	//inherited abstract method ����..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() �޼ҵ� ����");
		Log.d(TAG, "get tag name is song..!");
		return "song";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() �޼ҵ� ����");
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
		Log.e(TAG, "parseEndElement() �޼ҵ� ����");
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
		Log.e(TAG, "getParseResult() �޼ҵ� ����, return this");
		return this;
	}
	
	//Song ��ü�� ���� ����Ʈ�� ������ ������ ��ü�̱� ������ toString() �޼ҵ带 override�ؼ�
	//� �����͸� print�� ���� ����
	@Override
	public String toString() {
		return songName;
	}
}
