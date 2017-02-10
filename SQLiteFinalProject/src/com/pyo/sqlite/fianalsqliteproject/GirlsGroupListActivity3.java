package com.pyo.sqlite.fianalsqliteproject;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pyo.sqlite.fianalsqliteproject.GirlsGroupDB3.GirlsGroupInfo;
import com.pyo.sqlite.fianalsqliteproject.GirlsGroupDB3.GirlsGroupMusic;
import com.pyo.sqlite.fianalsqliteproject.R;

public class GirlsGroupListActivity3 extends MediaStoreGirlsBaseActivty {
   protected Cursor mCursor;
   
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.show_image_girls_list_layout);
	   
	   //DB로 리스트 채움
	   displayGirlsGroupList();
	   
	   final Button backBtn = (Button)findViewById(R.id.historyBackBtn);
	   backBtn.setOnClickListener(new View.OnClickListener(){ 
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			GirlsGroupListActivity3.this.finish();
		}
	   });
   }
   public void displayGirlsGroupList() {
		// SQL Query
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(
				   GirlsGroupMusic.TABLE_NAME + ", " +
				   GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(
				GirlsGroupMusic.TABLE_NAME + "." +
				GirlsGroupMusic.GIRLS_GROUP_ID + "=" + 
				GirlsGroupInfo.TABLE_NAME + "."
				+ GirlsGroupInfo._ID);

		// Run Query
		String asColumnsToReturn[] = {
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.GROUP_IMAGE_URI,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.GROUP_IMAGE_ID,
				GirlsGroupInfo.TABLE_NAME + "." +   GirlsGroupInfo.TEAM_NAME };
		
		mCursor = queryBuilder.query(girlsDB, asColumnsToReturn, null, null,
				     null, null, GirlsGroupMusic.SORT_ORDER);
		startManagingCursor(mCursor);
	   
		//어댑터 연결
		GirlsGroupListAdapter adapter = new GirlsGroupListAdapter(this, mCursor);	
		ListView av = (ListView) findViewById(R.id.imageListGirlsGroup);
		av.setAdapter(adapter);

		// ListView 아이템 클랙 리스너 등록
		av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final long deleteGroupId = id;

				// 다이얼 로그 등록
				new AlertDialog.Builder(GirlsGroupListActivity3.this).setMessage(
						"정말 삭제 할래요?").setPositiveButton("삭제",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// 삭제
								deleteGirlsGroupMusic(deleteGroupId);
								// 커서의 리쿼리동작(requery());
								reDisplayGirlsGroupList(); 
							}
						}).show();
			}
		});
	}
	// 필요하다면 다시 쿼리를 날림
	public void reDisplayGirlsGroupList() {	
		mCursor.requery();
		GirlsGroupListAdapter adapter = new GirlsGroupListAdapter(this, mCursor);
		ListView av = (ListView) findViewById(R.id.imageListGirlsGroup);
		av.setAdapter(adapter);
	}
	// id로 삭제
	public void deleteGirlsGroupMusic(Long id) {
		String astrArgs[] = { id.toString() };
		girlsDB.delete(GirlsGroupMusic.TABLE_NAME,
				          GirlsGroupMusic._ID + "=?", astrArgs);
	}

	// 리스트 뷰의 자식 뷰를 간단하게 표현
	static class GirlsGroupListItemContainer  {	
	    TextView mMusicName; 
	    TextView mGroupName; 
	    ImageView mGroupPicture; 
	}

	//어댑터 구현
	public class GirlsGroupListAdapter extends BaseAdapter {

		private GirlsGroupMusicInfo[] groupInfos;
		private Context mContext;
		private LayoutInflater mInflater;

		public GirlsGroupListAdapter(Context context, Cursor curs) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext); 

			int iNumGroups = curs.getCount();
			groupInfos = new GirlsGroupMusicInfo[iNumGroups];

			// 커서를 네비게이션 한다
			curs.moveToFirst();
			for (int i = 0; i < iNumGroups; i++) {
				final String musicName = curs.getString(
						   curs.getColumnIndex(GirlsGroupMusic.MUSIC_TITLE));
				final String groupName = curs.getString(
						   curs.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				final String imageUriPath = curs.getString(
						  curs.getColumnIndex(GirlsGroupMusic.GROUP_IMAGE_URI));	
				final long id = 
					      curs.getLong(curs.getColumnIndex(GirlsGroupMusic._ID));
				final long groupImageId = curs.getLong(
						  curs.getColumnIndex(GirlsGroupMusic.GROUP_IMAGE_ID));
               
				//객체 생성
				groupInfos[i] = new GirlsGroupMusicInfo(
						   musicName, groupName, imageUriPath, groupImageId, id);
				curs.moveToNext();
			}

		}
		public int getCount() {
			return groupInfos.length;
		}
		public Object getItem(int position){
			return groupInfos[position];
		}
		public long getItemId(int position) {
			return groupInfos[position].getId();
		}
		//뷰 재정의 
		public View getView(int position, View convertView, ViewGroup parent) {

			GirlsGroupListItemContainer groupListItem;
			
			if(convertView == null){
				// 객체 하나를 새롭게 생성
				convertView = (RelativeLayout)mInflater.inflate(R.layout.girls_group_item, null); 
				groupListItem = new GirlsGroupListItemContainer();
				groupListItem.mMusicName =
					(TextView) convertView.findViewById(R.id.textMusicName);
				groupListItem.mGroupName =
					(TextView) convertView.findViewById(R.id.textGroupName);
				groupListItem.mGroupPicture =
					(ImageView) convertView.findViewById(R.id.imageGirlsGroupImage);
    			convertView.setTag(groupListItem);
				
			}else{
				// 태그에서 GirlsGroupListItemContainer 객체를 가져옴
				groupListItem = (GirlsGroupListItemContainer) convertView.getTag();
			}
						
			groupListItem.mMusicName.setText(groupInfos[position].getMusicName());
			groupListItem.mGroupName.setText(groupInfos[position].getGroupName());
	
			// 유효한 이미지라면 가져 온다
	        if(groupInfos[position].getImageId() != 
	        	       GirlsGroupMusicInfo.INVALID_GIRLS_GROUP_IMAGE_ID){
	        	Uri baseUri = Uri.parse(groupInfos[position].getImageUriPath());
			    Uri imageUri = ContentUris.withAppendedId(baseUri, groupInfos[position].getImageId());
			    groupListItem.mGroupPicture.setImageURI(imageUri); 
	        }  
			return convertView;
		}	
	}
}
