package com.pyo.android.simple.widget;

public class WidgetFormDisplay extends BaseActivity{

	@Override
	public void displayActivityList() {
	    addActionMap("2.1.�Է���" , TextInputActivity.class);
	    addActionMap("2.2.Ű�е��Է�",KeyPadInputTypeActivity.class);
	    addActionMap("2.3.Ű�е�����",KeyPadControlActivity.class);
	    addActionMap("2.4.�ڵ��ϼ����",AutoCompleteTextViewActivity.class);
	    addActionMap("2.5.�̹��������",ImageViewActivity.class);
	    addActionMap("2.6.��ư������" , ButtonKindActivity.class);
	    addActionMap("2.7.�����ع�������",QuickContactBadgeActivity.class);
	    addActionMap("2.8.��¥�Է�/���", DateTimePicker.class);
	}
}