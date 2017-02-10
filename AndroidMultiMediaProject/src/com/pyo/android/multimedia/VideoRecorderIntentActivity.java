/*
 *  ����Ʈ�� �̿��� ������ ��ȭ
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
	   //API 8���� ��� ����(����Ʈ�� ������content://....�� ���� �Ǵ� ������ �߻�)
	   //videoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
	   //����Ʈ�� ������ ����� ���丮�� ���ϵ�
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
			Toast.makeText(getApplicationContext(), data.getDataString() + " ���Ϸ� ���� �Ǿ����ϴ�.", Toast.LENGTH_LONG).show();
			//��Ƽ�̵�� ���� ����Ʈ�� ȣ�� �ϸ� ȣ���� ���� �ʵ尡 �ʱ�ȭ �� �� ����
			//���Ӱ� ����� ��ġ�� ��� �´�
			returedFileName = data.getDataString();
		}
	}
}