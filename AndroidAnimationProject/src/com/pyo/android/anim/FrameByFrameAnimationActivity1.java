/*
 *  Made By PYO.IN.SOO
 *  ImageView를 이용한 Frame By Frame 예제
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FrameByFrameAnimationActivity1 extends Activity {

	private AnimationDrawable mframeAnimation = null;

	private int[] clubImageR = { 
			R.drawable.club_01, R.drawable.club_02, R.drawable.club_03,
			R.drawable.club_04, R.drawable.club_05, R.drawable.club_06,
			R.drawable.club_07, R.drawable.club_08, R.drawable.club_09,
			R.drawable.club_10, R.drawable.club_11, R.drawable.club_12,
			R.drawable.club_13, R.drawable.club_14, R.drawable.club_15,
			//jpg 파일을 너무 많이 로드하면, 런타임 에러 난다..
			R.drawable.club_16, R.drawable.club_17, R.drawable.club_18,
			R.drawable.club_19, R.drawable.club_20, R.drawable.club_21,
			R.drawable.club_22, R.drawable.club_23, R.drawable.club_24,
			R.drawable.club_25, R.drawable.club_26, R.drawable.club_27,
			R.drawable.club_28, R.drawable.club_29, R.drawable.club_30,
			R.drawable.club_31, R.drawable.club_32, R.drawable.club_33,
			//			R.drawable.club_34, R.drawable.club_35, R.drawable.club_36,
			//			R.drawable.club_37, R.drawable.club_38, R.drawable.club_39,
			//			R.drawable.club_40, R.drawable.club_41, R.drawable.club_42,
			//			R.drawable.club_43, R.drawable.club_44, R.drawable.club_45
	};
	
	private BitmapDrawable[] clubFrames = new BitmapDrawable[clubImageR.length];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framebyframe);

		final Button onButton = (Button)findViewById(R.id.animStartBtn);
		onButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clubStartAnimation();
			}
		});	
		
		final Button offButton = (Button) findViewById(R.id.animStopBtn);
		offButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clubStopAnimation();
			}
		});  
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//...
	}
	
	private void clubStartAnimation(){
		ImageView clubImages = (ImageView)findViewById(R.id.animImageView);
		
		Resources imageFrameRes = getResources();
		int frameDuration = 100;
		mframeAnimation = new AnimationDrawable();
		for(int i = 0, imageLength = clubImageR.length; i < imageLength; i++){
			clubFrames[i] = (BitmapDrawable) imageFrameRes.getDrawable(clubImageR[i]);
			mframeAnimation.addFrame(clubFrames[i], frameDuration);
		}
		
		//ImageView에 해당 애니메이션을 등록 해야 함
		clubImages.setBackgroundDrawable(mframeAnimation);

		mframeAnimation.setOneShot(false);
		//애니메이션을 시작하고, 재시작도 등록
		mframeAnimation.setVisible(true, true);
		//내부적으로 Runnable로 실행 됨
		mframeAnimation.start();
	}
	
	private void clubStopAnimation(){
		mframeAnimation.stop();
		mframeAnimation.setVisible(false, false);
	}
}