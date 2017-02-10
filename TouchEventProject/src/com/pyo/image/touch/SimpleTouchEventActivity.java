/*
 *  made by PYO.IN.SOO
 *  �⺻ ��ġ �̺�Ʈ ó��
 */
package com.pyo.image.touch;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class SimpleTouchEventActivity extends Activity{
	
	//�̵� �� �Ÿ�
    private float motionPositionX;
    private float motionPositionY;
    
    //������ ��ġ ��ǥ
    private float motionLastTouchX;
    private float motionLastTouchY;
	private ImageView imageView;
	
	//�̵��� ���� �� ��ȯ ��� ��ü
	private Matrix translatedMatrix = new Matrix();

	@Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.simple_multi_event_layout);
	   
	   imageView = (ImageView)findViewById(R.id.simpleTouchImageView);
	   //�ݵ�� MATRIX Ÿ������ ���� ��
	   imageView.setScaleType(ImageView.ScaleType.MATRIX);
	   //���� ��Ʈ������ �⺻������ �V��
	   translatedMatrix.setTranslate(1f, 1f);
	   //��Ʈ���� ��ü�� �̹��� �信 ���� ��
	   imageView.setImageMatrix(translatedMatrix);
	   
	   imageView.setOnTouchListener(simpleTouchListener);
   }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	   return true;
	}
   private View.OnTouchListener simpleTouchListener = new View.OnTouchListener(){
	@Override
	public boolean onTouch(View v, MotionEvent event){
		
		  final int action = event.getAction();
		  switch (action) {
		    case MotionEvent.ACTION_DOWN: {
		    
		        final float x = event.getX();
		        final float y = event.getY();
		        
		        //ó�� ��ġ �� ���� ��ǥ�� ����
		        motionLastTouchX = x;
		        motionLastTouchY = y;
		        break;
		    }case MotionEvent.ACTION_MOVE: {
		        final float x = event.getX();
		        final float y = event.getY();
		  
		        final float distanceX = x - motionLastTouchX;
		        final float distanceY = y - motionLastTouchY;
		        
		        // �̹����� �̵��� �Ÿ�
		        motionPositionX += distanceX;
		        motionPositionY += distanceY;
		        
		        //������ ��ǥ�� �ٽ� ���
		        motionLastTouchX = x;
		        motionLastTouchY = y;
		        
		        //����� �̵� ��Ų��
		        translatedMatrix.setTranslate(motionPositionX, motionPositionY);
	            //�ش� �̹����� ���� �Ѵ�
		        imageView.setImageMatrix(translatedMatrix);
		        break;
		    }
		 }
		  //true�� �����ؾ� ����ؼ� ��ġ�̺�Ʈ�� �߻� ��
		  return true;
	  }  
    };
}