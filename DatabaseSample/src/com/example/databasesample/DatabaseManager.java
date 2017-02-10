package com.example.databasesample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.example.databasesample.DBConstant.PersonTable;

public class DatabaseManager {
	private static final String TAG = "DatabaseManager";

	Context mContext;

	DatabaseOpenHelper mHelper;

	public interface OnQueryCompleteListener {
		public void onCompleted(ArrayList<Person> personlist);
	}

	private static DatabaseManager instance;
	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	private DatabaseManager() {
		mContext = MyApplication.getContext();
		mHelper = new DatabaseOpenHelper(mContext);
	}

	public void savePerson(Person person) {
		SQLiteDatabase db = mHelper.getWritableDatabase();  //db ����, ���� ����..

		//ContentResolver�� ó���� �� �ִ� �����͸� �����ϴ� Ŭ�����̴�.
		ContentValues values = new ContentValues();

		values.put(PersonTable.NAME, person.name);  //key, value
		values.put(PersonTable.ADDRESS, person.address);

		db.insert(PersonTable.TABLE_NAME, null, values);  //���� �ְ�,
		db.close();  //�ݱ�
	}

	public void getPersonTable(final OnQueryCompleteListener listener, final Handler handler) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.i(TAG, "�ٸ� ������");

				final ArrayList<Person> list = new ArrayList<Person>();
				
				SQLiteDatabase db = mHelper.getReadableDatabase();  //db ����, �б� ����..
				String[] columns = {PersonTable.ID, PersonTable.NAME, PersonTable.ADDRESS};

				Cursor c = db.query(PersonTable.TABLE_NAME, columns, null, null, null, null, null);  //Ŀ�� ���ͼ�,
				int indexID = c.getColumnIndex(PersonTable.ID);  //�÷� �ε����� ���� ��,
				int indexName = c.getColumnIndex(PersonTable.NAME);
				int indexAddress = c.getColumnIndex(PersonTable.ADDRESS);

				while(c.moveToNext()) {  //Ŀ���� �̵���Ű�鼭,
					Person p = new Person();
					p.id = c.getInt(indexID);  //�÷� �ε����� ���� �����͸� �̾Ƴ���.
					p.name = c.getString(indexName);
					p.address = c.getString(indexAddress);
					list.add(p);
				}
				
				c.close();  //�� ����, Ŀ�� �ݱ�
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						Log.i(TAG, "���� ������");
						listener.onCompleted(list);
					}
				});
			}
		}).start();
	}
	
}
