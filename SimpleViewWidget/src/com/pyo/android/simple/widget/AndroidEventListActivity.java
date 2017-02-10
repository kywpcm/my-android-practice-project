package com.pyo.android.simple.widget;

public class AndroidEventListActivity extends BaseActivity{
	@Override
	public void displayActivityList() {
	    addActionMap("3.1.뷰 콜백 이벤트구현" , SimpleViewCallBackEventActivity.class);
	    addActionMap("3.2.액티비티 콜백 이벤트 구현" ,ActivityCallbackEventActivity .class);
	    addActionMap("3.3.이벤트우선순위샘플",EventPriorityActivity.class);
	    addActionMap("3.4.터치이벤트고급",GestureTouchEventActivity.class);
	}
}