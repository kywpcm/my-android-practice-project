package com.pyo.android.simple.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class TextViewDisplay extends Activity {
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);      
	     setContentView(R.layout.textview_layout);
	     
	     //�ڵ�� ��Ű���̸� �����ϴ� ���
	     TextView  linkifyAppliedMessage = (TextView)findViewById(R.id.linkify_text);
	     linkifyAppliedMessage.setText(R.string.autolink_text);
	     linkifyAppliedMessage.setTextColor(Color.WHITE);
	     
	     linkifyAppliedMessage.setTypeface(
	    		  Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD | Typeface.ITALIC));
	     int linkedTextColor = Color.argb(255, 0, 255, 255);
	     linkifyAppliedMessage.setLinkTextColor(linkedTextColor);
	     linkifyAppliedMessage.setTextSize(20);
	     Linkify.addLinks(linkifyAppliedMessage, Linkify.ALL);
	  }
}