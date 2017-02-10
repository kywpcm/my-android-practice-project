/*
 *  인터폴레이터가 적용 된 애니메이션
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class InterPolatorAnimationActivity extends Activity {
   private boolean clickableState;
   private Button accelerateBtn;
   private Button decelerateBtn;
   private Button accelDecelBtn;
   private Button bounceBtn;
   private Button btnAnticipate;
   private Button btnOverShoot;
   private Button btnLinear;
   private Button btnCycle;
   private ImageView imageView;
   private Button btnAnticipateOvershoot;
 
	@Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.interpolator_layout);
	   
	   imageView =  (ImageView)findViewById(R.id.imageViewInterpolator);
	   imageView.setImageResource(R.drawable.white_stroke_oval);
	   imageView.setVisibility(View.VISIBLE);
	   
	   accelerateBtn = (Button)findViewById(R.id.btn_accelerate);
	   accelerateBtn.setOnClickListener(new View.OnClickListener() {	
		@Override
		 public void onClick(View v){
			animationApplyInterpolater(R.anim.accelerate_interpolator);  
		 }
	   });
	   decelerateBtn = (Button)findViewById(R.id.btn_decelerate);
	   decelerateBtn.setOnClickListener(new View.OnClickListener() {	
		 @Override
		 public void onClick(View v) {
			animationApplyInterpolater(R.anim.decelerate_interpolator);  
		 }
	   });
	   accelDecelBtn = (Button)findViewById(R.id.btn_accel_decel);
	   accelDecelBtn.setOnClickListener(new View.OnClickListener() {	
		@Override
		 public void onClick(View v) {
			animationApplyInterpolater(R.anim.accel_decel_interpolator);		
		 }
	   });
	   bounceBtn = (Button)findViewById(R.id.btn_bounce);
	   bounceBtn.setOnClickListener(new View.OnClickListener(){	
		@Override
		 public void onClick(View v) {
			animationApplyInterpolater(R.anim.bounce_interpolator);
	 	}
	   });
	 //코드상에서 직접 인터폴레이터를 적용
	   btnAnticipate = (Button)findViewById(R.id.btn_Anticipate);
	   btnAnticipate.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				animationApplyInterpolater(R.anim.bounce_interpolator, new AnticipateInterpolator());
			}
	   });
	   btnOverShoot =  (Button)findViewById(R.id.btn_Overshoot);
	   btnOverShoot.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				animationApplyInterpolater(R.anim.bounce_interpolator, new OvershootInterpolator());
			}
	   });
	   
	   btnLinear =  (Button)findViewById(R.id.btn_Linear);
	   btnLinear.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				animationApplyInterpolater(R.anim.bounce_interpolator, new LinearInterpolator());
			}
	   });
	   btnCycle =  (Button)findViewById(R.id.btn_Cycle);
	   btnCycle.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				animationApplyInterpolater(R.anim.bounce_interpolator, new CycleInterpolator(2));
			}
	   });
	   btnAnticipateOvershoot = (Button)findViewById(R.id.btn_AnticipateOvershoot);
	   btnAnticipateOvershoot.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				animationApplyInterpolater(R.anim.bounce_interpolator, new AnticipateOvershootInterpolator());
			}
	   });
   }
   private  void  animationApplyInterpolater(int resourceId, Interpolator interpolator){
       Animation animation =  AnimationUtils.loadAnimation(this, resourceId);
       animation.setInterpolator(interpolator);
       animation.setAnimationListener(new MyAnimationListener());	
       imageView.startAnimation(animation);
   }
   private  void  animationApplyInterpolater(int resourceId){	   
       Animation animation =  AnimationUtils.loadAnimation(this, resourceId);
       animation.setAnimationListener(new MyAnimationListener());	
       imageView.startAnimation(animation);
   }
   private void clickableStateButtons(){	
	    accelerateBtn.setClickable(clickableState);
	    decelerateBtn.setClickable(clickableState);
	    accelDecelBtn.setClickable(clickableState);
	    bounceBtn.setClickable(clickableState);
   }
   private class MyAnimationListener implements Animation.AnimationListener {
		public void onAnimationEnd(Animation animation) {
			clickableState = true;
			clickableStateButtons();	
		}
		public void onAnimationRepeat(Animation animation) {
		}
		public void onAnimationStart(Animation animation){
			clickableState = false;
			clickableStateButtons();		
		}		
	}
}