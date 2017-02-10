package com.example.multiplechoicelistview;

import android.util.Log;

//Model
public class MyData {
	private static final String TAG = "MyData";

	public String name;
	private int age;
	private String desc;  //description
	
	public MyData() {
		Log.i(TAG, "MyData() �⺻ ������ ȣ��");
	}
	
	public MyData(String name, int age, String desc){
		Log.i(TAG, "MyData(String name, int age, String desc) ������ ȣ��");
		
		this.name = name;
		this.age = age;
		this.desc = desc;
	}

	@Override
	public String toString() {
		Log.i(TAG, "toString()");
		return name + "(" + age + ")" + "\n\r" + desc;
	}
	
}
