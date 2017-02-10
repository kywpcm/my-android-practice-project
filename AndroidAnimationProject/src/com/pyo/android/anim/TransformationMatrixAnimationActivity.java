/*
 *  변환 행렬을 적용한 애니메이션
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
	  
	  //애니메이션 효과를 초기화 하는 콜백 메소드
	  @Override
	  public void initialize(int width,int height,int parentWidth,int parentHeight){
		 super.initialize(width, height, parentWidth, parentHeight);
		 //AnimationUtils.loadAnimation(Context, int resourdeId)를 통해
		 //XML로 선언된 속성을 적용 해도 됨
		 setDuration(3000);
		 setFillAfter(true);
		 //setInterpolator(new LineartInterpolator());
		 setInterpolator(new OvershootInterpolator());
	  }
	  
	  @Override
	  public void applyTransformation(float interpolatedTime, Transformation t){
		  float dX = interpolatedTime/2;
		  float dY = interpolatedTime/2;
		  
		  //Transformation의 타입을 메트릭스로 설정 한다 
		  t.setTransformationType(Transformation.TYPE_MATRIX);
		  
		  //변환행렬을 적용 할 객체를 얻어 온다
		  
		  Matrix matrixObj = t.getMatrix();
		  	  
		  // interpolatedTime값은 애니메이션 시작 시 0, 끝날 땐 1일 됨
		  //즉, 여기선 duration 3초 동안 0 에서 1 값으로 계속 호출 됨
		  //이 효과를 적용한 위젯은 작은 점(0,0)에서 시작해 위젯의 최대 크기(1,1)로 변환행렬이
		  //적용 됨
		  matrixObj.setScale(interpolatedTime, interpolatedTime); //크기 조절
		  //행렬연산의 원점(좌측상단구석)이 애니메이션 중심(좌측상단)에 맞지 않기 때문에
		  //ListView의 아이템이 균일하게 커지지 않음
		  //균일하게 커지게 하려면 위젯과 애니메이션 중심을 일치시킨 후 행렬연산을 적용하고
		  //다시 위젯의 이전 중심으로 다음과 같이 이동 시킨다
		  matrixObj.preTranslate(-dX, -dY);
		  matrixObj.postTranslate(dX, dY);
	  }
  }
}
