package com.pyo.android.simple.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SimpleViewCallBackEventActivity extends Activity {
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   
	   ViewCallBackMethodImpl viewCallBack = new ViewCallBackMethodImpl(this);
	   LinearLayout rootLayout = new LinearLayout(this);
	   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			   ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
       rootLayout.setLayoutParams(params);
       viewCallBack.setLayoutParams(params);
       viewCallBack.setHint(" 뷰 콜백 메소드 ");
       viewCallBack.setTextSize(20);
       viewCallBack.setTypeface(Typeface.DEFAULT_BOLD);
       rootLayout.addView(viewCallBack);
	   setContentView(rootLayout);
   }
   //재정의 해야 만 함
   private class ViewCallBackMethodImpl extends EditText{
       public  ViewCallBackMethodImpl(Context content){
    	   super(content);
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
    				   keyCode , 2000).show();  
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
}
