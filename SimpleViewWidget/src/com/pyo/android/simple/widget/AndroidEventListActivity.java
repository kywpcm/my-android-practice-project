package com.pyo.android.simple.widget;

public class AndroidEventListActivity extends BaseActivity{
	@Override
	public void displayActivityList() {
	    addActionMap("3.1.�� �ݹ� �̺�Ʈ����" , SimpleViewCallBackEventActivity.class);
	    addActionMap("3.2.��Ƽ��Ƽ �ݹ� �̺�Ʈ ����" ,ActivityCallbackEventActivity .class);
	    addActionMap("3.3.�̺�Ʈ�켱��������",EventPriorityActivity.class);
	    addActionMap("3.4.��ġ�̺�Ʈ���",GestureTouchEventActivity.class);
	}
}