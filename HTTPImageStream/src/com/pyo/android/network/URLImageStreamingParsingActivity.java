/*
 *  ��Ʈ�������� �̹����� �����ϴ� ����
 */
package com.pyo.android.network;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class URLImageStreamingParsingActivity extends Activity{

	private static String DEBUG = "URLImageStreamingParsingActivity";
	private Handler mHandler = new Handler();

	private XMLDocumentParserThread xmlParserThread = null;

	private DelayedLooperThread imageThread = new DelayedLooperThread();

	private String memberName = "";

	private TextSwitcher status;
	private TextSwitcher nameSwitcher;
	private ImageSwitcher imageSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		setContentView(R.layout.only_parsing_layout);

		status = (TextSwitcher) findViewById(R.id.status);
		status.setFactory(new ViewSwitcher.ViewFactory() {
			public View makeView() {
				TextView tv = new TextView(URLImageStreamingParsingActivity.this);
				tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
				tv.setTextSize(24);
				return tv;
			}
		});
		Animation slideInLeft = 
				AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		Animation slideOutRight = 
				AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		status.setInAnimation(slideInLeft);
		status.setOutAnimation(slideOutRight);

		nameSwitcher = (TextSwitcher) findViewById(R.id.member_name);
		nameSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			public View makeView() {
				TextView tv = new TextView(URLImageStreamingParsingActivity.this);
				tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
				tv.setTextSize(24);
				return tv;
			}
		});
		nameSwitcher.setInAnimation(slideInLeft);
		nameSwitcher.setOutAnimation(slideOutRight);

		imageSwitcher = (ImageSwitcher) findViewById(R.id.images);
		imageSwitcher.setFactory(
				new ImageSwitcher.ViewFactory() {
					public View makeView() {
						ImageView iv = 
								new ImageView(
										URLImageStreamingParsingActivity.this);
						iv.setBackgroundColor(0x00FFFFFF);
						iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
						iv.setLayoutParams(new
								ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
										LayoutParams.MATCH_PARENT));
						return iv;
					}
				});

		imageSwitcher.setInAnimation(slideInLeft);
		imageSwitcher.setOutAnimation(slideOutRight);

		status.setText("[[XML �Ľ� �غ� ��!]]");
		//�Ľ̵Ǿ� �Ѱ��� �����͸� ������� ��Ʈ������ �����ϴ� ����
		imageThread.start();
		Button doAction = (Button) findViewById(R.id.do_action);
		doAction.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				xmlParserThread = new XMLDocumentParserThread(
						"http://192.168.201.120:5678/androidNetwork/" +
								"girlsGenerationXML.pyo");
				xmlParserThread.start();
			}
		});
	}
	
	//������ ������..
	private class  XMLDocumentParserThread extends Thread{   
		private  String  xmlAddress;
		public   XMLDocumentParserThread(String  xmlAddress){
			this.xmlAddress = xmlAddress;
		}
		public void run(){

			HttpURLConnection httpConn = null;
			InputStream imageStream = null;
			URL targetURL = null;
			try{
				targetURL = new URL(xmlAddress);         
				httpConn = (HttpURLConnection) targetURL.openConnection();
				httpConn.setDoInput(true);

				imageStream = httpConn.getInputStream();
				XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();

				parser.setInput(imageStream, "UTF-8");

				mHandler.post(new Runnable(){
					public void run() {
						status.setText("==  XML �Ľ� ����! == ");
					}
				});
				String tagName = null;

				//�̹����� Ÿ��Ʋ�� ȭ�鿡 ��� �ϱ� ���� �÷��� ��
				boolean isTitle = false;
				int imgCount = 0 ;
				int parserEvent = parser.getEventType();

				while (parserEvent != XmlPullParser.END_DOCUMENT){
					switch (parserEvent){
					case XmlPullParser.TEXT:
						if (isTitle) {
							memberName = parser.getText();
						}
						break;
					case XmlPullParser.END_TAG :
						tagName = parser.getName();
						if (tagName.equalsIgnoreCase("title")) {
							isTitle = false;
						}
						break;
					case XmlPullParser.START_TAG :
						tagName = parser.getName();
						if (tagName.equalsIgnoreCase("title")) {
							isTitle = true;
						}              
						if (tagName.equalsIgnoreCase("link")) {
							String relType = parser.getAttributeValue(null, "rel");
							if (relType.equalsIgnoreCase("enclosure")) {
								String mimyType = parser.getAttributeValue(null, "type");
								if (mimyType.startsWith("image/")) {
									final String imageSrc = parser.getAttributeValue(null, "href");
									final int curImageCount = ++imgCount;     
									mHandler.post(new Runnable() {
										public void run() {
											status.setText(" ���� �Ľ̵�  ���� = " + curImageCount);
										}
									});
									;
									final String currentName = memberName;
									imageThread.setImageSrcInfo(new ImageSrcInfo(){
										public void execute(){
											try{
												BufferedInputStream imageStream = 
														new BufferedInputStream(new URL(imageSrc).openStream());
												final Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
												mHandler.post(new Runnable() {
													public void run() {
														imageSwitcher.setImageDrawable(new BitmapDrawable(imageBitmap));
														nameSwitcher.setText(currentName);
													}
												});
												imageStream.close();
											}catch(Exception e){
												Log.e(DEBUG, "�̹��� �ٿ�ε� ��! ���� �߻�!");  
											}
											/*BufferedInputStream imageReader = null;
                            BufferedOutputStream imageWriter = null;
                            URL imageURL = null;
                            try{
                     	      imageURL = new URL(imageSrc);
                     	      imageReader = new BufferedInputStream(
                     			             imageURL.openStream());
                               final ByteArrayOutputStream imageStreamData = 
                             		           new ByteArrayOutputStream();
                               int imageData = 0 ;
                               while((imageData = imageReader.read()) != -1){
                         	    imageStreamData.write(imageData);
                               }
                               imageStreamData.flush();

                               final byte[] imageFullData = imageStreamData.toByteArray();
                               imageSwitcher.
                               Bitmap bitmap = 
                            		   BitmapFactory.decodeByteArray(imageFullData, 0,
                          		                  imageFullData.length);
                               final Drawable image = new BitmapDrawable(bitmap);
                               mHandler.post(new Runnable() {
                                 public void run() {
                                   imageSwitcher.setImageDrawable(image);
                                   nameSwitcher.setText(currentName);
                                 }
                               });
                            }catch(IOException e){
                     	      Log.e(DEBUG, " �̹��� �д� �� ���� �߻�!", e);
                            }finally{
                     	       if( imageReader != null){
                                try{
                         	     imageReader.close();
                         	   }catch(IOException e){}
                               }
                               if( imageWriter != null){
                         	    try{
                         	      imageWriter.close();
                         	    }catch(IOException e){}
                               }  
                            }
											 */
										}
									});
								}
							}
						}
						break;
					} //switch �� ��.
					parserEvent = parser.next();
				}//while �� ��.
				mHandler.post(new Runnable() {
					public void run() {
						status.setText("�Ľ� ����!");
					}
				});
			}catch(Exception e){
				Log.e(DEBUG, " �Ľ� �� ���� �߻� !", e);
			}finally{
				if( imageStream != null){
					try{
						imageStream.close();
					}catch(IOException ioe){}
				}
				if( httpConn != null){
					httpConn.disconnect();
				}
			}
		} // run ��.
	} //Ŭ���� ���� ��
	public interface ImageSrcInfo{
		public void  execute();
	}
	
	//�Һ��� ������..
	private class DelayedLooperThread extends Thread {

		private long lastTime = 0;
		private long waitTime = 5000;
		private boolean flag = false;
		private ArrayList<ImageSrcInfo> imageSrcInfoQueue = new ArrayList<ImageSrcInfo>();  //ArrayLsit�� ó������ ������ ť ������ ����..

		public void run() {
			while (!flag){
				synchronized (this) {
					long thisTime = System.currentTimeMillis();
					long delayTime = thisTime - lastTime;
					if (delayTime < waitTime) {
						try {
							wait(waitTime - delayTime);
						} catch (InterruptedException e) {
							Log.e(DEBUG, "Wait-Interrupted �����߻�!", e);
						}
					}else{
						//ť(ArrayList)�� �ִ� �����带 �����ϰ� ���� �޴´�
						ImageSrcInfo firstQueue = getImageSrcInfo();
						if (firstQueue != null) {
							lastTime = thisTime;
							firstQueue.execute();
						} else {
							try {
								Log.i(DEBUG, "���� ó���� XML ��� ����...");
								wait();  //��ٷ�..
							} catch (InterruptedException e){
							}
						}
					}
				}
				Log.i(DEBUG, "while �� ���� �� ...");
			}
			Log.i(DEBUG, "while ��  over...");
		}

		public void setImageSrcInfo(ImageSrcInfo imageInfo) {
			synchronized (this) {
				imageSrcInfoQueue.add(imageInfo);
				notifyAll();  //��ο��� ����!
			}
		}
		private ImageSrcInfo getImageSrcInfo() {
			synchronized (this) {
				if (imageSrcInfoQueue.size() > 0) {
					return imageSrcInfoQueue.remove(0);
				}
			}
			return null;
		}
		private void finish() {
			synchronized (this) {
				flag = true;
				notifyAll();
			}
			try {
				join();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		} 
	}
	@Override
	//��Ƽ��Ƽ ��׶���� ���� �����带 ����
	protected void onPause(){
		super.onPause();
		if( imageThread != null && imageThread.isAlive())
			imageThread.finish();
	}
}
