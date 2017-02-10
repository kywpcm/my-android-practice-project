package com.pyo.android.widget.expension;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public  class DrawView extends View{
	 private Paint paint;
	 public DrawView(Context context) {
	   super(context);
	   viewInit();
    }
	public DrawView(Context context, AttributeSet attrs){
		super(context, attrs);
		viewInit();
	}
	public DrawView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs,defStyle);
		viewInit();
	}
	private  void viewInit(){
		paint = new Paint();
	    paint.setColor(Color.RED);
		paint.setTextSize(28);

		//Paint.ANTI_ALIAS_FLAG : 화면을 좀 더 매끄럽게 그림.
		paint.setAntiAlias(true);
	}
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText("안녕 여러분!", 20, 20, paint);
		//뷰의 디스플레이 공간을 무효화하고 새로 Update 함
	    invalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        //사용자 터치가 일어나면 반응
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	      Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " + 
                         event.getX() + ", " + event.getY(), 1000).show();
        }
        //부모 뷰의 이벤트 처리기능을 유지하기 위해 호출 함.
        return super.onTouchEvent(event);
     }
  }