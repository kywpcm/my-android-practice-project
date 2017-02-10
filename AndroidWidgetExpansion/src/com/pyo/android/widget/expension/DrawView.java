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

		//Paint.ANTI_ALIAS_FLAG : ȭ���� �� �� �Ų����� �׸�.
		paint.setAntiAlias(true);
	}
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText("�ȳ� ������!", 20, 20, paint);
		//���� ���÷��� ������ ��ȿȭ�ϰ� ���� Update ��
	    invalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        //����� ��ġ�� �Ͼ�� ����
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	      Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " + 
                         event.getX() + ", " + event.getY(), 1000).show();
        }
        //�θ� ���� �̺�Ʈ ó������� �����ϱ� ���� ȣ�� ��.
        return super.onTouchEvent(event);
     }
  }