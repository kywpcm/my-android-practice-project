package com.example.networkparsersample;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Songs implements SaxParserHandler {
	
	private static final String TAG = "Songs";
	
	//���� �� xml ���������� songs �±� ���� ���� �±׵�
	ArrayList<Song> song = new ArrayList<Song>();

	//inherited abstract method ����..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() �޼ҵ� ����");
		Log.d(TAG, "get tag name is songs..!");
		return "songs";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() �޼ҵ� ����");
		if (tagName.equalsIgnoreCase("song")) {  //start �±� ������ "song"�� ��ġ�ϸ�,
			Log.d(TAG, "start tag name is song..!");
			Song song = new Song();  //Song Ÿ�� Ŭ������ song ��ü����
			parser.pushHandler(song);  //�Ľ��� push�Ѵ�. �� ���� �����Ѵ�.
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() �޼ҵ� ����");
		if (tagName.equalsIgnoreCase("song")) {  //end �±� ������ "song"�� ��ġ�ϸ�,
			Log.d(TAG, "end tag name is song..!");
			song.add((Song)content);  //content�� �����´�.
		}
		
	}

	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() �޼ҵ� ����, return this");
		return this;
	}
}
