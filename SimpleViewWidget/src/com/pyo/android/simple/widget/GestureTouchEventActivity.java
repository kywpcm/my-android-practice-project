package com.pyo.android.simple.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GestureTouchEventActivity extends Activity {
	private String mSaveText = null;
    private GestureDetector mGestures = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestures != null) {
            return mGestures.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.gesture_event_layout);
        
        //EditText ��ü
        final EditText textEvents = (EditText)findViewById(R.id.last_event_text);
        
        //ScrollView Layout Object  
        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.events_screen);
        //�۷ι� �̺�Ʈ(layout��ü)�� �߻� ��Ű�� ����
        ViewTreeObserver viewTreeObserver =  rootLayout.getViewTreeObserver();
        viewTreeObserver.addOnTouchModeChangeListener(
        		      new ViewTreeObserver.OnTouchModeChangeListener() {
          public void onTouchModeChanged( boolean isInTouchMode) {
        	  textEvents.setText("��ġ ��� �����ϴ� ����?  " + isInTouchMode);
          }
        });
        viewTreeObserver.addOnGlobalFocusChangeListener(
        		new ViewTreeObserver.OnGlobalFocusChangeListener() {
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (oldFocus != null && newFocus != null) {
                	textEvents.setText("��Ŀ���̵� ���� \nfrom: " + 
                    	oldFocus.getClass().getName() + "   \nto: " + newFocus.getClass().getName());    
                }
            }
        });
        final Button longPress = (Button)findViewById(R.id.long_press);
        longPress.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
               	textEvents.setText("��Ŭ�������� : " + v.getClass().getName());
                return true;
            }      
        });         
        mGestures = new GestureDetector( 
        		new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, 
            		               float velocityX,float velocityY) {
            	textEvents.setText("�ø� ������ �߻� ! \nx= " + 
                		                  velocityX + "px/s,\ny="+velocityY + "px/s");        
                return super.onFling(e1, e2, velocityX, velocityY);
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, 
            		                          float distanceX, float distanceY) {
            textEvents.setText("��ũ�� ������! \nX=" + distanceX +"\nY = " +distanceY);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });     
        final EditText focusChange = (EditText)findViewById(R.id.text_focus_change);
        focusChange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {           
                if (hasFocus) {
                    if (mSaveText != null) {
                        ((TextView)v).setText(mSaveText);
                    }
                }else{
                    mSaveText = ((TextView)v).getText().toString();
                    ((TextView)v).setText("");             
                }
            }         
        });             
    }
}