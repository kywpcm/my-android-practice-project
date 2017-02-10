package com.example.androidcustomintentsample;
import java.io.IOException;


public class Activity {

	public void startActivityForResult(Intent intent) {
		try {
			Class loadClass = intent.getClassName();
			InternetViewResAction action = (InternetViewResAction)loadClass.newInstance();
			action.excuteAction(intent.getReqActivity(), intent.getContentMaps());
		} catch (InstantiationException ie) {
			System.err.println(ie);
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			System.err.println(iae);
			iae.printStackTrace();
		} catch (IOException io) {
			System.err.println(io);
			io.printStackTrace();
		}
	}
	
}
