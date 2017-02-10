package com.kywpcm.android.naverbooksearch;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.kywpcm.android.naverbooksearch.parser.SaxParserHandler;
import com.kywpcm.android.naverbooksearch.parser.SaxResultParser;

public class NaverBookItem implements SaxParserHandler {
	private static final String TAG = "NaverBookItem";

	String title;
	String image;
	String author;
	
	@Override
	public String getTagName() {
		return "item";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("title")) {
			String str = (String)content;
			Log.e(TAG, "content str is " + str);
			String goodStr = str.replace("<b>", "");
			Log.e(TAG, "goodStr1 is " + goodStr);
			goodStr = goodStr.replace("</b>", "");
			Log.e(TAG, "goodStr2 is " + goodStr);
			this.title = goodStr;
		} else if (tagName.equalsIgnoreCase("image")) {
			this.image = (String)content;
		} else if (tagName.equalsIgnoreCase("author")) {
			this.author = (String)content;
		}
	}
	
	@Override
	public Object getParseResult() {
		return this;
	}
}
