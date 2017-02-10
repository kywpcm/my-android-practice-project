package com.kywpcm.android.naverbooksearch;

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
	ArrayList<NaverBookItem> items;
	
	public MyAdapter(Context context, ArrayList<NaverBookItem> items) {
		Log.i(TAG, "MyAdapter(Context context, ArrayList<NaverMovieItem> items) ������ ȣ��");
		mContext = context;
		if (items == null) {
			items = new ArrayList<NaverBookItem>();
		}
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public NaverBookItem getItem(int position) {
		return items.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView() �޼ҵ� ����");
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
