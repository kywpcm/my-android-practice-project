/*
 *  인텐트를 이용한 동영상 녹화
 *  made by PYO IN SOO
 */
package com.pyo.android.multimedia;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoRecorderIntentActivity extends Activity{
   private File videoDirectory  ;
   private String videoFileName;
   private VideoView videoView;
   private String returedFileName;
	@Override
   public void onCreate(Bundle bundle){
	   super.onCreate(bundle);
	   setContentView(R.layout.video_record_intent_layout);
	   
	   Button btnVideoRecord = (Button)findViewById(R.id.btn_intent_video_record);
	   Button btnVideoPlay = (Button)findViewById(R.id.btn_video_play);
	   videoView = (VideoView)findViewById(R.id.videoView1);
	   MediaController  mediaController = new MediaController(this);
	   videoView.setMediaController(mediaController);
	   //API 8부터 사용 가능(인텐트로 보내면content://....가 리턴 되는 현상이 발생)
	   //videoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
	   //인텐트로 보내면 저장된 디렉토리가 리턴됨
	   videoDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile();
	   btnVideoRecord.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentVideoRecorder = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			videoFileName = "video_record_file_" + System.currentTimeMillis()/1000 + ".mp4";
			File currentFile = new File(videoDirectory, videoFileName);
			
			Uri  videoStoreURl = Uri.fromFile(currentFile);
			//intentVideoRecorder.putExtra(MediaStore.EXTRA_OUTPUT, 
				//	   videoStoreURl);
			intentVideoRecorder.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
			startActivityForResult(intentVideoRecorder, 10);
		 }
    	});
	   btnVideoPlay.setOnClickListener(new View.OnClickListener(){
			 @Override
			 public void onClick(View v){
				 videoView.setVideoPath(returedFileName);
			     videoView.start();
			     videoView.requestFocus();
			 }
	    	});
   }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 10 && resultCode == Activity.RESULT_OK){
			Toast.makeText(getApplicationContext(), data.getDataString() + " 파일로 저장 되었습니다.", Toast.LENGTH_LONG).show();
			//멀티미디어 관련 인텐트를 호출 하면 호출한 쪽의 필드가 초기화 될 수 있음
			//새롭게 저장된 위치를 얻어 온다
			returedFileName = data.getDataString();
		}
	}
}