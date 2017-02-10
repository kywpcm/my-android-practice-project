package com.example.customlistsample;

import android.util.Log;

//Model
public class MyData {
	private static final String TAG = "MyData";
	
	String name;
	int age;
	String desc;
	boolean isSend = false;
	
	public MyData() {
		//...
	}
	
	public MyData(String name, int age, String desc) {
		this(name, age, desc, false);  //�̷� ������ �־���
//		Log.i(TAG, "MyData(String name, int age, String desc) ������");
	}
	
	public MyData(String name, int age, String desc, boolean isSend) {
		Log.i(TAG, "MyData(String name, int age, String desc, boolean isSend) ������");
		Log.e(TAG, "isSend is " + isSend);
		
		this.name = name;
		this.age = age;
		this.desc = desc;
		this.isSend = isSend;
	}
	
}
