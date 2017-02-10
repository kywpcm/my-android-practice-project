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

	//�׸���� �������� ���̾ƿ��� ���ڷ� ���� ����
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
		ViewHolder holder = null;  //��Ȧ�� ��ü ����

		if (row == null) {  //convertView�� ������,
			//�� ���÷���Ʈ
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			//��Ȧ���� �� ���� �ּ� �����ֱ�
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.text);
			holder.image = (ImageView) row.findViewById(R.id.image);
			
			//�׸���� ������ ���̾ƿ��� ���÷���Ʈ �� �信 Object Tag ����
			row.setTag(holder);
		} else {  //convertView�� ������
			holder = (ViewHolder) row.getTag();
		}

		//��Ȧ���� �信 data ����
		MyItem item = (MyItem) data.get(position);
		holder.imageTitle.setText(item.getTitle());
		holder.image.setImageBitmap(item.getImage());
		
		return row;
	}

	//��Ȧ�� Ŭ����
	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
	
}