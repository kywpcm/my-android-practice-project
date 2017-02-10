/*
 *  ��ȯ ����� ������ �ִϸ��̼�
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TransformationMatrixAnimationActivity extends Activity {
	private String[] exampleItems = new String[] {"M", "A","T","R","I","X"};
	private Button btnMatrix;
	@Override
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.transformation_matrix_layout);
	  
	  btnMatrix = (Button)findViewById(R.id.btn_matrix_start);
	  
	  ArrayAdapter<Object> listItemAdapter =  new ArrayAdapter<Object>(this 
	          ,android.R.layout.simple_list_item_1 ,exampleItems);
     final ListView listView = (ListView)this.findViewById(R.id.anim_matrix_list_id); 
     listView.setAdapter(listItemAdapter);
     
     btnMatrix.setOnClickListener(new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
		   listView.startAnimation(new TransformationMatrix());
		}
	 });
  }
  private class TransformationMatrix extends Animation{
	  public TransformationMatrix(){}
	  
	  //�ִϸ��̼� ȿ���� �ʱ�ȭ �ϴ� �ݹ� �޼ҵ�
	  @Override
	  public void initialize(int width,int height,int parentWidth,int parentHeight){
		 super.initialize(width, height, parentWidth, parentHeight);
		 //AnimationUtils.loadAnimation(Context, int resourdeId)�� ����
		 //XML�� ����� �Ӽ��� ���� �ص� ��
		 setDuration(3000);
		 setFillAfter(true);
		 //setInterpolator(new LineartInterpolator());
		 setInterpolator(new OvershootInterpolator());
	  }
	  
	  @Override
	  public void applyTransformation(float interpolatedTime, Transformation t){
		  float dX = interpolatedTime/2;
		  float dY = interpolatedTime/2;
		  
		  //Transformation�� Ÿ���� ��Ʈ������ ���� �Ѵ� 
		  t.setTransformationType(Transformation.TYPE_MATRIX);
		  
		  //��ȯ����� ���� �� ��ü�� ��� �´�
		  
		  Matrix matrixObj = t.getMatrix();
		  	  
		  // interpolatedTime���� �ִϸ��̼� ���� �� 0, ���� �� 1�� ��
		  //��, ���⼱ duration 3�� ���� 0 ���� 1 ������ ��� ȣ�� ��
		  //�� ȿ���� ������ ������ ���� ��(0,0)���� ������ ������ �ִ� ũ��(1,1)�� ��ȯ�����
		  //���� ��
		  matrixObj.setScale(interpolatedTime, interpolatedTime); //ũ�� ����
		  //��Ŀ����� ����(������ܱ���)�� �ִϸ��̼� �߽�(�������)�� ���� �ʱ� ������
		  //ListView�� �������� �����ϰ� Ŀ���� ����
		  //�����ϰ� Ŀ���� �Ϸ��� ������ �ִϸ��̼� �߽��� ��ġ��Ų �� ��Ŀ����� �����ϰ�
		  //�ٽ� ������ ���� �߽����� ������ ���� �̵� ��Ų��
		  matrixObj.preTranslate(-dX, -dY);
		  matrixObj.postTranslate(dX, dY);
	  }
  }
}
