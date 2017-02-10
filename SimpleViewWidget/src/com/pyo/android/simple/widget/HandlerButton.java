package com.pyo.android.simple.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public  class  HandlerButton extends Button{
	private Context context;
	public HandlerButton(Context context){
		super(context);
		this.context = context;
	}
	public HandlerButton(Context context, AttributeSet attrs){
		 super(context, attrs);
		 this.context = context;
	}
	public HandlerButton(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		this.context = context;
	}
	/*
	 * 2번째 우선 순위
	 */
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		if( event.getAction() == MotionEvent.ACTION_DOWN){
		  Toast.makeText(context,"버튼 구현 콜백 이벤트 발생!", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
}