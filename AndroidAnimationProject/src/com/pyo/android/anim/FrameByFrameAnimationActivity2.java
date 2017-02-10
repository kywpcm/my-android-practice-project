/*
 *  ImageSwitcher Ȱ���� Frame By Frame
 */
package com.pyo.android.anim;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class FrameByFrameAnimationActivity2 extends Activity{
	
	private Handler mHandler = new Handler();
	
	private ImageSwitcher imageSwitcher;
	
	private ShoesAnimationThread shoesThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framebyframe_switcher);

		//�̹��� ������ ��ü�� ȹ��
		imageSwitcher  = (ImageSwitcher) findViewById(R.id.animImageSwitcher);
		imageSwitcher.setVisibility(View.VISIBLE);

		//�̹�������Ī��ü�� �̹����䰴ü�� �Ӽ��� �����Ͽ� �߰���.
		imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			
			public View makeView() {
				ImageView imageView = new ImageView(FrameByFrameAnimationActivity2.this);
				imageView.setBackgroundColor(0xFF000000);
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				return imageView;
			}
		});

		// �ִϸ��̼� ����
		Button startButton = (Button) findViewById(R.id.animStartBtnSwitcher);
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				shoesThread = new ShoesAnimationThread();
				shoesThread.start();
			}
		});
		
		Button stopButton = (Button) findViewById(R.id.animStopBtnSwitcher);
		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(shoesThread != null){
					shoesThread.interrupt();
				}
			}
		});
	}
	private class ShoesAnimationThread extends Thread{
		
		private int durationTime = 500;
		
		private int imageResourceIds[] = {
				R.drawable.shoes_01, R.drawable.shoes_02, R.drawable.shoes_03,
				R.drawable.shoes_04, R.drawable.shoes_05, R.drawable.shoes_06,
				R.drawable.shoes_07, R.drawable.shoes_08, R.drawable.shoes_09, 
				R.drawable.shoes_10, R.drawable.shoes_11, R.drawable.shoes_12,
				R.drawable.shoes_13, R.drawable.shoes_14, R.drawable.shoes_15, 
				R.drawable.shoes_16, R.drawable.shoes_17, R.drawable.shoes_18
		};
		
		public void run(){
			
			while(!isInterrupted()){
				final int imageResourcesSize = imageResourceIds.length;
				int currentIdx = 0;
				while (currentIdx < imageResourcesSize) {
					final int curIdx = currentIdx;
					mHandler.post(new Runnable(){
						public void run() {
							imageSwitcher.setImageResource(imageResourceIds[curIdx]);
						}
					});
					try{
						sleep(durationTime);
					}catch(InterruptedException ie){
						this.interrupt();
					}
					currentIdx++;
				}
			}
			
		}
	}
	
}