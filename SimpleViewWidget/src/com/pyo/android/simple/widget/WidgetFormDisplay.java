package com.pyo.android.simple.widget;

public class WidgetFormDisplay extends BaseActivity{

	@Override
	public void displayActivityList() {
	    addActionMap("2.1.입력폼" , TextInputActivity.class);
	    addActionMap("2.2.키패드입력",KeyPadInputTypeActivity.class);
	    addActionMap("2.3.키패드제어",KeyPadControlActivity.class);
	    addActionMap("2.4.자동완성기능",AutoCompleteTextViewActivity.class);
	    addActionMap("2.5.이미지뷰샘플",ImageViewActivity.class);
	    addActionMap("2.6.버튼류샘플" , ButtonKindActivity.class);
	    addActionMap("2.7.퀵컨텍뱃지샘플",QuickContactBadgeActivity.class);
	    addActionMap("2.8.날짜입력/출력", DateTimePicker.class);
	}
}