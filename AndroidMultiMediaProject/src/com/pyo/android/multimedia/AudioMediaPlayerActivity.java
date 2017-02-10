/*
 *   ������� ���� ����(����Ʈ��� �� ��������)
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
   //�ΰ��� Ŀ�� ����� �����ϱ�����
   private MatrixCursor  matrixAudioCursor;
   //����Ʈ�� ���� �ٹ� ID����
   private ArrayList<String> intentSenderIDs = new ArrayList<String>();
   //����Ʈ�� ���� �ٹ���Ʈ ��
   private ArrayList<String> intentAlbumArts = new ArrayList<String>();
   
   private Cursor audioCursor ;
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.audio_list_layout);
	   
	   Button btnActionMediaSearch = 
			       (Button)findViewById(R.id.intent_media_search);
	   //��������Ʈ ȭ�� ���
	   btnActionMediaSearch.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/*
			 * ���� ����� ������ ����Ʈ�� ��� �� ��
			 * ��������Ʈ�� �����ϸ� �ش� ������ �˻��ؼ� ����Ʈ�� ��� ����
			 * EXTRA_MEDIA_ARTIST,EXTRA_MEDIA_ALBUM,EXTRA_MEDIA_TITLE,EXTRA_MEDIA_FOCUS
			 */
			Intent mediaSearch = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
			mediaSearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mediaSearch);
		}
	   });
	   Button btnActionMediaPlayer = (Button)findViewById(R.id.intent_media_player);
	   //���� ��ü ����
	   btnActionMediaPlayer.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//���� ����� ������ ����Ʈ�� ��� �� ��
			//Intent mediaSearch = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
			Intent mediaPlayer = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
			mediaPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mediaPlayer);
		}
	   });
	    //API 9 �����̻� ���� ����(2.3)
	   //���ķ����Ϳ����� ���� ���� �Ǿ� ���� ����
	   //�ܸ��⿡�� �׽�Ʈ ���
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
					Toast.makeText(getApplicationContext(), " �ȵ���̵� 2.3 �����̻󿡼��� ���� �մϴ�", 1000).show();
				}
			   });
	   }
	   //ACTION_VIEW�� �����  �̵�� �÷��̾� ����
	   Button btnActionViewMedia =   (Button)findViewById(R.id.action_view_media);
	   btnActionViewMedia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				//�� �ܸ����� Uri
				//Uri uri = Uri.parse("file:///sdcard/melon/Dire Straits-01-Sultans Of Swing-128.mp3");
				//���������ͼ� ����
				Uri uri = Uri.parse("file:///sdcard/Dire Straits-01-Sultans Of Swing-128.mp3");
				intent.setDataAndType(uri, "audio/mp3");
				startActivity(intent);
				/*
				 * �̷��� �ص� ��(���� �ܸ��⿡�� ����)
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
			   //���������� audio View �� ���� ��
			   MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			   projection,
				null,null, null);//MediaStore.MediaColumns._ID + " ASC");
	   //�ΰ��� Ŀ�� ����� ���ս��� �ϳ��� Ŀ�� ��Ʈ������ ���� �Ѵ�.
	   matrixAudioCursor = new MatrixCursor(
			         new String[]{"_id", "title","album_art", "duration","artist"}); //��Ʈ�������� ����� �÷��� ����
	   audioCursor.moveToFirst();
	   while(!audioCursor.isAfterLast()){
		   long audioID = audioCursor.getInt(0);
		   //�ٹ���Ʈ�� ã������ ���ǹ����� ���� album id ��
		   long albumID = audioCursor.getInt(4);
		   
		   //����Ʈ ȣ��� �ѱ� id������ ArrayList�� �����Ѵ�
		   intentSenderIDs.add(String.valueOf(audioID));
		   //�ٹ���Ʈ��  audio id�� �̿��� ���� �Ѵ�.
		   //���������� album_info View�� ���� ��
		   Uri albumArtUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumID);
	
		   Cursor albumArtCursor = this.getContentResolver().query(albumArtUri,
                   new String[]{
                       MediaStore.Audio.AlbumColumns.ALBUM_ART
                   }, 
                   null,null,null);
		   String albumArtData = null;
		   if( albumArtCursor.getCount() <= 0 ){
			   // �ٹ���Ʈ�� ���ٸ� ����Ʈ �ٹ���Ʈ�� ����
			   albumArtData = "NO_ALBUM_ART";
		   }else{
		       albumArtCursor.moveToFirst();
		       //�ٹ� ��Ʈ�� �����´�
		       albumArtData = albumArtCursor.getString(albumArtCursor.getColumnIndex(
		    		      MediaStore.Audio.AlbumColumns.ALBUM_ART));
		       if( albumArtData == null || albumArtData.equals("null")){
		    	   albumArtData = "NO_ALBUM_ART";
		       }
		   }
		   
		   //����Ʈ�� ������ ����
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
		   
		   //���� ���õ� ���������� �̵� �Ѵ�
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
		   //���� album art�� ��� �ִ� ImageView ��ü�� ��´�.
		   //���� album art�� Bitmap���� ��ȯ(Parcelable)�ؾ� Intent�� ���� ������ �����ϱ� ����
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
				
			audioTitle.setText(c.getString(1)); //�뷡����
			String.valueOf(R.drawable.nell_album_art);
			String albumArtLocation = c.getString(2);
			if(albumArtLocation != null && !albumArtLocation.equalsIgnoreCase("NO_ALBUM_ART")){
				Uri albumArtUri = Uri.fromFile(new File(albumArtLocation));
				albumArt.setImageURI(albumArtUri);
			}else{
				albumArt.setImageResource(R.drawable.nell_album_art);
			}
			
			int durationTime = Integer.parseInt(c.getString(3))/1000; //����ð�
			int minute = durationTime / 60;
			int seconds = durationTime % 60;
			playbackTime.setText(String.format("%d�� %d��", minute,seconds));
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
