/*
 *  사운드 풀 사용 방법
 *  made By PYO IN SOO
 */
package com.pyo.android.multimedia;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

public class SoundPoolUsingActivity extends Activity {
    private ImageView musics [] = new ImageView[4];
    
    private SeekBar  volumnBar; //볼륨
    private RatingBar speedBar; //스피드
    
    //로드된 ogg 리소스의 식별자 값(SoundPool은 이 식별자를 이용하여 음원을재생)
    private int [] rawResIds = { 0,0,0,0};
    private boolean [] isSounds = {true, true, true, true}; //재생 중 여부
    private SoundPool soundPool ;
    
    private Animation musicAnimation;
    
    private Handler musicHandler = new Handler();
    
    private MusicThread  threads [] = new MusicThread[4];
	@Override
    public void onCreate(Bundle bundle){
    	super.onCreate(bundle);
    	setContentView(R.layout.sound_pool_layout);
    	setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	
    	
    	soundPool = new SoundPool( 10, AudioManager.STREAM_MUSIC, 0);
    	
    	musics[0] = (ImageView)findViewById(R.id.music_image_1);
    	musics[1] = (ImageView)findViewById(R.id.music_image_2);    	
    	musics[2] = (ImageView)findViewById(R.id.music_image_3);
    	musics[3] = (ImageView)findViewById(R.id.music_image_4);
    	
   
    	musicAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    	musicAnimation.setRepeatMode(Animation.INFINITE);
    	musicAnimation.setRepeatCount(-1);
    	
    	//ogg 파일 로드
    	rawResIds[0] = soundPool.load(getApplicationContext(), R.raw.autumn_d, 1);
    	rawResIds[1] = soundPool.load(this, R.raw.bliss_g1_1, 1);
    	rawResIds[2] = soundPool.load(getBaseContext(), R.raw.time_b, 1);
    	rawResIds[3] = soundPool.load(getBaseContext(), R.raw.chau_back, 1);
    	musics[0].setOnTouchListener(new View.OnTouchListener(){		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				  if((event.getAction() == MotionEvent.ACTION_DOWN)){
					  if(isSounds[0]){
						  threads[0] = new MusicThread(isSounds[0], 0);		  					  
						  threads[0].start();
						  musics[0].startAnimation(musicAnimation);
					  }else{
					      musics[0].clearAnimation();
					      threads[0].setPlay(isSounds[0]);
				      }
				  }
				  isSounds[0] = !isSounds[0];
				  return false;
			}
		});
    	musics[1].setOnTouchListener(new View.OnTouchListener(){		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				  if((event.getAction() == MotionEvent.ACTION_DOWN)){
					  if(isSounds[1]){
						  threads[1] = new MusicThread(isSounds[1], 1);		  					  
						  threads[1].start();
						  musics[1].startAnimation(musicAnimation);
					  }else{
					      musics[1].clearAnimation();
					      threads[1].setPlay(isSounds[1]);
				      }
				  }
				  isSounds[1] = !isSounds[1];
				  return false;
			}
		});
    	musics[2].setOnTouchListener(new View.OnTouchListener(){		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				  if((event.getAction() == MotionEvent.ACTION_DOWN)){
					  if(isSounds[2]){
						  threads[2] = new MusicThread(isSounds[2], 2);		  					  
						  threads[2].start();
						  musics[2].startAnimation(musicAnimation);
					  }else{
					      musics[2].clearAnimation();
					      threads[2].setPlay(isSounds[2]);
				      }
				  }
				  isSounds[2] = !isSounds[2];
				  return false;
			}
		});
    	musics[3].setOnTouchListener(new View.OnTouchListener(){		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 if((event.getAction() == MotionEvent.ACTION_DOWN)){
					  if(isSounds[3]){
						  threads[3] = new MusicThread(isSounds[3], 3);		  					  
						  threads[3].start();
						  musics[3].startAnimation(musicAnimation);
					  }else{
					      musics[3].clearAnimation();
					      threads[3].setPlay(isSounds[3]);
				      }
				  }
				  isSounds[3] = !isSounds[3];
				  
				  return false;
			}
		});
    	
    	volumnBar = (SeekBar)findViewById(R.id.volumn_bar);
    	/*
    	AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
    	volumnBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
    	audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumnBar.getProgress(), AudioManager.FLAG_SHOW_UI);
    	*/
    	volumnBar.setProgress(50);
    	
    	final int rawLength = rawResIds.length;
    	volumnBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {	
			@Override
			public void onStopTrackingTouch(SeekBar seekBar){}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar){}		
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser){
				if(fromUser){
					for(int i = 0 ; i < rawLength ; i++){
						if( isSounds[i]){
							soundPool.setVolume(rawResIds[i], volumnBar.getProgress()/100.f, volumnBar.getProgress()/100.f);
							/*
							 * 시스템 볼륨을 안드로이드에서 조절하고 싶을 때
							((AudioManager)getSystemService(AUDIO_SERVICE)).setStreamVolume(
									AudioManager.STREAM_MUSIC,
									progress,
									AudioManager.FLAG_SHOW_UI
								);
							*/
						}
					}
				}
			}
		});
    	
    	speedBar = (RatingBar)findViewById(R.id.speed_bar);
    	speedBar.setRating(1);
    	speedBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {	
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		         if(fromUser){
		        	 Toast.makeText(getApplicationContext(), " 음원의 스피드가 변합니다 ", 1000).show();
		        	 for(int i = 0 ; i < rawLength ; i++){
							if( isSounds[i]){
								soundPool.setRate(rawResIds[i], rating);
							}
					 }
		         }
			}
		});
	}
	/*
	 *  재생하는 Thread
	 */
	private class MusicThread extends Thread{
		private int isSoundsIdx;
		private boolean isPlaying ;
		private long musicSpeed;
		public MusicThread(boolean isPlaying, int isSoundsIdx){
			super();
			this.isPlaying = isPlaying;
			this.isSoundsIdx = isSoundsIdx;
		}
		public void run(){
			while(isPlaying){
			 try{
				long sleepTime = 0 ;
				//음원의 스피드
	            float tempSpeed = speedBar.getRating();
	            if( tempSpeed <= 1 ){
	            	musicSpeed = 1;
	            	sleepTime = 7950;
	            }else if(tempSpeed <= 3){
	            	musicSpeed = 2;
	            	sleepTime = 3950;
	            }else if(tempSpeed > 3){
	            	musicSpeed = 4;
	            	sleepTime = 1950;
	            }
	            if(soundPool != null){
				  musicHandler.post(new Runnable(){
					 public void run(){						   
						   soundPool.play(rawResIds[isSoundsIdx], volumnBar.getProgress()/100.f, volumnBar.getProgress()/100.f, 1, 0, musicSpeed );
					 }
				  });
	            }
				sleep(sleepTime);
			 }catch(InterruptedException ie){
				 this.interrupt();
			 }
			}
		}
		public void  setPlay(boolean isPlaying){
			this.isPlaying = isPlaying;
		}
	}
    @Override 
    public void onPause(){
    	super.onPause();
    	if( soundPool != null){
    		soundPool.release();
    		soundPool = null;
    	}
    }
}