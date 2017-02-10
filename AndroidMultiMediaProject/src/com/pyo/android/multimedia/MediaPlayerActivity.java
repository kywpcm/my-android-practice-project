/*
 *  미디어 플레이어 액티비티
 *  made By PYO IN SOO
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerActivity extends Activity {
	
	private ArrayList<String> audioIDList; //DB ID값들
	private ArrayList<String> albumArts;
	private MediaPlayer mediaPlayer;
	private ImageButton btnAudioPlay;

	// audioIDList에서 현재 실행 중인 ArrayList Index 값
	private int currentPlayingIdx;
	private String currentPlayingAudioDBID; // 현재 실행 중인 DB ID값
	private Uri audioUriWithID; // 현재 실행 중인 오디오 자원의 uri
	// private String currentPlayingAudioFile; //현재 오디오 파일의 위치
	
	private boolean isCurrentAudioRepeat; //현재 음원 반복 유무

	private SeekBar audioProgressBar; // 현재 음원의 진행 상태
	
	//음원 전/후 선택 버튼 클릭시 FLAG 값
	
	private static final int AUDIO_NEXT = 1;
	private static final int AUDIO_PREVIOUS = 2;
	
	private TextView audioProgressTime; // 재생 흐름 시간
	private TextView audioTitle; // 노래제목
	private TextView artistName;
	private TextView playbackTime; // 런닝타임
	private ImageView albumArtIV; // 앨범아트
	
	private String   timeProgressFormat = "%d:%d"; //타임 포맷
	//SeekBar 진행 처리 
	private final Handler progressBarHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if (mediaPlayer == null) {
				return;
			}
			if (mediaPlayer.isPlaying()) {
				audioProgressBar.setProgress(mediaPlayer.getCurrentPosition());
			}
			//1초 후에 자동으로 빈 핸들러 메세지를 보낸다
			progressBarHandler.sendEmptyMessageDelayed(0, 1000);
		}
	};

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.audio_play_layout);

		// 미디어 플레이어를 생성 한다
		mediaPlayer = new MediaPlayer();

		// 인텐트를 넘겨 받고 각 번들값을 가져온다
		Intent receiveIntent = getIntent();
		Bundle receiveBundle = receiveIntent.getExtras();

		// 인덱스별로 각각 "_id", "title","album_art", "duration","artist"값이 존재
		String receiveAudioItem[] = receiveBundle.getStringArray("selected_audio_item");
		// 전체 Audio DB의 ID값을 넘겨 받는다
		audioIDList = receiveBundle.getStringArrayList("audio_ids");
		albumArts  = receiveBundle.getStringArrayList("album_arts");
		
	
		// 인텐트로 넘어온 현재 음원의 앨범 아트
		Bitmap receiveAlbumArtBitmap = (Bitmap) receiveBundle.getParcelable("albumArtBitmap");

		// 현재 Media DB ID값을 세팅
		currentPlayingAudioDBID = receiveAudioItem[0];

		// 현재 DB ID에 해당하는 ArrayList의 인덱스 값을 설정
		currentPlayingIdx = findArrayListIndxFromDBID(receiveAudioItem[0]);
	
		// 넘어온 값을 이용하여 현재 UI값을 세팅 한다
		audioTitle = (TextView) findViewById(R.id.player_audio_title);
		audioTitle.setText(receiveAudioItem[1]);

		artistName = (TextView) findViewById(R.id.player_artist_name);
		artistName.setText(receiveAudioItem[4]);

		playbackTime = (TextView) findViewById(R.id.audio_playback_time);
		playbackTime.setText(durationTimeFormatChange(receiveAudioItem[3]));

		audioProgressTime = (TextView) findViewById(R.id.audio_progress_time);

		albumArtIV = (ImageView) findViewById(R.id.player_album_art);
		albumArtIV.setImageBitmap(receiveAlbumArtBitmap);

		// 오디오 재생 버튼(재생, 잠시멈춤)
		btnAudioPlay = (ImageButton) findViewById(R.id.btn_play);
		// 이벤트 등록
		btnAudioPlay.setOnClickListener(audioClickPlayAndPause);

		// 계속 재생 하고 싶을 때 구현 해야함
		 final Button btnAudioRepeat = (Button)findViewById(R.id.btn_audio_repeat);
		 btnAudioRepeat.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
			    if( mediaPlayer == null){
			    	mediaPlayer = new MediaPlayer();
			    }
			    //현재 음원 루핑 여부 세팅
			    isCurrentAudioRepeat = !isCurrentAudioRepeat;
			    mediaPlayer.setLooping(isCurrentAudioRepeat);
			    String message = null;
			    if(isCurrentAudioRepeat){
			    	message = " 현재 노래를 반복 합니다.";
			    	btnAudioRepeat.setBackgroundResource(R.drawable.ic_media_looping);
			    }else{
			    	message ="  반복을 해제 합니다.";
			    	btnAudioRepeat.setBackgroundResource(R.drawable.ic_media_audio_repeat);
			    }
			    Toast.makeText(getApplication(), message, 1500).show();
			}
		 });

		// 현재 재생음악의 바로 전/후 음원을 재생 함
		Button btnBack = (Button) findViewById(R.id.btn_audio_back);
		Button btnAudioNext = (Button) findViewById(R.id.btn_audio_next);
		btnBack.setOnClickListener(clickNextORPrevious);
		btnAudioNext.setOnClickListener(clickNextORPrevious);

		// 미디어플레이어가 완료 됐을 때 자동 호출
		mediaPlayer.setOnCompletionListener(mediaCompleteListener);
		// 미디어 플레이어의 위치가 변경(강제) 되었을 때 호출
		mediaPlayer.setOnSeekCompleteListener(mediaOnSeekCompleteListener);
		//진행중 에러 발생시 처리하는 콜백 메소드
		mediaPlayer.setOnErrorListener(errorListener);

		// 프로그레시브바
		audioProgressBar = (SeekBar) findViewById(R.id.audio_progress_bar);
		//시크바 변경 등록
		audioProgressBar.setOnSeekBarChangeListener(progressBarListener);
	}
	@Override
	public void onStart() {
		super.onStart();
		//현재 Audio DB로 세팅 한다
		audioUriWithID = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currentPlayingAudioDBID);
		// Cursor audioFileCursor = managedQuery(audioUri,
		// new String[] { MediaStore.MediaColumns.DATA }, null, null, null);
		// audioFileCursor.moveToFirst();
		// currentPlayingAudioFile = audioFileCursor.getString(0);
		// audioFileCursor.close();
		// setMediaAudioResource(currentPlayingAudioFile); //이렇게 해도 됨
		// 세팅하고 시작
		boolean isAudioPlay = setMediaAudioResource();
		if (isAudioPlay){
			btnAudioPlay.performClick(); // 음원 재생을 시작 한다
			//플레이하면 자동으로 빈 메세지를 handler에 보낸다
			progressBarHandler.sendEmptyMessage(0);
		}
	}

	/*
	 * Media DB ID 값에 해당하는 ArrayList의 index 값을 리턴
	 */
	private int findArrayListIndxFromDBID(String audioID) {
		if (audioIDList != null && audioIDList.size() > 0) {
			return audioIDList.indexOf(audioID);
		}
		return -1;
	}
	

	// 플레이어에 재생할 음원위치를 세팅하고 준비시킨다
	private boolean setMediaAudioResource() {
		boolean isReady = false;
		if (audioUriWithID != null) {
			try {
				mediaPlayer.setDataSource(getApplicationContext(),audioUriWithID);
				isReady = true;
			} catch (Exception ioe) {
				Log.e(this.getClass().getName()," setMediaAudioResource() 에서 예외발생!", ioe);
				return isReady;
			}
		}
		isReady = isAudioPlayPrepare();
		if (isReady) {
			// SeeKBar의 최대 진행 값 설정
			audioProgressBar.setMax(mediaPlayer.getDuration());
		}
		return isReady;
	}

	private boolean isAudioPlayPrepare() {
		boolean isPrepare = false;
		try {
			mediaPlayer.prepare();
			mediaPlayer.setLooping(isCurrentAudioRepeat);
			isPrepare = true;
		} catch (Exception e) {
			Log.e(this.getClass().getName()," isAudioPlayPrepare() 에서 예외발생!",	e);
		}
		return isPrepare;
	}
	// 음원재생 및 잠시 멈춤 처리 리스너
	private  View.OnClickListener audioClickPlayAndPause = new View.OnClickListener() {
		@Override
		public void onClick(View v){
			if (mediaPlayer.isPlaying()){
				mediaPlayer.pause();
			    btnAudioPlay.setImageResource(R.drawable.ic_media_play);
			} else {
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
				mediaPlayer.start();			 		
			}
		}
	};
	//재생이 완료되면 자동 호출 됨
	private MediaPlayer.OnCompletionListener mediaCompleteListener= new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp){
			//현재 음악이 종료되면 다음 음악을 실행 함
			new AudioMediaAsyncTask(AUDIO_NEXT).execute("dummyValue");
			mediaPlayer.reset();
			boolean flag = setMediaAudioResource();
			//정상적으로 새 음원이 세팅 되었다면
			if(flag){
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
				Toast.makeText(getApplicationContext(), " 다음 곡으로 자동 실행 됩니다.", 1000).show();
			}else{
				Toast.makeText(getApplicationContext(), " 문제가 있어 재생을 진행하지 못했습니다", 1000).show();
			}
		}
	};
	//오디오 재생 중 재생 위치가 변경되면 자동으로 호출
	private MediaPlayer.OnSeekCompleteListener mediaOnSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
		@Override
		public void onSeekComplete(MediaPlayer mp) {
			
			if (mediaPlayer.isPlaying()){
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
			}else{
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
			}
		}
	};
	/*
	 *  back/next 버튼 클릭시 처리 할 리스너
	 */
	private View.OnClickListener clickNextORPrevious = new View.OnClickListener() {
		@Override
		public void onClick(View btnView) {
            //현재 음원의 바로 전 음원을 가져온다
			if (btnView.getId() == R.id.btn_audio_back) { //Previous 버튼 클릭시
				new AudioMediaAsyncTask(AUDIO_PREVIOUS).execute("dummyValue");
			} else { // 다음 음원을 가져온다
				new AudioMediaAsyncTask(AUDIO_NEXT).execute("dummyValue");
			}
			//현재 음악을 리셋
			mediaPlayer.reset();
			//새로운 음원을 세팅(setDataSource, prepare시킴)
			boolean isPlayingFlag = setMediaAudioResource();
			//정상적으로 새 음원이 세팅 되었다면
			if(isPlayingFlag){
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
			}else{
				Toast.makeText(getApplicationContext(), " 문제가 있어 재생을 진행하지 못했습니다", 1000).show();
			}
		}
	};
	//플레이 중 에러 발생시 처리
	private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Toast.makeText(getApplicationContext(),
					 " 오류 발생! (what = " + what + ", extra = " + extra, 3000).show();
			return false; //what, extra등을 분석하여 에러를 처리 하면 true를 리턴 하면 됨
		}
	};
	// 시간 변환
	private String durationTimeFormatChange(String durationTime) {
		int playbackTime = Integer.parseInt(durationTime) / 1000;
		return String.format(timeProgressFormat, playbackTime / 60, playbackTime % 60);
	}
	// 시크바 처리 하기
	private final SeekBar.OnSeekBarChangeListener progressBarListener = new SeekBar.OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {	
			if (fromUser){ //사용자에 의해 SeekBar가 진행 된다면	
				mediaPlayer.seekTo(progress);
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
			}
			progressBarHandler.post(new Runnable(){
				   public void run(){
					   int currentSeconds = progress/1000;
					   if( currentSeconds < 60){
					      audioProgressTime.setText(String.format(timeProgressFormat, 0, currentSeconds));
					   }else{
						  audioProgressTime.setText(String.format(timeProgressFormat,currentSeconds/60, currentSeconds % 60));
					   }
				   }
			});
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause(); //잠시 멈춤
			}
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};
	/*
	 *  해당 오디오 정보를 가져오는 백그라운드 쓰레드
	 */
	private class AudioMediaAsyncTask  extends AsyncTask<String, String, String>{
		private int flagNextPrevious;
		public AudioMediaAsyncTask(int flag){
			this.flagNextPrevious = flag;
			Log.e("currentPlayingIdx", "currentPlayingIdx(생성자) => " + currentPlayingIdx );
		};
		//백그라운드 실행 전에 먼저 실행
		@Override
		public void onPreExecute(){
		   if( flagNextPrevious == AUDIO_NEXT){ //Next 음원을 선택
			   
			   //현재 실행중인 Id의 index 값에 1을 더한 값을 가져온다
			   currentPlayingIdx = audioIDList.indexOf(currentPlayingAudioDBID) + 1;
			   
			   //현재 인덱스의 클기를 벗어나면 처음 인덱스로 다시 세팅
			   if( currentPlayingIdx == audioIDList.size()){ 
				   currentPlayingIdx = 0 ;
			   }
		   }else{ //Previous 선택
			   currentPlayingIdx = audioIDList.indexOf(currentPlayingAudioDBID) - 1;
			   if( currentPlayingIdx == -1 ){ //처음 음원을 선택한다면 맨마지막으로 세팅
				   currentPlayingIdx = audioIDList.size() - 1 ;
			   }
		   }
		   //현재 Audio DB의 ID값을 세팅
		   currentPlayingAudioDBID = audioIDList.get(currentPlayingIdx);
		   //DB의 PK값을 이용하여 다시 세팅
		   audioUriWithID = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currentPlayingAudioDBID);
		}
		public  String doInBackground(String... id){
		   Cursor  audioInfoCursor = null;
		//   Cursor albumArtCursor = null;
		   String title = null;
		   String duration = null;
		   String artist = null;
		   String postMessage = null;
		   try{
			   Log.e("audioUriWithID", audioUriWithID.toString());
			   audioInfoCursor =  getContentResolver().query(audioUriWithID,
		                new String[]{
				          MediaStore.Audio.AudioColumns.TITLE,
				          MediaStore.Audio.AudioColumns.DURATION,
				          MediaStore.Audio.AudioColumns.ARTIST,
		             },null,null,null);	
		      audioInfoCursor.moveToFirst();	
		     
		      title = audioInfoCursor.getString(audioInfoCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
		      duration = audioInfoCursor.getString(audioInfoCursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
		      artist = audioInfoCursor.getString(audioInfoCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
		 
		      publishProgress(title, duration, artist);
		      postMessage = title + " 을 시작 합니다";		     
		   }catch(Exception e){
			   Log.e("AudioMediaAsyncTask"," doInBackground(String... id)에서 발생! " + e);
			   //백그라운드 실행 종료
			   this.cancel(true);
			   postMessage =  " TEST 에러가 발생하여 음원 재생을 못합니다.[로그기록 확인 바람]!";
		   }finally{
			   if( audioInfoCursor != null){
				   audioInfoCursor.close();
			   }
		   }	   
			return postMessage  ;
		}
		@Override
		 public  void onProgressUpdate(String... audioInfos) {
	         audioTitle.setText(audioInfos[0]);
	         playbackTime.setText(durationTimeFormatChange(audioInfos[1]));
	         artistName.setText(audioInfos[2]);
	         String albumArtLocation = albumArts.get(currentPlayingIdx);
	         if(albumArtLocation.equalsIgnoreCase("NO_ALBUM_ART")){
	        	 albumArtIV.setImageResource(R.drawable.nell_album_art);
	         }else{
	        	 albumArtIV.setImageURI(Uri.fromFile(new File(albumArtLocation)));
	         }
	     }
		@Override
		protected void onPostExecute(String result){
	         Toast.makeText( getApplication() , result , 1000).show();
	    }
	}
	//종료시 음원도 종료 한다
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}