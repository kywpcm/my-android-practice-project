/*
 *  �ȵ���̵� ��ġ �̺�Ʈ �ӵ�(Velocity) üũ �ϱ�
 */
package com.pyo.image.touch;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class VelocityTrackerActivity extends Activity{
    
	private VelocityTracker tracker;
	public boolean onTouchEvent(MotionEvent event){
		int  touchAction = event.getAction();
		switch(touchAction){
		   case MotionEvent.ACTION_DOWN :
			     if(tracker != null){
			    	 //�ٽ� ��ġ�� ���� �� �� ���� �������� �ʰ� �ʱ�ȭ �Ͽ� ���
			    	 tracker.clear();
			     }else{
			    	 // ��ü�� ���ϱ� ���� ���丮 �޼ҵ� ȣ��
			    	 tracker = VelocityTracker.obtain();
			     }
			     //�ӵ��� üũ�ϱ� ���� ���� ��ǰ�ü�� ����
			     tracker.addMovement(event);
			     break;
		   case MotionEvent.ACTION_MOVE :
			   //�ӵ��� üũ�ϱ� ���� ���� ��ǰ�ü�� ����
			    tracker.addMovement(event);
			    //�Էµ� ����Ÿ�� ������� �ӵ��� ����
			    //���� �ð��� 1�� 1�и��ʸ�, 1000�� 1�ʸ� �ǹ��մϴ�. 
			    //���ڰ��� 1�̶�� 1�и��ʵ����� �ȼ������� �̵� �Ÿ��� ����
			    //1000�̸� 1�� ������ �ȼ� ������ �̵� �Ÿ��� ���� ��
			    tracker.computeCurrentVelocity(1000);
			    float xAxisVelocity = tracker.getXVelocity();
			    float yAxisVelocity = tracker.getYVelocity();
			    Log.i("VELOCITY", "X�� �ӵ�" + xAxisVelocity );
			    Log.i("VELOCITY", "Y�� �ӵ�" + yAxisVelocity );
			    break;
		   case MotionEvent.ACTION_CANCEL :
		   case MotionEvent.ACTION_UP :
			   /*  
			   ��ü�� ������ �� �ֵ��� �ʱ�ȭ	
			   �巡�� �ӵ� ������ ����ϰ� �Ͼ�� ����
			   OnTouchListener�� VelocityTracker�� �ν��Ͻ��� �ϳ��� ���� ����.
		       VelocityTracker ��ü�� ������ ���丮 �޼ҵ忡 �����ϰ� ��ü�� ��� �� ��ȯ
			   ��������� VelocityTracker ��ü �ϳ��� ��� �巡�� ���ۿ� �����Ǿ� ���� �� ����
		        */
			     tracker.recycle();
			     break;
		}
		
		event.recycle();
		return true;
	}
}
