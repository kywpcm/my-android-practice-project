/*
 *  코드를 통한 음원 녹음 하기
 *  10초 동안만 녹음이 가능 함
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
  private Button btnAudioPlayStart; //녹음내용 듣기
  private EditText editRecordingSubject; //녹음내용 제목
  private EditText editRecordingDescription; //녹음 내용 설명
  private Button btnRecordingInsert; //녹음 내용 저장
  private Button btnRecordingCancel; //녹음 내용 취소
  
  private ImageView imageAudioRecordingFlag; //녹음 시작
  
  private boolean isSoundProcess; //미디오 플레이어 사용중 유무
  @Override
  public void onCreate(Bundle bundle){
	  super.onCreate(bundle);
	  //화면은 세로 모드로 
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
	  setContentView(R.layout.audio_recorder_layout);
	  
	  editProgress = (EditText)findViewById(R.id.edit_recording_progress);
	  btnAudioPlayStart = (Button)findViewById(R.id.btn_audio_play_start);
	  editRecordingSubject = (EditText)findViewById(R.id.edit_recording_subject);
	  editRecordingDescription = (EditText)findViewById(R.id.edit_recording_description);
	  btnRecordingInsert = (Button)findViewById(R.id.btn_recording_insert);
	  btnRecordingCancel = (Button)findViewById(R.id.btn_recording_cancel);
	  
	  imageAudioRecordingFlag = (ImageView)findViewById(R.id.image_audio_recording_flag);
	  isSoundProcess = false; //시작 하면 오디오 플레이어가 사용 중이 아님을 의미
	  audioRecordedDirectory = Environment.getExternalStorageDirectory();
	  
	  //녹음 시작 시 발생
	  imageAudioRecordingFlag.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				  case MotionEvent.ACTION_DOWN:
					// 현재 사운드 플레이 유무를 체크
					if (isSoundProcess){
						Toast.makeText(getApplicationContext(),"사운드 디바이스 사용 중!", Toast.LENGTH_SHORT).show();
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
	  //녹음 된 내용 들어 보기
	  btnAudioPlayStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 현재 사운드 플레이 상황을 체크
				if (isSoundProcess) {
					Toast.makeText(getApplicationContext(),
							"사운드 사용중 입니다!", Toast.LENGTH_SHORT).show();
				}else{
					editProgress.setText("녹음 재생 시작!");
					startVoicePlayStart();
				}
			}
		});
	  //녹음 내용을 오디오 프로바이더에 저장
	  btnRecordingInsert.setOnClickListener(new View.OnClickListener(){
		  public void onClick(View v) {
			  String message = null;
			  if( isSoundProcess == false ){
				  ContentValues values = new ContentValues();
				  long fileFlag =  System.currentTimeMillis()/1000;
				  if( editRecordingSubject != null && editRecordingSubject.getText().toString().length() > 0 ){
					  values.put(MediaStore.MediaColumns.TITLE, editRecordingSubject.getText().toString());
				  }else{
					  values.put(MediaStore.MediaColumns.TITLE, "오디오타이틀_" + fileFlag);
				  }
	              values.put(MediaStore.Audio.Media.ALBUM, "오디오레코드범주_" + fileFlag);
	              values.put(MediaStore.Audio.Media.ARTIST, "표인수_" + fileFlag);
	              if( editRecordingDescription != null && editRecordingDescription.getText().toString().length() > 0  ){
	                 values.put(MediaStore.Audio.Media.DISPLAY_NAME , editRecordingDescription.getText().toString());
	              }else{
	            	 values.put(MediaStore.Audio.Media.DISPLAY_NAME , "오디오 녹음 예제_ " + fileFlag);
	              }
	              //벨소리 목록에  추가
	              values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
	              // 음악 재생 목록에 나타냄
	              values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
	              values.put(MediaStore.MediaColumns.DATE_ADDED,
	              		     System.currentTimeMillis() / 1000);
	              values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
	              values.put(MediaStore.Audio.Media.DATA, audioRecordedDirectory + currentRecordingFileName);
	              Uri audioUri = getContentResolver().insert(
	                	                     MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
	              if (audioUri == null) {
	                  Log.e(DEBUG, "Content resolver 저장에 실패 했습니다!");
	                  message = " 저장 중 에러 발생! 로그기록을 확인하세요";
	                  return;
	              }else{
	            	  Log.i(DEBUG, "Audio Uri Path = " + audioUri.getPath());
	            	  //오디오 스캔 하기
	                  sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, audioUri));
	                  //벨소리 등록(참조만 하세요)
	                  //RingtoneManager.setActualDefaultRingtoneUri(
	                    		//getApplicationContext(),  RingtoneManager.TYPE_RINGTONE, audioUri);
	                 message = " 정상 저장 되었습니다";
	              }
			  }else{
				     message = " 오디오 관련 객체를 사용 중입니다.잠시 후 다시 저장 하세요";
			  }
			  Toast.makeText(getApplication(), message, 1000).show();
		  }
	  });
	  //전체 녹음된 내용을 취소
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
		      Toast.makeText(getBaseContext(), " 녹음 내용이 취소 되었습니다.처음 부터 다시 진행해 주시기 바랍니다", 1500).show();
		  }
	  });
  }
  /*
   *  녹음을 시작 함
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
				Log.e(DEBUG, "startVoiceRecording 발생 ! ", e);
			}catch (IOException e) {
				Log.e(DEBUG, "startVoiceRecording 발생 ! ", e);
			}
   }
   /*
    *  레코딩 종료 
    */
	private void  stopVoiceRecording(){
		audioRecorder.stop();
	    audioRecorder.release();
	    audioRecorder = null;
		isSoundProcess = false;
	}
   //인자는 상관없음 .
	private class RecordAsyncTask extends AsyncTask<String, Integer, Long> {
		// Background Thread 에서 동작하는 코드
		protected Long doInBackground(String... s) {
			Long totalSize = 0l;
			try {
				for (int waitTime = 0; waitTime < 100; waitTime++) {
					Thread.sleep(100);
					publishProgress(waitTime); 
				}
			} catch (InterruptedException e) {
				Log.e(DEBUG, "doInBackground에서 발생 ! ", e);
			}
			return totalSize;
		}
		// UI Thread 에서 동작하는 코드
		protected void onProgressUpdate(Integer... progress) {
			setProgress(progress[0] * 100); // setProgress 는 0에서 10000 까지
			editProgress.setText(String.format("%2.1f 초",((float) (100 - progress[0]) / 10)));
		}
		// UI Thread 에서 동작하는 코드
		protected void onPreExecute() {
			setProgress(0); // setProgress 는 0에서 10000 까지
			editProgress.setText("녹음 시작!");
		}

		// UI Thread 에서 동작하는 코드
		// return 값은 UI Thread 로 전달
		protected void onPostExecute(Long result) {
			editProgress.setText("녹음 종료!");
			stopVoiceRecording();
		}
	}
	/*
	 *  녹음된 내용 들어 보기
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
				Log.e(DEBUG, "startVoicePlayStart 발생 ! ", e);
			} catch (IllegalStateException e) {
				Log.e(DEBUG, "startVoicePlayStart 발생 ! ", e);
			} catch (IOException e) {
				Log.e(DEBUG, "startVoicePlayStart 발생 ! ", e);
			}
	}
	/*
	 *  녹음 재생 종료
	 */
	private void stopVoicePlay(){
		audioPlayer.stop();
		audioPlayer.release();
		audioPlayer = null;
		isSoundProcess = false;
	}
}