package com.example.networkimageparsersample;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//MVC, Controll..
public class MyAdapter extends BaseAdapter {
	private static final String TAG = "MyAdapter";

	Context mContext;
	ArrayList<NaverMovieItem> items;
	
	public MyAdapter(Context context, ArrayList<NaverMovieItem> items) {
		Log.i(TAG, "MyAdapter(Context context, ArrayList<NaverMovieItem> items) 생성자 호출");
		mContext = context;
		if (items == null) {
			items = new ArrayList<NaverMovieItem>();
		}
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public NaverMovieItem getItem(int position) {
		return items.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView() 메소드 시작");
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setItemData(items.get(position));
		return v;
	}

}
