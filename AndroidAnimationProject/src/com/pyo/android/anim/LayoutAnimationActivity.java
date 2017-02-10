/*
 *  ���̾ƿ��� �ִϸ��̼��� ����
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
	   // /anim/�� �����ϴ� �ڿ������� ȣ��
		Animation layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.snazzyintro);
		//�ڿ������� �̿��� �ִϸ��̼� ȿ���� ��
        layoutToAnimate.startAnimation(layoutAnimation);
	}
}