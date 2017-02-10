/*
 *  SurfaceView Ȯ�� ����
 */
package com.pyo.image.touch;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewExtentionActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new SurfaceViewImageDrawing(this));
	}
	private class SurfaceViewImageDrawing extends SurfaceView implements SurfaceHolder.Callback, Runnable{
		private SurfaceHolder holder;	
		private Bitmap imageMove;	
		private float movePointX = 0;	
		private float movePointY = 0;	

		private Thread thread = null;	
		private PointF pImage;	
		private Point pWindow;	
		private boolean imageMoveFlag = false;	
		private float touchPointX = 0;	
		private float touchPointY = 0;
		private Paint paint;

		public SurfaceViewImageDrawing(Context context) {
			super(context);	

			//���ǽ�Ȧ�� ��ü�� ����
			holder = getHolder();
			//Ȧ���� �ݹ�޼ҵ尡 ���� �ϵ��� ���
			holder.addCallback(this);	
			setFocusable(true);

			paint = new Paint();	
			paint.setAntiAlias(true);	
			paint.setTextSize(20);	
			paint.setColor(Color.BLACK);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
		}
		/** ȭ���� ���� �� �� */	
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		/** ���� �� �� */
		public void surfaceCreated(SurfaceHolder holder) {	
			// window ũ��
			pWindow = new Point();
			pWindow.x = 320;
			pWindow.y = 480;  

			// �̹��� ��ġ	
			pImage = new PointF(0, 0);	
			Resources res = getResources();	
			Bitmap tempBitmap = BitmapFactory.decodeResource(res, R.drawable.taxiicon);

			// ǥ���� ��ġ
			movePointX = pWindow.x / 2;	
			movePointY = pWindow.y / 2;

			//�ش� ��Ʈ���� ��´�
			imageMove = Bitmap.createScaledBitmap(tempBitmap, 100, 100, true);
			setClickable(true);

			thread = new Thread(this);
			thread.start();	
		}
		/** ���� �� �� */
		public void surfaceDestroyed(SurfaceHolder holder){
			if( thread != null){ 
				thread.interrupt();
			}
		}	
		public void run() {
			// Canvas �� ������
			pWindow.x = getWidth();
			pWindow.y = getHeight();
			while (!Thread.currentThread().isInterrupted()) {
				Canvas surfaceCanvas = null;	
				try {
					surfaceCanvas = holder.lockCanvas(null);
					synchronized (holder){	
						doSurfaceViewDrawing(surfaceCanvas);
						Thread.sleep(50);
					}
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}finally{
					if (surfaceCanvas != null){
						holder.unlockCanvasAndPost(surfaceCanvas);
					}
				}
			}
		}
		private void doSurfaceViewDrawing(Canvas canvas){	
			pImage.x = movePointX;	
			pImage.y = movePointY;

			if(canvas != null) {
				canvas.drawColor(Color.LTGRAY);
				canvas.drawBitmap(imageMove, movePointX - 40, movePointY - 30, null);
				canvas.drawText("��ġ ���� ���� : " + imageMoveFlag, 0, 50, paint);	
				canvas.drawText("�̹��� ��ġ  : X= " + pImage.x + ", Y=" + pImage.y, 0, 80, paint);
				canvas.drawText("��ġ ����Ʈ  : X= " + touchPointX +", Y=" + touchPointY, 0, 110, paint);
			}
		}
		@Override	
		public boolean onTouchEvent(MotionEvent event) {

			final float currentPointX = event.getX();	
			final float currentPointY = event.getY();

			touchPointX = currentPointX;	
			touchPointY = currentPointY;

			switch (event.getAction()){	
			case MotionEvent.ACTION_MOVE:	
				if (imageMoveFlag){	//��ġ �Ǿ� �ִٸ� �̵� ��Ų��
					movePointX = currentPointX;	
					movePointY = currentPointY;	
				}
				break;
			case MotionEvent.ACTION_UP:	
				imageMoveFlag = false;
				break;
			case MotionEvent.ACTION_DOWN:
				//���� �̹����� ��ġ �Ǿ� �ִ��� Ȯ�� �Ѵ�(���� 30,30)
				if ((pImage.x - 30 < currentPointX) && (currentPointX < pImage.x + 30)){	
					if ((pImage.y - 30 < currentPointY) && ( currentPointY < pImage.y + 30)){
						//��ġ ��
						imageMoveFlag = true;
					}
				}
				break;
			}
			return true;
		}
	}
}