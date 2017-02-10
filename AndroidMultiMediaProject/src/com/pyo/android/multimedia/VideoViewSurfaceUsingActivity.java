/*
 *  SurfaceView�� �̿��� ���� �÷��̾� ����
 *  made by PYO IN SOO
 */
package com.pyo.android.multimedia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class VideoViewSurfaceUsingActivity extends Activity{
	private ProgressDialog  videoLoading ;
	private  MediaPlayer mediaPlayer;
	
    private SeekBar   videoProgressBar; // ���� ������ ���� ����
	private TextView videoProgressTime; // ��� �帧 �ð�
	private TextView videoPlaybackTime; // ����Ÿ��
	private ImageButton btnVideoPlay; //��� ����
	private VideoPlaySurfaceView  videoSurfaceView;
	
	private String   timeProgressFormat = "%d:%d"; //Ÿ�� ����
	//SeekBar ���� ó�� 
	private final Handler progressBarHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if (mediaPlayer == null) {
				return;
			}
			if (mediaPlayer.isPlaying()) {
				videoProgressBar.setProgress(mediaPlayer.getCurrentPosition());
			}
			//1�� �Ŀ� �ڵ����� �� �ڵ鷯 �޼����� ������
			progressBarHandler.sendEmptyMessageDelayed(0, 1000);
		}
	};
	@Override
   public void onCreate(Bundle initBundle){
	   super.onCreate(initBundle);
	   setContentView(R.layout.video_play_layout);
	   
	   FrameLayout  frameIncludeSurface = (FrameLayout)findViewById(R.id.frame_in_surface);
	   videoSurfaceView = new VideoPlaySurfaceView(this);
	   frameIncludeSurface.addView(videoSurfaceView);
	   
	   videoPlaybackTime = (TextView) findViewById(R.id.video_playback_time);
	   videoProgressTime = (TextView)findViewById(R.id.video_progress_time);
	   btnVideoPlay = (ImageButton) findViewById(R.id.btn_video_play);
	   btnVideoPlay.setOnClickListener(videoClickPlayAndPause);
	   videoLoading = new ProgressDialog(this);
	   progressBarHandler.post(new Runnable(){
		   public void run(){	   
			   videoLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			   videoLoading.setMessage("�ε� �� �Դϴ� ��ø� ��ٷ� �ֽʽÿ�....");
			   videoLoading.show();
		   }
	   });
	   
	   // ���α׷��ú��
		videoProgressBar = (SeekBar) findViewById(R.id.video_progress_bar);
		//��ũ�� ���� ���
		videoProgressBar.setOnSeekBarChangeListener(progressBarListener);
   }
	//���� ��� �� ��� ���� ó�� ������
	private  View.OnClickListener videoClickPlayAndPause = new View.OnClickListener() {
		@Override
		public void onClick(View v){
			if(mediaPlayer != null){
				if(mediaPlayer.isPlaying()){
				    mediaPlayer.pause();
				    btnVideoPlay.setImageResource(R.drawable.ic_media_play);
				}else{
					mediaPlayer.start();
					btnVideoPlay.setImageResource(R.drawable.ic_media_pause);
				}
			}else{
				videoSurfaceView.setVideoMediaPlayer(videoSurfaceView.surfaceHolder);
			}				
		}
	};
	/*
	 * back/next ��ư ó��
	 * android:onClick="performPrevioutNext"
	 */
	//progressBarListener.onProgressChanged(videoProgressBar, mediaPlayer.getCurrentPosition() - 10000 , arg2)
   public void performPrevioutNext(View backNextButton){
	   
        if( mediaPlayer != null){
			if (backNextButton.getId() == R.id.btn_video_back) { //Previous ��ư Ŭ����
				//��ġ�ÿ� 10�� �� �ڷ�
				int backLocation =  mediaPlayer.getCurrentPosition() - 10000;
				if( backLocation < 0){
					backLocation = 0 ;
				}
				progressBarListener.onProgressChanged(videoProgressBar, backLocation , true);
			}else{ // NEXT ��ư Ŭ����
				int nextLocation =  mediaPlayer.getCurrentPosition() + 10000;
				if( nextLocation >= mediaPlayer.getDuration()){
					Toast.makeText(getApplicationContext(), "������ ��ġ �Դϴ�", 1500).show();
					mediaPlayer.pause();
					btnVideoPlay.setImageResource(R.drawable.ic_media_play);
				}else{
					progressBarListener.onProgressChanged(videoProgressBar, nextLocation , true);
				}
			}
        }
   }
   private  MediaPlayer.OnPreparedListener videoPreparedListener = new MediaPlayer.OnPreparedListener(){
	   public void onPrepared(MediaPlayer mediaPlayer){
		     videoLoading.dismiss();
		   if( mediaPlayer != null && !mediaPlayer.isPlaying()){
			   videoProgressBar.setMax(mediaPlayer.getDuration());
			   progressBarHandler.sendEmptyMessage(0);
			   mediaPlayer.start();
		   }
	   }
   };
   private  MediaPlayer.OnCompletionListener videoCompletionListener = new MediaPlayer.OnCompletionListener(){
         public void onCompletion(MediaPlayer arg0) {
           if( mediaPlayer != null){
  			   mediaPlayer.reset();
  			   mediaPlayer.release();
  			   mediaPlayer = null;
  		   }
         }
    };
 // ��ũ�� ó�� �ϱ�
	private final SeekBar.OnSeekBarChangeListener progressBarListener = new SeekBar.OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {	
			if (fromUser){ //����ڿ� ���� SeekBar�� ���� �ȴٸ�	
				mediaPlayer.seekTo(progress);
				mediaPlayer.start();
				btnVideoPlay.setImageResource(R.drawable.ic_media_pause);
			}
			progressBarHandler.post(new Runnable(){
				   public void run(){
					   int currentSeconds = progress/1000;
					   if( currentSeconds < 60){
					      videoProgressTime.setText(String.format(timeProgressFormat, 0, currentSeconds));
					   }else{
						  videoProgressTime.setText(String.format(timeProgressFormat,currentSeconds/60, currentSeconds % 60));
					   }
				   }
			});
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar){
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause(); //��� ����
			}
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar){
		}
	};
	
    class VideoPlaySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	   
	   SurfaceHolder surfaceHolder;
	   
	   public VideoPlaySurfaceView(Context context){
		    super(context);
		    surfaceViewInit();
	   }
	   public VideoPlaySurfaceView(Context context, AttributeSet attrs){
		    super(context, attrs);
		    surfaceViewInit();
	   }
	   public VideoPlaySurfaceView(Context context, AttributeSet attrs,int defStyle){
		    super(context, attrs, defStyle);
		    surfaceViewInit();
	   }
	   public void surfaceViewInit(){
		    surfaceHolder = getHolder();
		    surfaceHolder.addCallback(this);
		    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	   }
	   @Override
	   public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k){
	   }
	   @Override
	   public void surfaceDestroyed(SurfaceHolder surfaceholder){
		   if( mediaPlayer != null){
			   mediaPlayer.reset();
			   mediaPlayer.release();
			   mediaPlayer = null;
		   }
	   }
	   @Override
	   public void surfaceCreated(SurfaceHolder holder) {
		   if( mediaPlayer == null){
		       setVideoMediaPlayer(holder);
		   }
	   }
	   public void   setVideoMediaPlayer(SurfaceHolder holder){
		      if(mediaPlayer == null) {
		    	  mediaPlayer = new MediaPlayer();
	          } else {
	        	  mediaPlayer.reset();
	          }
	          try {
	        	  mediaPlayer.setDataSource(
	        			  Environment.getExternalStorageDirectory().getAbsolutePath() +
	        			  "/t_ara_music_video.mp4");
	        	  //������ Ȧ���� ����ϰ� ���񽺿� �׷� ���񽺺信 ��� �Ѵ�
	        	  mediaPlayer.setDisplay(surfaceHolder);
	        	  //������ ���
	              mediaPlayer.setOnPreparedListener(videoPreparedListener);
	        	  mediaPlayer.setOnCompletionListener(videoCompletionListener);
	        	  
	        	  mediaPlayer.prepare();
	        	  int playbackTime = mediaPlayer.getDuration()/1000;
	        	  videoPlaybackTime.setText(String.format(timeProgressFormat, playbackTime/60, playbackTime % 60));
	          } catch (Exception e) {
	              Log.e("TEST", " ���񽺺� ���� �� ����! ", e);
	          }
	   }
     }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if( mediaPlayer != null){
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
  }