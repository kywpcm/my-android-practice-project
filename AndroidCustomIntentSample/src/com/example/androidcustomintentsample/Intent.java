package com.example.androidcustomintentsample;
import java.util.HashMap;


public class Intent {

	private InternetViewReqActivity reqActivity;
	private Class className;
	private HashMap<String, String> contentMaps;
	
	public Intent(InternetViewReqActivity reqActivity, Class className) {
		this.reqActivity = reqActivity;
		this.className = className;
		contentMaps = new HashMap<String, String>();
	}

	public InternetViewReqActivity getReqActivity() {
		return reqActivity;
	}

	public Class getClassName() {
		return className;
	}

	public HashMap<String, String> getContentMaps() {
		return contentMaps;
	}
	
	public void setExtras(String key, String value) {
		contentMaps.put(key, value);
	}
	
}
