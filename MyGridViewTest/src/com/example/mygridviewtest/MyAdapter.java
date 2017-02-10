package com.example.mygridviewtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private int layoutResourceId;
	private ArrayList data = new ArrayList();

	//그리드뷰 아이템의 레이아웃을 인자로 전달 받음
	public MyAdapter(Context context, int layoutResourceId, ArrayList data) {
		super();

		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;  //뷰홀더 객체 생성

		if (row == null) {  //convertView가 없으면,
			//뷰 인플레이트
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			//뷰홀더에 뷰 참조 주소 끼워넣기
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.text);
			holder.image = (ImageView) row.findViewById(R.id.image);
			
			//그리드뷰 아이템 레이아웃이 인플레이트 된 뷰에 Object Tag 설정
			row.setTag(holder);
		} else {  //convertView가 있으면
			holder = (ViewHolder) row.getTag();
		}

		//뷰홀더의 뷰에 data 셋팅
		MyItem item = (MyItem) data.get(position);
		holder.imageTitle.setText(item.getTitle());
		holder.image.setImageBitmap(item.getImage());
		
		return row;
	}

	//뷰홀더 클래스
	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
	
}