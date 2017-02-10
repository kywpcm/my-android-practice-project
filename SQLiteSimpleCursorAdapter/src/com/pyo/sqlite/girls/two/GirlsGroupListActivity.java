package com.pyo.sqlite.girls.two;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.pyo.sqlite.girls.two.GirlsGroupDB.GirlsGroupInfo;
import com.pyo.sqlite.girls.two.GirlsGroupDB.GirlsGroupMusic;

/**********************************************************************************
 *  Adapter를 Cursor와 적용한 Girls Group 업그레이드 예제
 *********************************************************************************/
//안드로이드 내장 SimpleCursorAdapter 사용..
public class GirlsGroupListActivity extends GirlsGroupBaseActivity {

	private static final String TAG = "GirlsGroupListActivity";
	
	private SQLiteDatabase dbHandler;
	private Cursor joinResultSet;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.girls_adapter_layout);

		final Button finishBtn = (Button)findViewById(R.id.historyBackBtn);
		finishBtn.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		
		//그 전에 예제와 다르게 Adapter와 Cursor를 연결한 예제
		displayGirlsGroupList();
	}

	private void displayGirlsGroupList() {

		dbHandler = girlsDB.getReadableDatabase();

		//복잡한 두 테이블 이상의 JOIN 등의 작업을 할 때는 SQLiteQueryBuilder 객체를 사용한다.
		//걸그룹 테이블의 ID와 뮤직테이블 ID값에 해당하는 데이터를 가져오는 쿼리를 빌드
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + "," + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME+"."+GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME+"."+GirlsGroupInfo._ID);

		//결과 집합으로 가져올 컬럼의 이름들(2개 이상 테이블로 빌드시 풀네임을 주어야 함)
		String columnsToReturn [] = {
				//onItemClick() 메소드의 id 인자에 넘어오는 _id 칼럼을 구한다..
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
//				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo._ID,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};

		//빌드된 쿼리로 해당 결과 집합을 가져 온다
		joinResultSet = queryBuilder.query(dbHandler,
				columnsToReturn,null,null,null,null,
				GirlsGroupMusic.SORT_ORDER);

		//커서의 관리를 현재 Activity에 위임
		//deprecated 됐다..
		//왠만하면 쓰지 말자..
//		startManagingCursor(joinResultSet);

		//현재 결과 집합을 Adapter와 Mapping시켜 레이아웃 적용 후 출력 한다
		//주의! SimpleCursorAdapter는 어느 시점(?)에 자동으로 requery한다..
		//deprecated
		//SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) 생성자
		final SimpleCursorAdapter resultSetAdapter = new SimpleCursorAdapter(
				this,
				R.layout.group_list_item,
				joinResultSet,
				new String[]{GirlsGroupMusic.MUSIC_TITLE, GirlsGroupInfo.TEAM_NAME},
				new int[]{R.id.textMusicName, R.id.textGroupName});

		ListView listFromAdapter = (ListView)findViewById(R.id.adapterGirlsGroup);
		listFromAdapter.setAdapter(resultSetAdapter);

		//ListView에 각 item에 이벤트 등록 및 처리 하기
		listFromAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//인자로 넘어온 id 값 사용..
			public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
				Log.w(TAG, "onItemClick(), id : " + id);
				
				final long deleteMusicItem = id;
				
				//삭제 여부를 묻는 대화 상자 오픈
				new AlertDialog.Builder(GirlsGroupListActivity.this)
				.setMessage("삭제 할까요?")
				.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which){
						//해당 걸그룹의 리스트를 삭제
						if(deleteGirlsGroup(deleteMusicItem)){
							//쿼리를 다시 호출 함
							//deprecated
							//requery() 메소드
							joinResultSet.requery();  //다시 query할 때 dbHandler가 필요한데 닫혀 있으면 어댑터가 새로운 화면을 갱신하디 못한다..
						}
					}
				}).show();  //PositiveButton 이벤트 설정
			}  //onItemClick() 끝
		});  //ListView Item에 대한 이벤트 등록
		
//		dbHandler.close();
//		joinResultSet.close();  //어댑터가 화면을 구성하는 시점에 이미 닫혀 있는 것 같다..
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
		
		dbHandler.close();
		joinResultSet.close();
	}

	private boolean deleteGirlsGroup(long groupID){
		SQLiteDatabase dbHandler = null;
		boolean flag = false;
		try{
			dbHandler = girlsDB.getWritableDatabase();
			dbHandler.beginTransaction();
//			dbHandler.delete(GirlsGroupInfo.TABLE_NAME, 
//					GirlsGroupInfo._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.delete(GirlsGroupMusic.TABLE_NAME, 
					GirlsGroupMusic._ID + "=?", new String[]{String.valueOf(groupID)});
			dbHandler.setTransactionSuccessful();
			flag = true; //정상 처리
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
//			dbHandler.close();
		}
		return flag;
	}

}