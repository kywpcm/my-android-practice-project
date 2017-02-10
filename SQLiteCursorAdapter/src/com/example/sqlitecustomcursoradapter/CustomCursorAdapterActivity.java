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

		//�ɱ׷� ���̺��� ID�� �������̺� ID���� �ش��ϴ� �����͸� �������� ������ ����
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(GirlsGroupMusic.TABLE_NAME + "," + GirlsGroupInfo.TABLE_NAME);
		queryBuilder.appendWhere(GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.GIRLS_GROUP_ID 
				+ "=" + GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo._ID);

		//��� �������� ������ �÷��� �̸���(2�� �̻� ���̺�� ����� Ǯ������ �־�� ��)
		String columnsToReturn [] = {
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic._ID,
				GirlsGroupMusic.TABLE_NAME + "." + GirlsGroupMusic.MUSIC_TITLE,
				GirlsGroupInfo.TABLE_NAME + "." + GirlsGroupInfo.TEAM_NAME
		};

		//����� ������ �ش� ��� ������ ���� �´�
		Cursor joinResultSet = queryBuilder.query(
				dbHandler, 
				columnsToReturn, 
				null, null, null, null, 
				GirlsGroupMusic.SORT_ORDER);

		//����° ���� autoRequery false..
		CustomCursorAdapter customAdapter = new CustomCursorAdapter(
				this, joinResultSet, false);

		list = (ListView)findViewById(R.id.listView);
		list.setAdapter(customAdapter);
		list.setOnItemClickListener(itemClickListener);
		list.setClickable(true);
	}

	//CursorAdapter�� ����� Inner CustomCursorAdapter Ŭ����..
	private class CustomCursorAdapter extends CursorAdapter {

		public CustomCursorAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		//�� ��° ���� autoCursor = autoRequery
		public CustomCursorAdapter(Context context, Cursor cursor, boolean autoCursor){
			super(context, cursor, autoCursor);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			groupName = (TextView) view.findViewById(R.id.groupNameText);
			songTitle = (TextView) view.findViewById(R.id.songTitleText);
			deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
			
			//�� ���ڵ��� ���� �ش� Widget�� ���� �Ѵ�.
			groupName.setText(cursor.getString(cursor.getColumnIndex(GirlsGroupInfo.TEAM_NAME)));
			songTitle.setText(cursor.getString(cursor.getColumnIndex(GirlsGroupMusic.MUSIC_TITLE)));
		}

		//���ϵǴ� View(��Ʈ ��)�� bindView(View view,,,)�� ���ڰ����� �Ѿ� ��
		//���⼭ View�� �����ϰų� ���÷���Ʈ ��.
		//���� ������ Ŀ���� ������ ������ �̷�� ���� ����..
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
			if(!deleteGirlsGroup(id)) { //���� �޼ҵ� ȣ��
				toastMessage = "�ɰ��� ���� �߻�! LogCat���� Ȯ�� �ٶ�!";
			} else {
				toastMessage = "���������� ���� �Ǿ���!";
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
			flag = true; //���� ó��
		}catch(SQLiteException sql){
			Log.e("deleteGirlsGroup_ERROR", sql.toString());
		}finally{
			dbHandler.endTransaction();
		}
		return flag;
	}

}
