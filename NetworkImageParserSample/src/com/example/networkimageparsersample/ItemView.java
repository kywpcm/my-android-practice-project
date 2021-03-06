package com.example.networkimageparsersample;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//MVC, View..
//item_layout.xml에 해당하는 View..
public class ItemView extends FrameLayout {
	private static final String TAG = "ItemView";

	URLImageView image;
	TextView title;
	TextView director;
	NaverMovieItem item;
	Handler mHandler = new Handler();
	ImageRequest mRequest;
	
	//생성자
	public ItemView(Context context) {
		super(context);
		Log.i(TAG, "ItemView(Context context) 생성자");
		
		//레이아웃 인플레이트
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);
		
		image = (URLImageView)findViewById(R.id.image);
		title = (TextView)findViewById(R.id.title);
		director = (TextView)findViewById(R.id.director);
	}
	//해당하는 뷰에 해당하는 아이템 데이터를 set
	public void setItemData(NaverMovieItem item) {
		Log.d(TAG, "setItemData() 메소드 시작");
		this.item = item;
		title.setText(item.title);
		director.setText(item.director);
		image.setImaegURL(item.image);
	}
}
