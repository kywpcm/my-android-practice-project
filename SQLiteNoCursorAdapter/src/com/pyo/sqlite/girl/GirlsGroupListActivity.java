package com.pyo.sqlite.girl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupInfo;
import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupMusic;

//리스트를 보여 줄 때, Adapter를 쓰고 있지 않다..
public class GirlsGroupListActivity extends GirlsGroupBaseActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.girls_list_layout);	   

		displayGirlsGroupList();

		final Button finishBtn = (Button)findViewById(R.id.historyBackBtn);
		finishBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void displayGirlsGroupList(){
		final TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayoutGroupList);
		
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();

		//복잡한 두 테이블 이상의 JOIN 등의 작업을 할 때는 SQLiteQueryBuilder 객체를 사용한다.
		//걸그룹 테이블의 _id와 뮤직 테이블의 걸그룹 ID 값에 해당하는 데이터를 가져오는 쿼리를 빌드
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + ", " + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME+"."+GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME+"."+GirlsGroupInfo._ID);

		//결과 집합으로 가져올 컬럼의 이름들(2개 이상 테이블로 빌드시 풀네임을 주어야 함)
		String columnsToReturn [] = {
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};
		
		//빌드된 쿼리로 해당 결과 집합을 가져 온다
		Cursor joinResultSet = queryBuilder.query(dbHandler,
				columnsToReturn,null,null,null,null,
				GirlsGroupMusic.SORT_ORDER);
		
		//Adapter를 쓰지 않아서 뷰와 커서 데이터의 연결 부분 코드가 매우 복잡해졌다..
		if(joinResultSet.moveToFirst()){
			int resultSetSize = joinResultSet.getCount();
			
			Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
			int rgbValue = Color.rgb(0,0,0);

			for(int i = 0; i < resultSetSize; i++){
				//테이블 레이아웃에 레코드 값들을 추가 한다
				TableRow insertRow = new TableRow(this);
				TextView musicName = new TextView(this);
				TextView groupName = new TextView(this);

				musicName.setTypeface(typeface);
				musicName.setTextSize(14);
				musicName.setTextColor(rgbValue);
				groupName.setTypeface(typeface);
				groupName.setTextSize(14);
				groupName.setTextColor(rgbValue);

				//TableRow 객체에 해당 ID를 태그로 놓고 나중에 레이아웃에서 현재 행을 삭제하기 위함
				insertRow.setTag(joinResultSet.getLong(joinResultSet.getColumnIndex(GirlsGroupMusic._ID)));

				//각 레코드의 값을 해당 Widget에 설정 한다
				musicName.setText(joinResultSet.getString(joinResultSet.getColumnIndex(GirlsGroupMusic.MUSIC_TITLE)));
				groupName.setText(joinResultSet.getString(joinResultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME)));

				//걸그룹을 제거 할 때 호출 될 버튼 생성
				Button deleteBtn = new Button(this);  //세번째 컬럼
				deleteBtn.setText("삭제");
				deleteBtn.setTypeface(typeface);
				
				//ID값은 나중에 삭제 버튼을 터치 할 때 편하게 삭제하도록 Tag로 설정한다
				deleteBtn.setTag(joinResultSet.getLong(joinResultSet.getColumnIndex(GirlsGroupInfo._ID)));
				deleteBtn.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View btn){
						
						//제거할 ID값을 Button Tag에서 가져 온다
						Long id = (Long)btn.getTag();
						String toastMessage = null;
						if(!deleteGirlsGroup(id)){ //제거 메소드 호출
							toastMessage = "심각한 문제 발생! LogCat정보 확인 바람!";
						}else{
							toastMessage = "정상적으로 제거 되었음!";
							//ID값이 태그로 설정된 TableRow 객체를 찾아온다
							TableRow removeRow = (TableRow)tableLayout.findViewWithTag(id);
							//현재 보여지는 해당 행을 제거 한다
							tableLayout.removeView(removeRow);
						}
						Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
					}
				});
				
				//TableRow에 해당 위젯을 붙임
				insertRow.addView(musicName);
				insertRow.addView(groupName);
				insertRow.addView(deleteBtn);

				//TableRow를 TableLayout에 추가
				tableLayout.addView(insertRow);
				joinResultSet.moveToNext();
			} //for문 끝
		} else{
			TableRow insertRow = new TableRow(this);
			TextView  noRecord = new TextView(this);
			noRecord.setText("등록된 데이터 없음!");
			insertRow.addView(noRecord);
			tableLayout.addView(insertRow);
		}
		
		joinResultSet.close();
		dbHandler.close();
	}
	
	private boolean deleteGirlsGroup(Long groupID){
		SQLiteDatabase dbHandler = null;
		boolean flag = false;
		try{
			dbHandler = girlsDB.getWritableDatabase();
			dbHandler.beginTransaction();
			dbHandler.delete(GirlsGroupInfo.TABLE_NAME,
					GirlsGroupInfo._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.delete(GirlsGroupMusic.TABLE_NAME,
					GirlsGroupMusic._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.setTransactionSuccessful();
			flag = true; //정상 처리
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
			dbHandler.close();
		}
		return flag;
	}
	
}