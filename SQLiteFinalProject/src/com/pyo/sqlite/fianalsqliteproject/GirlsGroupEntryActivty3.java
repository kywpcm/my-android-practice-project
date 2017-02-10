package com.pyo.sqlite.fianalsqliteproject;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.pyo.sqlite.fianalsqliteproject.GirlsGroupDB3.GirlsGroupInfo;
import com.pyo.sqlite.fianalsqliteproject.GirlsGroupDB3.GirlsGroupMusic;
import com.pyo.sqlite.fianalsqliteproject.R;

public class GirlsGroupEntryActivty3 extends MediaStoreGirlsBaseActivty{
   protected Cursor  cursorAutoComplete; //Ŀ�� �ڵ� �ϼ� ���
   protected Cursor  imagesCursor ;// Image Ŀ��
   
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.girls_group_media_entry_layout);
	   
	   //�̹��� ����ͷ� ������ �̹����� ���� �Ѵ�.
	   //SDī��� ���� �̹��� �ڿ��� ���� ���� �´�
	   displayGalleryFromMedia();
	   //�ڵ� �ϼ� ���(DB����)
	   applyAutoCompleteFromTBLGirlsGroupInfo() ;
	   
	   final Button saveBtn = (Button)findViewById(R.id.girlsGroupInsert);
	   saveBtn.setOnClickListener(new View.OnClickListener() {	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final EditText  musicName = (EditText)findViewById(R.id.musicNameEdit);
			final EditText  groupName = (EditText)findViewById(R.id.autoTextGirlsGroupFinder);
			long imageId = GirlsGroupMusicInfo.INVALID_GIRLS_GROUP_IMAGE_ID;
			String imageUriPathString = "";
			
			final Gallery imageGallery = (Gallery)findViewById(R.id.girlsGroupImageGallery);
			ImageView selectedImageView = (ImageView)imageGallery.getSelectedView();
			GirlsGroupGalleryInfo groupGallInfo;
			if( selectedImageView != null){
			   groupGallInfo = (GirlsGroupGalleryInfo)selectedImageView.getTag();
			   //�̹����� Id���� ��� �´�
			   imageId = groupGallInfo.getImageId();
			   //�̹����� ����Ǿ� �ִ� Uri ��ü�� ��´�
			   imageUriPathString = groupGallInfo.getImageUriPath();
			}
			String  strGroupName = groupName.getText().toString();
			String  strMusicName =  musicName.getText().toString();
			
			//�� �ɱ׷��� ���� ������ �������� �ɱ׷��� ������ ���� ��ü�� ����
			GirlsGroupMusicInfo groupMusicInfo = new GirlsGroupMusicInfo(
					 strMusicName,strGroupName,imageUriPathString,imageId,
					 GirlsGroupMusicInfo.INVALID_GIRLS_GROUP_ID);
			dbInsertGirlsGroupMusicInfo(groupMusicInfo);
			
			groupName.setText(null);
			musicName.setText(null);
		}
	  });
	   // ����Ʈ ��ư ���� ��ư
		final Button gotoGroupList = (Button) findViewById(R.id.girlGroupDisplayBtn);
		gotoGroupList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent intent = new Intent(
		        		 getApplicationContext(),GirlsGroupListActivity3.class);
				startActivity(intent);
			}
		});
   }
   /**************************************************************************
    *  �� �� ���� �ϰ� �ٸ� �����̴� Ȯ�� �ϱ� �ٶ�
    *  �ڵ� �ϼ� ���
    */
   private  void  applyAutoCompleteFromTBLGirlsGroupInfo(){
	   cursorAutoComplete = 
		         girlsDB.query(
			       GirlsGroupInfo.TABLE_NAME,
			       new String[]{ GirlsGroupInfo.TEAM_NAME, GirlsGroupInfo._ID},
			       null,null,null,null,
                   GirlsGroupInfo.SORT_ORDER
	             );
	   //Activity���� Ŀ���� ������ ���� ��
	   startManagingCursor(cursorAutoComplete);
	   //����� ��ü�� ��� ��
	   SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
			       android.R.layout.simple_dropdown_item_1line,
			       cursorAutoComplete,
			       new String[]{GirlsGroupInfo.TEAM_NAME},
			       new int[]{android.R.id.text1});
	   
	   //Ŀ����ü���� ���ڿ��� �Ѱ� �� �� DB�� Adapter�� ���ڿ��� ���� ��Ű�� ����
	   adapter.setCursorToStringConverter(new TeamNameToStringConverter());
	   //DB�� ������� ���ڿ��� ���� ��Ű�� ����
	   adapter.setFilterQueryProvider(new CustomFilterQueryProvider());
	   
	   AutoCompleteTextView autoText = 
		    (AutoCompleteTextView)findViewById(R.id.autoTextGirlsGroupFinder);
	   autoText.setAdapter(adapter);
   }
// Ŀ����ü���� ���ڿ��� �Ѱ��ٶ� �߻�
  private class TeamNameToStringConverter implements 
	            SimpleCursorAdapter.CursorToStringConverter {

		public CharSequence convertToString(Cursor cursor) {
			return cursor.getString(
					   cursor.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
			
		}
	}
  //������ü���� ������ �ѱ涧 �ڵ� ȣ�� ��
  private 	class CustomFilterQueryProvider implements FilterQueryProvider {

		public Cursor runQuery(CharSequence constraint) {
			if ((constraint != null) && (cursorAutoComplete != null)){
				String strWhere = GirlsGroupInfo.TEAM_NAME  + " LIKE ?";	              
				//�Ŵ�¡ ����
				stopManagingCursor(cursorAutoComplete);
				cursorAutoComplete =  girlsDB.query(
					      GirlsGroupInfo.TABLE_NAME,
						  new String[] { GirlsGroupInfo.TEAM_NAME, GirlsGroupInfo._ID },
						  strWhere, new String[] { "%" + constraint.toString() + "%"},
						  null, null,
						  GirlsGroupInfo.SORT_ORDER);
				startManagingCursor(cursorAutoComplete);
			}
			return cursorAutoComplete;
		}	
	}
  private  void displayGalleryFromMedia(){
	   //MediaStore ContentProvider ���
	  String [] projection = new String[]{Media._ID,Media.TITLE};
	 
	  //SD Card �� URL ��
	  Uri  sdcardUri = Media.EXTERNAL_CONTENT_URI;
	  //���Ǹ� �Ѵ�.
	  imagesCursor = managedQuery(
			                sdcardUri, projection,null,null,
			                Media.DATE_TAKEN + "  ASC");
	  //Ŀ���κ��� GroupImageUriAdapter��ü�� ��´�.
	  GroupImageUriAdapter  groupImageAdapter = 
		    new GroupImageUriAdapter(this, imagesCursor, sdcardUri);
	  //�̹����� ���÷��� �ϱ� ����
	  final Gallery imageGallery = (Gallery)findViewById(R.id.girlsGroupImageGallery);
	  //Gallery Widget�� Adapter�� ���� �Ѵ�
	  imageGallery.setAdapter(groupImageAdapter);
  }
  private  void dbInsertGirlsGroupMusicInfo(GirlsGroupMusicInfo groupGirlsInfo){
	    
	  girlsDB.beginTransaction();
		try {
			//�ش� �ɱ׷��� ���� ���θ� Ȯ��
			long rowGroupId = 0;

			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
			queryBuilder.setTables(GirlsGroupInfo.TABLE_NAME);
			queryBuilder.appendWhere(GirlsGroupInfo.TEAM_NAME + "='" + 
					                           groupGirlsInfo.getGroupName()+"'");
            //������ ����.
			Cursor cursor = queryBuilder.query(girlsDB, null, null, null, null, null,null);
		
			if (cursor.getCount() == 0) { //�ش� �ɱ׷��� ���ٸ�
				ContentValues recordToAdd = new ContentValues();
				recordToAdd.put(GirlsGroupInfo.TEAM_NAME,
						              groupGirlsInfo.getGroupName());
				rowGroupId = girlsDB.insert(
						               GirlsGroupInfo.TABLE_NAME,
						               GirlsGroupInfo.TEAM_NAME, 
						               recordToAdd);
			} else {
				//���� �ϸ� ó������ ���� ��.
                cursor.moveToFirst();
				//�Ƶ� ���� ��� �´�.
                rowGroupId = cursor.getLong(cursor.getColumnIndex(GirlsGroupInfo._ID));
			}
			cursor.close();

			// �ɱ׷��� ������ �Է�
			ContentValues girlsGroupValues = new ContentValues();
			girlsGroupValues.put(
					GirlsGroupMusic.MUSIC_TITLE, groupGirlsInfo.getMusicName());
			girlsGroupValues.put(
					GirlsGroupMusic.GIRLS_GROUP_ID, rowGroupId);
			girlsGroupValues.put(
					GirlsGroupMusic.GROUP_IMAGE_URI, groupGirlsInfo.getImageUriPath());
			girlsGroupValues.put(
					GirlsGroupMusic.GROUP_IMAGE_ID, groupGirlsInfo.getImageId());
			
			girlsDB.insert(
					GirlsGroupMusic.TABLE_NAME, 
					GirlsGroupMusic.MUSIC_TITLE, girlsGroupValues);
			girlsDB.setTransactionSuccessful();
			Toast.makeText(this, "���������� ���� �Ǿ���!" ,Toast.LENGTH_SHORT).show();
		}catch(SQLiteException sqle){
			Log.e("DB INSERT ERROR ", sqle.toString());
			Toast.makeText(this, "DB INSERT ERROR Logcat��� Ȯ�� ���!" ,Toast.LENGTH_SHORT).show();
		} finally {
			girlsDB.endTransaction();
		}
  }
  public  class  GroupImageUriAdapter extends BaseAdapter{
	    int mGalleryItemBackground;
		private GirlsGroupGalleryInfo [] mPics;
		private Context mContext;

		public GroupImageUriAdapter(Context c, Cursor curs, Uri baseUri) {
			mContext = c;
			int iNumPics = curs.getCount();
			mPics = new GirlsGroupGalleryInfo[iNumPics];

			curs.moveToFirst();
			for (int i = 0; i < iNumPics; i++) {				
				final String strUri = baseUri.toString();
				final long iId = curs.getLong(curs.getColumnIndex(Media._ID));	
				mPics[i] = new GirlsGroupGalleryInfo(strUri, iId);
				curs.moveToNext();
			}
			// /res/values/attrs.xml�� ����� ���� �Ӽ� ���(R.java Ȯ�� �ٶ�)
			TypedArray typedArray = obtainStyledAttributes(R.styleable.default_gallery);
			mGalleryItemBackground = typedArray.getResourceId(
					R.styleable.default_gallery_android_galleryItemBackground, 0);
			//��Ÿ�� ���
			typedArray.recycle();
		}

		public int getCount() {
			return mPics.length;
		}
		public Object getItem(int position) {		
			return mPics[position].getImageId(); 	
		}
		public long getItemId(int position) {
			return mPics[position].getImageId();
		}
		//�並 ������ �Ͽ� ����
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

		    Uri baseUri = Uri.parse(mPics[position].getImageUriPath());
			Uri myImageUri = ContentUris.withAppendedId(
					baseUri, mPics[position].getImageId());
			
			i.setImageURI(myImageUri);
			i.setTag(mPics[position]);
			
			i.setMaxHeight(100);
			i.setMaxWidth(100);
			//�̹����� ����/���� ������ ���� ����
			i.setAdjustViewBounds(true);
			//��׶��� ��Ÿ�� ����
			i.setBackgroundResource(mGalleryItemBackground);
			return i;
		}
	}
  }

