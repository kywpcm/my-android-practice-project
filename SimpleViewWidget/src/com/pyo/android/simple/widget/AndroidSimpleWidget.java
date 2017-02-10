package com.pyo.android.simple.widget;

public class AndroidSimpleWidget extends BaseActivity {
	public final static String DEBUG_TAG = 
		 "AndroidSimpleWidget";
    @Override
	public void displayActivityList() {
		addActionMap("1.TextView ", TextViewDisplay.class);
		addActionMap("2.Form UI ",WidgetFormDisplay.class);
		addActionMap("3.Event√≥∏Æ ", AndroidEventListActivity.class);
	}
}