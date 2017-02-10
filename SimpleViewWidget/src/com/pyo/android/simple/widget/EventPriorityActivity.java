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
	         * 1번째 이벤트 발생
			 */
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if( event.getAction() == MotionEvent.ACTION_DOWN){
				    Toast.makeText(getApplicationContext(),"버튼 터치 리스너 클래스!", 
				    		 Toast.LENGTH_SHORT).show();
				    //true를 리턴하면 여기서 종료 됨
				     //return true;
				}
				return false;
			}	
		});
	}
	/*
     * 3번째 이벤트 발생
	 */
	   @Override
	   public boolean onTouchEvent(MotionEvent event){
	       super.onTouchEvent(event);
	       int eventCode = event.getAction();
	       if( eventCode == MotionEvent.ACTION_DOWN){
	    	  Toast.makeText(getApplicationContext(), " 액티비티 터치 발생 좌표( X = " 
	    				+ event.getX() + ",Y = " + event.getY() +" )", 1000).show();
	       }
	       return true;
	  }
}