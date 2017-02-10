/*
 *  VideoView를 이용한 동영상 재생
 */
package com.pyo.android.multimedia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class AndroidVideoViewUsingActivity extends Activity{
   private ProgressDialog  videoLoading ;
	@Override
   public void onCreate(Bundle bundle){
	   super.onCreate(bundle);
	   setContentView(R.layout.video_view_simple_layout);
	   
	   final VideoView videoView = (VideoView)findViewById(R.id.video_view);
	   
	   MediaController  mediaController = new MediaController(this);
	   // mediaController.show(0); //hide()가 호출 될 때 까지 계속 보여짐
	   videoView.setMediaController(mediaController);
	   
	   videoLoading = new ProgressDialog(this);
	   videoLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	   videoLoading.setMessage("로딩 중 입니다 잠시만 기다려 주십시요....");
	   videoLoading.show();
	   
	  // videoView.setVideoURI(Uri.parse(
			//   "http://www.archive.org/download/Unexpect2001/Unexpect2001_512kb.mp4"));
	   videoView.setVideoPath(
			   Environment.getExternalStorageDirectory().getAbsolutePath()
			   + "/t_ara_music_video.mp4");
	   /*
	    *  미디어의 준비가 끝나면 호출
	    */
	   videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){	
			@Override
			public void onPrepared(MediaPlayer mp){
				videoView.start();
				videoLoading.dismiss();
				videoView.requestFocus();
			}
		});
   }
}
