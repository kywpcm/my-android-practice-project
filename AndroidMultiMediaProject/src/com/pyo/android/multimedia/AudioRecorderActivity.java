/*
 *  �ڵ带 ���� ���� ���� �ϱ�
 *  10�� ���ȸ� ������ ���� ��
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AudioRecorderActivity extends Activity {
  private final static String DEBUG = "AudioRecorderActivity";
  private MediaRecorder audioRecorder;
  private 	MediaPlayer    audioPlayer;
  
  private File audioRecordedDirectory;
  private String currentRecordingFileName;
  
  private EditText editProgress;
  private Button btnAudioPlayStart; //�������� ���
  private EditText editRecordingSubject; //�������� ����
  private EditText editRecordingDescription; //���� ���� ����
  private Button btnRecordingInsert; //���� ���� ����
  private Button btnRecordingCancel; //���� ���� ���
  
  private ImageView imageAudioRecordingFlag; //���� ����
  
  private boolean isSoundProcess; //�̵�� �÷��̾� ����� ����
  @Override
  public void onCreate(Bundle bundle){
	  super.onCreate(bundle);
	  //ȭ���� ���� ���� 
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
	  setContentView(R.layout.audio_recorder_layout);
	  
	  editProgress = (EditText)findViewById(R.id.edit_recording_progress);
	  btnAudioPlayStart = (Button)findViewById(R.id.btn_audio_play_start);
	  editRecordingSubject = (EditText)findViewById(R.id.edit_recording_subject);
	  editRecordingDescription = (EditText)findViewById(R.id.edit_recording_description);
	  btnRecordingInsert = (Button)findViewById(R.id.btn_recording_insert);
	  btnRecordingCancel = (Button)findViewById(R.id.btn_recording_cancel);
	  
	  imageAudioRecordingFlag = (ImageView)findViewById(R.id.image_audio_recording_flag);
	  isSoundProcess = false; //���� �ϸ� ����� �÷��̾ ��� ���� �ƴ��� �ǹ�
	  audioRecordedDirectory = Environment.getExternalStorageDirectory();
	  
	  //���� ���� �� �߻�
	  imageAudioRecordingFlag.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				  case MotionEvent.ACTION_DOWN:
					// ���� ���� �÷��� ������ üũ
					if (isSoundProcess){
						Toast.makeText(getApplicationContext(),"���� ����̽� ��� ��!", Toast.LENGTH_SHORT).show();
					} else {
						imageAudioRecordingFlag.setImageResource(R.drawable.recpush);
						startVoiceRecording();
					}
					break;
				case MotionEvent.ACTION_UP:
					imageAudioRecordingFlag.setImageResource(R.drawable.rec);
					break;
				}
				return true;
			}
		});
	  //���� �� ���� ��� ����
	  btnAudioPlayStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// ���� ���� �÷��� ��Ȳ�� üũ
				if (isSoundProcess) {
					Toast.makeText(getApplicationContext(),
							"���� ����� �Դϴ�!", Toast.LENGTH_SHORT).show();
				}else{
					editProgress.setText("���� ��� ����!");
					startVoicePlayStart();
				}
			}
		});
	  //���� ������ ����� ���ι��̴��� ����
	  btnRecordingInsert.setOnClickListener(new View.OnClickListener(){
		  public void onClick(View v) {
			  String message = null;
			  if( isSoundProcess == false ){
				  ContentValues values = new ContentValues();
				  long fileFlag =  System.currentTimeMillis()/1000;
				  if( editRecordingSubject != null && editRecordingSubject.getText().toString().length() > 0 ){
					  values.put(MediaStore.MediaColumns.TITLE, editRecordingSubject.getText().toString());
				  }else{
					  values.put(MediaStore.MediaColumns.TITLE, "�����Ÿ��Ʋ_" + fileFlag);
				  }
	              values.put(MediaStore.Audio.Media.ALBUM, "��������ڵ����_" + fileFlag);
	              values.put(MediaStore.Audio.Media.ARTIST, "ǥ�μ�_" + fileFlag);
	              if( editRecordingDescription != null && editRecordingDescription.getText().toString().length() > 0  ){
	                 values.put(MediaStore.Audio.Media.DISPLAY_NAME , editRecordingDescription.getText().toString());
	              }else{
	            	 values.put(MediaStore.Audio.Media.DISPLAY_NAME , "����� ���� ����_ " + fileFlag);
	              }
	              //���Ҹ� ��Ͽ�  �߰�
	              values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
	              // ���� ��� ��Ͽ� ��Ÿ��
	              values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
	              values.put(MediaStore.MediaColumns.DATE_ADDED,
	              		     System.currentTimeMillis() / 1000);
	              values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
	              values.put(MediaStore.Audio.Media.DATA, audioRecordedDirectory + currentRecordingFileName);
	              Uri audioUri = getContentResolver().insert(
	                	                     MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
	              if (audioUri == null) {
	                  Log.e(DEBUG, "Content resolver ���忡 ���� �߽��ϴ�!");
	                  message = " ���� �� ���� �߻�! �αױ���� Ȯ���ϼ���";
	                  return;
	              }else{
	            	  Log.i(DEBUG, "Audio Uri Path = " + audioUri.getPath());
	            	  //����� ��ĵ �ϱ�
	                  sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, audioUri));
	                  //���Ҹ� ���(������ �ϼ���)
	                  //RingtoneManager.setActualDefaultRingtoneUri(
	                    		//getApplicationContext(),  RingtoneManager.TYPE_RINGTONE, audioUri);
	                 message = " ���� ���� �Ǿ����ϴ�";
	              }
			  }else{
				     message = " ����� ���� ��ü�� ��� ���Դϴ�.��� �� �ٽ� ���� �ϼ���";
			  }
			  Toast.makeText(getApplication(), message, 1000).show();
		  }
	  });
	  //��ü ������ ������ ���
	  btnRecordingCancel.setOnClickListener(new View.OnClickListener(){
		  public void onClick(View v) {
		      if( currentRecordingFileName != null){
		    	  currentRecordingFileName = null;
		      }
		      if( editRecordingSubject != null){
		    	  editRecordingSubject.setText("");
		      }
		      if( editRecordingDescription != null){
		    	  editRecordingDescription.setText("");
		      }
		      Toast.makeText(getBaseContext(), " ���� ������ ��� �Ǿ����ϴ�.ó�� ���� �ٽ� ������ �ֽñ� �ٶ��ϴ�", 1500).show();
		  }
	  });
  }
  /*
   *  ������ ���� ��
   */
  private void startVoiceRecording(){
			isSoundProcess = true;
			audioRecorder = new MediaRecorder();
			audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			currentRecordingFileName = "/voice_record_name_" + System.currentTimeMillis()/1000 + ".mp4";
			
			audioRecorder.setOutputFile(audioRecordedDirectory + currentRecordingFileName);
			try {
				audioRecorder.prepare();
				audioRecorder.start();
				new RecordAsyncTask().execute();
			}catch (IllegalStateException e) {
				Log.e(DEBUG, "startVoiceRecording �߻� ! ", e);
			}catch (IOException e) {
				Log.e(DEBUG, "startVoiceRecording �߻� ! ", e);
			}
   }
   /*
    *  ���ڵ� ���� 
    */
	private void  stopVoiceRecording(){
		audioRecorder.stop();
	    audioRecorder.release();
	    audioRecorder = null;
		isSoundProcess = false;
	}
   //���ڴ� ������� .
	private class RecordAsyncTask extends AsyncTask<String, Integer, Long> {
		// Background Thread ���� �����ϴ� �ڵ�
		protected Long doInBackground(String... s) {
			Long totalSize = 0l;
			try {
				for (int waitTime = 0; waitTime < 100; waitTime++) {
					Thread.sleep(100);
					publishProgress(waitTime); 
				}
			} catch (InterruptedException e) {
				Log.e(DEBUG, "doInBackground���� �߻� ! ", e);
			}
			return totalSize;
		}
		// UI Thread ���� �����ϴ� �ڵ�
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0] * 100); // setProgress �� 0���� 10000 ����
			editProgress.setText(String.format("%2.1f ��",((float) (100 - progress[0]) / 10)));
		}
		// UI Thread ���� �����ϴ� �ڵ�
		protected void onPreExecute() {
			setProgress(0); // setProgress �� 0���� 10000 ����
			editProgress.setText("���� ����!");
		}

		// UI Thread ���� �����ϴ� �ڵ�
		// return ���� UI Thread �� ����
		protected void onPostExecute(Long result) {
			editProgress.setText("���� ����!");
			stopVoiceRecording();
		}
	}
	/*
	 *  ������ ���� ��� ����
	 */
	private void startVoicePlayStart(){
			try {
				isSoundProcess = true;
				audioPlayer = new MediaPlayer();
				audioPlayer.setDataSource(audioRecordedDirectory + currentRecordingFileName);
				audioPlayer.prepare();
				audioPlayer.start();
				audioPlayer.setOnCompletionListener(new OnCompletionListener() {		
					public void onCompletion(MediaPlayer arg0) {
						stopVoicePlay();
					}
				});
			} catch (IllegalArgumentException e) {
				Log.e(DEBUG, "startVoicePlayStart �߻� ! ", e);
			} catch (IllegalStateException e) {
				Log.e(DEBUG, "startVoicePlayStart �߻� ! ", e);
			} catch (IOException e) {
				Log.e(DEBUG, "startVoicePlayStart �߻� ! ", e);
			}
	}
	/*
	 *  ���� ��� ����
	 */
	private void stopVoicePlay(){
		audioPlayer.stop();
		audioPlayer.release();
		audioPlayer = null;
		isSoundProcess = false;
	}
}