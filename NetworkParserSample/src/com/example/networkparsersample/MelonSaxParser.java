package com.example.networkparsersample;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

//SaxResultParser 클래스를 상속한 클래스
public class MelonSaxParser extends SaxResultParser {
	private static final String TAG = "MelonSaxParser";

	Melon melon;
	
	//abstract 메소드
	//SaxResultParser의 doParse() 메소드 안에서 호출된다.
	//구현은 여기서..
	//Melon의 부모 타입인 SaxParserHandler로 리턴
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() 메소드 시작");
		melon = new Melon();
		return melon;
	}

	//SaxResultParser의 부모인 InputStreamParser의 abstract 메소드
	//최종 결과를 가져오는 메소드
	@Override
	public Object getResult() {
		Log.d(TAG, "getResult() 메소드 시작");
		return melon;
	}

}
