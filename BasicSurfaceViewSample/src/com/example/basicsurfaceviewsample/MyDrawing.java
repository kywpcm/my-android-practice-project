package com.example.basicsurfaceviewsample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyDrawing {

	private static final String TAG = "MyDrawing";
	
	SurfaceHolder mHolder;

	boolean isRunning = false;

	int startX = 10;
	int startY = 10;
	int endX = 210;
	int endY = 210;
	int delta = 2;

	Paint mPaint;
	
	Bitmap[] mBitmaps;

	public MyDrawing(Context context) {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);

		mBitmaps = new Bitmap[30];
		for(int i = 0; i < mBitmaps.length; i++) {
			Log.w(TAG, "디코드 인덱스 : " + i);
			mBitmaps[i] = decodeSampledBitmapFromResource(context.getResources(),
					AnimationConstantR.introAnimationFrame[i], 1280, 720);
		}
		
	}

	public void setSurfaceHolder(SurfaceHolder holder) {
		mHolder = holder;
	}

	public void start() {

		if (isRunning) return;
		isRunning = true;

		new Thread( new Runnable() {

			int cnt = 0;
			
			@Override
			public void run() {
				while(isRunning && mHolder != null) {
					Canvas canvas = null;
					try {
						canvas = mHolder.lockCanvas();
						if(cnt < 30) {
							draw(canvas, cnt);
						}
					} finally {
						if (canvas != null) {
							mHolder.unlockCanvasAndPost(canvas);
						}
					}
					cnt++;
				}
			}
		}).start();
	}

	public void draw(Canvas canvas, int index) {
		Log.i(TAG, "draw()");
		
		canvas.drawBitmap(mBitmaps[index], 0, 0, null);
		mBitmaps[index].recycle();
		mBitmaps[index] = null;
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		isRunning = false;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Log.e(TAG, "디코드 시작!");
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		//		Log.d(TAG, "ImageView raw dimension height : " + height 
		//				+ "\nwidth : " + width);
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			//Math.round()
			//소수점 첫째자리에서 반올림한 정수값(long)을 반환하는 메서드
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			Log.d(TAG, "inSampleSize is " + inSampleSize);
		}

		return inSampleSize;
	}

}
