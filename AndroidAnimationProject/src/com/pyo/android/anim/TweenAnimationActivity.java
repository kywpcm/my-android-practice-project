/*
 *  트위닝 애니메이션 예제
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class TweenAnimationActivity extends Activity {
    private boolean clickableState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tween_layout);		
	     // 알파애니메이션
		final Button fadeButton = (Button) findViewById(R.id.ButtonAlpha);
		fadeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweeningPerformAnimation(R.anim.alpha_anim);
			}
		});			
		//크기애니메이션
		final Button growButton = (Button) findViewById(R.id.ButtonScale);
		growButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweeningPerformAnimation(R.anim.scale_anim);
			}
		});		
	     //이동애니메이션
		final Button moveButton = (Button) findViewById(R.id.ButtonTranslate);
		moveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweeningPerformAnimation(R.anim.translate_anim);
			}
		});			
		//회전애니메이션
		final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
		spinButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweeningPerformAnimation(R.anim.rotate_anim);
			}
		});	
		//AnimationSet 적용
		final Button allButton = (Button) findViewById(R.id.ButtonAll);
		allButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweeningPerformAnimation(R.anim.all_animation);
			}
		});
	}	
	private void tweeningPerformAnimation(int animationResourceID){
		ImageView reusableImageView1 = 
			 (ImageView)findViewById(R.id.ImageViewForTweening1);
		ImageView reusableImageView2 = 
			 (ImageView)findViewById(R.id.ImageViewForTweening2);
		reusableImageView1.setImageResource(R.drawable.white_stroke_oval);
		reusableImageView1.setVisibility(View.VISIBLE);
		reusableImageView2.setImageResource(R.drawable.white_stroke_oval);
		reusableImageView2.setVisibility(View.VISIBLE);
	    Animation xmlAnim =  AnimationUtils.loadAnimation(this, animationResourceID);
	    xmlAnim.setAnimationListener(new MyAnimationListener());	
        reusableImageView1.startAnimation(xmlAnim);
        reusableImageView2.startAnimation(xmlAnim);
	}	
	private void clickableStateButtons(){
		final Button fadeButton = (Button) findViewById(R.id.ButtonAlpha);
		fadeButton.setClickable(clickableState);
			
		final Button growButton = (Button) findViewById(R.id.ButtonScale);
		growButton.setClickable(clickableState);
		
	 	final Button moveButton = (Button) findViewById(R.id.ButtonTranslate);
		moveButton.setClickable(clickableState);
		
		final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
		spinButton.setClickable(clickableState);
		
		final Button allButton = (Button) findViewById(R.id.ButtonAll);
		allButton.setClickable(clickableState);	
	}
	class MyAnimationListener implements Animation.AnimationListener {
		public void onAnimationEnd(Animation animation) {
			clickableState = true;
			clickableStateButtons();	
		}
		public void onAnimationRepeat(Animation animation) {
		}
		public void onAnimationStart(Animation animation) {
			clickableState = false;
			clickableStateButtons();		
		}		
	}
}