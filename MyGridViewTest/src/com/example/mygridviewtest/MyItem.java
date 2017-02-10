package com.example.mygridviewtest;

import android.graphics.Bitmap;

//Model Ŭ����
public class MyItem {

	private Bitmap image;
	private String title;

	public MyItem(Bitmap image, String title) {
		super();
		
		this.image = image;
		this.title = title;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}