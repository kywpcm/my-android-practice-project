package com.pyo.android.simple.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity{
   @Override
   public void onCreate(Bundle savedStateInstance){
	   super.onCreate(savedStateInstance);
	   setContentView(R.layout.image_view_layout);
	   
	   //ScaleType.CENTER 적용1
	   //디폴트
	   ImageView center = (ImageView)findViewById(R.id.scale_type_center);
	   Bitmap jiYeonImage = BitmapFactory.decodeResource(
			                  getResources(), R.drawable.tara_ji_yeon);
	   center.setScaleType(ImageView.ScaleType.CENTER);
	   center.setImageBitmap(jiYeonImage);
	   
	   //ScaleType.FIT_XY 적용2
	   ImageView fitXY = (ImageView)findViewById(R.id.scale_type_fitXY);
	   //이미지를 축소(1/4)하여 코드로 가져 옴
	   BitmapFactory.Options imageOptions = new BitmapFactory.Options();
	   imageOptions.inSampleSize = 4;
	   Bitmap originalImage = 
		   BitmapFactory.decodeResource(getResources(), R.drawable.tara_ji_yeon, imageOptions);
	   jiYeonImage = Bitmap.createScaledBitmap(originalImage, 300, 300, true);
	   fitXY.setScaleType(ImageView.ScaleType.FIT_XY);
	   fitXY.setImageBitmap(jiYeonImage);
	   
	 //ScaleType.CENTER_CROP 적용3
	   ImageView centerCrop = (ImageView)findViewById(R.id.scale_type_centerCrop);
	   jiYeonImage = Bitmap.createScaledBitmap(jiYeonImage, 50, 50, true);
	   centerCrop.setScaleType(ImageView.ScaleType.CENTER_CROP);
	   centerCrop.setImageBitmap(jiYeonImage);
	   
	 //ScaleTypeCENTER_INSIDE 적용4
	   ImageView centerInside = (ImageView)findViewById(R.id.scale_type_centerInside);
	   jiYeonImage = Bitmap.createScaledBitmap(jiYeonImage, 300, 300, true);
	   centerInside.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	   centerInside.setImageBitmap(jiYeonImage);
	   
	  //ScaleType.FIT_CENTER 적용5
	   ImageView fitCenter = (ImageView)findViewById(R.id.scale_type_fitCenter);
	   jiYeonImage = Bitmap.createScaledBitmap(jiYeonImage, 50, 50, true);
	   fitCenter.setScaleType(ImageView.ScaleType.FIT_CENTER);
	   fitCenter.setImageBitmap(jiYeonImage);
	   
	  //ScaleType.FIT_END 적용6
	   ImageView fitEnd = (ImageView)findViewById(R.id.scale_type_fitEnd);
	   fitEnd.setScaleType(ImageView.ScaleType.FIT_END);
	   fitEnd.setImageBitmap(jiYeonImage);
	   
	  //ScaleType.FIT_START 적용7
	   ImageView fitStart = (ImageView)findViewById(R.id.scale_type_fitStart);
	   jiYeonImage = Bitmap.createScaledBitmap(jiYeonImage, 300, 300, true);
	   fitStart.setScaleType(ImageView.ScaleType.FIT_START);
	   fitStart.setImageBitmap(jiYeonImage);
	   
	   //ScaleTypeMATRIX 적용8
	   ImageView matrix = (ImageView)findViewById(R.id.scale_type_matrix);
	   jiYeonImage = Bitmap.createScaledBitmap(jiYeonImage, 50, 50, true);
	   matrix.setScaleType(ImageView.ScaleType.MATRIX);
	   matrix.setImageBitmap(jiYeonImage);
   }
}