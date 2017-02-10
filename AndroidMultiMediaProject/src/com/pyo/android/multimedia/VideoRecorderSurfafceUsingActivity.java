/*
 *  SurfaceView�� ����� ������ ��ȭ �ϴ� ���
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
    *  <Button android:onClick="recordingKindButtonHandler" /> ����
    */
	public void recordingKindButtonHandler(View recordingKindButton){
		switch(recordingKindButton.getId())
		{
		case R.id.btn_video_recording_start :
			try{
				videoRecordingStart();
				buttonEnabled(R.id.btn_video_recording_start);
			}catch(Exception e){
				Log.e(DEBUG, "btn_video_recording_start ��ư���� �߻�!", e);
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
	 *  ������ ��ȭ ����
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
			//ī�޶� ������ ���ڴ��� ���
			videoRecorder.setVideoSource(
					MediaRecorder.VideoSource.CAMERA);
			videoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			//videoRecorder.setVideoSize(324, 244);
			//videoRecorder.setVideoFrameRate(20);
			videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			videoRecorder.setMaxDuration(60000); //��ȭ�� 60�ʷ� ���� �Ѵ�
			//���񽺸� �������ڵ忡 ���
			videoRecorder.setPreviewDisplay(recorderViewHolder.getSurface());
			//���� ����ϴ� ���� ��/��� ���Ŀ� ���� ���Ǹ� ���� �Ѵ�
			FileOutputStream toFile = new FileOutputStream(recodingOutFile);
			FileDescriptor fd = toFile.getFD();
		    videoRecorder.setOutputFile(fd)	;
		    
		    videoRecorder.prepare();
		    videoRecorder.start();
		}catch(Exception e){
			Log.e(DEBUG, "videoRecordingStart���� �߻�!", e);
		}
	}
	/*
	 *  ������ ����� ���� ����
	 */
	private void videoRecordingStop(){
		if( videoRecorder != null){
		  try{
			videoRecorder.stop();
			videoRecorder.reset();
			videoRecorder.release();
			videoRecorder = null;
		  }catch(IllegalStateException state){
			  Log.e(DEBUG, "videoRecordingStop���� �߻�!", state);
			  return;
		  }
		}
		 ContentValues values = new ContentValues(10);
		 
		 long recordInfo = System.currentTimeMillis()/1000;
         //ContentProvider�� MediaStore�� �ش� ��ȭ ���� ����
         values.put(MediaStore.MediaColumns.TITLE, "�����ȭ_" + recordInfo);
         values.put(MediaStore.Video.Media.DISPLAY_NAME, "�����ȭ�׽�Ʈ����_" + recordInfo);

         values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
         values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
         String storedRecodingFile = Environment.getExternalStorageDirectory() + "/" + currentRecordingFileName;
         values.put(MediaStore.Video.Media.DATA, storedRecodingFile);
   
         //���� ����Ʈ ���ι��̴��� ���� ��
         Uri storedRecodingFileURI = getContentResolver().insert(
         		MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
         if (storedRecodingFileURI == null){
             Log.d(DEBUG, "vodeoRecordingStop ���� �� ���� �߻�!" );
             return;
         }
         
         Log.d(DEBUG, "storedRecodingFileURI  Path = " + storedRecodingFileURI.getPath());
         //�̵� ��ĵ�Ͽ� �ش� ��ü ���̺귯����  �߰� �ϵ��� ��
         sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, storedRecodingFileURI));
	}
	/*
	 *  ��ȭ�� ������ �÷��� ����
	 */
	private void videoPlayStart(){
		MediaController mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);
		String filePath = videoRecordedDirectory +"/" +currentRecordingFileName;
		videoView.setVideoPath(filePath);
		videoView.start();
	}
	/*
	 *  ��ȭ�� ������ �÷��� ����
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
		Log.e(DEBUG, "surfaceChanged=" + "����=>"+ width + ", ����=>" + height);
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