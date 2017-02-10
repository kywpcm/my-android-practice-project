package com.kywpcm.android.chatwithcarlyraejepsen;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//Control..
public class MyAdapter extends BaseAdapter{
	private static final String TAG = "MyAdapter";

	public final static int VIEW_TYPE_COUNT = 2;  //�� Ÿ�� ����
	public final static int VIEW_TYPE_RECEIVE = 0;
	public final static int VIEW_TYPE_SEND = 1;

	Context mContext;
	ArrayList<ItemData> mData;

	public MyAdapter(Context context, ArrayList<ItemData> data) {
		Log.i(TAG, "MyAdapter(Context context, ArrayList<MyData> data) ������");
		mContext = context;
		mData = data;
	}

	//Adapter�� ��ǥ�� �޼ҵ� 4����
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ItemData getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	//�並 �����ϰ�, ��Ȱ���ϰ�, �����ش�.
	//Control���� View�� ����� �����ϰ�, �����͸� View���� ������ set�Ѵ�.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//		if(mData.get(position).message != "") {
		if(mData.get(position).isSend) {
			Log.d(TAG, "getView(), isSend is " + mData.get(position).isSend);
			SendItemView sendView;
			if (convertView == null) {  //save�� ���� �䰡 ������
				sendView = new SendItemView(mContext);  //�並 ���� �����,
			} else {  //������
				sendView = (SendItemView)convertView;  //convertView�� ����.
			}
			sendView.setItemData(mData.get(position));  //�並 �����ֱ� ��, �����͸� set
			return sendView;
		} else {
			Log.d(TAG, "getView(), isSend is " + mData.get(position).isSend);
			ReceiveItemView receiveView;
			if (convertView == null) {  //save�� ���� �䰡 ������
				receiveView = new ReceiveItemView(mContext);  //�並 ���� �����,
				//Log.d(TAG, "getView(), �信 setOnItemImageClickListener() ȣ��");
				//MyItemView.OnItemImageClickListener�� implements�߱� ������ �׳� this��..
				//�޼ҵ� �������̵��� �ؿ� ����..
				//v.setOnItemImageClickListener(this);
				//Log.d(TAG, "getView()�� �信 setOnItemImageClickListener ��� ��");
			} else {
				receiveView = (ReceiveItemView)convertView;  //convertView�� ����.
			}
			Log.e(TAG, position + "");
			receiveView.setItemData(mData.get(position));  //�並 �����ֱ� ��, �����͸� set
			return receiveView;
		}

		//		}

	}

	//����Ʈ�� add�� ���� ���⼭..
	public void add(ItemData item) {
		mData.add(item);
		Log.d(TAG, "add(), List�� item(MyData)�� add");
		notifyDataSetChanged(); //����Ϳ��� data set�� ����Ǿ��ٰ� �˷� ��
	}

	// getView �޼���� ������ �� �ִ� ���� ���� ��ȯ�ϴ� �޼���
	@Override
	public int getViewTypeCount() {
		Log.d(TAG, "getViewTypeCount()");
		return VIEW_TYPE_COUNT;
	}
	// ���ڷ� �Ѿ�� ���� �ش��ϴ� ���� Ÿ���� ��ȯ�ϴ� �޼���
	@Override
	public int getItemViewType(int position) {
		Log.d(TAG, "getItemViewType(position)");
		if (mData.get(position).isSend) {
			Log.d(TAG, "mData.get(position).isSend is " + mData.get(position).isSend);
			return VIEW_TYPE_SEND;
		} else {
			Log.d(TAG, "mData.get(position).isSend is " + mData.get(position).isSend);
			return VIEW_TYPE_RECEIVE;
		}
	}

}
