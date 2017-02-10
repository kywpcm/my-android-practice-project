package com.example.mygridviewtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridView gridView;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//view 객체 참조
		gridView = (GridView) findViewById(R.id.gridView);
		
		//어댑터 생성
		//그리드 아이템 레이아웃을 인자로 전달함
		adapter = new MyAdapter(this, R.layout.grid_item, getData());
		
		//그리드뷰에 어댑터 set
		gridView.setAdapter(adapter);
	}

	private ArrayList getData() {
		final ArrayList imageItems = new ArrayList();
		
		//TypedArray : array 컨테이너, 타입화된 array
		TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
		for (int i = 0; i < imgs.length(); i++) {
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
					imgs.getResourceId(i, -1));
//			imgs.getDrawable(index); 등
			imageItems.add(new MyItem(bitmap, "Image#" + i));
		}

		return imageItems;
	}

}