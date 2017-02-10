package com.pyo.android.anim;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TransformationCameraAnimationActivity extends Activity{
	private String[] exampleItems = new String[] {"C", "A","M","E","R","A"};
	private Button btnCamera;
	@Override
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.transformation_camera_layout);
	  
	  btnCamera = (Button)findViewById(R.id.btn_camera_start);
	  
	  ArrayAdapter<Object> listItemAdapter =  new ArrayAdapter<Object>(this 
	          ,android.R.layout.simple_list_item_1 ,exampleItems);
     final ListView listView = (ListView)this.findViewById(R.id.anim_camera_list_id);
     listView.setAdapter(listItemAdapter);
     
     btnCamera.setOnClickListener(new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
		   listView.startAnimation(new TransformationCameraMatrix());
		}
	 });
  }
  private class TransformationCameraMatrix extends Animation{
	  private Camera camera;
	  public TransformationCameraMatrix(){
		  camera = new Camera();
	  }
	  
	  //�ִϸ��̼� ȿ���� �ʱ�ȭ �ϴ� �ݹ� �޼ҵ�
	  @Override
	  public void initialize(int width,int height,int parentWidth,int parentHeight){
		 super.initialize(width, height, parentWidth, parentHeight);
		
		 setDuration(3000);
		 setFillAfter(true);
		 setInterpolator(new LinearInterpolator());
	  }
	  
	  @Override
	  public void applyTransformation(float interpolatedTime, Transformation t){
		  float centerX = interpolatedTime/2;
		  float centerY = interpolatedTime/2;
		  
	      final Matrix matrix = t.getMatrix();
	      //���� �� ī�޶� ���� �� ��Ʈ�������� ������ �غ� ��
	      camera.save();
	      //z������ 1500px �ڷ� �� �� �ٽ� z��ǥ�� 0�� ������� ��ܿ�
	      //interpolatedTime���� 0���� 1�� ����(3�ʵ���)�� ���� 
	      //z�� ���� �ִϸ��̼� ������ ���� �۾����ٰ� 1�̵Ǹ� 0�� ��
	      camera.translate(0.0f, 0.0f, (1500 - (1500.0f * interpolatedTime)));
	      //Y���� �������� 3�� ���� ȸ��(360��)�� �̷�
	      camera.rotateY(360 * interpolatedTime);
	      
	      //matrix���� ī�޶�ü�� ���� ���� ����� ��µ�
	      //�ʿ��� ��ȯȿ�� �۾����� ����,ī�޶�ü�� �����
	      //matrix�� �����ϱ� ����
	      camera.getMatrix(matrix);
          
	      //������ �ִϸ��̼� �߽��� ����
	      matrix.preTranslate(-centerX, -centerY); 
	      matrix.postTranslate(centerX, centerY);
	      //ī�޶� ������ ���� �ƴ� ���� ���·� �缳�� ��
	      //��Ʈ���� ���� �� ī�޶� �缳���� ���� ���ٰ� �ο�
	      camera.restore();
	  }
  }
}