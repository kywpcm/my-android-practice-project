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
	  
	  //애니메이션 효과를 초기화 하는 콜백 메소드
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
	      //설정 할 카메라에 설정 할 메트릭스값을 저장할 준비를 함
	      camera.save();
	      //z축으로 1500px 뒤로 뺀 후 다시 z좌표가 0인 평면으로 당겨옴
	      //interpolatedTime값이 0에서 1로 증가(3초동안)할 수록 
	      //z축 값은 애니메이션 끝까지 점점 작아지다가 1이되면 0이 됨
	      camera.translate(0.0f, 0.0f, (1500 - (1500.0f * interpolatedTime)));
	      //Y축을 기준으로 3초 동안 회전(360도)을 이룸
	      camera.rotateY(360 * interpolatedTime);
	      
	      //matrix에는 카메라객체를 통해 최종 결과를 얻는데
	      //필요한 변환효과 작업들이 들어가고,카메라객체는 사라짐
	      //matrix를 적용하기 위함
	      camera.getMatrix(matrix);
          
	      //위젯과 애니메이션 중심을 설정
	      matrix.preTranslate(-centerX, -centerY); 
	      matrix.postTranslate(centerX, centerY);
	      //카메라를 이전에 저장 됐던 원래 상태로 재설정 함
	      //메트릭스 적용 후 카메라 재설정을 통해 원근감 부여
	      camera.restore();
	  }
  }
}