/*
 *   음원재생 관련 예제(인텐트사용 및 직접구현)
 *   made By PYO IN SOO
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AudioMediaPlayerActivity extends Activity {
   private ListView audioList;
   //두개의 커서 결과를 병합하기위함
   private MatrixCursor  matrixAudioCursor;
   //인텐트로 보낼 앨범 ID값들
   private ArrayList<String> intentSenderIDs = new ArrayList<String>();
   //인텐트로 보낼 앨범아트 값
   private ArrayList<String> intentAlbumArts = new ArrayList<String>();
   
   private Cursor audioCursor ;
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.audio_list_layout);
	   
	   Button btnActionMediaSearch = 
			       (Button)findViewById(R.id.intent_media_search);
	   //음원리스트 화면 출력
	   btnActionMediaSearch.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/*
			 * 현재 오디오 파일을 리스트로 출력 해 줌
			 * 다음엑스트라를 설정하면 해당 음원을 검색해서 리스트에 출력 해줌
			 * EXTRA_MEDIA_ARTIST,EXTRA_MEDIA_ALBUM,EXTRA_MEDIA_TITLE,EXTRA_MEDIA_FOCUS
			 */
			Intent mediaSearch = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
			mediaSearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mediaSearch);
		}
	   });
	   Button btnActionMediaPlayer = (Button)findViewById(R.id.intent_media_player);
	   //음원 전체 정보
	   btnActionMediaPlayer.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//현재 오디오 파일을 리스트로 출력 해 줌
			//Intent mediaSearch = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
			Intent mediaPlayer = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
			mediaPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mediaPlayer);
		}
	   });
	    //API 9 버전이상 부터 동작(2.3)
	   //에뮬레이터에서는 아직 구현 되어 있지 않음
	   //단말기에서 테스트 요망
	   Button btnActionMediaPlayFromSearch =   (Button)findViewById(R.id.intent_media_play_from_search);
	   if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
		   btnActionMediaPlayFromSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent mediaPlayFromSearch = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
				Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "1");
				mediaPlayFromSearch.setData(uri);
				mediaPlayFromSearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(mediaPlayFromSearch);	
			}
		   });
	   }else{
		   btnActionMediaPlayFromSearch.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), " 안드로이드 2.3 버전이상에서만 동작 합니다", 1000).show();
				}
			   });
	   }
	   //ACTION_VIEW를 사용한  미디어 플레이어 동작
	   Button btnActionViewMedia =   (Button)findViewById(R.id.action_view_media);
	   btnActionViewMedia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				//내 단말기의 Uri
				//Uri uri = Uri.parse("file:///sdcard/melon/Dire Straits-01-Sultans Of Swing-128.mp3");
				//에물레이터서 실행
				Uri uri = Uri.parse("file:///sdcard/Dire Straits-01-Sultans Of Swing-128.mp3");
				intent.setDataAndType(uri, "audio/mp3");
				startActivity(intent);
				/*
				 * 이렇게 해도 됨(실제 단말기에서 실행)
				 * Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "1");
				 *  Intent returnIt = new Intent(Intent.ACTION_VIEW, uri);
				 *  startActivity(returnIt);
				 */
			}
		});
	   audioList = (ListView)findViewById(R.id.audio_list_view);
   }
   public void onResume(){
	   super.onResume();
	   String[] projection = {
			   MediaStore.MediaColumns._ID,
				MediaStore.Audio.AudioColumns.TITLE,
				MediaStore.Audio.AudioColumns.DURATION,
				MediaStore.Audio.ArtistColumns.ARTIST,
				MediaStore.Audio.AlbumColumns.ALBUM_ID
			  };
	   audioCursor = getContentResolver().query(
			   //내부적으로 audio View 가 실행 됨
			   MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			   projection,
				null,null, null);//MediaStore.MediaColumns._ID + " ASC");
	   //두개의 커서 결과를 병합시켜 하나의 커서 매트릭스에 저장 한다.
	   matrixAudioCursor = new MatrixCursor(
			         new String[]{"_id", "title","album_art", "duration","artist"}); //메트릭스에서 사용할 컬럼명 설정
	   audioCursor.moveToFirst();
	   while(!audioCursor.isAfterLast()){
		   long audioID = audioCursor.getInt(0);
		   //앨범아트를 찾기위해 조건문으로 사용될 album id 값
		   long albumID = audioCursor.getInt(4);
		   
		   //인텐트 호출시 넘길 id값들을 ArrayList에 세팅한다
		   intentSenderIDs.add(String.valueOf(audioID));
		   //앨범아트에  audio id를 이용해 접근 한다.
		   //내부적으로 album_info View가 실행 됨
		   Uri albumArtUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumID);
	
		   Cursor albumArtCursor = this.getContentResolver().query(albumArtUri,
                   new String[]{
                       MediaStore.Audio.AlbumColumns.ALBUM_ART
                   }, 
                   null,null,null);
		   String albumArtData = null;
		   if( albumArtCursor.getCount() <= 0 ){
			   // 앨범아트가 없다면 디폴트 앨범아트로 설정
			   albumArtData = "NO_ALBUM_ART";
		   }else{
		       albumArtCursor.moveToFirst();
		       //앨범 아트를 가져온다
		       albumArtData = albumArtCursor.getString(albumArtCursor.getColumnIndex(
		    		      MediaStore.Audio.AlbumColumns.ALBUM_ART));
		       if( albumArtData == null || albumArtData.equals("null")){
		    	   albumArtData = "NO_ALBUM_ART";
		       }
		   }
		   
		   //인텐트로 보내기 위함
		   intentAlbumArts.add(albumArtData);
		   
		   String title = audioCursor.getString(1);
		   int  playbackTime = audioCursor.getInt(2);
		   String artistName = audioCursor.getString(3);
		   matrixAudioCursor.addRow(new String[]{String.valueOf(audioID), title,
				          albumArtData, String.valueOf(playbackTime), artistName});
		   audioCursor.moveToNext();
		   albumArtCursor.close();
	   }
	   audioCursor.close();
	   audioList.setAdapter( new AudioPlayListAdapter(this,matrixAudioCursor ));
	   audioList.setOnItemClickListener(clickListener);
   }
   private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
	   @Override
		public void onItemClick(AdapterView<?> parent, View v, int pos,	long id){
		   
		   //현재 선택된 아이템으로 이동 한다
		   matrixAudioCursor.moveToPosition(pos);
		   String selectedAudioItem []  ={
				     matrixAudioCursor.getString(0),
				     matrixAudioCursor.getString(1),
					 matrixAudioCursor.getString(2),
					 matrixAudioCursor.getString(3),
					 matrixAudioCursor.getString(4)
		   };   
		   Intent audioPlayerTarget = new Intent(getApplicationContext(), MediaPlayerActivity.class);
		   audioPlayerTarget.putExtra("selected_audio_item", selectedAudioItem);
		   audioPlayerTarget.putStringArrayListExtra("audio_ids", intentSenderIDs);
		   audioPlayerTarget.putStringArrayListExtra("album_arts", intentAlbumArts);
		   
		   // Log.e("ADAPTERVIEW" , parent.getChildAt(0).getClass().getName());
		   //현재 album art를 담고 있는 ImageView 객체를 얻는다.
		   //현재 album art를 Bitmap으로 변환(Parcelable)해야 Intent를 통한 전달이 가능하기 때문
		   ImageView albumArtImageView = (ImageView)((LinearLayout)v).getChildAt(0);
		   albumArtImageView.setDrawingCacheEnabled(true);
		   Bitmap albumArtBitmap = albumArtImageView.getDrawingCache();
		   audioPlayerTarget.putExtra("albumArtBitmap", albumArtBitmap);
		   startActivity(audioPlayerTarget);
        }
   };
   class AudioPlayListAdapter extends CursorAdapter {
        
		public AudioPlayListAdapter(Context context, Cursor c) {
			super(context, c);
		}
		@Override
		public void bindView(View view, Context context, Cursor c) {
	         
			ImageView albumArt = (ImageView)view.findViewById(R.id.album_art);
			TextView audioTitle = (TextView)view.findViewById(R.id.audio_title);
			TextView playbackTime = (TextView)view.findViewById(R.id.audio_duration);
			TextView artistName = (TextView)view.findViewById(R.id.artist_name);
				
			audioTitle.setText(c.getString(1)); //노래제목
			String.valueOf(R.drawable.nell_album_art);
			String albumArtLocation = c.getString(2);
			if(albumArtLocation != null && !albumArtLocation.equalsIgnoreCase("NO_ALBUM_ART")){
				Uri albumArtUri = Uri.fromFile(new File(albumArtLocation));
				albumArt.setImageURI(albumArtUri);
			}else{
				albumArt.setImageResource(R.drawable.nell_album_art);
			}
			
			int durationTime = Integer.parseInt(c.getString(3))/1000; //재생시간
			int minute = durationTime / 60;
			int seconds = durationTime % 60;
			playbackTime.setText(String.format("%d분 %d초", minute,seconds));
			artistName.setText(c.getString(4));
		}
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent){
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.audio_list_item, null);	
			return v;
		}
	}
}
