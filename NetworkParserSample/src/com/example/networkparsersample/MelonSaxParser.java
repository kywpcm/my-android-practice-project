package com.example.networkparsersample;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

//SaxResultParser Ŭ������ ����� Ŭ����
public class MelonSaxParser extends SaxResultParser {
	private static final String TAG = "MelonSaxParser";

	Melon melon;
	
	//abstract �޼ҵ�
	//SaxResultParser�� doParse() �޼ҵ� �ȿ��� ȣ��ȴ�.
	//������ ���⼭..
	//Melon�� �θ� Ÿ���� SaxParserHandler�� ����
	@Override
	protected SaxParserHandler getFirstSaxParserHandler() {
		Log.e(TAG, "getFirstSaxParserHandler() �޼ҵ� ����");
		melon = new Melon();
		return melon;
	}

	//SaxResultParser�� �θ��� InputStreamParser�� abstract �޼ҵ�
	//���� ����� �������� �޼ҵ�
	@Override
	public Object getResult() {
		Log.d(TAG, "getResult() �޼ҵ� ����");
		return melon;
	}

}
