/*
 *  Frame By Frame XML을 이용한 선언적 기법
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FrameByFrameAnimationActivity3 extends Activity {
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		//이 레이아웃은 확인 하기 바람
		setContentView(R.layout.framebyframe_list);

		ImageView animImageView = (ImageView)findViewById(R.id.animImageViewXML);
		//레이아웃에 선언하지 않으면 다음과 같이 코드상에서 적용 하면 됨
		//android:background="@drawable/bag_animation_list"를 다음과 같이 코딩해도 됨
		//animImageView.setBackgroundDrawable(R.drawable.bag_animation_list);

		//애니메이션을 시작하고 정지하기 위해 사용 함
		//애니메이션은 기본적으로 첫번째 화면에서 정지 하고 있음
		final AnimationDrawable listDrawable = (AnimationDrawable)animImageView.getBackground();
		
		Button startAnimationBtn = (Button)findViewById(R.id.animStartBtnXML);
		startAnimationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listDrawable.setVisible(true, true);
				listDrawable.start();
			}
		});
		
		Button stopAnimationBtn = (Button)findViewById(R.id.animStopBtnXML);
		stopAnimationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listDrawable.stop();
				listDrawable.setVisible(false, false);
			}
		});
	}
	
}
