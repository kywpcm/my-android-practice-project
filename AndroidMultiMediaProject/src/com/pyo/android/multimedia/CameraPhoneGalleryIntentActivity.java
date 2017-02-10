/*
 * 인텐트를 이용한 이미지 캡쳐 방법
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
		//현재 화면을 가로 형으로 고정
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
				 * MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA 는 사진찍기 모드로
				 * 카메라 어플만 실행 함
				 */
				//카메라어플을 실행시켜 이미지 캡쳐를 위한 인텐트
				Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);		
				long currentTime = System.currentTimeMillis()/1000;
				String fileName =  "capture_image_02_" + currentTime +".jpg";
				lastPath = rootPath + "/" +  fileName;
				File  finalDir = new File(lastPath);
				//저장할 디렉토리를 엑스트라 값으로 넘겨 줌
				imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(finalDir));
				startActivityForResult(imageCaptureIntent, REQUEST_CAMERA_IMAGE_CAPTURE);
			}
		});
		btnGallery1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				/*
				 *  ACTION_GET_CONTENT : 데이터유형(MIME TYPE)(이미지나영상등)
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
				 *  데이터를 가져오기 위한 액션 인텐트
				 *   ACTION_PICK : 호출유형이 데이터의 실제 주소(URI)(주소록등에서 많이 사용됨)
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
		//SD카드가 없을시 에러처리
		String sdcardMounted = Environment.getExternalStorageState();
		if(sdcardMounted.equals(Environment.MEDIA_MOUNTED)== false){
			Toast.makeText(this, "SD 카드가 없어 종료 합니다.", 1500).show();
			finish();	
			return;
		}
		rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capture_image";
		Log.e("rootpath", rootPath);
		File imageStoreDir = new File(rootPath);
	    if( !imageStoreDir.exists()){
	    	if(imageStoreDir.mkdir()){
	    		Toast.makeText(getApplication(), " 저장할 디렉토리가 생성 됨", 2000).show();
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
			 *  lastPath값이 카메라 실행시키면 모토로이(프로요)에서 null로 세팅됨(다양한 폰 테스트 요망)
			 *  원인 : 내 폰이 메모리가 딸려서 그런건가?????? startActivityForResult하면 onDestroy까지 동작
			 *  UI 객체를 제외한 나머지 필드 객체는 null로 초기화 되는 현상이 발생
			 *  해결책: UI를 제외한 필드 값을 onSaveInstanceState(,) 저장하고, onRestoreInstanceState(,) 복원 시킴
			 */
			if( lastPath != null){
			  Log.e("CAMERA_CAPTURE_",lastPath);
			}else{
				Log.e("CAMERA_CAPTURE_"," last Path는 null" );
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