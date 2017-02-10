/*
 *  ��ġ�Ѱ��� ��ǥ�� ��������ϴ� ��
 */
package com.pyo.image.touch;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class FreedomCurveDrawingView extends View {
	private Paint mPaint;
	ArrayList<TouchPoint> touchPoints=new ArrayList<TouchPoint>();
	float left;
	float right;
	
	public FreedomCurveDrawingView(Context context,AttributeSet attrs){
		super(context,attrs);
		setDrawingCacheEnabled(true);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF000000);
		
		//���� �׷���
		mPaint.setStyle(Paint.Style.STROKE);
		//������ ���� ������ ����� ����
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		/*
		 * ���� ������� ���� ��
		 * ROUND : �ձ� ���
		 * SQUARE : �簢�� ���(���� ��ǥ���� ���� �� �׷���)
		 * BUTT :  ������ǥ���� ���� ����(Default)
		 */
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		//���� ����
		mPaint.setStrokeWidth(6);
	}
	
	public void onDraw(Canvas canvas){	
		canvas.drawColor(Color.TRANSPARENT);
        int touchPointSize = touchPoints.size();
		for(int i=0; i < touchPointSize ;i++){
			if(touchPoints.get(i).drawFlag){
				canvas.drawLine(touchPoints.get(i-1).pointX, touchPoints.get(i-1).pointY, touchPoints.get(i).pointX, touchPoints.get(i).pointY, mPaint);
			}
		}
	}
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			left=event.getX();
			right=event.getX();
			
			if(event.getX() < left){
				left=event.getX();
			}
			if(event.getX() > right){
				right=event.getX();
			}
			touchPoints.add(new TouchPoint(event.getX(),event.getY(),false));
			return true;
		}
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			if(event.getX() < left){
				left=event.getX();
			}
			if(event.getX() > right){
				right=event.getX();
			}
			touchPoints.add(new TouchPoint(event.getX(),event.getY(),true));
			invalidate();
			return true;
		}
		return false;
	}
	public class TouchPoint{
		float pointX;
		float pointY;
		boolean drawFlag;

		TouchPoint(float dx,float dy,boolean drawFlag){
			pointX=dx;
			pointY=dy;
			this.drawFlag=drawFlag;
		}
	}
}
