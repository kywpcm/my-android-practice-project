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
	   
	   //DB�� ����Ʈ ä��
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
	   
		//����� ����
		GirlsGroupListAdapter adapter = new GirlsGroupListAdapter(this, mCursor);	
		ListView av = (ListView) findViewById(R.id.imageListGirlsGroup);
		av.setAdapter(adapter);

		// ListView ������ Ŭ�� ������ ���
		av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final long deleteGroupId = id;

				// ���̾� �α� ���
				new AlertDialog.Builder(GirlsGroupListActivity3.this).setMessage(
						"���� ���� �ҷ���?").setPositiveButton("����",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// ����
								deleteGirlsGroupMusic(deleteGroupId);
								// Ŀ���� ����������(requery());
								reDisplayGirlsGroupList(); 
							}
						}).show();
			}
		});
	}
	// �ʿ��ϴٸ� �ٽ� ������ ����
	public void reDisplayGirlsGroupList() {	
		mCursor.requery();
		GirlsGroupListAdapter adapter = new GirlsGroupListAdapter(this, mCursor);
		ListView av = (ListView) findViewById(R.id.imageListGirlsGroup);
		av.setAdapter(adapter);
	}
	// id�� ����
	public void deleteGirlsGroupMusic(Long id) {
		String astrArgs[] = { id.toString() };
		girlsDB.delete(GirlsGroupMusic.TABLE_NAME,
				          GirlsGroupMusic._ID + "=?", astrArgs);
	}

	// ����Ʈ ���� �ڽ� �並 �����ϰ� ǥ��
	static class GirlsGroupListItemContainer  {	
	    TextView mMusicName; 
	    TextView mGroupName; 
	    ImageView mGroupPicture; 
	}

	//����� ����
	public class GirlsGroupListAdapter extends BaseAdapter {

		private GirlsGroupMusicInfo[] groupInfos;
		private Context mContext;
		private LayoutInflater mInflater;

		public GirlsGroupListAdapter(Context context, Cursor curs) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext); 

			int iNumGroups = curs.getCount();
			groupInfos = new GirlsGroupMusicInfo[iNumGroups];

			// Ŀ���� �׺���̼� �Ѵ�
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
               
				//��ü ����
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
		//�� ������ 
		public View getView(int position, View convertView, ViewGroup parent) {

			GirlsGroupListItemContainer groupListItem;
			
			if(convertView == null){
				// ��ü �ϳ��� ���Ӱ� ����
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
				// �±׿��� GirlsGroupListItemContainer ��ü�� ������
				groupListItem = (GirlsGroupListItemContainer) convertView.getTag();
			}
						
			groupListItem.mMusicName.setText(groupInfos[position].getMusicName());
			groupListItem.mGroupName.setText(groupInfos[position].getGroupName());
	
			// ��ȿ�� �̹������ ���� �´�
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
