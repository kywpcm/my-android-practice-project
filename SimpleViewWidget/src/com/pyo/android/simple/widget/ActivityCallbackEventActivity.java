package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class ActivityCallbackEventActivity extends Activity{
		   @Override
		   public void onCreate(Bundle savedInstanceState){
			   super.onCreate(savedInstanceState);			   
			   setContentView(R.layout.activity_callback_layout);
		   }
		   //터치 이벤트 재정의
		   @Override
		   public boolean onTouchEvent(MotionEvent event){
		       super.onTouchEvent(event);
		       int eventCode = event.getAction();
		    	   if( eventCode == MotionEvent.ACTION_DOWN){
		    	     Toast.makeText(getApplicationContext(), " 터치 이벤트 발생 좌표( X = " 
		    				     + event.getX() + ",Y = " + event.getY() +" )", 2000).show();
		           }
		    	return true;
		    }
		    //키 이벤트 재정의
		       @Override
		       public boolean onKeyDown(int keyCode, KeyEvent event){
		    	   super.onKeyDown(keyCode, event);
		    	   if(keyCode == KeyEvent.KEYCODE_BACK){
		    	      Toast.makeText(getApplicationContext(), " 백키 누름  keyCode = "  + 
		    				   keyCode + ")" , 2000).show(); 
		    	      finish();
		    	   }
		    	   return true;
		       }
		       //키 이벤트 재정의
		       @Override
		       public boolean onKeyUp(int keyCode, KeyEvent event){
		    	   super.onKeyDown(keyCode, event);
		    	   Toast.makeText(getApplicationContext(), " 키  업 이벤트 발생 ( keyCode = "  + 
		    				   keyCode +")" , 2000).show();  
		    	   return true;
		       }
		       //트랙볼 정의
		       @Override
		       public boolean onTrackballEvent(MotionEvent event){
		    	  super.onTrackballEvent(event);
		          Toast.makeText(getApplicationContext(), " 트랙볼 이벤트 발생 좌표( X = " 
		    				     + event.getX() + ",Y = " + event.getY( ) + ")", 2000).show();
		    	   return true;
		        }
}