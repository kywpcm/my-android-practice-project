package com.example.customlistsample;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.customlistsample.MyItemView.OnItemImageClickListener;

//Control
public class MyAdapter extends BaseAdapter implements MyItemView.OnItemImageClickListener {
	private final static String TAG = "MyAdapter";
	
	public final static int VIEW_TYPE_COUNT = 2;  //�� Ÿ�� ����
	public final static int VIEW_TYPE_RECEIVE = 0;
	public final static int VIEW_TYPE_SEND = 1;
	
	private List<MyData> mData;
	private Context mContext;
	
	private OnAdapterItemClickListener mListener;
	
	//������
	public MyAdapter(Context context, List<MyData> data) {
		Log.i(TAG, "MyAdapter(Context context, List<MyData> data) ������");
		mContext = context;
		mData = data;  //data ����..
	}
	
	//�������̽� ���ǿ� �޼ҵ� ������ ���⼭
	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(MyData data);
	}
	//������ set�ϴ� �޼ҵ�
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
		Log.d(TAG, "setOnAdapterItemClickListener()\n" +
				"MyAdapter�� ��� ���� mListener = listener");
	}
	
	//Adapter�� ��ǥ�� �޼ҵ� 4����
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public MyData getItem(int position) {
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
		
		if (mData.get(position).isSend) {
			Log.e(TAG, "getView(), isSend is " + mData.get(position).isSend);
			MyItemSendView v;
			if (convertView == null) {  //save�� ���� �䰡 ������
				v = new MyItemSendView(mContext);  //�並 ���� �����,
			} else {  //������
				v = (MyItemSendView)convertView;  //convertView�� ����.
			}
			v.setMyData(mData.get(position));  //�並 �����ֱ� ��, �����͸� set
			return v;
		} else {
			Log.e(TAG, "getView(), isSend is " + mData.get(position).isSend);
			MyItemView v;
			if (convertView == null) {  //save�� ���� �䰡 ������
				v = new MyItemView(mContext);  //�並 ���� �����,
				Log.d(TAG, "getView(), �信 setOnItemImageClickListener() ȣ��");
				
				//MyItemView.OnItemImageClickListener�� implements�߱� ������ �׳� this��, �޼ҵ� ������ �ؿ� ����
				v.setOnItemImageClickListener(this);
				//MyItemView�� �����ϴ� �������̽� �����̴�..
//				v.setOnItemImageClickListener(new MyItemView.OnItemImageClickListener() {
//					
//					@Override
//					public void onItemImageClick(MyData data) {
//						//...
//					}
//				});
				Log.d(TAG, "getView()�� �信 setOnItemImageClickListener ��� ��");
			} else {
				v = (MyItemView)convertView;  //convertView�� ����.
			}
			v.setMyData(mData.get(position));  //�並 �����ֱ� ��, �����͸� set
			return v;
		}
	}
	
	//����� ���� add �޼ҵ�..
	public void add(MyData item) {
		Log.i(TAG, "add()");
		mData.add(item);
		Log.d(TAG, "add(), List�� item(MyData)�� add");
		notifyDataSetChanged();
	}
	
	///////////////////////////////////////////////////////////
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
	///////////////////////////////////////////////////////////
	
	//�޼ҵ��� ���� ������ ���⼭!
	@Override
	public void onItemImageClick(MyData data) {
		Log.e(TAG, "onItemImageClick() ������ ���⼭");
		
		//�̹� MainActivity�� onCreate()���� �����ʸ� ��� ��..
		if (mListener != null) {
			Log.d(TAG, "mListener is not null");
			Log.e(TAG, "OnAdapterItemClickListener �������̽���\n" +
					"onAdapterItemClick() ȣ��\n" +
					"������ ����� ���� �Ǿ��ְ�, ������ ->");
			mListener.onAdapterItemClick(data);  //mListener�� is not null�� ��, onAdapterItemClick() �޼ҵ� ȣ��..
		}
	}
	
}
