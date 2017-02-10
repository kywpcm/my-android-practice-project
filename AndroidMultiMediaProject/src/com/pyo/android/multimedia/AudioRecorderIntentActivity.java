/*
 *  ����Ʈ�� �̿��� ����� ����
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
import android.widget.Toast;

public class AudioRecorderIntentActivity extends Activity {
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   
	   //�ȵ���̵忡�� ������ ����ϴ� ����Ʈ
	   Intent audioRecorderIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
	   Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
			                    "/audio_record/audio_record_" + System.currentTimeMillis()/1000 + ".mp3"));
	   audioRecorderIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	   startActivityForResult(audioRecorderIntent, 10);
   }
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent returedIntent){
	  if( requestCode == 10 && resultCode == Activity.RESULT_OK){
		  Toast.makeText(getApplicationContext(), "���� ������ ���� ��! �ٽ� ���� �� ������ ��� ���ϴ�", 2000).show();
		  
		  Uri uri = returedIntent.getData();
		  //������ ������ ��´�
		  Intent returnIt = new Intent(Intent.ACTION_VIEW, uri);
		  startActivity(returnIt);
		  finish();
	  }
   }
}
