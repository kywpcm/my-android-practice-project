/*
 *  SurfaceView를 사용한 동영상 녹화 하는 방법
 *  made by PYO IN SOO
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoRecorderSurfafceUsingActivity extends Activity implements SurfaceHolder.Callback{
   private static final String DEBUG = "VideoRecorderSurfafceUsingActivity";
   private MediaRecorder videoRecorder;
   private File videoRecordedDirectory;
   private VideoView videoView;
   private SurfaceHolder recorderViewHolder;
   private String currentRecordingFileName;
	@Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.video_recorder_surface_layout);
	   
	   videoView = (VideoView)findViewById(R.id.video_recodring_view);
	   recorderViewHolder = videoView.getHolder();
	   recorderViewHolder.addCallback(this);
	  // recorderViewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	      
	   videoRecordedDirectory = Environment.getExternalStorageDirectory();
   }
   /*
    *  <Button android:onClick="recordingKindButtonHandler" /> 구현
    */
	public void recordingKindButtonHandler(View recordingKindButton){
		switch(recordingKindButton.getId())
		{
		case R.id.btn_video_recording_start :
			try{
				videoRecordingStart();
				buttonEnabled(R.id.btn_video_recording_start);
			}catch(Exception e){
				Log.e(DEBUG, "btn_video_recording_start 버튼에서 발생!", e);
			}
			break;
		case R.id.btn_video_recording_stop:
				videoRecordingStop();
				buttonEnabled(R.id.btn_video_recording_stop);
			break;
		case R.id.btn_video_playback_start:
			   videoPlayStart();
			   buttonEnabled(R.id.btn_video_playback_start);
			break;
		case R.id.btn_video_playback_stop:
			videoPlayStop();
			buttonEnabled(R.id.btn_video_playback_stop);
			break;
		}
	}
	/*
	 *  동영상 녹화 시작
	 */
	private void videoRecordingStart() throws Exception{
		if( videoRecorder != null){
			videoRecorder.release();
			videoRecorder = null;
		}
		currentRecordingFileName = "video_surface_file_name_" + System.currentTimeMillis()/1000 + ".mp4";
		File recodingOutFile = new File(videoRecordedDirectory,currentRecordingFileName );
		try{
			videoRecorder = new MediaRecorder();
			//카메라를 동영상 레코더로 사용
			videoRecorder.setVideoSource(
					MediaRecorder.VideoSource.CAMERA);
			videoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			//videoRecorder.setVideoSize(324, 244);
			//videoRecorder.setVideoFrameRate(20);
			videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			videoRecorder.setMaxDuration(60000); //녹화는 60초로 제한 한다
			//서비스를 비디오레코드에 등록
			videoRecorder.setPreviewDisplay(recorderViewHolder.getSurface());
			//자주 사용하는 파일 입/출력 형식에 대한 정의를 지정 한다
			FileOutputStream toFile = new FileOutputStream(recodingOutFile);
			FileDescriptor fd = toFile.getFD();
		    videoRecorder.setOutputFile(fd)	;
		    
		    videoRecorder.prepare();
		    videoRecorder.start();
		}catch(Exception e){
			Log.e(DEBUG, "videoRecordingStart에서 발생!", e);
		}
	}
	/*
	 *  동영상 종료시 내용 저장
	 */
	private void videoRecordingStop(){
		if( videoRecorder != null){
		  try{
			videoRecorder.stop();
			videoRecorder.reset();
			videoRecorder.release();
			videoRecorder = null;
		  }catch(IllegalStateException state){
			  Log.e(DEBUG, "videoRecordingStop에서 발생!", state);
			  return;
		  }
		}
		 ContentValues values = new ContentValues(10);
		 
		 long recordInfo = System.currentTimeMillis()/1000;
         //ContentProvider인 MediaStore에 해당 녹화 영상 저장
         values.put(MediaStore.MediaColumns.TITLE, "영상녹화_" + recordInfo);
         values.put(MediaStore.Video.Media.DISPLAY_NAME, "영상녹화테스트파일_" + recordInfo);

         values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
         values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
         String storedRecodingFile = Environment.getExternalStorageDirectory() + "/" + currentRecordingFileName;
         values.put(MediaStore.Video.Media.DATA, storedRecodingFile);
   
         //비디오 컨텐트 프로바이더에 저장 함
         Uri storedRecodingFileURI = getContentResolver().insert(
         		MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
         if (storedRecodingFileURI == null){
             Log.d(DEBUG, "vodeoRecordingStop 저장 중 문제 발생!" );
             return;
         }
         
         Log.d(DEBUG, "storedRecodingFileURI  Path = " + storedRecodingFileURI.getPath());
         //미디어를 스캔하여 해당 매체 라이브러리에  추가 하도록 함
         sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, storedRecodingFileURI));
	}
	/*
	 *  녹화된 동영상 플레이 시작
	 */
	private void videoPlayStart(){
		MediaController mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);
		String filePath = videoRecordedDirectory +"/" +currentRecordingFileName;
		videoView.setVideoPath(filePath);
		videoView.start();
	}
	/*
	 *  녹화된 동영상 플레이 종료
	 */
	private void videoPlayStop(){
		if( videoView != null){
			videoView.stopPlayback();
		}
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder){
	    findViewById(R.id.btn_video_recording_start).setEnabled(true);
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
		Log.e(DEBUG, "surfaceChanged=" + "넓이=>"+ width + ", 높이=>" + height);
	}
	private void buttonEnabled(int buttonResID){
		switch(buttonResID)
		{
		case R.id.btn_video_recording_start :
				findViewById(R.id.btn_video_recording_start).setEnabled(false);
				
				findViewById(R.id.btn_video_recording_stop).setEnabled(true);
				findViewById(R.id.btn_video_recording_stop).requestFocus();
				
				findViewById(R.id.btn_video_playback_start).setEnabled(false);
				findViewById(R.id.btn_video_playback_stop).setEnabled(false);
			break;
		case R.id.btn_video_recording_stop:
			   findViewById(R.id.btn_video_recording_start).setEnabled(false);
			   findViewById(R.id.btn_video_recording_stop).setEnabled(false);
			   
			   findViewById(R.id.btn_video_playback_start).setEnabled(true);
			   findViewById(R.id.btn_video_playback_start).requestFocus();
			   
			   findViewById(R.id.btn_video_playback_stop).setEnabled(false);
			break;
		case R.id.btn_video_playback_start:
			   findViewById(R.id.btn_video_recording_start).setEnabled(false);
			   findViewById(R.id.btn_video_recording_stop).setEnabled(false);
			   findViewById(R.id.btn_video_playback_start).setEnabled(false);
			   
			   findViewById(R.id.btn_video_playback_stop).setEnabled(true);
			   findViewById(R.id.btn_video_playback_stop).requestFocus();
			break;
		case R.id.btn_video_playback_stop:
			   findViewById(R.id.btn_video_recording_start).setEnabled(true);
			   findViewById(R.id.btn_video_recording_start).requestFocus();
			   
			   findViewById(R.id.btn_video_recording_stop).setEnabled(false);
			   findViewById(R.id.btn_video_playback_start).setEnabled(false);
			   findViewById(R.id.btn_video_playback_stop).setEnabled(false);
			break;
		}
	}
}