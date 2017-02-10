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
 *  Adapter�� Cursor�� ������ Girls Group ���׷��̵� ����
 *********************************************************************************/
//�ȵ���̵� ���� SimpleCursorAdapter ���..
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
		
		//�� ���� ������ �ٸ��� Adapter�� Cursor�� ������ ����
		displayGirlsGroupList();
	}

	private void displayGirlsGroupList() {

		dbHandler = girlsDB.getReadableDatabase();

		//������ �� ���̺� �̻��� JOIN ���� �۾��� �� ���� SQLiteQueryBuilder ��ü�� ����Ѵ�.
		//�ɱ׷� ���̺��� ID�� �������̺� ID���� �ش��ϴ� �����͸� �������� ������ ����
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + "," + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME+"."+GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME+"."+GirlsGroupInfo._ID);

		//��� �������� ������ �÷��� �̸���(2�� �̻� ���̺�� ����� Ǯ������ �־�� ��)
		String columnsToReturn [] = {
				//onItemClick() �޼ҵ��� id ���ڿ� �Ѿ���� _id Į���� ���Ѵ�..
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
//				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo._ID,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};

		//����� ������ �ش� ��� ������ ���� �´�
		joinResultSet = queryBuilder.query(dbHandler,
				columnsToReturn,null,null,null,null,
				GirlsGroupMusic.SORT_ORDER);

		//Ŀ���� ������ ���� Activity�� ����
		//deprecated �ƴ�..
		//�ظ��ϸ� ���� ����..
//		startManagingCursor(joinResultSet);

		//���� ��� ������ Adapter�� Mapping���� ���̾ƿ� ���� �� ��� �Ѵ�
		//����! SimpleCursorAdapter�� ��� ����(?)�� �ڵ����� requery�Ѵ�..
		//deprecated
		//SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) ������
		final SimpleCursorAdapter resultSetAdapter = new SimpleCursorAdapter(
				this,
				R.layout.group_list_item,
				joinResultSet,
				new String[]{GirlsGroupMusic.MUSIC_TITLE, GirlsGroupInfo.TEAM_NAME},
				new int[]{R.id.textMusicName, R.id.textGroupName});

		ListView listFromAdapter = (ListView)findViewById(R.id.adapterGirlsGroup);
		listFromAdapter.setAdapter(resultSetAdapter);

		//ListView�� �� item�� �̺�Ʈ ��� �� ó�� �ϱ�
		listFromAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//���ڷ� �Ѿ�� id �� ���..
			public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
				Log.w(TAG, "onItemClick(), id : " + id);
				
				final long deleteMusicItem = id;
				
				//���� ���θ� ���� ��ȭ ���� ����
				new AlertDialog.Builder(GirlsGroupListActivity.this)
				.setMessage("���� �ұ��?")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which){
						//�ش� �ɱ׷��� ����Ʈ�� ����
						if(deleteGirlsGroup(deleteMusicItem)){
							//������ �ٽ� ȣ�� ��
							//deprecated
							//requery() �޼ҵ�
							joinResultSet.requery();  //�ٽ� query�� �� dbHandler�� �ʿ��ѵ� ���� ������ ����Ͱ� ���ο� ȭ���� �����ϵ� ���Ѵ�..
						}
					}
				}).show();  //PositiveButton �̺�Ʈ ����
			}  //onItemClick() ��
		});  //ListView Item�� ���� �̺�Ʈ ���
		
//		dbHandler.close();
//		joinResultSet.close();  //����Ͱ� ȭ���� �����ϴ� ������ �̹� ���� �ִ� �� ����..
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
			flag = true; //���� ó��
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
//			dbHandler.close();
		}
		return flag;
	}

}