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
		   //��ġ �̺�Ʈ ������
		   @Override
		   public boolean onTouchEvent(MotionEvent event){
		       super.onTouchEvent(event);
		       int eventCode = event.getAction();
		    	   if( eventCode == MotionEvent.ACTION_DOWN){
		    	     Toast.makeText(getApplicationContext(), " ��ġ �̺�Ʈ �߻� ��ǥ( X = " 
		    				     + event.getX() + ",Y = " + event.getY() +" )", 2000).show();
		           }
		    	return true;
		    }
		    //Ű �̺�Ʈ ������
		       @Override
		       public boolean onKeyDown(int keyCode, KeyEvent event){
		    	   super.onKeyDown(keyCode, event);
		    	   if(keyCode == KeyEvent.KEYCODE_BACK){
		    	      Toast.makeText(getApplicationContext(), " ��Ű ����  keyCode = "  + 
		    				   keyCode + ")" , 2000).show(); 
		    	      finish();
		    	   }
		    	   return true;
		       }
		       //Ű �̺�Ʈ ������
		       @Override
		       public boolean onKeyUp(int keyCode, KeyEvent event){
		    	   super.onKeyDown(keyCode, event);
		    	   Toast.makeText(getApplicationContext(), " Ű  �� �̺�Ʈ �߻� ( keyCode = "  + 
		    				   keyCode +")" , 2000).show();  
		    	   return true;
		       }
		       //Ʈ���� ����
		       @Override
		       public boolean onTrackballEvent(MotionEvent event){
		    	  super.onTrackballEvent(event);
		          Toast.makeText(getApplicationContext(), " Ʈ���� �̺�Ʈ �߻� ��ǥ( X = " 
		    				     + event.getX() + ",Y = " + event.getY( ) + ")", 2000).show();
		    	   return true;
		        }
}