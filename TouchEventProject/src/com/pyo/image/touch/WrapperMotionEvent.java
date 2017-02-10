package com.pyo.image.touch;

import android.view.MotionEvent;

public class WrapperMotionEvent{
   protected MotionEvent event;
   protected WrapperMotionEvent(MotionEvent event){
      this.event = event;
   }
   public static  WrapperMotionEvent 
         getMotionEventInstance(MotionEvent event){
     return new WrapperMotionEvent(event);
   }
   public int getAction() {
      return event.getAction();
   }
   public float getX() {
      return event.getX();
   }
   public float getX(int pointerIndex) {
      return event.getX(pointerIndex);
   }
   public float getY() {
      return event.getY();
   }
   public float getY(int pointerIndex) {
      return event.getY(pointerIndex);
   }
   public int getPointerCount() {
	  //���� ȭ��� ���� �ϴ� ��ġ�� ������ ���� ��
	  return event.getPointerCount();
   }
   public int getPointerId(int pointerIndex) {
	  //�����͸��� ������ ID���� �����ϸ� �� �޼ҵ�� �� 
	  //��ġ�������� ID�� ������ �ش�.
	  //MotionEvent.findPointerIndex(ID)�� ����
	  //�޼ҵ�� �ݴ�
	  return event.getPointerId(pointerIndex);
   }
}