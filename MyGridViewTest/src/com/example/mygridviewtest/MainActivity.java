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

		//view ��ü ����
		gridView = (GridView) findViewById(R.id.gridView);
		
		//����� ����
		//�׸��� ������ ���̾ƿ��� ���ڷ� ������
		adapter = new MyAdapter(this, R.layout.grid_item, getData());
		
		//�׸���信 ����� set
		gridView.setAdapter(adapter);
	}

	private ArrayList getData() {
		final ArrayList imageItems = new ArrayList();
		
		//TypedArray : array �����̳�, Ÿ��ȭ�� array
		TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
		for (int i = 0; i < imgs.length(); i++) {
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
					imgs.getResourceId(i, -1));
//			imgs.getDrawable(index); ��
			imageItems.add(new MyItem(bitmap, "Image#" + i));
		}

		return imageItems;
	}

}