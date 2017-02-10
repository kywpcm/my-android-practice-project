package com.example.sqlitecustomcursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitecustomcursoradapter.R;
import com.example.sqlitecustomcursoradapter.GirlsGroupDB.GirlsGroupInfo;
import com.example.sqlitecustomcursoradapter.GirlsGroupDB.GirlsGroupMusic;

public class CustomCursorAdapterActivity extends GirlsGroupBaseActivity{

	private ListView list;
	private TextView songTitle;
	private TextView groupName;
	private Button deleteBtn;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.girls_list_layout);

		setList();

		final Button finishBtn = (Button)findViewById(R.id.historyBackBtn);
		finishBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setList() {
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();

		//걸그룹 테이블의 ID와 뮤직테이블 ID값의 해당하는 데이터를 가져오는 쿼리를 빌드
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + "," + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo._ID);

		//결과 집합으로 가져올 컬럼의 이름들(2개 이상 테이블로 빌드시 풀네임을 주어야 함)
		String columnsToReturn [] = {
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};

		//빌드된 쿼리로 해당 결과 집합을 가져 온다
		Cursor joinResultSet = queryBuilder.query(
				dbHandler, 
				columnsToReturn, 
				null, null, null, null, 
				GirlsGroupMusic.SORT_ORDER);

		//세번째 인자 autoRequery false..
		CustomCursorAdapter customAdapter = new CustomCursorAdapter(
				this, joinResultSet, false);

		list = (ListView)findViewById(R.id.listView);
		list.setAdapter(customAdapter);
		list.setOnItemClickListener(itemClickListener);
		list.setClickable(true);
	}

	//CursorAdapter를 상속한 Inner CustomCursorAdapter 클래스..
	private class CustomCursorAdapter extends CursorAdapter {

		public CustomCursorAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		//세 번째 인자 autoCursor = autoRequery
		public CustomCursorAdapter(Context context, Cursor cursor, boolean autoCursor){
			super(context, cursor, autoCursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			groupName = (TextView) view.findViewById(R.id.groupNameText);
			songTitle = (TextView) view.findViewById(R.id.songTitleText);
			deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
			
			//각 레코드의 값을 해당 Widget에 설정 한다.
			groupName.setText(cursor.getString(cursor.getColumnIndex(GirlsGroupInfo.TEAM_NAME)));
			songTitle.setText(cursor.getString(cursor.getColumnIndex(GirlsGroupMusic.MUSIC_TITLE)));
		}

		//리턴되는 View(루트 뷰)는 bindView(View view,,,)의 인자값으로 넘어 감
		//여기서 View를 생성하거나 인플레이트 함.
		//아직 위젯과 커서의 데이터 맵핑은 이루어 지지 않음..
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
//			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.my_item, parent, false);
//			View view = View.inflate(context, R.layout.item, null);
			return view;
		}

	}
	
	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){

		@Override
		public  void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String toastMessage = null;
			if(!deleteGirlsGroup(id)) { //제거 메소드 호출
				toastMessage = "심각한 문제 발생! LogCat정보 확인 바람!";
			} else {
				toastMessage = "정상적으로 제거 되었음!";
				Cursor cursor = (Cursor)parent.getItemAtPosition(position);
				cursor.requery();
			}
			Toast.makeText(getApplicationContext(),toastMessage, Toast.LENGTH_LONG).show();
		}
	};

	private  boolean deleteGirlsGroup(Long groupID){
		SQLiteDatabase dbHandler = null;
		boolean flag = false;
		try{
			dbHandler = girlsDB.getWritableDatabase();
			dbHandler.beginTransaction();
			dbHandler.delete(GirlsGroupMusic.TABLE_NAME, GirlsGroupMusic._ID 
					+ "=?", new String[] {String.valueOf(groupID)});
			dbHandler.setTransactionSuccessful();
			flag = true; //정상 처리
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
		}
		return flag;
	}

}
