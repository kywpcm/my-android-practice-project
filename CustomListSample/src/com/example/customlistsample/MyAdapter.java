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
	
	public final static int VIEW_TYPE_COUNT = 2;  //뷰 타입 갯수
	public final static int VIEW_TYPE_RECEIVE = 0;
	public final static int VIEW_TYPE_SEND = 1;
	
	private List<MyData> mData;
	private Context mContext;
	
	private OnAdapterItemClickListener mListener;
	
	//생성자
	public MyAdapter(Context context, List<MyData> data) {
		Log.i(TAG, "MyAdapter(Context context, List<MyData> data) 생성자");
		mContext = context;
		mData = data;  //data 셋팅..
	}
	
	//인터페이스 정의와 메소드 선언은 여기서
	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(MyData data);
	}
	//리스너 set하는 메소드
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
		Log.d(TAG, "setOnAdapterItemClickListener()\n" +
				"MyAdapter의 멤버 변수 mListener = listener");
	}
	
	//Adapter의 대표적 메소드 4가지
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
	
	//뷰를 생성하고, 재활용하고, 보여준다.
	//Control에서 View를 만들어 제어하고, 데이터를 View에게 전달해 set한다.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (mData.get(position).isSend) {
			Log.e(TAG, "getView(), isSend is " + mData.get(position).isSend);
			MyItemSendView v;
			if (convertView == null) {  //save해 놓은 뷰가 없으면
				v = new MyItemSendView(mContext);  //뷰를 새로 만들고,
			} else {  //있으면
				v = (MyItemSendView)convertView;  //convertView를 쓴다.
			}
			v.setMyData(mData.get(position));  //뷰를 보여주기 전, 데이터를 set
			return v;
		} else {
			Log.e(TAG, "getView(), isSend is " + mData.get(position).isSend);
			MyItemView v;
			if (convertView == null) {  //save해 놓은 뷰가 없으면
				v = new MyItemView(mContext);  //뷰를 새로 만들고,
				Log.d(TAG, "getView(), 뷰에 setOnItemImageClickListener() 호출");
				
				//MyItemView.OnItemImageClickListener를 implements했기 때문에 그냥 this로, 메소드 구현은 밑에 따로
				v.setOnItemImageClickListener(this);
				//MyItemView가 제공하는 인터페이스 서비스이다..
//				v.setOnItemImageClickListener(new MyItemView.OnItemImageClickListener() {
//					
//					@Override
//					public void onItemImageClick(MyData data) {
//						//...
//					}
//				});
				Log.d(TAG, "getView()의 뷰에 setOnItemImageClickListener 등록 됨");
			} else {
				v = (MyItemView)convertView;  //convertView를 쓴다.
			}
			v.setMyData(mData.get(position));  //뷰를 보여주기 전, 데이터를 set
			return v;
		}
	}
	
	//사용자 정의 add 메소드..
	public void add(MyData item) {
		Log.i(TAG, "add()");
		mData.add(item);
		Log.d(TAG, "add(), List에 item(MyData)이 add");
		notifyDataSetChanged();
	}
	
	///////////////////////////////////////////////////////////
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
	///////////////////////////////////////////////////////////
	
	//메소드의 실제 구현은 여기서!
	@Override
	public void onItemImageClick(MyData data) {
		Log.e(TAG, "onItemImageClick() 구현은 여기서");
		
		//이미 MainActivity의 onCreate()에서 리스너를 등록 함..
		if (mListener != null) {
			Log.d(TAG, "mListener is not null");
			Log.e(TAG, "OnAdapterItemClickListener 인터페이스의\n" +
					"onAdapterItemClick() 호출\n" +
					"하지만 여기는 선언만 되어있고, 구현은 ->");
			mListener.onAdapterItemClick(data);  //mListener가 is not null일 때, onAdapterItemClick() 메소드 호출..
		}
	}
	
}
