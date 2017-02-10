package com.example.networkparsersample;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

public class Artists implements SaxParserHandler {
	
	private static final String TAG = "Artists";
	
	//���� �� xml ���������� artists �±� ���� ���� �±׵�
	ArrayList<Artist> artist = new ArrayList<Artist>();

	//inherited abstract method ����..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() �޼ҵ� ����");
		Log.d(TAG, "get tag name is artists..!");
		return "artists";
	}

	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() �޼ҵ� ����");
		if (tagName.equalsIgnoreCase("artist")) {
			Log.d(TAG, "start tag name is artist..!");
			Artist artist = new Artist();
			parser.pushHandler(artist);
		}
		
	}

	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() �޼ҵ� ����");
		if(tagName.equalsIgnoreCase("artist")) {
			Log.d(TAG, "end tag name is artist..!");
			artist.add((Artist)content);
		}
		
	}

	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() �޼ҵ� ����, return this");
		return this;
	}
	
}
