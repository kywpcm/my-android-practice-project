/*
 *  레이아웃에 애니메이션을 적용
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class LayoutAnimationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweenoflayout);
		
		LinearLayout layoutToAnimate = (LinearLayout)findViewById(R.id.LayoutRow);
	   // /anim/에 존재하는 자원파일을 호출
		Animation layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.snazzyintro);
		//자원파일을 이용해 애니메이션 효과를 줌
        layoutToAnimate.startAnimation(layoutAnimation);
	}
}