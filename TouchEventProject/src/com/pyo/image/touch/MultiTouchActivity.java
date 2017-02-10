/*
 *  ��Ƽ ��ġ ���� 
 *  �ݵ�� �ȵ���̵� �ܸ��⿡�� �׽�Ʈ �ؾ� ��
 */
package com.pyo.image.touch;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MultiTouchActivity extends Activity{
   private static final String TAG =  "MultiTouchActivity";
   
   //�̹����� Move �Ǵ� Zoom ������ ������ ��ȯ ��� ��ü
   private Matrix matrix = new Matrix();
   private Matrix savedMatrix = new Matrix();

   //������� ��ġ �׼� ���°�
   private static final int NONE_STATE = 0;
   private static final int DRAG_STATE = 1;
   private static final int ZOOM_STATE = 2;
   
   //������� ���� ����
   private int userMode = NONE_STATE;

   //Zoom ���½� ���� �� ���� ��
   private PointF startPoint = new PointF();
   private PointF midPoint = new PointF();
   
   private float oldDistance = 1f;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.multi_touch_layout);
      ImageView imageView = (ImageView)findViewById(R.id.touchImageView);
      imageView.setOnTouchListener(userTouchImpl);
      
      //���� ��Ʈ������ �⺻������ �V��
      matrix.setTranslate(1f, 1f);
      //��Ʈ���� ��ü�� �̹��� �信 ���� ��
      imageView.setImageMatrix(matrix);
   }
   private View.OnTouchListener userTouchImpl = new  View.OnTouchListener(){
	   @Override
	   public boolean onTouch(View v, MotionEvent rawEvent) {
		   WrapperMotionEvent event = WrapperMotionEvent.getMotionEventInstance(rawEvent);
	       ImageView view = (ImageView) v;
	      //�׼� ���� MotionEvent.ACTION_MASK �� '&' ������ �ϰ� �Ǹ�, 
	      //�����ų�(DOWN) ��(UP) �������� �ε��� ���� ���� �� ����
	      switch (event.getAction() & MotionEvent.ACTION_MASK) {
	       case MotionEvent.ACTION_DOWN: //��ġ ���� ��
	         //���� ��ȯ ��� ��ü�� ����
	    	 savedMatrix.set(matrix);
	         
	         //PointF�� ��ǥ���� �V��
	    	 startPoint.set(event.getX(), event.getY());
	         Log.d(TAG, " mode =DRAG");
	         userMode = DRAG_STATE;
	         break;
	       //ù��° �����Ͱ� �ƴ� �ٸ� �����Ͱ� �������� ��� �߻�
	       case MotionEvent.ACTION_POINTER_DOWN:
	    	 oldDistance = spacingPointers(event);
	         Log.d(TAG, "oldDistance =" + oldDistance);
	         if (oldDistance > 10f) {
	            savedMatrix.set(matrix);
	            middlePoint(midPoint, event);
	            userMode = ZOOM_STATE;
	            Log.d(TAG, "userMode=ZOOM_STATE");
	         }
	         break;
	       case MotionEvent.ACTION_UP:
	       //ù��° �����Ͱ� �ƴ� �ٸ� �����͸� UP�� ��� �߻�
	       case MotionEvent.ACTION_POINTER_UP:
	         userMode = NONE_STATE;
	         Log.d(TAG, "userMode=NONE_STATE");
	         break;
	       case MotionEvent.ACTION_MOVE:
	         if (userMode == DRAG_STATE) {
	            //��ȯ ��İ� �V��
	        	matrix.set(savedMatrix);
	            matrix.postTranslate(event.getX() - startPoint.x,
	                                 event.getY() - startPoint.y);
	         }else if (userMode == ZOOM_STATE) {
	            float newDistance = spacingPointers(event);
	            Log.d(TAG, "newDistance=" + newDistance);
	            if (newDistance > 10f) {
	               matrix.set(savedMatrix);              
	               //�Ÿ��� ����Ͽ� ũ�� ��ȯ
	               float scale = newDistance/oldDistance;
	               matrix.postScale(scale, scale, midPoint.x, midPoint.y);
	            }
	         }
	         break;
	      }
	      //���� �V�õ� ��ȯ ��°��� �����Ͽ� �̹����� �׸�.
	      view.setImageMatrix(matrix);
	      return true; // �ڵ鸵�� �̺�Ʈ
	   }  
   }; 
   /** 
       ù��°�� �ι�°�� ������ �˾Ƴ�
   */
   private float spacingPointers(WrapperMotionEvent event) {
      //�� �������� ��ǥ���� ��´�.
      float dX = event.getX(0) - event.getX(1);
      float dY = event.getY(0) - event.getY(1);
      //����Ͽ� ���簢���� ���� ����Ͽ� �ѱ�(������)
      return FloatMath.sqrt(dX *dX + dY *dY);
   }
   /** 
     ù��°�� �ι�° ������ �߰�ġ�� ��� �Ͽ� PointF�� ���� ��
   */
   private void middlePoint(PointF point, WrapperMotionEvent event){
	  //ù��° ��ǥ�� �ι�° �������� ��ǥ���� ���� �߰����� ��´�
      float x = event.getX(0) + event.getX(1);
      float y = event.getY(0) + event.getY(1);
      point.set(x / 2, y / 2);
   }
}