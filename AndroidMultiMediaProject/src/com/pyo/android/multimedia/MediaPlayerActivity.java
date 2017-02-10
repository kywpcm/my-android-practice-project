/*
 *  �̵�� �÷��̾� ��Ƽ��Ƽ
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
	
	private ArrayList<String> audioIDList; //DB ID����
	private ArrayList<String> albumArts;
	private MediaPlayer mediaPlayer;
	private ImageButton btnAudioPlay;

	// audioIDList���� ���� ���� ���� ArrayList Index ��
	private int currentPlayingIdx;
	private String currentPlayingAudioDBID; // ���� ���� ���� DB ID��
	private Uri audioUriWithID; // ���� ���� ���� ����� �ڿ��� uri
	// private String currentPlayingAudioFile; //���� ����� ������ ��ġ
	
	private boolean isCurrentAudioRepeat; //���� ���� �ݺ� ����

	private SeekBar audioProgressBar; // ���� ������ ���� ����
	
	//���� ��/�� ���� ��ư Ŭ���� FLAG ��
	
	private static final int AUDIO_NEXT = 1;
	private static final int AUDIO_PREVIOUS = 2;
	
	private TextView audioProgressTime; // ��� �帧 �ð�
	private TextView audioTitle; // �뷡����
	private TextView artistName;
	private TextView playbackTime; // ����Ÿ��
	private ImageView albumArtIV; // �ٹ���Ʈ
	
	private String   timeProgressFormat = "%d:%d"; //Ÿ�� ����
	//SeekBar ���� ó�� 
	private final Handler progressBarHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if (mediaPlayer == null) {
				return;
			}
			if (mediaPlayer.isPlaying()) {
				audioProgressBar.setProgress(mediaPlayer.getCurrentPosition());
			}
			//1�� �Ŀ� �ڵ����� �� �ڵ鷯 �޼����� ������
			progressBarHandler.sendEmptyMessageDelayed(0, 1000);
		}
	};

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.audio_play_layout);

		// �̵�� �÷��̾ ���� �Ѵ�
		mediaPlayer = new MediaPlayer();

		// ����Ʈ�� �Ѱ� �ް� �� ���鰪�� �����´�
		Intent receiveIntent = getIntent();
		Bundle receiveBundle = receiveIntent.getExtras();

		// �ε������� ���� "_id", "title","album_art", "duration","artist"���� ����
		String receiveAudioItem[] = receiveBundle.getStringArray("selected_audio_item");
		// ��ü Audio DB�� ID���� �Ѱ� �޴´�
		audioIDList = receiveBundle.getStringArrayList("audio_ids");
		albumArts  = receiveBundle.getStringArrayList("album_arts");
		
	
		// ����Ʈ�� �Ѿ�� ���� ������ �ٹ� ��Ʈ
		Bitmap receiveAlbumArtBitmap = (Bitmap) receiveBundle.getParcelable("albumArtBitmap");

		// ���� Media DB ID���� ����
		currentPlayingAudioDBID = receiveAudioItem[0];

		// ���� DB ID�� �ش��ϴ� ArrayList�� �ε��� ���� ����
		currentPlayingIdx = findArrayListIndxFromDBID(receiveAudioItem[0]);
	
		// �Ѿ�� ���� �̿��Ͽ� ���� UI���� ���� �Ѵ�
		audioTitle = (TextView) findViewById(R.id.player_audio_title);
		audioTitle.setText(receiveAudioItem[1]);

		artistName = (TextView) findViewById(R.id.player_artist_name);
		artistName.setText(receiveAudioItem[4]);

		playbackTime = (TextView) findViewById(R.id.audio_playback_time);
		playbackTime.setText(durationTimeFormatChange(receiveAudioItem[3]));

		audioProgressTime = (TextView) findViewById(R.id.audio_progress_time);

		albumArtIV = (ImageView) findViewById(R.id.player_album_art);
		albumArtIV.setImageBitmap(receiveAlbumArtBitmap);

		// ����� ��� ��ư(���, ��ø���)
		btnAudioPlay = (ImageButton) findViewById(R.id.btn_play);
		// �̺�Ʈ ���
		btnAudioPlay.setOnClickListener(audioClickPlayAndPause);

		// ��� ��� �ϰ� ���� �� ���� �ؾ���
		 final Button btnAudioRepeat = (Button)findViewById(R.id.btn_audio_repeat);
		 btnAudioRepeat.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
			    if( mediaPlayer == null){
			    	mediaPlayer = new MediaPlayer();
			    }
			    //���� ���� ���� ���� ����
			    isCurrentAudioRepeat = !isCurrentAudioRepeat;
			    mediaPlayer.setLooping(isCurrentAudioRepeat);
			    String message = null;
			    if(isCurrentAudioRepeat){
			    	message = " ���� �뷡�� �ݺ� �մϴ�.";
			    	btnAudioRepeat.setBackgroundResource(R.drawable.ic_media_looping);
			    }else{
			    	message ="  �ݺ��� ���� �մϴ�.";
			    	btnAudioRepeat.setBackgroundResource(R.drawable.ic_media_audio_repeat);
			    }
			    Toast.makeText(getApplication(), message, 1500).show();
			}
		 });

		// ���� ��������� �ٷ� ��/�� ������ ��� ��
		Button btnBack = (Button) findViewById(R.id.btn_audio_back);
		Button btnAudioNext = (Button) findViewById(R.id.btn_audio_next);
		btnBack.setOnClickListener(clickNextORPrevious);
		btnAudioNext.setOnClickListener(clickNextORPrevious);

		// �̵���÷��̾ �Ϸ� ���� �� �ڵ� ȣ��
		mediaPlayer.setOnCompletionListener(mediaCompleteListener);
		// �̵�� �÷��̾��� ��ġ�� ����(����) �Ǿ��� �� ȣ��
		mediaPlayer.setOnSeekCompleteListener(mediaOnSeekCompleteListener);
		//������ ���� �߻��� ó���ϴ� �ݹ� �޼ҵ�
		mediaPlayer.setOnErrorListener(errorListener);

		// ���α׷��ú��
		audioProgressBar = (SeekBar) findViewById(R.id.audio_progress_bar);
		//��ũ�� ���� ���
		audioProgressBar.setOnSeekBarChangeListener(progressBarListener);
	}
	@Override
	public void onStart() {
		super.onStart();
		//���� Audio DB�� ���� �Ѵ�
		audioUriWithID = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currentPlayingAudioDBID);
		// Cursor audioFileCursor = managedQuery(audioUri,
		// new String[] { MediaStore.MediaColumns.DATA }, null, null, null);
		// audioFileCursor.moveToFirst();
		// currentPlayingAudioFile = audioFileCursor.getString(0);
		// audioFileCursor.close();
		// setMediaAudioResource(currentPlayingAudioFile); //�̷��� �ص� ��
		// �����ϰ� ����
		boolean isAudioPlay = setMediaAudioResource();
		if (isAudioPlay){
			btnAudioPlay.performClick(); // ���� ����� ���� �Ѵ�
			//�÷����ϸ� �ڵ����� �� �޼����� handler�� ������
			progressBarHandler.sendEmptyMessage(0);
		}
	}

	/*
	 * Media DB ID ���� �ش��ϴ� ArrayList�� index ���� ����
	 */
	private int findArrayListIndxFromDBID(String audioID) {
		if (audioIDList != null && audioIDList.size() > 0) {
			return audioIDList.indexOf(audioID);
		}
		return -1;
	}
	

	// �÷��̾ ����� ������ġ�� �����ϰ� �غ��Ų��
	private boolean setMediaAudioResource() {
		boolean isReady = false;
		if (audioUriWithID != null) {
			try {
				mediaPlayer.setDataSource(getApplicationContext(),audioUriWithID);
				isReady = true;
			} catch (Exception ioe) {
				Log.e(this.getClass().getName()," setMediaAudioResource() ���� ���ܹ߻�!", ioe);
				return isReady;
			}
		}
		isReady = isAudioPlayPrepare();
		if (isReady) {
			// SeeKBar�� �ִ� ���� �� ����
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
			Log.e(this.getClass().getName()," isAudioPlayPrepare() ���� ���ܹ߻�!",	e);
		}
		return isPrepare;
	}
	// ������� �� ��� ���� ó�� ������
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
	//����� �Ϸ�Ǹ� �ڵ� ȣ�� ��
	private MediaPlayer.OnCompletionListener mediaCompleteListener= new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp){
			//���� ������ ����Ǹ� ���� ������ ���� ��
			new AudioMediaAsyncTask(AUDIO_NEXT).execute("dummyValue");
			mediaPlayer.reset();
			boolean flag = setMediaAudioResource();
			//���������� �� ������ ���� �Ǿ��ٸ�
			if(flag){
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
				Toast.makeText(getApplicationContext(), " ���� ������ �ڵ� ���� �˴ϴ�.", 1000).show();
			}else{
				Toast.makeText(getApplicationContext(), " ������ �־� ����� �������� ���߽��ϴ�", 1000).show();
			}
		}
	};
	//����� ��� �� ��� ��ġ�� ����Ǹ� �ڵ����� ȣ��
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
	 *  back/next ��ư Ŭ���� ó�� �� ������
	 */
	private View.OnClickListener clickNextORPrevious = new View.OnClickListener() {
		@Override
		public void onClick(View btnView) {
            //���� ������ �ٷ� �� ������ �����´�
			if (btnView.getId() == R.id.btn_audio_back) { //Previous ��ư Ŭ����
				new AudioMediaAsyncTask(AUDIO_PREVIOUS).execute("dummyValue");
			} else { // ���� ������ �����´�
				new AudioMediaAsyncTask(AUDIO_NEXT).execute("dummyValue");
			}
			//���� ������ ����
			mediaPlayer.reset();
			//���ο� ������ ����(setDataSource, prepare��Ŵ)
			boolean isPlayingFlag = setMediaAudioResource();
			//���������� �� ������ ���� �Ǿ��ٸ�
			if(isPlayingFlag){
				mediaPlayer.start();
				btnAudioPlay.setImageResource(R.drawable.ic_media_pause);
			}else{
				Toast.makeText(getApplicationContext(), " ������ �־� ����� �������� ���߽��ϴ�", 1000).show();
			}
		}
	};
	//�÷��� �� ���� �߻��� ó��
	private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Toast.makeText(getApplicationContext(),
					 " ���� �߻�! (what = " + what + ", extra = " + extra, 3000).show();
			return false; //what, extra���� �м��Ͽ� ������ ó�� �ϸ� true�� ���� �ϸ� ��
		}
	};
	// �ð� ��ȯ
	private String durationTimeFormatChange(String durationTime) {
		int playbackTime = Integer.parseInt(durationTime) / 1000;
		return String.format(timeProgressFormat, playbackTime / 60, playbackTime % 60);
	}
	// ��ũ�� ó�� �ϱ�
	private final SeekBar.OnSeekBarChangeListener progressBarListener = new SeekBar.OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {	
			if (fromUser){ //����ڿ� ���� SeekBar�� ���� �ȴٸ�	
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
				mediaPlayer.pause(); //��� ����
			}
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};
	/*
	 *  �ش� ����� ������ �������� ��׶��� ������
	 */
	private class AudioMediaAsyncTask  extends AsyncTask<String, String, String>{
		private int flagNextPrevious;
		public AudioMediaAsyncTask(int flag){
			this.flagNextPrevious = flag;
			Log.e("currentPlayingIdx", "currentPlayingIdx(������) => " + currentPlayingIdx );
		};
		//��׶��� ���� ���� ���� ����
		@Override
		public void onPreExecute(){
		   if( flagNextPrevious == AUDIO_NEXT){ //Next ������ ����
			   
			   //���� �������� Id�� index ���� 1�� ���� ���� �����´�
			   currentPlayingIdx = audioIDList.indexOf(currentPlayingAudioDBID) + 1;
			   
			   //���� �ε����� Ŭ�⸦ ����� ó�� �ε����� �ٽ� ����
			   if( currentPlayingIdx == audioIDList.size()){ 
				   currentPlayingIdx = 0 ;
			   }
		   }else{ //Previous ����
			   currentPlayingIdx = audioIDList.indexOf(currentPlayingAudioDBID) - 1;
			   if( currentPlayingIdx == -1 ){ //ó�� ������ �����Ѵٸ� �Ǹ��������� ����
				   currentPlayingIdx = audioIDList.size() - 1 ;
			   }
		   }
		   //���� Audio DB�� ID���� ����
		   currentPlayingAudioDBID = audioIDList.get(currentPlayingIdx);
		   //DB�� PK���� �̿��Ͽ� �ٽ� ����
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
		      postMessage = title + " �� ���� �մϴ�";		     
		   }catch(Exception e){
			   Log.e("AudioMediaAsyncTask"," doInBackground(String... id)���� �߻�! " + e);
			   //��׶��� ���� ����
			   this.cancel(true);
			   postMessage =  " TEST ������ �߻��Ͽ� ���� ����� ���մϴ�.[�αױ�� Ȯ�� �ٶ�]!";
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
	//����� ������ ���� �Ѵ�
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