package com.kywpcm.android.chatwithcarlyraejepsen;

import android.util.Log;

//Model..
public class ItemData {
	private static final String TAG = "MyData";
	
	String message;
	boolean isSend = false;
	
	ItemData() {
		Log.i(TAG, "ItemData() �⺻������");
	}
	
	ItemData(String message, boolean isSend) {
		Log.i(TAG, "MyData(String message, boolean isSend) ������\n" + "isSend is" + isSend);
		this.message = message;
		this.isSend = isSend;
	}
	
}
