/*
 *  ī�޶� ���ǽ� �� ���� ��Ƽ��Ƽ
 *  ���� �ܸ��⿡�� �׽�Ʈ
 */
package com.pyo.android.multimedia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CameraSurfaceViewActivity extends Activity {
	//String mRootPath;
	File dcimPath;
	LinearLayout mTakePicture;
	ImageView mReview;
	ImageView mMacro;
	
	//ī�޶� ���ǽ� ��
	CameraSurfaceViewImpl mSurface;
	//ī�޶� ������
	int mPicWidth;
	int mPicHeight;
	
	int mSelect;
	int mMacroSelect;					//������ ����
	//String mLastPicture = "NODATA";
	File lastFile;
	static final String PICFOLDER = "cameraImage";
	static final int TAKEDELAY = 300;
	Context mMainContext;	
	
	public final static String FILE_PATH = "FILE_PATH";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camera_include_surface_layout);
		
		mMainContext = this;
		
		//SDī�尡 ������ ����ó��
		String ext = Environment.getExternalStorageState();
		if(ext.equals(Environment.MEDIA_MOUNTED)== false){
			Toast.makeText(this, "SDī�尡 �����ؾ� �մϴ�..", 1000).show();
			finish();
			return;
		}
		//���� ī�޶��� ���� ���丮
		dcimPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/PYO_CAMERA");
	//	File  storeDir = new File(mRootPath);
		if(dcimPath.exists() == false){
			if(dcimPath.mkdir() == false){
				Toast.makeText(this, "������ ������ ������ �����ϴ�.", 1).show();
				finish();
				return;
			}
		}
		
		mPicWidth = 640;
		mPicHeight = 480;
		
		//��ư���� Ŭ�� �����ʸ� ����
		mTakePicture = (LinearLayout)findViewById(R.id.takepicture);
		mReview = (ImageView)findViewById(R.id.imgreview);
		mSurface = (CameraSurfaceViewImpl)findViewById(R.id.preview);
		mMacro = (ImageView)findViewById(R.id.btnmacro2);

		findViewById(R.id.btnreview).setOnClickListener(mReviewClick);
		findViewById(R.id.btntake).setOnClickListener(mTakeClick);
		findViewById(R.id.btnfinish).setOnClickListener(mFinishClick);
		((CheckBox)findViewById(R.id.btnmacro)).setOnCheckedChangeListener(toggleMacro);	
		
		//������
		mMacro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {			
				if (mMacroSelect==0) {	//üũ�Ѵ�
					Camera.Parameters params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
					mSurface.mCamera.setParameters(params);
					mMacro.setImageDrawable(getResources().getDrawable(R.drawable.camerabutton3push));
					mMacroSelect=1;
					
				} else {				//�����·�
					Camera.Parameters params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
					mSurface.mCamera.setParameters(params);
					mMacro.setImageDrawable(getResources().getDrawable(R.drawable.camerabutton3));
					mMacroSelect=0;
				}
			}
		});
	}
	@Override
	public void onResume() {
		super.onResume();
		if (mSurface.mCamera != null) {
			mSurface.mCamera.startPreview();
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if (mSurface.mCamera != null) {
			mSurface.mCamera.stopPreview();
		}
	}
	public void onDestroy() {
		super.onDestroy();
	}	
	
	// ���� Ȯ�� ���¿��� Back Key������ �Կ� ���� ��ȯ
	public boolean onKeyUp (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mReview.getVisibility() == View.VISIBLE) {
			mReview.setVisibility(View.GONE);
			mTakePicture.setVisibility(View.VISIBLE);
			return true;
		} else {
			super.onKeyUp(keyCode, event);
			return false;
		}
	}
	
	// ���� �Կ�
	ImageView.OnClickListener mTakeClick = new ImageView.OnClickListener() {
		public void onClick(View v) {
			mSurface.mCamera.autoFocus(mAutoFocus);
		}
	};
	
	// ������
	ImageView.OnClickListener mFinishClick = new ImageView.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	// ��Ŀ�� �����ϸ� �Կ�
	AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				mTakePicture.postDelayed(new Runnable() {
					public void run() {
						mSurface.mCamera.takePicture(null, null, mPicture);
					}
				}, TAKEDELAY);
			} else {
				Toast.makeText(mMainContext, "������ ���� �� �����ϴ�.", 1000).show();
			}
		}
	};	
	
	// ���� ����. ��¥�� �ð����� ���ϸ� �����ϰ� ������ �̵�� ��ĵ ����
	PictureCallback mPicture = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Calendar calendar = Calendar.getInstance();
			String fileName = String.format("PYO%02d%02d%02d-%02d%02d%02d.jpg", 
					calendar.get(Calendar.YEAR) % 100, calendar.get(Calendar.MONTH)+1, 
					calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), 
					calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		
			 File file = new File(dcimPath, fileName);
			 FileOutputStream toFile = null; 
			 try {
                  toFile = new FileOutputStream(file);
                  toFile.write(data);
                  toFile.flush();
			  }catch (Exception e) {
					Toast.makeText(mMainContext, "���� ���� �� ���� �߻� : " + 
							e.getMessage(), 0).show();
   			     return;
			  }finally{
					if( toFile != null){
						try{
							toFile.close();
						}catch(IOException ioe){}
					}
			 }
			 ContentValues values = new ContentValues();
			 
			 values.put(Images.Media.DATA, file.getAbsolutePath());
             values.put(Images.Media.TITLE, fileName);
             values.put(Images.Media.DISPLAY_NAME, fileName);
             values.put(Images.Media.DESCRIPTION, "�����ð��� ���� ����_" + fileName);
             values.put(Images.Media.MIME_TYPE, "image/jpeg");
             
             Uri insertedImageRowUri = getContentResolver().insert(
                     MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        
			// ���� ������ ��ĳ�� ��û
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		//	Uri fileUri = Uri.fromFile(file);
		//	Uri uri = Uri.parse("file://" + path);
		//	intent.setData(fileUri);
			intent.setData(insertedImageRowUri);
			sendBroadcast(intent);

			lastFile = file;
			mSurface.mCamera.startPreview();
		}
	};	
	// ���� Ȯ��
	ImageView.OnClickListener mReviewClick = new ImageView.OnClickListener() {
		public void onClick(View v) {
			//if (!mLastPicture.equals("NODATA")) {
			if( lastFile.isFile()){
				//���� ������ ���� �� �ֵ��� ����
				mReview.setVisibility(View.VISIBLE);
				//���� ȭ���� ���̰� ���� ����
				mTakePicture.setVisibility(View.GONE);
				try {
					Bitmap bm = BitmapFactory.decodeFile(lastFile.getAbsolutePath());
					mReview.setImageBitmap(bm);
				}catch(OutOfMemoryError e) {
					Toast.makeText(mMainContext,"�̹����� ���� �� �����ϴ�", 0).show();
				}
				//�ٽ� Ŭ�� �Ѵٸ�
				mReview.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						mReview.setVisibility(View.GONE);
						mTakePicture.setVisibility(View.VISIBLE);
						return false;
					}
				});
			} else {
				Toast.makeText(mMainContext,"���� ���� ������ �����ϴ�.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}; 
	// ��ũ�� ��� ���
	CompoundButton.OnCheckedChangeListener toggleMacro = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.btnmacro) {
				Camera.Parameters params = null;
				if (isChecked) {		//üũ�Ǿ��ٸ�
					params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
					mSurface.mCamera.setParameters(params);
				} else {				//üũ�����ʾҴٸ�
					params = mSurface.mCamera.getParameters();
					params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
					mSurface.mCamera.setParameters(params);
				}
			}
		}
	};	
}
//ī�޶� �̸����� ǥ�� Ŭ����
 class CameraSurfaceViewImpl extends SurfaceView implements SurfaceHolder.Callback{
	SurfaceHolder mHolder;
	Context mContext;
	Camera mCamera;
	
	public CameraSurfaceViewImpl(Context context) {
		super(context);
		init(context);
	}

	public CameraSurfaceViewImpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}	

	public CameraSurfaceViewImpl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	void init(Context context) {
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	// ǥ�� ������ ī�޶� �����ϰ� �̸����� ����
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open(); //ī�޶� ����
		try {
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			if( mCamera != null){
			  mCamera.release();
			}
			Log.e("surfaceCreated", " ī�޶� ���� ��! ���� �߻�", e);
			mCamera = null;
		}
	}

	// ǥ���� ũ�Ⱑ ������ �� ������ �̸����� ũ�⸦ ���� �����Ѵ�.
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		Camera.Parameters params = mCamera.getParameters();
        List<Size> arSize = params.getSupportedPreviewSizes();
        if (arSize == null) {
			params.setPreviewSize(width, height);
        } else {
	        int diff = 10000;
	        Camera.Size opti = null;
	        for (Camera.Size s : arSize) {
	        	if (Math.abs(s.height - height) < diff) {
	        		diff = Math.abs(s.height - height);
	        		opti = s;      		
	        	}
	        }
			params.setPreviewSize(opti.width, opti.height);
        }     
        CameraSurfaceViewActivity mainContext = (CameraSurfaceViewActivity)mContext;
        if (mainContext.mPicWidth != -1) {
			params.setPictureSize(mainContext.mPicWidth, mainContext.mPicHeight);
        }
		mCamera.setParameters(params);
		mCamera.startPreview();
	}
	//ǥ�� �ı��� ī�޶� ��ü�� ���� �Ѵ�
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}	
	}
}
