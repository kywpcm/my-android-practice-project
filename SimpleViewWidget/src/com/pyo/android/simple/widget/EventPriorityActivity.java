package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class EventPriorityActivity extends Activity{
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.event_handler_layout);
		HandlerButton  priorityHandler = (HandlerButton)findViewById(R.id.btn_prioriry_handler);
		priorityHandler.setOnTouchListener(new View.OnTouchListener() {
			/*
	         * 1��° �̺�Ʈ �߻�
			 */
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if( event.getAction() == MotionEvent.ACTION_DOWN){
				    Toast.makeText(getApplicationContext(),"��ư ��ġ ������ Ŭ����!", 
				    		 Toast.LENGTH_SHORT).show();
				    //true�� �����ϸ� ���⼭ ���� ��
				     //return true;
				}
				return false;
			}	
		});
	}
	/*
     * 3��° �̺�Ʈ �߻�
	 */
	   @Override
	   public boolean onTouchEvent(MotionEvent event){
	       super.onTouchEvent(event);
	       int eventCode = event.getAction();
	       if( eventCode == MotionEvent.ACTION_DOWN){
	    	  Toast.makeText(getApplicationContext(), " ��Ƽ��Ƽ ��ġ �߻� ��ǥ( X = " 
	    				+ event.getX() + ",Y = " + event.getY() +" )", 1000).show();
	       }
	       return true;
	  }
}