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

	public final static int VIEW_TYPE_COUNT = 2;  //뷰 타입 갯수
	public final static int VIEW_TYPE_RECEIVE = 0;
	public final static int VIEW_TYPE_SEND = 1;

	Context mContext;
	ArrayList<ItemData> mData;

	public MyAdapter(Context context, ArrayList<ItemData> data) {
		Log.i(TAG, "MyAdapter(Context context, ArrayList<MyData> data) 생성자");
		mContext = context;
		mData = data;
	}

	//Adapter의 대표적 메소드 4가지
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

	//뷰를 생성하고, 재활용하고, 보여준다.
	//Control에서 View를 만들어 제어하고, 데이터를 View에게 전달해 set한다.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//		if(mData.get(position).message != "") {
		if(mData.get(position).isSend) {
			Log.d(TAG, "getView(), isSend is " + mData.get(position).isSend);
			SendItemView sendView;
			if (convertView == null) {  //save해 놓은 뷰가 없으면
				sendView = new SendItemView(mContext);  //뷰를 새로 만들고,
			} else {  //있으면
				sendView = (SendItemView)convertView;  //convertView를 쓴다.
			}
			sendView.setItemData(mData.get(position));  //뷰를 보여주기 전, 데이터를 set
			return sendView;
		} else {
			Log.d(TAG, "getView(), isSend is " + mData.get(position).isSend);
			ReceiveItemView receiveView;
			if (convertView == null) {  //save해 놓은 뷰가 없으면
				receiveView = new ReceiveItemView(mContext);  //뷰를 새로 만들고,
				//Log.d(TAG, "getView(), 뷰에 setOnItemImageClickListener() 호출");
				//MyItemView.OnItemImageClickListener를 implements했기 때문에 그냥 this로..
				//메소드 오버라이딩은 밑에 따로..
				//v.setOnItemImageClickListener(this);
				//Log.d(TAG, "getView()의 뷰에 setOnItemImageClickListener 등록 됨");
			} else {
				receiveView = (ReceiveItemView)convertView;  //convertView를 쓴다.
			}
			Log.e(TAG, position + "");
			receiveView.setItemData(mData.get(position));  //뷰를 보여주기 전, 데이터를 set
			return receiveView;
		}

		//		}

	}

	//리스트에 add는 실제 여기서..
	public void add(ItemData item) {
		mData.add(item);
		Log.d(TAG, "add(), List에 item(MyData)이 add");
		notifyDataSetChanged(); //어댑터에게 data set이 변경되었다고 알려 줌
	}

	// getView 메서드로 생성될 수 있는 뷰의 수를 반환하는 메서드
	@Override
	public int getViewTypeCount() {
		Log.d(TAG, "getViewTypeCount()");
		return VIEW_TYPE_COUNT;
	}
	// 인자로 넘어온 값에 해당하는 뷰의 타입을 반환하는 메서드
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
