package com.example.databasesample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {

	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.w("MyApplication", "onCreate");
		mContext = this;
	}
	
	public static Context getContext() {
		return mContext;
	}
}
