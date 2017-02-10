/*
 * ����Ʈ�� �̿��� �̹��� ĸ�� ���
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraPhoneGalleryIntentActivity extends Activity{

	private static final int REQUEST_CAMERA_IMAGE_CAPTURE = 1;
	private static final int REQUEST_PHONE_GALLERY_1 = 2;
	private static final int REQUEST_PHONE_GALLERY_2 = 3;
	private  ImageView returedImage;
	private  String rootPath;
	private  String lastPath;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//���� ȭ���� ���� ������ ����
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.camera_intent_layout);
		
		returedImage = (ImageView) findViewById(R.id.retured_image_photo);
		Button btnCapture = (Button) findViewById(R.id.btn_camera_capture);
		Button btnGallery1 = (Button) findViewById(R.id.btn_phone_gallery1);
		Button btnGallery2 = (Button) findViewById(R.id.btn_phone_gallery2);

		btnCapture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){				
				/*
				 * MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA �� ������� ����
				 * ī�޶� ���ø� ���� ��
				 */
				//ī�޶������ ������� �̹��� ĸ�ĸ� ���� ����Ʈ
				Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);		
				long currentTime = System.currentTimeMillis()/1000;
				String fileName =  "capture_image_02_" + currentTime +".jpg";
				lastPath = rootPath + "/" +  fileName;
				File  finalDir = new File(lastPath);
				//������ ���丮�� ����Ʈ�� ������ �Ѱ� ��
				imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(finalDir));
				startActivityForResult(imageCaptureIntent, REQUEST_CAMERA_IMAGE_CAPTURE);
			}
		});
		btnGallery1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				/*
				 *  ACTION_GET_CONTENT : ����������(MIME TYPE)(�̹����������)
				 */
				Intent intent = new Intent();  
	            intent.setAction(Intent.ACTION_GET_CONTENT);  
		        intent.setType("image/*");  
		        startActivityForResult(intent ,REQUEST_PHONE_GALLERY_1);
			}
		});
		btnGallery2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				/*
				 *  �����͸� �������� ���� �׼� ����Ʈ
				 *   ACTION_PICK : ȣ�������� �������� ���� �ּ�(URI)(�ּҷϵ�� ���� ����)
				 */  
				Uri mediaUri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_PICK, mediaUri);
		        startActivityForResult(intent ,REQUEST_PHONE_GALLERY_2);
			}
		});
	}
	@Override
	public void onResume(){
		super.onResume();
		//SDī�尡 ������ ����ó��
		String sdcardMounted = Environment.getExternalStorageState();
		if(sdcardMounted.equals(Environment.MEDIA_MOUNTED)== false){
			Toast.makeText(this, "SD ī�尡 ���� ���� �մϴ�.", 1500).show();
			finish();	
			return;
		}
		rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture_image";
		Log.e("rootpath", rootPath);
		File imageStoreDir = new File(rootPath);
	    if( !imageStoreDir.exists()){
	    	if(imageStoreDir.mkdir()){
	    		Toast.makeText(getApplication(), " ������ ���丮�� ���� ��", 2000).show();
	    	}
	    }
	}
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		if( lastPath != null){
			outState.putString("file_name", lastPath);
		}
	}	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if( lastPath == null){
			lastPath = savedInstanceState.getString("file_name");
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri returedImgURI = null;
		Bitmap  bitmap = null;
		if (requestCode == REQUEST_CAMERA_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
			/*
			 *  lastPath���� ī�޶� �����Ű�� �������(���ο�)���� null�� ���õ�(�پ��� �� �׽�Ʈ ���)
			 *  ���� : �� ���� �޸𸮰� ������ �׷��ǰ�?????? startActivityForResult�ϸ� onDestroy���� ����
			 *  UI ��ü�� ������ ������ �ʵ� ��ü�� null�� �ʱ�ȭ �Ǵ� ������ �߻�
			 *  �ذ�å: UI�� ������ �ʵ� ���� onSaveInstanceState(,) �����ϰ�, onRestoreInstanceState(,) ���� ��Ŵ
			 */
			if( lastPath != null){
			  Log.e("CAMERA_CAPTURE_",lastPath);
			}else{
				Log.e("CAMERA_CAPTURE_"," last Path�� null" );
			}
			File fileDir = new File(lastPath);
			returedImgURI = Uri.fromFile(fileDir);
			try {			
				bitmap = Images.Media.getBitmap(getContentResolver(), returedImgURI);
		        returedImage.setImageBitmap(bitmap);
			}catch (IOException e) {
				Log.e("CAMERA_CAPTURE_ERROR","REQUEST_CAMERA_IMAGE_CAPTURE",e);
			}
		}else if (requestCode == REQUEST_PHONE_GALLERY_1 && resultCode == Activity.RESULT_OK){
			returedImgURI = data.getData();
			Bitmap bm;
			try {
				bm = Images.Media.getBitmap(getContentResolver(), returedImgURI);
				returedImage.setImageBitmap(bm);
			}catch (IOException e) {
				Log.e("GALLARY_ERROR","REQUEST_PHONE_GALLERY_1", e);
			}
		}else if (requestCode == REQUEST_PHONE_GALLERY_2 && resultCode == Activity.RESULT_OK){
			returedImgURI = data.getData();
			Bitmap bm;
			try {
				bm = Images.Media.getBitmap(getContentResolver(), returedImgURI);
				returedImage.setImageBitmap(bm);
			}catch (IOException e) {
				Log.e("GALLARY_ERROR","REQUEST_PHONE_GALLERY_2", e);
			}
		}
	}
}