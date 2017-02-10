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
   protected Cursor  cursorAutoComplete; //커서 자동 완성 기능
   protected Cursor  imagesCursor ;// Image 커서
   
   @Override
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.girls_group_media_entry_layout);
	   
	   //이미지 어댑터로 갤러리 이미지를 구성 한다.
	   //SD카드로 부터 이미지 자원을 전부 가져 온다
	   displayGalleryFromMedia();
	   //자동 완성 기능(DB에서)
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
			   //이미지의 Id값을 얻어 온다
			   imageId = groupGallInfo.getImageId();
			   //이미지가 저장되어 있는 Uri 객체를 얻는다
			   imageUriPathString = groupGallInfo.getImageUriPath();
			}
			String  strGroupName = groupName.getText().toString();
			String  strMusicName =  musicName.getText().toString();
			
			//각 걸그룹의 대한 정보를 바탕으로 걸그룹의 정보를 가진 객체를 생성
			GirlsGroupMusicInfo groupMusicInfo = new GirlsGroupMusicInfo(
					 strMusicName,strGroupName,imageUriPathString,imageId,
					 GirlsGroupMusicInfo.INVALID_GIRLS_GROUP_ID);
			dbInsertGirlsGroupMusicInfo(groupMusicInfo);
			
			groupName.setText(null);
			musicName.setText(null);
		}
	  });
	   // 리스트 버튼 보기 버튼
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
    *  그 전 예제 하고 다른 구성이니 확인 하기 바람
    *  자동 완성 기능
    */
   private  void  applyAutoCompleteFromTBLGirlsGroupInfo(){
	   cursorAutoComplete = 
		         girlsDB.query(
			       GirlsGroupInfo.TABLE_NAME,
			       new String[]{ GirlsGroupInfo.TEAM_NAME, GirlsGroupInfo._ID},
			       null,null,null,null,
                   GirlsGroupInfo.SORT_ORDER
	             );
	   //Activity에게 커서의 관리를 위임 함
	   startManagingCursor(cursorAutoComplete);
	   //어댑터 객체에 등록 함
	   SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
			       android.R.layout.simple_dropdown_item_1line,
			       cursorAutoComplete,
			       new String[]{GirlsGroupInfo.TEAM_NAME},
			       new int[]{android.R.id.text1});
	   
	   //커서객체에서 문자열을 넘겨 줄 때 DB와 Adapter의 문자열을 부합 시키기 위함
	   adapter.setCursorToStringConverter(new TeamNameToStringConverter());
	   //DB와 어댑터의 문자열과 부합 시키기 위함
	   adapter.setFilterQueryProvider(new CustomFilterQueryProvider());
	   
	   AutoCompleteTextView autoText = 
		    (AutoCompleteTextView)findViewById(R.id.autoTextGirlsGroupFinder);
	   autoText.setAdapter(adapter);
   }
// 커서객체에서 문자열을 넘겨줄때 발생
  private class TeamNameToStringConverter implements 
	            SimpleCursorAdapter.CursorToStringConverter {

		public CharSequence convertToString(Cursor cursor) {
			return cursor.getString(
					   cursor.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
			
		}
	}
  //쿼리객체에서 쿼리를 넘길때 자동 호출 됨
  private 	class CustomFilterQueryProvider implements FilterQueryProvider {

		public Cursor runQuery(CharSequence constraint) {
			if ((constraint != null) && (cursorAutoComplete != null)){
				String strWhere = GirlsGroupInfo.TEAM_NAME  + " LIKE ?";	              
				//매니징 스톱
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
	   //MediaStore ContentProvider 사용
	  String [] projection = new String[]{Media._ID,Media.TITLE};
	 
	  //SD Card 의 URL 값
	  Uri  sdcardUri = Media.EXTERNAL_CONTENT_URI;
	  //질의를 한다.
	  imagesCursor = managedQuery(
			                sdcardUri, projection,null,null,
			                Media.DATE_TAKEN + "  ASC");
	  //커서로부터 GroupImageUriAdapter객체를 얻는다.
	  GroupImageUriAdapter  groupImageAdapter = 
		    new GroupImageUriAdapter(this, imagesCursor, sdcardUri);
	  //이미지를 디스플레이 하기 위함
	  final Gallery imageGallery = (Gallery)findViewById(R.id.girlsGroupImageGallery);
	  //Gallery Widget에 Adapter를 부착 한다
	  imageGallery.setAdapter(groupImageAdapter);
  }
  private  void dbInsertGirlsGroupMusicInfo(GirlsGroupMusicInfo groupGirlsInfo){
	    
	  girlsDB.beginTransaction();
		try {
			//해당 걸그룹의 존재 여부를 확인
			long rowGroupId = 0;

			SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
			queryBuilder.setTables(GirlsGroupInfo.TABLE_NAME);
			queryBuilder.appendWhere(GirlsGroupInfo.TEAM_NAME + "='" + 
					                           groupGirlsInfo.getGroupName()+"'");
            //쿼리를 날림.
			Cursor cursor = queryBuilder.query(girlsDB, null, null, null, null, null,null);
		
			if (cursor.getCount() == 0) { //해당 걸그룹이 없다면
				ContentValues recordToAdd = new ContentValues();
				recordToAdd.put(GirlsGroupInfo.TEAM_NAME,
						              groupGirlsInfo.getGroupName());
				rowGroupId = girlsDB.insert(
						               GirlsGroupInfo.TABLE_NAME,
						               GirlsGroupInfo.TEAM_NAME, 
						               recordToAdd);
			} else {
				//존재 하면 처음으로 돌아 감.
                cursor.moveToFirst();
				//아뒤 값을 얻어 온다.
                rowGroupId = cursor.getLong(cursor.getColumnIndex(GirlsGroupInfo._ID));
			}
			cursor.close();

			// 걸그룹의 정보를 입력
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
			Toast.makeText(this, "성공적으로 저장 되었음!" ,Toast.LENGTH_SHORT).show();
		}catch(SQLiteException sqle){
			Log.e("DB INSERT ERROR ", sqle.toString());
			Toast.makeText(this, "DB INSERT ERROR Logcat기록 확인 요망!" ,Toast.LENGTH_SHORT).show();
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
			// /res/values/attrs.xml에 선언된 내장 속성 사용(R.java 확인 바람)
			TypedArray typedArray = obtainStyledAttributes(R.styleable.default_gallery);
			mGalleryItemBackground = typedArray.getResourceId(
					R.styleable.default_gallery_android_galleryItemBackground, 0);
			//스타일 사용
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
		//뷰를 재정의 하여 구현
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

		    Uri baseUri = Uri.parse(mPics[position].getImageUriPath());
			Uri myImageUri = ContentUris.withAppendedId(
					baseUri, mPics[position].getImageId());
			
			i.setImageURI(myImageUri);
			i.setTag(mPics[position]);
			
			i.setMaxHeight(100);
			i.setMaxWidth(100);
			//이미지의 가로/세로 비율을 조절 가능
			i.setAdjustViewBounds(true);
			//백그라운드 스타일 적용
			i.setBackgroundResource(mGalleryItemBackground);
			return i;
		}
	}
  }

