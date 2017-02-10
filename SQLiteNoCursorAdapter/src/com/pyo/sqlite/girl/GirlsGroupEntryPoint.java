package com.pyo.sqlite.girl;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupInfo;
import com.pyo.sqlite.girl.GirlsGroupDB.GirlsGroupMusic;

public class GirlsGroupEntryPoint extends GirlsGroupBaseActivity {
	private static final String TAG = "GirlsGroupEntryPoint";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.i("GirlsGroupEntryPoint", "onCreate()");
		setContentView(R.layout.girls_group_layout);

		//액티비티 화면의 정보를 DB에 저장 하는 이벤트
		Button saveBtn = (Button)findViewById(R.id.girlsGroupInsert);
		saveBtn.setOnClickListener(girlsSaveListener);

		//전체 걸그룹의 리스트를 출력하는 인텐트 실행 버튼
		Button displayBtn = (Button)findViewById(R.id.girlGroupDisplayBtn);
		displayBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), GirlsGroupListActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//등록된 걸그룹의 이름들을 이용하여 자동 완성 기능 적용
		applyAutoCompleteFromTBLGirlsGroupInfo();
	}

	//해당 테이블에서 등록된 걸그륩의 이름을 ArrayAdapter에 채움
	private void applyAutoCompleteFromTBLGirlsGroupInfo(){

		//읽기 가능한 DB를 얻는다.
		SQLiteDatabase dbHandler = girlsDB.getReadableDatabase();
		
		//쿼리를 조합 한다.
		Cursor resultSet = dbHandler.query(
				GirlsGroupInfo.TABLE_NAME,
				new String[]{GirlsGroupInfo.TEAM_NAME},
				null, null, null, null,
				GirlsGroupInfo.SORT_ORDER);
		
		//등록된 걸그룹의 갯수를 가져 온다
		int numberOfGroupNames = resultSet.getCount();
		String[] numberOfAutoText = new String[numberOfGroupNames];

		if(numberOfGroupNames > 0){
			
			resultSet.moveToFirst();
			for(int i = 0; i < numberOfGroupNames; i++){
				/*************************************************************************
				 *  질의 결과를 바탕으로 컬럼의 이름을 이용해 현재 컬럼의 위치를 파악하고
				 *  커서의 현재 위치에서의 행의 값을 가져온다
				 *************************************************************************/
				Log.d(TAG, "applyAutoCompleteFromTBLGirlsGroupInfo()\n" +
						"현재 커서 위치 : " + resultSet.getPosition());
				numberOfAutoText[i] = resultSet.getString(resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				Log.d(TAG, "resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME)\n" + 
						"col_team_name의 컬럼 인덱스 : " + resultSet.getColumnIndex(GirlsGroupInfo.TEAM_NAME));
				//질의 결과의 다음 행으로 커서를 이동 시킨다
				resultSet.moveToNext();
			}
			
			ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_dropdown_item_1line,
					numberOfAutoText);
			
			//AutoCompleteTextView에 현재 DB에서 가져온 정보를 바탕으로 자동 완성 기능을 추가 한다
			AutoCompleteTextView autoText = (AutoCompleteTextView)findViewById(R.id.autoTextGirlsGroupFinder);
			autoText.setAdapter(autoTextAdapter);

		}
		
		resultSet.close();
		dbHandler.close();
	}

	private OnClickListener girlsSaveListener = new OnClickListener(){

		public void onClick(View  button){
			
			final EditText girlsGroupMusicName = (EditText)findViewById(R.id.musicNameEdit);
			final EditText girlsGroupName = (EditText)findViewById(R.id.autoTextGirlsGroupFinder);
			
			//각 테이블에 레코드를 저장하기 위함
			SQLiteDatabase dbHandler = girlsDB.getWritableDatabase();

			//트랜잭션을 시작
			dbHandler.beginTransaction();
			Cursor resultExist  = null;
			//뮤직테이블로 입력하여 외래키의 효과를 주기 위함
			long girlGroupID = 0 ;
			try{
				String groupName = girlsGroupName.getText().toString();

				//걸그룹이름이 등록되었는지 확인해 보는 쿼리 빌더
				SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
				queryBuilder.setTables(GirlsGroupInfo.TABLE_NAME);
				queryBuilder.appendWhere(GirlsGroupInfo.TEAM_NAME +  "='" + groupName + "'");
				//빌드된 쿼리로 결과집합을 알아 본다
				//쿼리빌더를 사용 할 때는 첫번째 인자에 SQLiteDatabase 객체를 등록함
				resultExist = queryBuilder.query(dbHandler,null,null,null,null,null,null);
				
				if(resultExist.getCount() == 0){ //그룹명이 존재 하지 않는다면
					//ContentValues 객체를 만든다.
					ContentValues groupNameValues = new ContentValues();
					//컬럼명, 값의 map 구조..
					groupNameValues.put(GirlsGroupInfo.TEAM_NAME, groupName);
					//레코드 추가..
					//현재 저장된 그룹이름의 ID 값을 가져 온다(문제시는 -1)
					girlGroupID = dbHandler.insert(GirlsGroupInfo.TABLE_NAME, "NODATA", groupNameValues);
				}else{ //그룹명이 존재 한다면 _ID값을 가져 온다
					resultExist.moveToFirst();
					Log.d(TAG, "resultExist.moveToFirst();\n" +
							" 현재 커서 위치 : " + resultExist.getPosition());
					//컬럼명을 통해 컬럼 인덱스를 가져온다.
					girlGroupID = resultExist.getLong(resultExist.getColumnIndex(GirlsGroupInfo._ID));
					Log.d(TAG, "resultExist.getColumnIndex(GirlsGroupInfo._ID)\n" + 
							" _id 컬럼 인덱스 : " + resultExist.getColumnIndex(GirlsGroupInfo._ID));
				}
				
				//MUSIC 테이블에 걸그룹의 음악과 ID값을 추가 한다.
				ContentValues musicRowValues = new ContentValues();
				musicRowValues.put(GirlsGroupMusic.MUSIC_TITLE, girlsGroupMusicName.getText().toString());
				musicRowValues.put(GirlsGroupMusic.GIRLS_GROUP_ID, girlGroupID);
				dbHandler.insert(GirlsGroupMusic.TABLE_NAME, "NODATA", musicRowValues);

				//트랜잭션 성공
				dbHandler.setTransactionSuccessful();
				Toast.makeText(GirlsGroupEntryPoint.this, "정상 처리 되었습니다!", Toast.LENGTH_SHORT).show();
			} catch(SQLiteException sqle){
				Log.e("girlsSaveListenerERROR", sqle.toString());
				Toast.makeText(GirlsGroupEntryPoint.this, 
						"심각한 에러 발생! LogCat 확인 바람!", 
						Toast.LENGTH_LONG).show();
			} finally{
				dbHandler.endTransaction();
				resultExist.close();
				dbHandler.close();
			}
			
			girlsGroupMusicName.setText("");
			girlsGroupName.setText("");
		}
	};

}