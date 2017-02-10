/*
 *  Frame By Frame XML�� �̿��� ������ ���
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
		//�� ���̾ƿ��� Ȯ�� �ϱ� �ٶ�
		setContentView(R.layout.framebyframe_list);

		ImageView animImageView = (ImageView)findViewById(R.id.animImageViewXML);
		//���̾ƿ��� �������� ������ ������ ���� �ڵ�󿡼� ���� �ϸ� ��
		//android:background="@drawable/bag_animation_list"�� ������ ���� �ڵ��ص� ��
		//animImageView.setBackgroundDrawable(R.drawable.bag_animation_list);

		//�ִϸ��̼��� �����ϰ� �����ϱ� ���� ��� ��
		//�ִϸ��̼��� �⺻������ ù��° ȭ�鿡�� ���� �ϰ� ����
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
